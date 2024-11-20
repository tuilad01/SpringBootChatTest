package com.example.testwebapp.websocket;

public class Message {

    String from;
    String to;
    String content;
    String SentAt;

    public Message() {
    }

    public Message(String from, String to, String content, String sentAt) {
        this.from = from;
        this.to = to;
        this.content = content;
        SentAt = sentAt;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentAt() {
        return SentAt;
    }

    public void setSentAt(String sentAt) {
        SentAt = sentAt;
    }

}
