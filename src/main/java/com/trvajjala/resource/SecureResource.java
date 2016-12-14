package com.trvajjala.resource;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureResource {

    @RequestMapping(value = { "/user", "/me" }, method = RequestMethod.GET)
    public ResponseEntity<?> user(Principal principal) {
        return ResponseEntity.ok(principal);
    }

}
