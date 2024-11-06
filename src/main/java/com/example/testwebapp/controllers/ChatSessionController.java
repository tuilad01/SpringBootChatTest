package com.example.testwebapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testwebapp.entities.ChatSession;
import com.example.testwebapp.repositories.ChatSessionRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/chat-session")
@CrossOrigin
public class ChatSessionController {

    final Logger logger = LoggerFactory.getLogger(ChatSessionController.class);
    private final ChatSessionRepository chatSessionRepository;

    public ChatSessionController(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<List<ChatSession>> getAllTest(@RequestParam("status") String status,
            @RequestParam("session_type") String sessionType) {
        // List<ChatSession> list = chatSessionRepository.findAll();
        // System.err.println(status + " " + sessionType);
        List<ChatSession> list = chatSessionRepository.findAllByStatusAndSessionType(status, sessionType);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/")
    public ResponseEntity<List<ChatSession>> getAll() {
        logger.debug("My logging");
        List<ChatSession> list = chatSessionRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/")
    public ResponseEntity<ChatSession> create(@RequestBody ChatSession chatSessionModel) {
        ChatSession chatSession = chatSessionRepository.save(chatSessionModel);
        return ResponseEntity.ok(chatSession);
    }
    // TODO: delete and update and make a complex query. Logs

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Boolean> delete(@PathVariable String sessionId) {
        chatSessionRepository.deleteBySessionId(sessionId);

        return ResponseEntity.ok(true);
    }
}
