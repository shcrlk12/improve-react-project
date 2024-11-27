import UserDetail from "./UserDetail";
import { User } from "@reducers/userActions";

/**
 * UserDetailContainer Component
 *
 * This component encapsulates the business logic for the UserDetail component.
 *
 * @author Karden
 * @created 2024-07-19
 */

type UserDetailContainerProps = {
  user: User;
};
const UserDetailContainer = ({ user }: UserDetailContainerProps) => {
  return <UserDetail user={user}></UserDetail>;
};

export default UserDetailContainer;
