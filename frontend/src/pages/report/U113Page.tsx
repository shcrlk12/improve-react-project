import DailyReportContainer from "@components/report/DailyReportContainer";

/**
 * Renders the U113 report page.
 * Simply renders the DailyReportContainer component.
 *
 * @author Karden
 * @created 2024-07-17
 */
const U113Page = () => {
  return (
    <>
      <DailyReportContainer
        selectedSite={"7e8f1213-c2a5-4335-8e33-594025784e0b"}
      />
    </>
  );
};

export default U113Page;
