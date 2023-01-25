<template>
  <AUIGridSearch
    v-bind="plant"
    @grid-created="(proxy) => $setState('plant.$grid', proxy)"
    @button-click="onButtonClickOnGrid"
    @enter="fetchPlants"
  />
  <FormWithHeader v-bind="input" @button-click="onButtonClickOnInput" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/plantManage';

export default {
  name: 'PlantManagePage',
  mounted() {
    this.fetchPlants();
  },
  data() {
    const { plant, input } = this.$copy(values);

    return {
      plant: {
        ...plant.static,
        forms: plant.forms(),
        columns: plant.columns(),
        event: {
          cellDoubleClick: (e) => this.selectRow(e),
        },
      },
      input: {
        ...input.static,
        forms: input.forms(),
      },
    };
  },
  methods: {
    async fetchPlants() {
      this.initInput();
      const param = FormUtil.getData(this.plant.forms);
      const { data } = await this.plant.$grid
        ._useLoader(() => this.$axios.get('sy/plantManage', param))
        .catch(() => this.$error(this.$message.error.fetchData));
      this.plant.$grid.setGridData(data);
    },

    onButtonClickOnGrid({ name }) {
      if (name === 'search') {
        this.fetchPlants();
      }
    },
    selectRow(e) {
      FormUtil.setData(this.input.forms, e.item);
    },

    onButtonClickOnInput({ name }) {
      if (name === 'init') {
        this.initInput();
        return;
      }
      if (name === 'save') {
        this.save();
        return;
      }
    },
    initInput() {
      this.input.forms = values.input.forms();
    },
    async save() {
      const param = FormUtil.getData(this.input.forms);
      this.isUpdate() ? await this.update(param) : await this.create(param);
      this.fetchPlants();
    },
    async create(param) {
      await this.$eSign(() => this.$axios.post('sy/plantManage', param)).catch(() =>
        this.$error(this.$message.error.createData),
      );
    },
    async update(param) {
      await this.$eSignWithReason(() => this.$axios.put('sy/plantManage', param)).catch(() =>
        this.$error(this.$message.error.updateData),
      );
    },
    isUpdate() {
      return FormUtil.existsValue(this.input.forms, 'bplcCode');
    },
  },
};
</script>

<style></style>
