import React, { useEffect, useState } from "react";
import {
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  getPaginationRowModel,
  useReactTable,
} from "@tanstack/react-table";
import { format } from "date-fns";
import styled from "styled-components";
import { arePropsEmpty } from "@src/utils/props";

//TData
export type AlarmType = {
  timestamp: Date | string;
  alarmNumber: number | string;
  alarmName: string;
  remarks: string;
};

export type AlarmTableProps = {
  alarms: AlarmType[];
};

const columnHelper = createColumnHelper<AlarmType>();

const TableContainer = styled.div`
  width: 100%;
`;
const columns = [
  columnHelper.accessor("timestamp", {
    cell: (info) => info.getValue(),
    header: () => <span>발생 시간</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 200,
  }),
  columnHelper.accessor("alarmNumber", {
    cell: (info) => <i>{info.getValue()}</i>,
    header: () => <span>에러코드</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 100,
  }),
  columnHelper.accessor("alarmName", {
    cell: (info) => info.getValue(),
    header: () => <span>내용</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 450,
  }),
  columnHelper.accessor("remarks", {
    header: () => <span>비고</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 150,
  }),
];

const AlarmTable = React.memo(({ alarms }: AlarmTableProps) => {
  const [alarmData, setAlarmData] = React.useState<AlarmType[]>([] as AlarmType[]);
  const rerender = React.useReducer(() => ({}), {})[1];
  const [pagination, setPagination] = useState({
    pageIndex: 0, //initial page index
    pageSize: 20, //default page size
  });

  useEffect(() => {
    if (alarms.length === 0) {
      alarms.push({ alarmNumber: "", timestamp: "", alarmName: "", remarks: "" } as AlarmType);
    }
    if (!arePropsEmpty(alarms)) setAlarmData([...alarms]);
  }, [alarms]);

  const table = useReactTable({
    data: alarmData,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    onPaginationChange: setPagination,
    columnResizeMode: "onChange",
    columnResizeDirection: "ltr",
    state: {
      pagination,
    },
    defaultColumn: {
      minSize: 50,
    },
  });

  return (
    <TableContainer>
      <table>
        <thead>
          {table.getHeaderGroups().map((headerGroup) => (
            <tr key={headerGroup.id}>
              {headerGroup.headers.map((header) => (
                <th key={header.id} style={{ width: `${header.getSize()}px` }}>
                  {header.isPlaceholder ? null : flexRender(header.column.columnDef.header, header.getContext())}
                  <div
                    className={`resizer ${header.column.getIsResizing() ? "isResizing" : ""}`}
                    onMouseDown={header.getResizeHandler()}></div>
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody>
          {table.getRowModel().rows.map((row) => (
            <tr key={row.id}>
              {row.getVisibleCells().map((cell) => (
                <td key={cell.id}>
                  {(() => {
                    if (cell.getValue() instanceof Date) {
                      return format(cell.getValue() as Date, "yyyy-MM-dd HH:mm:ss");
                    } else if (cell.getContext().column.id === "note") {
                      return <input type="text" style={{ background: "none", border: "none" }} />;
                    } else {
                      return String(cell.getValue());
                    }
                  })()}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          gap: "10px",
          marginTop: "10px",
        }}>
        <button className="border rounded p-1" onClick={() => table.firstPage()} disabled={!table.getCanPreviousPage()}>
          {"<<"}
        </button>
        <button
          className="border rounded p-1"
          onClick={() => table.previousPage()}
          disabled={!table.getCanPreviousPage()}>
          {"<"}
        </button>
        <button className="border rounded p-1" onClick={() => table.nextPage()} disabled={!table.getCanNextPage()}>
          {">"}
        </button>
        <button className="border rounded p-1" onClick={() => table.lastPage()} disabled={!table.getCanNextPage()}>
          {">>"}
        </button>
      </div>
    </TableContainer>
  );
});

export default AlarmTable;
