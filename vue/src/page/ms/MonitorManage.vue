<template>
  <Card>
    <TabBase v-bind="tabs">
      <template #tab-monitorItemInfo>
        <FormWithHeader
          v-bind="monitorManage"
          @button-click="onClickMonitorManageButton"
          @form-event="searchFormEvent"
        />

        <AUIGridWithHeader
          v-bind="monitorSearchResult"
          @grid-created="(proxy) => $setState('monitorSearchResult.$grid', proxy)"
        />

        <FormWithHeader
          v-bind="monitorManageInfo"
          @form-event="infoFormEvent"
          @button-click="onEventsByDetailMonitorButton"
        />
      </template>
      <template #tab-itemSe>
        <TreeCommonCodeManage title="모니터링 품목 구분 목록" code="M1000001" />
      </template>

      <template #tab-workplaceSe>
        <TreeCommonCodeManage title="모니터링 작업실 구분 목록" code="M2000000" />
      </template>
    </TabBase>
  </Card>
</template>

<script>
import { TreeCommonCodeModal } from '@/page/modal';
import TreeCommonCodeManage from '@/page/sy/TreeCommonCodeManage';
import { FormUtil } from '@/util';

import values from './values/monitorManage';

export default {
  name: 'monitorManage',
  components: {
    TreeCommonCodeManage,
    TreeCommonCodeModal,
  },
  mounted() {
    this.fetchMonitorManage();
  },
  data() {
    const { tabs, monitorManage, monitorManageInfo, monitorSearchResult } = this.$copy(values);
    return {
      tabs,
      monitorManage: {
        ...monitorManage.static,
        forms: monitorManage.forms(),
      },
      monitorSearchResult: {
        ...monitorSearchResult.static,
        columns: monitorSearchResult.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.getMonitorManageInfoByMitmCd(event);
          },
        },
      },
      monitorManageInfo: {
        ...monitorManageInfo.static,
        forms: monitorManageInfo.forms(),
      },
    };
  },
  methods: {
    async fetchMonitorManage() {
      const { $grid } = this.monitorSearchResult;
      const { forms } = this.monitorManage;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/monitorManage', parameter))
        .then(({ data }) => data);
      this.monitorSearchResult.$grid.setGridData(data);
    },
    onClickMonitorManageButton({ name }) {
      switch (name) {
        case 'search':
          this.fetchMonitorManage();
          this.initMonitorManageInfo();
          FormUtil.enableButtons(this.monitorManageInfo.buttons, ['init', 'save']);
          FormUtil.disableButtons(this.monitorManageInfo.buttons, ['update', 'delete']);
          break;
      }
    },
    initMonitorManageInfo() {
      this.monitorManageInfo.forms = values.monitorManageInfo.forms();
    },
    async getMonitorManageInfoByMitmCd(event) {
      const selectedItem = event.item;
      const forms = this.monitorManageInfo.forms;
      await this.setSelectByUpperCd(forms, selectedItem.mitmPitmUpperDiv, 'mitmPitmDiv');
      await this.setSelectByUpperCd(forms, selectedItem.mitmWrkStudioDiv, 'mitmWrkPlcUpperDiv');
      await this.setSelectByUpperCd(forms, selectedItem.mitmWrkPlcUpperDiv, 'mitmWrkPlcDiv');
      FormUtil.disable(this.monitorManageInfo.forms, [
        'mitmPitmUpperDiv',
        'perDiv',
        'mitmPitmDiv',
        'mitmWrkPlcDiv',
        'mitmWrkPlcNm',
        'mitmWrkStudioDiv',
        'mitmWrkPlcUpperDiv',
        'ansCylCd',
        'roomno',
        'point',
        'grade',
        'wrkDiv',
      ]);
      FormUtil.setData(this.monitorManageInfo.forms, selectedItem);
      FormUtil.enableButtons(this.monitorManageInfo.buttons, ['update', 'delete']);
      FormUtil.disableButtons(this.monitorManageInfo.buttons, ['save']);
    },
    async createMonitorManage() {
      const param = FormUtil.getData(this.monitorManageInfo.forms);
      await this.$eSign(() => this.$axios.post('/ms/monitorManage', param))
        .then(() => this.$info(this.$message.info.saved))
        .catch(() => this.$error(this.$message.error.createData));
      this.initMonitorManageInfo();
      this.fetchMonitorManage();
    },
    async updateMonitorManage() {
      const param = FormUtil.getData(this.monitorManageInfo.forms);
      await this.$eSign(() => this.$axios.put('/ms/monitorManage', param))
        .then(() => this.$info(this.$message.info.updated))
        .catch(() => this.$error(this.$message.error.updateData));
      this.initMonitorManageInfo();
      this.fetchMonitorManage();
    },
    async deleteMonitorManage() {
      const param = FormUtil.getData(this.monitorManageInfo.forms);
      await this.$eSign(() => this.$axios.put('/ms/monitorManage/delete', param))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.initMonitorManageInfo();
          this.fetchMonitorManage();
        })
        .catch(() => this.$error(this.$message.error.deleteData));
    },
    searchFormEvent(event) {
      const forms = this.monitorManage.forms;
      const value = event.item.value;
      if (event.type === 'change' && event.item.name === 'mitmPitmUpperDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcUpperDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkPlcUpperDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.fetchMonitorManage();
      }
    },
    infoFormEvent(event) {
      const forms = this.monitorManageInfo.forms;
      const value = event.item.value;
      if (event.type === 'change' && event.item.name === 'mitmPitmUpperDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcUpperDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkPlcUpperDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
    },
    async setSelectByUpperCd(forms, upperCd, targetName) {
      let targetForm = FormUtil.findItem(forms, targetName);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetName);
      resultForm.value = '';
      resultForm.elements = result;
    },
    onEventsByDetailMonitorButton({ name }) {
      switch (name) {
        case 'init':
          this.initMonitorManageInfo();
          FormUtil.enableButtons(this.monitorManageInfo.buttons, ['init', 'save']);
          FormUtil.disableButtons(this.monitorManageInfo.buttons, ['update', 'delete']);
          break;
        case 'save':
          this.monitorManageInfo.forms.validate().then(() => {
            this.createMonitorManage();
          });
          break;
        case 'delete':
          this.deleteMonitorManage();
          this.initMonitorManageInfo();
          FormUtil.enableButtons(this.monitorManageInfo.buttons, ['init', 'save']);
          FormUtil.disableButtons(this.monitorManageInfo.buttons, ['update', 'delete']);
          break;
        case 'update':
          this.updateMonitorManage();
          this.fetchMonitorManage();
          break;
      }
    },
  },
};
</script>
