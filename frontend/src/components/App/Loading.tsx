import { LoadingInner, Loadingbg } from "./App.styled";
import { CircularProgress } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "@src/main";

const Loading = () => {
  const { isLoading } = useSelector((store: RootState) => store.appReducer);

  return (
    isLoading && (
      <Loadingbg>
        <LoadingInner>
          <CircularProgress className="loading-icon" />
        </LoadingInner>
      </Loadingbg>
    )
  );
};

export default Loading;
