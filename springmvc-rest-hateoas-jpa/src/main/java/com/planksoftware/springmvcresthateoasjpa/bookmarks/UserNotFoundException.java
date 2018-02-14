package com.planksoftware.springmvcresthateoasjpa.bookmarks;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("could not find user with id: " + userId);
    }
}
