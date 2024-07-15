import "styled-components";
import { ColorType, FontType } from "./theme";

declare module "styled-components" {
  export interface DefaultTheme {
    color: ColorType;
    font: FontType;
  }
}
