/* eslint-disable @typescript-eslint/no-explicit-any */
import HeaderContainer from "@components/header/HeaderContainer";
import LoginPage from "@pages/LoginPage";
import { Navigate, Route, Routes } from "react-router";
import U136Page from "@pages/report/U151Page";
import U120Page from "@pages/report/U120Page";
import U113Page from "@pages/report/U113Page";
import { routes } from "@config/routes";
import ManagementPage from "@pages/user/ManagementPage";
import AddPage from "@pages/user/AddPage";
import ModifyPage from "@pages/user/ModifyPage";
import Loading from "@components/App/Loading";
import { ROLE_ADMIN, ROLE_ANONYMOUS, ROLE_MANAGER, ROLE_USER, UserRoleType } from "@config/userRole";
import { useSelector } from "react-redux";
import { RootState } from "./main";

type PageRole = {
  path: string;
  component: any;
};

const getRouteByRole = (userRole: UserRoleType) => {
  const anonymousRoleRoutes: PageRole[] = [{ path: routes.LOGIN.INDEX, component: <LoginPage /> }];

  const userRoleRoutes: PageRole[] = [
    ...anonymousRoleRoutes,
    { path: routes.REPORT.INDEX, component: <Navigate to={routes.REPORT.U151.INDEX} /> },
    { path: routes.REPORT.U151.INDEX, component: <U136Page /> },
    { path: routes.REPORT.U113.INDEX, component: <U113Page /> },
    { path: routes.REPORT.U120.INDEX, component: <U120Page /> },
  ];

  const managerRoleRoutes: PageRole[] = [...userRoleRoutes];

  const adminRoleRoutes: PageRole[] = [
    ...managerRoleRoutes,
    { path: routes.USER.MANAGEMENT.INDEX, component: <ManagementPage /> },
    { path: routes.USER.ADD.INDEX, component: <AddPage /> },
    { path: routes.USER.MODIFY.INDEX, component: <ModifyPage /> },
  ];

  let roleRoutes: PageRole[];

  switch (userRole) {
    case ROLE_ADMIN:
      roleRoutes = adminRoleRoutes;
      break;
    case ROLE_MANAGER:
      roleRoutes = managerRoleRoutes;
      break;
    case ROLE_USER:
      roleRoutes = userRoleRoutes;
      break;
    case ROLE_ANONYMOUS:
      roleRoutes = anonymousRoleRoutes;
      break;
    default:
      roleRoutes = [{ path: "", component: "" }];
  }

  return roleRoutes.map((route) => <Route key={route.path} path={route.path} element={route.component} />);
};

function App() {
  const userRole = useSelector((store: RootState) => store.userReducer.user.role) as UserRoleType;

  return (
    <>
      <HeaderContainer />
      <Loading />
      <Routes>
        {getRouteByRole(userRole)}
        <Route path="*" element={<Navigate to={routes.LOGIN.INDEX} />} />
      </Routes>
    </>
  );
}

export default App;
