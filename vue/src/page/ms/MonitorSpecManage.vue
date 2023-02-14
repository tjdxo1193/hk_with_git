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

  <Form-Base v-bind="valueWithMItemSpecList" />

  <Form-Base v-bind="valueWithVersionList" />

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
</template>

<script>
import { ItemsByTestMethodModal, RequestReviewerModal } from '@/page/modal';
import { FormUtil, StringUtil, GridUtil } from '@/util';

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
      valueWithVersionList,
      valueWithMItemSpecList,
    } = this.$copy(values);
    return {
      mItemSpecList: {
        ...mItemSpecList.static,
        forms: mItemSpecList.forms(),
        columns: mItemSpecList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.init();
            this.fetchVersionList(e.item);
            this.settingDepartmentList(e.item);
            this.setValueWithMItemSpecListHiddenForm(e.item);
            this.bottomGridSetting($grid);
          },
        },
      },
      versionList: {
        ...versionList.static,
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.fetchMItemSpecAItemList(e);
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
      valueWithVersionList: {
        forms: valueWithVersionList.forms(),
      },
      valueWithMItemSpecList: {
        forms: valueWithMItemSpecList.forms(),
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
    setValueWithMItemSpecListHiddenForm(item) {
      FormUtil.setData(this.valueWithMItemSpecList.forms, item);
    },
    setValueWithVersionListHiddenForm(item) {
      FormUtil.setData(this.valueWithMItemSpecList.forms, item);
    },
    bottomGridSetting($grid) {
      if (this.isFirstVersionMode()) {
        this.changeButtonWhenSelectedMItem();
      } else {
        const bottomSetting = {
          item: $grid.getItemsByValue('aitmSpecVer', $grid.getRowCount())[0],
          rowIndex: $grid.getRowIndexesByValue('aitmSpecVer', $grid.getRowCount())[0],
        };
        this.fetchMItemSpecAItemList(bottomSetting);
        this.versionList.$grid.setSelectionByIndex(bottomSetting.rowIndex);
        this.changeButtonWhenSelectedVersion();
      }
    },

    async fetchMItemSpecAItemList({ item }) {
      const { $grid } = this.testItemList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecManage/aItem', item))
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
      //사용버전 Y일때 수정(개정)가능
      if (this.isUseVersionY()) {
        FormUtil.enableButtons(this.testItemList.buttons, ['updateVersion', 'addRow', 'removeRow']);
        FormUtil.disableButtons(this.testItemList.buttons, [
          'up',
          'down',
          'temporarySave',
          'requestReview',
        ]);
        return;
      } else {
        //사용버전 N이고, 임시저장또는 검토반려일때 임시저장(수정) 가능
        if (this.isTemporaryStorage() || this.isReviewReject()) {
          FormUtil.enableButtons(this.testItemList.buttons, [
            'temporarySave',
            'requestReview',
            'addRow',
            'removeRow',
          ]);
          FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down', 'updateVersion']);
          return;
        } else {
          FormUtil.disableButtons(this.testItemList.buttons, [
            'temporarySave',
            'requestReview',
            'updateVersion',
            'addRow',
            'removeRow',
            'up',
            'down',
          ]);
          return;
        }
      }
    },
    changeButtonWhenSelectedTestItem() {
      // 사용버전 Y일떄 up,down 가능, 임시저장, 검토반려(N)일때 up,down 가능
      if (
        this.isFirstVersionMode() ||
        this.isTemporaryStorage() ||
        this.isReviewReject() ||
        this.isUseVersionY()
      ) {
        FormUtil.enableButtons(this.testItemList.buttons, ['up', 'down']);
      } else {
        FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
      }
    },
    requestReview(popupParam) {
      const parameter = FormUtil.getData(this.valueWithVersionList.forms);
      Object.assign(parameter, popupParam);

      this.$eSignWithReason(() => this.$axios.put('/ms/monitorSpecManage/requestReview', parameter))
        .then(() => {
          this.$info(this.$message.info.reviewRequest);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    delete() {
      const parameter = FormUtil.getData(this.valueWithVersionList.forms);
      this.$eSignWithReason(() => this.$axios.put('/ms/monitorSpecManage/mSpec/delete', parameter))
        .then(() => {
          this.$info(this.$message.info.delete);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    addRowVersion() {
      this.versionList.$grid.addRow({
        aitmSpecVer: 1,
        mitmCd: FormUtil.getData(this.valueWithMItemSpecList.forms).mitmCd ?? '',
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
    isTemporaryStorage() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionList.forms);
      return specProcCd == 'S0080100';
    },
    isReviewReject() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionList.forms);
      return specProcCd == 'S0080110';
    },
    isApproved() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionList.forms);
      return specProcCd == 'S0080400';
    },
    isUseVersionY() {
      const { useVerYn } = FormUtil.getData(this.valueWithVersionList.forms);
      return useVerYn == 'Y';
    },
    updateVersion() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const gridData = this.testItemList.$grid.getGridData();
      const { mitmCd, aitmSpecVer, rvsDt, enfoDt, rvsCtt, rvsDivPs, rvsReaCd } = FormUtil.getData(
        this.valueWithVersionList.forms,
      );

      const parameter = gridData.map((row, index) => ({
        ...row,
        mitmCd,
        aitmSpecVer,
        rvsDt,
        enfoDt,
        rvsCtt,
        rvsDivPs,
        rvsReaCd,
        aitmOrd: index + 1,
      }));

      this.$confirm(this.$message.warn.updateAprrovedSpec).then(() => {
        this.$eSignWithReason(() => this.$axios.post('/ms/monitorSpecManage/version', parameter))
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

      const gridData = this.testItemList.$grid.getGridData();
      const getSpecInfo = FormUtil.getData(this.valueWithMItemSpecList.forms);

      const parameter = gridData.map((row, index) => ({
        ...row,
        mitmCd: getSpecInfo.mitmCd,
        aitmOrd: index + 1,
      }));

      this.$eSign(() => this.$axios.post('/ms/monitorSpecManage/version', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    saveAllRow() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const gridData = this.testItemList.$grid.getGridData();
      const getVersionInfo = FormUtil.getData(this.valueWithVersionList.forms);
      const parameter = gridData.map((row, index) => ({
        ...row,
        aitmSpecIdx: getVersionInfo.aitmSpecIdx,
        aitmOrd: index + 1,
      }));

      this.$eSignWithReason(() => this.$axios.post('/ms/monitorSpecManage/aItem', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchMItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    addRowTestItem(items) {
      const { aitmSpecIdx } = FormUtil.getData(this.valueWithVersionList.forms);
      this.testItemList.$grid.addRow(
        items.map((row) => ({
          aitmSpecIdx: aitmSpecIdx ?? '',
          amitmCd: row.amitmCd,
          aitmKn: row.aitmKn,
          vriaKn: row.vriaKn,
          vriaNo: row.vriaNo,
        })),
      );
    },
    removeRowTestItem() {
      this.testItemList.$grid.removeRow('selectedIndex');
    },
    upOrd() {
      this.testItemList.$grid.moveRowsToUp();
    },
    downOrd() {
      this.testItemList.$grid.moveRowsToDown();
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
