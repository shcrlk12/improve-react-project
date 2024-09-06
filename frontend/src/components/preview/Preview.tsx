import DailyRWPGraph, {
  DailyRWPGraphProps,
} from "@components/graph/DailyRWPGraph";
import PowerCurveGraph, {
  PowerCurveGraphProps,
} from "@components/graph/PowerCurveGraph";
import DailyOperatingTable, {
  DailyOperatingTableProps,
} from "@components/preview/table/DailyOperatingTable";
import TotalOperatingTable, {
  TotalOperatingTableProps,
} from "./table/TotalOperatingTable";
import AlarmTable, { AlarmTableProps } from "./table/AlarmTable";

export type PreviewProps = {
  dailyOperatingTableProps: DailyOperatingTableProps;
  totalOperatingTableProps: TotalOperatingTableProps;
  powerCurveGraphProps: PowerCurveGraphProps;
  dailyRWPGraphProps: DailyRWPGraphProps;
  alarmTableProps: AlarmTableProps;
};
const Preview = ({
  dailyOperatingTableProps,
  totalOperatingTableProps,
  powerCurveGraphProps,
  dailyRWPGraphProps,
  alarmTableProps,
}: PreviewProps) => {
  return (
    <>
      <AlarmTable alarmTableProps={alarmTableProps} />
      <DailyOperatingTable
        dailyOperatingTableProps={dailyOperatingTableProps}
      />
      <TotalOperatingTable
        totalOperatingTableProps={totalOperatingTableProps}
      />
      <PowerCurveGraph
        referenceCurve={powerCurveGraphProps.referenceCurve}
        scatter={powerCurveGraphProps.scatter}
      />
      <DailyRWPGraph
        dailyRWPGraphProps={dailyRWPGraphProps.dailyRWPGraphProps}
      />
    </>
  );
};

export default Preview;
