import { GlobalNavigationBarType } from "@src/configs/navigationBarConfig";
import styled from "styled-components";
import LocalNavigationBar from "./LocalNavigationBar";
import { UserRoleType } from "@src/configs/userRole";
import { useState } from "react";

/**
 * Style
 */
export const StyledGlobalNavigationBarItem = styled.div`
  position: relative;
  margin-right: 36px;
  cursor: pointer;
`;

export const Name = styled.span`
  display: flex;
  align-items: center;
  height: 100%;

  padding: 0 14px;
  border-left: 1px solid ${({ theme }) => theme.color.primary};

  * {
    background-color: inherit;
  }
`;

/**
 * Type
 */
type GlobalNavigationBarItemProps = {
  navigationItem: GlobalNavigationBarType;
  userRole: UserRoleType;
};

/**
 * Render local navigation bar
 * and verify user roles to determine navigation bar display
 *
 * @author Karden
 * @created 2024-07-17
 */
const GlobalNavigationBarItem = ({
  navigationItem,
  userRole,
}: GlobalNavigationBarItemProps) => {
  const [enterGNB, setEnterGNB] = useState<boolean>(false);
  const [enterLNB, setEnterLNB] = useState<boolean>(false);

  const onGNBMouseEnter = () => {
    setEnterGNB(true);
  };
  const onGNBMouseLeave = () => {
    setEnterGNB(false);
  };
  const onLNBMouseEnter = () => {
    setEnterLNB(true);
  };
  const onLNBMouseLeave = () => {
    setEnterLNB(false);
  };

  return (
    <StyledGlobalNavigationBarItem
      onMouseEnter={onGNBMouseEnter}
      onMouseLeave={onGNBMouseLeave}
    >
      <Name>{navigationItem.name}</Name>
      <LocalNavigationBar
        navigationItems={navigationItem.items}
        userRole={userRole}
        onMouseEnter={onLNBMouseEnter}
        onMouseLeave={onLNBMouseLeave}
        isShow={enterGNB || enterLNB}
      />
    </StyledGlobalNavigationBarItem>
  );
};

export default GlobalNavigationBarItem;
