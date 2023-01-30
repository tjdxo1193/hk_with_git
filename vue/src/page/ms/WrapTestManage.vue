<template>
  <Horizontal align-item="start" :spans="[5, 5]">
    <Vertical>
      <AUIGridSearch
        v-bind="qmPkgaList"
        :height="'470px'"
        @button-click="onClickBtnEvent"
        @enter="qmPkgaListEnter"
        @grid-created="(proxy) => $setState('qmPkgaList.$grid', proxy)"
      />

      <FormBase v-bind="valueWithqmPkgaListGrid" />
    </Vertical>

    <Vertical>
      <AUIGridWithHeader
        v-bind="versionList"
        :height="'395px'"
        @button-click="onClickBtnEvent"
        @grid-created="(proxy) => $setState('versionList.$grid', proxy)"
      />

      <FormWithHeader
        v-bind="{ ...versionForm }"
        :buttons="versionFormButtons"
        @button-click="onClickBtnEvent"
      />
    </Vertical>
  </Horizontal>
  <Card>
    <TabBase v-bind="tabs">
      <template #tab-testItemList>
        <AUIGridWithHeader
          v-bind="testItemList"
          :height="'200px'"
          @button-click="onClickBtnEvent"
          @grid-created="(proxy) => $setState('testItemList.$grid', proxy)"
        />
      </template>

      <template #tab-specList>
        <AUIGridWithHeader
          v-bind="specList"
          :height="'200px'"
          @grid-created="(proxy) => $setState('specList.$grid', proxy)"
        />
      </template>
    </TabBase>
  </Card>

  <ItemsByTestMethodModal
    :activedGrid="testItemList.$grid"
    :show="itemsByTestMethodModal.show"
    @close="hideItemsByTestMethodModal()"
    @select="addTestItemClick"
  />
</template>

<script>
import { ItemsByTestMethodModal } from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/wrapTestManage';

export default {
  name: 'WrapTestManage',
  components: { ItemsByTestMethodModal },
  data() {
    const {
      qmPkgaList,
      versionList,
      versionForm,
      versionFormRequiredList,
      tabs,
      testItemList,
      specList,
      valueWithqmPkgaListGrid,
    } = this.$copy(values);
    return {
      qmPkgaList: {
        ...qmPkgaList.static,
        forms: qmPkgaList.forms(),
        columns: qmPkgaList.columns(),
        isDisabledButton: true,
        event: {
          cellDoubleClick: async (event) => {
            this.resetSubGridList();
            await this.fetchVersionList(event.item);
            this.setPkgaInfoToPkgaGridValueForm(event.item);
            this.focusFirstRowItemOfVersionGrid();
            await this.fetchSpecList(event.item);
          },
        },
      },
      versionList: {
        ...versionList.static,
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.versionListClicked(event);
          },
        },
      },
      versionForm: {
        ...versionForm.static,
        forms: versionForm.forms(),
        isDisabledButton: true,
      },
      versionFormRequiredList: versionFormRequiredList,
      tabs,
      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
        event: {
          cellDoubleClick: () => {
            this.testItemListClicked();
          },
          cellEditEnd: (e) => {
            this.changeModifiableColumn(e);
          },
        },
      },
      specList: {
        ...specList.static,
        columns: specList.columns(),
      },
      itemsByTestMethodModal: {
        show: false,
      },
      processCode: {
        approved: 'S0080400',
        temporarySave: 'S0080100',
      },
      valueWithqmPkgaListGrid: {
        forms: valueWithqmPkgaListGrid.forms(),
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.resetVersionForm();
      this.resetSubGridList();

      this.changeButtonDisabledAll();
      this.enableTemporarySaveButton();
      this.enableAddTestItemButton();

      this.fetchQmPkgaList();
    },
    async fetchQmPkgaList() {
      const { $grid, forms } = this.qmPkgaList;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    async fetchVersionList({ pkgaCd, plntCd }) {
      const versionListGrid = this.versionList.$grid;
      const parameter = { pkgaCd, plntCd };

      const data = await versionListGrid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage/getVersion', parameter))
        .then(({ data }) => data);

      versionListGrid.setGridData(data);
    },
    async fetchTestItemList(item) {
      const testItemGrid = this.testItemList.$grid;
      const parameter = item;

      Object.keys(parameter).forEach((key) => !parameter[key] && delete parameter[key]);

      const data = await testItemGrid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage/getTestItem', parameter))
        .then(({ data }) => data);

      testItemGrid.setGridData(data);
    },
    async fetchSpecList() {
      const specListGrid = this.specList.$grid;
      const parameter = FormUtil.getData(this.valueWithqmPkgaListGrid.forms);

      const data = await specListGrid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage/getSpec', parameter))
        .then(({ data }) => data);

      specListGrid.setGridData(
        data.filter((row) => row.specProcCd != this.processCode.temporarySave),
      );
    },

    isSapPrdhaDuplicate(sapPrdha) {
      this.$axios
        .get('ms/wrapTestManage/getSapPrdhaDuplicateCheck', { sapPrdha })
        .then(({ data }) => {
          if (data > 0) {
            this.$error(this.$message.error.isSapPrdhaDuplicate);
            return true;
          }
        })
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      return false;
    },

    setPkgaInfoToPkgaGridValueForm(item) {
      FormUtil.setData(this.valueWithqmPkgaListGrid.forms, item);
    },

    focusFirstRowItemOfVersionGrid() {
      const { $grid } = this.versionList;
      $grid.setSelectionByIndex(0);
      const item = $grid.getSelectedRows()[0];
      this.loadToVersionFormAndTestListGrid(item);
    },

    loadToVersionFormAndTestListGrid(item) {
      FormUtil.setData(this.versionForm.forms, item);
      // 버튼 제어
      // 버전 리스트 fetch
      if (item) {
        this.checkSpecListSpecProcCd();
        this.fetchTestItemList(item);
      }
    },

    versionListClicked(event) {
      this.fetchSpecList();
      this.fetchTestItemList(event.item);
      this.setVersionForm(event);
      this.checkSpecListSpecProcCd();
    },
    setVersionForm({ item = null }) {
      if (item) {
        const { forms } = this.versionForm;
        FormUtil.setData(forms, item);
      }
    },
    checkSpecListSpecProcCd() {
      const specListGrid = this.specList.$grid;
      const specData = specListGrid.getGridData();
      const checkNotApproved = specData.some((data) => !this.isApproved(data.specProcCd));

      if (checkNotApproved) {
        this.changeButtonDisabledAll();
      } else {
        this.changeButtonWhenSelectedVersion();
      }
    },
    resetSubGridList() {
      this.resetVersionListGrid();
      this.resetTestItemListGrid();
      this.resetSpecListGrid();
    },
    resetVersionListGrid() {
      this.versionList.$grid.clearGridData();
    },
    resetVersionForm() {
      this.versionForm.forms = values.versionForm.forms();
    },
    resetTestItemListGrid() {
      this.testItemList.$grid.clearGridData();
    },
    resetSpecListGrid() {
      this.specList.$grid.clearGridData();
    },
    isUse(useVerYn) {
      return useVerYn == 'Y';
    },
    isFirst(specProcCd) {
      return !specProcCd;
    },
    isTemporaryStorage(specProcCd) {
      return specProcCd == 'S0080100';
    },
    isReviewReject(specProcCd) {
      return specProcCd == 'S0080110';
    },
    isReviewRequested(specProcCd) {
      return specProcCd == 'S0080200';
    },
    isApprovedReject(specProcCd) {
      return specProcCd == 'S0080210';
    },
    isReviewWait(specProcCd) {
      return specProcCd == 'S0080300';
    },
    isApproved(specProcCd) {
      return specProcCd == 'S0080400';
    },
    changeButtonDisabledAll() {
      FormUtil.disableButtons(this.versionForm.buttons, [
        'temporarySave',
        'approval',
        'updateVersion',
      ]);
      FormUtil.disableButtons(this.testItemList.buttons, [
        'up',
        'down',
        'addTestItem',
        'removeTestItem',
      ]);
    },
    controllEditable(boolean) {
      this.versionFormEditable(boolean);
      this.testItemGridEditable(boolean);
    },
    versionFormEditable(boolean) {
      const { forms } = this.versionForm;

      if (boolean) {
        FormUtil.enable(forms, 'pkgaDiv');
        FormUtil.enable(forms, 'sapPrdha');
      } else {
        FormUtil.disable(forms);
      }
    },
    testItemGridEditable(boolean) {
      const { $grid } = this.testItemList;
      $grid.setProp({ editable: boolean });
    },
    qmPkgaListEnter({ type, originEvent }) {
      if (type == 'keydown' && originEvent.key == 'Enter') {
        this.onClickBtnEvent({ name: 'search' });
      }
    },
    onClickBtnEvent({ name }) {
      if (name == 'search' || name == 'reset') {
        this.doInit();
      } else if (name == 'up') {
        this.upOrd();
      } else if (name == 'down') {
        this.downOrd();
      } else if (name == 'temporarySave') {
        this.isTestListGridChanged() ||
        (this.isTestListGridNotEmpty() && this.isVersionFormChanged())
          ? this.preputQmPkga()
          : this.$error(this.$message.warn.noSaveGridData);
      } else if (name == 'updateVersion') {
        this.preputQmPkga();
      } else if (name == 'approval') {
        this.approval();
      } else if (name == 'addTestItem') {
        this.showItemsByTestMethodModal();
      } else if (name == 'removeTestItem') {
        this.removeTestItem();
      }

      return;
    },
    isTestListGridChanged() {
      const { $grid } = this.testItemList;
      let testItemParameter = [
        ...$grid.getEditedRowItems(),
        ...$grid.getAddedRowItems(),
        ...$grid.getRemovedItems(),
      ];

      return testItemParameter.length;
    },
    isTestListGridNotEmpty() {
      const { $grid } = this.testItemList;

      return $grid.getGridData().length > 0;
    },
    isVersionFormChanged() {
      const { forms } = this.versionForm;
      const selectedGridData = this.versionList.$grid.getSelectedRows()[0];
      Object.keys(selectedGridData).forEach(
        (key) => !selectedGridData[key] && delete selectedGridData[key],
      );

      const formData = FormUtil.getData(forms);

      return this.versionFormRequiredList.some(
        (requiredAttribute) => selectedGridData[requiredAttribute] != formData[requiredAttribute],
      );
    },
    changeButtonWhenSelectedVersion() {
      const { specProcCd, useVerYn } = FormUtil.getData(this.versionForm.forms);
      if (!this.isUse(useVerYn)) {
        if (this.isFirst(specProcCd)) {
          this.controllEditable(true);

          FormUtil.enableButtons(this.versionForm.buttons, ['temporarySave']);
          FormUtil.disableButtons(this.versionForm.buttons, ['approval', 'updateVersion']);

          FormUtil.enableButtons(this.testItemList.buttons, ['addTestItem', 'removeTestItem']);
          FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
        } else if (this.isTemporaryStorage(specProcCd)) {
          this.controllEditable(true);

          FormUtil.enableButtons(this.versionForm.buttons, ['temporarySave', 'approval']);
          FormUtil.disableButtons(this.versionForm.buttons, ['updateVersion']);

          FormUtil.enableButtons(this.testItemList.buttons, ['addTestItem', 'removeTestItem']);
          FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
        } else if (this.isReviewReject(specProcCd) || this.isApprovedReject(specProcCd)) {
          this.controllEditable(true);

          FormUtil.enableButtons(this.versionForm.buttons, ['temporarySave', 'approval']);
          FormUtil.disableButtons(this.versionForm.buttons, ['updateVersion']);

          FormUtil.enableButtons(this.testItemList.buttons, ['addTestItem', 'removeTestItem']);
          FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
        } else if (
          this.isReviewRequested(specProcCd) ||
          this.isReviewWait(specProcCd) ||
          this.isApproved(specProcCd)
        ) {
          this.controllEditable(false);
          this.changeButtonDisabledAll();
        }
      } else if (this.isUse(useVerYn)) {
        if (this.isApproved(specProcCd)) {
          this.controllEditable(true);

          FormUtil.enableButtons(this.versionForm.buttons, ['updateVersion']);
          FormUtil.disableButtons(this.versionForm.buttons, ['temporarySave', 'approval']);

          FormUtil.enableButtons(this.testItemList.buttons, ['addTestItem', 'removeTestItem']);
          FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
        }
      }

      return;
    },
    testItemListClicked() {
      const qmPkgaProcCd = FormUtil.getData(this.valueWithqmPkgaListGrid.forms).specProcCd;
      const { specProcCd, useVerYn } = FormUtil.getData(this.versionForm.forms);
      const specData = this.specList.$grid.getGridData();
      const specOkey = specData.some((data) => !this.isApproved(data.specProcCd));

      qmPkgaProcCd &&
      !this.isApproved(qmPkgaProcCd) &&
      ((this.isApproved(specProcCd) && this.isUse(useVerYn)) || !this.isApproved(specProcCd)) &&
      !specOkey
        ? FormUtil.enableButtons(this.testItemList.buttons, ['up', 'down'])
        : FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
    },
    approval() {
      const selectedItem = FormUtil.getData(this.valueWithqmPkgaListGrid.forms);
      this.$eSign(() =>
        this.$axios
          .put('/ms/wrapTestManage', selectedItem)
          .then(() => {
            this.$info(this.$message.info.reviewCompleted);
            this.doInit();
          })
          .catch(() => this.$error(this.$message.error.updateData)),
      );
    },
    enableTemporarySaveButton() {
      FormUtil.enableButtons(this.versionForm.buttons, ['temporarySave']);
    },
    enableAddTestItemButton() {
      FormUtil.enableButtons(this.testItemList.buttons, ['addTestItem']);
    },
    showItemsByTestMethodModal() {
      this.$setState('itemsByTestMethodModal', { show: true });
    },
    hideItemsByTestMethodModal() {
      this.$setState('itemsByTestMethodModal', { show: false });
    },
    isNotExistRequiredValue(args) {
      const message = '필수 필드는 반드시 값을 직접 입력해야 합니다.';

      const isValid = !this.testItemList.$grid.validateChangedGridData(args, message);

      return isValid;
    },
    isNotExistStandardValueByJdgType() {
      let isValid = false;
      const gridData = this.testItemList.$grid.getGridData();

      for (let i in gridData) {
        let emptyTest = this.invalidStandardValueByjdgType(gridData[i]);

        if (emptyTest) {
          isValid = true;
          return true;
        }
      }

      return isValid;
    },
    invalidStandardValueByjdgType(element) {
      const { jdgTyp } = element;
      const suitableType = 'S0070001';
      const numericExceedType = 'S0070002';
      const numericMoreThanType = 'S0070003';
      const descriptionType = 'S0070004';
      if (jdgTyp === suitableType) {
        return this.suitableTypeJdg(element);
      }
      if (jdgTyp === numericExceedType) {
        return this.numericExceedTypeJdg(element);
      }
      if (jdgTyp === numericMoreThanType) {
        return this.numericMoreThanTypeJdg(element);
      }
      if (jdgTyp === descriptionType) {
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
    preputQmPkga() {
      const testItemGrid = this.testItemList.$grid;
      const versionForm = this.versionForm.forms;

      let qmPkgaListParameter = FormUtil.getData(this.valueWithqmPkgaListGrid.forms);
      let testItemParameter = [...testItemGrid.getGridData()];
      let deleteTestItemParameter = [...testItemGrid.getRemovedItems()];
      let formData = FormUtil.getData(versionForm);

      const aitmSpecIdx = qmPkgaListParameter?.aitmSpecIdx;
      const specProcCd = qmPkgaListParameter?.specProcCd;
      testItemParameter.forEach((item, index) => {
        item.aitmSpecIdx = aitmSpecIdx;
        item.aitmOrd = index + 1;
      });

      if (!qmPkgaListParameter) qmPkgaListParameter = {};
      qmPkgaListParameter.testItemList = testItemParameter;
      qmPkgaListParameter.deleteTestItemList = deleteTestItemParameter;

      this.versionFormRequiredList.forEach(
        (requiredAttribute) =>
          (qmPkgaListParameter[requiredAttribute] = formData[requiredAttribute]),
      );

      if (this.isNotExistRequiredValue(['specTyp', 'jdgTyp'])) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      if (this.isApproved(specProcCd)) {
        this.$confirm(this.$message.warn.updateAprrovedSpec).then(() =>
          this.putQmPkga(qmPkgaListParameter),
        );
      } else {
        this.putQmPkga(qmPkgaListParameter);
      }
    },
    putQmPkga(parameter) {
      this.$eSignWithReason(() =>
        this.$axios
          .post('/ms/wrapTestManage/putQmPkga', parameter)
          .then(() => {
            this.$info(this.$message.info.saved);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          }),
      );
    },
    addTestItemClick(items) {
      const aitmSpecIdx = FormUtil.getData(this.valueWithqmPkgaListGrid.forms).aitmSpecIdx ?? '';
      const testItemListGrid = this.testItemList.$grid;

      testItemListGrid.addRow(
        items.map((row) => ({
          aitmSpecIdx,
          amitmCd: row.amitmCd,
          aitmKn: row.aitmKn,
          vriaKn: row.vriaKn,
          vriaNo: row.vriaNo,
        })),
      );
      const addedList = testItemListGrid.getGridData();

      if (addedList.length > 0) {
        this.changeButtonWhenSelectedVersion();
      } else {
        this.changeButtonDisabledAll();
        this.enableAddTestItemButton();
      }
    },
    removeTestItem() {
      this.testItemList.$grid.removeRow('selectedIndex');
    },
    upOrd() {
      this.testItemList.$grid.moveRowsToUp();
    },
    downOrd() {
      this.testItemList.$grid.moveRowsToDown();
    },
    changeModifiableColumn({ value, rowIndex, dataField, item }) {
      const { $grid } = this.testItemList;
      const isColNameJudgmentType = dataField == 'jdgTyp';
      const judgmentTypeCode = isColNameJudgmentType ? value : item.jdgTyp;

      if (!judgmentTypeCode) return;

      const isConformityCode = judgmentTypeCode == 'S0070001';
      const isNumericCode = judgmentTypeCode == 'S0070002' || judgmentTypeCode == 'S0070003';
      const isNarrativeCode = judgmentTypeCode == 'S0070004';
      const isNoStandardCode = judgmentTypeCode == 'S0070005';

      if (isConformityCode) {
        ['perSlvLow', 'perSlvUpp', 'perSlvDesc', 'owcSlvLow', 'owcSlvUpp', 'owcSlvDesc'].forEach(
          (key) => $grid.setCellValue(rowIndex, key, ''),
        );
      } else if (isNumericCode) {
        ['perSlvDesc', 'owcSlvDesc', 'slvJdgCfm', 'slvJdgNonCfm'].forEach((key) =>
          $grid.setCellValue(rowIndex, key, ''),
        );
      } else if (isNarrativeCode) {
        ['perSlvLow', 'perSlvUpp', 'owcSlvLow', 'owcSlvUpp', 'slvJdgCfm', 'slvJdgNonCfm'].forEach(
          (key) => $grid.setCellValue(rowIndex, key, ''),
        );
      } else if (isNoStandardCode) {
        [
          'perSlvLow',
          'perSlvUpp',
          'perSlvDesc',
          'owcSlvLow',
          'owcSlvUpp',
          'owcSlvDesc',
          'slvJdgCfm',
          'slvJdgNonCfm',
        ].forEach((key) => $grid.setCellValue(rowIndex, key, ''));
      }

      return;
    },
  },
  computed: {
    versionFormButtons() {
      const { buttons, isDisabledButton } = this.versionForm;
      return buttons.map((button) => {
        return {
          ...button,
          type: isDisabledButton ? button.type : 'default',
        };
      });
    },
  },
};
</script>

<style></style>
