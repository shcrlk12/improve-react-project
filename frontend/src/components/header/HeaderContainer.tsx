import { ROLE_ADMIN, UserRoleType } from "@src/configs/userRole";
import Header from "./Header";
import { globalNavigationBar } from "@src/configs/navigationBarConfig";
import useTitle from "@src/hooks/useTitle";
import useLogout from "@src/hooks/useLogout";
import { useSelector } from "react-redux";
import { RootState } from "@src/main";
import { User } from "@reducers/userActions";

/**
 * Render and involve bussiness logic of header component
 *
 * @author Karden
 * @created 2024-07-17
 */
const HeaderContainer = () => {
  const title = useTitle();
  const user = useSelector((store: RootState) => store.userReducer.user);

  return (
    <>
      <Header title={title} user={user as User} globalNavigationBar={globalNavigationBar} />
    </>
  );
};

export default HeaderContainer;
