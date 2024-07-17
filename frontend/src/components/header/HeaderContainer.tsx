import { ROLE_ADMIN } from "@src/configs/userRole";
import Header from "./Header";
import { globalNavigationBar } from "@src/configs/navigationBarConfig";
import useTitle from "@src/hooks/useTitle";
import useLogout from "@src/hooks/useLogout";

/**
 * Render and involve bussiness logic of header component
 *
 * @author Karden
 * @created 2024-07-17
 */
const HeaderContainer = () => {
  const title = useTitle();
  const handleLogout = useLogout();

  return (
    <>
      <Header
        title={title}
        userRole={ROLE_ADMIN}
        globalNavigationBar={globalNavigationBar}
        logoOnClick={() => {}}
        logoutOnClick={handleLogout}
      />
    </>
  );
};

export default HeaderContainer;
