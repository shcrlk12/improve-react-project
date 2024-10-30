/* eslint-disable @typescript-eslint/no-explicit-any */
import { resetLoading, setLoading } from "@reducers/appAction";
import { ErrorCode } from "@src/error/ErrorCode";
import { ErrorWithCode } from "@src/error/ErrorWithCode";
import { Dispatch } from "react";
import { useDispatch } from "react-redux";
import { NavigateFunction, useNavigate } from "react-router-dom";
import { logout } from "@reducers/userActions";

const statusOk = async (response: Response) => {
  if (!response.ok) {
    const data = await response.json();
    console.log(data);

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

  const fetchData = async <T>(url: string, option?: RequestInit, errorCallback?: (error: any) => void): Promise<T> => {
    dispatch(setLoading());

    try {
      const response = await fetch(url, option);
      if (!response.ok) {
        if (response.status === 401) {
          expireSession(dispatch, navigate);
        }
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const json = await response.json();
      return json.data as T;
    } catch (e: any) {
      if (errorCallback) {
        errorCallback(e);
      } else {
        console.error("Error fetching data:", e);
      }
      throw e; // 에러가 발생했음을 호출한 쪽에서 인지할 수 있게 던짐
    } finally {
      dispatch(resetLoading());
    }
  };

  return fetchData;
};

export default useFetchData;
