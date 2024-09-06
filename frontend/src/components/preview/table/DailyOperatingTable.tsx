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
export type DailyOperatingTableProps = {
  operatingDate: Date;
  reportWriteDate: Date;
  averageWindSpeed: number; // m/s
  totalActivePower: number; //kWh
  capacityFactor: number; //percent
  operatingTime: number; // second
  generatingTime: number; //second
};

/**
 * Styled
 */

const DailyOperatingTable: React.FC<{
  dailyOperatingTableProps: DailyOperatingTableProps;
}> = ({ dailyOperatingTableProps }) => {
  return (
    <StyledOperatingTable>
      <Title>일별 운전 현황</Title>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 기간</ItemTitle>
          <ItemContent>`24.08.27 00:00 ~ 24:00</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>작성일</ItemTitle>
          <ItemContent>2024년 08월 28일</ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>평균 풍속</ItemTitle>
          <ItemContent>3.47 m/s</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>발전량</ItemTitle>
          <ItemContent>9,203.39 kWh (CF:8.92%)</ItemContent>
        </ItemContainer>
      </InfoLine>
      <InfoLine>
        <ItemContainer>
          <ItemTitle>운전 시간 (가동률)</ItemTitle>
          <ItemContent>17h 16m (71.96%)</ItemContent>
        </ItemContainer>
        <ItemContainer>
          <ItemTitle>발전시간</ItemTitle>
          <ItemContent>7h 37m</ItemContent>
        </ItemContainer>
      </InfoLine>
    </StyledOperatingTable>
  );
};

export default DailyOperatingTable;
