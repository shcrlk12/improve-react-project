import { DefaultTheme } from "styled-components";

const color = {
  light: {
    primary: "#16a6f3",
    darkPrimary: "#08689b",
    lightPrimary: "#47b8f5",
    secondary: "#1634f3",
    darkSecondary: "#0b24cb",
    lightSecondary: "#475ef5",
    textOnPrimary: "#F2F5FA",
    textOnSecondary: "#F2F5FA",
    black: "#0D171C",
    white: "#FFFFFF",
    background: "#F2F5FA",
    dangerous: "#f31634",
    info: "#309ed9",
    success: "#6ce359",
    wanring: "#f58447",
  },
  dark: {
    primary: "#16a6f3",
    darkPrimary: "#08689b",
    lightPrimary: "#47b8f5",
    secondary: "#1634f3",
    darkSecondary: "#0b24cb",
    lightSecondary: "#475ef5",
    textOnPrimary: "#F2F5FA",
    textOnSecondary: "#F2F5FA",
    black: "#0D171C",
    white: "#FFFFFF",
    background: "#F2F5FA",
    dangerous: "#f31634",
    info: "#309ed9",
    success: "#6ce359",
    wanring: "#f58447",
  },
};

const font = {
  family: {
    korean: "",
    english: "",
  },
  size: {
    xxLarge: "22px",
    xLarge: "20px",
    large: "18px",
    medium: "16px",
    small: "14px",
    xSmall: "12px",
  },
  weight: {
    bold: 600,
    semiBold: 500,
    medium: 400,
    regular: 300,
    light: 200,
  },
};

export type ColorType = typeof color;
export type FontType = typeof font;

const theme: DefaultTheme = {
  color,
  font,
};

export default theme;
