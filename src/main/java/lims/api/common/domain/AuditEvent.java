package lims.api.common.domain;

public interface AuditEvent {

    interface User {
        String update = "사용자 수정";
        String initPwd = "비밀번호 초기화";
    }

    interface Department {
        String create = "부서 생성";
        String update = "부서 수정";
    }

    interface Document {
        String create = "등록";
        String update = "수정";
    }

    interface File {
        String create = "파일 저장";
        String delete = "파일 삭제";
        String deleteAll = "파일 일괄 삭제";
    }

    interface Plant {
        String create = "사업장 신규 저장";
        String update = "사업장 수정";
    }

    interface Menu {
        String create = "메뉴 생성";
        String update = "메뉴 수정";
    }

    interface CommonCode {
        String create = "공통코드 생성";
        String update = "공통코드 수정";
        String detailCreate = "상세코드 생성";
        String detailUpdate = "상세코드 수정";
    }

    interface CommonCodeTree {
        String create = "공통코드 생성";
        String update = "공통코드 수정";
    }

    interface Material {
        String create = "시험재료 저장";
        String update = "시험재료 수정";
        String delete = "시험재료 삭제";
    }

    interface Auth {
        String create = "권한 저장";
        String update = "권한 수정";
        String delete = "권한 삭제";
    }

    interface Vendor {
        String create = "제조/공급처 저장";
        String update = "제조/공급처 수정";
        String delete = "제조/공급처 삭제";
    }

    interface StandardMaterial {
        String create = "표준품 입고 등록";
        String update = "표준품 입고 수정";
        String delete = "표준품 입고 삭제";
        String approve = "표준품 입고 승인요청";
        String stock = "표준품 입고";
        String printLabel = "표준품 라벨 출력";
        String reprintLabel = "표준품 라벨 재출력";
        String open = "표준품 개봉";
        String requestDisposal = "표준품 폐기요청";
        String openCancel = "표준품 개봉취소";
        String useCreate = "표준품 사용";
        String useUpdate = "표준품 사용 수정";
        String useDelete = "표준품 사용 삭제";
        String savedFile = "표준품 파일 등록";
    }

    interface ReagentMaterial {
        String create = "시약 입고 등록";
        String update = "시약 입고 수정";
        String delete = "시약 입고 삭제";
        String stock = "시약 입고";
        String printLabel = "시약 라벨 출력";
        String reprintLabel = " 시약 라벨 재출력";
        String open = "시약 개봉";
        String requestDisposal = "시약 폐기요청";
        String openCancel = "시약 개봉취소";
        String useCreate = "시약 사용";
        String useUpdate = "시약 사용 수정";
        String useDelete = "시약 사용 삭제";
        String savedFile = "시약 파일 등록";
    }

    interface AnalColMaterial {
        String create = "분석컬럼 입고 등록";
        String update = "분석컬럼 입고 수정";
        String delete = "분석컬럼 입고 삭제";
        String stock = "분석컬럼 입고";
        String printLabel = "분석컬럼 라벨 출력";
        String reprintLabel = "분석컬럼 라벨 재출력";
        String open = "분석컬럼 개봉";
        String openUpdate = "분석컬럼 정보 수정";
        String requestDisposal = "분석컬럼 폐기요청";
        String openCancel = "분석컬럼 개봉취소";
        String useCreate = "분석컬럼 사용";
        String useUpdate = "분석컬럼 사용 수정";
        String useDelete = "분석컬럼 사용 삭제";
        String savedFile = "분석컬럼 파일 등록";
    }

    interface GlassMaterial {
        String create = "초자 입고 등록";
        String update = "초자 입고 수정";
        String delete = "초자 입고 삭제";
        String stock = "초자 입고";
        String printLabel = "초자 라벨 출력";
        String reprintLabel = "초자 라벨 재출력";
        String open = "초자 개봉";
        String disposal = "초자 폐기";
        String openCancel = "초자 개봉취소";
        String savedFile = "초자 파일 등록";
    }

    interface Approve {
        String create = "승인 요청";
        String update = "재승인";
        String approve = "승인";
        String reject = "반려";
    }

    interface TestCycle {
        String create = "추가";
        String update = "수정";
        String delete = "삭제";
    }

    interface TestTerm {
        String createTerm = "기간추가";
        String updateTerm = "기간수정";
        String deleteTerm = "기간삭제";
        String createInterval = "간격추가";
        String updateInterval = "간격수정";
        String deleteInterval = "간격삭제";
    }

    interface MonitorSpec {
        String createVersion = "버전등록";
        String updateVersion = "버전수정";
        String createMItemSpec = "규격서등록";
        String revisionSpec = "규격서수정";

        String updateRequestReview = "검토요청";
        String deleteMItemSpec = "규격서삭제";
        String createMItemSpecAItem = "시험항목추가";
        String updateMItemSpecAItem = "시험항목수정";
        String deleteMItemSpecAItem = "시험항목삭제";
        String updateApproved = "승인";
        String updateApprovedReject = "승인반려";
        String updateApproveRequest = "승인요청";
        String updateApproveRequestReject = "승인요청반려";
        String updateUseVerN = "사용버전수정";
    }

    interface TestInfo {
        String apprReject = "승인 반려";
        String receipt = "시험 접수";
        String request = "시험 의뢰";
        String requestRegist = "시험 의뢰 등록";
        String requestCancel = "시험 의뢰 취소";
        String resultDelete = "결과 삭제";
        String resultInput = "결과 입력";
        String resultComplete = "결과 입력 완료";
        String resultReview = "결과 검토";
        String reviewReject = "검토 반려";
        String sampleCollect = "검체 채취";
        String inputSampleManage = "검체 채취 관리 등록";
        String testApprove = "결과 승인";
        String testAssign = "시험 배정";
        String testCancel = "시험 취소";
        String testHold = "시험 보류";
        String testHoldOff = "시험 보류해제";
        String testEnd = "시험 종료";
        String testInstruction = "시험 지시";
        String update = "시험 수정";
        String inputResult = "결과값 입력";
        String requestReview = "검토 요청";
        String publishLabEvent = "실험실 이벤트 발행";
        String reviewRejection = "결과검토 반려";
        String resultInputHold = "결과입력 보류";
        String requestReviewHold = "결과검토 보류";
        String requestApproveHold = "결과승인 보류";
        String completedReview = "결과검토 완료";
        String savedRequestFile = "의뢰 파일 등록";
        String savedItemFile = "항목 파일 등록";
        String testFinalApproved = "시험 최종승인";
        String testJudgeModify = "시험 결과판정 변경";
    }

    interface labEvent {
        String approveRequest = "승인 요청";
        String reject = "검토 반려";
        String approve = "승인";
        String approveReject = "승인 반려";
    }

    interface UserDelegate {
        String tempSave = "임시 저장";
        String delegationRegected = "위임 반려";
        String requestApprove = "승인 요청";
        String stay = "재실 등록";
        String update = "수정";
        String complete = "승인 완료";
    }

    interface Spec {
        String createVersion = "버전등록";
        String updateVersion = "버전수정";
        String createPItemSpec = "규격서등록";
        String revisionSpec = "규격서수정";

        String updateRequestReview = "검토요청";
        String deletePItemSpec = "규격서삭제";
        String createPItemSpecAItem = "시험항목추가";
        String updatePItemSpecAItem = "시험항목수정";
        String deletePItemSpecAItem = "시험항목삭제";
        String updateApproved = "승인";
        String updateApprovedReject = "승인반려";
        String updateApproveRequest = "승인요청";
        String updateApproveRequestReject = "승인요청반려";
        String updateUseVerN = "사용버전수정";
    }

    interface Item {
        String create = "품목 생성";
        String update = "품목 수정";
        String delete = "품목 삭제";
        String requestReview = "품목 검토 요청";
        String rejectReview = "품목 검토 반려";
        String requestApprove = "품목 승인 요청";
        String approve = "품목 승인";
        String rejecte = "품목 반려";
        String updateUseVerYn = "품목 버전 사용여부 수정";
        String updateSpecUseVerYn = "규격서 버전 사용여부 수정";
        String updateSpecPitmVer = "규격서 품목 버전 수정";
        String createSpec = "규격서 생성";
        String updateSpec = "규격서 수정";

        String createAitemSpec = "규격 생성";

        String crateAitem = "규격 항목 생성";


    }

    interface WrapTest {
        String createWrapTestSpecAItem = "시험항목추가";
        String updateWrapTestSpecAItem = "시험항목수정";
        String deleteWrapTestSpecAItem = "시험항목삭제";
        String createWrapTest = "자재계층 생성";
        String requestApproveWrapTest = "자재계층 승인 요청";
        String updateWrapTest = "자재계층 수정";
        String approveWrapTest = "자재계층 승인";
        String rejectWrapTest = "자재계층 반려";
    }

    interface Specimen {
        String create = "표준견본 생성";
        String update = "표준견본 수정";
        String delete = "표준견본 삭제";
        String revise = "표준견본 개정";
    }

    interface Equipment {
        String historySave = "이력관리 저장";
        String historyUpdate = "이력관리 수정";
        String historyDelete = "이력관리 삭제";
        String requestApprove = "이력관리 승인요청";
        String approve = "이력관리 승인";
        String reject = "이력관리 반려";
        String savedFile = "이력관리 파일 등록";
    }

    interface ProcessSpecimen {
        String create = "공정표준견본 생성";
        String update = "공정표준견본 수정";
        String delete = "공정표준견본 삭제";
    }

    interface PackagingSpecimen {
        String create = "포장재표준견본 생성";
        String update = "포장재표준견본 수정";
        String delete = "포장재표준견본 삭제";
        String reject = "포장재표준견본 반려";
        String addAcsr = "포장재표준견본 구성품추가";
        String updateAcsr = "포장재표준견본 구성품수정";
        String deleteAcsr = "포장재표준견본 구성품삭제";
        String deleteRequest = "포장재표준견본 삭제 승인요청";
    }

    interface Instrument {
        String create = "기기 생성";
        String update = "기기 수정";
        String delete = "기기 삭제";
        String savedFile = "기기 파일 등록";
        String createAccessory = "기기 구성품 생성";
        String updateAccessory = "기기 구성품 수정";
        String deleteAccessory = "기기 구성품 삭제";
        String createUsageHistory = "기기사용기록 생성";
        String updateUsageHistory = "기기사용기록 수정";
        String deleteUsageHistory = "기기사용기록 삭제";
    }

    interface ReportEvent {
        String createReportInfo = "리포트정보 저장";
    }

    interface RptMst {
        String create = "리포트 마스터 생성";
        String update = "리포트 마스터 수정";
        String delete = "리포트 마스터 삭제";
    }

    interface NonconformityReport {
        String update = "부적합통보서 수정";
        String requestApprove = "부적합통보서 승인요청";
        String approve = "부적합통보서 승인";
        String reject = "부적합통보서 반려";
    }

    interface PreventRecurrenceReport {
        String update = "재발방지대책서 수정";
        String requestApprove = "재발방지대책서 승인요청";
        String approve = "재발방지대책서 승인";
        String reject = "재발방지대책서 반려";
        String saveReport = "재발방지대책서 파일 저장";
        String deleteReport = "재발방지대책서 파일 삭제";
    }

    interface StabPln {
        String create = "안정성시험계획 생성";
        String update = "안정성시험계획 수정";
        String delete = "안정성시험계획 삭제";
        String stopRequest = "안정성시험계획 중단요청";
        String stopCancelRequest = "안정성시험계획 중단취소요청";
        String approveRequest = "안정성시험계획 승인요청";

        String reject = "안정성시험계획 반려";

        String approve = "안정성시험계획 승인";

        String createAns = "안정성계획 생성";

        String updateAns = "안정성계획 수정";
        String deleteAns = "안정성계획 삭제";

        String createAitm = "안정성 시험 시험항목 생성";
        String deleteAitm = "안정성 시험 시험항목 삭제";
    }

    interface Sample {
        String createSample = "검체 생성";
        String updateSample = "검체 수정";
        String deleteSample = "검체 삭제";
        String createSampleUsage = "검체 사용 생성";
        String updateSampleUsage = "검체 사용 수정";
        String deleteSampleUsage = "검체 사용 삭제";
        String rejectSampleUsage = "검체 사용 반려";
        String requestApproveUse = "검체 사용 승인 요청";
        String requestCancelUse = "검체 사용 취소 요청";
        String approve = "검체 승인";
        String reject = "검체 반려";
        String requestApproveDispose = "검체 폐기 승인 요청";
        String requestApproveCancelDispose = "검체 폐기 취소 승인 요청";

        String requestApproveAddSample = "추가 검체 승인 요청";
        String createAddSample = "추가 검체 생성";
        String updateAddSample = "추가 검체 수정";
        String deleteAddSample = "추가 검체 삭제";
        String approveAddSample = "추가 검체 승인";
        String rejectAddSample = "추가 검체 반려";
    }

    interface InspectionProductionPerformance {
        String createRecord = "생산 연계 이력 저장";
        String updateState = " 생산 연계 상태 수정";
    }

    interface InspectionPurchasingPerformance {
        String createRecord = "구매 연계 이력 저장";
        String updateState = "구매 연계 상태 수정";
    }

    interface LabelPrint {
        String createLabelPrint = "라벨 출력 생성";
        String updateLabelPrint = "라벨 출력 수정";
        String requestReLabelPrint = "라벨 재출력 요청";
        String rejectReLabelPrint = "라벨 재출력 반려";
        String approveReLabelPrint = "라벨 재출력 승인";
        String cancelReLabelPrint = "라벨 재출력 취소";
    }
}
