import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

const GlobalStyles = createGlobalStyle` 
  ${reset}

:root{
	background-color: ${({ theme }) => theme.color.background}
}

a{
	text-decoration: none;
}

.hidden-element{
	display:none;
}

@keyframes selected-site {
  0% {

  }
  100% {
	background: ${({ theme }) => theme.color.primary};

  }
}
`;
export default GlobalStyles;
