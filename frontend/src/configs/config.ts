/**
 * Configuration for common settings.
 */
export const config = {
  apiServer: "127.0.0.1",
};

export const turbineConfig = {
  sites: {
    ["a6fbb507-ee97-4277-ae6b-b8d3da2d9f64"]: "U151",
    ["7e8f1213-c2a5-4335-8e33-594025784e0b"]: "U113",
    ["095adeed-4989-456a-ba45-7bde19ec15fc"]: "U120",
  },
};

export type SitesType = typeof turbineConfig.sites;
