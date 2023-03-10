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

  <FormWithHeader v-bind="detailInfoForm" @form-event="formEventDetailInfoForm" />

  <FormWithHeader v-bind="materialInfoForm" />

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
import { BomModal, ItemManageFileAttacherModal, ReasonForItemRevisionModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/itemManage';

const { processCode } = values;

export default {
  name: 'itemManage',
  components: {
    ReasonForItemRevisionModal,
    ItemManageFileAttacherModal,
    BomModal,
  },
  mounted() {
    this.setPlntCd();
    this.initItemVersionForms();
    this.fetchSearchGrid();
  },
  data() {
    const {
      searchGridWithForm,
      itemVersionGrid,
      commonInfoForm,
      detailInfoForm,
      materialInfoForm,
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
              this.currentValueForScroll.loadListFlag == true
            ) {
              this.currentValueForScroll.loadListFlag = false;
              const isNotEnd = await this.addNextListGridData();
              this.currentValueForScroll.loadListFlag = isNotEnd;
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
      detailInfoForm: {
        ...detailInfoForm.static,
        forms: detailInfoForm.forms(),
      },
      materialInfoForm: {
        ...materialInfoForm.static,
        forms: materialInfoForm.forms(),
      },
      reasonForItemRevisionModal: {
        show: false,
        initData: {},
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
      currentValueForScroll: {
        searchParam: {},
        gridRowCnt: 0,
        loadListFlag: true,
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
      this.detailInfoForm.forms = values.detailInfoForm.forms();
      this.materialInfoForm.forms = values.materialInfoForm.forms();
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
    },

    async fetchSearchGrid() {
      this.initItemVersionForms();
      this.initItemVersionGrid();

      const { $grid } = this.searchGridWithForm;
      const param = FormUtil.getData(this.searchGridWithForm.forms);

      // 스크롤 이벤트 파라미터 초기화
      this.currentValueForScroll.searchParam = param;
      this.currentValueForScroll.loadListFlag = true;

      param.offset = 0;
      param.limit = 100;

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/pItem', param))
        .then(({ data }) => data);
      this.currentValueForScroll.gridRowCnt = data.resultList.length;
      $grid.setGridData(data.resultList);
    },

    async fetchItemVersionGrid(param) {
      const { $grid } = this.itemVersionGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/version', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
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

      this.currentValueForScroll.searchParam.offset = this.currentValueForScroll.gridRowCnt;
      this.currentValueForScroll.searchParam.limit = 100;

      const { data } = await this.$axios.get(
        '/ms/itemManage/pItem',
        this.currentValueForScroll.searchParam,
        { _progress: false },
      );

      this.currentValueForScroll.gridRowCnt += data.resultList.length;
      $grid.addRow(data.resultList, 'last');

      return this.currentValueForScroll.gridRowCnt <= data.total;
    },

    async onClickCommonInfoFormButtons({ name }) {
      const { forms } = this.commonInfoForm;

      if (name === 'save') {
        const { specProcCd } = FormUtil.getData(this.commonInfoForm.forms);
        const isTemporarySave = specProcCd == processCode.TEMPORARY_SAVE;
        const isApproved = specProcCd == processCode.APPROVED;

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
    async firstReg() {
      let param = this.formToParam();
      console.log(param);
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
      FormUtil.setData(this.detailInfoForm.forms, item);
      FormUtil.setData(this.materialInfoForm.forms, item);
    },

    formToParam() {
      const commonData = FormUtil.getData(this.commonInfoForm.forms);
      const materialData = FormUtil.getData(this.detailInfoForm.forms);
      const sampleData = FormUtil.getData(this.materialInfoForm.forms);
      let param = {
        ...commonData,
        ...materialData,
        ...sampleData,
      };
      return param;
    },

    formEventDetailInfoForm({ originEvent, item }) {
      if (originEvent === 'ctrptNoSearch') {
        if (item?.itemLabel === 'itemManageFileAttacherModal') {
          this.showModal(item.itemLabel);
        }
      }
    },

    showModal(name) {
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
        (specProcCd == processCode.TEMPORARY_SAVE || specProcCd == processCode.APPROVED) &&
        useVerYn == 'Y' &&
        qpSpecProcCd == processCode.APPROVED;
      const isFirstStorable = qpSpecProcCd == processCode.TEMPORARY_SAVE;

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

    enableFormButton(buttonName) {
      const { forms } = this.detailInfoForm;
      FormUtil.enable(forms, buttonName);
    },
    disableFormButton(buttonName) {
      const { forms } = this.detailInfoForm;
      FormUtil.disable(forms, buttonName);
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
      return FormUtil.getValue(this.detailInfoForm.forms, 'ctrptNo');
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
