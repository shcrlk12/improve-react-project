import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

const GlobalStyles = createGlobalStyle` 
  ${reset}

:root{
	background-color: ${({ theme }) => theme.color.background}
}

*{
  box-sizing: border-box;
}

a{
	text-decoration: none;
}

body{
  margin-bottom: 15px;
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

html {
  font-family: sans-serif;
  font-size: 14px;
}

table,
.divTable {
  min-width:100%;
  border: 1px solid ${({ theme }) => theme.color.black};
  width: fit-content;
}

.tr {
  display: flex;
}

tr,
.tr {
  width: fit-content;
  height: 30px;
}

th,
.th,
td,
.td {
  align-content: center;
  box-shadow: inset 0 0 0 1px ${({ theme }) => theme.color.black};
  padding: 0.25rem 6px;
}

th,
.th {
  padding: 2px 4px;
  position: relative;
  font-weight: bold;
  text-align: center;
  height: 30px;
}

td,
.td {
  height: 30px;
}

.resizer {
  position: absolute;
  top: 0;
  right: 0;
  height: 100%;
  width: 5px;
  background: rgba(0, 0, 0, 0.5);
  cursor: col-resize;
  user-select: none;
  touch-action: none;
}

.resizer.isResizing {
  background: blue;
  opacity: 1;
}

@media (hover: hover) {
  .resizer {
    opacity: 0;
  }

  *:hover > .resizer {
    opacity: 1;
  }
}
`;
export default GlobalStyles;
