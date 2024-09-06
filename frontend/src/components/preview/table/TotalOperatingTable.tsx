import React from "react";

import {
  InfoLine,
  ItemContainer,
  ItemContent,
  ItemTitle,
  StyledOperatingTable,
  Title,
} from "./OperatingTable.styled";
/**
 * Types
 */
export type TotalOperatingTableProps = {
  startOperatingDate: Date;
  targetOperatingDate: Date;
  totalActivePower: number; //kWh
  totalCapacityFactor: number; //percent
  operatingTime: number; //second
  generatingTime: number; //second
};
const TotalOperatingTable: React.FC<{
  totalOperatingTableProps: TotalOperatingTableProps;
}> = ({ totalOperatingTableProps }) => {
  // const {
  //   startOperatingDate,
  //   targetOperatingDate,
  //   totalActivePower,
  //   totalCapacityFactor,
  //   operatingTime,
  //   generatingTime,
  // } = totalOperatingTableProps;
  return (
    <StyledOperatingTable>
      <Title>누적 운전 현황</Title>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 기간</ItemTitle>
          <ItemContent>`19.09.03 00:00 ~ `24.08.27 24:00 (43,704h)</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>누적 발전량</ItemTitle>
          <ItemContent>38,509,959.02 kWh (CF: 23.49%)</ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>누적 운전시간</ItemTitle>
          <ItemContent>38,121h 23m (87.23%)</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>누적 발전시간</ItemTitle>
          <ItemContent>24,454h 0m</ItemContent>
        </ItemContainer>
      </InfoLine>
    </StyledOperatingTable>
  );
};

export default TotalOperatingTable;
