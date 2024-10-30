import DailyRWPGraph, { DailyRWPGraphProps } from "@components/graph/DailyRWPGraph";
import PowerCurveGraph, { PowerCurveGraphProps } from "@components/graph/PowerCurveGraph";
import DailyOperatingTable, { DailyOperatingTableProps } from "@components/preview/table/DailyOperatingTable";
import TotalOperatingTable, { TotalOperatingTableProps } from "./table/TotalOperatingTable";
import AlarmTableV2, { AlarmType } from "./table/AlarmTableV2";
import EventTextBox, { EventBoxNote } from "./EventTextBox";
import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { ChangeEvent, SetStateAction, useRef } from "react";
import html2canvas from "html2canvas";
import { format } from "date-fns";

export type PreviewProps = {
  type: string;
  date: string;
  dailyOperatingTableProps: DailyOperatingTableProps;
  totalOperatingTableProps: TotalOperatingTableProps;
  powerCurveGraphProps: PowerCurveGraphProps;
  dailyRWPGraphProps: DailyRWPGraphProps;
  alarmsProps: AlarmType[];
  eventBoxNotesProps: EventBoxNote[];
  setEventBoxNotes: React.Dispatch<SetStateAction<EventBoxNote[]>>;
  //   alarmTableProps: Error[];
};

/**
 * Styled
 */

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  justify-content: center;
  align-content: center;
`;
const PreviewTitle = styled.div`
  text-align: center;
  font-size: ${({ theme }) => theme.font.size.xxLarge};
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;

const ReportDownloadContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Preview = ({
  type,
  date,
  dailyOperatingTableProps,
  totalOperatingTableProps,
  powerCurveGraphProps,
  dailyRWPGraphProps,
  eventBoxNotesProps,
  alarmsProps,
  setEventBoxNotes,
  //   alarmTableProps,
}: PreviewProps) => {
  const powerCurveGraphRef = useRef<HTMLDivElement>(null);
  const dailyRWPGraphRef = useRef<HTMLDivElement>(null);

  let formattedDate;

  if (date !== "") {
    formattedDate = format(date, "yyyy-MM-dd");
  }
  console.log(dailyRWPGraphProps);
  console.log("alarmsProps");
  console.log(alarmsProps);

  return (
    <>
      <Container>
        <PreviewTitle>U151 Daily report [{formattedDate}]</PreviewTitle>
        <DailyOperatingTable dailyOperatingTableProps={dailyOperatingTableProps} />
        <TotalOperatingTable totalOperatingTableProps={totalOperatingTableProps} />
        <PowerCurveGraph
          ref={powerCurveGraphRef}
          title={"Power curve(`24.09-`24.12)"}
          referencePowerCurve={powerCurveGraphProps.referencePowerCurve}
          powerCurveScatter={powerCurveGraphProps.powerCurveScatter}
        />
        <DailyRWPGraph ref={dailyRWPGraphRef} title={"Time chart(24/9/02)"} timeChart={dailyRWPGraphProps.timeChart} />
        <AlarmTableV2 alarms={alarmsProps} />
        {eventBoxNotesProps.map((eventText, index) => (
          <EventTextBox
            title={`${index + 1}. ${eventText.title}`}
            content={eventText.content}
            setContent={(event: ChangeEvent<HTMLTextAreaElement>) => {
              const title = event.currentTarget.id;
              const content = event.currentTarget.value;

              setEventBoxNotes((prev) => prev.map((data) => (data.title === title ? { ...data, content } : data)));
            }}
          />
        ))}
        <ReportDownloadContainer>
          <PrimaryButton
            text={"리포트 다운로드"}
            onClick={() => {
              html2canvas(powerCurveGraphRef.current!).then((canvas) => {
                canvas.toBlob((blob) => {
                  const formData = new FormData();
                  formData.append("file", blob!, "capture.png");

                  fetch("/upload-image", {
                    method: "POST",
                    body: formData,
                  })
                    .then((response) => response.json())
                    .then((data) => {
                      console.log("Image uploaded successfully", data);
                    })
                    .catch((error) => {
                      console.error("Error uploading image", error);
                    });
                });

                const link = document.createElement("a");
                link.href = canvas.toDataURL();
                link.download = "graph.png";
                link.click();
              });

              html2canvas(dailyRWPGraphRef.current!).then((canvas) => {
                const link = document.createElement("a");
                link.href = canvas.toDataURL();
                link.download = "graph.png";
                link.click();
              });
            }}
            width="150px"
          />
        </ReportDownloadContainer>
      </Container>
    </>
  );
};

export default Preview;
