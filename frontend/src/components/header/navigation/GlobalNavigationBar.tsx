import { GlobalNavigationBarType } from "@src/configs/navigationBarConfig";
import { UserRoleType } from "@src/configs/userRole";
import { isAuthorization } from "@src/utils/user";
import styled from "styled-components";
import GlobalNavigationBarItem from "./GlobalNavigationBarItem";

/**
 * Style
 */
const StyledGlobalNavigationBarList = styled.div`
  height: 40px;
  margin: 0;
  flex-grow: 1;
  justify-content: flex-end;
  display: flex;
  margin-right: 100px;
`;

/**
 * Type
 */
type GlobalNavigationBarProps = {
  globalNavigationBar: GlobalNavigationBarType[];
  userRole: UserRoleType;
};

/**
 * Render global navigation bar using the dynamic configuration below
 * and verify user roles to determine navigation bar display
 *
 * @see /src/configs/navigationBarConfig.ts
 *
 * @author Karden
 * @created 2024-07-17
 */
const GlobalNavigationBar = ({
  globalNavigationBar,
  userRole,
}: GlobalNavigationBarProps) => {
  return (
    <StyledGlobalNavigationBarList>
      {globalNavigationBar.map(
        (item) =>
          isAuthorization(item.userRole, userRole) && (
            <GlobalNavigationBarItem
              key={item.name}
              navigationItem={item}
              userRole={userRole}
            />
          )
      )}
    </StyledGlobalNavigationBarList>
  );
};

export default GlobalNavigationBar;
