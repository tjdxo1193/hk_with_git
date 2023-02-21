<template>
  <AUIGridSearch v-bind="workplaceSearchGrid" />

  <ActionBar>
    <ButtonBase label="항목조회" />
  </ActionBar>

  <AUIGridWithHeader v-bind="itemSearchGrid" />

  <ActionBar>
    <h5 id="notice">※Negative 포인트는 조회에서 제외됩니다.</h5>
    <ButtonBase label="포인트별기준조회" />
  </ActionBar>

  <AUIGridWithHeader v-bind="testItemByPointGrid" />

  <ActionBar>
    <ButtonBase label="결과조회" />
  </ActionBar>

  <AUIGridWithHeader v-bind="resultSearch" />
</template>

<script>
import values from './values/searchForResultByMonitorPeriod';

export default {
  name: 'SearchForResultByMonitorPeriod',
  mounted() {},
  data() {
    const { workplaceSearchGrid, itemSearchGrid, testItemByPointGrid, resultSearch } =
      this.$copy(values);
    return {
      workplaceSearchGrid: {
        ...workplaceSearchGrid.static,
        forms: workplaceSearchGrid.forms(),
        columns: workplaceSearchGrid.columns(),
      },
      itemSearchGrid: {
        ...itemSearchGrid.static,
        columns: itemSearchGrid.columns(),
      },
      testItemByPointGrid: {
        ...testItemByPointGrid.static,
        columns: testItemByPointGrid.columns(),
      },
      resultSearch: {
        ...resultSearch.static,
        columns: resultSearch.columns(),
      },
    };
  },
};
</script>

<style>
#notice {
  color: '#3f51b5';
  align-content: center;
}
</style>
