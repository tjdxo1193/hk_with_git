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
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('detailList.$grid', proxy)"
    />
  </Card>

  <ItemModal
    :show="itemModal.show"
    :yn="itemModal.yn"
    @close="hideModal('itemModal')"
    @modalReturnDataEvent="itemModalReturnDataEvent"
  ></ItemModal>

  <RequestApproverModal
    :title="requestApproverModal.title"
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="deleteRequest"
  />
</template>

<script>
import { ItemModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/packagingSpecimenApprove';

export default {
  name: 'PackagingSpecimenApprove',
  components: {
    ItemModal,
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
            //FormUtil.setData(this.detailInfo.forms, {pmSpcmNoBf : e.item.pmSpcmNo});
            this.enableButtons(['init', 'reject', 'approve']);
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
      itemModal: {
        show: false,
        yn: 'Y',
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
        ._useLoader(() => this.$axios.get('/sm/packagingSpecimenApprove', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getPackagingSpecimenAcsr(event) {
      const parameter = event.item;
      FormUtil.setData(this.detailInfo.forms, parameter);
      this.detailList.$grid
        ._useLoader(() =>
          this.$axios.get('/sm/packagingSpecimenApprove/getPackagingSpecimenAcsr', parameter),
        )
        .then(({ data }) => this.detailList.$grid.setGridData(data));
    },
    approve() {
      const parameter = FormUtil.getData(this.detailInfo.forms);
      this.$eSignWithReason(() =>
        this.$axios.put('/sm/packagingSpecimenApprove/approve', parameter),
      )
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getPackagingSpecimenList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    reject() {
      let parameter = FormUtil.getData(this.detailInfo.forms);
      this.$eSignWithReason(() => this.$axios.put('/sm/packagingSpecimenApprove/reject', parameter))
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
      if (name === 'init') {
        this.init();
      }
      if (name === 'approve') {
        this.approve();
      }
      if (name === 'reject') {
        this.reject();
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
      });
      this.hideModal('itemModal');
    },
    init() {
      this.detailInfo.forms = values.detailInfo.forms();
      this.detailList.$grid.clearGridData();
      this.disableButtons(['init', 'reject', 'approve']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.detailInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.detailInfo.buttons, buttons);
    },
  },
};
</script>

<style></style>
