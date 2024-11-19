import {
  CLEAR_SITES,
  HeaderItemVisible,
  RESET_LOADING,
  SELECT_SITE,
  SET_LOADING,
  SET_SITES,
  SiteType,
  clearSites,
  headerItemVisible,
  resetLoading,
  selectSite,
  setLoading,
  setSites,
} from "./appAction";

//Action Type
type AppAction = ReturnType<typeof setLoading> | ReturnType<typeof resetLoading> | ReturnType<typeof headerItemVisible>;
type SiteAction = ReturnType<typeof setSites> | ReturnType<typeof selectSite> | ReturnType<typeof clearSites>;

//State type
type AppState = {
  headerItemVisible: HeaderItemVisible;
  isLoading: boolean;
  sites: SiteType[];
  selectedSite: SiteType;
};

const initialState: AppState = {
  headerItemVisible: {
    left: true,
    gnb: true,
    logoutBtn: true,
  },
  isLoading: false,
  sites: [] as SiteType[],
  selectedSite: {} as SiteType,
};

const appReducer = (state: AppState = initialState, action: AppAction | SiteAction) => {
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
    case SET_SITES:
      return {
        ...state,
        sites: action.payload,
      };
    case SELECT_SITE:
      return {
        ...state,
        selectedSite: action.payload,
      };

    case CLEAR_SITES:
      return {
        ...state,
        selectedSite: {} as SiteType,
      };
    default:
      return state;
  }
};

export default appReducer;
