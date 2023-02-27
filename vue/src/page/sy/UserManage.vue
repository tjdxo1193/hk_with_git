<template>
  <AUIGridSearch
    v-bind="{ ...userGrid }"
    @button-click="onClickButtonToSearchForm"
    @grid-created="(proxy) => $setState('userGrid.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FormWithHeader
    v-bind="{ ...userForm }"
    @button-click="onClickButtonToUserForm"
    @form-event="userFormEvent"
  />

  <DepartmentModal
    title="부서목록"
    :show="departmentModal.show"
    :readOnly="true"
    @close="hideDepartmentModal()"
    width="800px"
    height="400px"
    @select="setDptInfoToUserForm"
  ></DepartmentModal>
</template>

<script>
import { DepartmentModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/userManage';

export default {
  name: 'userManagePage',
  components: {
    DepartmentModal,
  },
  mounted() {
    this.getUserList();
  },
  data() {
    const { userGrid, userForm } = this.$copy(values);
    return {
      userGrid: {
        ...userGrid.static,
        forms: userGrid.forms(),
        columns: userGrid.columns(),
        event: {
          cellDoubleClick: (e) => this.getUserToGrid(e),
        },
      },
      userForm: {
        ...userForm.static,
        forms: userForm.forms(),
      },
      departmentModal: {
        show: false,
      },
    };
  },
  methods: {
    // 유저 조회 폼
    onClickButtonToSearchForm({ name }) {
      if (name === 'select') {
        this.getUserList();
      }
    },
    async getUserList() {
      const { $grid, forms } = this.userGrid;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/userManage', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
      this.initUserForm();
    },
    getUserToGrid(event) {
      const selectedItem = event.item;
      this.enableSaveButtonInUserForm();
      FormUtil.setData(this.userForm.forms, selectedItem);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getUserList();
      }
    },
    // 유저 상세정보 폼
    onClickButtonToUserForm({ name }) {
      if (name === 'save') {
        this.saveUserForm();
      }
      if (name === 'init') {
        this.initUserForm();
      }
    },
    userFormEvent(event) {
      if (event.originEvent === 'search') {
        this.showDepartmentModal();
      }
      if (event.originEvent === 'initPwd') {
        this.changePwd();
      }
    },
    saveUserForm() {
      this.userForm.forms
        .validate()
        .then(() => {
          this.update();
        })
        .catch(() => {});
    },
    changePwd() {
      this.userForm.forms
        .validate()
        .then(() => {
          this.initPwd();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async update() {
      const { forms } = this.userForm;
      const parameter = FormUtil.getData(forms);
      await this.$axios
        .put('/sy/userManage', parameter)
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getUserList();
          this.initUserForm();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    initSearchForm() {
      this.userGrid.forms = values.userGrid.forms();
    },
    initUserForm() {
      this.disableSaveButtonInUserForm();
      this.userForm.forms = values.userForm.forms();
    },
    showDepartmentModal() {
      this.$setState('departmentModal', { show: true });
    },
    hideDepartmentModal() {
      this.$setState('departmentModal', { show: false });
    },
    setDptInfoToUserForm({ dptNm, dptCd }) {
      FormUtil.setData(this.userForm.forms, { dptNm: dptNm, dptCd: dptCd });
    },
    disableSaveButtonInUserForm() {
      this.userForm.buttons = FormUtil.mapButtonsType(this.userForm.buttons, ['save'], 'disabled');
    },
    enableSaveButtonInUserForm() {
      this.userForm.buttons = FormUtil.mapButtonsType(this.userForm.buttons, ['save'], 'default');
    },
    async initPwd() {
      const { forms } = this.userForm;
      const parameter = FormUtil.getData(forms);
      await this.$axios
        .put('/sy/userManage/initPwd', parameter)
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getUserList();
          this.initUserForm();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
  },
};
</script>

<style></style>
