<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchFormButtons"
  />

  <FormBase v-bind="codeWithSearchGrid" />

  <Horizontal :spans="[2.6, 7.4]">
    <AUIGridWithHeader
      v-bind="itemVersionGrid"
      @grid-created="(proxy) => $setState('itemVersionGrid.$grid', proxy)"
    />

    <FormWithHeader v-bind="commonInfoForm" @button-click="onClickCommonInfoFormButtons" />
  </Horizontal>

  <FormWithHeader v-bind="materialInfoForm" @form-event="formEventMaterialInfoForm" />

  <FormWithHeader v-bind="sampleInfoForm" />

  <SapPrdhaSearchModal
    :show="sapPrdhaSearchModal.show"
    :sapPrdha="sapPrdhaSearchModal.sapPrdha"
    @close="hideModal('sapPrdhaSearchModal')"
    @modalReturnDataEvent="sapPrdhaModalReturnDataEvent"
  />

  <ReasonForItemRevisionModal
    :show="reasonForItemRevisionModal.show"
    :initData="reasonForItemRevisionModal.initData"
    @close="hideModal('reasonForItemRevisionModal')"
    @reg="getRevisionReason"
  />

  <BomModal
    :show="bomModal.show"
    :readonly="bomModal.readonly"
    :initData="bomModal.initData"
    @close="hideModal('bomModal')"
  />

  <ItemManageFileAttacherModal
    :initData="itemManageFileAttacherModal.initData"
    :show="itemManageFileAttacherModal.show"
    :readonly="itemManageFileAttacherModal.readonly"
    @save="fileSave"
    @close="hideModal('itemManageFileAttacherModal')"
  />
</template>

<script>
import {
  BomModal,
  ItemManageFileAttacherModal,
  ReasonForItemRevisionModal,
  SapPrdhaSearchModal,
} from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/itemManage';

export default {
  name: 'itemManage',
  components: {
    SapPrdhaSearchModal,
    ReasonForItemRevisionModal,
    ItemManageFileAttacherModal,
    BomModal,
  },
  mounted() {
    this.setPlntCd();
    this.initItemVersionForms();
    this.fetchSearchGrid();
    this.setSapCodeList();
  },
  data() {
    const {
      searchGridWithForm,
      itemVersionGrid,
      commonInfoForm,
      materialInfoForm,
      sampleInfoForm,
      codeWithSearchGrid,
    } = this.$copy(values);
    return {
      codeWithSearchGrid: {
        forms: codeWithSearchGrid.forms(),
      },
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: async (event) => {
            this.initItemVersionForms();
            const { plntCd, pitmCd } = event.item;
            await this.fetchItemVersionGrid({ plntCd, pitmCd });
            this.setProcessCodeToSearchGridCodeForm(event.item);
            this.focusFirstRowItemOfItemVersionGrid();
          },
          vScrollChange: async (event) => {
            if (
              event.maxPosition - event.position < 6 &&
              event.position - event.oldPosition > 0 &&
              this.currentPitemList.flag == true
            ) {
              this.currentPitemList.flag = false;
              await this.addNextListGridData();
            }
          },
        },
      },
      itemVersionGrid: {
        ...itemVersionGrid.static,
        columns: itemVersionGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.loadToItemVersionForms(event.item);
          },
        },
      },
      commonInfoForm: {
        ...commonInfoForm.static,
        forms: commonInfoForm.forms(),
      },
      materialInfoForm: {
        ...materialInfoForm.static,
        forms: materialInfoForm.forms(),
      },
      sampleInfoForm: {
        ...sampleInfoForm.static,
        forms: sampleInfoForm.forms(),
      },
      sapPrdhaSearchModal: {
        show: false,
        sapPrdha: null,
      },
      reasonForItemRevisionModal: {
        show: false,
        initData: {},
      },
      processCode: {
        temporarySave: 'S0080100',
        approved: 'S0080400',
      },
      pitemtype: {
        finishedSet: 'S0180100',
        finishedSingle: 'S0180101',
        beautifulPackaging: 'S0180102',
        semiManufacturesFillingFoam: 'S0180201',
        semiManufacturesOtherProduct: 'S0180202',
        semiManufacturesBulk: 'S0180203',
        semiManufacturesBase: 'S0180204',
        rawMaterial: 'S0180400',
        packagingMaterial: 'S0180500',
        goods: 'S0180600',
      },
      sapCodeList: {
        list: [],
      },
      bomModal: {
        show: false,
        readonly: false,
        initData: {},
      },
      itemManageFileAttacherModal: {
        show: false,
        initData: {},
        readonly: false,
      },
      currentPitemList: {
        rowCnt: 0,
        flag: true,
      },
    };
  },
  methods: {
    setProcessCodeToSearchGridCodeForm({ specProcCd, qpSpecProcCd, pitmTyp }) {
      FormUtil.setData(this.codeWithSearchGrid.forms, { specProcCd, qpSpecProcCd, pitmTyp });
    },

    setPlntCd() {
      const plntCd = TokenUtil.myPlantCode();
      FormUtil.setData(this.searchGridWithForm.forms, { plntCd });
    },

    initItemVersionGrid() {
      this.itemVersionGrid.$grid.clearGridData();
    },

    initItemVersionForms() {
      this.commonInfoForm.forms = values.commonInfoForm.forms();
      this.materialInfoForm.forms = values.materialInfoForm.forms();
      this.sampleInfoForm.forms = values.sampleInfoForm.forms();
      this.codeWithSearchGrid.forms = values.codeWithSearchGrid.forms();
      FormUtil.disableButtons(this.commonInfoForm.buttons, [
        'bomModal',
        'itemManageFileAttacherModal',
        'save',
        'firstReg',
      ]);
    },

    loadToItemVersionForms(rowItem) {
      this.setDataToItemVersionForms(rowItem);
      const { specProcCd, qpSpecProcCd } = FormUtil.getData(this.codeWithSearchGrid.forms);
      const { useVerYn } = rowItem;
      this.setButtonsByStatus(specProcCd, qpSpecProcCd, useVerYn);
      this.setSapPrdhaSearchButtonByPItemType(rowItem);
    },

    async fetchSearchGrid() {
      this.initItemVersionForms();
      this.initItemVersionGrid();

      const { $grid } = this.searchGridWithForm;
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      param.offset = 0;
      param.limit = 100;

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/pItem', param))
        .then(({ data }) => data);
      this.currentPitemList.rowCnt = data.resultList.length;
      $grid.setGridData(data.resultList);
    },

    async fetchItemVersionGrid(param) {
      const { $grid } = this.itemVersionGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/version', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async sapPrdhaModalReturnDataEvent({ sapPrdha = null }) {
      const forms = this.materialInfoForm?.forms;

      if (forms && sapPrdha) {
        FormUtil.setData(forms, { sapPrdha });
      }
    },

    onClickSearchFormButtons({ name }) {
      if (name === 'search') {
        this.searchGridWithForm.forms.validate().then(() => {
          this.fetchSearchGrid();
        });
      }
    },

    async addNextListGridData() {
      const { $grid } = this.searchGridWithForm;
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      param.offset = this.currentPitemList.rowCnt;
      param.limit = 100;

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/pItem', param, { _progress: false }))
        .then(({ data }) => data.resultList);
      this.currentPitemList.rowCnt += data.length;
      $grid.addRow(data, 'last');
      this.currentPitemList.flag = true;
    },

    async onClickCommonInfoFormButtons({ name }) {
      const { forms } = this.commonInfoForm;

      if (name === 'save') {
        const { specProcCd } = FormUtil.getData(this.commonInfoForm.forms);
        const isTemporarySave = specProcCd == this.processCode.temporarySave;
        const isApproved = specProcCd == this.processCode.approved;

        if (isTemporarySave) {
          return forms.validate().then(() => {
            this.tempSave();
          });
        }

        if (isApproved) {
          return forms.validate().then(() => {
            this.$confirm(this.$message.warn.updateApprrovedItem)
              .then(() => this.regRevisionReason())
              .catch();
          });
        }
        return this.$warn(this.$message.warn.reviseAfterSpecApprove);
      }

      if (name === 'firstReg') {
        return forms.validate().then(() => {
          this.firstReg();
        });
      }

      if (name === 'bomModal') {
        this.showModal(name);
        return;
      }
    },
    isWrap() {
      const { pitmTyp } = FormUtil.getData(this.codeWithSearchGrid.forms);
      return (
        pitmTyp == this.pitemtype.finishedSet ||
        pitmTyp == this.pitemtype.finishedSingle ||
        pitmTyp == this.pitemtype.packagingMaterial
      );
    },

    isVaildateSapCode() {
      let { sapPrdha } = this.formToParam();
      return this.sapCodeList.list.find((row) => row.sapPrdha == sapPrdha) === undefined;
    },

    async firstReg() {
      if (this.isWrap() && this.isVaildateSapCode()) {
        return this.$warn(this.$message.warn.noSapCode);
      }
      let param = this.formToParam();
      await this.$axios
        .put('/ms/itemManage/firstSave', param)
        .then(() => {
          this.initItemVersionForms();
          this.fetchSearchGrid();
          this.$info(this.$message.info.saved);
        })
        .catch(() => this.$error(this.$message.error.saveData));
    },

    async tempSave() {
      if (this.isWrap() && this.isVaildateSapCode()) {
        return this.$warn(this.$message.warn.noSapCode);
      }
      let param = this.formToParam();
      await this.$axios
        .put('/ms/itemManage/tempSave', param)
        .then(() => {
          this.initItemVersionForms();
          this.fetchSearchGrid();
          this.$info(this.$message.info.saved);
        })
        .catch(() => this.$error(this.$message.error.saveData));
    },

    async revision() {
      let param = this.formToParam();
      await this.$eSign(() => this.$axios.post('/ms/itemManage/revision', param))
        .then(() => {
          this.initItemVersionForms();
          this.fetchSearchGrid();
          this.$info(this.$message.warn.requestApproveAtSpecManage);
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    setDataToItemVersionForms(item) {
      FormUtil.setData(this.commonInfoForm.forms, item);
      FormUtil.setData(this.materialInfoForm.forms, item);
      FormUtil.setData(this.sampleInfoForm.forms, item);
    },

    formToParam() {
      const commonData = FormUtil.getData(this.commonInfoForm.forms);
      const materialData = FormUtil.getData(this.materialInfoForm.forms);
      const sampleData = FormUtil.getData(this.sampleInfoForm.forms);
      let param = {
        ...commonData,
        ...materialData,
        ...sampleData,
      };
      return param;
    },

    formEventMaterialInfoForm({ originEvent, item }) {
      if (originEvent == 'sapPrdhaSearch') {
        if (item?.itemLabel == 'sapPrdhaSearchModal') {
          this.showModal(item.itemLabel);
        }
      }
      if (originEvent === 'ctrptNoSearch') {
        if (item?.itemLabel === 'itemManageFileAttacherModal') {
          this.showModal(item.itemLabel);
        }
      }
    },

    showModal(name) {
      if (name === 'sapPrdhaSearchModal') {
        const forms = this.materialInfoForm?.forms;
        if (forms) {
          const sapPrdha = FormUtil.getValue(forms, 'sapPrdha');
          this.sapPrdhaSearchModal.sapPrdha = sapPrdha;
        }
        return (this.sapPrdhaSearchModal.show = true);
      }
      if (name === 'reasonForItemRevisionModal') {
        return (this.reasonForItemRevisionModal.show = true);
      }
      if (name === 'bomModal') {
        return (this.bomModal.show = true);
      }
      if (name === 'itemManageFileAttacherModal') {
        return (this.itemManageFileAttacherModal.show = true);
      }
    },

    hideModal(name) {
      if (name === 'sapPrdhaSearchModal') {
        return (this.sapPrdhaSearchModal.show = false);
      }
      if (name === 'reasonForItemRevisionModal') {
        return (this.reasonForItemRevisionModal.show = false);
      }
      if (name === 'bomModal') {
        return (this.bomModal.show = false);
      }
      if (name === 'itemManageFileAttacherModal') {
        return (this.itemManageFileAttacherModal.show = false);
      }
    },

    setButtonsByStatus(specProcCd, qpSpecProcCd, useVerYn) {
      const buttons = this.commonInfoForm.buttons;
      const isStorable =
        (specProcCd == this.processCode.temporarySave || specProcCd == this.processCode.approved) &&
        useVerYn == 'Y' &&
        qpSpecProcCd == this.processCode.approved;
      const isFirstStorable = qpSpecProcCd == this.processCode.temporarySave;

      FormUtil.disableButtons(buttons, ['bomModal', 'save', 'firstReg']);
      this.disableFormButton('ctrptNoSearch');

      if (isFirstStorable) {
        FormUtil.enableButtons(buttons, ['bomModal', 'firstReg']);
        FormUtil.disableButtons(buttons, ['save']);
        this.setItemManageFileAttacherModalInitData();
        this.setBomModalInitData();
        this.enableFormButton('ctrptNoSearch');
        return;
      }

      if (isStorable) {
        FormUtil.enableButtons(buttons, ['bomModal', 'save']);
        FormUtil.disableButtons(buttons, ['firstReg']);
        this.setItemManageFileAttacherModalInitData();
        this.setBomModalInitData();
        this.enableFormButton('ctrptNoSearch');
        return;
      }
    },

    setSapPrdhaSearchButtonByPItemType({ specProcCd, pitmTyp }) {
      const isChangeable =
        (specProcCd == this.processCode.temporarySave || specProcCd == this.processCode.approved) &&
        (pitmTyp == this.pitemtype.finishedSet ||
          pitmTyp == this.pitemtype.finishedSingle ||
          pitmTyp == this.pitemtype.packagingMaterial);
      const isNotChangeable = !isChangeable;

      if (isChangeable) {
        this.enableFormButton('sapPrdhaSearch');
      }

      if (isNotChangeable) {
        this.disableFormButton('sapPrdhaSearch');
      }
    },

    enableFormButton(buttonName) {
      const { forms } = this.materialInfoForm;
      FormUtil.enable(forms, buttonName);
    },
    disableFormButton(buttonName) {
      const { forms } = this.materialInfoForm;
      FormUtil.disable(forms, buttonName);
    },

    async setSapCodeList() {
      const param = {};
      await this.$axios
        .get('ms/wrapTestManage', param)
        .then(
          ({ data }) =>
            (this.sapCodeList.list = data.filter(
              (row) => row.specProcCd == this.processCode.approved,
            )),
        );
    },

    getRevisionReason({ rvsReaCd, rvsCtt }) {
      FormUtil.setData(this.commonInfoForm.forms, { rvsReaCd, rvsCtt });
      this.revision();
    },

    regRevisionReason() {
      const commonData = FormUtil.getData(this.commonInfoForm.forms);
      this.reasonForItemRevisionModal.initData = {
        ...commonData,
      };
      this.showModal('reasonForItemRevisionModal');
    },

    getCtrptNo() {
      return FormUtil.getValue(this.materialInfoForm.forms, 'ctrptNo');
    },

    setItemManageFileAttacherModalInitData() {
      let ctrptNo = null;
      let ctId = null;
      if (this.getCtrptNo() != null) {
        ctrptNo = Number(this.getCtrptNo());
        ctId = Object.assign(ctrptNo, Number);
      }
      const { labNo } = FormUtil.getData(this.commonInfoForm.forms);
      this.itemManageFileAttacherModal.initData = { ctId, matnr: labNo };
    },

    setBomModalInitData() {
      const { pitmCd } = FormUtil.getData(this.commonInfoForm.forms);
      this.bomModal.initData = { pitmCd };
    },

    fileSave({ addedFiles, removedFileIds }) {
      if (this.getCtrptNo() == null) {
        this.$error(this.$message.warn.noCtrptNo);
        return;
      }
      const ctrptNo = Number(this.getCtrptNo());
      const ctId = Object.assign(ctrptNo, Number);
      const commonInfoFormData = FormUtil.getData(this.commonInfoForm.forms);

      const fileInfoList = { addedFiles, removedFileIds, ctId, ...commonInfoFormData };

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ms/itemManage/saveFile', fileInfoList)
          .then(() => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }

            this.hideModal('itemManageFileAttacherModal');
            this.initItemVersionForms();
            this.fetchSearchGrid();
            this.setSapCodeList();
          })
          .catch(() => {
            this.$error(this.$message.error.savedFiles);
          });
      });
    },

    focusFirstRowItemOfItemVersionGrid() {
      const { $grid } = this.itemVersionGrid;
      $grid.setSelectionByIndex(0);
      const item = $grid.getSelectedRows()[0];
      this.loadToItemVersionForms(item);
    },
  },
};
</script>

<style></style>
