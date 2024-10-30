export type JsonApi<T> = {
  id: string;
  type: string;
  attributes: T;
};
