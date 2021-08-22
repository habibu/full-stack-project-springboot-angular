package com.habibu.abdullahi.shoppinglistsystem.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAuthenticate {
    
    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
    @RequestMapping("/item")
    public Principal item(Principal item){
        return item;
    }
}
