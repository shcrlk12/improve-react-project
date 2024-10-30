import { UserRoleType } from "@config/userRole";

//types
export const LOGIN_SUCCESS = "LOGIN_SUCCESS" as const;
export const LOGOUT = "LOGOUT" as const;

export type User = {
  id: string;
  password?: string;
  name: string;
  role: UserRoleType;
  lastLoginTime?: string | null;
};

// action 생성 함수
export const loginSuccess = (user: User) => ({
  type: LOGIN_SUCCESS,
  payload: user,
});

export const logout = () => ({
  type: LOGOUT,
});
