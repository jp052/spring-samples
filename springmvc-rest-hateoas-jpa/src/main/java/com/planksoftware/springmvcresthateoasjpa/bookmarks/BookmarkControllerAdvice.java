package com.planksoftware.springmvcresthateoasjpa.bookmarks;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Transforms normal Exceptions in a nice Rest hateoas error message.
 */
@ControllerAdvice
public class BookmarkControllerAdvice {

    /**
     * Translates UserNotFroundException into HTTP 404 (NOT FOUND) with mediatype
     * application/vnd.error (vnd = vendor)
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
