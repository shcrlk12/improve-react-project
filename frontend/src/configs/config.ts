/**
 * Configuration for common settings.
 */
export const config = {
  apiServer: {protocol: "http", ip: "127.0.0.1", port: "6789" },
  // apiServer: { protocol: "https", ip: "172.19.102.139", port: "" },
};

export const getRestApiServerUrl = (resource: string) => {
  let resourceUrl;

  if (resource.charAt(0) === "/") resourceUrl = resource.substring(1);
  else resourceUrl = resource;

  return `${config.apiServer.protocol}://${config.apiServer.ip}:${config.apiServer.port}/api/${resourceUrl}`;
};
