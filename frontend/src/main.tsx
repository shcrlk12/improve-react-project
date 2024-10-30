import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { DefaultTheme, ThemeProvider } from "styled-components";
import { Provider } from "react-redux";
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import userReducer from "./reducers/userReducer";
import appReducer from "./reducers/appReducer";

//style
import GlobalStyles from "./GlobalStyles";
import theme from "@config/theme/theme.ts";
import { BrowserRouter as Router } from "react-router-dom";

const mainTheme: DefaultTheme = {
  color: { ...theme.color.light },
  font: { ...theme.font },
};

const rootReducer = combineReducers({ appReducer, userReducer });

const store = configureStore({
  reducer: rootReducer,
  devTools: true,
});

export type RootState = ReturnType<typeof rootReducer>;

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <Provider store={store}>
      <ThemeProvider theme={mainTheme}>
        <GlobalStyles />
        <Router>
          <App />
        </Router>
      </ThemeProvider>
    </Provider>
  </React.StrictMode>,
);
