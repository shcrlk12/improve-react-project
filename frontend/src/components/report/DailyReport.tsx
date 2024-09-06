import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { TextArea1 } from "@karden/utils/Input";
import { SitesType, turbineConfig } from "@config/config";
import { Dispatch, MouseEventHandler, SetStateAction } from "react";
import CalendarPopup from "./../calendar/CalendarPopup";
import PreviewContainer from "./../preview/PreviewContainer";

/**
 * Styled
 */
const StyledDailyReport = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  width: 900px;
  margin: auto;
`;

const SiteHeaderContainer = styled.div`
  display: flex;
`;

const HeaderItem = styled.div`
  padding: 10px 20px;
  position: relative;

  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  cursor: pointer;
  &.selected::after {
    content: "";
    height: 3px;
    width: 70%;
    border-radius: 2px;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;

    animation-name: selected-site;
    animation-duration: 0.6s;
    animation-fill-mode: forwards;
  }
`;

const SignificantContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  padding-bottom: 20px;
  border-bottom: 2px solid ${({ theme }) => theme.color.primary};
`;

const SignificantHeader = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;

const SignificantEditor = styled.div`
  background: #fff;
`;

const SignificantButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;
`;

/**
 * Period styled
 */
const CalendarPopupContainer = styled.div`
  display: flex;
  justify-content: end;
`;

const PeriodContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
`;

const PeriodHeader = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  width: 100%;
`;

// const PeriodSelectionTitle = styled.div``;
// const PeriodSelectionDate = styled.div``;

/**
 * Type
 */
type DailyReportType = {
  selectedSite: string;
  selectedDate: Date;
  setSelectedDate: Dispatch<SetStateAction<Date>>;
  handleSiteClick: MouseEventHandler<HTMLDivElement>;
};

/**
 * This is the daily report component.
 * It allows selection of any site of wind turbine,
 * generates a daily report preview, and provides the option to download the report.
 *
 * @todo1 I can draw preview UI later
 * @author Karden
 * @created 2024-07-17
 */
const DailyReport = ({
  selectedSite,
  selectedDate,
  setSelectedDate,
  handleSiteClick,
}: DailyReportType) => {
  const createHeaderItem = () => {
    const newArr: JSX.Element[] = [];

    let key: keyof SitesType;
    for (key in turbineConfig.sites) {
      newArr.push(
        <HeaderItem
          key={key}
          className={selectedSite === key ? "selected" : undefined}
          onClick={handleSiteClick}
        >
          <div className="hidden-element">{key}</div>
          {turbineConfig.sites[key]}
        </HeaderItem>
      );
    }
    return newArr;
  };

  return (
    <StyledDailyReport>
      <SiteHeaderContainer>{createHeaderItem()}</SiteHeaderContainer>
      <SignificantContainer>
        <SignificantHeader>특이사항</SignificantHeader>
        <SignificantEditor>
          <TextArea1
            text="[44299] PCS fault status ON 알람 관련 냉각수 플럭싱 및 PCS 리액터 교체작업 진행 중"
            onChange={() => {}}
          />
        </SignificantEditor>
        <SignificantButtonContainer>
          <PrimaryButton
            type="submit"
            text="저장"
            width="30%"
            onClick={() => {}}
          />
          <PrimaryButton
            type="submit"
            text="편집"
            width="30%"
            onClick={() => {}}
          />
        </SignificantButtonContainer>
      </SignificantContainer>
      <PeriodContainer>
        <PeriodHeader>기간 선택</PeriodHeader>
        <CalendarPopupContainer>
          <CalendarPopup date={selectedDate} setDate={setSelectedDate} />
        </CalendarPopupContainer>
      </PeriodContainer>
      <PreviewContainer />
      <SignificantButtonContainer>
        <PrimaryButton
          type="submit"
          text="미리보기 생성"
          onClick={() => {
            console.log(selectedDate.toDateString());
          }}
        />
      </SignificantButtonContainer>
    </StyledDailyReport>
  );
};

export default DailyReport;
