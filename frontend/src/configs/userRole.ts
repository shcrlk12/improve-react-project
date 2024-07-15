export const ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
export const ROLE_USER = "ROLE_USER";
export const ROLE_MANAGER = "ROLE_MANAGER";
export const ROLE_ADMIN = "ROLE_ADMIN";

export type UserRoleType =
  | typeof ROLE_ANONYMOUS
  | typeof ROLE_USER
  | typeof ROLE_MANAGER
  | typeof ROLE_ADMIN;
