/* eslint-disable @typescript-eslint/no-explicit-any */
import HeaderContainer from "@components/header/HeaderContainer";
import LoginPage from "@pages/LoginPage";
import { Navigate, Route, Routes } from "react-router";
import U136Page from "@pages/report/U151Page";
import { routes } from "@config/routes";
import ManagementPage from "@pages/user/ManagementPage";
import AddPage from "@pages/user/AddPage";
import ModifyPage from "@pages/user/ModifyPage";
import Loading from "@components/App/Loading";
import { ROLE_ADMIN, ROLE_ANONYMOUS, ROLE_MANAGER, ROLE_USER, UserRoleType } from "@config/userRole";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "./main";
import { useEffect, useMemo } from "react";
import useFetchData from "./hooks/useFetchData";
import { JsonApi } from "./jsonApiOrg/JsonApiOrg";
import { config } from "@config/config";
import { selectSite, setSites, SiteType } from "@reducers/appAction";

type PageRole = {
  path: string;
  component: any;
};

type SiteTypeDto = {
  name: string;
  remark: string;
  ratedPower: number;
};

function App() {
  const userRole = useSelector((store: RootState) => store.userReducer.user.role) as UserRoleType;
  const sites = useSelector((store: RootState) => store.appReducer.sites);

  const fetchData = useFetchData();
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData<Array<JsonApi<SiteTypeDto>>>(
          `http://${config.apiServer.ip}:${config.apiServer.port}/api/data/sites`,
          {
            mode: "cors",
            method: "GET",
            credentials: "include",
          },
        );

        const sites: SiteType[] = data.map(
          (item) =>
            ({
              uuid: item.id,
              name: item.attributes.name.toUpperCase(),
              remark: item.attributes.remark,
              ratedPower: item.attributes.ratedPower,
            }) as SiteType,
        );

        dispatch(setSites(sites));
        dispatch(selectSite(sites[0]));
      } catch (error) {}
    };

    fetchDataAsync();
  }, [userRole]);

  const getRouteByRole = (userRole: UserRoleType, siteTypes: SiteType[]) => {
    const anonymousRoleRoutes: PageRole[] = [{ path: routes.LOGIN.INDEX, component: <LoginPage /> }];

    const userRoleRoutes: PageRole[] = [
      ...anonymousRoleRoutes,
      { path: routes.USER.MY_INFOMATION.INDEX, component: <ModifyPage /> },
    ];

    siteTypes.forEach((item, index) => {
      const url = routes.REPORT.INDEX + "/" + item.uuid;

      if (index === 0) {
        userRoleRoutes.push({ path: routes.REPORT.INDEX, component: <Navigate to={url} /> });
      }
      userRoleRoutes.push({ path: url, component: <U136Page /> });
    });
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

  const routesByRole = useMemo(() => getRouteByRole(userRole, sites), [sites, userRole]);

  return (
    <>
      <HeaderContainer />
      <Loading />
      <Routes>
        {routesByRole}
        <Route path="*" element={<Navigate to={routes.LOGIN.INDEX} />} />
      </Routes>
    </>
  );
}

export default App;
