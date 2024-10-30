import { isPropsEmpty } from "@src/utils/props";
import { InfoLine, ItemContainer, ItemContent, ItemTitle, StyledOperatingTable, Title } from "./OperatingTable.styled";
import { format } from "date-fns";

/**
 * Types
 */
export type DailyOperatingTableProps = {
  operatingPeriod: string;
  writtenDate: string;
  windSpeed: number; // m/s
  activePower: number; //kWh
  operatingTime: number; //second
  generatingTime: number; // second
};

/**
 * Styled
 */
const DailyOperatingTable: React.FC<{
  dailyOperatingTableProps: DailyOperatingTableProps;
}> = ({ dailyOperatingTableProps }) => {
  if (isPropsEmpty(dailyOperatingTableProps)) return null;

  const { operatingPeriod, writtenDate, windSpeed, activePower, operatingTime, generatingTime } =
    dailyOperatingTableProps;
  const ratedPower = 4300;

  const formattedDate = format(writtenDate, "yyyy년 MM월 dd일");

  return (
    <StyledOperatingTable>
      <Title>일별 운전 현황</Title>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 기간</ItemTitle>
          <ItemContent>{operatingPeriod}</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>작성일</ItemTitle>
          <ItemContent>{formattedDate}</ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>평균 풍속</ItemTitle>
          <ItemContent>{windSpeed} m/s</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>발전량</ItemTitle>
          <ItemContent>
            {Number(activePower.toFixed(2)).toLocaleString("ko-KR")} kWh (CF:{" "}
            {((activePower / (ratedPower * 24)) * 100).toFixed(2)} %)
          </ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 시간 (가동률)</ItemTitle>
          <ItemContent>
            {Number((operatingTime / 3600).toFixed(0)).toLocaleString("ko-KR")}h{" "}
            {((operatingTime % 3600) / 60).toFixed(0)}m ({((operatingTime / (3600 * 24)) * 100).toFixed(2)}%)
          </ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>발전시간</ItemTitle>
          <ItemContent>
            {Number((generatingTime / 3600).toFixed(0)).toLocaleString("ko-KR")}h{" "}
            {((generatingTime % 3600) / 60).toFixed(0)}m
          </ItemContent>
        </ItemContainer>
      </InfoLine>
    </StyledOperatingTable>
  );
};

export default DailyOperatingTable;
