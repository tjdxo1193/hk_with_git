<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchFormButtons"
  />
  <FormWithHeader
    ref="registerForm"
    v-bind="registerForm"
    @button-click="onClickRegisterFormButtons"
    @form-event="onClickButtonsInRegisterForm"
    :modelValue="registerForm.modelValue"
  />
  <AUIGridWithHeader
    v-bind="accessoryGrid"
    :height="'150px'"
    @grid-created="(proxy) => $setState('accessoryGrid.$grid', proxy)"
    @button-click="onClickAccessoryGridButtons"
  />
  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    @save="saveFile"
    @close="hideModal('fileAttacherModal')"
  />
  <SearchSapAssetsInfoModal
    :show="searchSapAssetsInfoModal.show"
    @close="hideSearchSapAssetsInfoModal()"
    @select="putAssetsInfo()"
  />
  <SearchSapDepreciationInfoModal
    :show="searchSapDepreciationInfoModal.show"
    @close="hideSearchSapDepreciationInfoModal()"
    @select="putDepreciationInfo()"
  />
  
</template>

<script>
import { FileAttacherModal, SearchSapAssetsInfoModal, SearchSapDepreciationInfoModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/instManage.js';

export default {
  name: 'instManage',
  components: {
    FileAttacherModal,
    SearchSapAssetsInfoModal,
    SearchSapDepreciationInfoModal,
  },
  mounted() {
    this.fetchSearchGrid();
    this.initButtonClick();
  },
  data() {
    const { searchGridWithForm, registerForm, accessoryGrid } = this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.onClickSearchGridWithFormCell(event);
            this.setButtonsEnable();
          },
        },
      },
      registerForm: {
        ...registerForm.static,
        forms: registerForm.forms(),
        modelValue: false,
      },
      accessoryGrid: {
        ...accessoryGrid.static,
        columns: accessoryGrid.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
      searchSapAssetsInfoModal: {
        show: false,
      },
      searchSapDepreciationInfoModal: {
        show: false,
      }
    };
  },
  methods: {
    initButtonClick() {
      this.registerForm.forms = values.registerForm.forms();
      const { $grid } = this.accessoryGrid;
      $grid.clearGridData();
      this.setButtonsEnable('init');
    },
    getInstrumentParam() {
      const { $grid } = this.accessoryGrid;
      const registerFormData = FormUtil.getData(this.registerForm.forms);
      if ($grid.validateGridData(['acsrNm'], '구성품 명을 입력해주세요.')) {
        const addedAcsrList = $grid.getAddedRowItems();
        const editedAcsrList = $grid.getEditedRowItems();
        const removedAcsrList = $grid.getRemovedItems();
        const param = { ...registerFormData, addedAcsrList, editedAcsrList, removedAcsrList };
        return param;
      }
    },
    async fetchSearchGrid() {
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instManage', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async fetchAccessoryGrid(eqmCd) {
      const { $grid } = this.accessoryGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instManage/accessory', { eqmCd }))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async createInstrument() {
      const param = this.getInstrumentParam();
      if (param !== undefined) {
        this.$eSign(() => this.$axios.post('/in/instManage', param))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.initButtonClick();
            this.fetchSearchGrid();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      }
    },
    async updateInstrument() {
      const param = this.getInstrumentParam();
      if (param !== undefined) {
        this.$eSign(() => this.$axios.put('/in/instManage', param))
          .then(() => {
            this.initButtonClick();
            this.fetchSearchGrid();
            this.$info(this.$message.info.updated);
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      }
    },
    async deleteInstrument() {
      const data = this.getInstrumentParam();
      const param = { eqmCd: data.eqmCd };
      this.$eSignWithReason(() => this.$axios.put('/in/instManage/delete', param))
        .then(() => {
          this.initButtonClick();
          this.fetchSearchGrid();
          this.$info(this.$message.info.deleted);
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    setButtonsEnable(name) {
      const buttons = this.registerForm.buttons;
      if (name === 'init') {
        FormUtil.enableButtons(buttons, ['save', 'init']);
        FormUtil.disableButtons(buttons, ['saveFile', 'update', 'delete']);
      } else {
        FormUtil.enableButtons(buttons, ['saveFile', 'update', 'delete', 'init']);
        FormUtil.disableButtons(buttons, ['save']);
      }
    },
    onClickSearchGridWithFormCell(event) {
      const data = event.item;
      const forms = this.registerForm.forms;
      FormUtil.setData(forms, data);
      this.fetchAccessoryGrid(data.eqmCd);
    },
    accessoryGridAddRow() {
      const { $grid } = this.accessoryGrid;
      $grid.addRow({});
    },
    accessoryGridDeleteRow() {
      const { $grid } = this.accessoryGrid;
      $grid.removeRow('selectedIndex');
    },
    async saveFile({ addedFiles, removedFileIds }) {
      const form = this.registerForm.forms;
      const eqmCd = FormUtil.getValue(form, 'eqmCd');
      const eqmFileIdx = Number(FormUtil.getValue(form, 'eqmFileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, eqmCd, eqmFileIdx };
      this.$axios
        .postByForm('/in/instManage/file', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length === 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.fetchSearchGrid();
          this.getFileListByFileIdx(eqmFileIdx, data);
        })
        .catch(() => {
          this.$error(this.$message.error.savedFiles);
        });
    },
    getFileListByFileIdx(originalFileIdx, eqmFileIdx) {
      if (originalFileIdx > 0) {
        return this.$refs.fileAttacherModal.getFileList();
      } else {
        return this.setInitFileIdx(eqmFileIdx);
      }
    },
    setInitFileIdx(eqmFileIdx) {
      if (this.fileAttacherModal.fileIdx === 0) {
        this.fileAttacherModal.fileIdx = eqmFileIdx;
      }
      FormUtil.setData(this.registerForm.forms, { eqmFileIdx });
    },
    showModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    
    showSearchSapAssetsInfoModal() {
      this.$setState('searchSapAssetsInfoModal', { show: true });
    },
    
    hideSearchSapAssetsInfoModal() {
      this.$setState('searchSapAssetsInfoModal', { show: false });
    },

    showSearchSapDepreciationInfoModal() {
      this.$setState('searchSapDepreciationInfoModal', { show: true });
    },
    
    hideSearchSapDepreciationInfoModal() {
      this.$setState('searchSapDepreciationInfoModal', { show: false });
    },

    onClickSearchFormButtons({ name }) {
      if (name === 'search') {
        this.initButtonClick();
        this.fetchSearchGrid();
      }
    },
    onClickRegisterFormButtons({ name }) {
      if (name === 'saveFile') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.registerForm.forms, 'eqmFileIdx');
        this.showModal('fileAttacherModal');
      }
      if (name === 'save') {
        this.registerForm.forms
          .validate()
          .then(() => {
            this.createInstrument();
          })
          .catch(() => {});
      }
      if (name === 'update') {
        this.registerForm.forms
          .validate()
          .then(() => {
            this.updateInstrument();
          })
          .catch(() => {});
      }
      if (name === 'delete') {
        this.deleteInstrument();
      }
      if (name === 'init') {
        this.initButtonClick();
      }
    },
    onClickButtonsInRegisterForm({ originEvent }) {
      if (originEvent === 'searchSapAssets'){
        this.showSearchSapAssetsInfoModal();
      }
      if (originEvent === 'searchSapDepreciation'){
        const {forms} = this.registerForm;
        if(!forms.anlkl || !forms.anln1){
          return this.$warn(this.$message.warn.noAssetsInfo);
        }
        this.showSearchSapDepreciationInfoModal();
      }
    },
    onClickAccessoryGridButtons({ name }) {
      if (name === 'addRow') {
        this.accessoryGridAddRow();
      }
      if (name === 'deleteRow') {
        this.accessoryGridDeleteRow();
      }
    },
    putAssetsInfo(item){
      const mappingParam = {
        sapAstNo : item.anln1,
        sapAstNoDtl : item.anln2,
        anlkl : item.anlkl,
        sapAstNm : item.txt50,
        sapCrtDt : item.erdat,
        sapChgDt : item.aedat,
        sapAcqDt : item.zugdt,
        sapAccd : item.kumafa,
        sapSaleDpsDt : item.deakt, // 비활성화일? SAP매각/폐기일자,
        sapAddDesc : item.txa50,
        sapCrgNmEmid : item.zzpic,
        sapCosc : item.kostl,
        sapOrco : item.kansw,
      }
      
      FormUtil.setData(this.registerForm.forms, mappingParam)
    },
    putDepreciationInfo(item){
      const mappingParam = {
        sapAccd : item.kumafa,
        sapOrco : item.kansw,
        //'bukrs','회사코드'
        //'bzdat','자산기준일'
      }
      FormUtil.setData(this.registerForm.forms, mappingParam)
    },
  },
};
</script>
