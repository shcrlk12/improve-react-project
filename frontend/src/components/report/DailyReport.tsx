import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { TextArea1 } from "@karden/utils/Input";
/**
 * Styled
 */
const StyledDailyReport = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
`;

const SiteHeaderContainer = styled.div`
  display: flex;
`;

const HeaderItem = styled.div`
  padding: 10px 20px;
  position: relative;

  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};

  &::after {
    content: "";
    height: 3px;
    width: 70%;
    border-radius: 2px;
    background: ${({ theme }) => theme.color.primary};
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
  }
`;

const SignificantContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;

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
const PeriodContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const PeriodHeader = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  width: 100%;
`;

const PeriodSelection = styled.div`
  display: flex;
`;

const PeriodSelectionTitle = styled.div``;
const PeriodSelectionDate = styled.div``;

/**
 * Preview styled
 */
const PreviewContainer = styled.div``;

/**
 * This is the daily report component.
 * It allows selection of any site of wind turbine,
 * generates a daily report preview, and provides the option to download the report.
 *
 * @todo1 I can draw preview UI later
 * @author Karden
 * @created 2024-07-17
 */
const DailyReport = () => {
  return (
    <StyledDailyReport>
      <SiteHeaderContainer>
        <HeaderItem>U151</HeaderItem>
        <HeaderItem>U113</HeaderItem>
        <HeaderItem>U120</HeaderItem>
      </SiteHeaderContainer>
      <SignificantContainer>
        <SignificantHeader>특이사항</SignificantHeader>
        <SignificantEditor>
          <TextArea1 text="[44299] PCS fault status ON 알람 관련 냉각수 플럭싱 및 PCS 리액터 교체작업 진행 중" />
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
        <PeriodSelection>
          <PeriodSelectionTitle>생성 일자:</PeriodSelectionTitle>
          <PeriodSelectionDate>
            <select name="period-year" id="period-year">
              <option value="2024">2024</option>
            </select>
            -
            <select name="period-month" id="period-month">
              <option value="7">7</option>
            </select>
            -
            <select name="period-date" id="period-date">
              <option value="17">17</option>
            </select>
          </PeriodSelectionDate>
        </PeriodSelection>
      </PeriodContainer>
      <PreviewContainer>Preview test</PreviewContainer>
      <SignificantButtonContainer>
        <PrimaryButton type="submit" text="미리보기 생성" onClick={() => {}} />
      </SignificantButtonContainer>
    </StyledDailyReport>
  );
};

export default DailyReport;
