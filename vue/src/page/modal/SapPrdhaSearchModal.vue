<template>
  <ModalBase :title="title" :show="show" width="600px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />

    <Space :gap="10" />

    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/sapPrdhaSearchModal';

export default {
  name: 'SapPrdhaSearchModal',
  emits: [],
  components: {},
  props: {
    title: {
      type: String,
      default: '자재 계층 코드',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '500px',
    },
    sapPrdha: {
      type: String,
      default: () => '',
    },
  },
  data() {
    const { searchForm } = this.$copy(values);

    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.cellDoubleClick(e);
          },
        },
      },
      processCode: {
        approved: 'S0080400',
      },
    };
  },
  watch: {
    show: function () {
      if (this.$props.show) {
        this.doInit();
      }
    },
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      const sapPrdha = this.$props?.sapPrdha;
      if (sapPrdha) {
        const forms = this.searchForm?.forms;
        FormUtil.setData(forms, { sapPrdha });
      }

      this.fetchQmPkgaList();
    },
    reset() {
      this.searchForm.forms = values.searchForm.forms();
    },
    close() {
      this.reset();
      this.$emit('close');
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchQmPkgaList();
      }
      if (name === 'select') {
        this.selectSapCode();
      }
    },
    async fetchQmPkgaList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data.filter((row) => row.specProcCd == this.processCode.approved));
    },
    async getOneQmPkga() {
      const { forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);

      const data = this.$axios.get('ms/wrapTestManage/getOne', parameter).then(({ data }) => data);

      return data;
    },
    cellDoubleClick({ item = {} }) {
      const { forms } = this.searchForm;
      const { sapPrdha, pkgaDiv } = item;
      FormUtil.setData(forms, { sapPrdha, pkgaDiv });
    },
    searchFormEvent({ type, originEvent }) {
      if (type == 'keydown') {
        if (originEvent.key == 'Enter') {
          this.fetchQmPkgaList();
        }
      }
    },
    inputSapPrdha() {
      const { forms } = this.searchForm;
      const sapPrdha = FormUtil.getValue(forms, 'sapPrdha');

      if (!sapPrdha) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      } else {
        this.getOneQmPkga().then((data) => {
          if (!data) {
            this.$warn(this.$message.warn.unSelectedData);
            return;
          }

          this.$emit('modalReturnDataEvent', { sapPrdha });
          this.close();
        });
      }
    },
    selectSapCode() {
      const selectItem = this.searchForm.$grid.getSelectedRows()[0];
      if (!selectItem.sapPrdha) {
        return this.$warn(this.$message.warn.unSelectedData);
      }

      this.$emit('modalReturnDataEvent', { sapPrdha: selectItem.sapPrdha });
      this.close();
    },
  },
  computed: {},
};
</script>

<style></style>
