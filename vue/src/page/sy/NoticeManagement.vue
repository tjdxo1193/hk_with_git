<template>
  <AUIGridSearch
    v-bind="noticeManagement"
    @button-click="onEventBySearchButton"
    @grid-created="(proxy) => setState('noticeManagement.$grid', proxy)"
  />

  <FormWithHeader v-bind="{ ...noticeManagementInfo }" @button-click="onEventsByDetailButton" />
</template>

<script>
import { FormUtil, ObjectUtil } from '@/util';

import values from './values/NoticeManagement';

export default {
  name: 'NoticeManagementPage',
  mounted() {
    this.fetchNoticeManagement();
  },
  data() {
    const { noticeManagement, noticeManagementInfo } = this.$copy(values);
    return {
      noticeManagement: {
        ...noticeManagement.static,
        forms: noticeManagement.forms(),
        columns: noticeManagement.columns(),
        event: {
          cellDoubleClick: (e) => {
            e.item.popupPeriod = [e.item.popTrmStr, e.item.popTrmEnd];
            this.getNoticeManagementInfoByNoticeId(e);
          },
        },
      },
      noticeManagementInfo: {
        ...noticeManagementInfo.static,
        forms: noticeManagementInfo.forms(),
      },
    };
  },
  methods: {
    setState(keyChain, value) {
      ObjectUtil.setObject(this.$data, keyChain, value);
    },
    async fetchNoticeManagement() {
      const { $grid, forms } = this.noticeManagement;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/noticeManagement', parameter))
        .then(({ data }) => data);

      this.noticeManagement.$grid.setGridData(data);
    },
    getNoticeManagementInfoByNoticeId(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.noticeManagementInfo.forms, selectedItem);
    },
    onEventBySearchButton({ name }) {
      if (name === 'search') {
        this.fetchNoticeManagement();
      }
    },
    onEventsByDetailButton({ name }) {
      if (name === 'init') {
        this.initDetail();
      }
      if (name === 'save') {
        this.noticeManagementInfo.forms
          .validate()
          .then(() => {
            `유효성 검증기 성공했을 때 실행됩니다. 저장 등의 프로세스를 진행합니다.`;
            this.isUpdate() ? this.updateNotice() : this.createNotice();
          })
          .catch(() => {
            `유효성 검증이 실패했을 때 실행됩니다. 관련 에외 처리를 할 수 있습니다.`;
          });
      }
    },
    setDetailData(data) {
      this.initDetail();
      FormUtil.setData(this.noticeManagementInfo.forms, data);
    },
    initDetail() {
      this.noticeManagementInfo.forms = values.noticeManagementInfo.forms();
    },
    isUpdate() {
      return FormUtil.existsValue(this.noticeManagementInfo.forms, 'ntbIdx');
    },
    createNotice() {
      const param = FormUtil.getData(this.noticeManagementInfo.forms);
      this.$axios
        .postByForm('/sy/noticeManagement', param)
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchNoticeManagement();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    updateNotice() {
      const param = FormUtil.getData(this.noticeManagementInfo.forms);

      this.$axios
        .putByForm('/sy/noticeManagement', param)
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchNoticeManagement();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
  },
};
</script>
