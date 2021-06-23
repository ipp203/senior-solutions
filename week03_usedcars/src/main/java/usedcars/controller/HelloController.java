package usedcars.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import usedcars.service.HelloService;

@RestController
public class HelloController {

    HelloService service;

    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String greeting() {
        return service.getGreeting();
    }

}
