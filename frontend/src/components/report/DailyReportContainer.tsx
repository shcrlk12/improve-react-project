import { MouseEvent, useState } from "react";
import DailyReport from "./DailyReport";
import { useLocation, useNavigate } from "react-router";
import { replaceLastPath } from "@src/utils/path";
import { selectSite } from "@reducers/appAction";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@src/main";

/**
 * Renders the daily report component and manages business logic related to daily reports.
 *
 * @author Karden
 * @created 2024-07-17
 */
const DailyReportContainer = () => {
  const [selectedDate, setSelectedDate] = useState<Date>(new Date(Date.now() - 24 * 60 * 60 * 1000));
  const selectedSite = useSelector((store: RootState) => store.appReducer.selectedSite);
  const sites = useSelector((store: RootState) => store.appReducer.sites);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { pathname } = useLocation();

  return (
    <DailyReport
      sites={sites}
      selectedSite={selectedSite}
      selectedDate={selectedDate}
      setSelectedDate={setSelectedDate}></DailyReport>
  );
};

export default DailyReportContainer;
