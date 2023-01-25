<template>
  <ModalBase :title="title" :show="show" width="1100px" :top="30" @close="close">
    <Horizontal align-items="center" :spans="[10, 0.1, 10]">
      <AUIGridSearch
        v-bind="srmSearchForm"
        @button-click="onClickButton"
        @grid-created="(proxy) => $setState('srmSearchForm.$grid', proxy)"
      />
      <div></div>

      <AUIGridSearch
        v-bind="mesSearchForm"
        @button-click="onClickButton"
        @grid-created="(proxy) => $setState('mesSearchForm.$grid', proxy)"
      />
    </Horizontal>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/finalOrderModal';

export default {
  name: 'FinalOrderModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '마감오더조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {},
  data() {
    const { srmSearchForm, mesSearchForm } = this.$copy(values);
    return {
      srmSearchForm: {
        ...srmSearchForm.static,
        forms: srmSearchForm.forms(),
        columns: srmSearchForm.columns(),
        buttons: srmSearchForm.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
      mesSearchForm: {
        ...mesSearchForm.static,
        forms: mesSearchForm.forms(),
        columns: mesSearchForm.columns(),
        buttons: mesSearchForm.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
    };
  },
  methods: {
    getSrmFinalOrderList() {
      const { $grid, forms } = this.srmSearchForm;
      const parameter = FormUtil.getData(forms);
      const data = $grid
        ._useLoader(() => this.$axios.get('/ts/srmMesModal/getSrmFinalOrderList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    getMesFinalOrderList() {
      const { $grid, forms } = this.mesSearchForm;
      const parameter = FormUtil.getData(forms);
      const data = $grid
        ._useLoader(() => this.$axios.get('/ts/srmMesModal/getMesFinalOrderList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getSrmFinalOrderList();
      }
    },
    onClickButton({ name }) {
      if (name === 'selectSRM') {
        return this.getSrmFinalOrderList();
      }
      if (name === 'selectMES') {
        return this.getMesFinalOrderList();
      }
    },
    init() {
      this.srmSearchForm.forms = values.srmSearchForm.forms();
      this.mesSearchForm.forms = values.mesSearchForm.forms();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
