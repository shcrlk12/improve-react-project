import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { DefaultTheme, ThemeProvider } from "styled-components";

//style
import GlobalStyles from "./GlobalStyles";
import theme from "@config/theme/theme.ts";
import { BrowserRouter as Router } from "react-router-dom";

const mainTheme: DefaultTheme = {
  color: { ...theme.color.light },
  font: { ...theme.font },
};

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider theme={mainTheme}>
      <GlobalStyles />
      <Router>
        <App />
      </Router>
    </ThemeProvider>
  </React.StrictMode>
);
