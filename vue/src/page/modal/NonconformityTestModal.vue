<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
    <Space :gap="10" />
  </ModalBase>
</template>

<script>
import values from './values/nonconformityTestModal';

export default {
  name: 'NonconformityTestModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '자재시험의뢰부적합조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    reqIdx: {
      type: String,
      default: null,
    },
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {},
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getNonconformityTestList();
      }
    },
  },
  methods: {
    async getNonconformityTestList() {
      const { $grid } = this.searchForm;
      const parameter = { reqIdx: this.$props.reqIdx };
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testReceipt/getNonconformityTestList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getNonconformityTestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getNonconformityTestList();
      }
    },
    init() {},
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
