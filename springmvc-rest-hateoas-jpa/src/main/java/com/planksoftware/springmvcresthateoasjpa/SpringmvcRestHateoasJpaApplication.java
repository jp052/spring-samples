package com.planksoftware.springmvcresthateoasjpa;

import com.planksoftware.springmvcresthateoasjpa.bookmarks.Account;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.AccountRepository;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.Bookmark;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.BookmarkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

// curl -X POST -vu android-bookmarks:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password&username=jlong&grant_type=password&scope=write&client_secret=123456&client_id=android-bookmarks"
// curl -v POST http://127.0.0.1:8080/bookmarks -H "Authorization: Bearer <oauth_token>""

/*
Postman request oAuth2 Token:
URL: http://localhost:8080/oauth/token
Type: POST
Header: content-type:application/json
Authentication: Basich Auth
Username: android-bookmarks Password:123456
Raw Body: password=password&username=jlong&grant_type=password&scope=write&client_secret=123456&client_id=android-bookmarks

Read bookmarks with Authentication:
URL: http://127.0.0.1:8080/bookmarks
Auth: Bearer: token from oaut2 request
 */

@SpringBootApplication
public class SpringmvcRestHateoasJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmvcRestHateoasJpaApplication.class, args);
    }

    /**
     * @Value("${tagit.origin:http://localhost:9000}") needs to be replaced for the url where e.g.
     * a javascript client is accessing from the api from. the url/urls should come from the database.
     * @param origin
     * @return
     */
    @Bean
    FilterRegistrationBean corsFilter(@Value("${tagit.origin:http://localhost:9000}") String origin) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
                new Filter() {
                    public void doFilter(ServletRequest req, ServletResponse res,
                                         FilterChain chain) throws IOException, ServletException {
                        HttpServletRequest request = (HttpServletRequest) req;
                        HttpServletResponse response = (HttpServletResponse) res;
                        String method = request.getMethod();
                        // this origin value could just as easily have come from a database
                        response.setHeader("Access-Control-Allow-Origin", origin);
                        response.setHeader("Access-Control-Allow-Methods",
                                "POST,GET,OPTIONS,DELETE");
                        response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        response.setHeader(
                                "Access-Control-Allow-Headers",
                                "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                        if ("OPTIONS".equals(method)) {
                            response.setStatus(HttpStatus.OK.value());
                        } else {
                            chain.doFilter(req, res);
                        }
                    }

                    public void init(FilterConfig filterConfig) {
                    }

                    public void destroy() {
                    }
                }
        );

        return filterRegistrationBean;
    }

    @Bean
    CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(
                        username -> {
                            Account account = accountRepository.save(new Account(username, "password"));
                            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + username, "Description Bookmark 1"));
                            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + username, "Description Bookmark 2"));

                        }
                );

    }
}
