import { resetLoading, setLoading } from "@reducers/appAction";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";
import { expireSession } from "./useFetchJsonData";
import { ErrorWithCode } from "@src/error/ErrorWithCode";
import { ErrorCode } from "@src/error/ErrorCode";
import Swal, { SweetAlertResult } from "sweetalert2";

/**
 * Custom hook that simplifies the usage of the fetch function.
 * Provides utilities and wrappers to perform HTTP requests more easily.
 *
 * @returns {function} improved fetch function .
 *
 * @author Karden
 * @created 2024-07-17
 */
const useFetchData = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const fetchData = async (
    url: string,
    option?: RequestInit,
    errorPopup?: () => Promise<SweetAlertResult<any>>,
  ): Promise<Response> => {
    dispatch(setLoading());

    try {
      const response = await fetch(url, option);
      if (!response.ok) {
        if (response.status === 401) {
          expireSession(dispatch, navigate);
        } else if (response.status === 400)
          throw new ErrorWithCode(ErrorCode.BAD_REQUEST, `HTTP error! status: ${response.status}`);
      }

      return response as Response;
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
    } finally {
      dispatch(resetLoading());
    }
  };

  return fetchData;
};

export default useFetchData;
