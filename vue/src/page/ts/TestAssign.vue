<template>
  <FormWithHeader
    v-bind="searchForm"
    @form-event="enterEvent"
    @button-click="onClickButton"
  ></FormWithHeader>

  <AUIGridWithHeader v-bind="list" @grid-created="(proxy) => $setState('list.$grid', proxy)" />

  <Card title="항목정보">
    <FormBase v-bind="itemInfoSearchForm"></FormBase>

    <AUIGrid v-bind="itemInfo" @grid-created="(proxy) => $setState('itemInfo.$grid', proxy)" />

    <AUIGrid
      v-bind="itemDetailInfo"
      @grid-created="(proxy) => $setState('itemDetailInfo.$grid', proxy)"
    />
  </Card>

  <ActionBar :buttons="formButtons.buttons" @button-click="onClickButton"></ActionBar>

  <FileAttacherModal
    title="파일등록"
    :show="fileAttacherModal.show"
    @close="hideModal('fileAttacherModal')"
    width="60%"
    height="450px"
  >
  </FileAttacherModal>
</template>

<script>
import { FileAttacherModal } from '@/page/modal';

import values from './values/testAssign';

export default {
  name: 'TestAssign',
  components: {
    FileAttacherModal,
  },
  mounted() {},
  data() {
    const { searchForm, list, itemInfoSearchForm, itemInfo, itemDetailInfo, formButtons } =
      this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },
      list: {
        ...list.static,
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getAssign(e),
        },
      },
      itemInfoSearchForm: {
        ...itemInfoSearchForm.static,
        forms: itemInfoSearchForm.forms(),
      },
      itemInfo: {
        ...itemInfo.static,
        columns: itemInfo.columns(),
      },
      itemDetailInfo: {
        ...itemDetailInfo.static,
        columns: itemDetailInfo.columns(),
      },
      formButtons: {
        buttons: formButtons.buttons,
      },
      fileAttacherModal: {
        show: false,
      },
    };
  },
  methods: {
    async getAssignList() {},
    // getAssign(event) {
    //   const selectedItem = event.item;
    // },
    enterEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getAssignList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getAssignList();
      }
      if (name === 'addFile') {
        this.showModal();
      }
      if (name === 'assignTester') {
        alert('시험자배정');
      }
      if (name === 'init') {
        this.init();
      }
    },
    init() {
      this.itemInfo.$grid.clearGridData();
      this.itemDetailInfo.$grid.clearGridData();
    },
    activeButtons() {
      //버튼 활성화
    },
    inActiveButtons() {
      //버튼 비활성화
    },
    showModal() {
      this.$setState('fileAttacherModal', { show: true });
    },
    hideModal() {
      this.$setState('fileAttacherModal', { show: false });
    },
  },
};
</script>

<style></style>
