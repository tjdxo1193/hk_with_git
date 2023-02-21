<template>
  <Card>
    <TabBase v-bind="tabs">
      <template #tab-testMaterialInfo>
        <AUIGridSearch
          v-bind="testMaterialManage"
          @button-click="onClickButton"
          @grid-created="(proxy) => $setState('testMaterialManage.$grid', proxy)"
          @form-event="searchFormEvent"
        />
        <FormWithHeader
          v-bind="{ ...testMaterialManageInfo }"
          @button-click="onEventsByDetailButton"
          @form-event="inputFormEvent"
        />
      </template>

      <template #tab-testMaterialSe>
        <TreeCommonCodeManage title="시험재료 구분 목록" code="R0000000" />
      </template>
    </TabBase>
  </Card>
</template>

<script>
import TreeCommonCodeManage from '@/page/sy/TreeCommonCodeManage';
import { FormUtil } from '@/util';

import values from './values/testMaterialManage';

export default {
  name: 'testMaterialManage',
  components: {
    TreeCommonCodeManage,
  },
  mounted() {
    this.fetchTestMaterialManage();
  },
  data() {
    const { tabs, testMaterialManage, testMaterialManageInfo } = this.$copy(values);
    return {
      tabs,
      testMaterialManage: {
        ...testMaterialManage.static,
        forms: testMaterialManage.forms(),
        columns: testMaterialManage.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getTestMaterialManageInfoByTestMaterialManageId(e);
            this.enableButtons();
          },
        },
      },
      testMaterialManageInfo: {
        ...testMaterialManageInfo.static,
        forms: testMaterialManageInfo.forms(),
      },
    };
  },
  methods: {
    async fetchTestMaterialManage() {
      const { $grid, forms } = this.testMaterialManage;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/testMaterialManage', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    getTestMaterialManageInfoByTestMaterialManageId(event) {
      const selectedItem = event.item;
      this.setRitmTreeCdCombo(this.testMaterialManageInfo.forms, selectedItem.upperRitmTreeCd);
      this.setCheckedValue(selectedItem);
      FormUtil.setData(this.testMaterialManageInfo.forms, selectedItem);
    },
    setCheckedValue(data) {
      const item = this.testMaterialManageInfo.forms.filter((column) => column.name === 'extraYn');
      const checkedValue = item[0].groups
        .map((checkbox) => checkbox.checkedValue)
        .filter((group) => data[group] === 'Y');

      this.testMaterialManageInfo.forms.filter((column) => {
        if (column.name === 'extraYn') {
          column.value = checkedValue;
        }
      });
    },
    enableButtons() {
      FormUtil.enableButtons(this.testMaterialManageInfo.buttons, ['save', 'delete', 'init']);
    },
    disableButtons() {
      FormUtil.disableButtons(this.testMaterialManageInfo.buttons, ['delete']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchTestMaterialManage();
        this.initDetail();
      }
    },
    onEventsByDetailButton({ name }) {
      if (name === 'init') {
        return this.initDetail();
      }
      if (name === 'save') {
        return this.checkDataBeforeSave();
      }
      if (name === 'delete') {
        return this.deleteTestMaterialManage();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.fetchTestMaterialManage();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.testMaterialManage.forms, event.item.value);
      }
    },
    inputFormEvent(event) {
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.testMaterialManageInfo.forms, event.item.value);
      }
    },
    setDetailData(data) {
      FormUtil.setData(this.testMaterialManageInfo.forms, data);
      this.enableButtons();
    },
    initDetail() {
      this.testMaterialManageInfo.forms = values.testMaterialManageInfo.forms();
      this.disableButtons();
    },
    checkDataBeforeSave() {
      this.testMaterialManageInfo.forms.validate().then(() => {
        this.isExist() ? this.update() : this.create();
      });
    },
    isExist() {
      return FormUtil.existsValue(this.testMaterialManageInfo.forms, 'ritmCd');
    },
    create() {
      const param = FormUtil.getData(this.testMaterialManageInfo.forms);
      if (param.extraYn == '') {
        param.extraYn = [];
      }
      const checkboxGroups = param.extraYn.reduce((acc, v) => {
        acc[v] = 'Y';
        return acc;
      }, {});

      this.$eSign(() => this.$axios.post('/ms/testMaterialManage', { ...param, ...checkboxGroups }))
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchTestMaterialManage();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    update() {
      const param = FormUtil.getData(this.testMaterialManageInfo.forms);
      if (param.extraYn == '') {
        param.extraYn = [];
      }
      const checkboxGroups = param.extraYn.reduce((acc, v) => {
        acc[v] = 'Y';
        return acc;
      }, {});

      this.$eSignWithReason(() =>
        this.$axios.put('/ms/testMaterialManage', { ...param, ...checkboxGroups }),
      )
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchTestMaterialManage();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    deleteTestMaterialManage() {
      const param = FormUtil.getData(this.testMaterialManageInfo.forms);

      this.$eSignWithReason(() => this.$axios.put('/ms/testMaterialManage/delete', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchTestMaterialManage();
          this.initDetail();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    setRitmTreeCdCombo(forms, upperRitmTreeCd, initValue = '') {
      forms.map((item) => {
        if (item.name == 'ritmTreeCd') {
          item.value = initValue;
          item.async(upperRitmTreeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
  },
};
</script>
