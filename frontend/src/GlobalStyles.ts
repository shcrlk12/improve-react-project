import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

const GlobalStyles = createGlobalStyle` 
  ${reset}

:root{
	background-color: ${({ theme }) => theme.color.light.background}
}
`;

export default GlobalStyles;
