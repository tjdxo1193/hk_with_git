<template>
  <Horizontal align-item="start" :spans="[5, 5]">
    <Vertical>
      <FormWithHeader
        v-bind="{ ...testItemSearch }"
        :buttons="testItemSearchButtons"
        @button-click="testItemSearchButtonClick"
        @form-event="testItemSearchFormEvent"
      />
      <AUIGridWithHeader
        v-bind="testItemGrid"
        :height="'570px'"
        @grid-created="(proxy) => $setState('testItemGrid.$grid', proxy)"
      />
    </Vertical>
    <Vertical>
      <FormWithHeader
        v-bind="{ ...testItemPerSearch }"
        :buttons="testItemPerSearchButtons"
        @button-click="testItemPerSearchButtonClick"
        @form-event="testItemPerSearchFormEvent"
      />
      <AUIGridWithHeader
        v-bind="testItemPerGrid"
        :height="'360px'"
        @grid-created="(proxy) => $setState('testItemPerGrid.$grid', proxy)"
      />
      <FormWithHeader
        v-bind="{ ...testItemPerInfo }"
        :buttons="testItemPerButtons"
        @button-click="testItemPerButtonClick"
        @form-event="testItemPerFormEvent"
      />
    </Vertical>
  </Horizontal>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/testItemPerManage';

export default {
  name: 'TestItemPerManage',
  components: {},
  data() {
    const { testItemSearch, testItemGrid, testItemPerSearch, testItemPerGrid, testItemPerInfo } =
      values;
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
            this.testItemGridCellClicked(e);
          },
        },
      },
      testItemPerSearch: {
        ...testItemPerSearch.static,
        forms: testItemPerSearch.forms(),
        isDisabledButton: true,
      },
      testItemPerGrid: {
        ...testItemPerGrid.static,
        columns: testItemPerGrid.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.testItemPerGridCellClicked(e);
          },
        },
      },
      testItemPerInfo: {
        ...testItemPerInfo.static,
        forms: testItemPerInfo.forms(),
        isDisabledButton: true,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.resetTestItemPerSearch();
      this.resetTestItemPerGrid();
      this.resetTestItemPerInfo();
      this.fetchTestItemManage();
    },
    resetTestItemPerSearch() {
      this.testItemPerSearch.forms = values.testItemPerSearch.forms();
    },
    resetTestItemPerGrid() {
      this.testItemPerGrid.$grid.clearGridData();
    },
    resetTestItemPerInfo() {
      this.testItemPerInfo.forms = values.testItemPerInfo.forms();
    },
    async fetchTestItemManage() {
      const { forms } = this.testItemSearch;
      const { $grid } = this.testItemGrid;

      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/testItemPerManage', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    async fetchTestItemPerManage() {
      const { forms } = this.testItemPerSearch;
      const { $grid } = this.testItemPerGrid;

      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/testItemPerManage/getPerList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.checkVriaNo(data);
    },
    testItemSearchButtonClick({ name }) {
      if (name == 'search') {
        this.doInit();
      }
    },
    testItemSearchFormEvent({ originEvent }) {
      if (originEvent.key == 'Enter') {
        this.resetTestItemPerInfo();
        this.fetchTestItemManage();
      }
    },
    testItemPerSearchButtonClick({ name }) {
      if (name == 'search') {
        this.fetchTestItemPerManage();
      }
    },
    testItemPerSearchFormEvent({ originEvent }) {
      if (originEvent.key == 'Enter') {
        this.resetTestItemPerInfo();
        this.fetchTestItemPerManage();
      }
    },
    testItemGridCellClicked({ item }) {
      item.useYn = '';
      FormUtil.setData(this.testItemPerSearch.forms, item);

      const { forms } = this.testItemPerInfo;
      FormUtil.setData(this.testItemPerInfo.forms, item);
      const testItemPerInfoAmitmCd = forms.find((e) => e.name == 'amitmCd');
      const choosedTestItemAitmCd = FormUtil.getValue(forms, 'aitmCd');
      testItemPerInfoAmitmCd.value = choosedTestItemAitmCd;

      FormUtil.setData(forms, { useYn: 'Y' });

      this.testItemPerEnable();
      this.fetchTestItemPerManage();
    },
    testItemPerGridCellClicked({ item }) {
      this.resetTestItemPerInfo();

      const { forms } = this.testItemPerInfo;
      FormUtil.setData(forms, item);

      if (FormUtil.existsValue(forms, 'vriaNo')) {
        this.testItemPerDisabled();
      }
    },
    checkVriaNo(data) {
      const totalVriaNoList = [
        { value: '01', label: '01' },
        { value: '02', label: '02' },
        { value: '03', label: '03' },
        { value: '04', label: '04' },
        { value: '05', label: '05' },
        { value: '06', label: '06' },
        { value: '07', label: '07' },
        { value: '08', label: '08' },
        { value: '09', label: '09' },
        { value: '10', label: '10' },
        { value: '11', label: '11' },
        { value: '12', label: '12' },
      ];
      const vriaNoList = data.map((e) => e.vriaNo);
      const realVriaNoList = totalVriaNoList.filter(
        (totalVriaNo) => !vriaNoList.includes(totalVriaNo.value),
      );
      const { forms } = this.testItemPerInfo;

      FormUtil.setProp(forms, 'vriaNo', { elements: realVriaNoList });
    },
    testItemPerDisabled() {
      const { forms } = this.testItemPerInfo;
      FormUtil.disable(forms, 'vriaNo');
    },
    testItemPerEnable() {
      const { forms } = this.testItemPerInfo;
      FormUtil.enable(forms, 'vriaNo');
    },
    testItemPerButtonClick({ name }) {
      if (name == 'save') {
        this.checkTestItemPer();
      } else if (name == 'reset') {
        this.resetTestItemPerInfo();
      } else if (name == 'elnSend') {
        this.elnSendTestItemMethodInfo();
      }
    },
    testItemPerFormEvent({ type, item }) {
      if (type == 'change' && item.name == 'vriaNo') {
        this.setAmitmCd(item);
      }
    },
    setAmitmCd({ value }) {
      const { forms } = this.testItemPerInfo;
      const aitmCd = FormUtil.getValue(forms, 'aitmCd');
      if (aitmCd) {
        FormUtil.setData(forms, { amitmCd: aitmCd + value });
      }
    },
    checkTestItemPer() {
      const { forms } = this.testItemPerInfo;
      const aitmCdYn = FormUtil.existsValue(forms, 'aitmCd');

      forms
        .validate()
        .then(() => {
          if (aitmCdYn) {
            this.putTestItemPer();
          } else {
            this.$warn(this.$message.warn.unSelectedData);
          }
        })
        .catch(() => {});
    },
    putTestItemPer() {
      const parameter = this.getParameter();

      this.$eSign(() => this.$axios.post('/ms/testItemPerManage/putTestItemPer', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    getParameter() {
      const { forms } = this.testItemPerInfo;
      let parameter = FormUtil.getData(forms);
      parameter.vriaNo = FormUtil.getValue(forms, 'vriaNo');
      parameter.amitmCd = FormUtil.getValue(forms, 'amitmCd');

      return parameter;
    },
    elnSendTestItemMethodInfo() {
      const parameter = FormUtil.getData(this.testItemPerInfo.forms);
      this.$eSign(() => this.$axios.post('/ms/testItemPerManage/elnSendTestItemMethod', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
  },
  computed: {
    testItemSearchButtons() {
      const { buttons, isDisabledButton } = this.testItemSearch;
      return buttons.map((button) => {
        return {
          ...button,
          type: isDisabledButton ? button.type : 'default',
        };
      });
    },
    testItemPerSearchButtons() {
      const { buttons, isDisabledButton } = this.testItemPerSearch;
      return buttons.map((button) => {
        return {
          ...button,
          type: isDisabledButton ? button.type : 'default',
        };
      });
    },
    testItemPerButtons() {
      const { buttons, isDisabledButton } = this.testItemPerInfo;
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
