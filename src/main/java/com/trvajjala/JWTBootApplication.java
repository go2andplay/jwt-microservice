package com.trvajjala;

import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author ThirupathiReddy V
 *
 */
@SpringBootApplication
public class JWTBootApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Value("${jwt.secret}")
    private String secret;

    /** Reference to logger */
    private static final Logger LOG = LoggerFactory.getLogger(JWTBootApplication.class);

    @Override
    public WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        return super.createRootApplicationContext(servletContext);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JWTBootApplication.class);
    }

    public static void main(String[] args) {

        System.setProperty("spring.profiles.active", "jwt");

        LOG.info("Running Spring boot application with profile :  {}", System.getProperty("spring.profiles.active"));

        final SpringApplication application = new SpringApplication(JWTBootApplication.class);
        final Properties properties = new Properties();
        // properties.put("server.servletPath", "/api/*");// dispatch-servlet path can be set here
        application.setBannerMode(Mode.CONSOLE);
        application.setDefaultProperties(properties);
        application.run();

    }

    @Override
    public void run(String... arguments) throws Exception {

        LOG.info("Executing initializing steps with arguements {} ", Arrays.toString(arguments));

        final Claims claims = Jwts.claims().setSubject("admin");
        claims.put("userId", "1L");
        claims.put("role", "ROLE_ADMIN");

        LOG.info("Generating JWT Token with secret {} ", secret);

        final String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Bearer {} ", token);
        LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
