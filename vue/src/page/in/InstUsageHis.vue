<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClicksearchGridWithFormButtons"
  />
  <FormWithHeader
    v-bind="registerForm"
    @form-event="registerFormEvent"
    @button-click="onClickRegisterFormButtons"
  />
  <InstrumentSearchModal
    :show="instrumentSearchModal.show"
    @close="hideModal('instrumentSearchModal')"
    @modalReturnDataEvent="instrumentSearchModalReturnDataEvent"
  />
  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    @close="hideModal('fileAttacherModal')"
    @save="saveFile"
  />
</template>

<script>
import { InstrumentSearchModal, FileAttacherModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util/index.js';

import values from './values/instUsageHis.js';

export default {
  name: 'instUsageHis',
  components: {
    InstrumentSearchModal,
    FileAttacherModal,
  },
  data() {
    const { searchGridWithForm, registerForm } = this.$copy(values);
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
      },
      instrumentSearchModal: {
        show: false,
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
    };
  },
  methods: {
    init() {
      this.registerForm.forms = values.registerForm.forms();
      this.setButtonsEnable('init');
    },
    setDefaultInfo() {
      const { forms } = this.registerForm;
      const ansNm = TokenUtil.myName();
      const ansUid = TokenUtil.myId();
      FormUtil.setData(forms, { ansNm, ansUid });
    },
    async fetchSearchGridWithForm() {
      const { $grid, forms } = this.searchGridWithForm;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instUsageHis', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    registerFormEvent(event) {
      if (event.originEvent === 'instrumentSearchModal') {
        this.showModal('instrumentSearchModal');
      }
    },
    async save() {
      const { forms } = this.registerForm;
      const param = FormUtil.getData(forms);
      await this.$eSign(() => this.$axios.post('/in/instUsageHis', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchSearchGridWithForm();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    async update() {
      const { forms } = this.registerForm;
      const param = FormUtil.getData(forms);
      await this.$eSignWithReason(() => this.$axios.put('/in/instUsageHis', param))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.init();
          this.fetchSearchGridWithForm();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    async delete() {
      const { forms } = this.registerForm;
      const param = FormUtil.getData(forms);
      await this.$eSignWithReason(() => this.$axios.put('/in/instUsageHis/delete', param))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.init();
          this.fetchSearchGridWithForm();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    async saveFile({ addedFiles, removedFileIds }) {
      const { forms } = this.registerForm;
      const eqmCd = FormUtil.getValue(forms, 'eqmCd');
      const eqmFileIdx = Number(FormUtil.getValue(forms, 'eqmFileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, eqmCd, eqmFileIdx };
      await this.$axios
        .postByForm('/in/instUsageHis/file', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length === 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.fetchSearchGridWithForm();
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
    setButtonsEnable(name) {
      const buttons = this.registerForm.buttons;
      if (name === 'init') {
        FormUtil.enableButtons(buttons, ['excel', 'save', 'init']);
        FormUtil.disableButtons(buttons, ['saveFile', 'update', 'delete']);
      } else {
        FormUtil.enableButtons(buttons, ['excel', 'saveFile', 'update', 'delete', 'init']);
        FormUtil.disableButtons(buttons, ['save']);
      }
    },
    onClickSearchGridWithFormCell(event) {
      const data = event.item;
      const useDsList = [event.item.useStrDs, event.item.useEndDs];
      FormUtil.setData(this.registerForm.forms, data);
      FormUtil.setData(this.registerForm.forms, { useDsList });
    },
    instrumentSearchModalReturnDataEvent(event) {
      const instrumentData = {
        plntCd: event.plntCd,
        eqmCd: event.eqmCd,
        eqmNm: event.eqmNm,
        makComp: event.makComp,
        splComp: event.splComp,
        crgNm: event.crgNm,
      };
      FormUtil.setData(this.registerForm.forms, instrumentData);
    },
    showModal(name) {
      if (name === 'instrumentSearchModal') {
        return (this.instrumentSearchModal.show = true);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'instrumentSearchModal') {
        return (this.instrumentSearchModal.show = false);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    onClicksearchGridWithFormButtons({ name }) {
      if (name === 'search') {
        this.init();
        this.fetchSearchGridWithForm();
      }
    },
    onClickRegisterFormButtons({ name }) {
      const { forms } = this.registerForm;
      if (name === 'excel') {
        alert('excel');
      }
      if (name === 'saveFile') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.registerForm.forms, 'eqmFileIdx');
        this.showModal('fileAttacherModal');
      }
      if (name === 'save') {
        forms
          .validate()
          .then(() => {
            this.save();
          })
          .catch(() => {});
      }
      if (name === 'update') {
        forms
          .validate()
          .then(() => {
            this.update();
          })
          .catch(() => {});
      }
      if (name === 'delete') {
        this.delete();
      }
      if (name === 'init') {
        this.init();
      }
    },
  },
  mounted() {
    this.fetchSearchGridWithForm();
    this.setDefaultInfo();
  },
};
</script>
