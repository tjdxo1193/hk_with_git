<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
  />
  <AUIGridSearch
    v-bind="detailList"
    @button-click="onClickButton"
    @form-event="formEvent"
    @grid-created="(proxy) => $setState('detailList.$grid', proxy)"
  />

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

import values from './values/specimenManage';

export default {
  name: 'SpecimenManage',
  components: {
    ItemModal,
  },
  props: {
    pitmCd: { type: String, default: '' },
    pitmNm: { type: String, default: '' },
    pitmVer: { type: String, default: '' },
  },
  mounted() {
    FormUtil.setData(this.list.forms, {
      pitmCd: this.$route.params.pitmCd,
      pitmNm: this.$route.params.pitmNm,
    });
    this.getSpecimenList();
    const gridData = this.list.$grid.getGridData();
    if (gridData.length == 0) {
      FormUtil.setData(this.detailList.forms, {
        pitmCd: this.$route.params.pitmCd,
        pitmNm: this.$route.params.pitmNm,
        pitmVer: this.$route.params.pitmVer,
      });
    }
  },
  data() {
    const { list, detailList, hiddenInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.init();
            this.getSpecimenHis(e);
            this.enableButtons(['init']);
            FormUtil.disable(this.detailList.forms, ['search']);
            FormUtil.readonly(this.detailList.forms, ['enmDt']);
          },
        },
      },
      detailList: {
        ...detailList.static,
        forms: detailList.forms(),
        columns: detailList.columns(),
        event: {},
      },
      hiddenInfo: {
        forms: hiddenInfo.forms(),
      },
      itemModal: {
        show: false,
        yn: '',
      },
    };
  },
  methods: {
    async getSpecimenList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/sm/specimenManage', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getSpecimenHis(event) {
      const parameter = event.item;
      FormUtil.setData(this.detailList.forms, parameter);
      FormUtil.setData(this.hiddenInfo.forms, parameter);
      this.detailList.$grid
        ._useLoader(() => this.$axios.get('/sm/specimenManage/getSpecimenHis', parameter))
        .then(({ data }) => this.detailList.$grid.setGridData(data));
    },
    save() {
      const parameter = FormUtil.getData(this.detailList.forms);
      const target = this.hiddenInfo.forms;
      let udtHis = '';
      target.map((item) => {
        const name = item.name;
        if (parameter[name] !== item.value) {
          if (udtHis !== '') {
            udtHis += ', ';
          }
          udtHis += item.itemLabel + ' : ' + item.value + ' > ' + parameter[name];
        }
      });
      Object.assign(parameter, { udtHis: udtHis });
      this.detailList.forms.validate().then(() => {
        this.$eSignWithReason(() => this.$axios.put('/sm/specimenManage/save', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getSpecimenList();
          })
          .catch(() => this.$error(this.$message.error.updateData));
      });
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getSpecimenList();
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
        const data = FormUtil.getData(this.detailList.forms);
        const enmDt = dayjs(data.enmDt).add(-1, 'D').format('YYYY-MM-DD');
        let addNum = '';
        let expirDt = '';

        if (event.originEvent === 'add1Year') {
          addNum = 1;
        } else {
          addNum = 2;
        }
        expirDt = dayjs(enmDt).add(addNum, 'y').format('YYYY-MM-DD');
        FormUtil.setData(this.detailList.forms, { expirDt: expirDt });
      }
    },
    itemModalReturnDataEvent(data) {
      FormUtil.setData(this.detailList.forms, {
        pitmCd: data.pitmCd,
        pitmNm: data.pitmNm,
        pitmVer: data.pitmVer,
      });
      this.hideModal('itemModal');
    },
    init() {
      this.detailList.forms = values.detailList.forms();
      this.detailList.$grid.clearGridData();
      this.disableButtons(['init']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.detailList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.detailList.buttons, buttons);
    },
  },
};
</script>

<style></style>
