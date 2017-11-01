package com.demo.spring_boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OneController {

    @RequestMapping("/index")
    public String index() {
        return "hello spring boot";
    }

}
