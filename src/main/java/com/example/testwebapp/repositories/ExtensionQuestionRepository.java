package com.example.testwebapp.repositories;

import com.example.testwebapp.entities.ChatSession;
import com.example.testwebapp.entities.ExtensionQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ExtensionQuestionRepository extends MongoRepository<ExtensionQuestion, Integer> {

    ExtensionQuestion findByQuestion(String question);
}
