import "styled-components";
import { DarkColorType, FontType, LightColorType } from "./theme";

declare module "styled-components" {
  export interface DefaultTheme {
    color: LightColorType | DarkColorType;
    font: FontType;
  }
}
