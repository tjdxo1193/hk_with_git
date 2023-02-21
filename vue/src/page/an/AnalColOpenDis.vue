<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButton" />

    <Horizontal align-items="center" :spans="[5, 5]">
      <FormWithHeader v-bind="detailColumn" />

      <FormWithHeader v-bind="detailInfo" />
    </Horizontal>
  </Card>

  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  />

  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="approveModalReturnDataEvent"
  ></RequestApproverModal>
</template>

<script>
import dayjs from 'dayjs';

import { FileAttacherModal, RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/analColOpenDis';

export default {
  name: 'AnalColOpenDis',
  components: {
    FileAttacherModal,
    RequestApproverModal,
  },
  mounted() {
    this.getAnalColOpenDis();
  },
  data() {
    const { list, detailColumn, detailInfo, buttonGroups } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getAnalColOpenDisByRitmMngIdx(e);
          },
        },
      },
      detailColumn: {
        ...detailColumn.static,
        forms: detailColumn.forms(),
      },
      detailInfo: {
        ...detailInfo.static,
        forms: detailInfo.forms(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050002',
      },
    };
  },
  methods: {
    async getAnalColOpenDis() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/an/analColOpenDis', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    getAnalColOpenDisByRitmMngIdx(event) {
      const selectedItem = event.item;
      const opnDt = dayjs().format('YYYY-MM-DD');

      FormUtil.setData(this.detailColumn.forms, selectedItem);
      FormUtil.setData(this.detailInfo.forms, selectedItem);
      selectedItem.opnDt == null
        ? FormUtil.setData(this.detailInfo.forms, { opnDt })
        : selectedItem.opnDt;
      this.setChangeButtonsState(selectedItem.mngProcCd);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.initDetail();
      }
      if (name === 'fileSave') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.detailInfo.forms, 'fileIdx');
        return this.showModal('fileAttacherModal');
      }
      if (name === 'openItem' || name === 'update') {
        return this.dataCheckBeforeSave();
      }
      if (name === 'disposalItem') {
        this.$confirm(this.$message.confirm.disposalRequest).then(() => {
          return this.showModal('requestApproverModal');
        });
      }
      if (name === 'init') {
        return this.initDetail();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getAnalColOpenDis();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    initDetail() {
      this.detailColumn.forms = values.detailColumn.forms();
      this.detailInfo.forms = values.detailInfo.forms();
      this.disableButtons(['fileSave', 'openItem', 'update', 'disposalItem']);
      this.getAnalColOpenDis();
    },
    dataCheckBeforeSave() {
      const stockState = 'S0020100';
      const mngProcCd = FormUtil.getValue(this.detailInfo.forms, 'mngProcCd');
      this.detailInfo.forms.validate().then(() => {
        mngProcCd === stockState ? this.open() : this.update();
      });
    },
    open() {
      const parameter = FormUtil.getData(this.detailInfo.forms);

      this.$confirm(this.$message.confirm.opened).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColOpenDis/open', parameter))
          .then(() => {
            this.$info(this.$message.info.opened);

            const openState = 'S0020200';
            this.getAnalColOpenDis();
            this.setChangeButtonsState(openState);
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    update() {
      const parameter = FormUtil.getData(this.detailInfo.forms);

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColOpenDis', parameter))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.getAnalColOpenDis();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    approveModalReturnDataEvent(data) {
      const parameter = FormUtil.getData(this.detailInfo.forms);

      this.$eSign(() => this.$axios.put('/an/analColOpenDis/disposal', { ...parameter, ...data }))
        .then(() => {
          this.$info(this.$message.info.disposalRequest);
          this.getAnalColOpenDis();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    showModal(name) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.detailInfo.forms, 'fileIdx');
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.buttonGroups.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.buttonGroups.buttons, buttons);
    },
    isDisposalRequest() {
      const isDisposalRequest = FormUtil.getValue(this.list.forms, 'mngProcCd');
      const disposalRequestState = 'S0020200';
      return isDisposalRequest === disposalRequestState ? true : false;
    },
    setChangeButtonsState(mngProcCd) {
      const stockState = 'S0020100';
      const openState = 'S0020200';

      if (mngProcCd === stockState && !this.isDisposalRequest()) {
        this.enableButtons(['fileSave', 'openItem']);
        this.disableButtons(['update', 'disposalItem']);
        return;
      }
      if (mngProcCd === openState && !this.isDisposalRequest()) {
        this.enableButtons(['fileSave', 'update']);
        this.disableButtons(['openItem', 'disposalItem']);
        return;
      }
      if (this.isDisposalRequest()) {
        this.enableButtons(['fileSave', 'disposalItem']);
        this.disableButtons(['update', 'openItem']);
        return;
      }
    },
    setRitmTreeCdCombo(forms, upperRitmTreeCd, initValue = '') {
      forms.map((item) => {
        if (item.name == 'ritmTreeCd') {
          item.value = initValue;
          item.async(upperRitmTreeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = FormUtil.getValue(this.detailInfo.forms, 'ritmEtrIdx');
      const fileIdx = Number(FormUtil.getValue(this.detailInfo.forms, 'fileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/an/analColReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getAnalColOpenDis();
          this.getFileListByFileIdx(fileIdx, data);
        })
        .catch(() => {
          this.$error(this.$message.error.savedFiles);
        });
    },
    getFileListByFileIdx(originFileIdx, fileIdx) {
      if (originFileIdx > 0) {
        return this.$refs.fileAttacherModal.getFileList();
      } else {
        return this.setInitFileIdx(fileIdx);
      }
    },
    setInitFileIdx(fileIdx) {
      this.fileAttacherModal.fileIdx = fileIdx;
      FormUtil.setData(this.detailInfo.forms, { fileIdx });
    },
  },
};
</script>
