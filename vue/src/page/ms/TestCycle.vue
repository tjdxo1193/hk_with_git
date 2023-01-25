<template>
  <AUIGridWithHeader v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />

  <FormWithHeader
    v-bind="detailForm"
    @button-click="onEventsButton"
    @form-event="changeInputValue"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/testCycle.js';
export default {
  name: 'TestCyclePage',

  mounted() {
    this.fetchSearchList();
    this.getCycleDivCode();
  },
  data() {
    const { list, detailForm } = this.$copy(values);
    return {
      list: {
        ...list.static,
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getItemForDetailForm(e),
        },
      },

      detailForm: {
        ...detailForm.static,
        forms: detailForm.forms(),
      },

      cycleDivCode: {
        list: [],
      },
    };
  },
  methods: {
    async fetchSearchList() {
      const { $grid } = this.list;
      const { forms } = this.detailForm;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/testCycle', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async getCycleDivCode() {
      const combo = {
        value: 'S009',
      };
      await this.$axios
        .get('combo/detailSystemCommonCode', combo)
        .then(({ data }) => (this.cycleDivCode.list = data));
    },
    getItemForDetailForm({ item }) {
      FormUtil.setData(this.detailForm.forms, item);
      this.changeButtonTypeToEdit();
    },
    changeButtonTypeToEdit() {
      FormUtil.enableButtons(this.detailForm.buttons, ['update', 'delete']);
      FormUtil.disableButtons(this.detailForm.buttons, ['save']);
    },
    changeButtonTypeToSave() {
      FormUtil.enableButtons(this.detailForm.buttons, ['save']);
      FormUtil.disableButtons(this.detailForm.buttons, ['update', 'delete']);
    },
    changeInputValue({ item, type }) {
      const ischangeableName = item.name === 'ansCylDiv' || item.name === 'ansItv';

      if ((type === 'input' && ischangeableName) || (type === 'change' && ischangeableName)) {
        const formData = FormUtil.getData(this.detailForm.forms);
        if (formData.ansItv && formData.ansCylDiv) {
          const { label } = this.cycleDivCode.list.find((row) => row.value === formData.ansCylDiv);

          formData.ansCylRuleNm = '1회/' + formData.ansItv + label;
          formData.ansCylMarkNm = '1회/' + formData.ansItv + label;
          FormUtil.setData(this.detailForm.forms, formData);
        }
      }
    },
    onEventsButton({ name }) {
      if (name == 'save') {
        this.detailForm.forms
          .validate()
          .then(() => {
            this.save();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
      if (name == 'update') {
        this.detailForm.forms
          .validate()
          .then(() => {
            this.update();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
      if (name == 'delete') {
        this.delete();
      }
      if (name == 'init') {
        this.init();
      }
    },
    save() {
      const { forms } = this.detailForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.post('/ms/testCycle', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    update() {
      const { forms } = this.detailForm;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/ms/testCycle', parameter))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.fetchSearchList();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    delete() {
      const { forms } = this.detailForm;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/ms/testCycle/delete', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.fetchSearchList();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    init() {
      this.detailForm.forms = values.detailForm.forms();
      this.fetchSearchList();
      this.changeButtonTypeToSave();
    },
  },
};
</script>

<style></style>
