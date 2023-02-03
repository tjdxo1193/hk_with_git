<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="pItemSpecList"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('pItemSpecList.$grid', proxy)"
    />

    <AUIGridWithHeader
      v-bind="aItemList"
      @button-click="onClickButton"
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
      default: 'ELN규격 복사',
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
      }
    },
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
      const formData = FormUtil.getData(forms);
      const parameter = {labNo : formData.labNo , prdDiv : formData.prdDiv, ifDtParam: formData.ifDt};
      
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/getSemiPItemListToModal', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchAItemList({labNo, prdDiv}) {
      const { $grid } = this.aItemList;
      const parameter = {labNo, prdDiv}
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/getSemiAItemListToModal', parameter))
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
      const items = this.aItemList.$grid.getCheckedRowItemsAll();
      this.$emit('select', items);
    },
  },
};
</script>

<style></style>
