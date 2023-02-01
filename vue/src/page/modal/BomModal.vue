<template>
  <ModalBase :title="title" :show="show" width="700px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="bomGridWithForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="bomGridWithForm" @form-event="searchFormEvent" />

    <Space :gap="10" />

    <AUIGrid
      v-bind="bomGridWithForm"
      @grid-created="(proxy) => $setState('bomGridWithForm.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/bomModal';

export default {
  name: 'BomModal',
  components: {},
  props: {
    title: {
      type: String,
      default: 'BOM',
    },
    show: {
      type: Boolean,
      default: false,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    initData: {
      type: Object,
      default: {},
    },
  },
  data() {
    const { bomGridWithForm } = this.$copy(values);
    return {
      bomGridWithForm: {
        ...bomGridWithForm.static,
        forms: bomGridWithForm.forms(),
        columns: bomGridWithForm.columns(),
        isDisabledButton: true,
        event: {
          cellDoubleClick: (e) => {
            this.chooseSapItem(e.item);
          },
        },
      },
    };
  },
  mounted() {
    this.fetchBomGrid();
  },
  watch: {
    show: function () {
      FormUtil.setData(this.bomGridWithForm.forms, { matnr: this.$props.initData.pitmCd });
      this.fetchBomGrid();
    },
  },
  methods: {
    async fetchBomGrid() {
      const { $grid, forms } = this.bomGridWithForm;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/getBomList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.fetchBomGrid();
      }
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchBomGrid();
      }
    },
  },
  computed: {
    computedButtons() {
      const { buttons } = this.modalButtons;
      return buttons;
    },
  },
};
</script>

<style></style>
