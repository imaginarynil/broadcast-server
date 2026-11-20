"use client";

import { Client, IMessage } from "@stomp/stompjs";
import { Button, Input, Space, Typography } from "antd";
import { useEffect, useState } from "react";

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

interface Message {
  username: string;
  content: string;
  messageType: string;
  createdAt: string;
}

export default function Home() {
  const [username, setUsername] = useState("");
  const [messages, setMessages] = useState<Message[]>([]);
  const [active, setActive] = useState(false);

  useEffect(() => {
    stompClient.onConnect = () => {
      stompClient.subscribe("/topic/public", (message: IMessage) => {
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
      <Space orientation="vertical">
        {messages.map((message, i) => (
          <Text key={i}>{message.content}</Text>
        ))}
      </Space>
    </Space>
  );
}
