package com.plankdev.jwtsecurity.restcommons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class ResponseBuilder {

    public static <T extends Model> ResponseEntity<?> buildNewResponseEntity(Optional<T> objectOpt) {
        return buildEditedResponseEntity(objectOpt, true);
    }

    public static <T extends Model> ResponseEntity<?> buildEditedResponseEntity(Optional<T> objectOpt) {
        return buildEditedResponseEntity(objectOpt, false);
    }

    private static <T extends Model> ResponseEntity<?> buildEditedResponseEntity(Optional<T> objectOpt, boolean isNew) {
        ResponseEntity<?> response;

        if (objectOpt.isPresent()) {
            T model = objectOpt.get();
            ServletUriComponentsBuilder servletUriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

            //adds the id of the current model to the URL
            if (isNew) {
                servletUriComponentsBuilder.path("/{id}");
            }

            URI location = servletUriComponentsBuilder.buildAndExpand(model.getId()).toUri();
            response = ResponseEntity.created(location).body(model);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }
}
