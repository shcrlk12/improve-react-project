import React, { ChangeEvent } from "react";

export type EventBoxNote = {
  title: string;
  content?: string | null | undefined;
};
type EventTextBoxProps = {
  setContent?: (event: ChangeEvent<HTMLTextAreaElement>) => void;
} & EventBoxNote;

const EventTextBox = ({ title, content, setContent }: EventTextBoxProps) => {
  return (
    <div>
      <div style={{ fontSize: "16px", fontWeight: "600" }}>{title}</div>
      <textarea
        name="text"
        id={title}
        style={{ fontSize: "16px", padding: "4px", marginTop: "6px" }}
        cols={75}
        rows={6}
        value={content ? content : "특이사항 없음."}
        onChange={setContent}></textarea>
    </div>
  );
};

export default EventTextBox;
