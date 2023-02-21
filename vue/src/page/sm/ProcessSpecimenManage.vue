<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
  />
  <FormWithHeader v-bind="detailInfo" @button-click="onClickButton" @form-event="formEvent" />

  <ItemModal
    :show="itemModal.show"
    :yn="itemModal.yn"
    @close="hideModal('itemModal')"
    @modalReturnDataEvent="itemModalReturnDataEvent"
  ></ItemModal>
</template>

<script>
import dayjs from 'dayjs';

import { ItemModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/processSpecimenManage';

export default {
  name: 'ProcessSpecimenManage',
  components: {
    ItemModal,
  },
  props: {
    pitmCd: { type: String, default: '' },
    pitmNm: { type: String, default: '' },
    pitmVer: { type: String, default: '' },
    lotNo: { type: String, default: '' },
  },
  mounted() {
    FormUtil.setData(this.list.forms, {
      pitmCd: this.$route.params.pitmCd,
      pitmNm: this.$route.params.pitmNm,
    });
    this.getProcessSpecimenList();
    FormUtil.setData(this.detailInfo.forms, {
      pitmCd: this.$route.params.pitmCd,
      pitmNm: this.$route.params.pitmNm,
      pitmVer: this.$route.params.pitmVer,
      pdtNo: this.$route.params.lotNo,
    });
  },
  data() {
    const { list, detailInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.init();
            this.enableButtons(['init']);
            FormUtil.setData(this.detailInfo.forms, e.item);
            FormUtil.disable(this.detailInfo.forms, ['search']);
            FormUtil.readonly(this.detailInfo.forms, ['pdtNo', 'enmDt']);
          },
        },
      },
      detailInfo: {
        ...detailInfo.static,
        forms: detailInfo.forms(),
        event: {},
      },
      itemModal: {
        show: false,
        yn: '',
      },
    };
  },
  methods: {
    async getProcessSpecimenList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/sm/processSpecimenManage', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    save() {
      const parameter = FormUtil.getData(this.detailInfo.forms);
      this.detailInfo.forms.validate().then(() => {
        this.$eSign(() => this.$axios.put('/sm/processSpecimenManage/save', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getProcessSpecimenList();
          })
          .catch(() => this.$error(this.$message.error.updateData));
      });
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getProcessSpecimenList();
      }
      if (name === 'save') {
        this.save();
      }
      if (name === 'init') {
        this.init();
      }
    },
    formEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal(event.item.itemLabel);
      }
      if (event.originEvent === 'add1Year' || event.originEvent === 'add2Year') {
        const data = FormUtil.getData(this.detailInfo.forms);
        const enmDt = dayjs(data.enmDt).add(-1, 'D').format('YYYY-MM-DD');
        let addNum = '';
        let expirDt = '';

        if (event.originEvent === 'add1Year') {
          addNum = 1;
        } else {
          addNum = 2;
        }
        expirDt = dayjs(enmDt).add(addNum, 'y').format('YYYY-MM-DD');
        FormUtil.setData(this.detailInfo.forms, { expirDt: expirDt });
      }
    },
    itemModalReturnDataEvent(data) {
      FormUtil.setData(this.detailInfo.forms, {
        pitmCd: data.pitmCd,
        pitmNm: data.pitmNm,
        pitmVer: data.pitmVer,
      });
      this.hideModal('itemModal');
    },
    init() {
      this.detailInfo.forms = values.detailInfo.forms();
      this.disableButtons(['init']);
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
