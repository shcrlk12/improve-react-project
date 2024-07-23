package com.unison.monitoring.api.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")
public class MemberController {

    @PostMapping("/sign-up")
    public void signUpMember(){

    }
}
