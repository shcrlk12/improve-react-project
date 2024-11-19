import { ROLE_ANONYMOUS } from "@config/userRole";
import { LOGIN_SUCCESS, LOGOUT, User, loginSuccess, logout } from "./userActions";

//Action Type
type UserAction = ReturnType<typeof loginSuccess> | ReturnType<typeof logout>;

//State type
type UserState = {
  isAuthenticated: boolean;
  user: User;
};

const initialState: UserState = {
  isAuthenticated: false,
  user: {
    id: "",
    name: "",
    role: ROLE_ANONYMOUS,
    lastLoginTime: null,
  },
};

const userReducer = (state: UserState = initialState, action: UserAction) => {
  switch (action.type) {
    case LOGIN_SUCCESS:
      return {
        ...state,
        isAuthenticated: true,
        user: action.payload,
      };
    case LOGOUT:
      return {
        ...state,
        isAuthenticated: false,
        user: {
          id: "",
          name: "",
          role: ROLE_ANONYMOUS,
          lastLoginTime: null,
        },
      };
    default:
      return state;
  }
};

export default userReducer;
