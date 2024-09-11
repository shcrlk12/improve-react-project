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
import AlarmTableV2 from "./table/AlarmTableV2";
import EventTextBox from "./EventTextBox";
import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { ChangeEvent, useRef } from "react";
import html2canvas from "html2canvas";

type EventTextBoxProps = {
  title: string;
  content?: string;
};

export type PreviewProps = {
  dailyOperatingTableProps: DailyOperatingTableProps;
  totalOperatingTableProps: TotalOperatingTableProps;
  powerCurveGraphProps: PowerCurveGraphProps;
  dailyRWPGraphProps: DailyRWPGraphProps;
  eventTextBoxProps: EventTextBoxProps[];
  setEventTextBoxProps: any;
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
  dailyOperatingTableProps,
  totalOperatingTableProps,
  powerCurveGraphProps,
  dailyRWPGraphProps,
  eventTextBoxProps,
  setEventTextBoxProps,
  //   alarmTableProps,
}: PreviewProps) => {
  const powerCurveGraphRef = useRef<HTMLDivElement>(null);
  const dailyRWPGraphRef = useRef<HTMLDivElement>(null);

  return (
    <>
      <Container>
        <PreviewTitle>U151 Daily report [2024-09-03]</PreviewTitle>
        <DailyOperatingTable
          dailyOperatingTableProps={dailyOperatingTableProps}
        />
        <TotalOperatingTable
          totalOperatingTableProps={totalOperatingTableProps}
        />
        <PowerCurveGraph
          ref={powerCurveGraphRef}
          title={"Power curve(`24.09-`24.12)"}
          referenceCurve={powerCurveGraphProps.referenceCurve}
          scatter={powerCurveGraphProps.scatter}
        />
        <DailyRWPGraph
          ref={dailyRWPGraphRef}
          title={"Time chart(24/9/02)"}
          dailyRWPGraphProps={dailyRWPGraphProps.dailyRWPGraphProps}
        />
        <AlarmTableV2 />
        {eventTextBoxProps.map((eventText) => (
          <EventTextBox
            title={eventText.title}
            content={eventText.content}
            setContent={(event: ChangeEvent<HTMLTextAreaElement>) => {
              const title = event.currentTarget.id;
              const content = event.currentTarget.value;

              setEventTextBoxProps((prev: EventTextBoxProps[]) =>
                prev.map((data) =>
                  data.title === title ? { ...data, content } : data
                )
              );
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
