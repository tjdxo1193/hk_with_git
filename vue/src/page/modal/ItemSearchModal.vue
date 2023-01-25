<template>
  <ModalBase v-bind="$props" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <template #body>
      <FormBase v-bind="searchForm" />
      <Space :gap="10" />
      <AUIGrid v-bind="searchForm" />
    </template>
  </ModalBase>
</template>

<script>
import values from './values/itemSearchModal';

export default {
  name: 'ItemSearchModal',
  emits: ['close'],
  props: {
    show: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.getItemList();
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
      },
    };
  },
  methods: {
    async getItemList() {},
    onClickButton({ name }) {
      if (name === 'select') {
        this.getItemList();
      }
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
