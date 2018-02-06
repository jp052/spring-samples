package com.planksoftware.springmvcresthateoasjpa;

import com.planksoftware.springmvcresthateoasjpa.bookmarks.Account;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.AccountRepository;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.Bookmark;
import com.planksoftware.springmvcresthateoasjpa.bookmarks.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;

import java.util.Arrays;

@SpringBootApplication
public class SpringmvcRestHateoasJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmvcRestHateoasJpaApplication.class, args);
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
