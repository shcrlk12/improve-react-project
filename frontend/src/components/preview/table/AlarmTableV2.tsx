import React, { useState } from "react";
import {
  ColumnDef,
  createColumnHelper,
  flexRender,
  getCoreRowModel,
  getPaginationRowModel,
  useReactTable,
} from "@tanstack/react-table";
import { format } from "date-fns";
import styled from "styled-components";

//TData
export type Error = {
  time: Date;
  erorrCode: number;
  errorContent: string;
  note: string;
};

const defaultData: Error[] = [
  {
    time: new Date(2024, 7, 23, 0, 56, 23),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 1, 0, 48),
    erorrCode: 20027,
    errorContent: "Cable twist 2 turns CW",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 2, 56, 56),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 3, 45, 12),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 24),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake storage pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 25),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake main pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 23, 11, 33),
    erorrCode: 20026,
    errorContent: "Yaw misalignment high",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 0, 56, 23),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 1, 0, 48),
    erorrCode: 20027,
    errorContent: "Cable twist 2 turns CW",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 2, 56, 56),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 3, 45, 12),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 24),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake storage pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 25),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake main pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 23, 11, 33),
    erorrCode: 20026,
    errorContent: "Yaw misalignment high",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 0, 56, 23),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 1, 0, 48),
    erorrCode: 20027,
    errorContent: "Cable twist 2 turns CW",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 2, 56, 56),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 3, 45, 12),
    erorrCode: 20011,
    errorContent: "Low wind speed",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 24),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake storage pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 11, 7, 25),
    erorrCode: 20011,
    errorContent: "HYD. Rotor brake main pressure low",
    note: "",
  },
  {
    time: new Date(2024, 7, 23, 23, 11, 33),
    erorrCode: 20026,
    errorContent: "Yaw misalignment high",
    note: "",
  },
];

const columnHelper = createColumnHelper<Error>();

// const defaultColumns: ColumnDef<Error>[] = [
//   {
//     header: "에러 발생 현황",
//     footer: (props) => props.column.id,
//     columns: [
//       {
//         accessorKey: "time",
//         header: () => <span>발생 시간</span>,
//         cell: (info) => info.getValue(),
//         footer: (props) => props.column.id,
//         enableResizing: true,
//         size: 500,
//       },
//       {
//         accessorKey: "erorrCode",
//         header: () => <span>발생 시간</span>,
//         cell: (info) => info.getValue(),
//         footer: (props) => props.column.id,
//       },
//       {
//         accessorKey: "errorContent",
//         header: () => <span>내용</span>,
//         cell: (info) => info.getValue(),
//         footer: (props) => props.column.id,
//       },
//       {
//         accessorKey: "note",
//         header: () => <span>비고</span>,
//         cell: (info) => info.getValue(),
//         footer: (props) => props.column.id,
//       },
//     ],
//   },
// ];

const TableContainer = styled.div`
  width: 100%;
`;
const columns = [
  columnHelper.accessor("time", {
    cell: (info) => info.getValue(),
    header: () => <span>발생 시간</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 200,
  }),
  columnHelper.accessor("erorrCode", {
    cell: (info) => <i>{info.getValue()}</i>,
    header: () => <span>에러코드</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 100,
  }),
  columnHelper.accessor("errorContent", {
    cell: (info) => info.getValue(),
    header: () => <span>내용</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 450,
  }),
  columnHelper.accessor("note", {
    header: () => <span>비고</span>,
    footer: (info) => info.column.id,
    enableResizing: true,
    size: 150,
  }),
];

const AlarmTableV2 = () => {
  const [data, _setData] = React.useState<Error[]>(() => [...defaultData]);
  const rerender = React.useReducer(() => ({}), {})[1];
  const [pagination, setPagination] = useState({
    pageIndex: 0, //initial page index
    pageSize: 20, //default page size
  });

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    onPaginationChange: setPagination, //update the pagination state when internal APIs mutate the pagination state
    columnResizeMode: "onChange",
    columnResizeDirection: "ltr",
    state: {
      pagination,
    },
    defaultColumn: {
      minSize: 50, //enforced during column resizing
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
                  {header.isPlaceholder
                    ? null
                    : flexRender(
                        header.column.columnDef.header,
                        header.getContext()
                      )}
                  <div
                    className={`resizer ${header.column.getIsResizing() ? "isResizing" : ""}`}
                    onMouseDown={header.getResizeHandler()}
                  ></div>
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
                      return format(
                        cell.getValue() as Date,
                        "yyyy-MM-dd HH:mm:ss"
                      );
                    } else if (cell.getContext().column.id === "note") {
                      return (
                        <input
                          type="text"
                          style={{ background: "none", border: "none" }}
                        />
                      );
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
        }}
      >
        <button
          className="border rounded p-1"
          onClick={() => table.firstPage()}
          disabled={!table.getCanPreviousPage()}
        >
          {"<<"}
        </button>
        <button
          className="border rounded p-1"
          onClick={() => table.previousPage()}
          disabled={!table.getCanPreviousPage()}
        >
          {"<"}
        </button>
        <button
          className="border rounded p-1"
          onClick={() => table.nextPage()}
          disabled={!table.getCanNextPage()}
        >
          {">"}
        </button>
        <button
          className="border rounded p-1"
          onClick={() => table.lastPage()}
          disabled={!table.getCanNextPage()}
        >
          {">>"}
        </button>
      </div>
    </TableContainer>
  );
};

export default AlarmTableV2;
