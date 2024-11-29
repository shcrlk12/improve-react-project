import styled from "styled-components";
import { PrimaryButton } from "@karden/utils/button";
import { TextArea1 } from "@karden/utils/Input";
import { ChangeEvent, Dispatch, MouseEventHandler, SetStateAction, useEffect, useMemo, useState } from "react";
import CalendarPopup from "./../calendar/CalendarPopup";
import PreviewContainer from "./../preview/PreviewContainer";
import { selectSite, setSites, SiteType } from "@reducers/appAction";
import { useLocation, useNavigate } from "react-router";
import { replaceLastPath } from "@src/utils/path";
import { useDispatch, useSelector } from "react-redux";
import useFetchData from "@src/hooks/useFetchData";
import { createPostRequestObject, jsonOrgConfig } from "@src/jsonApiOrg/JsonApiOrg";
import { config, getRestApiServerUrl } from "@config/config";
import { RootState } from "@src/main";
import Swal from "sweetalert2";

/**
 * Styled
 */
const StyledDailyReport = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  width: 900px;
  margin: auto;
`;

const SiteHeaderContainer = styled.div`
  display: flex;
`;

const HeaderItem = styled.div`
  padding: 10px 20px;
  position: relative;

  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  cursor: pointer;
  &.selected::after {
    content: "";
    height: 3px;
    width: 70%;
    border-radius: 2px;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;

    animation-name: selected-site;
    animation-duration: 0.6s;
    animation-fill-mode: forwards;
  }

  &:hover {
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;
const SignificantContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;

  width: 100%;
  padding-bottom: 20px;
  border-bottom: 2px solid ${({ theme }) => theme.color.primary};
`;

const SignificantHeader = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;

const SignificantEditor = styled.div`
  background: #fff;
`;

const SignificantButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;
`;

/**
 * Period styled
 */
const CalendarPopupContainer = styled.div`
  display: flex;
  justify-content: end;
`;

const PeriodContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
`;

const PeriodHeader = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  width: 100%;
`;

// const PeriodSelectionTitle = styled.div``;
// const PeriodSelectionDate = styled.div``;

/**
 * Type
 */
type DailyReportType = {
  sites: SiteType[];
  selectedSite: SiteType;
  selectedDate: Date;
  setSelectedDate: Dispatch<SetStateAction<Date>>;
};

type DailyReportRemarkOfRequest = {
  remark: string;
};
/**
 * This is the daily report component.
 * It allows selection of any site of wind turbine,
 * generates a daily report preview, and provides the option to download the report.
 *
 * @todo1 I can draw preview UI later
 * @author Karden
 * @created 2024-07-17
 */

const DailyReport = ({ sites, selectedSite, selectedDate, setSelectedDate }: DailyReportType) => {
  const [remark, setRemark] = useState<string>("");
  const [previewRefreshKey, setRreviewRefreshKey] = useState<number>(0);
  const [isShowPreview, setIsShowPreview] = useState<boolean>(false);
  const [isDisableRemarkBox, setIsDisableRemarkBox] = useState<boolean>(true);
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const fetchData = useFetchData();
  const user = useSelector((store: RootState) => store.userReducer.user);

  useEffect(() => {
    setRemark(selectedSite.remark);
    setIsShowPreview(false);
    setIsDisableRemarkBox(true);
  }, [selectedSite]);

  useEffect(() => {
    const splitedPath = location.pathname.split("/");
    const selectedSiteUuid = splitedPath[splitedPath.length - 1];

    sites.forEach((item) => {
      if (item.uuid.toLowerCase() === selectedSiteUuid.toLowerCase()) {
        dispatch(selectSite(item));
        navigate(replaceLastPath(location.pathname, item.uuid));
      }
    });
  }, [location.pathname]);

  const uploadRemark = async () => {
    let request = createPostRequestObject<DailyReportRemarkOfRequest>();

    request.data = {
      id: selectedSite.uuid,
      type: "daily-remarks",
      attributes: {
        remark,
      },
    };

    const response = await fetchData(getRestApiServerUrl(`/data/remarks/${selectedSite.uuid}`), {
      method: "PATCH",
      credentials: "include",
      body: JSON.stringify(request),
      headers: { "Content-Type": jsonOrgConfig.CONTENT_TYPE, Accept: jsonOrgConfig.ACCEPT },
    });
  };
  const createHeaderItem = useMemo((): JSX.Element[] => {
    return sites.map((item) => (
      <HeaderItem
        key={item.uuid}
        className={selectedSite.uuid === item.uuid ? "selected" : undefined}
        onClick={() => {
          navigate(replaceLastPath(location.pathname, item.uuid));
        }}>
        <div className="hidden-element">{item.uuid}</div>
        {item.name}
      </HeaderItem>
    ));
  }, [location.pathname, selectedSite.uuid]);

  return (
    <StyledDailyReport>
      <SiteHeaderContainer>{createHeaderItem}</SiteHeaderContainer>
      <MainContainer style={{ width: "450px" }}>
        <SignificantContainer>
          <SignificantHeader>특이사항</SignificantHeader>
          <textarea
            name="text"
            style={{ fontSize: "16px", padding: "4px", marginTop: "6px" }}
            cols={50}
            rows={8}
            value={remark}
            disabled={isDisableRemarkBox}
            onChange={(event: ChangeEvent<HTMLTextAreaElement>) => {
              event.preventDefault();

              setRemark(event.currentTarget.value);
            }}></textarea>
          <SignificantButtonContainer>
            <PrimaryButton
              type="submit"
              text="저장"
              width="30%"
              onClick={async () => {
                if (!isDisableRemarkBox) {
                  const result = await Swal.fire({
                    title: "특이사항 저장",
                    text: "특이사항 저장을 하시겠습니까?",
                    icon: "info",
                    showCancelButton: true,
                    confirmButtonText: "확인",
                    cancelButtonText: "취소",
                  });
                  if (result.isConfirmed) {
                    await uploadRemark();

                    const newSites = sites.map((site) =>
                      site.uuid === selectedSite.uuid ? { ...site, remark } : site
                    );
                    dispatch(setSites(newSites));
                    setIsDisableRemarkBox(true);
                  }
                }
              }}
            />
            <PrimaryButton
              type="submit"
              text={isDisableRemarkBox ? "편집" : "취소"}
              width="30%"
              onClick={async () => {
                if (isDisableRemarkBox) {
                  const result = await Swal.fire({
                    title: "특이사항 편집",
                    text: "특이사항 편집을 하시겠습니까?",
                    icon: "info",
                    showCancelButton: true,
                    confirmButtonText: "확인",
                    cancelButtonText: "취소",
                  });
                  if (result.isConfirmed) {
                    setIsDisableRemarkBox(false);
                  }
                } else {
                  const result = await Swal.fire({
                    title: "특이사항 편집 취소",
                    text: "특이사항 편집을 취소 하시겠습니까?",
                    icon: "info",
                    showCancelButton: true,
                    confirmButtonText: "확인",
                    cancelButtonText: "취소",
                  });
                  if (result.isConfirmed) {
                    setIsDisableRemarkBox(true);
                  }
                }
              }}
            />
          </SignificantButtonContainer>
        </SignificantContainer>
        <PeriodContainer>
          <PeriodHeader>기간 선택</PeriodHeader>
          <CalendarPopupContainer>
            <CalendarPopup date={selectedDate} setDate={setSelectedDate} />
          </CalendarPopupContainer>
          <SignificantButtonContainer>
            <PrimaryButton
              type="submit"
              text="미리보기 생성"
              onClick={async () => {
                const now = new Date(Date.now());

                if (
                  selectedDate.getFullYear() * 365 + selectedDate.getMonth() * 30.41 + selectedDate.getDate() >
                  now.getFullYear() * 365 + now.getMonth() * 30.41 + now.getDate()
                ) {
                  await Swal.fire({
                    title: "날짜 선택 에러",
                    text: "오늘 날짜 이전으로 선택하세요.",
                    icon: "error",
                  });
                } else {
                  setIsShowPreview(true);
                  setRreviewRefreshKey((prev) => prev + 1);
                }
              }}
            />
          </SignificantButtonContainer>
        </PeriodContainer>
      </MainContainer>
      {isShowPreview && (
        <PreviewContainer selectedDate={selectedDate} selectedSite={selectedSite} key={previewRefreshKey} />
      )}
    </StyledDailyReport>
  );
};

export default DailyReport;
