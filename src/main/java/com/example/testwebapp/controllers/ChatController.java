package com.example.testwebapp.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testwebapp.websocket.Message;

@RestController
@RequestMapping("/chat")
public class ChatController {

    SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/new")
    @SendTo("/topic/chat")
    public Message sendMessage(Message message) throws Exception {
        return message;
    }

    @MessageMapping("/secured/room")
    public void sendSpeificMessage(Message message) throws Exception {
        System.out
                .println("Server received a new message from " + message.getFrom() + ". The message will send to user: "
                        + message.getTo() + " with message content: " + message.getContent());
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/queue/specific-user", message);
    }

}
