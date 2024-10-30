import React from "react";

import { InfoLine, ItemContainer, ItemContent, ItemTitle, StyledOperatingTable, Title } from "./OperatingTable.styled";
import { isPropsEmpty } from "@src/utils/props";
import { format, parseISO, differenceInHours, differenceInSeconds } from "date-fns";

/**
 * Types
 */
export type TotalOperatingTableProps = {
  startDate: string;
  writtenDate: string;
  totalActivePower: number;
  totalOperatingTime: number;
  totalGeneratingTime: number;
  ratedPower: number; //kw
};
const TotalOperatingTable: React.FC<{
  totalOperatingTableProps: TotalOperatingTableProps;
}> = ({ totalOperatingTableProps }) => {
  if (isPropsEmpty(totalOperatingTableProps)) return null;

  const { startDate, writtenDate, totalActivePower, totalOperatingTime, totalGeneratingTime, ratedPower } = {
    ...totalOperatingTableProps,
    ratedPower: 4300,
  };

  // const {
  //   startOperatingDate,
  //   targetOperatingDate,
  //   totalActivePower,
  //   totalCapacityFactor,
  //   operatingTime,
  //   generatingTime,
  // } = totalOperatingTableProps;
  const operatingTimeInHours = differenceInHours(parseISO(writtenDate), parseISO(startDate));
  const operatingTimeInSeconds = differenceInSeconds(parseISO(writtenDate), parseISO(startDate));

  return (
    <StyledOperatingTable>
      <Title>누적 운전 현황</Title>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 기간</ItemTitle>
          <ItemContent>
            {format(startDate, "`yy.MM.dd 00:00") + " ~ " + format(writtenDate, "`yy.MM.dd 00:00")} (
            {operatingTimeInHours.toLocaleString("ko-KR")}h)
          </ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>누적 발전량</ItemTitle>
          <ItemContent>
            {Number(totalActivePower.toFixed(2)).toLocaleString("ko-KR")} kWh (CF:{" "}
            {((totalActivePower / (ratedPower * operatingTimeInHours)) * 100).toFixed(2)} %)
          </ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>누적 운전시간</ItemTitle>
          <ItemContent>
            {Number((totalOperatingTime / 3600).toFixed(0)).toLocaleString("ko-KR")}h{" "}
            {((totalOperatingTime % 3600) / 60).toFixed(0)}m (
            {((totalOperatingTime / operatingTimeInSeconds) * 100).toFixed(2)} %)
          </ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>누적 발전시간</ItemTitle>
          <ItemContent>
            {Number((totalGeneratingTime / 3600).toFixed(0)).toLocaleString("ko-KR")}h{" "}
            {((totalGeneratingTime % 3600) / 60).toFixed(0)}m
          </ItemContent>
        </ItemContainer>
      </InfoLine>
    </StyledOperatingTable>
  );
};

export default TotalOperatingTable;
