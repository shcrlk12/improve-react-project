import { titleMathcers } from "@config/routes";
import { useLocation } from "react-router";

/**
 * Returns the title of current path in route configuration file.
 *
 * @returns {string}  the title of current path
 *
 * @author Karden
 * @created 2024-07-17
 */
const useTitle = () => {
  //1st priority: matched specific path
  //2st priority: matched range path
  let matchedPath = "";
  let matchedTitle = "";

  const { pathname } = useLocation();

  for (const titleMathcer of titleMathcers) {
    const regex = new RegExp(titleMathcer.route, "i");
    if (regex.test(pathname)) {
      if (matchedPath.length < titleMathcer.route.length) {
        matchedPath = titleMathcer.route;
        matchedTitle = titleMathcer.title;
      }
    }
  }

  return matchedTitle;
};

export default useTitle;
