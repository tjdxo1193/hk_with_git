<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="50" @close="close">
    <AUIGridSearch
      v-bind="list"
      @grid-created="(proxy) => $setState('list.$grid', proxy)"
      @button-click="onClickButton"
    />

    <Space :gap="10" />

    <FormWithHeader v-bind="detail" @button-click="onClickButton">
      <template #form-dropzone>
        <Dropzone @created="$setState('detail.dropzone', $event)" :option="{ readonly }" />
      </template>
    </FormWithHeader>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

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
    initData: {
      type: Object,
      default: {},
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    height: {
      type: String,
      default: '1000px',
    },
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        columns: list.columns(),
        forms: list.forms(),
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        dropzone: null,
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        FormUtil.setData(this.list.forms, {
          ctId: this.$props.initData.ctId,
          matnr: this.$props.initData.labNo,
        });
      }
    },
  },
  methods: {
    async getFileList() {
      const param = FormUtil.getData(this.list.forms);
      const { $grid } = this.list;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemManage/getFileList', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
      this.detail.dropzone.clear();
      this.detail.dropzone.addFiles(data);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getFileList();
      }
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
};
</script>
