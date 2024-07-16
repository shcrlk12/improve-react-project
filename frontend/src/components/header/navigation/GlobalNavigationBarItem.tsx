import { GlobalNavigationBarType } from "@src/configs/navigationBarConfig";
import styled from "styled-components";
import LocalNavigationBarContainer from "./LocalNavigationBarContainer";
import { UserRoleType } from "@src/configs/userRole";

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
  border-left: 1px solid ${({ theme }) => theme.color.light.primary};

  * {
    background-color: inherit;
  }
`;

type GlobalNavigationBarItemProps = {
  navigationItem: GlobalNavigationBarType;
  userRole: UserRoleType;
};

const GlobalNavigationBarItem = ({
  navigationItem,
  userRole,
}: GlobalNavigationBarItemProps) => {
  return (
    <StyledGlobalNavigationBarItem>
      <Name>{navigationItem.name}</Name>
      <LocalNavigationBarContainer
        navigationItems={navigationItem.items}
        userRole={userRole}
      />
    </StyledGlobalNavigationBarItem>
  );
};

export default GlobalNavigationBarItem;
