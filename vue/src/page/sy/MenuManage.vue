<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="formEvent"
  />

  <FormWithHeader v-bind="detail" @button-click="onClickButton" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/menuManage';

export default {
  name: 'MenuManage',
  mounted() {
    this.getMenuList();
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getMenuByMenuCode(e),
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
      },
    };
  },
  methods: {
    async getMenuList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/menuManage', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
      this.init();
    },
    getMenuByMenuCode(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.detail.forms, selectedItem);
    },
    formEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMenuList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMenuList();
      }
      if (name === 'save') {
        this.formDataValidate();
      }
      if (name === 'new') {
        this.init();
      }
    },
    formDataValidate() {
      this.detail.forms.validate().then(() => this.update());
    },
    update() {
      const { forms } = this.detail;
      const parameter = FormUtil.getData(forms);

      this.$eSignWithReason(() => this.$axios.put('/sy/menuManage', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getMenuList();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    init() {
      this.detail.forms = values.detail.forms();
    },
  },
};
</script>

<style></style>
