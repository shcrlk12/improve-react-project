import { flexCenter } from "@style/Common";
import styled from "styled-components";

export const Loadingbg = styled.div`
  position: fixed;
  z-index: 99999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
`;

export const LoadingInner = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 70px;
  height: 70px;
  transform: translate(-50%, -50%);
  border-radius: 6px;
  ${flexCenter}
  background-color: ${({ theme }) => theme.color.background};

  .loading-icon {
    color: ${({ theme }) => theme.color.primary};
  }
`;
