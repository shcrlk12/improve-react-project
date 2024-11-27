import UserDetailContainer from "@components/user/detail/UserDetailContainer";
import { User } from "@reducers/userActions";
import { RootState } from "@src/main";
import { useSelector } from "react-redux";

/**
 * Renders the user modifing page.
 * Simply renders the UserDetailContainer component.
 *
 * @author Karden
 * @created 2024-07-17
 */
const MyInformation = () => {
  const user = useSelector((store: RootState) => store.userReducer.user as User);

  return (
    <>
      <UserDetailContainer user={user} />
    </>
  );
};

export default MyInformation;
