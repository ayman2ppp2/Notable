package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MyApplication {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String home() {
        return "hello can you hear me";
    }

    public static void main(String[] args) {
       
        
        SpringApplication.run(MyApplication.class, args);
    }
}
