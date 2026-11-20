"use client";

import { Client, IMessage } from "@stomp/stompjs";
import { Button, Flex, Input, Space, Typography } from "antd";
import { useEffect, useState } from "react";
import ChatForm from "./ChatForm";
import { ChatMessage } from "./interface";

const { Text } = Typography;

const BROKER_URL = "ws://localhost:8080/chat";

const MESSAGE_TYPE = Object.freeze({
  CONNECTED: "CONNECTED",
  CHATTING: "CHATTING",
  DISCONNECTED: "DISCONNECTED",
});

const stompClient = new Client({
  brokerURL: BROKER_URL,
  onStompError: (frame) => {
    console.error("Broker error: ", frame.headers["message"], frame.body);
  },
  onWebSocketError: (event) => {
    console.error("WebSocket error: ", event);
  },
});

export default function Home() {
  const [username, setUsername] = useState("");
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [active, setActive] = useState(false);

  useEffect(() => {
    stompClient.onConnect = () => {
      stompClient.subscribe("/topic/public", (message: IMessage) => {
        // TODO: why this needs an updater function to work?
        // maybe because this is asynchronous and receives the prev state when the component renders
        setMessages((prevMessages) => [
          ...prevMessages,
          JSON.parse(message.body),
        ]);
      });
      setActive(true);
    };
  }, []);

  const handleConnect = async () => {
    stompClient.activate();
  };

  const handleDisconnect = async () => {
    await stompClient.deactivate();
    setActive(false);
  };

  const handleSendMessage = (message: string) => {
    stompClient.publish({
      destination: "/app/chat.sendMessage",
      body: JSON.stringify({
        username: username,
        content: message,
        type: MESSAGE_TYPE.CHATTING,
      }),
    });
  };

  return (
    <Space orientation="vertical">
      <Space>
        <Button type="primary" onClick={handleConnect} disabled={active}>
          Connect
        </Button>
        <Button type="primary" onClick={handleDisconnect} disabled={!active}>
          Disconnect
        </Button>
      </Space>
      <Space>
        <Input
          style={{ maxWidth: "200px", width: "100%" }}
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Type your username here..."
          disabled={!active}
        />
        <Button
          type="primary"
          onClick={() => {
            stompClient.publish({
              destination: "/app/chat.connectUser",
              body: JSON.stringify({
                username: username,
                type: MESSAGE_TYPE.CONNECTED,
              }),
            });
          }}
          disabled={!active}
        >
          Join
        </Button>
      </Space>
      <ChatForm handleSendMessage={handleSendMessage} />
      {/* Chat Area */}
      <Space orientation="vertical">
        {messages.map((message, i) => {
          if (message.messageType === MESSAGE_TYPE.CHATTING) {
            return (
              <Flex key={i} orientation="vertical">
                <Text>{message.username}</Text>
                <Text>{message.content}</Text>
              </Flex>
            );
          } else {
            return <Text key={i}>{message.content}</Text>;
          }
        })}
      </Space>
    </Space>
  );
}
