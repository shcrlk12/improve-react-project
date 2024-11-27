import React, { ChangeEvent } from "react";
import { constants } from "./../../constants";

export type EventBoxNote = {
  title: string;
  content?: string | null | undefined;
  uuid?: string;
};
type EventTextBoxProps = {
  setContent?: (event: ChangeEvent<HTMLTextAreaElement>) => void;
} & EventBoxNote;

const EventTextBox = ({ title, content, uuid, setContent }: EventTextBoxProps) => {
  return (
    <div>
      <div style={{ fontSize: "16px", fontWeight: "600" }}>{title}</div>
      <textarea
        name="text"
        id={uuid == "" ? constants.defaultUuid + constants.seperator + title : uuid + constants.seperator + title}
        style={{ fontSize: "16px", padding: "4px", marginTop: "6px" }}
        cols={75}
        rows={6}
        value={content ? content : ""}
        onChange={setContent}></textarea>
    </div>
  );
};

export default EventTextBox;
