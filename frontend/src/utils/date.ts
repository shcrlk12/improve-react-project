/**
 * Formats a Date object into a string based on the provided format.
 *
 * This function takes a Date object and a format string, and returns a formatted date string.
 * The format string can contain placeholders that will be replaced with corresponding parts of the date.
 * Supported placeholders are:
 * - YYYY: Full year (e.g., 2024)
 * - YY: Last two digits of the year (e.g., 24)
 * - MM: Month (1-12)
 * - DD: Day of the month (1-31)
 * - hh: Hour (0-23)
 * - mm: Minutes (0-59)
 * - ss: Seconds (0-59)
 *
 * @param {Date} date - The Date object to format.
 * @param {string} format - The format string containing placeholders.
 * @returns {string} The formatted date string.
 *
 * @example
 * // Returns "2024-07-19"
 * formatDateToString(new Date(2024, 6, 19), "YYYY-MM-DD");
 *
 * @example
 * // Returns "24-07-19 13:45:30"
 * formatDateToString(new Date(2024, 6, 19, 13, 45, 30), "YY-MM-DD hh:mm:ss");
 *
 * @throws {Error} If the date parameter is not a valid Date object.
 * @throws {Error} If the format parameter is not a string.
 *
 * @author Karden
 * @created 2024-07-19
 */
export const formatDateToString = (date: Date, format: string): string => {
  const padZero = (num: number, size: number = 2): string => {
    let s = num.toString();
    while (s.length < size) s = "0" + s;
    return s;
  };

  const replacements: { [key: string]: string } = {
    YYYY: date.getFullYear().toString(),
    YY: padZero(date.getFullYear() % 100),
    MM: padZero(date.getMonth() + 1),
    DD: padZero(date.getDate()),
    hh: padZero(date.getHours()),
    mm: padZero(date.getMinutes()),
    ss: padZero(date.getSeconds()),
  };

  return format.replace(
    /YYYY|YY|MM|DD|hh|mm|ss/g,
    (match) => replacements[match]
  );
};

/**
 * Returns the name of the day of the week in the specified language.
 *
 * This function takes a day of the week represented as a number (0 for Sunday, 1 for Monday, etc.)
 * and a language code ("en" for English, "kr" for Korean) and returns the corresponding name
 * of the day in the specified language.
 *
 * @param {number} dayOfWeek - The day of the week as a number (0 for Sunday, 1 for Monday, etc.).
 * @param {'en' | 'kr'} language - The language code for the desired output ("en" for English, "kr" for Korean).
 * @returns {string} The name of the day of the week in the specified language.
 *
 * @example
 * // Returns "Sunday"
 * getNameDayOfWeek(0, "en");
 *
 * @example
 * // Returns "월"
 * getNameDayOfWeek(1, "kr");
 *
 * @throws {Error} If the `dayOfWeek` is not a number between 0 and 6.
 * @throws {Error} If the `language` is not "en" or "kr".
 *
 * @author Karden
 * @created 2024-07-19
 */
export const getNameDayOfWeek = (
  dayOfWeek: number,
  language: "en" | "kr" | undefined = "kr"
): string => {
  if (language === "en") {
    if (dayOfWeek === 0) return "Sunday";
    if (dayOfWeek === 1) return "Monday";
    if (dayOfWeek === 2) return "Tuesday";
    if (dayOfWeek === 3) return "Wednesday";
    if (dayOfWeek === 4) return "Thursday";
    if (dayOfWeek === 5) return "Friday";
    if (dayOfWeek === 6) return "Saturday";
  } else if (language === "kr") {
    if (dayOfWeek === 0) return "일";
    if (dayOfWeek === 1) return "월";
    if (dayOfWeek === 2) return "화";
    if (dayOfWeek === 3) return "수";
    if (dayOfWeek === 4) return "목";
    if (dayOfWeek === 5) return "금";
    if (dayOfWeek === 6) return "토";
  }
  return "";
};
