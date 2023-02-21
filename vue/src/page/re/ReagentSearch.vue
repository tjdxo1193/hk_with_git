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

  <AUIGridSearch
    v-bind="usageList"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('usageList.$grid', proxy)"
  />

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

import values from './values/reagentSearch';

export default {
  name: 'ReagentSearch',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getReagentItemSearch();
  },
  data() {
    const { list, detailList, usageList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getReagentItemByRitmEtrIdx(e);
          },
        },
      },
      detailList: {
        ...detailList.static,
        columns: detailList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getReagentItemByRitmMngIdx(e.item);
          },
        },
      },
      usageList: {
        ...usageList.static,
        forms: usageList.forms(),
        columns: usageList.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
        readonly: true,
      },
    };
  },
  methods: {
    async getReagentItemSearch() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/re/reagentSearch', parameter))
        .then(({ data }) => data);
      this.list.$grid.setGridData(data);
    },
    async getReagentItemByRitmEtrIdx(event) {
      const selectedItem = event.item;
      const data = await this.detailList.$grid
        ._useLoader(() => this.$axios.get(`/re/reagentSearch/${selectedItem.ritmEtrIdx}`))
        .then(({ data }) => data);

      this.detailList.$grid.setGridData(data);
      this.usageList.$grid.clearGridData();
    },
    async getReagentItemByRitmMngIdx(item) {
      const delYn = FormUtil.getValue(this.usageList.forms, 'delYn');
      const data = await this.usageList.$grid
        ._useLoader(() =>
          this.$axios.get(`/re/reagentSearch/${item.ritmEtrIdx}/${item.ritmMngIdx}`, {
            delYn: delYn ? delYn : '',
          }),
        )
        .then(({ data }) => data);

      this.usageList.$grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getReagentItemSearch();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
      if (name === 'init') {
        return this.init();
      }
      if (name === 'usageInit') {
        return this.usageInit();
      }
      if (name === 'usageSearch') {
        const selectedItem = this.detailList.$grid.getSelectedItems();
        return this.getReagentItemByRitmMngIdx(selectedItem[0].item);
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getReagentItemSearch();
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
      this.usageList.$grid.clearGridData();
    },
    usageInit() {
      this.usageList.forms = values.usageList.forms();
      this.usageList.$grid.clearGridData();
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
