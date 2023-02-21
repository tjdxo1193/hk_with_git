<template>
  <ModalBase :title="title" :show="show" width="1250px" :top="100" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />

    <Space :gap="10" />

    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/testMaterialOpenItemModal';

export default {
  name: 'TestMaterialOpenItemModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '표준품조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    treeCd: {
      type: String,
      default: null,
    },
  },
  mounted() {
    this.getTestMaterialOpenItemList();
    this.setUpperRitmTreeCdCombo();
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getTestMaterialOpenItemList();
      }
    },
  },
  methods: {
    async getTestMaterialOpenItemList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      const normalUrl = '/sd/stdItemUsageHis/openItem';
      const analColUrl = '/an/analColUsageHis/openItem';
      const getOpenItemApi = this.isAnalColTree() ? analColUrl : normalUrl;
      parameter.treeCd = this.$props.treeCd;

      const data = await $grid
        ._useLoader(() => this.$axios.get(getOpenItemApi, parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    isAnalColTree() {
      return this.$props.treeCd === 'R4000000';
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestMaterialOpenItemList();
      }
    },
    searchFormEvent(event) {
      const isUpperRitmTreeCd = event.item.name === 'upperRitmTreeCd';
      if (event.type === 'change' && isUpperRitmTreeCd) {
        return this.setRitmTreeCdCombo(this.searchForm.forms, event.item.value);
      }
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getTestMaterialOpenItemList();
      }
    },
    init() {
      this.searchForm.forms = values.searchForm.forms();
    },
    close() {
      this.init();
      this.$emit('close');
    },
    setRitmTreeCdCombo(forms, upperRitmTreeCd, initValue = '') {
      forms.map((item) => {
        if (item.name == 'ritmTreeCd') {
          item.value = initValue;
          item.async(upperRitmTreeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
    setUpperRitmTreeCdCombo() {
      this.searchForm.forms.map((item) => {
        if (item.name == 'upperRitmTreeCd') {
          item.async(this.$props.treeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
  },
};
</script>

<style></style>
