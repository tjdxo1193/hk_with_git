<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <AUIGridSearch
    v-bind="resultInputInfo"
    :columns="computedItemListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('resultInputInfo.$grid', proxy)"
    @grid-button-click="gridButtonClick"
  />

  <FileAttacherModal
    ref="fileAttacherModal"
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  />

  <TestLabEventModal
    :parameter="testLabEventModal.parameter"
    :show="testLabEventModal.show"
    @close="hideModal('testLabEventModal')"
    @modalReturnDataEvent="afterModalEvent"
  />

  <RequestReviewerModal
    :show="requestReviewerModal.show"
    @close="hideModal('requestReviewerModal')"
    @modalReturnDataEvent="requestReviewModalReturnDataEvent"
  />

  <ResultHistoryModal
    :parameter="resultHistoryModal.parameter"
    :show="resultHistoryModal.show"
    @close="hideModal('resultHistoryModal')"
  />

  <FinalOrderModal
    :parameter="finalOrderModal.parameter"
    :show="finalOrderModal.show"
    @close="hideModal('finalOrderModal')"
  ></FinalOrderModal>

  <InputPerformanceModal
    :show="inputPerformanceModal.show"
    :parameter="inputPerformanceModal.parameter"
    @close="hideModal('inputPerformanceModal')"
  ></InputPerformanceModal>
</template>

<script>
import {
  FileAttacherModal,
  TestLabEventModal,
  RequestReviewerModal,
  ResultHistoryModal,
  FinalOrderModal,
  InputPerformanceModal,
} from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/testResultInput';

export default {
  name: 'TestResultInput',
  components: {
    FileAttacherModal,
    TestLabEventModal,
    RequestReviewerModal,
    ResultHistoryModal,
    FinalOrderModal,
    InputPerformanceModal,
  },
  mounted() {
    this.getResultInputList();
  },
  data() {
    const { list, resultInputInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.resultInputInfo.forms, e.item);
            this.getResultDetail(e.item.ansIdx);
          },
        },
      },
      resultInputInfo: {
        ...resultInputInfo.static,
        forms: resultInputInfo.forms(),
        columns: resultInputInfo.columns(),
        event: {
          cellEditEnd: (e) => this.cellEditEndEvent(e),
        },
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
      testLabEventModal: {
        show: false,
        parameter: {},
      },
      requestReviewerModal: {
        show: false,
      },
      resultHistoryModal: {
        show: false,
        parameter: {},
      },
      finalOrderModal: {
        show: false,
        parameter: {},
      },
      inputPerformanceModal: {
        show: false,
        parameter: {},
      },
      fileInfo: {
        ansIdx: '',
        fileIdx: '',
        rstSeq: '',
      },
    };
  },
  methods: {
    async getResultInputList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testResultInput', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.disableButtons();
      this.resultInputInfo.$grid.clearGridData();
    },
    async getResultDetail(ansIdx) {
      const { $grid } = this.resultInputInfo;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ts/testResultInput/${ansIdx}`))
        .then(({ data }) => data);
      $grid.setGridData(data);

      const pitmSpecIdx = data[0].pitmSpecIdx;
      FormUtil.setData(this.list.forms, { ansIdx, pitmSpecIdx });
      this.enableButtons();
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getResultInputList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getResultInputList();
      }
      if (name === 'save') {
        return this.beforeCheckSave();
      }
      if (name === 'inputCfmRstVal') {
        return this.inputCfmRstVal();
      }
      if (name === 'init') {
        return this.init();
      }
      if (name === 'hold') {
        return this.hold();
      }
      if (name === 'resultHistory') {
        if (this.isSelectedRow()) {
          return this.getResultHistory();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'finalOrder') {
        return this.showModal('finalOrderModal');
      }
      if (name === 'inputPerformance') {
        return this.showModal('inputPerformanceModal');
      }
      if (name === 'requestReview') {
        return this.beforeCheckRequestReview();
      }
      if (name === 'labEventPublish') {
        if (this.isSelectedRow()) {
          return this.publishLabEvent();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    isSelectedRow() {
      const selectedItem = this.resultInputInfo.$grid.getSelectedItems();
      return selectedItem.length > 0 ? true : false;
    },
    inputCfmRstVal() {
      const gridData = this.resultInputInfo.$grid.getGridData();
      gridData.map((row) => {
        if (row.jdgTyp === 'S0070001') {
          row.rstVal = row.slvJdgCfm;
          const updateData = {
            _$uid: row._$uid,
            rstVal: row.slvJdgCfm,
            markVal: '',
            rstJdg: '',
          };
          this.resultInputInfo.$grid.updateRowsById(updateData);
          this.judgementTypeCheck(row);
        }
      });
    },
    init() {
      this.resultInputInfo.forms = values.resultInputInfo.forms();
      this.resultInputInfo.$grid.clearGridData();
      this.disableButtons();
    },
    hold() {
      const parameter = FormUtil.getData(this.resultInputInfo.forms);
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultInput/requestHold', parameter))
          .then(() => {
            this.$info(this.$message.info.hold);
            this.init();
            this.getResultInputList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    getResultHistory() {
      /**
       * ? 조건 : 시험항목별방법코드 + VARIANT_NO + 접수일자 기준으로 한달 + 결과승인완료된 + 선택된 항목의 이력만 출력
       */
      const selectedItem = this.resultInputInfo.$grid.getSelectedItems();
      const amitmCd = selectedItem[0].item.amitmCd;
      const vriaNo = selectedItem[0].item.vriaNo;
      const aitmCd = selectedItem[0].item.aitmCd;
      const rcpDt = selectedItem[0].item.rcpDt;
      const ansProcCd = selectedItem[0].item.ansProcCd;

      const parameter = { amitmCd, vriaNo, aitmCd, rcpDt, ansProcCd };
      this.showModal('resultHistoryModal', parameter);
    },
    isPublished() {
      const selectedItem = this.resultInputInfo.$grid.getSelectedItems();
      return selectedItem[0].item.labEvtIdx;
    },
    publishLabEvent() {
      if (this.isPublished() > 0) {
        this.$warn(this.$message.warn.alreadyLabEventPublished);
      } else {
        const selectedItem = this.resultInputInfo.$grid.getSelectedItems();
        const parameter = selectedItem[0].item;
        this.showModal('testLabEventModal', parameter);
      }
    },
    isCompletedPublished() {
      const gridData = this.resultInputInfo.$grid.getGridData();
      return gridData.filter((row) => row.labEvtIdx != null && row.aprYn == null).length == 0
        ? true
        : false;
    },
    isCompletedResultDataForReview() {
      const gridData = this.resultInputInfo.$grid.getGridData();
      const nonConformity = 'S0120002';
      if (gridData.length === 0) {
        return this.$warn(this.$message.warn.noSaveGridData);
      } else {
        return gridData.filter(
          (row) =>
            StringUtil.isEmpty(row.rstVal) ||
            StringUtil.isEmpty(row.markVal) ||
            StringUtil.isEmpty(row.rstJdg) ||
            (row.rstJdg === nonConformity && StringUtil.isEmpty(row.rstRmk)),
        ).length === 0
          ? true
          : false;
      }
    },
    isCompletedInputForReview() {
      const gridData = this.resultInputInfo.$grid.getGridData();
      const completedInput = 'S0140200';
      if (gridData.length === 0) {
        return this.$warn(this.$message.warn.noSaveGridData);
      } else {
        return gridData.filter((row) => row.rstProcCd != completedInput).length === 0
          ? true
          : false;
      }
    },
    inputSytJdg() {
      const gridData = this.resultInputInfo.$grid.getGridData();
      const cnt = gridData.filter((row) => row.rstJdg !== null).length;
      if (cnt !== gridData.length) {
        return;
      }
      let sytJdg = '';
      const nonConformityCondition = 'S0120002';
      const conditionalConformityCondition = 'S0120003';
      const countOfNonConformity =
        gridData.filter((row) => row.rstJdg === nonConformityCondition).length > 0;
      const countOfConditionalConformity =
        gridData.filter((row) => row.rstJdg === conditionalConformityCondition).length > 0;

      const conformity = 'S0110001';
      const nonConformity = 'S0110002';
      const conditionalConformity = 'S0110003';
      sytJdg =
        countOfNonConformity > 0
          ? nonConformity
          : countOfConditionalConformity > 0
          ? conditionalConformity
          : conformity;

      const updateData = {
        sytJdg: sytJdg,
      };
      FormUtil.setData(this.resultInputInfo.forms, updateData);
    },
    hasNonResultJudge() {
      const item = FormUtil.getData(this.resultInputInfo.forms);
      return StringUtil.isNotEmpty(item.sytJdg);
    },
    beforeCheckRequestReview() {
      /**
       * ? 선행조건: 1. editedRows가 존재한다면 저장 후 검토요청 할 수 있음
       * ?          2. 실험실이벤트가 있을 경우, 실험실이벤트가 완료되어야 검토요청할 수 있음
       * ?          3. 결과값, 표기값, 결과판정, 결과판정이 부적합인 결과비고까지 CHECK한 뒤 검토요청할 수 있음
       * ?          4. 입력상태값(완료)이어야 검토요청할 수 있음
       * ?          5. 결과판정 값이 있어야 함
       */
      const editedRows = this.resultInputInfo.$grid.getEditedRowItems();
      if (editedRows.length > 0) {
        return this.$warn(this.$message.warn.editedRowsIsPresent);
      }
      if (!this.isCompletedPublished()) {
        return this.$warn(this.$message.warn.notCompletedLabEvent);
      }
      if (!this.isCompletedResultDataForReview()) {
        return this.$warn(this.$message.warn.noCompleteResultData);
      }
      if (!this.isCompletedInputForReview()) {
        return this.$warn(this.$message.warn.afterSaveRequestReview);
      }
      if (!this.hasNonResultJudge()) {
        return this.$warn(this.$message.warn.noResultJudge);
      }
      return this.showModal('requestReviewerModal');
    },
    requestReviewModalReturnDataEvent(approveInfo) {
      let parameter = FormUtil.getData(this.resultInputInfo.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$confirm(this.$message.confirm.requestTestReview).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultInput/requestReview', parameter))
          .then(() => {
            this.$info(this.$message.info.reviewRequest);
            this.init();
            this.getResultInputList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    hasNonConformity() {
      const nonConformity = 'S0120002';
      const editedRows = this.resultInputInfo.$grid.getEditedRowItems();
      return editedRows.filter(
        (row) => row.rstJdg === nonConformity && StringUtil.isEmpty(row.rstRmk),
      ).length > 0
        ? true
        : false;
    },
    isCompletedResultDataForSave() {
      const editedRows = this.resultInputInfo.$grid.getEditedRowItems();
      if (editedRows.length === 0) {
        return this.$warn(this.$message.warn.noSaveGridData);
      } else {
        return editedRows.filter(
          (row) =>
            StringUtil.isEmpty(row.rstVal) ||
            StringUtil.isEmpty(row.markVal) ||
            StringUtil.isEmpty(row.rstJdg),
        ).length === 0
          ? true
          : false;
      }
    },
    beforeCheckSave() {
      /**
       * ? 선행조건: 1. 결과값, 표기값, 결과판정 필수값 확인
       * ?          2. 결과판정이 부적합판정이 있을경우, 결과비고에 부적합판정사유를 입력해야함
       */
      if (!this.isCompletedResultDataForSave()) {
        return this.$warn(this.$message.warn.noCompleteResultData);
      }
      if (this.hasNonConformity()) {
        return this.$warn(this.$message.warn.inputNonConformityReason);
      }
      return this.save();
    },
    save() {
      const formData = FormUtil.getData(this.resultInputInfo.forms);
      const editedRows = this.resultInputInfo.$grid.getEditedRowItems();
      const parameter = {
        editedRowItems: editedRows,
        ...formData,
      };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultInput', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getResultInputList();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      });
    },
    judgementTypeCheck(item) {
      /**
       * ? 결과값이 입력된 row에 대해서만 판정
       */
      if (item.rstVal === null) {
        return;
      }
      const suitableType = 'S0070001';
      const numericExceedType = 'S0070002';
      const numericMoreThanType = 'S0070003';
      const descriptionType = 'S0070004';
      item.jdgTyp === suitableType
        ? this.suitableTypeJdg(item)
        : item.jdgTyp === numericExceedType
        ? this.numericExceedTypeJdg(item)
        : item.jdgTyp === numericMoreThanType
        ? this.numericMoreThanTypeJdg(item)
        : item.jdgTyp === descriptionType
        ? this.descriptionTypeJdg(item)
        : this.noStandardTypeJdg(item);

      this.resultInputInfo.$grid.getGridData();
    },
    suitableTypeJdg(item) {
      const rstVal = item.rstVal;
      const suitable = item.slvJdgCfm;
      const rstJdgCfm = 'S0120001';
      const rstJdgNonCfm = 'S0120002';
      const updateData = {
        _$uid: item._$uid,
        markVal: this.setRstJdgCompareValueEquals(rstVal, suitable)
          ? item.slvJdgCfmNm
          : item.slvJdgNonCfmNm,
        rstJdg: this.setRstJdgCompareValueEquals(rstVal, suitable) ? rstJdgCfm : rstJdgNonCfm,
      };
      this.resultInputInfo.$grid.updateRowsById(updateData);
    },
    setRstJdgCompareValueEquals(rstVal, compareValue) {
      return rstVal == compareValue ? true : false;
    },
    numericExceedTypeJdg(item) {
      const ownType = 'S0060001';
      let slvLow, slvUpp, slvLowEtc, slvUppEtc, rstJdgEtc;
      if (item.specTyp === ownType) {
        slvLow = item.owcSlvLow;
        slvUpp = item.owcSlvUpp;
        slvLowEtc = item.perSlvLow;
        slvUppEtc = item.perSlvUpp;
      } else {
        slvLow = item.perSlvLow;
        slvUpp = item.perSlvUpp;
        slvLowEtc = item.owcSlvLow;
        slvUppEtc = item.owcSlvUpp;
      }

      rstJdgEtc = this.setJudgmentExceedValue(item, slvLowEtc, slvUppEtc);
      const markVal = this.setNumericTypeMarkVal(item);
      const updateData = {
        _$uid: item._$uid,
        markVal,
        rstJdg: this.setJudgmentExceedValue(item, slvLow, slvUpp),
        rstJdgEtc: rstJdgEtc,
      };
      this.resultInputInfo.$grid.updateRowsById(updateData);
      if (rstJdgEtc === 'S0120002') {
        this.$warn('기타 규격 판정이 부적합입니다.');
      }
    },
    setJudgmentExceedValue(item, slvLow, slvUpp) {
      /**
       * 수치값 비교 3가지 경우(초과/미만)
       * 1. 하한값만 있는 경우
       * 2. 상한값만 있는 경우
       * 3. 하한/상한값 모두 있는 경우
       */
      const rstVal = Number(item.rstVal);
      const suitable = 'S0120001';
      const unsuitable = 'S0120002';

      if (StringUtil.isNotEmpty(slvLow) && StringUtil.isEmpty(slvUpp)) {
        return rstVal > slvLow ? suitable : unsuitable;
      } else if (StringUtil.isEmpty(slvLow) && StringUtil.isNotEmpty(slvUpp)) {
        return rstVal < slvUpp ? suitable : unsuitable;
      } else {
        return rstVal > slvLow && rstVal < slvUpp ? suitable : unsuitable;
      }
    },
    setNumericTypeMarkVal(item) {
      if (StringUtil.isEmpty(item.rstDpnt)) {
        return item.rstVal;
      } else {
        // ? 결과소수점이 3이면 4번째 자리에서 반올림하고 3째자리까지 표현
        // ? 부동소수점 처리를 위해 toFixed()를 이용
        const rstVal = Number(item.rstVal);
        const rstDpnt = Number(item.rstDpnt);
        return rstVal.toFixed(rstDpnt);
      }
    },
    numericMoreThanTypeJdg(item) {
      const ownType = 'S0060001';
      let slvLow, slvUpp, slvLowEtc, slvUppEtc, rstJdgEtc;
      if (item.specTyp === ownType) {
        slvLow = item.owcSlvLow;
        slvUpp = item.owcSlvUpp;
        slvLowEtc = item.perSlvLow;
        slvUppEtc = item.perSlvUpp;
      } else {
        slvLow = item.perSlvLow;
        slvUpp = item.perSlvUpp;
        slvLowEtc = item.owcSlvLow;
        slvUppEtc = item.owcSlvUpp;
      }

      rstJdgEtc = this.setJudgmentMoreThanValue(item, slvLowEtc, slvUppEtc);
      const markVal = this.setNumericTypeMarkVal(item);
      const updateData = {
        _$uid: item._$uid,
        markVal,
        rstJdg: this.setJudgmentMoreThanValue(item, slvLow, slvUpp),
        rstJdgEtc: rstJdgEtc,
      };
      this.resultInputInfo.$grid.updateRowsById(updateData);
      if (rstJdgEtc === 'S0120002') {
        this.$warn('기타 규격 판정이 부적합입니다.');
      }
    },
    setJudgmentMoreThanValue(item, slvLow, slvUpp) {
      /**
       * 수치값 비교 3가지 경우(이상/이하)
       * 1. 하한값만 있는 경우
       * 2. 상한값만 있는 경우
       * 3. 하한/상한값 모두 있는 경우
       */
      const rstVal = Number(item.rstVal);
      const suitable = 'S0120001';
      const unsuitable = 'S0120002';

      if (StringUtil.isNotEmpty(slvLow) && StringUtil.isEmpty(slvUpp)) {
        return rstVal >= slvLow ? suitable : unsuitable;
      } else if (StringUtil.isEmpty(slvLow) && StringUtil.isNotEmpty(slvUpp)) {
        return rstVal <= slvUpp ? suitable : unsuitable;
      } else {
        return rstVal >= slvLow && rstVal <= slvUpp ? suitable : unsuitable;
      }
    },
    descriptionTypeJdg(item) {
      const ownType = 'S0060001';
      const rstVal = item.rstVal;
      const suitable = 'S0120001';
      const unsuitable = 'S0120002';
      let slvDesc;

      if (item.specTyp === ownType) {
        slvDesc = item.owcSlvDesc;
      } else {
        slvDesc = item.perSlvDesc;
      }

      const updateData = {
        _$uid: item._$uid,
        markVal: rstVal,
        rstJdg: this.setRstJdgCompareValueEquals(rstVal, slvDesc) ? suitable : unsuitable,
      };
      this.resultInputInfo.$grid.updateRowsById(updateData);
    },
    noStandardTypeJdg(item) {
      const updateData = {
        _$uid: item._$uid,
        markVal: item.rstVal,
      };
      this.resultInputInfo.$grid.updateRowsById(updateData);
    },
    cellEditEndEvent(event) {
      if (event.dataField === 'rstVal') {
        const updateData = {
          _$uid: event.item._$uid,
          markVal: '',
          rstJdg: '',
        };
        this.resultInputInfo.$grid.updateRowsById(updateData);
        this.judgementTypeCheck(event.item);
      }
      this.inputSytJdg();
    },
    enableButtons() {
      const buttons = this.resultInputInfo.buttons.map(({ name }) => name);
      FormUtil.enableButtons(this.resultInputInfo.buttons, buttons);
    },
    disableButtons() {
      const buttons = this.resultInputInfo.buttons.map(({ name }) => name);
      FormUtil.disableButtons(this.resultInputInfo.buttons, buttons);
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        const fileIdx = Number(event.item.fileIdx);
        this.fileInfo = { ansIdx: ansIdx, fileIdx: fileIdx, rstSeq: rstSeq };
        return this.showModal('fileAttacherModal', { fileIdx });
      }
    },
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = parameter.fileIdx;
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'testLabEventModal') {
        this.testLabEventModal.parameter = parameter;
        return (this.testLabEventModal.show = true);
      }
      if (name === 'requestReviewerModal') {
        return (this.requestReviewerModal.show = true);
      }
      if (name === 'resultHistoryModal') {
        this.resultHistoryModal.parameter = parameter;
        return (this.resultHistoryModal.show = true);
      }
      if (name === 'finalOrderModal') {
        const parameter = FormUtil.getData(this.resultInputInfo.forms);
        this.finalOrderModal.parameter = parameter;
        return (this.finalOrderModal.show = true);
      }
      if (name === 'inputPerformanceModal') {
        const parameter = FormUtil.getData(this.resultInputInfo.forms);
        this.inputPerformanceModal.parameter = parameter;
        return (this.inputPerformanceModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'testLabEventModal') {
        return (this.testLabEventModal.show = false);
      }
      if (name === 'requestReviewerModal') {
        return (this.requestReviewerModal.show = false);
      }
      if (name === 'resultHistoryModal') {
        return (this.resultHistoryModal.show = false);
      }
      if (name === 'finalOrderModal') {
        return (this.finalOrderModal.show = false);
      }
      if (name === 'inputPerformanceModal') {
        return (this.inputPerformanceModal.show = false);
      }
    },
    afterModalEvent(ansIdx) {
      this.getResultDetail(ansIdx);
    },
    getAnsIdx() {
      return FormUtil.getValue(this.list.forms, 'ansIdx');
    },
    fileSave({ addedFiles, removedFileIds }) {
      let parameter = this.fileInfo;
      const ansIdx = parameter.ansIdx;
      const fileIdx = parameter.fileIdx;
      parameter = {
        ...parameter,
        addedFiles,
        removedFileIds,
      };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testResultInput/savedFile', parameter)
          .then(({ data }) => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }
            this.getResultInputList();
            this.getResultDetail(ansIdx);
            this.getFileListByFileIdx(fileIdx, data);
          })
          .catch(() => {
            this.$error(this.$message.error.savedFiles);
          });
      });
    },
    getFileListByFileIdx(originFileIdx, fileIdx) {
      if (originFileIdx > 0) {
        return this.$refs.fileAttacherModal.getFileList();
      } else {
        this.fileAttacherModal.fileIdx = fileIdx;
        this.fileInfo.fileIdx = fileIdx;
      }
    },
  },
  computed: {
    computedListColumns() {
      const editableColumns = [];

      return this.list.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
    computedItemListColumns() {
      const editableColumns = [
        'rstVal',
        'markVal',
        'rstJdg',
        'rstRmk',
        'rptPrtYn',
        'rptPrtItmNm',
        'rptPrtSlvVal',
        'rstJdgEtc',
      ];

      return this.resultInputInfo.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
  },
};
</script>
