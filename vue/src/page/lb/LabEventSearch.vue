<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridButtons"
  />

  <Space :gap="10" />

  <FormWithHeader v-bind="reviewInfoForm" />

  <Space :gap="10" />

  <FormWithHeader v-bind="detailInfoForm" />
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/labEventSearch';

export default {
  name: 'LabEventSearch',
  components: {},
  data() {
    const { searchGridWithForm, reviewInfoForm, detailInfoForm } = this.$copy(values);

    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setFormData(event);
          },
        },
      },
      reviewInfoForm: {
        ...reviewInfoForm.static,
        forms: reviewInfoForm.forms(),
      },
      detailInfoForm: {
        ...detailInfoForm.static,
        forms: detailInfoForm.forms(),
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchSearchGrid();
      this.clear();
    },
    async fetchSearchGrid() {
      const { $grid, forms } = this.searchGridWithForm;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/lb/labEventSearch', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    clear() {
      this.clearSearchForm();
      this.clearReviewInfoForm();
      this.clearDetailInfoForm();
    },
    clearSearchForm() {
      this.searchGridWithForm.forms = values.searchGridWithForm.forms();
    },
    clearReviewInfoForm() {
      this.reviewInfoForm.forms = values.reviewInfoForm.forms();
    },
    clearDetailInfoForm() {
      this.detailInfoForm.forms = values.detailInfoForm.forms();
    },
    setFormData(event = null) {
      const reviewInfoFormForms = this.reviewInfoForm.forms;
      const detailInfoFormForms = this.detailInfoForm.forms;
      const item = event?.item;

      if (!item) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      FormUtil.setData(reviewInfoFormForms, item);
      FormUtil.setData(detailInfoFormForms, item);
      return;
    },
    onClickSearchGridButtons({ name = null }) {
      if (name === 'search') {
        this.searchGridWithForm.forms
          .validate()
          .then(
            () => this.fetchSearchGrid(),
            this.clearReviewInfoForm(),
            this.clearDetailInfoForm(),
          )
          .catch(() => {});
      }
    },
  },
};
</script>

<style></style>
