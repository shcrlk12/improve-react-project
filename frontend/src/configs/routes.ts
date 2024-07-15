import {
  ROLE_ADMIN,
  ROLE_ANONYMOUS,
  ROLE_MANAGER,
  ROLE_USER,
} from "./userRole";

const ALL_ROLES = [ROLE_ANONYMOUS, ROLE_USER, ROLE_MANAGER, ROLE_ADMIN];
const AUTHENTICATED_ROLES = [ROLE_USER, ROLE_MANAGER, ROLE_ADMIN];
const MANAGE_ROLES = [ROLE_MANAGER, ROLE_ADMIN];

export const routes = {
  HOME: { INDEX: "/", permit: ALL_ROLES },
  LOGIN: { INDEX: "/login", permit: ALL_ROLES },
  REPORT: {
    INDEX: "/report",
    permit: AUTHENTICATED_ROLES,
    U136: {
      INDEX: "/report/u136",
      permit: AUTHENTICATED_ROLES,
    },
    U113: {
      INDEX: "/report/u113",
      permit: AUTHENTICATED_ROLES,
    },
    U120: {
      INDEX: "/report/u120",
      permit: AUTHENTICATED_ROLES,
    },
  },
  USER: {
    INDEX: "/user",
    permit: MANAGE_ROLES,
    MANAGEMENT: {
      INDEX: "/user/management",
      permit: MANAGE_ROLES,
    },
    ADD: {
      INDEX: "/user/add",
      permit: MANAGE_ROLES,
    },
    MODIFY: {
      INDEX: "/user/modify",
      permit: MANAGE_ROLES,
    },
  },
};
