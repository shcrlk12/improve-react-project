import { SubNavigationItem } from "@src/configs/navigationBarConfig";
import { UserRoleType } from "@src/configs/userRole";
import { isAuthorization } from "@src/utils/user";
import { Link } from "react-router-dom";
import styled from "styled-components";

const StyledLocalNavigationBarList = styled.div<{ $display: boolean }>`
  position: absolute;
  top: 40px;
  left: -15px;

  z-index: 9999;
  width: 150px;

  padding: 12px;
  background-color: ${({ theme }) => theme.color.light.secondary};
  border: 1px solid ${({ theme }) => theme.color.light.darkSecondary};
  border-radius: 4px;

  box-sizing: border-box;

  display: ${(props) => (props.$display ? "block" : "none")};

  & * {
    background-color: inherit;
  }
`;

const LocalNavigationBarInnter = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: space-between;
  margin: 0;

  & > li {
    font-size: 14px;
    height: 26px;
    display: flex;
    align-items: center;
    margin-top: 6px;
    box-sizing: border-box;

    &:first-child {
      margin-top: 0;
    }

    & > a {
      display: flex;
      width: 100%;
      height: 100%;
      align-items: center;
      &:hover {
        font-weight: bold;
        color: ${({ theme }) => theme.color.light.primary};
        border-bottom: 2px solid ${({ theme }) => theme.color.light.darkPrimary};
      }
    }
  }
`;

type LocalNavigationBarContainerProps = {
  navigationItems: SubNavigationItem[];
  userRole: UserRoleType;
};

const LocalNavigationBarContainer = ({
  navigationItems,
  userRole,
}: LocalNavigationBarContainerProps) => {
  return (
    <StyledLocalNavigationBarList $display={true}>
      <LocalNavigationBarInnter>
        {navigationItems.map(
          (item, index) =>
            isAuthorization(item.userRole, userRole) && (
              <li key={index}>
                {/* <Link to={item.link}>{item.name}</Link> */}
              </li>
            )
        )}
      </LocalNavigationBarInnter>
    </StyledLocalNavigationBarList>
  );
};

export default LocalNavigationBarContainer;
