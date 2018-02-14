package com.planksoftware.springmvcresthateoasjpa.bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO:
 * * Compare if up to date with Spring Boot 2.0 and Spring 5
 * * Replace RequestMapping with @GetMapping etc. if possible
 * * Find out difference between @RestController and @Controller
 * * Check if @Autowired is needed
 */
@RestController
@RequestMapping("/bookmarks")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkResource> readBookmarks(Principal principal) {
        this.validateUser(principal);

        List<BookmarkResource> bookmarkResourceList = bookmarkRepository.findByAccountUsername(principal.getName()).stream().map(BookmarkResource::new)
                .collect(Collectors.toList());

        return new Resources<BookmarkResource>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addBookmark(Principal principal, @RequestBody Bookmark inputBookmark) {
        this.validateUser(principal);

        return this.accountRepository
                .findByUsername(principal.getName())
                .map(account -> {
                    Bookmark resultBookmark = bookmarkRepository.save(new Bookmark(account,
                            inputBookmark.getUri(), inputBookmark.getDescription()));

                    Link forOneBookmark = new BookmarkResource(resultBookmark).getLink("self");

                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    BookmarkResource readBookmark(Principal principal, @PathVariable Long bookmarkId) {
        this.validateUser(principal);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }
}
