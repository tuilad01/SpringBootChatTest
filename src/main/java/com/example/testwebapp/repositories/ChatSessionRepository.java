package com.example.testwebapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.testwebapp.entities.ChatSession;;

public interface ChatSessionRepository extends MongoRepository<ChatSession, Integer> {

    List<ChatSession> findAllByStatusAndSessionType(String status, String sessionType);

    void deleteBySessionId(String sessionId);
}
