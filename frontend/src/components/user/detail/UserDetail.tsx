import { config, getRestApiServerUrl } from "@config/config";
import { AUTHENTICATED_ROLES, routes } from "@config/routes";
import { ROLE_USER, UserRoleType } from "@config/userRole";
import { PrimaryButton, SecondaryButton } from "@karden/utils/button";
import { InputType1 } from "@karden/utils/Input";
import useFetch from "@src/hooks/useFetch";
import { createPostRequestObject, jsonOrgConfig } from "@src/jsonApiOrg/JsonApiOrg";
import { ChangeEvent, useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import styled from "styled-components";
import Swal from "sweetalert2";
import { ErrorWithCode } from "@src/error/ErrorWithCode";
import { ErrorCode } from "@src/error/ErrorCode";
import { User } from "@reducers/userActions";

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
  border: 1px solid ${({ theme }) => theme.color.primary};
  height: 36px;
  width: 100%;
  font-size: ${({ theme }) => theme.font.size.medium};
  color: ${({ theme }) => theme.color.primary};
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

export type UserOfRequest = {
  pw: string;
  role: string;
  name: string;
};

type UserDetailProps = {
  user: User;
};
const UserDetail = ({ user }: UserDetailProps) => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const confirmPasswordRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);

  const navigate = useNavigate();
  const fetchData = useFetch();

  const [userDetailId, setUserDetailId] = useState<string>("");
  const [disableUserIdInput, setDisableUserIdInput] = useState<boolean>(true);
  const [roleChange, isRoleChange] = useState<boolean>(true);
  const [userDetailRole, setUserDetailRole] = useState<UserRoleType>(user.role as UserRoleType);
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");

  const [name, setName] = useState<string>("");

  const location = useLocation();

  useEffect(() => {
    setName(user.name);
    setUserDetailRole(user.role as UserRoleType);
    setUserDetailId(user.id);

    if (location.pathname === routes.USER.MY_INFOMATION.INDEX) {
      setDisableUserIdInput(true);
      isRoleChange(true);
    } else if (location.pathname === routes.USER.ADD.INDEX) {
      setDisableUserIdInput(false);
      isRoleChange(false);
    } else if (location.pathname.includes(routes.USER.MODIFY.INDEX.split("/*")[0])) {
      isRoleChange(false);
    }
  }, [user, location.pathname]);

  const isValidCheck = async () => {
    if (password.length === 0) {
      await Swal.fire({
        title: "비밀번호 오류",
        text: "비밀번호를 입력하세요",
        icon: "warning",
      });
      return false;
    } else if (password !== confirmPassword) {
      await Swal.fire({
        title: "비밀번호 오류",
        text: "확인 비밀번호가 다릅니다.",
        icon: "warning",
      });
      return false;
    } else if (name == "") {
      await Swal.fire({
        title: "이름 오류",
        text: "이름을 입력하세요.",
        icon: "warning",
      });
      return false;
    }

    return true;
  };
  const createUser = async () => {
    if ((await isValidCheck()) == false) return;

    let request = createPostRequestObject<UserOfRequest>();
    request = {
      data: {
        id: userDetailId,
        type: "users",
        attributes: { name: name, pw: password, role: userDetailRole },
      },
    };
    try {
      const response = await fetchData(getRestApiServerUrl(`/users`), {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(request),
        headers: { "Content-Type": jsonOrgConfig.CONTENT_TYPE, Accept: jsonOrgConfig.ACCEPT },
      });
      if (response.status === 201) {
        //ACCEPT
        await Swal.fire({
          title: "유저 추가",
          text: "성공",
          icon: "success",
        });
      }
    } catch (e: unknown) {
      if (e instanceof ErrorWithCode) {
        if (e.code === ErrorCode.BAD_REQUEST) {
          await Swal.fire({
            title: "유저 추가 실패",
            text: "중복된 유저 추가",
            icon: "error",
          });
        }
      } else {
        console.error("Unexpected error:", e);
      }
    }
  };

  const modifyUser = async () => {
    if ((await isValidCheck()) == false) return;

    let request = createPostRequestObject<UserOfRequest>();
    request = {
      data: {
        id: userDetailId,
        type: "users",
        attributes: { name: name, pw: password, role: userDetailRole },
      },
    };

    try {
      const response = await fetchData(getRestApiServerUrl(`/users/${user.id}`), {
        method: "PATCH",
        credentials: "include",
        body: JSON.stringify(request),
        headers: { "Content-Type": jsonOrgConfig.CONTENT_TYPE, Accept: jsonOrgConfig.ACCEPT },
      });
      if (response.status === 200) {
        //OK
        await Swal.fire({
          title: "유저 수정",
          text: "성공",
          icon: "success",
        });
      }
    } catch (e: unknown) {
      if (e instanceof ErrorWithCode) {
        if (e.code === ErrorCode.BAD_REQUEST) {
          await Swal.fire({
            title: "유저 수정 실패",
            text: "잘못된 요청. 관리자한테 문의하세요.",
            icon: "error",
          });
        }
      }
    }
  };

  return (
    <StyledUserContainer>
      <BorderHeaderContainer></BorderHeaderContainer>
      <BodyInputContainer>
        <InputItem>
          <div>User id</div>
          <InputType1
            type="text"
            placeholder="id"
            ref={emailRef}
            disable={disableUserIdInput}
            text={userDetailId}
            onChange={(event: ChangeEvent<HTMLInputElement>) => {
              event.preventDefault();
              setUserDetailId(event.target.value);
            }}
          />
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
          <Select
            disabled={roleChange}
            value={userDetailRole}
            onChange={(event: ChangeEvent<HTMLSelectElement>) => {
              event.preventDefault();
              setUserDetailRole(event.currentTarget.value as UserRoleType);
            }}>
            {AUTHENTICATED_ROLES.map((role) => (
              <option value={role}>{role}</option>
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
            else if (location.pathname.includes(routes.USER.MODIFY.INDEX.split("/*")[0])) modifyUser();
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
