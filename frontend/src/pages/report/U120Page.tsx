import DailyReportContainer from "@components/report/DailyReportContainer";

/**
 * Renders the U120 report page.
 * Simply renders the DailyReportContainer component.
 *
 * @author Karden
 * @created 2024-07-17
 */
const U120Page = () => {
  return (
    <>
      <DailyReportContainer
        selectedSite={"095adeed-4989-456a-ba45-7bde19ec15fc"}
      />
    </>
  );
};

export default U120Page;
