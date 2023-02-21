<template>
  <AUIGridSearch
    v-bind="product"
    @button-click="onClickButton"
    @form-event="onFormEvent"
    @grid-created="(proxy) => $setState('product.$grid', proxy)"
  />

  <ModalBoard
    title="Header"
    :show="modal.show"
    @ok="hideModal"
    @close="hideModal"
    @cancel="hideModal"
    width="60%"
    height="500px"
  >
    <FormBase :forms="modal.forms"></FormBase>
  </ModalBoard>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/auigrid';

export default {
  name: 'AUIGridSamplePage',
  mounted() {
    this.fetchProducts();
  },
  data() {
    const { product, productModal } = this.$copy(values);
    return {
      product: {
        ...product.static,
        forms: product.forms(),
        columns: product.columns(),
        event: {
          cellDoubleClick: this.showModal,
        },
      },
      modal: {
        show: false,
        forms: productModal.forms(),
      },
    };
  },
  methods: {
    async fetchProducts() {
      const { $grid, forms } = this.product;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/fake/products', parameter))
        .then(({ data }) =>
          data.map((row) => ({
            ...row,
            rate: row.rating.rate,
            count: row.rating.count,
          })),
        );

      this.product.$grid.setGridData(data);
    },
    showModal() {
      this.$setState('modal', { show: true });
    },
    hideModal() {
      this.$setState('modal', {
        show: false,
        forms: values.productModal.forms(),
      });
    },
    onClickButton({ name }) {
      if (name === 'init') {
        this.init();
      }
      if (name === 'map') {
        this.mapTest();
      }
      if (name === 'mapArray') {
        this.mapArrayTest();
      }
      if (name === 'getData') {
        this.getDataTest();
      }
      if (name === 'setData') {
        this.setDataTest();
      }
      if (name === 'findItem') {
        this.findItemTest();
      }
    },
    onFormEvent() {},
    save() {
      this.fetchProducts();
    },
    init() {
      this.product.forms = values.product.forms();
      this.$axios.post('/auth/test');
    },
    mapTest() {
      this.product.forms = FormUtil.map(this.product.forms, { name: 'dept', is: 'InputBase' });
    },
    mapArrayTest() {
      this.product.forms = FormUtil.map(this.product.forms, [
        { name: 'dept', is: 'SelectBase' },
        { name: 'type', value: 3 },
      ]);
    },
    getDataTest() {},
    setDataTest() {
      FormUtil.setData(this.product.forms, {
        div: 3,
        category: null,
        type: 3,
        cr: ['three', 'two'],
        check1: true,
        11: true,
        22: 123123,
        33: null,
      });
    },
    findItemTest() {},
  },
};
</script>
