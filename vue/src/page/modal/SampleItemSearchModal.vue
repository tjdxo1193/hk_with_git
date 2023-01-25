<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="inquireForm" @button-click="onClickSearchBtn" />

    <AUIGridWithHeader v-bind="gridForSearchResult" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/sampleItemSearchModal';
export default {
  name: 'SampleItemSearchModal',
  emits: ['close', 'select'],
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '검체품목조회',
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '700px',
    },
  },
  data() {
    const { inquireForm, gridForSearchResult } = this.$copy(values);
    return {
      inquireForm: {
        ...inquireForm.static,
        forms: inquireForm.forms(),
      },
      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),

        event: {
          cellDoubleClick: (e) => this.getStabItemByItemCode(e),
        },
      },
    };
  },
  methods: {
    async fetchSearchResult() {
      const { $grid, forms } = this.inquireForm;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan', parameter))
        .then(({ data }) => data);
    },

    onClickSearchBtn({ name }) {
      if (name === 'search') {
        this.inquireForm.forms
          .validate()
          .then(() => {
            this.fetchSearchResult();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
    },
    getStabItemByItemCode({ item }) {
      this.$emit('select', item);
      this.close();
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
