package com.project.project_oop.controller;

import com.project.project_oop.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(

    ) {
        return;
    }

    @GetMapping("/get")
    public void getMessage(

    ) {
        return;
    }

}
