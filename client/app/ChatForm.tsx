import { Button, Input, Space } from "antd";
import { useState } from "react";

interface Props {
  handleSendMessage: (message: string) => void;
}

function ChatForm({ handleSendMessage }: Props) {
  const [message, setMessage] = useState("");

  return (
    <Space>
      <Input
        style={{ maxWidth: "200px", width: "100%" }}
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        placeholder="Type your message here..."
      />
      <Button
        type="primary"
        onClick={() => {
          handleSendMessage(message);
        }}
      >
        Send
      </Button>
    </Space>
  );
}

export default ChatForm;
