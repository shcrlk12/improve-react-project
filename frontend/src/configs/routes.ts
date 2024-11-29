import { constants } from "@config/constants";
import { ROLE_ADMIN, ROLE_ANONYMOUS, ROLE_MANAGER, ROLE_USER } from "./userRole";

export const ALL_ROLES = [ROLE_ANONYMOUS, ROLE_USER, ROLE_MANAGER, ROLE_ADMIN];
export const AUTHENTICATED_ROLES = [ROLE_USER, ROLE_MANAGER, ROLE_ADMIN];
export const MANAGE_ROLES = [ROLE_MANAGER, ROLE_ADMIN];

export const routes = {
  LOGIN: { INDEX: "/login", permit: ALL_ROLES },
  REPORT: {
    INDEX: "/report",
    permit: AUTHENTICATED_ROLES,
    U151: {
      INDEX: "/report/" + constants.u151Uuid,
      permit: AUTHENTICATED_ROLES,
    },
    U113: {
      INDEX: "/report/" + constants.u113Uuid,
      permit: AUTHENTICATED_ROLES,
    },
    U120: {
      INDEX: "/report/" + constants.u120Uuid,
      permit: AUTHENTICATED_ROLES,
    },
  },
  USER: {
    INDEX: "/user",
    permit: AUTHENTICATED_ROLES,
    MANAGEMENT: {
      INDEX: "/user/management",
      permit: MANAGE_ROLES,
    },
    ADD: {
      INDEX: "/user/add",
      permit: MANAGE_ROLES,
    },
    MODIFY: {
      INDEX: "/user/modify/*",
      permit: MANAGE_ROLES,
    },
    MY_INFOMATION: {
      INDEX: "/user/my_infomation",
      permit: AUTHENTICATED_ROLES,
    },
  },
};

export const titleMathcers = [
  { route: routes.LOGIN.INDEX, title: "Login" },
  { route: routes.REPORT.U151.INDEX, title: "U151 일일 보고서 생성" },
  { route: routes.REPORT.U120.INDEX, title: "U120 일일 보고서 생성" },
  { route: routes.REPORT.U113.INDEX, title: "U113 일일 보고서 생성" },
  { route: routes.USER.MANAGEMENT.INDEX, title: "유저 관리" },
  { route: routes.USER.ADD.INDEX, title: "유저 추가" },
  { route: routes.USER.MODIFY.INDEX, title: "유저 수정" },
  { route: routes.USER.MY_INFOMATION.INDEX, title: "내 정보 관리" },
];
