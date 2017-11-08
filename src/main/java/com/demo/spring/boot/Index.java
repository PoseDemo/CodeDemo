package com.demo.spring.boot;

import com.demo.dao.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    @RequestMapping("/")
    public User index() {
        User user = new User();
        user.setName("lijia");
        user.setAge(27);
        user.setAddress("北京");
        return user;
    }

}
