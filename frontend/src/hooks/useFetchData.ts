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

const useFetchData = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const fetchData = async <T>(
    url: string,
    option?: RequestInit,
    errorPopup?: () => Promise<SweetAlertResult<any>>,
  ): Promise<T> => {
    dispatch(setLoading());

    let json;
    try {
      const response = await fetch(url, option);
      dispatch(resetLoading());

      if (!response.ok) {
        if (response.status === 401) {
          expireSession(dispatch, navigate);
        }
      }

      json = await response.json();
      if (json.data === undefined || json.data === null)
        throw new ErrorWithCode(ErrorCode.INCORRECT_DATA, "not correct fetch data.");
    } catch (e: any) {
      dispatch(resetLoading());

      if (e instanceof TypeError) {
        //network disconnect
        await Swal.fire({
          title: "서버 연결 실패",
          text: "서버 연결 실패 관리자에게 문의하세요.",
          icon: "error",
        });
        throw new ErrorWithCode(ErrorCode.NETWRORK_DISCONNECT, "network disconnect.");
      } else {
        if (errorPopup) await errorPopup();
      }

      throw e;
    }
    return json.data as T;
  };

  return fetchData;
};

export default useFetchData;
