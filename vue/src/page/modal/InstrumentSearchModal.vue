<template>
  <ModalBase :title="title" :show="show" width="700px" @close="close">
    <AUIGridSearch
      v-bind="instrumentGridWithForm"
      @button-click="onClickInstrumentGridButtons"
      @grid-created="(proxy) => $setState('instrumentGridWithForm.$grid', proxy)"
    />
    <template #footer>
      <ButtonBase @click="onSelect">{{ selectText }}</ButtonBase>
      <ButtonBase @click="close" type="normal">{{ cancelText }}</ButtonBase>
    </template>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/instrumentSearchModal.js';

export default {
  name: 'InstrumentSearchModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '기기조회',
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '600px',
    },
    show: {
      type: Boolean,
      default: false,
    },
    selectText: {
      type: String,
      default: '선택',
    },
    cancelText: {
      type: String,
      default: '취소',
    },
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.fetchInstrumentGrid();
      }
    },
  },
  mounted() {
    this.fetchInstrumentGrid();
  },
  data() {
    const { instrumentGridWithForm, modalButtons } = this.$copy(values);
    return {
      instrumentGridWithForm: {
        ...instrumentGridWithForm.static,
        buttons: instrumentGridWithForm.static.buttons,
        forms: instrumentGridWithForm.forms(),
        columns: instrumentGridWithForm.columns(),
      },
      modalButtons: {
        ...modalButtons.buttons,
      },
    };
  },
  methods: {
    async fetchInstrumentGrid() {
      const param = FormUtil.getData(this.instrumentGridWithForm.forms);
      const { $grid } = this.instrumentGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instUsageHis/instrument', param))
        .then(({ data }) => data)
        .catch(() => {});
      $grid.setGridData(data);
    },
    close() {
      this.instrumentGridWithForm.forms = values.instrumentGridWithForm.forms();
      this.$emit('close');
    },
    onSelect() {
      const { $grid } = this.instrumentGridWithForm;
      const data = $grid.getSelectedRows();
      const param = { ...data[0] };
      this.$emit('modalReturnDataEvent', param);
      this.close();
    },
    onClickInstrumentGridButtons({ name }) {
      if (name === 'search') {
        this.fetchInstrumentGrid();
      }
    },
  },
};
</script>
