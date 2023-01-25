<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
  />
  <Card>
    <FormWithHeader v-bind="detailInfo" @button-click="onClickButton" />
    <AUIGridWithHeader
      v-bind="detailList"
      :columns="computedListColumns"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('detailList.$grid', proxy)"
    />
  </Card>

  <PackagingItemModal
    :show="packagingItemModal.show"
    :yn="packagingItemModal.yn"
    :gridData="packagingItemModal.gridData"
    @close="hideModal('packagingItemModal')"
    @modalReturnDataEvent="itemModalReturnDataEvent"
  ></PackagingItemModal>

  <RequestApproverModal
    :title="requestApproverModal.title"
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="deleteRequest"
  />
</template>

<script>
import dayjs from 'dayjs';

import { PackagingItemModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/packagingSpecimenManage';

export default {
  name: 'PackagingSpecimenManage',
  components: {
    PackagingItemModal,
    RequestApproverModal,
  },
  mounted() {
    this.getPackagingSpecimenList();
  },
  data() {
    const { list, detailInfo, detailList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getPackagingSpecimenAcsr(e);
            if (e.item.pmSpcmProc === 'S0220200' || e.item.pmSpcmProc === 'S0220300') {
              FormUtil.readonly(this.detailInfo.forms, ['pmSpcmNo']);
              FormUtil.disable(this.detailInfo.forms, ['pkgMtrDiv']);
              this.detailList.$grid.setProp({ editable: false });
              this.disableButtons(['save', 'add', 'delete', 'deleteRequest']);
            } else {
              this.enableButtons(['init', 'add', 'delete', 'deleteRequest']);
            }
            //FormUtil.setData(this.detailInfo.forms, {pmSpcmNoBf : e.item.pmSpcmNo});
          },
        },
      },
      detailInfo: {
        ...detailInfo.static,
        forms: detailInfo.forms(),
      },
      detailList: {
        ...detailList.static,
        columns: detailList.columns(),
        event: {},
      },
      packagingItemModal: {
        show: false,
        yn: 'Y',
        gridData: {},
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050011',
        title: '삭제요청',
      },
    };
  },
  methods: {
    async getPackagingSpecimenList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/sm/packagingSpecimenManage', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getPackagingSpecimenAcsr(event) {
      const parameter = event.item;
      FormUtil.setData(this.detailInfo.forms, parameter);
      this.detailList.$grid
        ._useLoader(() =>
          this.$axios.get('/sm/packagingSpecimenManage/getPackagingSpecimenAcsr', parameter),
        )
        .then(({ data }) => this.detailList.$grid.setGridData(data));
    },
    save() {
      const formData = FormUtil.getData(this.detailInfo.forms);
      const parameter = {
        ...formData,
        addedRowItems: this.detailList.$grid.getAddedRowItems(),
        removedRowItems: this.detailList.$grid.getRemovedItems(),
        editedRowItems: this.detailList.$grid.getEditedRowItems(),
      };
      this.detailInfo.forms.validate().then(() => {
        this.$eSign(() => this.$axios.put('/sm/packagingSpecimenManage/save', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getPackagingSpecimenList();
          })
          .catch(() => this.$error(this.$message.error.updateData));
      });
    },
    deleteRequest(approveInfo) {
      let parameter = FormUtil.getData(this.detailInfo.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$eSignWithReason(() =>
        this.$axios.put('/sm/packagingSpecimenManage/deleteRequest', parameter),
      )
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getPackagingSpecimenList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getPackagingSpecimenList();
      }
      if (name === 'save') {
        this.save();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'add') {
        this.$setState('packagingItemModal', { gridData: this.detailList.$grid.getGridData() });
        this.showModal('packagingItemModal');
      }
      if (name === 'deleteRequest') {
        this.showModal('requestApproverModal');
      }
      if (name === 'delete') {
        const checkedRows = this.detailList.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.detailList.$grid.removeCheckedRows();
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    formEvent() {},
    itemModalReturnDataEvent(data) {
      this.detailList.$grid.addRow({
        pitmCd: data.pitmCd,
        pitmNm: data.pitmNm,
        pitmVer: data.pitmVer,
        plntCd: TokenUtil.myPlantCode(),
        aprUnm: TokenUtil.myName(),
        aprUid: TokenUtil.myId(),
        enmDt: dayjs().format('YYYY-MM-DD'),
        expirDt: dayjs().add(2, 'y').format('YYYY-MM-DD'),
      });
      this.hideModal('packagingItemModal');
    },
    init() {
      this.detailInfo.forms = values.detailInfo.forms();
      this.detailList.$grid.clearGridData();
      this.disableButtons(['init', 'deleteRequest']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.detailInfo.buttons, buttons);
      FormUtil.enableButtons(this.detailList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.detailInfo.buttons, buttons);
      FormUtil.disableButtons(this.detailList.buttons, buttons);
    },
  },
  computed: {
    computedListColumns() {
      const editableColumns = ['enmDt', 'expirDt', 'muft', 'rmk'];

      return this.detailList.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
  },
};
</script>

<style></style>
