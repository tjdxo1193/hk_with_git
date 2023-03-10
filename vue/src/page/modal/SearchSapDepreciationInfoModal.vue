<template>
  <ModalBase v-bind="$props" @close="close" height="100%">
    <template #action>
      <ActionBar :buttons="depreciationList.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase :forms="depreciationList.forms" />

    <Space :gap="10" />

    <AUIGrid
      v-bind="depreciationList"
      @grid-created="(proxy) => $setState('depreciationList.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/searchSapDepreciationInfoModal';

export default {
  name: 'searchSapDepreciationInfoModal',
  emits: ['close', 'select'],
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
    anlkl: {
      type: String,
      default: null,
      description: `
        자산클래스.
        유무형 감가상각 데이터를 조회하기 위해서는
        유무형 자산 마스터에서 가져온 자산 클래스 값이 필요합니다.
      `,
    },
  },
  data() {
    const { depreciationList } = this.$copy(values);

    return {
      depreciationList: {
        ...depreciationList.static,
        columns: depreciationList.columns(),
        buttons: depreciationList.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.selectItems(e.item);
          },
        },
        forms: depreciationList.forms(),
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
        this.selectItems(this.getSelectedItem());
      }
    },
    async fetchDepreciationList() {
      const param = FormUtil.getData(this.depreciationList.forms);
      param.anlkl = this.$props.anlkl;

      const { $grid } = this.depreciationList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('in/instManage/assetsDepreciation', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    getSelectedItem() {
      const [selectedItem] = this.depreciationList.$grid.getSelectedItems();
      if (selectedItem == null) {
        return this.$warn(this.$message.warn.unSelectedData);
      }
      return selectedItem.item;
    },
    selectItems(items) {
      this.$emit('select', items);
      this.close();
    },
  },
};
</script>

<style></style>
