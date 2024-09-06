import React from "react";
import {
  Column,
  CompactTable,
} from "@table-library/react-table-library/compact";
import { useTheme } from "@table-library/react-table-library/theme";
import { getTheme } from "@table-library/react-table-library/baseline";
import {
  Data,
  TableNode,
} from "@table-library/react-table-library/types/table";
import { format } from "date-fns";

export type AlarmTableProps = {
  data: Data<TableNode>;
};
// 페이지 네이션 + Editable로 만들자.
const AlarmTable: React.FC<{ alarmTableProps: AlarmTableProps }> = ({
  alarmTableProps,
}) => {
  const [data, setData] = React.useState<Data<TableNode>>(alarmTableProps.data);

  const theme = useTheme(getTheme());

  const COLUMNS: Column<TableNode>[] = [
    {
      label: "발생 시간",
      renderCell: (item) => format(item.time, "yyyy-MM-dd HH:mm:ss"),
      resize: true,
    },
    {
      label: "에러코드",
      renderCell: (item) => item.erorrCode,
      resize: true,
    },
    {
      label: "내용",
      renderCell: (item) => item.errorContent,
      resize: true,
      width: "50px",
    },
    {
      label: "비고",
      renderCell: (item) => (
        <input
          type="text"
          style={{
            width: "100%",
            border: "none",
            fontSize: "1rem",
            padding: 0,
            margin: 0,
          }}
          value={item.note}
          onChange={(event) =>
            handleUpdate(event.target.value, item.id, "note")
          }
        />
      ),
    },
  ];

  const handleUpdate = (value: any, id: any, property: string) => {
    setData((state) => ({
      ...state,
      nodes: state.nodes.map((node) => {
        if (node.id === id) {
          return { ...node, [property]: value };
        } else {
          return node;
        }
      }),
    }));
  };

  return (
    <>
      <div style={{ width: "100%" }}>
        <CompactTable columns={COLUMNS} data={data} theme={theme} />
      </div>
    </>
  );
};

export default AlarmTable;
