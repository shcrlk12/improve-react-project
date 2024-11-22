import { ROLE_ADMIN, UserRoleType } from "@src/configs/userRole";
import Header from "./Header";
import { globalNavigationBar } from "@src/configs/navigationBarConfig";
import useTitle from "@src/hooks/useTitle";
import useLogout from "@src/hooks/useLogout";
import { useSelector } from "react-redux";
import { RootState } from "@src/main";

/**
 * Render and involve bussiness logic of header component
 *
 * @author Karden
 * @created 2024-07-17
 */
const HeaderContainer = () => {
  const title = useTitle();
  const handleLogout = useLogout();
  const user = useSelector((store: RootState) => store.userReducer);

  return (
    <>
      <Header
        title={title}
        userRole={user.user.role as UserRoleType}
        globalNavigationBar={globalNavigationBar}
        logoutOnClick={handleLogout}
      />
    </>
  );
};

export default HeaderContainer;
