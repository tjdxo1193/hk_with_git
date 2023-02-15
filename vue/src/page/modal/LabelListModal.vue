<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="labelListGridWithForms"
      @button-click="onButtonClick"
      @grid-created="(proxy) => $setState('labelListGridWithForms.$grid', proxy)"
    />
    <ActionBar :buttons="buttonGroups.buttons" @button-click="onButtonClick" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/labelListModal';

export default {
  name: 'LabelListModal',
  emits: ['close'],
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '라벨목록 조회',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '600px',
    },
    ansIdx: {
      type: Number,
      default: null,
    },
  },
  data() {
    const { labelListGridWithForms, buttonGroups } = this.$copy(values);
    return {
      labelListGridWithForms: {
        ...labelListGridWithForms.static,
        forms: labelListGridWithForms.forms(),
        columns: labelListGridWithForms.columns(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
    };
  },
  watch: {
    show: function () {
      const { show } = this.$props;
      if (show) {
        this.doInit();
      }
    },
  },
  methods: {
    doInit() {
      this.fetchLabelListGridWithForms();
      this.resetLabelListGridWithForms();
    },
    async fetchLabelListGridWithForms() {
      const { forms, $grid } = this.labelListGridWithForms;
      const { ansIdx } = this.$props;
      const parameter = { ansIdx, ...FormUtil.getData(forms) };

      const data = await $grid
        ._useLoader(() => this.$axios.get('/pr/rePrintLabelRequest/labelList', parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    resetLabelListGridWithForms() {
      this.labelListGridWithForms.forms = values.labelListGridWithForms.forms();
    },
    onButtonClick({ name }) {
      if (name === 'select') {
        this.fetchLabelListGridWithForms();
      } else if (name === 'close') {
        this.close();
      }
    },
    close() {
      this.$emit('close', 'labelListModal');
    },
  },
};
</script>

<style></style>
