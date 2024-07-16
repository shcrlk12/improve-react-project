import { GlobalNavigationBarType } from "@src/configs/navigationBarConfig";
import { UserRoleType } from "@src/configs/userRole";
import { isAuthorization } from "@src/utils/user";
import styled from "styled-components";
import GlobalNavigationBarItem from "./GlobalNavigationBarItem";

const StyledGlobalNavigationBarList = styled.div`
  height: 40px;
  margin: 0;
  flex-grow: 1;
  justify-content: flex-end;
  display: flex;
  margin-right: 100px;
`;

type GlobalNavigationBarProps = {
  globalNavigationBar: GlobalNavigationBarType[];
  userRole: UserRoleType;
};

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
