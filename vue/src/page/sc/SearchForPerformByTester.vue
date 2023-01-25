<template>
  <AUIGridSearch v-bind="searchForPerformByTesterGrid" />
  <AUIGridWithHeader title="Test History" v-bind="testHistoryGrid" />
</template>

<script>
import values from './values/searchForPerformByTester';

export default {
  name: 'searchForPerformByTester',
  mounted() {},
  data() {
    const { searchForPerformByTesterGrid, testHistoryGrid } = this.$copy(values);
    return {
      searchForPerformByTesterGrid: {
        ...searchForPerformByTesterGrid.static,
        forms: searchForPerformByTesterGrid.forms(),
        columns: searchForPerformByTesterGrid.columns(),
      },
      testHistoryGrid: {
        ...testHistoryGrid.static,
        columns: testHistoryGrid.columns(),
      },
    };
  },
};
</script>
