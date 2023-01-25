<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridButtons"
    @form-event="searchGridEvent"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/loginHistory';

export default {
  name: 'LoginHistory',
  data() {
    const { searchGridWithForm } = this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
      },
    };
  },
  mounted() {
    this.fetchLoginHistory();
  },
  methods: {
    init() {},
    async fetchLoginHistory() {
      const { $grid } = this.searchGridWithForm;
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const data = await $grid._useLoader(() =>
        this.$axios
          .get('/sy/loginHistory', param)
          .then(({ data }) => data)
          .catch(() => this.$error(this.message.error.fetchData)),
      );
      $grid.setGridData(data);
    },
    searchGridEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.fetchLoginHistory();
      }
    },
    onClickSearchGridButtons({ name }) {
      if (name === 'search') {
        this.fetchLoginHistory();
      }
    },
  },
};
</script>

<style></style>
