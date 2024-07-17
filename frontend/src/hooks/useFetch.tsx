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
  /**
   * @todo deal with error handling process
   */
  return async function fetchData(
    input: string | URL | globalThis.Request,
    init?: RequestInit
  ): Promise<Response> {
    try {
      const response = await fetch(input, init);

      if (response.status >= 200 && response.status < 300) {
        return response;
      } else {
        throw new Error(`HTTP error`);
      }
    } catch (error) {
      throw new Error(`Failed to fetch data`);
    }
  };
};

export default useFetch;
