import { config } from "@config/config";
import { routes } from "@config/routes";
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

  const handleLogout = () => {
    fetch(config.apiServer + "/logout", {
      mode: "cors",
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    navigate(routes.LOGIN.INDEX);
  };

  return handleLogout;
};

export default useLogout;
