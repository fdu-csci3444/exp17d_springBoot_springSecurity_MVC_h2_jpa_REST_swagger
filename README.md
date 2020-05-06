# exp17d_springBoot_springSecurity_MVC_h2_jpa_REST_swagger
Example SpringBoot project showing;
- spring security (see exp17d.config.security.SecurityConfig)
- using Https SSL via self signed certificate
- spring MVC using thymeleaf as templating engine (see exp17d.mvc.WebController and 
  src/main/resources/templates/*.html)
- using "thymeleaf security dialect" (see pom.xml and src/main/resources/templates.home.html)
- using Bootstrap for CSS in MVC views(html files) (see src/main/resources/templates/home.html)
- using basic CSS (see src/main/resources/templates/home_noBootstrap_usingBasicCsssOnly.html)
- h2 as RDBMS
- JPA (see exp17d.jpa.model.Customer, exp17d.jpa.repo.CustomerRepository)
- lombok (see exp17d.jpa.model.Customer)
- REST (see exp17d.rest.*)
- swagger (see exp17d.config.SwaggerConfig)

## Spring Security
- NOTE ilker, this application's MVC and REST (and it's h2 console, since it gets deployed along 
with app) is protected via "Spring Security"
- Spring Security can be used in 4 or more ways;
  - using "Spring Security Auto Configuration"("default Spring Security Config"), where you 
    do not configure anything but include "spring-boot-starter-security" dependency 
    in pom.xml
  - using "Http Basic"
  - using "Http form validation"
  - using JWT
  - using OAuth2

## generating "Self Signed" "SSL Certificate"
- open a dos command as administrator
- use keytool that is there in jdk bin
- check that keytool is found from the path via "where keytool"
- generate "self signed" SSL certificate, exp17d.p12, using keytool
```dos
cd dirContainingProjects
cd exp17d_springBoot_springSecurity_MVC_h2_jpa_REST_swagger
cd src\main\resources\certificates4https
where keytool
keytool -genkey -alias exp17d -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore exp17d.p12 -validity 3650
  Enter keystore password: password
  Re-enter new password:   password
  What is your first and last name?                  ilker kiris
  What is the name of your organizational unit?      mh
  What is the name of your organization?             mh
  What is the name of your City or Locality?         Holmdel
  What is the name of your State or Province?        NJ
  What is the two-letter country code for this unit? US
  Is CN=ilker kiris, OU=mh, O=mh, L=Holmdel, ST=NJ, C=US correct?
  [no]: yes
```


## to access h2 console
- http://localhost:8889/h2_console
- since end points are protected with Spring Security, and configured h2 related urls 
  to be protected and allowed for user's with "ADMIN" role, login as admin/admin in 
  the login popup
- use below values in h2 console
Driver Class: org.h2.Driver                <br>
JDBC URL:     jdbc:h2:file:~/h2/testdb     <br>
User Name:    sa                           <br>
Password:             (leave it empty)     <br>
- click "Test Connection" and see "Test successfull" green bar at the bottom
- click "Connect" to use h2 console

## to access MVC UI home.html using HTTPS
https://localhost:8890/
<br> or <br>
https://localhost:8890/home
<br>
- Upon your 1st access with a browser, it will complain "Your connection is not private"(because using self signed certificate 
which is not known by browser) , click "Advanced" button in Chrome, or in Opera click "Explain more". Then click "Proceed to localhost(unsafe)" 
- the certificate will be cached and you won't need to do above afterwards on that browser 

## to access MVC UI home.html using HTTP
- NOTE if you try to reach via http, it will auto redirect to https of above (using 
  exp17d.config.security.HttpsSSLConfig4tomcat)
http://localhost:8889/
<br> or <br>
http://localhost:8889/home

## REST endpoints
- NOTE below requires login of patient/patient
  <br>
https://localhost:8890/rest/v1/patient/echoMessage
- NOTE below requires login of provider_owner/provider_owner  or provider_admin/provider_admin 
  <br>
https://localhost:8890/rest/v1/provider/echoMessage
- NOTE below requires authentication, does not require any specific role, but just user 
  logged in. If you login as "developer", which has 2 roles "DEVELOPER" and "ADMIN" 
  then you can execute below save and login to h2 console and see new 5 rows added to 
  h2 DB 
  <br>
https://localhost:8890/rest/v1/customer/save

