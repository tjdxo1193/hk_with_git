<template>
  <Card :title="title" :margin="margin">
    <template #action>
      <ButtonBase
        v-for="(button, index) in buttons"
        :key="button.name ?? index"
        v-bind="button"
        @click="$emit('button-click', { name: button.name })"
      >
        {{ button.label }}
      </ButtonBase>
    </template>

    <section class="auigrid-search__form">
      <FormBase
        :title="title"
        :labelWidth="labelWidth"
        :countPerRow="countPerRow"
        :buttons="buttons"
        :forms="forms"
        @form-event="$emit('form-event', $event)"
        @enter="$emit('enter', $event)"
      >
        <template v-for="item in forms" v-slot:[`form-${item.name}`]="slotProps">
          <slot v-if="item?._slot" v-bind="slotProps" :name="`form-${item.name}`"></slot>
        </template>
      </FormBase>
    </section>

    <section class="auigrid-search__content">
      <div class="auigrid-search__content-header">
        <div class="auigrid-search__content-legend">
          <template v-if="legends">
            <LegendBar :legends="legends" />
          </template>
        </div>
      </div>
      <div>
        <AUIGrid
          :columns="columns"
          :props="props"
          :defaultData="defaultData"
          :event="event"
          :width="width"
          :height="height"
          @grid-created="$emit('grid-created', $event)"
          @grid-button-click="$emit('grid-button-click', $event)"
        ></AUIGrid>
      </div>
    </section>
  </Card>
</template>

<script>
export default {
  name: 'AUIGridSearch',
  emits: ['form-event', 'button-click', 'grid-created', 'enter', 'grid-button-click', 'click'],
  inheritAttrs: false,
  props: {
    // card
    title: String,
    margin: String,

    // form
    labelWidth: String,
    countPerRow: Number,
    buttons: Array,
    forms: Array,

    // grid
    columns: Array,
    props: Object,
    event: Object,
    legends: Array,
    width: String,
    height: String,
    defaultData: Array,
  },
};
</script>

<style lang="scss" scope>
.auigrid-search {
  &__content {
    &-header {
      display: flex;
      justify-content: end;
    }

    &-legend {
      padding: 8px;
    }
  }
}
</style>
