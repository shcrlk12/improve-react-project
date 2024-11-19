import { config } from "@config/config";
import { AUTHENTICATED_ROLES, routes } from "@config/routes";
import { ROLE_MANAGER, ROLE_USER, UserRoleType } from "@config/userRole";
import { PrimaryButton, SecondaryButton } from "@karden/utils/button";
import { InputType1 } from "@karden/utils/Input";
import useFetch from "@src/hooks/useFetch";
import { ACCEPT, CONTENT_TYPE, createPostRequestObject, createPostRequestsObject } from "@src/jsonApiOrg/JsonApiOrg";
import { RootState } from "@src/main";
import { isAuthorization } from "@src/utils/user";
import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router";
import styled from "styled-components";
import Swal from "sweetalert2";

/**
 * Styles
 */
const StyledUserContainer = styled.div`
  width: 900px;
  margin: auto;
`;

const HeaderContainer = styled.div`
  height: 72px;

  & > div:first-child {
    font-size: 32px;
    font-weight: bold;
  }
`;

const BorderHeaderContainer = styled.div`
  ${HeaderContainer}
  border-bottom: 1px solid ${({ theme }) => theme.color.secondary};
`;

const BodyInputContainer = styled.div`
  padding-top: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.color.primary};
`;

const InputItem = styled.div`
  display: flex;
  margin-bottom: 20px;
  & div:first-child {
    min-width: 170px;
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    padding-right: 20px;
    font-size: 15px;
    font-weight: bold;
    text-align: right;
  }
`;

const Select = styled.select`
  border-radius: 12px;
  border: 1px solid ${({ theme }) => theme.color.secondary};
  height: 36px;
  width: 100%;
  font-size: ${({ theme }) => theme.font.size.medium};
  color: ${({ theme }) => theme.color.secondary};
  padding: 0 10px;

  &:focus {
    outline: 1px solid ${({ theme }) => theme.color.primary};
  }
`;

const ButtonContainer = styled.div`
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
`;

/**
 * UserDetail Component
 *
 * This component render the user detail
 * and it is able to modify user detail.
 *
 * @author Karden
 * @created 2024-07-19
 */

type UserOfRequest = {
  pw: string;
  role: string;
  name: string;
};

const UserDetail = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const confirmPasswordRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);
  const roleRef = useRef<HTMLSelectElement>(null);

  const navigate = useNavigate();
  const fetchData = useFetch();
  const user = useSelector((store: RootState) => store.userReducer.user);

  const [roleChange, isRoleChange] = useState<boolean>(false);

  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");

  const [name, setName] = useState<string>("");

  const location = useLocation();

  useEffect(() => {
    setName(user.name);

    if (isAuthorization(user.role as UserRoleType, ROLE_MANAGER as UserRoleType)) {
      isRoleChange(true);
    }
  }, [user]);

  const createUser = async () => {
    if (password.length === 0) {
      await Swal.fire({
        title: "비밀번호 오류",
        text: "비밀번호를 입력하세요",
        icon: "warning",
      });
      return;
    }
    if (password !== confirmPassword) {
      await Swal.fire({
        title: "비밀번호 오류",
        text: "확인 비밀번호가 다릅니다.",
        icon: "warning",
      });
      return;
    }
    if (name.length <= 0) {
      await Swal.fire({
        title: "이름 오류",
        text: "이름을 입력하세요.",
        icon: "warning",
      });
      return;
    }

    let request = createPostRequestObject<UserOfRequest>();

    request.data = {
      id: user.id,
      type: "users",
      attributes: { name: name, pw: password, role: user.role },
    };
    const response = await fetchData(`http://${config.apiServer.ip}:${config.apiServer.port}/api/users`, {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(request),
      headers: { "Content-Type": CONTENT_TYPE, Accept: ACCEPT },
    });

    if (response.status === 201) {
      //ACCEPT
      await Swal.fire({
        title: "유저 추가",
        text: "성공",
        icon: "success",
      });
    }
    return response;
  };

  const modifyUser = async () => {
    let request = createPostRequestObject<UserOfRequest>();

    const response = await fetchData(`http://${config.apiServer.ip}:${config.apiServer.port}/api/users/${user.id}`, {
      method: "PATCH",
      credentials: "include",
      body: JSON.stringify(request),
      headers: { "Content-Type": CONTENT_TYPE, Accept: ACCEPT },
    });
  };

  return (
    <StyledUserContainer>
      <BorderHeaderContainer></BorderHeaderContainer>
      <BodyInputContainer>
        <InputItem>
          <div>User id</div>
          <InputType1 type="email" placeholder="email" ref={emailRef} disable={true} text={user.id} />
        </InputItem>
        <InputItem>
          <div>Password</div>
          <InputType1
            type="password"
            placeholder="password"
            ref={passwordRef}
            text={password}
            onChange={(event: ChangeEvent<HTMLInputElement>) => {
              event.preventDefault();
              setPassword(event.target.value);
            }}
          />
        </InputItem>
        <InputItem>
          <div>Confirm Password</div>
          <InputType1
            type="password"
            placeholder="confirm password"
            ref={confirmPasswordRef}
            text={confirmPassword}
            onChange={(event: ChangeEvent<HTMLInputElement>) => {
              event.preventDefault();
              setConfirmPassword(event.target.value);
            }}
          />
        </InputItem>
        <InputItem>
          <div>Name</div>
          <InputType1
            type="text"
            placeholder="name"
            ref={nameRef}
            text={name}
            onChange={(event: ChangeEvent<HTMLInputElement>) => {
              event.preventDefault();
              setName(event.target.value);
            }}
          />
        </InputItem>
        <InputItem>
          <div>Role</div>
          <Select disabled={roleChange} ref={roleRef}>
            {AUTHENTICATED_ROLES.map((role) => (
              <option value={role} selected={user.role === role ? true : false}>
                {role}
              </option>
            ))}
          </Select>
        </InputItem>
      </BodyInputContainer>
      <ButtonContainer>
        <PrimaryButton
          type="submit"
          text="저장"
          width="auto"
          onClick={() => {
            if (location.pathname === routes.USER.MY_INFOMATION.INDEX) modifyUser();
            else if (location.pathname === routes.USER.ADD.INDEX) createUser();
          }}
        />
        <SecondaryButton
          text="Cancel"
          width="auto"
          onClick={() => {
            navigate(routes.REPORT.INDEX);
          }}
        />
      </ButtonContainer>
    </StyledUserContainer>
  );
};

export default UserDetail;
