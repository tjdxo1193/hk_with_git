<template>
  <AUIGridSearch
    v-bind="notice"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('notice.$grid', proxy)"
  />

  <FormWithHeader v-bind="{ ...noticeInfo }" />
</template>

<script>
import { FormUtil, ObjectUtil } from '@/util';

import values from './values/Notice';

export default {
  name: 'NoticePage',
  mounted() {
    this.getNoticeList();
  },
  data() {
    const { notice, noticeInfo } = this.$copy(values);
    return {
      notice: {
        ...notice.static,
        forms: notice.forms(),
        columns: notice.columns(),
        event: {
          cellDoubleClick: (e) => {
            e.item.popupPeriod = [e.item.popTrmStr, e.item.popTrmEnd];
            this.getNoticeInfoByNoticeId(e);
          },
        },
      },
      noticeInfo: {
        ...noticeInfo.static,
        forms: noticeInfo.forms(),
      },
    };
  },
  methods: {
    setState(keyChain, value) {
      ObjectUtil.setObject(this.$data, keyChain, value);
    },
    async getNoticeList() {
      const { $grid, forms } = this.notice;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/notice', parameter))
        .then(({ data }) => data);

      this.notice.$grid.setGridData(data);
    },
    getNoticeInfoByNoticeId(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.noticeInfo.forms, selectedItem);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getNoticeList();
      }
    },
  },
};
</script>
