package com.cmy.zujuan.service;

import com.cmy.zujuan.pojo.ChatMessage;
import com.cmy.zujuan.pojo.MessageResponse;

public interface ChatService {
    MessageResponse sendMessage(ChatMessage chatMessage);
}
