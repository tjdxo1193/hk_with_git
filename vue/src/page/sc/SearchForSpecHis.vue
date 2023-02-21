<template>
  <AUIGridSearch
    v-bind="pitmList"
    @grid-created="(proxy) => $setState('pitmList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <AUIGridWithHeader
    v-bind="versionList"
    @grid-created="(proxy) => $setState('versionList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <AUIGridWithHeader
    v-bind="testItemList"
    @grid-created="(proxy) => $setState('testItemList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <ItemsByTestMethodModal
    :activedGrid="testItemList.$grid"
    :show="itemsByTestMethodModal.show"
    @close="hideItemsByTestMethodModal()"
    @select="addRowTestItem"
  />

  <ItemsCopyByTestMethodModal
    :show="itemsCopyByTestMethodModal.show"
    @close="hideItemsCopyByTestMethodModal()"
    @select="copyRowTestItem"
  />

  <RequestReviewerModal
    :show="requestReviewerModal.show"
    @close="hideRequestReviewerModal()"
    @modalReturnDataEvent="requestReview"
  />
</template>

<script>
import {
  ItemsByTestMethodModal,
  ItemsCopyByTestMethodModal,
  RequestReviewerModal,
} from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/searchForSpecHis';

export default {
  name: 'searchForSpecHis',
  mounted() {
    this.fetchPItemSpecList();
  },
  components: {
    ItemsByTestMethodModal,
    RequestReviewerModal,
    ItemsCopyByTestMethodModal,
  },
  data() {
    const { pitmList, versionList, testItemList } = this.$copy(values);
    return {
      pitmList: {
        ...pitmList.static,
        forms: pitmList.forms(),
        columns: pitmList.columns(),
        event: {
          cellClick: (e) => {
            this.init();
            this.fetchVersionList(e.item);
            this.settingDepartmentList(e.item);
          },
        },
      },
      versionList: {
        ...versionList.static,
        columns: versionList.columns(),
        event: {
          cellClick: (e) => {
            if (this.isSelectedItemHasVersion()) {
              this.fetchAItemListMatchingByPitmType(e.item);
            }
          },
        },
      },
      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
      },
      itemsByTestMethodModal: {
        show: false,
      },
      requestReviewerModal: {
        show: false,
      },
      itemsCopyByTestMethodModal: {
        show: false,
      },
      processCode: {
        temporarySave: 'S0080100',
      },
      pitemtype: {
        finishedSet: 'S0180100',
        finishedSingle: 'S0180101',
        beautifulPackaging: 'S0180102',
        semiManufacturesFillingFoam: 'S0180201',
        semiManufacturesOtherProduct: 'S0180202',
        semiManufacturesBulk: 'S0180203',
        semiManufacturesBase: 'S0180204',
        rawMaterial: 'S0180400',
        packagingMaterial: 'S0180500',
        goods: 'S0180600',
      },
      departmentList: {
        hirDepartmentCode: '',
        list: [],
      },
    };
  },
  methods: {
    async fetchPItemSpecList() {
      const { $grid, forms } = this.pitmList;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('sc/searchForSpecHis/pItem', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },

    async fetchVersionList({ pitmCd, pitmVer }) {
      const { $grid } = this.versionList;
      const parameter = { pitmCd, pitmVer };
      const data = await $grid
        ._useLoader(() => this.$axios.get('sc/searchForSpecHis/version', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
      this.pitmSpecAItemListGridSetting($grid);
    },

    pitmSpecAItemListGridSetting($grid) {
      if (this.isSelectedItemHasNotVersion()) {
        this.versionList.$grid.setSelectionByIndex(0);
        if (this.isImpossibleToRevisionBecausePackigingAndfinishedProduct()) {
          return;
        }
        if (this.isSemiManufactures()) {
          this.fetchPItemSpecSemiAItemList($grid.getItemByRowIndex(0));
        }
      } else {
        const bottomSetting = {
          item: $grid.getItemByRowIndex(0),
          rowIndex: 0,
        };
        this.fetchPItemSpecAItemList(bottomSetting.item);
        this.versionList.$grid.setSelectionByIndex(bottomSetting.rowIndex);
      }
    },

    fetchAItemListMatchingByPitmType(item) {
      this.isSemiManufactures() && item.aitmSpecIdx == null
        ? this.fetchPItemSpecSemiAItemList(item)
        : this.fetchPItemSpecAItemList(item);
    },

    async fetchPItemSpecAItemList({ aitmSpecIdx }) {
      const { $grid } = this.testItemList;
      const parameter = { aitmSpecIdx };

      const data = await $grid
        ._useLoader(() => this.$axios.get('sc/searchForSpecHis/aItem', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchPItemSpecSemiAItemList({ pitmCd, pitmVer }) {
      const { $grid } = this.testItemList;
      const parameter = { pitmCd, pitmVer };

      const data = await $grid
        ._useLoader(() => this.$axios.get('sc/searchForSpecHis/semiAItem', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.fetchPItemSpecList();
        this.init();
        return;
      }
    },

    init() {
      this.versionList.$grid.clearGridData();
      this.testItemList.$grid.clearGridData();
    },

    isSemiManufactures() {
      const selectType = this.pitmList.$grid.getSelectedRows()[0].pitmTyp;
      return (
        selectType == this.pitemtype.semiManufacturesFillingFoam ||
        selectType == this.pitemtype.semiManufacturesOtherProduct ||
        selectType == this.pitemtype.semiManufacturesBulk ||
        selectType == this.pitemtype.semiManufacturesBase
      );
    },

    settingDepartmentList({ crgDptCd }) {
      if (crgDptCd == null || crgDptCd == '') {
        this.departmentList.hirDepartmentCode = '';
        this.departmentList.list = [];

        return;
      }
      this.$axios.get('/ms/specManage/departmentList').then(({ data }) => {
        this.departmentList.hirDepartmentCode = crgDptCd;
        this.departmentList.list = data;
      });
    },

    isSelectedItemHasVersion() {
      return !this.isSelectedItemHasNotVersion();
    },

    isSelectedItemHasNotVersion() {
      return this.versionList.$grid.getItemsByValue('aitmSpecVer', 1).length == 0;
    },

    isImpossibleToRevisionBecausePackigingAndfinishedProduct() {
      const selectType = this.pitmList.$grid.getSelectedRows()[0].pitmTyp;
      return (
        selectType == this.pitemtype.finishedSet ||
        selectType == this.pitemtype.finishedSingle ||
        selectType == this.pitemtype.packagingMaterial
      );
    },
  },
};
</script>

<style></style>
