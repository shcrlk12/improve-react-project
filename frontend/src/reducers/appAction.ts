//types
export const SET_LOADING = "SET_LOADING" as const;
export const RESET_LOADING = "RESET_LOADING" as const;
export const HEADER_ITEM_VISIBLE = "HEADER_ITEM_VISIBLE" as const;

export type HeaderItemVisible = {
  left: boolean;
  gnb: boolean;
  logoutBtn: boolean;
};

// action 생성 함수
export const setLoading = () => ({
  type: SET_LOADING,
});

export const resetLoading = () => ({
  type: RESET_LOADING,
});

export const headerItemVisible = (headerTiemVisible: HeaderItemVisible) => ({
  type: SET_LOADING,
  payload: headerTiemVisible,
});

/**
 * site config
 */
export const SET_SITES = "SET_SITES" as const;
export const CLEAR_SITES = "CLEAR_SITES" as const;
export const SELECT_SITE = "SELECT_SITE" as const;

export type SiteType = {
  uuid: string;
  name: string;
  remark: string;
  ratedPower: number;
};

export const setSites = (sites: SiteType[]) => ({
  type: SET_SITES,
  payload: sites,
});

export const selectSite = (site: SiteType) => ({
  type: SELECT_SITE,
  payload: site,
});

export const clearSites = () => ({
  type: CLEAR_SITES,
});
