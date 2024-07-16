import { ROLE_ADMIN } from "@src/configs/userRole";
import Header from "./Header";
import { globalNavigationBar } from "@src/configs/navigationBarConfig";

/**
 * Component
 */
const HeaderContainer = () => {
  return (
    <>
      <Header
        title="test"
        userRole={ROLE_ADMIN}
        globalNavigationBar={globalNavigationBar}
        logoOnClick={() => {}}
        logoutOnClick={() => {}}
      />
    </>
  );
};

export default HeaderContainer;
