import React, { ChangeEvent } from "react";

type EventTextBox = {
  title: string;
  content?: string | null | undefined;
  setContent?: (event: ChangeEvent<HTMLTextAreaElement>) => void;
};
const EventTextBox = ({ title, content, setContent }: EventTextBox) => {
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
        onChange={setContent}
      ></textarea>
    </div>
  );
};

export default EventTextBox;
