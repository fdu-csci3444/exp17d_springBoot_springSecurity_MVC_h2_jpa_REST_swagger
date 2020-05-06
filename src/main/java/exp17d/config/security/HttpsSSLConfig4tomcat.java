/**
 * 
 */
package exp17d.config.security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ilker
 *
 */
@Configuration
public class HttpsSSLConfig4tomcat {
    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Traffic
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

        return tomcat;
    }

    // NOTE ilker read below javaDoc
    /**
     * We need to redirect from HTTP to HTTPS. Without SSL, this application used port 8889. With SSL it will use port 8890.
     * So, any request for 8889 needs to be redirected to HTTPS on 8890.
     */
    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8889);
        connector.setSecure(false);
        connector.setRedirectPort(8890);
        return connector;
    }

}
