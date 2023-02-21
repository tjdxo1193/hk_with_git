<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButtonToSearchForm"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FormWithHeader
    v-bind="detail"
    @button-click="onClickButtonToDetailForm"
    @form-event="detailFormEvent"
  />

  <DepartmentModal
    title="부서목록"
    :show="departmentModal.show"
    @close="hideDepartmentModal()"
    width="800px"
    height="400px"
    :readOnly="false"
    @select="setHirDptInfoToDetailForm"
  />
</template>

<script>
import { DepartmentModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/departmentManage';
export default {
  name: 'DepartmentManagePage',
  components: {
    DepartmentModal,
  },
  mounted() {
    this.getDepartmentList();
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getDepartmentByDepartmentCode(e),
        },
      },
      departmentModal: {
        show: false,
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
      },
    };
  },
  methods: {
    // 부서 조회폼
    onClickButtonToSearchForm({ name }) {
      if (name === 'select') {
        this.getDepartmentList();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getDepartmentList();
      }
    },
    async getDepartmentList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/departmentManage', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
      this.initDetailForm();
    },
    getDepartmentByDepartmentCode({ item, rowIdValue }) {
      const isNotUpdatableRow = item._$depth === 1;
      if (isNotUpdatableRow) {
        return this.$error(this.$message.validate.topLevel);
      }

      const { $grid } = this.list;
      FormUtil.disableButtons(this.detail.forms[0]._multiForms, ['search']);
      FormUtil.setData(this.detail.forms, {
        ...item,
        hirDptNm: $grid.getParentItemByRowId(rowIdValue).dptNm,
      });
    },
    // 부서 상세폼
    onClickButtonToDetailForm({ name }) {
      if (name === 'save') {
        this.detail.forms
          .validate()
          .then(() => {
            this.saveDetailForm();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
      if (name === 'new') {
        this.initDetailForm();
      }
    },
    detailFormEvent(event) {
      if (event.originEvent === 'search') {
        this.showDepartmentModal();
      }
    },
    saveDetailForm() {
      this.detail.forms.validate().then(() => {
        this.isExistDptCdInDetailForm() ? this.update() : this.insert();
      });
    },
    isExistDptCdInDetailForm() {
      return FormUtil.existsValue(this.detail.forms, 'dptCd');
    },
    insert() {
      const { forms } = this.detail;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.post('/sy/departmentManage', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getDepartmentList();
          this.initDetailForm();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    update() {
      const { forms } = this.detail;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/sy/departmentManage', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getDepartmentList();
          this.initDetailForm();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    initDetailForm() {
      this.detail.forms = values.detail.forms();
    },
    showDepartmentModal() {
      this.$setState('departmentModal', { show: true });
    },
    hideDepartmentModal() {
      this.$setState('departmentModal', { show: false });
    },
    setHirDptInfoToDetailForm({ dptNm, dptCd }) {
      this.initDetailForm();
      FormUtil.setData(this.detail.forms, { hirDptCd: dptCd, hirDptNm: dptNm });
    },
  },
};
</script>

<style></style>
