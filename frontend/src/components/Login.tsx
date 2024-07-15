import styled from "styled-components";
import Input from "@karden/utils/Input/Input";
import Button from "@karden/utils/button/Button";

const Section = styled.div`
  display: flex;
  justify-content: center;
  padding-top: 40px;
`;

const LoginContainer = styled.div`
  width: 400px;
  height: 100%;
`;

const LoginHeader = styled.div`
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  padding-bottom: 8px;
`;

const InputContainer = styled.div`
  height: 84px;
`;

const InputLabel = styled.div`
  height: 32px;
  font-size: 16px;
  font-weight: bold;
`;

const LoginButtonContainer = styled.div`
  padding-top: 15px;
`;

const Login = () => {
  return (
    <Section>
      <LoginContainer>
        <form>
          <LoginHeader>Login</LoginHeader>
          <InputContainer>
            <InputLabel>Email</InputLabel>
            <Input type="email" name="username" placeholder="email" />
          </InputContainer>
          <InputContainer>
            <InputLabel>password</InputLabel>
            <Input type="password" name="password " placeholder="password" />
          </InputContainer>
          <LoginButtonContainer>
            <Button type="submit" isPrimary={true} text="Log in" width="100%" />
          </LoginButtonContainer>
        </form>
      </LoginContainer>
    </Section>
  );
};

export default Login;
