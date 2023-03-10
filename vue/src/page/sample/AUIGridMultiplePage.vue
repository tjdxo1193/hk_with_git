<template>
  <AUIGridSearch
    v-bind="product"
    @button-click="onProductEvent"
    @grid-created="(proxy) => $setState('product.$grid', proxy)"
  >
    <template #form-slotItem="{ value, $$update }">
      <InputBase :modelValue="value" @update:modelValue="$$update" />
    </template>
  </AUIGridSearch>

  <Horizontal align-items="center" :spans="[7, 1, 12]">
    <AUIGridWithHeader v-bind="cart" @grid-created="(proxy) => $setState('cart.$grid', proxy)" />
    <ExchangePanel direction="vertical" @click="onExchangePanelEvent" />
    <AUIGridWithHeader v-bind="item" @grid-created="(proxy) => $setState('item.$grid', proxy)" />
  </Horizontal>
</template>

<script>
import { FormUtil, GridUtil } from '@/util';

import values from './values/auigridMultiple';

export default {
  name: 'AUIGridMultipleSamplePage',
  mounted() {
    this.getProducts();
  },
  data() {
    const { product, carts, item } = this.$copy(values);
    return {
      product: {
        ...product.static,
        forms: product.forms(),
        columns: product.columns(),
        event: {
          cellDoubleClick: (e) => this.getCartsByProductId(e.item.id),
        },
      },
      cart: {
        ...carts.static,
        columns: carts.columns(),
        event: {
          cellDoubleClick: (e) => this.getCartById(e.item.id),
        },
      },
      item: {
        ...item.static,
        columns: item.columns(),
      },
    };
  },
  methods: {
    async getProducts() {
      const { forms, $grid } = this.product;
      const parameter = FormUtil.getData(forms);

      await $grid
        ._useLoader(() => this.$axios.get('/fake/products', parameter))
        .then(({ data }) =>
          data.map((row) => ({
            ...row,
            rate: row.rating.rate,
            count: row.rating.count,
          })),
        );
    },
    async getCartsByProductId(id) {
      const { $grid } = this.cart;

      await $grid._useLoader(() => this.$axios.get(`/fake/products/${id}/carts`, id));
    },
    async getCartById(id) {
      const { $grid } = this.item;
      await $grid._useLoader(() => this.$axios.get(`/fake/carts/${id}`, id));
    },

    initProdudct() {
      const forms = values.product.forms();
      this.$setState('product', { forms });
      this.$setState('product', { data: [] });
    },
    initCart() {
      this.$setState('cart.data', []);
    },
    initItem() {
      this.$setState('item.data', []);
    },

    async onProductEvent({ name }) {
      switch (name) {
        case 'init':
          this.initProdudct();
          this.initCart();
          this.initItem();
          break;

        case 'select':
          FormUtil.enableButtons(this.product.buttons, ['search', 'clear']);
          this.getProducts();
          break;

        case 'save':
          await this.product.forms.validate();
          break;
      }

      if (name === 'search' || name === 'clear') {
        FormUtil.disableButtons(this.product.buttons, ['search', 'clear']);
      }
    },
    onExchangePanelEvent({ name }) {
      switch (name) {
        case 'first':
          GridUtil.moveToGrid(this.cart.$grid, this.item.$grid);
          break;
        case 'second':
          GridUtil.moveToGridByPid(this.item.$grid._pid, this.cart.$grid._pid);
          break;
      }
    },
  },
};
</script>

<style></style>
