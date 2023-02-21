<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="inquireForm" @form-event="enterEvent" @button-click="onClickButton" />

    <AUIGridSearch
      v-bind="inquireResultForm"
      @grid-created="(proxy) => $setState('inquireResultForm.$grid', proxy)"
    />

    <ActionBar :buttons="buttonGroups.buttons" />
  </ModalBase>
</template>

<script>
import values from './values/stageModal';

export default {
  name: 'StageModal',
  emits: ['close'],
  props: {
    show: Boolean,
  },
  data() {
    const { inquireForm, inquireResultForm, buttonGroups } = this.$copy(values);
    return {
      inquireForm: {
        ...inquireForm.static,
        forms: inquireForm.forms(),
      },
      inquireResultForm: {
        ...inquireResultForm.static,
        forms: inquireResultForm.forms(),
        columns: inquireResultForm.columns(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
    };
  },
  methods: {
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
