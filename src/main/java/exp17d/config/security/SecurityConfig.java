package exp17d.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Value("${mh.security.authserver.BCryptPasswordEncoder.usedToEncodePassword}")
	private boolean useBCryptPasswordEncoder2encodePassword;
	
	/**
	 * NOTE ilker starting with Spring 5, which spring-boot-starter-parent 2.x uses, have to use a password encoder 
	 *      otherwise password check will fail with below WARN message in logs (console)
	 *      WARN BCryptPasswordEncoder "Encoded password does not look like BCrypt"
	 *      So when spring-boot-starter-parent version is 2.x, then make sure to set "mh.security.authserver.BCryptPasswordEncoder.usedToEncodePassword=true" in application.properties
	 * NOTE ilker BCryptPasswordEncoder always generates a random salt, so if you invoke it 2 times with SAME input, it will return 2 DIFFERENT output
	 * @return encoded rawPassword if {@link #useBCryptPasswordEncoder2encodePassword} == true. Otherwise rawPassword 
	 */
	private String encode(String rawPassword) {
		String password = useBCryptPasswordEncoder2encodePassword ? passwordEncoder().encode(rawPassword) : rawPassword;
		logger.info("--ILKER --> encode({}) with useBCryptPasswordEncoder2encodePassword:{} returning password as {}", rawPassword, useBCryptPasswordEncoder2encodePassword, password);
		return password;
	}
		
	/** NOTE ilker starting with Spring 5, which spring-boot-starter-parent 2.x uses, you are required to use a password encoder while setting password */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// sample MVC urls
				.antMatchers("/", "/home","/public**").permitAll()	// NOTE ilker urls allowed to see with or without login(without being authenticated == anonymous)
				.antMatchers("/anonymous").anonymous()				// NOTE ilker the difference of this is from above is, if user is logged in, then this page will NOT be accessible. When user is not logged in, this is another way of doing same thing as above, another url allowed to see without login
				.antMatchers("/authenticated", "userSettings/**").authenticated()	// NOTE ilker probably not needed as ".anyRequest().authenticated()" line further below will cover this and "/user" url
				.antMatchers("/admin", "/h2_console/**").hasRole("ADMIN")
				// mh related example MVC urls
				.antMatchers("/patient/**").hasRole("PATIENT")
				.antMatchers("/provider/**").hasAnyRole("PROVIDER", "PROVIDER_OWNER")
				.antMatchers("/employer/**").hasAnyRole("EMPLOYER", "EMPLOYER_OWNER")
				.antMatchers("/partner/**").hasAnyRole("PARTNER", "PARTNER_OWNER")
				.antMatchers("/internal/**").hasAnyRole("INTERNAL", "INTERNAL_OWNER")
				// REST api urls
				.antMatchers("/rest/v1/patient/echoMessage").hasRole("PATIENT")
				.antMatchers("/rest/v1/provider/echoMessage").hasAnyAuthority("PROVIDER_CAN_ECHO", "PROVIDER_CAN_VIEW_APPOINMENTS")	// NOTE can also use "authority"("permission") instead of "role"
				// NOTE ilker rest of urls that are not matched via above antMathers will be caught by below line. Rest of urls require user to be logged in, meaning if authenticated then allow to proceed to that url, otherwise redirect to "/login"
				.anyRequest().authenticated()	// NOTE ilker if not authenticated and trying to access an authenticated url, it will 1st try to go to url, then will be "redirected"(302) to "login" page(with "location" attribute in response pointing to login url), after user logs in, he will go to url 
				.and()
				// NOTE ilker below line is to configure "HTTP Basic Authentication with Spring Security", which uses browser provided login popup
//				.httpBasic()
				// NOTE ilker below 3 lines is for "Form validation with Spring Security"
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll()
				;
		
		http.exceptionHandling().accessDeniedPage("/403");
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	protected void configure_OLD(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.antMatchers("/admin", "/h2_console/**").hasRole("ADMIN").anyRequest()
				.authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll();
		http.exceptionHandling().accessDeniedPage("/403");
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	/**
	 * NOTE ilker starting with Spring 5, which spring-boot-starter-parent 2.x uses, have to use a password encoder 
	 *      otherwise password check will fail with below WARN message in logs (console)
	 *      WARN BCryptPasswordEncoder "Encoded password does not look like BCrypt"
	 *      So when spring-boot-starter-parent version is 2.x, then make sure to set "mh.security.authserver.BCryptPasswordEncoder.usedToEncodePassword=true" in application.properties
	 */
	@Autowired
	public void configureGlobal_(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password(encode("user")).roles("USER")
				.and()
//				  NOTE ilker BCryptPasswordEncoder always generates a random salt, so if you invoke it 2 times with SAME input, it will return 2 DIFFERENT output
				.withUser("user2").password(encode("user")).roles("USER")
				.and()
				.withUser("developer").password(encode("developer")).roles("DEVELOPER", "ADMIN")
				.and()
				.withUser("admin").password(encode("admin")).roles("ADMIN")
				.and()
				.withUser("patient").password(encode("patient")).roles("PATIENT")
				.and()
				.withUser("provider").password(encode("provider")).roles("PROVIDER")
				.and()
				.withUser("employer").password(encode("employer")).roles("EMPLOYER")
				.and()
				.withUser("partner").password(encode("partner")).roles("PARTNER")
				.and()
				.withUser("internal").password(encode("internal")).roles("INTERNAL")	// NOTE ilker "role" is typically more coarse granular 
				.and()
				.withUser("provider_owner").password(encode("provider_owner")).roles("PROVIDER_OWNER").authorities("PROVIDER_CAN_ECHO") // NOTE ilker "authority" is fine granular "permissions"("privileges")
				.and()
				.withUser("provider_admin").password(encode("provider_admin")).roles("PROVIDER_ADMIN").authorities("PROVIDER_CAN_ECHO", "PROVIDER_CAN_VIEW_APPOINMENTS")
				.and()
				.withUser("provider_secretary").password(encode("provider_secretary")).roles("PROVIDER_SECRETARY")
				.and()
				.withUser("provider_doctor").password(encode("provider_doctor")).roles("PROVIDER_DOCTOR")
				.and()
				.withUser("provider_nurse").password(encode("provider_nurse")).roles("PROVIDER_NURSE")
				.and()
				.withUser("employer_owner").password(encode("employer_owner")).roles("EMPLOYER_OWNER")
				.and()
				.withUser("employer_admin").password(encode("employer_admin")).roles("EMPLOYER_ADMIN")
				.and()
				.withUser("partner_owner").password(encode("partner_owner")).roles("PARTNER_OWNER")
				.and()
				.withUser("partner_admin").password(encode("partner_admin")).roles("PARTNER_ADMIN")
				.and()
				.withUser("partner_sales").password(encode("partner_sales")).roles("PARTNER_SALES")
				.and()
				.withUser("partner_marketing").password(encode("partner_marketing")).roles("PARTNER_MARKETING")
				.and()
				.withUser("partner_support").password(encode("partner_support")).roles("PARTNER_SUPPORT")
				.and()
				.withUser("internal_owner").password(encode("internal_owner")).roles("INTERNAL_OWNER")
				.and()
				.withUser("internal_admin").password(encode("internal_admin")).roles("INTERNAL_ADMIN")
				.and()
				.withUser("internal_sales").password(encode("internal_sales")).roles("INTERNAL_SALES")
				.and()
				.withUser("internal_marketing").password(encode("internal_marketing")).roles("INTERNAL_MARKETING")
				.and()
				.withUser("internal_support").password(encode("internal_support")).roles("INTERNAL_SUPPORT")
				;
	}
}