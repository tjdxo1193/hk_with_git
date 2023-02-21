<template>
  <AUIGridSearch
    v-bind="calListWithSearch"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('calListWithSearch.$grid', proxy)"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/scheduleManage';
export default {
  name: 'ScheduleManage',
  data() {
    const { calListWithSearch } = this.$copy(values);

    return {
      calListWithSearch: {
        ...calListWithSearch.static,
        forms: calListWithSearch.forms(),
        columns: calListWithSearch.columns(),
      },
    };
  },
  methods: {
    async fetchScheduleList() {
      const { $grid, forms } = this.calListWithSearch;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('sy/scheduleManage/list', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name == 'search') {
        this.fetchScheduleList();
      }
    },
  },
};
</script>

<style></style>
