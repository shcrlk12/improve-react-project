import Preview from "./Preview";
import { PowerCurveGraphProps } from "@components/graph/PowerCurveGraph";
import { DailyRWPGraphProps } from "@components/graph/DailyRWPGraph";
import { useEffect, useState } from "react";
import useFetchJsonData from "@src/hooks/useFetchJsonData";
import { config, getRestApiServerUrl } from "@config/config";
import { TotalOperatingTableProps } from "./table/TotalOperatingTable";
import { DailyOperatingTableProps } from "./table/DailyOperatingTable";
import { JsonApi, jsonOrgConfig } from "@src/jsonApiOrg/JsonApiOrg";
import { AlarmTableProps, AlarmType } from "./table/AlarmTable";
import { EventBoxNote } from "./EventTextBox";
import { SiteType } from "@reducers/appAction";
import Swal from "sweetalert2";

/**
 * Types
 */

type ResponseOfReportU151 = {
  date: string;
  powerCurve: never;
  eventBoxNotes: EventBoxNote[];
} & DailyOperatingTableProps &
  TotalOperatingTableProps &
  PowerCurveGraphProps &
  DailyRWPGraphProps &
  AlarmTableProps;

type PreviewContainerProps = {
  key: number;
  selectedSite: SiteType;
  selectedDate: Date;
};
const PreviewContainer = ({ key, selectedSite, selectedDate }: PreviewContainerProps) => {
  const fetchData = useFetchJsonData();
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [dailyRWPGraphProps, setDailyRWPGraphProps] = useState<DailyRWPGraphProps>({} as DailyRWPGraphProps);
  const [powerCurveGraphProps, setPowerCurveGraphProps] = useState<PowerCurveGraphProps>({} as PowerCurveGraphProps);
  const [dailyOperatingTableProps, setDailyOperatingTableProps] = useState<DailyOperatingTableProps>(
    {} as DailyOperatingTableProps
  );
  const [totalOperatingTableProps, setTotalOperatingTableProps] = useState<TotalOperatingTableProps>(
    {} as TotalOperatingTableProps
  );
  const [date, setDate] = useState<string>("");
  const [alarms, setAlarms] = useState<AlarmType[]>([] as AlarmType[]);
  const [eventBoxNotes, setEventBoxNotes] = useState<EventBoxNote[]>([] as EventBoxNote[]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData<JsonApi<ResponseOfReportU151>>(
          getRestApiServerUrl(`/reports/${selectedSite.uuid}/daily?writeDate=${selectedDate.toISOString()}`),
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
            })
        );

        setDailyOperatingTableProps(data.attributes as DailyOperatingTableProps);
        setTotalOperatingTableProps(data.attributes as TotalOperatingTableProps);
        setDate(data.attributes.date);

        setAlarms(data.attributes.alarms.map((alarm) => ({ ...alarm, timestamp: new Date(alarm.timestamp) })));

        setPowerCurveGraphProps((prev) => ({
          ...prev,
          powerCurveScatter: data.attributes.powerCurveScatter,
          referencePowerCurve: data.attributes.referencePowerCurve,
        }));

        setDailyRWPGraphProps((prev) => ({
          ...prev,
          timeChart: data.attributes.timeChart,
        }));

        setEventBoxNotes(data.attributes.eventBoxNotes);
        setIsLoading(true);
      } catch (error) {}
    };

    fetchDataAsync();
  }, [key]);

  useEffect(() => {
    setDate(selectedDate.toString());
  }, [selectedDate]);

  return (
    isLoading && (
      <Preview
        selectedSite={selectedSite}
        date={date}
        dailyOperatingTableProps={dailyOperatingTableProps}
        totalOperatingTableProps={totalOperatingTableProps}
        powerCurveGraphProps={powerCurveGraphProps}
        dailyRWPGraphProps={dailyRWPGraphProps}
        eventBoxNotesProps={eventBoxNotes}
        alarmsProps={alarms}
        setEventBoxNotes={setEventBoxNotes}
      />
    )
  );
};

export default PreviewContainer;
