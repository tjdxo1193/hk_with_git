<template>
  <ModalBase :title="title" :show="show" width="1250px" :top="100" @close="close">
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import values from './values/interfaceRecordModal';

export default {
  name: 'InterfaceRecordModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '이력조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    ispPdtPfaIdx: {
      type: Number,
      default: 0,
    },
    ispPhsPfaIdx: {
      type: Number,
      default: 0,
    },
  },
  mounted() {
    if (this.existsIspPdtPfaIdx()) {
      this.getProductRecord();
    }
    if (this.existsIspPhsPfaIdx()) {
      this.getPurchasingRecord();
    }
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        columns: searchForm.columns(),
      },
    };
  },
  watch: {
    ispPdtPfaIdx() {
      if (this.existsIspPdtPfaIdx()) {
        this.getProductRecord();
      }
    },
    ispPhsPfaIdx() {
      if (this.existsIspPhsPfaIdx()) {
        this.getPurchasingRecord();
      }
    },
    show() {
      if (this.$props.show && this.existsIspPdtPfaIdx()) {
        this.getProductRecord();
      }
      if (this.$props.show && this.existsIspPhsPfaIdx()) {
        this.getPurchasingRecord();
      }
    },
  },
  methods: {
    existsIspPdtPfaIdx() {
      return this.$props.ispPdtPfaIdx > 0;
    },
    existsIspPhsPfaIdx() {
      return this.$props.ispPhsPfaIdx > 0;
    },
    async getProductRecord() {
      const { $grid } = this.searchForm;
      const ispPdtPfaIdx = this.$props.ispPdtPfaIdx;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/sc/inspectProductionPerformance/${ispPdtPfaIdx}`))
        .then(({ data }) => data);

      $grid.showColumnByDataField(['ispPfaCanlYn']);
      $grid.setGridData(data);
    },
    async getPurchasingRecord() {
      const { $grid } = this.searchForm;
      const ispPhsPfaIdx = this.$props.ispPhsPfaIdx;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/sc/inspectPurchasingPerformance/${ispPhsPfaIdx}`))
        .then(({ data }) => data);

      $grid.hideColumnByDataField(['ispPfaCanlYn']);
      $grid.setGridData(data);
    },
    close() {
      this.searchForm.$grid.clearGridData();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
