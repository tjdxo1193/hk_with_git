<template>
  <ModalBase :title="title" :show="show" width="700px" :top="50" @close="close">
    <AUIGrid v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />

    <Space :gap="10" />

    <FormBase v-bind="detail">
      <template #form-dropzone>
        <Dropzone @created="$setState('detail.dropzone', $event)" :option="{ readonly }" />
      </template>
    </FormBase>

    <template #footer>
      <ActionBar :buttons="computedButtons" @button-click="onClickButton"></ActionBar>
    </template>
  </ModalBase>
</template>

<script>
import values from './values/fileAttacherModal';

export default {
  name: 'FileAttacherModal',
  emits: ['close', 'save'],
  props: {
    title: {
      type: String,
      default: '파일등록',
    },
    show: {
      type: Boolean,
      default: false,
    },
    fileIdx: {
      type: Number,
      default: 0,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    if (this.existsFileIdx()) {
      this.getFileList();
    }
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        columns: list.columns(),
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        dropzone: null,
      },
    };
  },
  watch: {
    fileIdx() {
      if (this.existsFileIdx()) {
        this.getFileList();
      }
    },
    show() {
      if (this.$props.show && this.existsFileIdx()) {
        this.getFileList();
      }
    },
  },
  methods: {
    existsFileIdx() {
      return this.$props.fileIdx > 0;
    },
    async getFileList() {
      const fileIdx = this.$props.fileIdx;
      const { $grid } = this.list;

      const data = await $grid
        ._useLoader(() => this.$axios.get(`/files/${fileIdx}`))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.detail.dropzone.clear();
      this.detail.dropzone.addFiles(data);
    },
    onClickButton({ name }) {
      if (name === 'save') {
        this.emitSave();
      }
    },
    emitSave() {
      const { dropzone } = this.detail;
      const addedFiles = dropzone.getAddedFiles();
      const removedFileIds = dropzone.getRemovedIds();
      this.$emit('save', { addedFiles, removedFileIds });
    },
    close() {
      this.$emit('close');
      this.list.$grid.clearGridData();
      this.detail.dropzone.clear();
    },
  },
  computed: {
    computedButtons() {
      const { buttons } = values.list.static;
      return (this.list.buttons = this.$props.readonly
        ? buttons.map((button) => ({
            ...button,
            disabled: true,
          }))
        : buttons);
    },
  },
};
</script>
