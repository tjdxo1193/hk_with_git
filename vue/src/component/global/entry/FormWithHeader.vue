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

    <FormBase
      :labelWidth="labelWidth"
      :forms="forms"
      :countPerRow="countPerRow"
      @form-event="$emit('form-event', $event)"
      @enter="$emit('enter', $event)"
    >
      <template v-for="slotKey in slotKeys" :key="slotKey" #[slotKey]="slotProps">
        <slot :name="slotKey" v-bind="slotProps"></slot>
      </template>
    </FormBase>
  </Card>
</template>

<script>
import FormBase from './FormBase';

export default {
  name: 'Forms',
  emits: ['form-event', 'button-click', 'enter'],
  components: {
    FormBase,
  },
  data() {
    return {
      slotKeys: Object.keys(this.$slots),
    };
  },
  props: {
    title: String,
    margin: String,
    buttons: Array,
    labelWidth: String,
    forms: Array,
    countPerRow: Number,
  },
};
</script>

<style></style>
