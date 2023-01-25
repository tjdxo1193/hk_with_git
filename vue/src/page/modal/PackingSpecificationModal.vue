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

import values from './values/packingSpecificationModal';

export default {
  name: 'PackingSpecificationModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: 'MES포장사양서',
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
    async getPackingSpecList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      parameter.opsSpecUseVerYn = this.$props.yn;
      parameter.plntCd = TokenUtil.myPlantCode();
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/srmMesModal/getPackingSpecList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getPackingSpecList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getPackingSpecList();
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
