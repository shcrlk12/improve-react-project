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
