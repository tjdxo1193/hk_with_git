<template>
  <ModalBase :title="title" :show="show" width="600px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <FormBase v-bind="valueWithWrapInfo" />

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
    versionInfo: {
      type: Object,
      default: {},
    },
  },
  data() {
    const { searchForm, valueWithWrapInfo } = this.$copy(values);

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
      valueWithWrapInfo: {
        forms: valueWithWrapInfo.forms(),
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
      this.reset();

      const sapPrdha = this.$props?.sapPrdha;
      if (sapPrdha) {
        const forms = this.searchForm?.forms;
        FormUtil.setData(forms, { sapPrdha });
      }

      this.fetchQmPkgaList();
    },
    reset() {
      this.searchForm.forms = values.searchForm.forms();
      this.valueWithWrapInfo.forms = values.valueWithWrapInfo.forms();
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
        this.putPkgaCd();
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
      const { sapPrdha, pkgaDiv, pkgaCd, aitmSpecIdx, pkgaTypNm } = item;
      FormUtil.setData(this.valueWithWrapInfo.forms, { pkgaCd, aitmSpecIdx, sapPrdha, pkgaTypNm });
      FormUtil.setData(this.searchForm.forms, { sapPrdha, pkgaDiv });
    },
    searchFormEvent({ type, originEvent }) {
      if (type == 'keydown') {
        if (originEvent.key == 'Enter') {
          this.fetchQmPkgaList();
        }
      }
    },
    async putPkgaCd() {
      const { pkgaCd, aitmSpecIdx, sapPrdha, pkgaTypNm } = FormUtil.getData(
        this.valueWithWrapInfo.forms,
      );

      if (!pkgaCd) {
        return this.$warn(this.$message.warn.unSelectedData);
      }
      console.log(pkgaTypNm);
      const { pitmSpecIdx, pitmCd, pitmVer } = this.$props.versionInfo;
      const parameter = { pkgaCd, aitmSpecIdx, sapPrdha, pitmSpecIdx, pitmCd, pitmVer, pkgaTypNm };

      this.$eSign(() => this.$axios.put('/ms/specManage/putPkgaCd', parameter))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.$emit('modalReturnDataEvent', parameter);
          this.close();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
  },
  computed: {},
};
</script>

<style></style>
