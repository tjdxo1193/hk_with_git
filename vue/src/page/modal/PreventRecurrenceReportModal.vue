<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="10" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
    <Space :gap="10" />

    <FormBase v-bind="detail">
      <template #form-dropzone>
        <Dropzone @created="$setState('detail.dropzone', $event)" :option="{ readonly }" />
      </template>
    </FormBase>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/preventRecurrenceReportModal';

export default {
  name: 'PreventRecurrenceReportModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '재발방지대책서',
    },
    show: {
      type: Boolean,
      default: false,
    },
    readonly: {
      type: Boolean,
      default: true,
    },
    parameter: {},
  },
  mounted() {},
  data() {
    const { searchForm, detail } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {},
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        dropzone: null,
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        const item = this.$props.parameter;
        FormUtil.setData(this.searchForm.forms, item);
        this.getPrvRcrReportList();
      }
    },
  },
  methods: {
    async getPrvRcrReportList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testIFModal/getPrvRcrReportList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.detail.dropzone.clear();
      this.detail.dropzone.addFiles(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getPrvRcrReportList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getPrvRcrReportList();
      }
    },
    init() {
      this.searchForm.$grid.clearGridData();
      this.searchForm.forms = values.searchForm.forms();
      this.detail.dropzone.clear();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
