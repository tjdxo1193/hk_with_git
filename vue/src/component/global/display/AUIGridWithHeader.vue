<template>
  <Card :title="title" :margin="margin">
    <template #action>
      <div class="auigrid-container__header-legend">
        <LegendBar :legends="legends" />
      </div>
      <div>
        <ButtonBase
          v-for="(button, index) in buttons"
          :key="button.name ?? index"
          v-bind="button"
          @click="$emit('button-click', { name: button.name })"
        >
          {{ button.label }}
        </ButtonBase>
      </div>
    </template>

    <AUIGrid
      :columns="columns"
      :props="props"
      :defaultData="defaultData"
      :event="event"
      :width="width"
      :height="height"
      @grid-created="$emit('grid-created', $event)"
      @grid-button-click="$emit('grid-button-click', $event)"
    />
  </Card>
</template>

<script>
import AUIGrid from './AUIGrid';

export default {
  name: 'AUIGridWithHeader',
  emits: ['grid-created', 'button-click', 'grid-button-click'],
  components: {
    AUIGrid,
  },
  inheritAttrs: false,
  props: {
    title: String,
    margin: String,
    buttons: Array,
    legends: Array,
    columns: Array,
    props: Object,
    defaultData: Array,
    event: Object,
    width: String,
    height: String,
  },
};
</script>

<style>
.auigrid-container__header-legend {
  display: flex;
  align-items: center;
}
</style>
