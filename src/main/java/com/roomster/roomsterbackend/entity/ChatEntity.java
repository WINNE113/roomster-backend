package com.roomster.roomsterbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class ChatEntity extends BaseEntity{
    @Column(name = "chat_content")
    private String chatContent;
    @Column(name = "chat_receiver_id")
    private Long chatReceiverId;
    @Column(name = "chat_sender_id")
    private Long chatSenderId;

    @OneToMany(mappedBy = "chat")
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public Long getChatReceiverId() {
        return chatReceiverId;
    }

    public void setChatReceiverId(Long chatReceiverId) {
        this.chatReceiverId = chatReceiverId;
    }

    public Long getChatSenderId() {
        return chatSenderId;
    }

    public void setChatSenderId(Long chatSenderId) {
        this.chatSenderId = chatSenderId;
    }

    public List<ChatMessageEntity> getChatMessageEntityList() {
        return chatMessageEntityList;
    }

    public void setChatMessageEntityList(List<ChatMessageEntity> chatMessageEntityList) {
        this.chatMessageEntityList = chatMessageEntityList;
    }
}
