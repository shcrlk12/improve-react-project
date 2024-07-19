/**
 * Replaces the last segment of a URL path with a new segment.
 * This function takes a given URL path and replaces its last segment with
 * the provided segment, then returns the modified path.
 *
 * @param {string} currentPath - The original path where the replacement will occur.
 * @param {string} lastPath - The new segment to replace the last segment in the path.
 * @returns {string} The modified path with the last segment replaced.
 *
 * @example
 * // Returns "/users/profile"
 * replaceLastPath("/users/settings", "profile");
 *
 * @author Karden
 * @created 2024-07-17
 */
export const replaceLastPath = (
  currentPath: string,
  lastPath: string
): string => {
  const splitCurrentPath = currentPath.split("/");
  splitCurrentPath[splitCurrentPath.length - 1] = lastPath;

  return splitCurrentPath.join("/");
};
