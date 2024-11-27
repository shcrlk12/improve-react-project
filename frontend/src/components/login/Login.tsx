/* eslint-disable @typescript-eslint/no-explicit-any */
import styled from "styled-components";
import InputType1 from "@karden/utils/Input/InputType1";
import { PrimaryButton } from "@karden/utils/button";
import useFetchData from "@src/hooks/useFetchData";
import { config } from "@config/config";
import { FormEvent } from "react";
import { loginSuccess } from "@reducers/userActions";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";
import { UserRoleType } from "@config/userRole";
import { JsonApi } from "@src/jsonApiOrg/JsonApiOrg";
import Swal from "sweetalert2";

/**
 * Types
 */
export type ResponseOfLogin = {
  message: string;
  role: UserRoleType;
  name: string;
};

/**
 * Styled
 */
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

/**
 * Component for user login using email and password.
 *
 * This component renders a form with input fields for email and password,
 * allowing users to log in to the application.
 *
 * @author Karden
 * @created 2024-07-17
 */
const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const fetchData = useFetchData();

  const loginSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const formData = new FormData(event.currentTarget);

      const data = await fetchData<JsonApi<ResponseOfLogin>>(
        `${config.apiServer.protocol}://${config.apiServer.ip}:${config.apiServer.port}/api/login`,
        {
          mode: "cors",
          method: "POST",
          credentials: "include",
          body: formData,
        },
        () =>
          Swal.fire({
            title: "로그인 실패",
            text: "아이디/패스워드를 확인하세요.",
            icon: "error",
          }),
      );

      const {
        id,
        attributes: { role, name },
      } = data;

      dispatch(loginSuccess({ id, name, role }));
      navigate("/report");
    } catch (e) {}
  };
  return (
    <Section>
      <LoginContainer>
        <form onSubmit={loginSubmit}>
          <LoginHeader>Login</LoginHeader>
          <InputContainer>
            <InputLabel>Id</InputLabel>
            <InputType1 type="text" name="username" placeholder="id" />
          </InputContainer>
          <InputContainer>
            <InputLabel>password</InputLabel>
            <InputType1 type="password" name="password " placeholder="password" />
          </InputContainer>
          <LoginButtonContainer>
            <PrimaryButton type="submit" text="Log in" width="100%" onClick={() => {}} />
          </LoginButtonContainer>
        </form>
      </LoginContainer>
    </Section>
  );
};

export default Login;
