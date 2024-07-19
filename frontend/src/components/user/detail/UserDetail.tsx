import { PrimaryButton, SecondaryButton } from "@karden/utils/button";
import { InputType1 } from "@karden/utils/Input";
import { useEffect, useRef } from "react";
import styled from "styled-components";

/**
 * Styles
 */
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
const UserDetail = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const confirmPasswordRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);
  const roleRef = useRef<HTMLSelectElement>(null);

  const title = "제목";
  const isIdDisable = false;

  useEffect(() => {}, []);

  return (
    <>
      <BorderHeaderContainer>
        <div>{title}</div>
      </BorderHeaderContainer>
      <BodyInputContainer>
        <InputItem>
          <div>User id</div>
          <InputType1
            type="email"
            placeholder="email"
            ref={emailRef}
            disable={isIdDisable}
          />
        </InputItem>
        <InputItem>
          <div>Password</div>
          <InputType1
            type="password"
            placeholder="password"
            ref={passwordRef}
          />
        </InputItem>
        <InputItem>
          <div>Confirm Password</div>
          <InputType1
            type="password"
            placeholder="confirm password"
            ref={confirmPasswordRef}
          />
        </InputItem>
        <InputItem>
          <div>Name</div>
          <InputType1 type="text" placeholder="name" ref={nameRef} />
        </InputItem>
        <InputItem>
          <div>Role</div>
          <Select ref={roleRef}>
            {/* <option value={ROLE_USER} selected>
              User
            </option>
            <option value={ROLE_MANAGER}>Manager</option>
            <option value={ROLE_ADMIN}>Admin</option> */}
          </Select>
        </InputItem>
      </BodyInputContainer>
      <ButtonContainer>
        <PrimaryButton
          type="submit"
          text="저장"
          width="auto"
          onClick={() => {}}
        />
        <SecondaryButton text="Cancel" width="auto" onClick={() => {}} />
      </ButtonContainer>
    </>
  );
};

export default UserDetail;
