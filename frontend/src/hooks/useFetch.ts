import { resetLoading, setLoading } from "@reducers/appAction";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";
import { expireSession } from "./useFetchData";

/**
 * Custom hook that simplifies the usage of the fetch function.
 * Provides utilities and wrappers to perform HTTP requests more easily.
 *
 * @returns {function} improved fetch function .
 *
 * @author Karden
 * @created 2024-07-17
 */
const useFetch = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const fetchData = async (
    url: string,
    option?: RequestInit,
    errorCallback?: (error: any) => void,
  ): Promise<Response> => {
    dispatch(setLoading());

    try {
      const response = await fetch(url, option);
      if (!response.ok) {
        if (response.status === 401) {
          expireSession(dispatch, navigate);
        }
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      return response as Response;
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

export default useFetch;
