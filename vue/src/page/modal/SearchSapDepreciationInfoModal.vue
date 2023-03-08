<template>
  <ModalBase v-bind="$props" @close="close">
    <ActionBar :buttons="depreciationList.buttons" @button-click="onClickButton"></ActionBar>
    <Space :gap="10" />
    <AUIGrid v-bind="depreciationList" @grid-created="(proxy) => $setState('depreciationList.$grid', proxy)" />
    <FormBase v-bind="depreciationValueForm" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/searchSapDepreciationInfoModal';

export default {
  name: 'searchSapDepreciationInfoModal',
  emits: ['close', 'select'],
  components: {},
  props: {
    title: {
      type: String,
      default: '감가상각누계액 목록',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '450px',
    },
  },
  data() {
    const { depreciationList, depreciationValueForm } = this.$copy(values);

    return {
      depreciationList: {
        ...depreciationList.static,
        columns: depreciationList.columns(),
        buttons: depreciationList.static.buttons,
        event: {
          cellClick: (e) => {
            this.setHiddenValueForm(e.item);
          },
          cellDoubleClick: (e) => {
            this.selectItems(e.item);
          },
        },
      },
      depreciationValueForm: {
        forms: depreciationValueForm.forms(),
      }
    };
  },
  watch: {
    show: function () {
      if (this.$props.show) {
        this.doInit();
      }
    },
  },
  methods: {
    doInit() {
        this.fetchDepreciationList();
    },
    close() {
      this.$emit('close');
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchDepreciationList();
      }
      if (name === 'select') {
        const items = FormUtil.getData(this.depreciationValueForm.forms);
        this.selectItems(items);
      }
    },
    async fetchDepreciationList() {
      const {$grid} = this.depreciationList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('in/instManage/getAssetsDepreciation'))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    setHiddenValueForm(item){
      FormUtil.setData(this.depreciationValueForm.forms, item);
    },
    selectItems(items) {
      this.$emit('select', items);
      this.close();
    },
  },
};
</script>

<style></style>
