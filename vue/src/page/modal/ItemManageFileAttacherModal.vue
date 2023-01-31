<template>
  <ModalBase :title="title" :show="show" width="700px" :top="50" @close="close">
    <AUIGridSearch v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)"  
      @button-click="onClickCommonInfoFormButtons"/>

    <Space :gap="10" />

    <FormWithHeader v-bind="detail" @button-click="onClickCommonInfoFormButtons">
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
    ctrptNo() {
      this.getFileList();
    },
    show() {
      if (this.$props.show) {
        FormUtil.setData(this.list.forms, {pitmCd: this.$props.initData.pitmCd});
        this.getFileList();
      }
    },
  },
  methods: {
    existsCtrptNo() {
      return this.$props.initData.ctrptNo > 0;
    },
    async getFileList() {
      const ctrptNo = this.$props.initData.ctrptNo;
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
};
</script>
