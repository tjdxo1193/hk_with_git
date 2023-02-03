<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <FormWithHeader v-bind="testInfo" @form-event="formEvent" @button-click="onClickButton" />
    <FormWithHeader v-bind="requestInfo" />
  </Card>

  <TestModal
    :show="testModal.show"
    @close="hideModal('testModal')"
    @modalReturnDataEvent="testModalReturnDataEvent"
  ></TestModal>
</template>

<script>
import { TestModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testRequest';

export default {
  name: 'TestRequest',
  components: {
    TestModal,
  },
  mounted() {
    this.getTestRequestList();
  },
  data() {
    const { list, requestInfo, testInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.requestInfo.forms, e.item);
            FormUtil.setData(this.testInfo.forms, e.item);
            this.enableButtons(['init', 'save']);
          },
        },
      },
      requestInfo: {
        ...requestInfo.static,
        forms: requestInfo.forms(),
      },
      testInfo: {
        ...testInfo.static,
        forms: testInfo.forms(),
      },
      testModal: {
        show: false,
      },
    };
  },
  methods: {
    async getTestRequestList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/ts/testRequest', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    requestRegist() {
      const parameter = FormUtil.getData(this.testInfo.forms);
      this.$eSign(() => this.$axios.put('/ts/testRequest/requestRegist', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestRequestList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    request() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      if (checkedRows.length > 0) {
        const parameter = checkedRows.map((row) => ({
          ...row.item,
        }));
        this.$eSignWithReason(() => this.$axios.put('/ts/testRequest/request', parameter))
          .then(() => {
            this.$info(this.$message.info.request);
            this.init();
            this.getTestRequestList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    save() {
      const parameter = FormUtil.getData(this.testInfo.forms);
      this.$eSign(() => this.$axios.put('/ts/testRequest/save', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestRequestList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    checkTestType() {
      const item = FormUtil.getData(this.testInfo.forms);
      return item.ansTyp === null || item.ansTyp === '' ? true : false;
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestRequestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestRequestList();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'requestRegist') {
        if (this.checkTestType()) {
          return this.$warn(this.$message.validate.requiredValueNotInput);
        }
        return this.requestRegist();
      }
      if (name === 'request') {
        this.request();
      }
      if (name === 'save') {
        if (this.checkTestType()) {
          return this.$warn(this.$message.validate.requiredValueNotInput);
        }
        return this.save();
      }
    },
    formEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal(event.item.itemLabel);
      }
    },
    testModalReturnDataEvent(data) {
      FormUtil.setData(this.requestInfo.forms, data);
      FormUtil.setData(this.testInfo.forms, data);
      this.hideModal('testModal');
      this.enableButtons(['init', 'requestRegist']);
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.testInfo.forms = values.testInfo.forms();
      this.disableButtons(['init', 'save', 'requestRegist']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.testInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.testInfo.buttons, buttons);
    },
  },
};
</script>

<style></style>
