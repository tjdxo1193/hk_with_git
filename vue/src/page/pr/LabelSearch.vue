<template>
  <AUIGridSearch
    v-bind="labelSearchGridWithForms"
    @grid-created="(proxy) => $setState('labelSearchGridWithForms.$grid', proxy)"
    @button-click="onButtonClick"
    @form-event="labelSearchGridWithFormsEvent"
  />

  <FormWithHeader v-bind="labelSearchInfoForms" @button-click="onButtonClick" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/labelSearch';

export default {
  name: 'LabelSearch',
  components: {},
  data() {
    const { labelSearchGridWithForms, labelSearchInfoForms } = this.$copy(values);
    return {
      labelSearchGridWithForms: {
        ...labelSearchGridWithForms.static,
        forms: labelSearchGridWithForms.forms(),
        columns: labelSearchGridWithForms.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.labelSearchGridWithFormsDoubleClicked(event);
          },
        },
      },
      labelSearchInfoForms: {
        ...labelSearchInfoForms.static,
        forms: labelSearchInfoForms.forms(),
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchLabelSearchGridWithForms();
      this.resetLabelSearchInfoForms();
    },
    async fetchLabelSearchGridWithForms() {
      const { forms, $grid } = this.labelSearchGridWithForms;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/pr/labelSearch', parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    resetLabelSearchInfoForms() {
      this.labelSearchInfoForms.forms = values.labelSearchInfoForms.forms();
    },
    onButtonClick({ name }) {
      if (name === 'select') {
        this.doInit();
      } else if (name === 'reset') {
        this.resetLabelSearchInfoForms();
      }
    },
    labelSearchGridWithFormsEvent({ type, originEvent }) {
      if (type === 'keydown' && originEvent.key === 'Enter') {
        this.doInit();
      }
    },
    labelSearchGridWithFormsDoubleClicked(event) {
      const { forms } = this.labelSearchInfoForms;
      const { item } = event;

      FormUtil.setData(forms, item);
    },
  },
};
</script>

<style></style>
