<template>
  <ModalBase v-bind="$props" @close="close">
    <ActionBar :buttons="assetsList.buttons" @button-click="onClickButton"></ActionBar>
    <AUIGrid v-bind="assetsList" @grid-created="(proxy) => $setState('assetsList.$grid', proxy)" />
    <FormBase v-bind="assetsValueForm" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/SearchSapAssetsInfoModal';

export default {
  name: 'searchSapAssetsInfoModal',
  emits: ['close', 'select'],
  components: {},
  props: {
    title: {
      type: String,
      default: '자산정보 목록',
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
    const { assetsList, assetsValueForm } = this.$copy(values);

    return {
      assetsList: {
        ...assetsList.static,
        columns: assetsList.columns(),
        buttons: assetsList.static.buttons,
        event: {
          cellClick: (e) => {
            this.setHiddenValueForm(e.item);
          },
          cellDoubleClick: (e) => {
            this.selectItems(e.item);
          },
        },
      },
      assetsValueForm: {
        forms: assetsValueForm.forms(),
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
        this.fetchAssetsList();
        this.assetsValueForm.forms = values.assetsValueForm.forms();
    },
    close() {
      this.$emit('close');
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchAssetsList();
      }
      if (name === 'select') {
        const item = FormUtil.getData(this.assetsValueForm.forms);
        if(!item.anln1){
          return this.$warn(this.$message.warn.unSelectedData);
        }
        this.selectItems(item);
      }
    },
    async fetchAssetsList() {
      const {$grid} = this.assetsList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('in/instManage/getAssetsMaster', {}))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    setHiddenValueForm(item){
      FormUtil.setData(this.assetsValueForm.forms, item);
    },
    selectItems(item) {
      this.$emit('select', item);
      this.close();
    },
  },
};
</script>

<style></style>
