<template>
  <ModalBase
    :title="title"
    :show="show"
    :aprReqDiv="aprReqDiv"
    width="800px"
    :top="50"
    @close="close"
  >
    <template #action>
      <ActionBar :buttons="listButtonGroups.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="list" />

    <AUIGridWithHeader
      v-bind="sapItemList"
      @grid-created="(proxy) => $setState('sapItemList.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/wrapTestBaseModal';

export default {
  name: 'WrapTestBaseModal',
  emits: [],
  components: {},
  props: {
    title: {
      type: String,
      default: '포장시험',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '500px',
    },
    height: {
      type: String,
      default: '300px',
    },
    aprReqDiv: {
      type: String,
      default: null,
    },
    currentData: {
      type: Object,
      default: null,
    },
  },
  data() {
    const { list, listButtonGroups, sapItemList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
      },
      listButtonGroups: {
        buttons: listButtonGroups.buttons,
      },
      sapItemList: {
        ...sapItemList.static,
        columns: sapItemList.columns(),
        isDisabledButton: true,
        event: {
          cellDoubleClick: (e) => {
            this.chooseSapItem(e.item);
          },
        },
      },
    };
  },
  watch: {
    currentData() {
      const propData = this.$props.currentData;
      FormUtil.setData(this.list.forms, propData);
    },
  },
  mounted() {
    this.fetchSapItem();
  },
  methods: {
    async fetchSapItem() {
      const { $grid } = this.sapItemList;

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/wrapTestManage/getSap', {}))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name === 'okay') {
        this.hasInfo();
      }
    },
    hasInfo() {
      return this.list.forms.validate().then(() => {
        this.wrapTestInputBaseData();
      });
    },
    chooseSapItem(item) {
      const currentPrdha = FormUtil.getValue(this.list.forms, 'sapPrdha');

      if (!currentPrdha) {
        const { prdha } = item;
        FormUtil.setData(this.list.forms, { sapPrdha: prdha });
      }
    },
    wrapTestInputBaseData() {
      const { forms } = this.list;

      const pkgaDiv = FormUtil.getValue(forms, 'pkgaDiv');
      const sapPrdha = FormUtil.getValue(forms, 'sapPrdha');
      const baseData = { pkgaDiv, sapPrdha };

      this.$emit('modalReturnDataEvent', baseData);
      this.close();
    },
    close() {
      // FormUtil.setData(this.list.forms, { aprReqUid: '' });
      this.list.forms = values.list.forms();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
