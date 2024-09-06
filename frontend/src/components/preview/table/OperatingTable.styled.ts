import styled from "styled-components";

export const StyledOperatingTable = styled.div`
  width: 900px;
  border: 1px solid ${({ theme }) => theme.color.black};
  & > div {
    border: 1px solid ${({ theme }) => theme.color.black};
  }
`;
export const Title = styled.div`
  padding: 3px;
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;
export const InfoLine = styled.div`
  display: flex;
  & > div:first-child {
    border-right: 2px solid ${({ theme }) => theme.color.black};
  }
`;
export const ItemContainer = styled.div`
  display: flex;
  width: 50%;
  & > div:first-child {
    border-right: 2px solid ${({ theme }) => theme.color.black};
  }
`;
export const ItemTitle = styled.div`
  flex-basis: 0px;
  flex-grow: 2;
  padding: 3px;
  align-content: center;
`;
export const ItemContent = styled.div`
  flex-basis: 0px;
  flex-grow: 5;
  padding: 3px 4px;
  align-content: center;
`;
