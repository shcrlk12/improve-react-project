import {
  HeaderItemVisible,
  RESET_LOADING,
  SET_LOADING,
  headerItemVisible,
  resetLoading,
  setLoading,
} from "./appAction";

//Action Type
type AppAction = ReturnType<typeof setLoading> | ReturnType<typeof resetLoading> | ReturnType<typeof headerItemVisible>;

//State type
type AppState = {
  headerItemVisible: HeaderItemVisible;
  isLoading: boolean;
};

const initialState: AppState = {
  headerItemVisible: {
    left: true,
    gnb: true,
    logoutBtn: true,
  },
  isLoading: false,
};

const appReducer = (state: AppState = initialState, action: AppAction) => {
  switch (action.type) {
    case SET_LOADING:
      return {
        ...state,
        isLoading: true,
      };
    case RESET_LOADING:
      return {
        ...state,
        isLoading: false,
      };
    default:
      return state;
  }
};

export default appReducer;
