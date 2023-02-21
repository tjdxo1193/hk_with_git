<template>
  <ModalBase v-bind="$props" @close="onCancel">
    <template #default>
      <slot></slot>
    </template>

    <template #footer>
      <ButtonBase @click="onCancel" type="normal">{{ cancelText }}</ButtonBase>
      <ButtonBase @click="onOk">{{ okText }}</ButtonBase>
    </template>
  </ModalBase>
</template>

<script>
export default {
  name: 'ModalBoard',
  emits: ['ok', 'cancel', 'close'],
  props: {
    show: Boolean,
    plain: Boolean,
    title: String,
    width: String,
    height: String,
    top: Number,
    right: Number,
    bottom: Number,
    left: Number,
    okText: {
      type: String,
      default: '확인',
    },
    cancelText: {
      type: String,
      default: '취소',
    },
  },
  mounted() {
    window.document.addEventListener('keydown', this.onCloseListener);
  },
  beforeUnmount() {
    window.document.removeEventListener('keydown', this.onCloseListener);
  },
  methods: {
    onOk() {
      this.$emit('ok');
      this.close();
    },
    onCancel() {
      this.$emit('cancel');
      this.close();
    },
    close() {
      this.$emit('close');
    },
    isEsc(key) {
      return key === 'Escape';
    },
    isHide() {
      return !this.$props.show;
    },
    onCloseListener({ key }) {
      if (this.isHide()) {
        return;
      }

      if (this.isEsc(key)) {
        this.onCancel();
        return;
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
