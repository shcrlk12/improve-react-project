import HeaderContainer from "@components/header/HeaderContainer";
import LoginPage from "./pages/LoginPage";
import { Navigate, Route, Routes } from "react-router";

function App() {
  return (
    <>
      <HeaderContainer />
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </>
  );
}

export default App;
