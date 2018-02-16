package com.plankdev.jwtsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloRestController {

    @GetMapping("/public")
    public String helloPublic() {
        return "Hello from public rest resource";
    }

    @TokenRequired
    @GetMapping("/private")
    public String helloPrivate() {
        return "Hello from private rest resource";
    }
}
