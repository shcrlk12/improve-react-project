import {
  Box,
  IconButton,
  Table,
  TableBody,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import { useEffect, useState } from "react";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import styled from "styled-components";
import { TablePaginationActionsProps } from "@mui/material/TablePagination/TablePaginationActions";
import { KeyboardArrowLeft, KeyboardArrowRight } from "@mui/icons-material";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import LastPageIcon from "@mui/icons-material/LastPage";
import Paper from "@mui/material/Paper";
import { UserOfRequest } from "../detail/UserDetail";
import { ACCEPT, CONTENT_TYPE, JsonApi } from "@src/jsonApiOrg/JsonApiOrg";
import { UserRoleType, UserType } from "@config/userRole";
import DangerButton from "./../../../../node_modules/@karden/utils/button/DangerButton";
import { PrimaryButton } from "@karden/utils/button";
import { useNavigate } from "react-router";
import { routes } from "@config/routes";
import useFetch from "@src/hooks/useFetch";
import { config } from "@config/config";
import Swal from "sweetalert2";

/**
 * Styles
 */
const StyledTableCell = styled(TableCell)({
  [`&.${tableCellClasses.head}`]: {
    fontWeight: "bold",
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
});

function TablePaginationActions(props: TablePaginationActionsProps) {
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };
  const handlePageButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    onPageChange(event, Number(event.currentTarget.innerHTML.split("<")[0]) - 1);
  };

  return (
    <Box sx={{ flex: "1 0 560px", ml: "40px" }}>
      <IconButton onClick={handleFirstPageButtonClick} disabled={page === 0} aria-label="first page">
        <FirstPageIcon />
      </IconButton>
      <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="previous page">
        <KeyboardArrowLeft />
      </IconButton>
      {/* {(() => {})()} */}
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page">
        <KeyboardArrowRight />
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page">
        <LastPageIcon />
      </IconButton>
    </Box>
  );
}

/**
 * Types
 */
type UserListProps = {
  users: Array<JsonApi<UserOfRequest>>;
};

const UserListGap = styled.div`
  margin-top: 20px;
`;

const UserListTableContainer = styled.div`
  width: 1200px;
  margin: 0 auto;
`;

/**
 * UserList Component
 *
 * This component render the user list table
 * and it is able to delete or edit user detail.
 *
 * @author Karden
 * @created 2024-07-19
 */
const UserList = ({ users }: UserListProps) => {
  const navigate = useNavigate();
  const fetchData = useFetch();

  const [page, setPage] = useState(0);
  const [userList, setUserList] = useState<Array<UserType>>([] as Array<UserType>);

  const [rowsPerPage, setRowsPerPage] = useState(5);

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - userList.length) : 0;

  const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    setRowsPerPage(parseInt(event.target?.value, 10));
    setPage(0);
  };

  useEffect(() => {
    const newUserList = users.map((user) => {
      let userDetail = {} as UserType;
      userDetail.id = user.id;
      userDetail.name = user.attributes.name;
      userDetail.userRole = user.attributes.role as UserRoleType;

      return userDetail;
    });

    setUserList(newUserList);
  }, [users]);

  return (
    <>
      <UserListGap></UserListGap>
      <UserListTableContainer>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <StyledTableCell align="center">Id</StyledTableCell>
                <StyledTableCell align="center">Name</StyledTableCell>
                <StyledTableCell align="center">Role</StyledTableCell>
                <StyledTableCell align="center">Delete</StyledTableCell>
                <StyledTableCell align="center">Edit</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0 ? userList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : userList).map(
                (row) => (
                  <TableRow key={row.id}>
                    <StyledTableCell style={{ width: 250 }} align="center">
                      {row.id}
                    </StyledTableCell>
                    <StyledTableCell style={{ width: 150 }} align="center">
                      {row.name}
                    </StyledTableCell>
                    <StyledTableCell style={{ width: 100 }} align="center">
                      {row.userRole.substring(5).toLowerCase()}
                    </StyledTableCell>
                    <StyledTableCell style={{ width: 122 }} align="center">
                      <DangerButton
                        text="삭제"
                        onClick={async () => {
                          const result = await Swal.fire({
                            title: "유저 삭제",
                            text: row.id + " 유저를 삭제 하시겠습니까?",
                            icon: "info",
                            showCancelButton: true,
                            confirmButtonText: "확인",
                            cancelButtonText: "취소",
                          });

                          if (!result.isConfirmed) return;

                          try {
                            await fetchData(
                              `${config.apiServer.protocol}://${config.apiServer.ip}:${config.apiServer.port}/api/users/${row.id}`,
                              {
                                method: "DELETE",
                                credentials: "include",
                                headers: { "Content-Type": CONTENT_TYPE, Accept: ACCEPT },
                              },
                            );

                            await Swal.fire({
                              title: "유저 삭제",
                              text: row.id + " 유저 삭제 완료",
                              icon: "success",
                            });

                            const newUserList = userList.filter((user) => user.id !== row.id);
                            setUserList(newUserList);
                          } catch (e) {}
                        }}
                      />
                    </StyledTableCell>
                    <StyledTableCell style={{ width: 122 }} align="center">
                      <PrimaryButton
                        text="수정"
                        onClick={() => {
                          navigate(
                            routes.USER.MODIFY.INDEX.substring(0, routes.USER.MODIFY.INDEX.length - 2) + "/" + row.id,
                          );
                        }}
                      />
                    </StyledTableCell>
                  </TableRow>
                ),
              )}
              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <StyledTableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
                  colSpan={6}
                  count={userList.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  labelRowsPerPage="Table Rows"
                  labelDisplayedRows={({ from, to, count }) => `${from} - ${to} / ${count}`}
                  onPageChange={handleChangePage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                  ActionsComponent={TablePaginationActions}
                />
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>
      </UserListTableContainer>
    </>
  );
};

export default UserList;
