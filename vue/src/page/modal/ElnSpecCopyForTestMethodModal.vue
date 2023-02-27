<template>
  <ModalBase v-bind="$props" @close="close">

    <FormBase
      v-bind="pItemSpecForm"
      @button-click="onClickButton"
    />

    <AUIGridSearch
       v-bind="pItemSpecList"
      @grid-created="(proxy) => $setState('pItemSpecList.$grid', proxy)"
    />

    <AUIGridWithHeader
      v-bind="aItemList"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('aItemList.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/elnSpecCopyForTestMethodModal.js';

export default {
  name: 'elnSpecCopyForTestMethodModal',
  emits: ['close', 'select'],
  props: {
    title: {
      type: String,
      default: 'ELN규격 복사',
    },
    width: {
      type: String,
      default: '1200px',
    },
    height: {
      type: String,
      default: '900px',
    },
    show: {
      type: Boolean,
      default: false,
    },
    labNo: {
      type: String,
    }
  },
  watch: {
    show: function () {
      if (this.$props.show) {
        this.pItemSpecList.$grid.clearGridData();
        this.aItemList.$grid.clearGridData();
        this.pItemSpecForm.forms = values.pItemSpecForm.forms();
        FormUtil.setData(this.pItemSpecForm.forms, {labNo : this.$props.labNo});
      }
    },
  },
  data() {
    const { pItemSpecList, aItemList, pItemSpecForm } = this.$copy(values);
    return {
      pItemSpecForm: {
        ...pItemSpecForm.static,
        forms: pItemSpecForm.forms(),
        buttons: pItemSpecForm.static.buttons,
      },

      pItemSpecList: {
        ...pItemSpecList.static,
        columns: pItemSpecList.columns(),
        event: {
          cellClick: (e) => {
            this.fetchAItemList(e.item);
          },
        },
      },
      aItemList: {
        ...aItemList.static,
        columns: aItemList.columns(),
      },
    };
  },
  methods: {
    async fetchPItemSpecList() {
      const { forms } = this.pItemSpecForm;
      const { $grid } = this.pItemSpecList;
      const formData = FormUtil.getData(forms);
      const parameter = {
        labNo: formData.labNo,
        prdDiv: formData.prdDiv,
        ifDtParam: formData.ifDt,
      };

      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/getSemiPItemListToModal', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchAItemList({ labNo, prdDiv }) {
      const { $grid } = this.aItemList;
      const parameter = { labNo, prdDiv };
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/getSemiAItemListToModal', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    getItems() {},

    onClickButton({ name }) {
      if (name === 'search') {
        this.pItemSpecForm.forms.validate().then(() => {
          this.fetchPItemSpecList();
        });
      }
      if (name === 'select') {
        this.selectItems();
        this.close();
      }
    },
    close() {
      this.$emit('close');
    },
    selectItems() {
      const items = this.aItemList.$grid.getCheckedRowItemsAll();
      this.$emit('select', items);
    },
  },
};
</script>

<style></style>
