<template>
  <AUIGridSearch
    v-bind="pitmList"
    @grid-created="(proxy) => $setState('pitmList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <FormBase v-bind="valueWithPitmGrid" />

  <!-- <Horizontal :spans="[2.6, 7.4]"> -->
  <Horizontal :spans="[5, 5]">
    <AUIGridWithHeader
      v-bind="versionList"
      :height="versionList.height"
      @grid-created="(proxy) => $setState('versionList.$grid', proxy)"
      @button-click="onClickBtnEvent"
    />

    <FormWithHeader
      v-bind="commonInfoForm"
      :height="commonInfoForm.height"
      @button-click="onClickBtnEvent"
      @form-event="onClickCommonInfoFormButtons"
    />
  </Horizontal>

  <FormBase v-bind="valueWithVersionGrid" />

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

  <ItemsCopyByTestMethodModal
    :show="itemsCopyByTestMethodModal.show"
    @close="hideItemsCopyByTestMethodModal()"
    @select="copyRowTestItem"
  />

  <RequestReviewerModal
    :show="requestReviewerModal.show"
    @close="hideRequestReviewerModal()"
    @modalReturnDataEvent="requestReview"
  />

  <ElnSpecCopyForTestMethodModal
    :show="elnSpecCopyForTestMethodModal.show"
    :labNo="elnSpecCopyForTestMethodModal.labNo"
    @close="hideElnSpecCopyForTestMethodModal()"
    @select="copyRowElnTestItem"
  />

  <SapPrdhaSearchModal
    :show="sapPrdhaSearchModal.show"
    :sapPrdha="sapPrdhaSearchModal.sapPrdha"
    :versionInfo="sapPrdhaSearchModal.versionInfo"
    @close="() => (sapPrdhaSearchModal.show = false)"
    @modalReturnDataEvent="sapPrdhaModalReturnDataEvent"
  />

  <PackingSpecificationModal
    :show="packingSpecificationModal.show"
    :parameter="packingSpecificationModal.parameter"
    @close="hidePackagingSpecModal"
  ></PackingSpecificationModal>
</template>

<script>
import {
  ElnSpecCopyForTestMethodModal,
  ItemsByTestMethodModal,
  ItemsCopyByTestMethodModal,
  RequestReviewerModal,
  SapPrdhaSearchModal,
  PackingSpecificationModal,
} from '@/page/modal';
import { FormUtil, GridUtil, StringUtil } from '@/util';

import values from './values/specManage';

const { processCode, pitemtype } = values;

export default {
  name: 'SpecManage',
  mounted() {
    this.fetchPItemSpecList();
  },
  components: {
    ItemsByTestMethodModal,
    RequestReviewerModal,
    ItemsCopyByTestMethodModal,
    ElnSpecCopyForTestMethodModal,
    SapPrdhaSearchModal,
    PackingSpecificationModal,
  },
  data() {
    const {
      pitmList,
      versionList,
      commonInfoForm,
      testItemList,
      valueWithPitmGrid,
      valueWithVersionGrid,
    } = this.$copy(values);
    return {
      pitmList: {
        ...pitmList.static,
        forms: pitmList.forms(),
        columns: pitmList.columns(),
        event: {
          cellDoubleClick: async (e) => {
            this.initAll();
            await this.fetchVersionList(e.item);
            this.settingDepartmentList(e.item);
            this.setPitmInfoToPitmGridValueForm(e.item);
            this.focusFirstRowItemOfVersionGrid();
          },
        },
      },
      versionList: {
        ...versionList.static,
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (e) => {
            if (this.isSelectedItemHasVersion()) {
              this.fetchAItemListMatchingByPitmType(e.item);
            }
            this.setVersionInfoToVersionGridValueForm(e.item);
            this.changeButtonDisabledAll();
            this.changeButtonWhenSelectedVersion();
            this.changeCommonInfoFormButtons();
          },
        },
      },
      commonInfoForm: {
        ...commonInfoForm.static,
        forms: commonInfoForm.forms(),
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
      itemsCopyByTestMethodModal: {
        show: false,
      },
      elnSpecCopyForTestMethodModal: {
        show: false,
        labNo: '',
      },
      departmentList: {
        hirDepartmentCode: '',
        list: [],
      },
      valueWithPitmGrid: {
        forms: valueWithPitmGrid.forms(),
      },
      valueWithVersionGrid: {
        forms: valueWithVersionGrid.forms(),
      },
      sapPrdhaSearchModal: {
        show: false,
      },
      packingSpecificationModal: {
        show: false,
        parameter: {},
      },
    };
  },
  methods: {
    async fetchPItemSpecList() {
      this.initAll();
      const { $grid, forms } = this.pitmList;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/pItem', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchVersionList({ pitmCd, pitmVer }) {
      const { $grid } = this.versionList;
      const parameter = { pitmCd, pitmVer };
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/version', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    fetchAItemListMatchingByPitmType(item) {
      const isMustGetInterfaceData = this.isSemiManufactures() && item.aitmSpecIdx == null;
      isMustGetInterfaceData
        ? this.fetchPItemSpecSemiAItemList(item)
        : this.fetchPItemSpecAItemList(item);
    },

    async fetchPItemSpecAItemList({ aitmSpecIdx }) {
      const { $grid } = this.testItemList;
      const parameter = { aitmSpecIdx };

      if (parameter.aitmSpecIdx == null) {
        return;
      }

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/aItem', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchPItemSpecSemiAItemList({ pitmCd, pitmVer }) {
      const { $grid } = this.testItemList;
      const parameter = {
        pitmCd,
        pitmVer,
        pitmTyp: FormUtil.getData(this.valueWithPitmGrid.forms).pitmTyp,
      };

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/semiAItem', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.initAll();
        this.fetchPItemSpecList();
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
          : this.isSelectedItemHasNotVersion()
          ? this.saveFirstVersion()
          : this.equalPreviousVersion()
          ? this.updateNewVersion()
          : this.saveAitemList();
        return;
      }
      if (name == 'requestReview') {
        if (!this.isNotUpdateTestItemList() || this.isTestListGridEmpty()) {
          return this.$warn(this.$message.warn.afterSaveRequestReview);
        }
        this.showRequestReviewerModal();
        return;
      }
      if (name == 'updateVersion') {
        this.createNewVersion();
      }
      if (name == 'addRow') {
        this.showItemsByTestMethodModal();
        return;
      }
      if (name == 'copyRow') {
        if (this.isImpossibleToCopyRow()) {
          this.showItemsCopyByTestMethodModal();
          return;
        }
      }
      if (name == 'removeRow') {
        this.removeRowTestItem();
        return;
      }
      if (name == 'elnSpec') {
        this.showElnSpecCopyForTestMethodModal();
        return;
      }
      if (name == 'packagingSpec') {
        this.showPackagingSpecModal();
      }
    },

    initAll() {
      this.versionList.$grid.clearGridData();
      this.testItemList.$grid.clearGridData();
      this.valueWithPitmGrid.forms = values.valueWithPitmGrid.forms();
      this.valueWithVersionGrid.forms = values.valueWithVersionGrid.forms();
      this.changeButtonDisabledAll();
      this.resetCommonInfoForm();
    },

    initVersion() {
      this.testItemList.$grid.clearGridData();
      this.valueWithVersionGrid.forms = values.valueWithVersionGrid.forms();
      this.changeButtonDisabledAll();
    },

    focusFirstRowItemOfVersionGrid() {
      const { $grid } = this.versionList;
      $grid.setSelectionByIndex(0);
      const item = $grid.getSelectedRows()[0];
      this.loadToVersionFormAndTestListGrid(item);
    },

    // loadToVersionFormAndTestListGrid : hiddenform인 version폼에 클릭된 값 세팅 하고 시험항목리스트를 불러온다
    loadToVersionFormAndTestListGrid(item) {
      this.activePackagingSpecButton();
      this.setVersionInfoToVersionGridValueForm(item);
      this.setCommonInfoForm(item);
      const { aitmSpecIdx, pitmCd, pitmVer } = FormUtil.getData(this.valueWithVersionGrid.forms);

      // 첫 규격이고, 규격 index 없을때 (아예초기상태)
      if (this.isSelectedItemHasNotVersion()) {
        if (this.isImpossibleToRevisionBecausePackigingAndfinishedProduct()) {
          this.activateRequestReviewButtonWhenTemporarySave();
          return;
        }

        this.actvateButtonWhenFirstVersionAndNoIndex();

        // 반제품 일 경우, Eln규격 버튼 활성화
        if (this.isSemiManufactures()) {
          this.fetchPItemSpecSemiAItemList({ pitmCd, pitmVer });
          this.elnSpecCopyForTestMethodModal.labNo = FormUtil.getValue(
            this.commonInfoForm.forms,
            'labNo',
          );
          this.activateElnSpecButton();
        }

        return;
      }

      if (aitmSpecIdx != null) {
        this.fetchPItemSpecAItemList({ aitmSpecIdx });
        this.changeButtonWhenSelectedVersion();
        return;
      }
    },

    actvateButtonWhenFirstVersionAndNoIndex() {
      FormUtil.enableButtons(this.testItemList.buttons, [
        'temporarySave',
        'addRow',
        'copyRow',
        'removeRow',
      ]);
    },

    changeButtonDisabledAll() {
      FormUtil.disableButtons(this.testItemList.buttons, [
        'up',
        'down',
        'requestReview',
        'elnSpec',
        'updateVersion',
        'temporarySave',
        'addRow',
        'copyRow',
        'removeRow',
        'packagingSpec',
      ]);
    },

    isSelectedItemHasVersion() {
      return !this.isSelectedItemHasNotVersion();
    },

    isSelectedItemHasNotVersion() {
      const { $grid } = this.versionList;
      const parameter = FormUtil.getData(this.valueWithVersionGrid.forms);

      return $grid.getRowCount() == 1 && !parameter.aitmSpecVer;
    },

    isTestListGridEmpty() {
      return this.testItemList.$grid.getRowCount() == 0;
    },

    equalPreviousVersion() {
      const { aitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);
      const aitmIdxArray = this.versionList.$grid.getColumnValues('aitmSpecIdx', true);
      if (aitmSpecIdx != aitmIdxArray.shift()) {
        return false;
      }
      return aitmIdxArray.includes(aitmSpecIdx);
    },

    changeButtonWhenSelectedVersion() {
      this.activePackagingSpecButton();
      const buttons = this.testItemList.buttons;

      if (this.isImpossibleToRevisionBecausePackigingAndfinishedProduct()) {
        this.activateRequestReviewButtonWhenTemporarySave();
        return;
      }

      if (
        this.isSemiManufactures() &&
        (this.isSelectedUsableVersion() ||
          this.isSelectedTemporaryVersion() ||
          this.isSelectedReviewRejectVersion())
      ) {
        this.activateElnSpecButton();
      }

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

    setCommonInfoForm(item) {
      const { forms } = this.commonInfoForm;
      const { useVerYn, specProcCd, pitmTyp } = item;
      const validateFlag =
        this.isOkayToSaveCommonInfoForm(useVerYn, specProcCd) &&
        this.isFinishedOrPackaging(pitmTyp);

      if (!validateFlag) {
        this.disableCommonInfoForm();
      } else {
        this.enableCommonInfoForm();
        this.disableTestItemListUpdatableButtons();
      }
      FormUtil.setData(forms, { ...item });
    },

    resetCommonInfoForm() {
      this.commonInfoForm.forms = values.commonInfoForm.forms();
    },

    isOkayToSaveCommonInfoForm(useVerYn, specProcCd) {
      return !(useVerYn != 'N' || specProcCd != 'S0080100');
    },

    isFinishedOrPackaging(pitmTyp = null) {
      const targetArray = ['S0180100', 'S0180101', 'S0180500'];
      return targetArray.includes(pitmTyp);
    },

    disableCommonInfoForm() {
      const { forms } = this.commonInfoForm;
      FormUtil.disable(forms, 'pkgaCdSearch');
    },

    enableCommonInfoForm() {
      const { forms } = this.commonInfoForm;
      FormUtil.enable(forms, 'pkgaCdSearch');
    },

    disableTestItemListUpdatableButtons() {
      FormUtil.disableButtons(this.testItemList.buttons, [
        'up',
        'down',
        'addRow',
        'copyRow',
        'removeRow',
      ]);
    },

    changeCommonInfoFormButtons() {
      if (
        this.isSelectedTemporaryVersion() &&
        this.isImpossibleToRevisionBecausePackigingAndfinishedProduct()
      ) {
        this.enableCommonInfoForm();
      } else {
        this.disableCommonInfoForm();
      }
    },

    onClickCommonInfoFormButtons({ originEvent }) {
      if (originEvent === 'pkgaCdSearch') {
        const { pitmSpecIdx, pitmCd, pitmVer } = FormUtil.getData(this.valueWithVersionGrid.forms);
        this.sapPrdhaSearchModal.versionInfo = { pitmSpecIdx, pitmCd, pitmVer };
        this.sapPrdhaSearchModal.show = true;
      }
    },

    async sapPrdhaModalReturnDataEvent(item = null) {
      const { aitmSpecIdx, pkgaCd, sapPrdha, pkgaTypNm } = item;
      const { forms } = this.commonInfoForm;
      const { pitmCd, pitmVer } = FormUtil.getData(this.valueWithPitmGrid.forms);

      FormUtil.setData(forms, { aitmSpecIdx, pkgaCd, sapPrdha, pkgaTypNm });

      await this.fetchVersionList({ pitmCd, pitmVer });
      this.fetchPItemSpecAItemList({ aitmSpecIdx });
    },

    changeButtonWhenSelectedTestItem() {
      // 사용버전 Y일떄 up,down 가능, 임시저장, 검토반려(N)일때 up,down 가능
      if (
        (this.isSelectedUsableVersion() ||
          this.isSelectedTemporaryVersion() ||
          this.isSelectedReviewRejectVersion()) &&
        !this.isImpossibleToRevisionBecausePackigingAndfinishedProduct()
      ) {
        FormUtil.enableButtons(this.testItemList.buttons, ['up', 'down']);
      } else {
        FormUtil.disableButtons(this.testItemList.buttons, ['up', 'down']);
      }
    },

    activateRequestReviewButtonWhenTemporarySave() {
      // 검토요청 버튼은 임시저장이나 검토반려 일때만 활성화
      if (this.isSelectedTemporaryVersion() || this.isSelectedReviewRejectVersion()) {
        FormUtil.enableButtons(this.testItemList.buttons, ['requestReview']);
      }

      return;
    },

    activateElnSpecButton() {
      FormUtil.enableButtons(this.testItemList.buttons, ['elnSpec']);
    },

    activePackagingSpecButton() {
      FormUtil.enableButtons(this.testItemList.buttons, ['packagingSpec']);
    },

    setPitmInfoToPitmGridValueForm({ pitmCd, pitmVer, pitmTyp }) {
      FormUtil.setData(this.valueWithPitmGrid.forms, { pitmCd, pitmVer, pitmTyp });
    },

    setVersionInfoToVersionGridValueForm(item) {
      FormUtil.setData(this.valueWithVersionGrid.forms, item);
    },

    requestReview(popupParam) {
      const { pitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);

      popupParam.pitmSpecIdx = pitmSpecIdx;
      popupParam.revwUid = popupParam.aprUid;

      this.$eSignWithReason(() => this.$axios.put('/ms/specManage/requestReview', popupParam))
        .then(() => {
          this.$info(this.$message.info.reviewRequest);
          this.initAll();
          this.fetchPItemSpecList();
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

    showItemsCopyByTestMethodModal() {
      this.$setState('itemsCopyByTestMethodModal', { show: true });
    },

    hideItemsCopyByTestMethodModal() {
      this.$setState('itemsCopyByTestMethodModal', { show: false });
    },

    showElnSpecCopyForTestMethodModal() {
      this.$setState('elnSpecCopyForTestMethodModal', { show: true });
    },

    hideElnSpecCopyForTestMethodModal() {
      this.$setState('elnSpecCopyForTestMethodModal', { show: false });
    },

    showPackagingSpecModal() {
      this.packingSpecificationModal.parameter = FormUtil.getData(this.commonInfoForm.forms);
      return (this.packingSpecificationModal.show = true);
    },

    hidePackagingSpecModal() {
      this.packingSpecificationModal.parameter = FormUtil.getData(this.commonInfoForm.forms);
      return (this.packingSpecificationModal.show = false);
    },

    isSelectedTemporaryVersion() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == processCode.TEMPORARY_SAVE;
    },

    isSelectedReviewRejectVersion() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == processCode.REVIEW_RETURN;
    },

    isApproved() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == processCode.APPROVED;
    },

    isSpecRemove() {
      const { specProcCd } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return specProcCd == processCode.SPEC_REMOVE;
    },

    isSelectedUsableVersion() {
      const { useVerYn } = FormUtil.getData(this.valueWithVersionGrid.forms);
      return useVerYn == 'Y';
    },

    createNewVersion() {
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
        this.$eSignWithReason(() => this.$axios.post('/ms/specManage/createNewVersion', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.initAll();
            this.fetchPItemSpecList();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },

    // 첫 규격서 저장
    saveFirstVersion() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const { $grid } = this.testItemList;
      const { pitmSpecIdx } = FormUtil.getData(this.valueWithVersionGrid.forms);
      const { pitmCd, pitmVer } = FormUtil.getData(this.valueWithPitmGrid.forms);

      const parameter = {
        pitmCd,
        pitmVer,
        pitmSpecIdx,
        addedRowItems: $grid.getAddedRowItems(),
        editedRowItems: $grid.getEditedRowItems(),
        removedRowItems: $grid.getRemovedItems(),
      };

      if (this.isSemiManufactures()) {
        parameter.addedRowItems = $grid.getGridData();
        parameter.editedRowItems = [];
        parameter.removedRowItems = [];
      } else if (this.isNotUpdateTestItemList()) {
        return this.$warn(this.$message.warn.noSaveGridData);
      }

      this.$eSign(() => this.$axios.post('/ms/specManage/createFirstVersion', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.initAll();
          this.fetchPItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    async saveAitemList() {
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

      await this.$eSign(() => this.$axios.post('/ms/specManage/saveAItemList', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.initAll();
          this.fetchPItemSpecList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    async updateNewVersion() {
      if (this.isNotExistJdgType()) {
        return;
      }

      if (this.isNotExistStandardValueByJdgType()) {
        return;
      }

      const { $grid } = this.testItemList;
      const { pitmSpecIdx, aitmSpecIdx, aitmSpecVer } = FormUtil.getData(
        this.valueWithVersionGrid.forms,
      );
      const { pitmCd, pitmVer } = FormUtil.getData(this.valueWithPitmGrid.forms);
      const parameter = {
        aitmSpecIdx,
        pitmSpecIdx,
        pitmCd,
        pitmVer,
        aitmSpecVer,
        addedRowItems: $grid.getGridData(),
        editedRowItems: [],
        removedRowItems: [],
      };

      if (this.isNotUpdateTestItemList()) {
        return this.$warn(this.$message.warn.noSaveGridData);
      }

      await this.$eSign(() => this.$axios.post('/ms/specManage/updateNewVersion', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.initAll();
          this.fetchPItemSpecList();
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
      if (item.slvJdgCfm == item.slvJdgNonCfm) {
        this.$warn(this.$message.warn.sameSlvJdg);
        return true;
      }
      return false;
    },

    numericExceedTypeJdg(item) {
      const ownType = 'S0060001';
      const permissionType = 'S0060002';
      if (
        item.specTyp === ownType &&
        StringUtil.isEmpty(item.owcSlvLow) &&
        StringUtil.isEmpty(item.owcSlvUpp)
      ) {
        this.$warn(this.$message.warn.noExcOwcSlvLowOrSlvUpp);
        return true;
      } else if (
        item.specTyp === permissionType &&
        StringUtil.isEmpty(item.perSlvLow) &&
        StringUtil.isEmpty(item.perSlvUpp)
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
        StringUtil.isEmpty(item.owcSlvLow) &&
        StringUtil.isEmpty(item.owcSlvUpp)
      ) {
        this.$warn(this.$message.warn.noMoreOwcSlvLowOrSlvUpp);
        return true;
      } else if (
        item.specTyp === permissionType &&
        StringUtil.isEmpty(item.perSlvLow) &&
        StringUtil.isEmpty(item.perSlvUpp)
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

    isImpossibleToRevisionBecausePackigingAndfinishedProduct() {
      const { pitmTyp } = FormUtil.getData(this.valueWithPitmGrid.forms);
      return (
        pitmTyp == pitemtype.FINISHED_SET ||
        pitmTyp == pitemtype.FINISHED_SINGLE ||
        pitmTyp == pitemtype.PACKAGING_MATERIAL
      );
    },

    isSemiManufactures() {
      const { pitmTyp } = FormUtil.getData(this.valueWithPitmGrid.forms);
      return (
        pitmTyp == pitemtype.SEMI_MANUFACTURES_FILLING_FOAM ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_OTHER_PRODUCT ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_BULK ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_BAS
      );
    },

    isImpossibleToCopyRow() {
      const { pitmTyp } = FormUtil.getData(this.valueWithPitmGrid.forms);
      return (
        pitmTyp == pitemtype.RAW_MATERIAL ||
        pitmTyp == pitemtype.GOODS ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_FILLING_FOAM ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_OTHER_PRODUCT ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_BULK ||
        pitmTyp == pitemtype.SEMI_MANUFACTURES_BASE
      );
    },

    settingDepartmentList({ crgDptCd }) {
      if (crgDptCd == null || crgDptCd == '') {
        this.departmentList.hirDepartmentCode = '';
        this.departmentList.list = [];

        return;
      }

      this.$axios.get('/ms/specManage/departmentList').then(({ data }) => {
        this.departmentList.hirDepartmentCode = crgDptCd;
        this.departmentList.list = data;
      });
    },

    setDropDownList({ ansDptCd }) {
      const { editRenderer } = GridUtil.findColumn(this.testItemList.columns, 'ansDptCd');
      editRenderer.list = this.departmentList.list.filter(
        (row) => row.valueOfHir == this.departmentList.hirDepartmentCode || row.value == ansDptCd,
      );
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
          rptPrtYn: 'Y',
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

    copyRowTestItem(list) {
      const { $grid } = this.testItemList;
      const gridRowCnt = $grid.getRowCount();
      if (gridRowCnt != 0) {
        $grid.removeRow(Array.from(Array(gridRowCnt).keys()));
      }
      $grid.addRow(list, 'last');
    },

    copyRowElnTestItem(list) {
      const { $grid } = this.testItemList;
      const preGirdRowCnt = $grid.getRowCount();
      const checkedDuplicateAitmCopyList = list.filter((element) => {
        return !$grid.getGridData().some((row) => {
          if (row.amitmCd == element.amitmCd) {
            return true;
          }
        });
      });
      checkedDuplicateAitmCopyList.map((element, index) => {
        element.aitmOrd = preGirdRowCnt + index + 1;
      });
      $grid.addRow(checkedDuplicateAitmCopyList, 'last');
    },
  },
};
</script>

<style></style>
