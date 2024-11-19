export type JsonApi<T> = {
  id: string;
  type: string;
  attributes: T;
};

export type JsonApiRequestPost<T> = {
  data: Resourse<T>;
};

export type Resourse<T> = {
  id: string;
  type: string;
  attributes: T;
};

export type JsonApiRequestsPost<T> = {
  data: Array<Resourse<T>>;
};

export const CONTENT_TYPE = "application/vnd.api+json";
export const ACCEPT = "application/vnd.api+json";

export const createPostRequestsObject = <T>(): JsonApiRequestsPost<T> => {
  const result: JsonApiRequestsPost<T> = { data: [] as Array<Resourse<T>> };
  return result;
};

export const createPostRequestObject = <T>(): JsonApiRequestPost<T> => {
  const result: JsonApiRequestPost<T> = { data: {} as Resourse<T> };
  return result;
};
