import DailyRWPGraph, { DailyRWPGraphProps } from "@components/graph/DailyRWPGraph";
import PowerCurveGraph, { PowerCurveGraphProps } from "@components/graph/PowerCurveGraph";
import DailyOperatingTable, { DailyOperatingTableProps } from "@components/preview/table/DailyOperatingTable";
import TotalOperatingTable, { TotalOperatingTableProps } from "./table/TotalOperatingTable";
import AlarmTableV2, { AlarmType } from "./table/AlarmTableV2";
import EventTextBox, { EventBoxNote } from "./EventTextBox";
import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { ChangeEvent, SetStateAction, useEffect, useRef, useState } from "react";
import html2canvas from "html2canvas";
import { endOfQuarter, format, startOfQuarter } from "date-fns";
import { SiteType } from "@reducers/appAction";
import { config } from "@config/config";
import useFetch from "@src/hooks/useFetch";
import { ACCEPT, CONTENT_TYPE, createPostRequestsObject, JsonApiRequestsPost } from "@src/jsonApiOrg/JsonApiOrg";
import { constants } from "@src/constants";
import Swal from "sweetalert2";

export type PreviewProps = {
  selectedSite: SiteType;
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
  selectedSite,
  date,
  dailyOperatingTableProps,
  totalOperatingTableProps,
  powerCurveGraphProps,
  dailyRWPGraphProps,
  eventBoxNotesProps,
  alarmsProps,
  setEventBoxNotes,
}: PreviewProps) => {
  const [eventBoxNote, setEventBoxNote] = useState<EventBoxNote[]>([...eventBoxNotesProps]);

  const powerCurveGraphRef = useRef<HTMLDivElement>(null);
  const dailyRWPGraphRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    setEventBoxNote([...eventBoxNotesProps]);
  }, [eventBoxNotesProps]);

  const fetchData = useFetch();
  let formattedDate, formattedTimechartDate, formattedPowerCurveDate;

  if (date !== "") {
    formattedDate = format(date, "yyyy-MM-dd");
    formattedTimechartDate = format(date, "(yy/MM/dd)");
    formattedPowerCurveDate =
      "(" + format(startOfQuarter(date), "`yy.MM") + "-" + format(endOfQuarter(date), "`yy.MM") + ")";
  }

  const ratedPower = selectedSite.ratedPower;

  const downloadReport = async () => {
    const result = await Swal.fire({
      title: "일일 리포트 다운",
      text: "일일 리포트를 다운로드 받으시겠습니까?",
      icon: "info",
      showCancelButton: true,
      confirmButtonText: "확인",
      cancelButtonText: "취소",
    });

    if (!result.isConfirmed) return;

    const powerCurveBlob = await generateBlobFromCanvas(powerCurveGraphRef);
    const dailyRWPBlob = await generateBlobFromCanvas(dailyRWPGraphRef);

    const formData = new FormData();

    formData.append("powerCurve", powerCurveBlob!);
    formData.append("timeChart", dailyRWPBlob!);
    formData.append("turbineUuid", selectedSite.uuid);
    formData.append("writeDate", date);
    formData.append("siteName", selectedSite.name);

    const remarksResponse = await sendRemarksRequest();

    const reportResponse = await generateReport(formData);
    downloadFile(reportResponse);
  };

  const generateBlobFromCanvas = (ref: React.RefObject<HTMLDivElement>): Blob => {
    return new Promise((resolve) => {
      html2canvas(ref.current!).then((canvas) => {
        canvas.toBlob((blob) => resolve(blob));
      });
    });
  };

  const sendRemarksRequest = async () => {
    let request = createPostRequestsObject<EventBoxNote & { turbineUuid: string; timestamp: string }>();
    let isCreate = false;

    let newEventBoxNotes: EventBoxNote[] = [];

    eventBoxNote.forEach((item) => {
      let uuid;
      if (item.uuid === constants.defaultUuid) {
        isCreate = true;
        uuid = crypto.randomUUID();
      } else {
        uuid = item.uuid;
      }

      newEventBoxNotes.push({ title: item.title, content: item.content, uuid });

      request.data.push({
        id: uuid,
        type: "remarks",
        attributes: { content: item.content, title: item.title, turbineUuid: selectedSite.uuid, timestamp: date },
      });
    });
    setEventBoxNote(newEventBoxNotes);

    let httpMethod;

    if (isCreate) {
      httpMethod = "POST";
    } else {
      httpMethod = "PATCH";
    }

    const response = await fetchData(`http://${config.apiServer.ip}:${config.apiServer.port}/api/data/remarks`, {
      method: httpMethod,
      credentials: "include",
      body: JSON.stringify(request),
      headers: { "Content-Type": CONTENT_TYPE, Accept: ACCEPT },
    });
    return response;
  };

  const generateReport = (formData: FormData) => {
    return fetchData(`http://${config.apiServer.ip}:${config.apiServer.port}/api/docx/daily-report`, {
      method: "POST",
      credentials: "include",
      body: formData,
    });
  };

  const downloadFile = async (response: Response) => {
    const disposition = response.headers.get("Content-Disposition");
    let filename = "default.docx";
    if (disposition && disposition.includes("filename=")) {
      filename = disposition.split("filename=")[1].replace(/"/g, "");
    }

    const wordBlob = await response.blob();
    const url = window.URL.createObjectURL(wordBlob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
  };

  return (
    <>
      <Container>
        <PreviewTitle>
          {selectedSite.name} Daily report [{formattedDate}]
        </PreviewTitle>
        <DailyOperatingTable dailyOperatingTableProps={{ ...dailyOperatingTableProps, ratedPower }} />
        <TotalOperatingTable totalOperatingTableProps={{ ...totalOperatingTableProps, ratedPower }} />
        <PowerCurveGraph
          ref={powerCurveGraphRef}
          title={`Power curve${formattedPowerCurveDate}`}
          referencePowerCurve={powerCurveGraphProps.referencePowerCurve}
          powerCurveScatter={powerCurveGraphProps.powerCurveScatter}
          ratedPower={ratedPower}
        />
        <DailyRWPGraph
          ref={dailyRWPGraphRef}
          title={`Time chart${formattedTimechartDate}`}
          timeChart={dailyRWPGraphProps.timeChart}
          ratedPower={ratedPower}
        />
        <AlarmTableV2 alarms={alarmsProps} />
        {eventBoxNote.map((eventText, index) => (
          <EventTextBox
            title={index + 1 + ". " + eventText.title}
            content={eventText.content}
            uuid={eventText.uuid}
            setContent={(event: ChangeEvent<HTMLTextAreaElement>) => {
              event.preventDefault();

              const content = event.currentTarget.value;
              setEventBoxNote((prev) => prev.map((data, idx) => (index === idx ? { ...data, content } : data)));
            }}
          />
        ))}
        <ReportDownloadContainer>
          <PrimaryButton text={"리포트 다운로드"} onClick={downloadReport} width="150px" />
        </ReportDownloadContainer>
      </Container>
    </>
  );
};

export default Preview;
