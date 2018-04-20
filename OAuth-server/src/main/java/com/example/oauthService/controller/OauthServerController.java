package com.example.oauthService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class OauthServerController {
    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
