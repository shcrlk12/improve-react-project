import { DateCalendar, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { formatDateToString, getNameDayOfWeek } from "@src/utils/date";
import { Dispatch, SetStateAction, useState } from "react";
import dayjs from "dayjs";
import { PrimaryButton, SecondaryButton } from "@karden/utils/button";
import styled from "styled-components";

/**
 * Styles
 */

const CalendarContainer = styled.div`
  position: relative;
`;

const ShowCurrentDate = styled.div`
  font-size: ${({ theme }) => theme.font.size.small};
  text-shadow: 1px 1px 2px #ccc;
  cursor: pointer;
  height: 20px;
  width: 120px;
  border-radius: 5px;

  display: flex;
  justify-content: center;
  align-items: center;
  &:hover {
    font-weight: 600;
    background: rgba(0, 0, 0, 0.12);
  }
`;

const PopupContainer = styled.div`
  position: absolute;
  top: 20px;
  right: 0;
  background-color: #fff;
  border-radius: 10px;
  border: 1px solid ${({ theme }) => theme.color.primary};
  flex-direction: column;
  align-items: center;
  padding-bottom: 5px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 30px;
`;

/**
 * Types
 */
type CalendarPopupProps = {
  date: Date;
  setDate: Dispatch<SetStateAction<Date>>;
};

/**
 * CalendarPopup Component
 *
 * This component renders a calendar popup that allows users to change the selected date.
 * It provides a visual interface for date selection and includes buttons to confirm or cancel the changes.
 *
 * @author Karden
 * @created 2024-07-19
 */

const CalendarPopup = ({ date, setDate }: CalendarPopupProps) => {
  const [tempSelectedDate, setTempSelectedDate] = useState<Date>(date);
  const [isCalendarVisible, setIsCalendarVisible] = useState(false);

  return (
    <CalendarContainer>
      <ShowCurrentDate
        onClick={() => {
          setIsCalendarVisible(true);
        }}
      >
        {`${formatDateToString(date, "YYYY-MM-DD")}, ${getNameDayOfWeek(
          date.getDay()
        )}`}
      </ShowCurrentDate>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <PopupContainer
          style={{ display: isCalendarVisible ? "flex" : "none" }}
        >
          <DateCalendar
            value={dayjs(tempSelectedDate.toISOString())}
            onChange={(value) => {
              setTempSelectedDate(value.toDate());
            }}
          />
          <ButtonWrapper>
            <PrimaryButton
              type="button"
              text="확인"
              onClick={() => {
                setDate(tempSelectedDate);
                setIsCalendarVisible(false);
              }}
            />
            <SecondaryButton
              type="button"
              text="취소"
              onClick={() => {
                setIsCalendarVisible(false);
              }}
            />
          </ButtonWrapper>
        </PopupContainer>
      </LocalizationProvider>
    </CalendarContainer>
  );
};

export default CalendarPopup;
