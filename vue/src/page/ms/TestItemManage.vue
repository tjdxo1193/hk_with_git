<template>
  <FormWithHeader
    v-bind="{ ...testItemSearch }"
    :buttons="searchFormButtons"
    @button-click="searchFormButtonClick"
    @form-event="searchInputFormEvent"
  />

  <AUIGridWithHeader
    v-bind="testItemGrid"
    @grid-created="(proxy) => $setState('testItemGrid.$grid', proxy)"
  />

  <FormWithHeader
    v-bind="{ ...selectedDataInfo }"
    :buttons="selectedDataFormButtons"
    @button-click="selectedDataFormButtonClick"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/testItemManage';

export default {
  name: 'TestItemManagePage',
  components: {},
  props: {},
  data() {
    const { testItemSearch, testItemGrid, selectedDataInfo } = this.$copy(values);
    return {
      testItemSearch: {
        ...testItemSearch.static,
        forms: testItemSearch.forms(),
        isDisabledButton: true,
      },
      testItemGrid: {
        ...testItemGrid.static,
        columns: testItemGrid.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.gridCellClicked(e);
          },
        },
      },
      selectedDataInfo: {
        ...selectedDataInfo.static,
        forms: selectedDataInfo.forms(),
        isDisabledButton: true,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.resetSelectedDataInfo();
      this.fetchTestItemManage();
    },
    async fetchTestItemManage() {
      const { forms } = this.testItemSearch;
      const { $grid } = this.testItemGrid;

      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/testItemManage', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormButtonClick({ name }) {
      if (name == 'search') {
        this.doInit();
      }
    },
    searchInputFormEvent({ originEvent }) {
      if (originEvent.key == 'Enter') {
        this.doInit();
      }
    },
    gridCellClicked({ item }) {
      FormUtil.setData(this.selectedDataInfo.forms, item);
    },
    selectedDataFormButtonClick({ name }) {
      if (name == 'save') {
        this.checkTestItem();
      } else if (name == 'reset') {
        this.resetSelectedDataInfo();
      }
    },
    checkTestItem() {
      const { forms } = this.selectedDataInfo;
      forms
        .validate()
        .then(() => {
          this.putTestItem();
        })
        .catch(() => {});
    },
    putTestItem() {
      const parameter = this.getParameter();

      this.$eSign(() => this.$axios.post('/ms/testItemManage/putTestItem', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    getParameter() {
      const { forms } = this.selectedDataInfo;
      let parameter = FormUtil.getData(forms);
      parameter.aitmCd = FormUtil.getValue(forms, 'aitmCd');

      return parameter;
    },
    resetSelectedDataInfo() {
      this.selectedDataInfo.forms = values.selectedDataInfo.forms();
    },
  },
  computed: {
    searchFormButtons() {
      const { buttons, isDisabledButton } = this.testItemSearch;
      return buttons.map((button) => {
        return {
          ...button,
          type: isDisabledButton ? button.type : 'default',
        };
      });
    },
    selectedDataFormButtons() {
      const { buttons, isDisabledButton } = this.selectedDataInfo;
      return buttons.map((button) => {
        return {
          ...button,
          type: isDisabledButton ? button.type : 'default',
        };
      });
    },
  },
};
</script>

<style></style>
