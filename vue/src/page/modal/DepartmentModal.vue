<template>
  <ModalBase v-bind="$props" @close="close">
    <template #action>
      <ActionBar :buttons="list.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <AUIGrid v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import values from './values/departmentModal';

export default {
  name: 'DepartmentModal',
  emits: ['close', 'select'],
  props: {
    show: Boolean,
    readOnly: {
      type: Boolean,
      default: true,
    },
  },
  mounted() {
    this.getDepartmentList();
  },
  watch: {
    show(newValue) {
      if (newValue) {
        this.getDepartmentList();
      }
    },
  },
  data() {
    const { list } = this.$copy(values);
    return {
      list: {
        ...list.static,
        columns: list.columns(),
        buttons: list.static.buttons,
        event: {
          cellDoubleClick: (e) => this.judgmentModeInGrid(e),
        },
      },
    };
  },
  methods: {
    async getDepartmentList() {
      const { $grid } = this.list;

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/departmentManage', ''))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getDepartmentList();
      }
    },
    judgmentModeInGrid(e) {
      const isReadOnlyMode = this.$props.readOnly;
      isReadOnlyMode ? this.emitSelectItemWhenReadOnlyMode(e) : this.emitSelectItemWhenEditMode(e);
    },
    emitSelectItemWhenReadOnlyMode({ item }) {
      this.$emit('select', item);
      this.close();
    },
    emitSelectItemWhenEditMode({ item }) {
      const isNotSelectableItem = item._$depth === 3;
      if (isNotSelectableItem) {
        return this.$error(this.$message.validate.lowestLevel);
      }
      this.$emit('select', item);
      this.close();
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
