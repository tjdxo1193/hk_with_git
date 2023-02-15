<template>
  <AUIGridSearch
    v-bind="mItemSpecList"
    @grid-created="(proxy) => $setState('mItemSpecList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <AUIGridWithHeader
    v-bind="versionList"
    @grid-created="(proxy) => $setState('versionList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <AUIGridWithHeader
    v-bind="testItemList"
    @grid-created="(proxy) => $setState('testItemList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <ItemsByTestMethodModal
    :show="itemsByTestMethodModal.show"
    @close="hideItemsByTestMethodModal()"
    :activedGrid="testItemList.$grid"
    @select="addRowTestItem"
  />

  <RequestReviewerModal
    :show="requestReviewerModal.show"
    @close="hideRequestReviewerModal()"
    @modalReturnDataEvent="requestReview"
  />

  <FormBase v-bind="valueWithMItemSpecGrid" />

  <FormBase v-bind="valueWithVersionGrid" />
</template>

<script>
import { ItemsByTestMethodModal, RequestReviewerModal } from '@/page/modal';
import { FormUtil, GridUtil, StringUtil } from '@/util';

import values from './values/monitorSpecManage';

export default {
  name: 'monitorSpecManage',
  mounted() {
    this.fetchMItemSpecList();
  },
  components: {
    ItemsByTestMethodModal,
    RequestReviewerModal,
  },
  data() {
    const {
      mItemSpecList,
      versionList,
      testItemList,
      valueWithMItemSpecGrid,
      valueWithVersionGrid,
    } = this.$copy(values);
    return {
      mItemSpecList: {
        ...mItemSpecList.static,
        forms: mItemSpecList.forms(),
        columns: mItemSpecList.columns(),
        event: {
          cellDoubleClick: async (e) => {
            this.init();
            await this.fetchVersionList(e.item);
            this.settingDepartmentList(e.item);
            this.setValueWithMItemSpecGrid(e.item);
            this.focusFirstRowItemOfVersionGrid();
          },
        },
      },
      versionList: {
        ...versionList.static,
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.fetchMItemSpecAItemList(e.item);
            this.changeButtonWhenSelectedVersion();
          },
        },
      },
      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
        event: {
          cellDoubleClick: () => {
            this.changeButtonWhenSelectedTestItem();
          },
          cellEditBegin: (e) => {
            this.setDropDownList(e.item);
          },
          cellEditEnd: (e) => {
            this.changeModifiableColumn(e);
          },
        },
      },
      valueWithMItemSpecGrid: {
        forms: valueWithMItemSpecGrid.forms(),
      },
      valueWithVersionGrid: {
        forms: valueWithVersionGrid.forms(),
      },

      itemsByTestMethodModal: {
        show: false,
      },
      requestReviewerModal: {
        show: false,
      },
      processCode: {
        temporarySave: 'S0080100',
      },
      departmentList: {
        list: [],
      },
    };
  },
  methods: {
    async fetchMItemSpecList() {
      const { $grid, forms } = this.mItemSpecList;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecManage/mSpec', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },

    async fetchVersionList({ mitmCd }) {
      const { $grid } = this.versionList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecManage/version', { mitmCd }))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },

    setValueWithMItemSpecGrid(item) {
      FormUtil.setData(this.valueWithMItemSpecGrid.forms, item);
    },

    setValueWithVersionGrid(item) {
      FormUtil.setData(this.valueWithVersionGrid.forms, item);
    },

    focusFirstRowItemOfVersionGrid() {
      const { $grid } = this.versionList;
      if (this.isFirstVersionMode()) {
        this.changeButtonWhenSelectedMItem();
      } else {
        $grid.setSelectionByIndex(0);
        const item = $grid.getSelectedRows()[0];
        this.loadToVersionFormAndTestListGrid(item);
      }
    },

    loadToVersionFormAndTestListGrid(item) {
      this.setValueWithVersionGrid(item);
      const { aitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);
      this.fetchMItemSpecAItemList({ aitmSpecIdx });
      this.changeButtonWhenSelectedVersion();
    },

    async fetchMItemSpecAItemList({ aitmSpecIdx }) {
      const { $grid } = this.testItemList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecManage/aItem', { aitmSpecIdx }))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.fetchMItemSpecList();
        this.init();
        return;
      }
      if (name == 'up') {
        this.upOrd();
        return;
      }
      if (name == 'down') {
        this.downOrd();
        return;
      }
      if (name == 'temporarySave') {
        this.isTestListGridEmpty()
          ? this.$error(this.$message.warn.noSaveGridData)
          : this.isFirstVersionMode()
          ? this.saveFirstVersion()
          : this.saveAllRow();
        return;
      }
      if (name == 'requestReview') {
        this.testItemList.$grid.getEditedRowItems().length == 0
          ? this.showRequestReviewerModal()
          : this.$warn(this.$message.warn.editedRowsIsPresent);
        return;
      }
      if (name == 'updateVersion') {
        this.updateVersion();
      }
      if (name == 'addRow') {
        this.showItemsByTestMethodModal();
        return;
      }
      if (name == 'removeRow') {
        this.removeRowTestItem();
        return;
      }
    },
    init() {
      this.versionList.$grid.clearGridData();
      this.testItemList.$grid.clearGridData();
      this.valueWithMItemSpecGrid.forms = values.valueWithMItemSpecGrid.forms();
      this.valueWithVersionGrid.forms = values.valueWithVersionGrid.forms();
      this.changeButtonDisabledAll();
    },
    changeButtonDisabledAll() {
      FormUtil.disableButtons(this.testItemList.buttons, [
        'up',
        'down',
        'requestReview',
        'updateVersion',
        'temporarySave',
        'addRow',
        'removeRow',
      ]);
    },
    changeButtonWhenSelectedMItem() {
      FormUtil.enableButtons(this.testItemList.buttons, ['temporarySave', 'addRow', 'removeRow']);
      FormUtil.disableButtons(this.testItemList.buttons, [
        'up',
        'down',
        'requestReview',
        'updateVersion',
      ]);
    },

    isFirstVersionMode() {
      return this.versionList.$grid.getRowCount() == 0;
    },
    isTestListGridEmpty() {
      return this.testItemList.$grid.getRowCount() == 0;
    },
    changeButtonWhenSelectedVersion() {
      const buttons = this.testItemList.buttons;

      if (this.isSelectedUsableVersion()) {
        FormUtil.enableButtons(buttons, ['updateVersion', 'addRow', 'copyRow', 'removeRow']);
        return;
      }

      // 임시저장, 검토반려일때
      if (this.isSelectedTemporaryVersion() || this.isSelectedReviewRejectVersion()) {
        FormUtil.enableButtons(buttons, ['temporarySave', 'addRow', 'copyRow', 'removeRow']);

        if (this.isSelectedItemHasVersion()) {
          FormUtil.enableButtons(buttons, ['requestReview']);
        }

        return;
      }
    },
    changeButtonWhenSelectedTestItem() {
      // 사용버전 Y일떄 up,down 가능, 임시저장, 검토반려(N)일때 up,down 가능
      if (
        this.isFirstVersionMode() ||
        this.isSelectedTemporaryVersion() ||
        this.isSelectedReviewRejectVersion() ||
        this.isSelectedUsableVersion()
      ) {
        FormUtil.enableButtons(this.testItemList.buttons, ['up', 'down']);
      } else {
        FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
      }
    },

    isSelectedUsableVersion() {
      const { useVerYn } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return useVerYn == 'Y';
    },

    isSelectedTemporaryVersion() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == 'S0080100';
    },

    isSelectedReviewRejectVersion() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == 'S0080110';
    },

    isSelectedItemHasVersion() {
      return !this.isSelectedItemHasNotVersion();
    },

    isSelectedItemHasNotVersion() {
      const { $grid } = this.versionList;
      const parameter = FormUtil.getData(this.valueWithVersionGrid.forms);

      return $grid.getRowCount() == 1 && !parameter.aitmSpecVer;
    },

    requestReview(popupParam) {
      const { mitmSpecIdx, aitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);
      popupParam.mitmSpecIdx = mitmSpecIdx;
      popupParam.revwUid = popupParam.aprUid;

      if (!aitmSpecIdx) {
        return this.$error(this.$message.warn.noAitmSpecIdx);
      }

      this.$eSignWithReason(() =>
        this.$axios.put('/ms/monitorSpecManage/requestReview', popupParam),
      )
        .then(() => {
          this.$info(this.$message.info.reviewRequest);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    showItemsByTestMethodModal() {
      this.$setState('itemsByTestMethodModal', { show: true });
    },

    hideItemsByTestMethodModal() {
      this.$setState('itemsByTestMethodModal', { show: false });
    },

    showRequestReviewerModal() {
      this.$setState('requestReviewerModal', { show: true });
    },

    hideRequestReviewerModal() {
      this.$setState('requestReviewerModal', { show: false });
    },

    copyRowToPreviousVersion() {
      let copyData = this.versionList.$grid.getItemsByValue(
        'aitmSpecVer',
        this.versionList.$grid.getRowCount(),
      );
      copyData.aitmSpecVer = copyData.aitmSpecVer + 1;
      copyData.specProcCd = '';
      this.versionList.$grid.addRow(copyData);
    },

    saveCopyAndUpVersion() {
      this.copyRowToPreviousVersion();
      const checkedRows = this.versionList.$grid.getAddedRowItems();
      if (!checkedRows) {
        this.$warn(this.$message.validate.noEditedRowItems);
        return;
      }
      this.$eSignWithReason(() => this.$axios.post('/ms/monitorSpecManage/version', checkedRows[0]))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    updateVersion() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const versionParam = FormUtil.getData(this.valueWithVersionGrid.forms);

      const parameter = {
        ...versionParam,
        addedRowItems: this.testItemList.$grid.getGridData(),
        editedRowItems: [],
        removedRowItems: [],
      };

      this.$confirm(this.$message.warn.updateAprrovedSpec).then(() => {
        this.$eSignWithReason(() => this.$axios.post('/ms/monitorSpecManage/newVersion', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.fetchMItemSpecList();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },

    saveFirstVersion() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const Mitmparam = FormUtil.getData(this.valueWithMItemSpecGrid.forms);

      const parameter = {
        ...Mitmparam,
        addedRowItems: this.testItemList.$grid.getGridData(),
        editedRowItems: [],
        removedRowItems: [],
      };

      this.$eSign(() => this.$axios.post('/ms/monitorSpecManage/firstVersion', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    async saveAllRow() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const { $grid } = this.testItemList;
      const { aitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);

      const parameter = {
        aitmSpecIdx,
        addedRowItems: $grid.getAddedRowItems(),
        editedRowItems: $grid.getEditedRowItems(),
        removedRowItems: $grid.getRemovedItems(),
      };

      if (this.isNotUpdateTestItemList()) {
        return this.$warn(this.$message.warn.noSaveGridData);
      }

      await this.$eSignWithReason(() => this.$axios.post('/ms/monitorSpecManage/aItem', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    isNotUpdateTestItemList() {
      const { $grid } = this.testItemList;
      return (
        $grid.getAddedRowItems().length == 0 &&
        $grid.getEditedRowItems().length == 0 &&
        $grid.getRemovedItems().length == 0
      );
    },

    changeModifiableColumn({ value, rowIndex, dataField, item }) {
      const grid = this.testItemList.$grid;
      const isColNameJudgmentType = dataField == 'jdgTyp';
      const judgmentTypeCode = isColNameJudgmentType ? value : item.jdgTyp;
      if (judgmentTypeCode == null) {
        return;
      }
      const isConformityCode = judgmentTypeCode == 'S0070001';
      const isNumericCode = judgmentTypeCode == 'S0070002' || judgmentTypeCode == 'S0070003';
      const isNarrativeCode = judgmentTypeCode == 'S0070004';
      const isNoStandardCode = judgmentTypeCode == 'S0070005';

      if (isConformityCode) {
        grid.setCellValue(rowIndex, 'perSlvLow', '');
        grid.setCellValue(rowIndex, 'perSlvUpp', '');
        grid.setCellValue(rowIndex, 'perSlvDesc', '');
        grid.setCellValue(rowIndex, 'owcSlvLow', '');
        grid.setCellValue(rowIndex, 'owcSlvUpp', '');
        grid.setCellValue(rowIndex, 'owcSlvDesc', '');
        return;
      }

      if (isNumericCode) {
        grid.setCellValue(rowIndex, 'perSlvDesc', '');
        grid.setCellValue(rowIndex, 'owcSlvDesc', '');
        grid.setCellValue(rowIndex, 'slvJdgCfm', '');
        grid.setCellValue(rowIndex, 'slvJdgNonCfm', '');
        return;
      }

      if (isNarrativeCode) {
        grid.setCellValue(rowIndex, 'perSlvLow', '');
        grid.setCellValue(rowIndex, 'perSlvUpp', '');
        grid.setCellValue(rowIndex, 'owcSlvLow', '');
        grid.setCellValue(rowIndex, 'owcSlvUpp', '');
        grid.setCellValue(rowIndex, 'slvJdgCfm', '');
        grid.setCellValue(rowIndex, 'slvJdgNonCfm', '');
        return;
      }

      if (isNoStandardCode) {
        grid.setCellValue(rowIndex, 'perSlvLow', '');
        grid.setCellValue(rowIndex, 'perSlvUpp', '');
        grid.setCellValue(rowIndex, 'perSlvDesc', '');
        grid.setCellValue(rowIndex, 'owcSlvLow', '');
        grid.setCellValue(rowIndex, 'owcSlvUpp', '');
        grid.setCellValue(rowIndex, 'owcSlvDesc', '');
        grid.setCellValue(rowIndex, 'slvJdgCfm', '');
        grid.setCellValue(rowIndex, 'slvjdgNonCfm', '');
        return;
      }
    },
    isNotExistJdgType() {
      const isValid = this.testItemList.$grid.validateGridData(
        ['jdgTyp', 'specTyp'],
        '필수 필드는 반드시 값을 직접 입력해야 합니다.',
      );

      return !isValid;
    },
    isNotExistStandardValueByJdgType() {
      const gridData = this.testItemList.$grid.getGridData();

      let invalid = false;

      gridData.some((element) => {
        if (this.invalidStandardValueByjdgType(element)) {
          invalid = true;
          return true;
        }
      });

      return invalid;
    },

    invalidStandardValueByjdgType(element) {
      const suitableType = 'S0070001';
      const numericExceedType = 'S0070002';
      const numericMoreThanType = 'S0070003';
      const descriptionType = 'S0070004';
      if (element.jdgTyp === suitableType) {
        return this.suitableTypeJdg(element);
      }
      if (element.jdgTyp === numericExceedType) {
        return this.numericExceedTypeJdg(element);
      }
      if (element.jdgTyp === numericMoreThanType) {
        return this.numericMoreThanTypeJdg(element);
      }
      if (element.jdgTyp === descriptionType) {
        return this.descriptionTypeJdg(element);
      }
      return false;
    },

    suitableTypeJdg(item) {
      if (StringUtil.isEmpty(item.slvJdgCfm) || StringUtil.isEmpty(item.slvJdgNonCfm)) {
        this.$warn(this.$message.warn.noSlvJdgCfm);
        return true;
      }
      return false;
    },

    numericExceedTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';
      if (
        item.specTyp === ownType &&
        (StringUtil.isEmpty(item.owcSlvLow) || StringUtil.isEmpty(item.owcSlvUpp))
      ) {
        this.$warn(this.$message.warn.noExcOwcSlvLowOrSlvUpp);
        return true;
      } else if (
        item.specTyp === permissionType &&
        (StringUtil.isEmpty(item.perSlvLow) || StringUtil.isEmpty(item.perSlvUpp))
      ) {
        this.$warn(this.$message.warn.noExcPerSlvLowOrSlvUpp);
        return true;
      } else {
        return false;
      }
    },
    numericMoreThanTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';

      if (
        item.specTyp === ownType &&
        (StringUtil.isEmpty(item.owcSlvLow) || StringUtil.isEmpty(item.owcSlvUpp))
      ) {
        this.$warn(this.$message.warn.noMoreOwcSlvLowOrSlvUpp);
        return true;
      } else if (
        item.specTyp === permissionType &&
        (StringUtil.isEmpty(item.perSlvLow) || StringUtil.isEmpty(item.perSlvUpp))
      ) {
        this.$warn(this.$message.warn.noMorePerSlvLowOrSlvUpp);
        return true;
      } else {
        return false;
      }
    },
    descriptionTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';
      if (item.specTyp === permissionType && StringUtil.isEmpty(item.perSlvDesc)) {
        this.$warn(this.$message.warn.noPerSlvDesc);
        return true;
      } else if (item.specTyp === ownType && StringUtil.isEmpty(item.owcSlvDesc)) {
        this.$warn(this.$message.warn.noOwcSlvDesc);
        return true;
      } else {
        return false;
      }
    },

    addRowTestItem(items) {
      const { aitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);
      const { $grid } = this.testItemList;
      $grid.addRow(
        items.map((row, idx) => ({
          aitmSpecIdx: aitmSpecIdx ?? '',
          amitmCd: row.amitmCd,
          aitmKn: row.aitmKn,
          vriaKn: row.vriaKn,
          vriaNo: row.vriaNo,
          aitmOrd: $grid.getRowCount() + idx + 1,
        })),
      );
    },

    removeRowTestItem() {
      const { $grid } = this.testItemList;
      const startRowIndex = $grid.getSelectedIndex()[0];
      const endRowIndex = $grid.getRowCount() - 1;

      this.testItemList.$grid.removeRow('selectedIndex');

      if (startRowIndex == endRowIndex) {
        return;
      }

      const indexArray = [...Array(endRowIndex - startRowIndex).keys()].map(
        (x) => x + startRowIndex,
      );

      const indexList = indexArray.map((_, index) => ({
        aitmOrd: indexArray[index] + 1,
      }));

      $grid.updateRows(indexList, indexArray);
    },

    upOrd() {
      const { $grid } = this.testItemList;
      const rowIndex = $grid.getSelectedIndex()[0];
      if (rowIndex == 0) {
        return;
      }
      $grid.updateRows(
        [{ aitmOrd: rowIndex }, { aitmOrd: rowIndex + 1 }],
        [rowIndex, rowIndex - 1],
      );
      this.testItemList.$grid.moveRowsToUp();
    },

    downOrd() {
      const { $grid } = this.testItemList;
      const rowIndex = $grid.getSelectedIndex()[0];
      if (rowIndex == $grid.getRowCount() - 1) {
        return;
      }
      $grid.updateRows(
        [{ aitmOrd: rowIndex + 2 }, { aitmOrd: rowIndex + 1 }],
        [rowIndex, rowIndex + 1],
      );
      $grid.moveRowsToDown();
    },

    settingDepartmentList({ crgDptCd }) {
      if (crgDptCd == null || crgDptCd == '') {
        this.departmentList.list = [];
        return;
      }
      this.$axios.get('/ms/monitorSpecManage/departmentList', { crgDptCd }).then(({ data }) => {
        this.departmentList.list = data;
      });
    },
    setDropDownList() {
      const { editRenderer } = GridUtil.findColumn(this.testItemList.columns, 'ansDptCd');
      editRenderer.list = this.departmentList.list;
    },
  },
};
</script>

<style></style>
