import {
  ROLE_ADMIN,
  ROLE_ANONYMOUS,
  ROLE_MANAGER,
  ROLE_USER,
  UserRoleType,
} from "@src/configs/userRole";

export const isAuthenticated = (userRole: UserRoleType) => {
  return (
    userRole === ROLE_USER ||
    userRole === ROLE_MANAGER ||
    userRole === ROLE_ADMIN
  );
};

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
