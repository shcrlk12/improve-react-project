import { SubNavigationItem } from "@src/configs/navigationBarConfig";
import { UserRoleType } from "@src/configs/userRole";
import { isAuthorization } from "@src/utils/user";
import { Link } from "react-router-dom";
import styled from "styled-components";

/**
 * Style
 */
const StyledLocalNavigationBar = styled.div<{ display: string }>`
  position: absolute;
  top: 40px;
  left: -15px;

  z-index: 9999;
  width: 150px;

  padding: 12px;
  background-color: ${({ theme }) => theme.color.lightPrimary};
  border: 1px solid ${({ theme }) => theme.color.lightSecondary};
  opacity: 0.9;
  border-radius: 4px;

  box-sizing: border-box;

  display: ${(props) => (props.display == "true" ? "block" : "none")};
`;

const LocalNavigationBarInnter = styled.ul`
  display: flex;
  flex-direction: column;
  align-content: space-between;
  margin: 0;

  & > li {
    font-size: ${({ theme }) => theme.font.size.small};
    height: 26px;
    display: flex;
    align-items: center;
    margin-top: 6px;
    box-sizing: border-box;
    &:first-child {
      margin-top: 0;
    }

    & > a {
      color: ${({ theme }) => theme.color.darkPrimary};
      font-weight: ${({ theme }) => theme.font.weight.bold};
      display: flex;
      width: 100%;
      height: 100%;
      align-items: center;
      &:hover {
        font-weight: bold;
        color: ${({ theme }) => theme.color.darkPrimary};
        border-bottom: 2px solid ${({ theme }) => theme.color.darkSecondary};
      }
    }
  }
`;

/**
 * Type
 */
type LocalNavigationBarContainerProps = {
  navigationItems: SubNavigationItem[];
  userRole: UserRoleType;
  isShow: boolean;
  onMouseEnter: React.MouseEventHandler<HTMLDivElement>;
  onMouseLeave: React.MouseEventHandler<HTMLDivElement>;
};

/**
 *
 * Render Local navigation container and items
 * and verify user roles to determine navigation bar display
 *
 * @author Karden
 * @created 2024-07-17
 */
const LocalNavigationBar = ({
  navigationItems,
  userRole,
  isShow = true,
  onMouseEnter,
  onMouseLeave,
}: LocalNavigationBarContainerProps) => {
  return (
    <StyledLocalNavigationBar
      display={isShow ? "true" : "false"}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
    >
      <LocalNavigationBarInnter>
        {navigationItems.map(
          (item, index) =>
            isAuthorization(item.userRole, userRole) && (
              <li key={index}>
                <Link to={item.link}>{item.name}</Link>
              </li>
            )
        )}
      </LocalNavigationBarInnter>
    </StyledLocalNavigationBar>
  );
};

export default LocalNavigationBar;
