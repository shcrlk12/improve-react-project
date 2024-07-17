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
  LOGIN: { INDEX: "/login", permit: ALL_ROLES },
  REPORT: {
    INDEX: "/report",
    permit: AUTHENTICATED_ROLES,
    U151: {
      INDEX: "/report/u151",
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

export const titleMathcers = [
  { route: routes.LOGIN.INDEX, title: "Login" },
  { route: routes.REPORT.U151.INDEX, title: "U136 일일 보고서 생성" },
  { route: routes.REPORT.U120.INDEX, title: "U120 일일 보고서 생성" },
  { route: routes.REPORT.U113.INDEX, title: "U113 일일 보고서 생성" },
  { route: routes.USER.MANAGEMENT.INDEX, title: "유저 관리" },
  { route: routes.USER.ADD.INDEX, title: "유저 추가" },
  { route: routes.USER.MODIFY.INDEX, title: "유저 수정" },
];
