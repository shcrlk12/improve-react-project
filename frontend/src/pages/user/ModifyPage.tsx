import UserDetailContainer from "@components/user/detail/UserDetailContainer";
import { ROLE_USER } from "@config/userRole";
import { User } from "@reducers/userActions";
import { useLocation } from "react-router";

/**
 * Renders the user modifing page.
 * Simply renders the UserDetailContainer component.
 *
 * @author Karden
 * @created 2024-07-17
 */
const ModifyPage = () => {
  const location = useLocation();

  const paths = location.pathname.split("/");

  const userId = paths[paths.length - 1];

  const user = { id: userId, role: ROLE_USER } as User;

  return (
    <>
      <UserDetailContainer user={user} />
    </>
  );
};

export default ModifyPage;
