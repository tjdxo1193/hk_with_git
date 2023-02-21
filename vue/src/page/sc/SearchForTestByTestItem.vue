<template>
  <AUIGridSearch v-bind="searchForTestByTestItemGrid" />
  <ActionBar class="avgTime">
    <h6>평균소요시간</h6>
    <InputBase name="data" disabled="true" />
    <br />
  </ActionBar>
  <ActionBar>
    <ButtonBase label="Excel" />
    <ButtonBase label="조회" />
  </ActionBar>
</template>

<script>
import ActionBar from '@/component/global/general/ActionBar.vue';
import { ObjectUtil } from '@/util';

import values from './values/searchForTestByTestItem';

export default {
  components: { ActionBar },
  name: 'searchForPerformByTester',
  mounted() {},
  data() {
    const { searchForTestByTestItemGrid } = this.$copy(values);
    return {
      searchForTestByTestItemGrid: {
        ...searchForTestByTestItemGrid.static,
        forms: searchForTestByTestItemGrid.forms(),
        columns: searchForTestByTestItemGrid.columns(),
      },
    };
  },

  methods: {
    setState(keyChain, value) {
      ObjectUtil.setObject(this.$data, keyChain, value);
    },
  },
};
</script>

<style>
.avgTime {
  width: 20%;
  float: right;
}
</style>
