import {
  ROLE_ADMIN,
  ROLE_ANONYMOUS,
  ROLE_MANAGER,
  ROLE_USER,
  UserRoleType,
} from "@src/configs/userRole";

/**
 * Check if the user is authenticated based on their role.
 *
 * @param {UserRoleType} userRole - The role of the user to check authentication status.
 * @returns {boolean} Returns true if the user is authenticated (ROLE_USER, ROLE_MANAGER, ROLE_ADMIN).
 *
 * @author Karden
 * @created 2024-07-17
 */
export const isAuthenticated = (userRole: UserRoleType) => {
  return (
    userRole === ROLE_USER ||
    userRole === ROLE_MANAGER ||
    userRole === ROLE_ADMIN
  );
};

/**
 * Verify if the user role is authorized based on the authorization role.
 *
 * @param {UserRoleType} authorizationRole - The role required for authorization.
 * @param {UserRoleType} userRole - The role of the current user to verify against authorizationRole.
 * @returns {boolean} Returns true if the user's role is authorized based on the authorizationRole.
 *
 * @author Karden
 * @created 2024-07-17
 */
export const isAuthorization = (
  authorizationRole: UserRoleType,
  userRole: UserRoleType
) => {
  if (authorizationRole === ROLE_ANONYMOUS) {
    if (
      userRole === ROLE_ANONYMOUS ||
      userRole === ROLE_USER ||
      userRole === ROLE_MANAGER ||
      userRole === ROLE_ADMIN
    ) {
      return true;
    }
    return false;
  } else if (authorizationRole === ROLE_USER) {
    if (
      userRole === ROLE_USER ||
      userRole === ROLE_MANAGER ||
      userRole === ROLE_ADMIN
    ) {
      return true;
    }
    return false;
  } else if (authorizationRole === ROLE_MANAGER) {
    if (userRole === ROLE_MANAGER || userRole === ROLE_ADMIN) {
      return true;
    }
    return false;
  } else if (authorizationRole === ROLE_ADMIN) {
    if (userRole === ROLE_ADMIN) {
      return true;
    }
    return false;
  }
};
