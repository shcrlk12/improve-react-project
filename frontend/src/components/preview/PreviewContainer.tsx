import Preview from "./Preview";
import { PowerCurveGraphProps } from "@components/graph/PowerCurveGraph";
import { DailyRWPGraphProps } from "@components/graph/DailyRWPGraph";
import { useEffect, useState } from "react";
import useFetchData from "@src/hooks/useFetchData";
import { config } from "@config/config";
import { TotalOperatingTableProps } from "./table/TotalOperatingTable";
import { DailyOperatingTableProps } from "./table/DailyOperatingTable";
import { JsonApi } from "@src/jsonApiOrg/JsonApiOrg";
import { AlarmTableV2Props, AlarmType } from "./table/AlarmTableV2";
import { EventBoxNote } from "./EventTextBox";

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
  AlarmTableV2Props;

const PreviewContainer = () => {
  console.log("PreviewContainer");
  const fetchData = useFetchData();

  const [dailyRWPGraphProps, setDailyRWPGraphProps] = useState<DailyRWPGraphProps>({} as DailyRWPGraphProps);
  const [powerCurveGraphProps, setPowerCurveGraphProps] = useState<PowerCurveGraphProps>({} as PowerCurveGraphProps);
  const [dailyOperatingTableProps, setDailyOperatingTableProps] = useState<DailyOperatingTableProps>(
    {} as DailyOperatingTableProps,
  );
  const [totalOperatingTableProps, setTotalOperatingTableProps] = useState<TotalOperatingTableProps>(
    {} as TotalOperatingTableProps,
  );
  const [date, setDate] = useState<string>("");
  const [alarms, setAlarms] = useState<AlarmType[]>([] as AlarmType[]);
  const [eventBoxNotes, setEventBoxNotes] = useState<EventBoxNote[]>([] as EventBoxNote[]);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData<JsonApi<ResponseOfReportU151>>(
          `http://${config.apiServer.ip}:${config.apiServer.port}/api/data/report/u151`,
          {
            mode: "cors",
            method: "GET",
            credentials: "include",
          },
        );
        console.log(data.attributes);

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
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, []);

  return (
    <Preview
      date={date}
      dailyOperatingTableProps={dailyOperatingTableProps}
      totalOperatingTableProps={totalOperatingTableProps}
      powerCurveGraphProps={powerCurveGraphProps}
      dailyRWPGraphProps={dailyRWPGraphProps}
      eventBoxNotesProps={eventBoxNotes}
      alarmsProps={alarms}
      setEventBoxNotes={setEventBoxNotes}
    />
  );
};

export default PreviewContainer;
