<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />
  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />
  <AUIGridWithHeader
    v-bind="itemSettingList"
    @grid-created="(proxy) => $setState('itemSettingList.$grid', proxy)"
  />
  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />

  <InputReasonModal
    :show="inputReasonModal.show"
    @modalReturnDataEvent="modalReturnDataEvent"
    @close="hideModal('inputReasonModal')"
  />
</template>

<script>
import dayjs from 'dayjs';

import { InputReasonModal } from '@/page/modal';
import { FormUtil, ObjectUtil } from '@/util';

import values from './values/stabPlanDetailAppr';

export default {
  name: 'StabPlanDetailApprPage',
  components: {
    InputReasonModal,
  },
  data() {
    const { searchForm, gridForSearchResult, itemSettingList, buttonGroups } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },

      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.preLoadGrid(e);
          },
        },
      },

      itemSettingList: {
        ...itemSettingList.static,
        columns: itemSettingList.columns(),
      },

      buttonGroups: {
        buttons: buttonGroups.buttons,
      },

      inputReasonModal: {
        show: false,
        updateType: null,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.resetUpdateType();
      this.resetSearchForm();
      this.resetItemSettingListGrid();
      this.fetchSearchResult();
    },
    // 조회
    async fetchSearchResult() {
      const { $grid } = this.gridForSearchResult;
      const { forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlanDetailAppr', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },

    onClickSearchBtn({ name }) {
      if (name == 'search') {
        this.resetItemSettingListGrid();
        this.fetchSearchResult();
      }
    },
    // inputResonModal의 updateType 초기화
    resetUpdateType() {
      this.inputReasonModal.updateType = null;
    },
    // 조회 검색 폼 초기화
    resetSearchForm() {
      this.searchForm.forms = values.searchForm.forms();
    },
    // 항목설정 그리드 초기화
    resetItemSettingListGrid() {
      // this.itemSettingList.$grid.clearGridData();
      this.itemSettingList.$grid.destroy();
      this.itemSettingList.$grid.create([]);
    },
    // 버튼 클릭 이벤트
    onEventsButton({ name }) {
      const { $grid } = this.gridForSearchResult;
      if (name == 'approve') {
        if (!this.isThereAnyCheckedData($grid)) {
          return this.$warn(this.$message.warn.unCheckedData);
        }
        this.approve();
      }
      if (name == 'reject') {
        if (!this.isThereAnyCheckedData($grid)) {
          return this.$warn(this.$message.warn.unCheckedData);
        }
        this.setUpdateTypeAndInputReasonModalShowOn('reject');
      }
      if (name == 'reset') {
        this.doInit();
      }
    },
    isThereAnyCheckedData(grid) {
      return grid.getCheckedRowItems()?.length;
    },
    // inputReasonModal의 updateType 바인딩 및 모달 띄우기
    setUpdateTypeAndInputReasonModalShowOn(updateType = null) {
      this.inputReasonModal.updateType = updateType;
      this.showModal('inputReasonModal');
    },
    // inputReasonModal 이벤트
    modalReturnDataEvent({ rjtReaDiv, rjtRea }) {
      const updateType = this.inputReasonModal.updateType;
      if (!updateType || !rjtReaDiv || !rjtRea) {
        return;
      }

      if (updateType === 'reject') {
        this.reject({ rjtReaDiv, rjtRea });
      }
    },

    // 모달 띄우기, 가리기
    showModal(modalName) {
      this.$setState(modalName, { show: true });
    },
    hideModal(modalName) {
      this.$setState(modalName, { show: false });
    },

    // 항목설정 그리드 데이터 조회
    async preLoadGrid(event) {
      this.itemSettingList.$grid.destroy();
      const item = event?.item;

      if (!item) {
        return;
      }

      const data = await this.fetchItemSettingList(item);

      const headerDetail = data.headerDetail;
      const aitmData = data.aitmData;
      // const decisionData = data.decisionData;

      this.loadGrid(headerDetail, aitmData);
    },
    // 시험, 시험항목 데이터 조회
    async fetchItemSettingList(item) {
      const { $grid } = this.itemSettingList;

      // 이 부분 없이 그냥 객체로 API에 태워보낼 경우, Validator에 의해 예외 뱉어낸다.
      const { forms } = this.searchForm;
      const sbtPlnIdx = item?.sbtPlnIdx;
      FormUtil.setData(forms, { sbtPlnIdx });

      const parameter = FormUtil.getData(forms);
      // 여기까지

      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan/detail', parameter))
        .then(({ data }) => data);

      return data;
    },
    // 항목설정 그리드 그리기(동적 그리드)
    async loadGrid(data = [], aitmData = []) {
      const { $grid } = this.itemSettingList;
      $grid.destroy();

      const dataGrid = [
        {
          col: '항목명',
        },
        {
          col: '항목명',
        },
        {
          col: '항목명',
        },
        {
          col: '항목명',
        },
      ];

      const columnLayout2 = [];

      columnLayout2.push({
        dataField: 'aitmKn',
        headerText: '항목명',
        width: 240,
        cellMerge: true,
      });

      const auiGridProps2 = {
        copySingleCellOnRowMode: true,
        selectionMode: 'singleRow',
        showRowNumColumn: true,
        showRowCheckColumn: false,
        enableSorting: false,
        fixedColumnCount: 1,
        editableOnFixedCell: true,
      };

      for (const i in data) {
        columnLayout2.push({
          dataField: 'collv1' + i,
          headerText: data[i]?.accMarkNm ? data[i].accMarkNm : '',
          width: '120',
          children: [
            {
              dataField: 'collv2',
              headerText: data[i]?.ansDt ? dayjs(data[i].ansDt).format('YYYY-MM-DD') : '',
              width: '120',
              children: [
                {
                  dataField: 'collv3',
                  headerText: data[i]?.sbtAnsStt ? data[i].sbtAnsSttNm : '',
                  width: '120',
                  children: [
                    {
                      dataField: 'col' + i,
                      headerText: data[i]?.ansSmpVol ? data[i].ansSmpVol : '',
                      width: '120',
                      renderer: {
                        type: 'CheckBoxEditRenderer',
                        showLabel: false,
                        editable: true,
                        checkableFunction: (
                          rowIndex,
                          columnIndex,
                          value,
                          isChecked,
                          item,
                          dataField,
                        ) => {
                          const dateTxt = data[i].ansDt;
                          const dtTestDate = dateTxt ? dayjs(dateTxt).format('YYYY-MM-DD') : null;
                          const preTxtRemark = data[i].accMarkNm;
                          const txtRemark = preTxtRemark ? preTxtRemark.split(' ')[0] : '';

                          return true;
                        },
                        disabledFunction: function (
                          rowIndex,
                          columnIndex,
                          value,
                          isChecked,
                          item,
                          dataField,
                        ) {
                          return true;
                        },
                      },
                      styleFunction: (
                        rowIndex,
                        columnIndex,
                        value,
                        headerText,
                        item,
                        dataField,
                      ) => {
                        // Filter
                        if (this.isTestStop(data[columnIndex - 1]?.sbtAnsStt)) return 'testStop';
                        else if (this.isTestOnProcess(data[columnIndex - 1]?.sbtAnsStt))
                          return 'testProgress';
                        else if (this.isTestEnd(data[columnIndex - 1]?.sbtAnsStt))
                          return 'textExit';
                      },
                    },
                  ],
                },
              ],
            },
          ],
        });
      }

      let dataHeader = data;
      let countData = data?.length;
      $grid.create(columnLayout2, auiGridProps2);
      // Set data grid 1
      let useYn = [];
      const data2 = aitmData?.table2;
      if (aitmData?.length) {
        let iAuditCnt = 0;
        let aitmKn = '';
        let accMarkNm = '';
        let checkData = '';
        for (const i in aitmData) {
          let objFor = { col: aitmData[i].aitmKn, col_Tag: aitmData[i].amitmCd };
          useYn[i] = aitmData[i].useYn;
          for (const j in countData) {
            objFor['col' + j] = false;
          }
          dataGrid.push(objFor);
        }
        if (data2?.length) {
          for (const i in dataHeader) {
            for (const j in aitmData) {
              for (const k in data2) {
                if (
                  aitmData[j]['amitmCd'] == data2[k].amitmCd &&
                  dataHeader[i].stabilityUnitCode == data2[k].stabilityUnitCode
                ) {
                  const colName = 'col' + i;
                  aitmData[j][colName] = true;
                }
              }

              aitmKn = aitmData[j]['aitmKn'];
              accMarkNm = dataHeader[i]['accMarkNm'];
              checkData = aitmData[j]['col' + i];
              checkData = !!checkData;
              // this.auditBeforCheckData[iAuditCnt] = analName + "/" + period + "/" + checkData;
              // iAuditCnt++;
            }
          }
        }
      } else {
        this.$warn(this.$message.warn.noStabPlanDetailData);
        return;
      }

      $grid.setGridData(aitmData);

      const columns = [];
      const columnSize = $grid.getColumnCount();
      for (let i = 1; i < columnSize; i++) {
        columns.push(i);
      }

      this.setGirdColumnSizeExlude($grid, columns, 120, false);

      for (let iRow = 0; iRow < dataGrid.length; iRow++) {
        //최신버전에서 삭제된 항목은 수정할 수 없도록 막음
        if (useYn[iRow] == 'N') {
        }
      }
      // });
    },
    async setGirdColumnSizeExlude(myGrid, excludeColumns, width, isExtend) {
      let colSizeList = myGrid.getFitColumnSizeList(false);
      const columnLayout = myGrid.getColumnLayout();

      for (var i = 0; i < colSizeList.length; i++) {
        if (!!columnLayout[i] && !!columnLayout[i].dataField) {
          const colValues = myGrid.getColumnDistinctValues(columnLayout[i].dataField);
          const maxColumnSize =
            colValues
              .map((value) => new Blob([value]).size)
              .reduce((prev, current) => (prev > current ? prev : current), 0) * 5;

          if (maxColumnSize > colSizeList[i]) {
            colSizeList[i] = maxColumnSize;
          }
        }

        if (excludeColumns.includes(i)) {
          if (isExtend) {
            colSizeList[i] = typeof width == 'number' ? colSizeList[i] + width : null;
          } else {
            colSizeList[i] = typeof width == 'number' ? width : null;
          }
        } else {
          colSizeList[i] += 10;
        }
      }

      myGrid.setColumnSizeList(colSizeList);
    },

    // 계획 상태값 확인
    isTestOnProcess(sbtAnsStt) {
      return sbtAnsStt === 'S0310200';
    },
    isTestStop(sbtAnsStt) {
      return sbtAnsStt === 'S0310300';
    },
    isTestEnd(sbtAnsStt) {
      return sbtAnsStt === 'S0310400';
    },

    // 안정성시험계획 승인
    approve() {
      const checkedRows = this.gridForSearchResult.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      // 내일 할 일 ☆
      // 01. 승인, 결과승인 마무리하기
      //  ㄴ 완료 프로세스: 계획 (승인, 반려), 중단 (승인, 반려)
      //  ㄴ 남은 프로세스: 결과 (승인, 반려), 계획 변경(승인, 반려)(이건 상태 코드가 따로 없다.)
      // 그러면 끝
      // 그리고 MIM 들어가기

      if (parameter.length) {
        this.$eSign(() => this.$axios.put('/st/stabPlanDetailAppr/approve', parameter))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.fetchSearchResult();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      } else {
        this.$warn(this.$message.warn.unCheckedData);
      }
    },
    // 안정성시험계획 반려
    reject({ rjtReaDiv = null, rjtRea = null }) {
      let checkedRows = this.gridForSearchResult.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => {
        return { ...row.item, rjtReaDiv, plnRjtRea: rjtRea };
      });

      if (parameter.length) {
        this.$eSign(() => this.$axios.put('/st/stabPlanDetailAppr/reject', parameter))
          .then(() => {
            this.$info(this.$message.info.reject);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      } else {
        this.$warn(this.$message.warn.unCheckedData);
      }
    },
  },
};
</script>

<style></style>
