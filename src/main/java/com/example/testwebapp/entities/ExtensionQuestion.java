package com.example.testwebapp.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "ExtensionQuestion")
public class ExtensionQuestion {
    public ExtensionQuestion() {
    }

    public ExtensionQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    private String question;
    private String answer;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
