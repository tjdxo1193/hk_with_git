<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="100" @close="close">
    <AUIGrid v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import values from './values/toDoListModal';

export default {
  name: 'TodoListModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '오늘의 할일',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.getToDoList();
  },
  data() {
    const { list } = this.$copy(values);
    return {
      list: {
        ...list.static,
        columns: list.columns(),
      },
    };
  },
  methods: {
    async getToDoList() {
      // const { $grid } = this.list;
      // const data = await $grid
      //   ._useLoader(() => this.$axios.get(`/main/getToDoList`))
      //   .then(({ data }) => data);
      // $grid.setGridData(data);
    },
    close() {
      this.list.$grid.clearGridData();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
