import { config } from "@config/config";
import { routes } from "@config/routes";
import { logout } from "@reducers/userActions";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";

/**
 * Handles the logout action.
 * Removes user data from cookie and redirects to the login page.
 *
 * @todo we need to implement logout function
 *
 * @author Karden
 * @created 2024-07-17
 */
const useLogout = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogout = () => {
    fetch(`http://${config.apiServer.ip}:${config.apiServer.port}/logout`, {
      mode: "cors",
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    navigate(routes.LOGIN.INDEX);
    dispatch(logout());
  };

  return handleLogout;
};

export default useLogout;
