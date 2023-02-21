<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <Card>
    <AUIGrid
      v-bind="detailList"
      @grid-created="(proxy) => $setState('detailList.$grid', proxy)"
    ></AUIGrid>
  </Card>

  <FileAttacherModal
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/analColSearch';

export default {
  name: 'AnalColSearch',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getAnalColItemSearch();
  },
  data() {
    const { list, detailList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getAnalColItemByRitmMngIdx(e);
          },
        },
      },
      detailList: {
        ...detailList.static,
        columns: detailList.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
        readonly: true,
      },
    };
  },
  methods: {
    async getAnalColItemSearch() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/an/analColSearch', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    async getAnalColItemByRitmMngIdx(event) {
      const selectedItem = event.item;
      const data = await this.detailList.$grid
        ._useLoader(() => this.$axios.get(`/an/analColSearch/${selectedItem.ritmMngIdx}`))
        .then(({ data }) => data);

      this.detailList.$grid.setGridData(data);
      this.usageList.$grid.clearGridData();
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getAnalColItemSearch();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
      if (name === 'init') {
        return this.init();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getAnalColItemSearch();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        return this.showModal(event.item);
      }
    },
    init() {
      this.list.$grid.clearGridData();
      this.detailList.$grid.clearGridData();
    },
    showModal(item) {
      this.fileAttacherModal.fileIdx = item.fileIdx;
      this.fileAttacherModal.show = true;
    },
    hideModal() {
      this.fileAttacherModal.show = false;
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
