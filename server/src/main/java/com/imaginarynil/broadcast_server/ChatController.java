package com.imaginarynil.broadcast_server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public UserJoinResponseMessage publishUserJoin(UserJoinRequestMessage msg) throws Exception {
        return new UserJoinResponseMessage(HtmlUtils.htmlEscape(msg.getName()) + " joined the chat");
    }
}
