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
import values from './values/itemManageFileAttacherModal';

export default {
  name: 'ItemManageFileAttacherModal',
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
    ctrptNo: {
      type: Number,
      default: 0,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    if (this.existsCtrptNo()) {
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
    ctrptNo() {
      this.getFileList();
    },
    show() {
      if (this.$props.show) {
        this.getFileList();
      }
    },
  },
  methods: {
    existsCtrptNo() {
      return this.$props.ctrptNo > 0;
    },
    async getFileList() {
      const ctrptNo = this.$props.ctrptNo;
      const { $grid } = this.list;

      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ms/itemManage/getFileList/${ctrptNo}`))
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

      const processedRemovedFileIds = removedFileIds.map((removeFile) => {
        let removeFileKey = {
          ctId: removeFile.fileIdx || removeFile.ctId,
          ctSeq: removeFile.fileSrlno || removeFile.ctSeq,
        };

        return removeFileKey;
      });

      this.$emit('save', { addedFiles, removedFileIds: processedRemovedFileIds });
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
