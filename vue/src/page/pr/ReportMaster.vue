<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Space :gap="10" />

  <FormWithHeader v-bind="detailInfoForm" @button-click="onClickButton" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/reportMaster';

export default {
  name: 'ReportMaster',
  components: {},
  data() {
    const { searchGridWithForm, detailInfoForm } = this.$copy(values);

    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellClick: (event) => {
            this.setFormData(event);
          },
        },
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
      this.clear();
      this.fetchSearchGridWithForm();
    },
    async fetchSearchGridWithForm() {
      const { $grid, forms } = this.searchGridWithForm;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get(`/pr/reportMaster`, parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    async save(parameter) {
      if (!parameter) return;

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.put(`/pr/reportMaster`, parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.doInit();
          })
          .catch(() => this.$error(this.$message.error.createData));
      });
    },
    async update(parameter) {
      if (!parameter) return;

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.put(`/pr/reportMaster`, parameter))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.doInit();
          })
          .catch(() => this.$error(this.$message.error.updateData));
      });
    },
    async delete(parameter) {
      if (!parameter) return;

      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.post(`/pr/reportMaster`, parameter))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.doInit();
          })
          .catch(() => this.$error(this.$message.error.deleteData));
      });
    },
    preSave() {
      const { forms } = this.detailInfoForm;
      forms.validate().then(() => {
        const parameter = FormUtil.getData(forms);
        const rptIdx = parameter?.rptIdx;

        if (rptIdx) {
          this.update(parameter);
        } else {
          this.save(parameter);
        }
      });

      return;
    },
    preDelete() {
      const { forms } = this.detailInfoForm;
      const rptIdx = FormUtil.getValue(forms, 'rptIdx');

      if (!rptIdx) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      const parameter = FormUtil.getData(forms);
      this.delete(parameter);

      return;
    },
    clear() {
      this.clearSearchForm();
      this.clearDetailInfoForm();
    },
    clearSearchForm() {
      this.searchGridWithForm.forms = values.searchGridWithForm.forms();
    },
    clearDetailInfoForm() {
      this.detailInfoForm.forms = values.detailInfoForm.forms();
    },
    setFormData(event) {
      const { forms } = this.detailInfoForm;
      const item = event?.item;

      if (!item) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      FormUtil.setData(forms, item);
      return;
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.fetchSearchGridWithForm();
      }
      if (name === 'save') {
        return this.preSave();
      }
      if (name === 'delete') {
        return this.preDelete();
      }
      if (name === 'reset') {
        this.detailInfoForm.forms = values.detailInfoForm.forms();
        return;
      }
    },
    searchFormEvent({ type, originEvent }) {
      if (type === 'keydown' && originEvent.key === 'Enter') {
        return this.fetchSearchGridWithForm();
      }
    },
  },
};
</script>

<style></style>
