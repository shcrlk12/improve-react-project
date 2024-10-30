import { FlattenInterpolation, css } from "styled-components";

export const flexCenter: FlattenInterpolation<any> = css`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const flexCoulmnCenter: FlattenInterpolation<any> = css`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const flexSpaceBetween: FlattenInterpolation<any> = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const flexRowReverse: FlattenInterpolation<any> = css`
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
`;
