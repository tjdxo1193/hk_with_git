<template>
  <ModalBase v-bind="$props" @close="close">
    <template #action>
      <ActionBar :buttons="assetsList.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="assetsList" />

    <Space :gap="10" />

    <AUIGrid v-bind="assetsList" @grid-created="(proxy) => $setState('assetsList.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/searchSapAssetsInfoModal';

export default {
  name: 'searchSapAssetsInfoModal',
  emits: ['close', 'select'],
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
    const { assetsList } = this.$copy(values);
    return {
      assetsList: {
        ...assetsList.static,
        columns: assetsList.columns(),
        forms: assetsList.forms(),
        event: {
          cellDoubleClick: (e) => {
            this.selectItems(e.item);
          },
        },
      },
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
    },
    close() {
      this.$emit('close');
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchAssetsList();
      }
      if (name === 'select') {
        this.selectItems(this.getSelectedItem());
      }
    },
    async fetchAssetsList() {
      const param = FormUtil.getData(this.assetsList.forms);
      const { $grid } = this.assetsList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('in/instManage/assetsMaster', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    getSelectedItem() {
      const [selectedItem] = this.assetsList.$grid.getSelectedItems();
      if (selectedItem == null) {
        return this.$warn(this.$message.warn.unSelectedData);
      }
      return selectedItem.item;
    },
    selectItems(item) {
      this.$emit('select', item);
      this.close();
    },
  },
};
</script>

<style></style>
