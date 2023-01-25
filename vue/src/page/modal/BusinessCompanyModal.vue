<template>
  <ModalBase :title="title" :show="show" width="700px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="mainButtons.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="list" @form-event="searchFormEvent" />

    <Space :gap="10" />

    <AUIGrid v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />

    <FormBase v-bind="detail" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/businessCompanyModal';

export default {
  name: 'BusinessCompanyModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '거래처조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.getCompanyList();
  },
  data() {
    const { mainButtons, list, detail } = this.$copy(values);
    return {
      mainButtons: {
        buttons: mainButtons.buttons,
      },
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getVendorByVdrCd(e);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getCompanyList();
      }
    },
  },
  methods: {
    async getCompanyList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/vendor', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    isDisabeldColumn(data) {
      if (data.name === 'vdrDiv' || data.name === 'vdrNm') {
        return true;
      }
    },
    getVendorByVdrCd(event) {
      const selectedItem = event.item;
      const validate = this.detail.forms.validate;

      this.detail.forms = this.detail.forms.map((data) => ({
        ...data,
        disabled: this.isDisabeldColumn(data) ? true : false,
      }));
      this.detail.forms.validate = validate;

      FormUtil.setData(this.detail.forms, selectedItem);
      this.activeButtons();
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getCompanyList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getCompanyList();
        this.initDetail();
        this.inActiveButtons();
      }
      if (name === 'create' || name === 'update') {
        this.formDataValidate();
      }
      if (name === 'choose') {
        const selectedItem = this.list.$grid.getSelectedRows();
        this.$emit('modalReturnDataEvent', selectedItem[0]);
        this.close();
      }
    },
    formDataValidate() {
      this.detail.forms.validate().then(() => (this.isExist() ? this.update() : this.create()));
    },
    isExist() {
      return FormUtil.existsValue(this.detail.forms, 'vdrCd');
    },
    create() {
      const parameter = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.post('/ms/vendor', parameter))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getCompanyList();
            FormUtil.setData(this.detail.forms, data);
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    update() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ms/vendor', param))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getCompanyList();
            FormUtil.setData(this.detail.forms, data);
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    initSearchForm() {
      this.list.forms = values.list.forms();
    },
    initDetail() {
      this.detail.forms = values.detail.forms();
    },
    activeButtons() {
      const buttonNames = this.mainButtons.buttons.map((button) => {
        return button.name;
      });

      FormUtil.enableButtons(this.mainButtons.buttons, buttonNames);
    },
    inActiveButtons() {
      FormUtil.disableButtons(this.mainButtons.buttons, ['update', 'delete']);
    },
    close() {
      this.initSearchForm();
      this.initDetail();
      this.inActiveButtons();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
