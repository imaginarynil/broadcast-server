import { Button, Form, Input, Space } from "antd";
import { Dispatch, SetStateAction, SubmitEventHandler } from "react";

interface Props {
  active: boolean;
  handleSignIn: (values: any) => void;
}

type FieldType = {
  username?: string;
};

function SignInForm({ active, handleSignIn }: Props) {
  return (
    <Form
      onFinish={handleSignIn}
      style={{ maxWidth: "300px" }}
      // layout="horizontal"
      disabled={!active}
    >
      <Form.Item<FieldType> name="username" label="Username">
        <Input />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Sign In
        </Button>
      </Form.Item>
    </Form>
  );
}

export default SignInForm;
