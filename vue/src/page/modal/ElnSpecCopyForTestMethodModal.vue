<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="pItemSpecList"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('pItemSpecList.$grid', proxy)"
    />

    <AUIGridWithHeader
      v-bind="aItemList"
      @grid-created="(proxy) => $setState('aItemList.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import {FormUtil} from '@/util';

import values from './values/elnSpecCopyForTestMethodModal.js';

export default {
  name: 'elnSpecCopyForTestMethodModal',
  emits: ['close', 'select'],
  props: {
    title: {
      type: String,
      default: '시험항목목록 복사',
    },
    width: {
      type: String,
      default: '1200px',
    },
    height: {
      type: String,
      default: '900px',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  watch: {
    show: function () {
      if (this.$props.show) {
        this.aItemList.$grid.clearGridData();
        this.pItemSpecList.forms = values.pItemSpecList.forms();
        this.fetchPItemSpecList();
      }
    },
  },
  mounted() {
    this.fetchPItemSpecList();
  },
  data() {
    const { pItemSpecList, aItemList } = this.$copy(values);
    return {
      pItemSpecList: {
        ...pItemSpecList.static,
        forms: pItemSpecList.forms(),
        columns: pItemSpecList.columns(),
        buttons: pItemSpecList.static.buttons,
        event: {
          cellClick: (e) => {
            this.fetchAItemList(e.item);
          },
        },
      },
      aItemList: {
        ...aItemList.static,
        columns: aItemList.columns(),
      },
    };
  },
  methods: {
    async fetchPItemSpecList() {
      const { $grid, forms } = this.pItemSpecList;
      const processApproveCode = 'S0080400';
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/pItemSpecListToModal', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data.filter((row) => row.specProcCd == processApproveCode));
    },
    async fetchAItemList({ aitmSpecIdx }) {
      const { $grid } = this.aItemList;
      const parameter = { aitmSpecIdx };
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/aItemListToModal', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    getItems() {},
    onClickButton({ name }) {
      if (name === 'search') {
        this.pItemSpecList.forms.validate().then(() => {
          this.fetchPItemSpecList();
        });
      }
      if (name === 'select') {
        this.selectItems();
        this.close();
      }
    },
    close() {
      this.$emit('close');
    },
    selectItems() {
      const items = this.aItemList.$grid.getGridData();
      this.$emit('select', items);
    },
  },
};
</script>

<style></style>
