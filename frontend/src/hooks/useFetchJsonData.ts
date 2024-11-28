/* eslint-disable @typescript-eslint/no-explicit-any */
import { resetLoading, setLoading } from "@reducers/appAction";
import { ErrorCode } from "@src/error/ErrorCode";
import { ErrorWithCode } from "@src/error/ErrorWithCode";
import { Dispatch } from "react";
import { useDispatch } from "react-redux";
import { NavigateFunction, useNavigate } from "react-router-dom";
import { logout } from "@reducers/userActions";
import Swal, { SweetAlertResult } from "sweetalert2";
import { UnknownAction } from "@reduxjs/toolkit";
import useFetchData from "./useFetchData";

const statusOk = async (response: Response) => {
  if (!response.ok) {
    const data = await response.json();

    throw new ErrorWithCode(data.code, data.message);
  }
};

export const expireSession = (dispatch: Dispatch<UnknownAction>, navigate: NavigateFunction) => {
  dispatch(logout());
  navigate("/login");
};

const useFetchJsonData = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const fetchData = useFetchData();

  const fetchJsonData = async <T>(
    url: string,
    option?: RequestInit,
    errorPopup?: () => Promise<SweetAlertResult<any>>,
  ): Promise<T> => {
    const response = await fetchData(url, option, errorPopup);

    const json = await response.json();

    if (json.data === undefined || json.data === null)
      throw new ErrorWithCode(ErrorCode.INCORRECT_DATA, "not correct fetch data.");

    return json.data as T;
  };

  return fetchJsonData;
};

export default useFetchJsonData;
