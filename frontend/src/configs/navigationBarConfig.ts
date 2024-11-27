import { ROLE_ADMIN, ROLE_USER, UserRoleType } from "./userRole";
import { routes } from "./routes";

export type SubNavigationItem = {
  name: string;
  link: string;
  userRole: UserRoleType;
};

export type GlobalNavigationBarType = {
  name: string;
  link: string;
  userRole: UserRoleType;
  items: SubNavigationItem[];
};

export const globalNavigationBar: GlobalNavigationBarType[] = [
  {
    name: "daily report",
    link: routes.REPORT.INDEX,
    userRole: ROLE_USER,
    items: [
      {
        name: "U113",
        link: routes.REPORT.U113.INDEX,
        userRole: ROLE_USER,
      },
      {
        name: "U151",
        link: routes.REPORT.U151.INDEX,
        userRole: ROLE_USER,
      },
      // {
      //   name: "U120",
      //   link: routes.REPORT.U120.INDEX,
      //   userRole: ROLE_USER,
      // },
    ],
  },
  {
    name: "user",
    link: routes.USER.MANAGEMENT.INDEX,
    userRole: ROLE_USER,
    items: [
      {
        name: "My infomation",
        link: routes.USER.MY_INFOMATION.INDEX,
        userRole: ROLE_USER,
      },
      {
        name: "User management",
        link: routes.USER.MANAGEMENT.INDEX,
        userRole: ROLE_ADMIN,
      },
      {
        name: "Add user",
        link: routes.USER.ADD.INDEX,
        userRole: ROLE_ADMIN,
      },
    ],
  },
];
