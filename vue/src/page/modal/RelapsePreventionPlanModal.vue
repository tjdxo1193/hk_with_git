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
import { FormUtil, TokenUtil } from '@/util';

import values from './values/relapsePreventionPlanModal';

export default {
  name: 'RelapsePreventionPlanModal',
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
        event: {
          cellDoubleClick: (e) => {
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        dropzone: null,
      },
    };
  },
  methods: {
    async getRelapsePrevList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      parameter.opsSpecUseVerYn = this.$props.yn;
      parameter.plntCd = TokenUtil.myPlantCode();
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/srmMesModal/getRelapsePrevList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getRelapsePrevList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getRelapsePrevList();
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
