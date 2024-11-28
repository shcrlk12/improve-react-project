import { config, getRestApiServerUrl } from "@config/config";
import { routes } from "@config/routes";
import { logout } from "@reducers/userActions";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";
import { jsonOrgConfig } from "./../jsonApiOrg/JsonApiOrg";

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
    fetch(getRestApiServerUrl(`/logout`), {
      mode: "cors",
      method: "POST",
      credentials: "include",
      headers: {
        Accept: jsonOrgConfig.ACCEPT,
        "Content-Type": jsonOrgConfig.CONTENT_TYPE,
      },
    });
    navigate(routes.LOGIN.INDEX);
    dispatch(logout());
  };

  return handleLogout;
};

export default useLogout;
