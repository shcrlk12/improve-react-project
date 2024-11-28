import { MouseEvent, useEffect, useState } from "react";
import DailyReport from "./DailyReport";
import { useLocation, useNavigate } from "react-router";
import { replaceLastPath } from "@src/utils/path";
import { selectSite, setSites, SiteType } from "@reducers/appAction";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@src/main";
import useFetchData from "@src/hooks/useFetchData";
import { JsonApi, jsonOrgConfig } from "@src/jsonApiOrg/JsonApiOrg";
import { config, getRestApiServerUrl } from "@config/config";
import Swal from "sweetalert2";
import { UserRoleType } from "@config/userRole";

type SiteTypeDto = {
  name: string;
  remark: string;
  ratedPower: number;
};

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
  const fetchData = useFetchData();
  const dispatch = useDispatch();
  const userRole = useSelector((store: RootState) => store.userReducer.user.role) as UserRoleType;

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData<Array<JsonApi<SiteTypeDto>>>(
          getRestApiServerUrl(`/data/sites`),
          {
            mode: "cors",
            method: "GET",
            credentials: "include",
            headers: { "Content-Type": jsonOrgConfig.CONTENT_TYPE, Accept: jsonOrgConfig.ACCEPT },
          },
          () =>
            Swal.fire({
              title: "데이터 에러",
              text: "서버 데이터 에러 관리자에게 문의하세요.",
              icon: "error",
            }),
        );

        const sites: SiteType[] = data.map(
          (item) =>
            ({
              uuid: item.id,
              name: item.attributes.name.toUpperCase(),
              remark: item.attributes.remark,
              ratedPower: item.attributes.ratedPower,
            }) as SiteType,
        );

        dispatch(setSites(sites));
        dispatch(selectSite(sites[0]));
      } catch (error) {}
    };

    fetchDataAsync();
  }, [userRole]);

  return (
    <DailyReport
      sites={sites}
      selectedSite={selectedSite}
      selectedDate={selectedDate}
      setSelectedDate={setSelectedDate}></DailyReport>
  );
};

export default DailyReportContainer;
