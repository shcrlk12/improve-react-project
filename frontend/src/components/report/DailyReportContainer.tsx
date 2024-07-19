import { MouseEvent, useState } from "react";
import DailyReport from "./DailyReport";
import { useLocation, useNavigate } from "react-router";
import { SitesType, turbineConfig } from "@config/config";
import { replaceLastPath } from "@src/utils/path";

type DailyReportContainerType = {
  selectedSite: string;
};
/**
 * Renders the daily report component and manages business logic related to daily reports.
 *
 * @author Karden
 * @created 2024-07-17
 */
const DailyReportContainer = ({ selectedSite }: DailyReportContainerType) => {
  const [currentSite, setCurrentSite] = useState<string>(selectedSite);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date(Date.now()));

  const navigate = useNavigate();
  const { pathname } = useLocation();
  return (
    <DailyReport
      selectedSite={currentSite}
      selectedDate={selectedDate}
      setSelectedDate={setSelectedDate}
      handleSiteClick={(event: MouseEvent<HTMLDivElement>) => {
        const siteUuid = event.currentTarget.querySelector(".hidden-element")
          ?.innerHTML as keyof SitesType;

        if (siteUuid != null) {
          setCurrentSite(turbineConfig.sites[siteUuid]);
          navigate(replaceLastPath(pathname, turbineConfig.sites[siteUuid]));
        }
      }}
    ></DailyReport>
  );
};

export default DailyReportContainer;
