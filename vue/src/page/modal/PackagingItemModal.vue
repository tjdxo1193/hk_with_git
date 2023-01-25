<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="100" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="10" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil, TokenUtil } from '@/util';

import values from './values/packagingItemModal';

export default {
  name: 'PackagingItemModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '포장재품목조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    yn: {
      type: String,
      default: null,
    },
    gridData: {
      type: Object,
      default: {},
    },
  },
  mounted() {
    this.getPackagingItemList();
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
            let bool = new Boolean(true);
            if (this.gridData.length !== 0) {
              const target = this.gridData;
              target.map((item) => {
                if (e.item.pitmCd === item.pitmCd) {
                  this.$warn(this.$message.warn.notAddSameItem);
                  bool = false;
                }
              });
            }

            if (bool) {
              this.$emit('modalReturnDataEvent', e.item);
            }
          },
        },
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getPackagingItemList();
      }
    },
  },
  methods: {
    async getPackagingItemList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      parameter.opsSpecUseVerYn = this.$props.yn;
      parameter.plntCd = TokenUtil.myPlantCode();
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/specManage/getPackagingItemListToModal', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getPackagingItemList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getPackagingItemList();
      }
    },
    init() {
      this.searchForm.forms = values.searchForm.forms();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
