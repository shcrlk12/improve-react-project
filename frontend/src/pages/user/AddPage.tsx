import UserDetailContainer from "@components/user/detail/UserDetailContainer";
import { ROLE_USER } from "@config/userRole";
import { User } from "@reducers/userActions";

/**
 * Renders the user adding page.
 * Simply renders the UserDetailContainer component.
 *
 * @author Karden
 * @created 2024-07-17
 */
const AddPage = () => {
  const user = {
    role: ROLE_USER,
  } as User;

  return (
    <>
      <UserDetailContainer user={user} />
    </>
  );
};

export default AddPage;
