package kr.co.kwt.kms.messageadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageDashBoardController {

    @GetMapping("/messages/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/messages/{id}")
    public String messageDetail() {
        return "messageDetail";
    }
}
