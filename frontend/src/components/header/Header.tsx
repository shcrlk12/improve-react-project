import { GlobalNavigationBarType } from "@src/configs/navigationBarConfig";
import { UserRoleType } from "@src/configs/userRole";
import logo from "@images/logo/UnisonLogo140px.png";

import styled from "styled-components";
import { isAuthenticated } from "@src/utils/user";
import GlobalNavigationBar from "./navigation/GlobalNavigationBar";
import { PrimaryButton } from "@karden/utils/button";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

/**
 * Style
 */
const StyledHeader = styled.div`
  height: 55px;
  box-sizing: border-box;
  border-bottom: solid 1px #e5e8eb;
  box-shadow: 1px 1px 1px #c4d8f0;
  padding: 0 20px;
  margin-bottom: 2px;
`;

const HeaderInner = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
`;

const LeftHeaderContainer = styled.div`
  display: flex;
`;

const Logo = styled.img`
  margin-right: 15px;
  width: 105px;
  height: 40.4px;
  cursor: pointer;
`;

const PageTitle = styled.div`
  font-size: ${({ theme }) => theme.font.size.xxLarge};
  font-weight: bold;
  display: flex;
  align-items: center;
`;

const RightHeaderContainer = styled.div`
  width: 500px;
  margin-right: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

/**
 * Type
 */
type HeaderProps = {
  title: string;
  globalNavigationBar: GlobalNavigationBarType[];
  userRole: UserRoleType;
  logoOnClick: React.MouseEventHandler<HTMLImageElement>;
  logoutOnClick: React.MouseEventHandler<HTMLButtonElement>;
};

/**
 * Header component for rendering the web header.
 *
 * @author Karden
 * @created 2024-07-17
 */
const Header = ({ title, globalNavigationBar, userRole, logoOnClick, logoutOnClick }: HeaderProps) => {
  const location = useLocation();

  const isLoginPage = location.pathname === "/login";
  const isUserAuthenticated = isAuthenticated(userRole);

  return (
    <StyledHeader>
      <HeaderInner>
        <LeftHeaderContainer>
          <Logo src={logo} alt="unison logo" onClick={logoOnClick} />
          <PageTitle>{title}</PageTitle>
        </LeftHeaderContainer>
        {isUserAuthenticated && !isLoginPage && (
          <RightHeaderContainer>
            <GlobalNavigationBar globalNavigationBar={globalNavigationBar} userRole={userRole} />
            <PrimaryButton text="Logout" onClick={logoutOnClick} />
          </RightHeaderContainer>
        )}
      </HeaderInner>
    </StyledHeader>
  );
};

export default Header;
