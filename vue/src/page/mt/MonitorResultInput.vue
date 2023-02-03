<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridSearch
    v-bind="itemList"
    @grid-created="(proxy) => $setState('itemList.$grid', proxy)"
    @button-click="onClickButton"
  />

  <FileAttacherModal
    title="파일등록"
    :show="fileAttacherModal.show"
    @close="hideModal('fileAttacherModal')"
  >
  </FileAttacherModal>

  <RequestReviewerModal
    :show="requestReviewerModal.show"
    @close="hideModal('requestReviewerModal')"
    @modalReturnDataEvent="requestReview"
  />
</template>

<script>
import { FileAttacherModal, RequestReviewerModal } from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/monitorResultInput';

export default {
  name: 'MonitorResultInput',
  components: {
    FileAttacherModal,
    RequestReviewerModal,
  },
  mounted() {
    this.getMonitorTestResultInput();
  },
  data() {
    const { list, itemList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (event) => {
            FormUtil.setData(this.itemList.forms, event.item);
            this.getMonitorTestRst(event);
            this.enableButtons([
              'resultHistory',
              'preResultTrend',
              'addFile',
              'requestReview',
              'save',
              'init',
              'hold',
            ]);
          },
        },
      },
      itemList: {
        ...itemList.static,
        forms: itemList.forms(),
        columns: itemList.columns(),
        event: {
          cellEditEnd: (e) => this.cellEditEndEvent(e),
        },
      },
      fileAttacherModal: {
        show: false,
      },
      requestReviewerModal: {
        show: false,
      },
    };
  },
  methods: {
    async getMonitorTestResultInput() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestResultInput', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    async setSelectByUpperCd(forms, upperCd, targetName) {
      let targetForm = FormUtil.findItem(forms, targetName);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetName);
      resultForm.value = '';
      resultForm.elements = result;
    },
    getMonitorTestRst(event) {
      const mitmReqIdx = event.item.mitmReqIdx;
      this.itemList.$grid
        ._useLoader(() =>
          this.$axios.get('/mt/monitorTestResultInput/getMonitorTestRst', { mitmReqIdx }),
        )
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    save() {
      const formData = FormUtil.getData(this.itemList.forms);
      const editedRows = this.itemList.$grid.getEditedRowItems();
      const parameter = {
        editedRowItems: editedRows,
        ...formData,
      };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.put('/mt/monitorTestResultInput/updateRst', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getMonitorTestResultInput();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      });
    },
    requestReview(popupParam) {
      let parameter = FormUtil.getData(this.itemList.forms);
      let gridData = this.itemList.$grid.getGridData();
      let bool = new Boolean(true);
      gridData.map((item) => {
        if (item.rstProcCd == 'S0140100') {
          this.$warn(this.$message.warn.noCompleteResultData);
          bool = false;
        }
      });
      if (bool) {
        Object.assign(parameter, popupParam);
        this.$eSign(() => this.$axios.put('/mt/monitorTestResultInput/requestReview', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getMonitorTestResultInput();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      }
    },
    hold() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultInput/hold', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getMonitorTestResultInput();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestResultInput();
      }
      if (event.type === 'change' && event.item.name === 'upperMitmPitmDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getMonitorTestResultInput();
      }
      if (name === 'save') {
        return this.beforeCheckSave();
      }
      if (name === 'addFile') {
        this.showModal('fileAttacherModal');
      }
      if (name === 'init') {
        return this.init();
      }
      if (name === 'hold') {
        return this.hold();
      }
      if (name === 'resultHistory') {
        return this.showModal('resultHistoryModal');
      }
      if (name === 'preResultTrend') {
        alert('이전결과동향');
      }
      if (name === 'requestReview') {
        return this.beforeCheckRequestReview();
      }
    },
    init() {
      this.itemList.$grid.clearGridData();
      this.disableButtons([
        'resultHistory',
        'preResultTrend',
        'addFile',
        'requestReview',
        'save',
        'init',
        'hold',
      ]);
    },

    /* request */
    isCompletedResultDataForReview() {
      const gridData = this.itemList.$grid.getGridData();
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
      const gridData = this.itemList.$grid.getGridData();
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
      const gridData = this.itemList.$grid.getGridData();
      const cnt = gridData.filter((row) => row.rstJdg !== null).length;
      console.log(cnt, 'cnt');
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
      FormUtil.setData(this.itemList.forms, updateData);
    },
    beforeCheckRequestReview() {
      /**
       * ? 선행조건: 1. editedRows가 존재한다면 저장 후 검토요청 할 수 있음
       * ?          2. 실험실이벤트가 있을 경우, 실험실이벤트가 완료되어야 검토요청할 수 있음
       * ?          3. 결과값, 표기값, 결과판정, 결과판정이 부적합인 결과비고까지 CHECK한 뒤 검토요청할 수 있음
       * ?          4. 입력상태값(완료)이어야 검토요청할 수 있음
       */
      const editedRows = this.itemList.$grid.getEditedRowItems();
      if (editedRows.length > 0) {
        return this.$warn(this.$message.warn.editedRowsIsPresent);
      }
      if (!this.isCompletedResultDataForReview()) {
        return this.$warn(this.$message.warn.noCompleteResultData);
      }
      if (!this.isCompletedInputForReview()) {
        return this.$warn(this.$message.warn.afterSaveRequestReview);
      }
      return this.showModal('requestReviewerModal');
    },

    /* save */
    hasNonConformity() {
      const nonConformity = 'S0120002';
      const editedRows = this.itemList.$grid.getEditedRowItems();
      return editedRows.filter(
        (row) => row.rstJdg === nonConformity && StringUtil.isEmpty(row.rstRmk),
      ).length > 0
        ? true
        : false;
    },
    isCompletedResultDataForSave() {
      const editedRows = this.itemList.$grid.getEditedRowItems();
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

    /* judge */
    judgementTypeCheck({ item }) {
      /**
       * 결과값이 입력된 row에 대해서만 판정
       */
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
    },
    suitableTypeJdg(item) {
      //TODO 해당 값 체크는 규격서 저장 또는 시험 의뢰가 취소 가능한 단계에서 확인되어야 할듯
      if (StringUtil.isEmpty(item.slvJdgCfm) || StringUtil.isEmpty(item.slvJdgNonCfm)) {
        const message = [this.$message.warn.noSlvJdgCfm, this.$message.warn.requireAtMaster].join(
          ' ',
        );
        return this.$warn(message);
      }

      const rstVal = item.rstVal;
      const suitable = item.slvJdgCfm;

      const updateData = {
        _$uid: item._$uid,
        markVal: this.setRstJdgCompareValueEquals(rstVal, suitable)
          ? item.slvJdgCfmNm
          : item.slvJdgNonCfmNm,
        rstJdg: this.setRstJdgCompareValueEquals(rstVal, suitable) ? 'S0120001' : 'S0120002',
      };
      this.itemList.$grid.updateRowsById(updateData);
    },
    setRstJdgCompareValueEquals(rstVal, compareValue) {
      return rstVal == compareValue ? true : false;
    },
    numericExceedTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';

      if (
        item.specTyp === ownType &&
        !(StringUtil.isNotEmpty(item.owcSlvLow) || StringUtil.isNotEmpty(item.owcSlvUpp))
      ) {
        const message = [
          this.$message.warn.noExcOwcSlvLowOrSlvUpp,
          this.$message.warn.requireAtMaster,
        ].join(' ');
        return this.$warn(message);
      }
      if (
        item.specTyp === permissionType &&
        !(StringUtil.isNotEmpty(item.perSlvLow) || StringUtil.isNotEmpty(item.perSlvUpp))
      ) {
        const message = [
          this.$message.warn.noExcPerSlvLowOrSlvUpp,
          this.$message.warn.requireAtMaster,
        ].join(' ');
        return this.$warn(message);
      }

      let slvLow, slvUpp;
      if (item.specTyp === ownType) {
        slvLow = item.owcSlvLow;
        slvUpp = item.owcSlvUpp;
      } else {
        slvLow = item.perSlvLow;
        slvUpp = item.perSlvUpp;
      }

      const markVal = this.setNumericTypeMarkVal(item);
      const updateData = {
        _$uid: item._$uid,
        markVal,
        rstJdg: this.setJudgmentExceedValue(item, slvLow, slvUpp),
      };
      this.itemList.$grid.updateRowsById(updateData);
    },
    setJudgmentExceedValue(item, slvLow, slvUpp) {
      /**
       * 수치값 비교 3가지 경우(초과/미만)
       * 1. 하한값만 있는 경우
       * 2. 상한값만 있는 경우
       * 3. 하한/상한값 모두 있는 경우
       */
      const rstVal = item.rstVal;
      const suitable = 'S0120001';
      const unsuitable = 'S0120002';
      if (StringUtil.isNotEmpty(slvLow) && StringUtil.isEmpty(slvUpp)) {
        return Number(rstVal) > Number(slvLow) ? suitable : unsuitable;
      } else if (StringUtil.isEmpty(slvLow) && StringUtil.isNotEmpty(slvUpp)) {
        return Number(rstVal) < Number(slvUpp) ? suitable : unsuitable;
      } else {
        return Number(rstVal) > Number(slvLow) && Number(rstVal) < Number(slvUpp)
          ? suitable
          : unsuitable;
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
      const permissionType = 'S0060002';

      if (
        item.specTyp === ownType &&
        !(StringUtil.isNotEmpty(item.owcSlvLow) || StringUtil.isNotEmpty(item.owcSlvUpp))
      ) {
        const message = [
          this.$message.warn.noMoreOwcSlvLowOrSlvUpp,
          this.$message.warn.requireAtMaster,
        ].join(' ');
        return this.$warn(message);
      }
      if (
        item.specTyp === permissionType &&
        !(StringUtil.isNotEmpty(item.perSlvLow) || StringUtil.isNotEmpty(item.perSlvUpp))
      ) {
        const message = [
          this.$message.warn.noMorePerSlvLowOrSlvUpp,
          this.$message.warn.requireAtMaster,
        ].join(' ');
        return this.$warn(message);
      }

      let slvLow, slvUpp;
      if (item.specTyp === ownType) {
        slvLow = item.owcSlvLow;
        slvUpp = item.owcSlvUpp;
      } else {
        slvLow = item.perSlvLow;
        slvUpp = item.perSlvUpp;
      }

      const markVal = this.setNumericTypeMarkVal(item);
      const updateData = {
        _$uid: item._$uid,
        markVal,
        rstJdg: this.setJudgmentMoreThanValue(item, slvLow, slvUpp),
      };
      this.itemList.$grid.updateRowsById(updateData);
    },
    setJudgmentMoreThanValue(item, slvLow, slvUpp) {
      /**
       * 수치값 비교 3가지 경우(이상/이하)
       * 1. 하한값만 있는 경우
       * 2. 상한값만 있는 경우
       * 3. 하한/상한값 모두 있는 경우
       */
      const rstVal = item.rstVal;
      const suitable = 'S0120001';
      const unsuitable = 'S0120002';

      if (StringUtil.isNotEmpty(slvLow) && StringUtil.isEmpty(slvUpp)) {
        return Number(rstVal) >= Number(slvLow) ? suitable : unsuitable;
      } else if (StringUtil.isEmpty(slvLow) && StringUtil.isNotEmpty(slvUpp)) {
        return Number(rstVal) <= Number(slvUpp) ? suitable : unsuitable;
      } else {
        return Number(rstVal) >= Number(slvLow) && Number(rstVal) <= Number(slvUpp)
          ? suitable
          : unsuitable;
      }
    },
    descriptionTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';
      if (item.specTyp === permissionType && StringUtil.isEmpty(item.perSlvDesc)) {
        const message = [this.$message.warn.noPerSlvDesc, this.$message.warn.requireAtMaster].join(
          ' ',
        );
        return this.$warn(message);
      }
      if (item.specTyp === ownType && StringUtil.isEmpty(item.owcSlvDesc)) {
        const message = [this.$message.warn.noOwcSlvDesc, this.$message.warn.requireAtMaster].join(
          ' ',
        );
        return this.$warn(message);
      }

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
      this.itemList.$grid.updateRowsById(updateData);
    },
    noStandardTypeJdg(item) {
      const updateData = {
        _$uid: item._$uid,
        markVal: item.rstVal,
      };
      this.itemList.$grid.updateRowsById(updateData);
    },
    cellEditEndEvent(event) {
      if (event.dataField === 'rstVal') {
        const updateData = {
          _$uid: event.item._$uid,
          markVal: '',
          rstJdg: '',
          rstRmk: '',
        };
        this.itemList.$grid.updateRowsById(updateData);
        this.judgementTypeCheck(event);
      }
      this.inputSytJdg();
    },

    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.itemList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.itemList.buttons, buttons);
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
  },
};
</script>

<style></style>
