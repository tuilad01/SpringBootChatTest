package com.example.testwebapp.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "ChatSession")
public class ChatSession {

    public ChatSession() {
    }

    public ChatSession(String sessionId, List<String> users) {
        this.sessionId = sessionId;
        this.users = users;
    }

    @Field(name = "session_id")
    private String sessionId;
    private List<String> users;
    private String status;
    @Field(name = "session_type")
    private String sessionType;

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
