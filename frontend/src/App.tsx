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

function App() {
  return (
    <>
      <HeaderContainer />
      <Routes>
        <Route path={routes.LOGIN.INDEX} element={<LoginPage />} />
        <Route
          path={routes.REPORT.INDEX}
          element={<Navigate to={routes.REPORT.U151.INDEX} />}
        />
        <Route path={routes.REPORT.U151.INDEX} element={<U136Page />} />
        <Route path={routes.REPORT.U113.INDEX} element={<U113Page />} />
        <Route path={routes.REPORT.U120.INDEX} element={<U120Page />} />
        <Route
          path={routes.USER.MANAGEMENT.INDEX}
          element={<ManagementPage />}
        />
        <Route path={routes.USER.ADD.INDEX} element={<AddPage />} />
        <Route path={routes.USER.MODIFY.INDEX} element={<ModifyPage />} />

        <Route path="*" element={<Navigate to={routes.LOGIN.INDEX} />} />
      </Routes>
    </>
  );
}

export default App;
