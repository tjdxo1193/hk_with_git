<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="detailPlanInfo" />

    <FormWithHeader v-bind="stabEachTestInfo" @button-click="onClickEditButton" />

    <AUIGridWithHeader
      v-bind="itemSettingList"
      @grid-created="(proxy) => $setState('itemSettingList.$grid', proxy)"
      @button-click="onClickEditButton"
    />
    <!-- <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButtonGroups" /> -->
  </ModalBase>

  <!-- <InputReasonModal
    :show="inputReasonModal.show"
    @modalReturnDataEvent="modalReturnDataEvent"
    @close="hideModal('inputReasonModal')"
  /> -->
</template>

<script>
import dayjs from 'dayjs';

import { FormUtil } from '@/util/index.js';

import values from './values/stabDetailPlanRegModal';

export default {
  name: 'StabDetailPlanRegModal',
  emits: ['close'],
  components: {},
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '안정성상세계획등록',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '600px',
    },
    selectedItem: {
      type: Object,
      default: {},
    },
    disable: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    const { detailPlanInfo, stabEachTestInfo, itemSettingList } = this.$copy(values);
    return {
      detailPlanInfo: {
        ...detailPlanInfo.static,
        forms: detailPlanInfo.forms(),
      },
      stabEachTestInfo: {
        ...stabEachTestInfo.static,
        forms: stabEachTestInfo.forms(),
      },
      itemSettingList: {
        ...itemSettingList.static,
        columns: itemSettingList.columns(),
      },
      // inputReasonModal: {
      //   show: false,
      //   updateType: null,
      // },
    };
  },
  methods: {
    doInit() {
      this.settingInfoText();
      this.preLoadGrid();
      this.resetStabEachTestInfoForms();
      if (!this.$props.disable) {
        this.enableStabEachTestInfoAddBtn();
        this.enableStabEachTestInfoForms();
        this.enableItemSettingListButtonGroups();
      } else {
        this.disableStabEachTestInfoAddBtn();
        this.disableStabEachTestInfoForms();
        this.disableItemSettingListButtonGroups();
      }
      this.disableStabEachTestInfoModifyBtn();
      this.disableStabEachTestInfoDeleteBtn();
    },
    // 기본 정보 바인딩
    settingStabEachTestInfoForms() {
      const { forms } = this.stabEachTestInfo;
      const { selectedItem } = this.$props;

      FormUtil.setData(forms, { ...selectedItem });
    },
    // 상세계획정보 바인딩
    settingInfoText() {
      const { forms } = this.detailPlanInfo;
      const item = this.$props.selectedItem;
      const txtinfo1 =
        '시험목적: ' +
        (item?.ansPpsNm ? item.ansPpsNm : '') +
        '/ ' +
        (item?.ansPpsDtl ? item.ansPpsDtl : '') +
        '\n' +
        '시험종류: ' +
        (item?.ansKndNm ? item.ansKndNm : '') +
        '\n' +
        '시험시작일: ' +
        (item?.ansStrDt ? item.ansStrDt : '') +
        '\n' +
        '시험기간: ' +
        (item?.ansTrmMarkNm ? item.ansTrmMarkNm : '');

      const txtinfo2 =
        '보관조건: ' +
        (item?.strgTermsNm ? item.strgTermsNm : '') +
        '\n' +
        '안정성검체량: ' +
        (item?.sbtSmpVol ? item.sbtSmpVol : '') +
        ' ' +
        (item?.smpVolUnitNm ? item.smpVolUnitNm : '');

      const txtinfo3 =
        '제조번호: ' +
        (item?.lotNo ? item.lotNo : '') +
        '\n' +
        '유효기간: ' +
        (item?.expiredate ? item.expiredate : '');

      FormUtil.setData(forms, { txtinfo1, txtinfo2, txtinfo3 });
    },
    // 폼 버튼 이벤트
    onClickEditButton({ name }) {
      if (name === 'add') {
        //추가
        this.add();
      }
      if (name === 'update') {
        //수정
        this.update();
      }
      if (name === 'delete') {
        this.delete();
      }
      if (name === 'reset') {
        this.doInit();
      }
      if (name === 'save') {
        this.save();
      }
    },
    // 동적 그리드 컨트롤러
    async preLoadGrid() {
      const data = await this.fetchItemSettingList();

      const headerDetail = data.headerDetail;
      const aitmData = data.aitmData;

      this.loadGrid(headerDetail, aitmData);
    },
    // 그리드에 바인딩될 데이터 조회
    async fetchItemSettingList() {
      const { $grid } = this.itemSettingList;
      const parameter = this.$props.selectedItem;

      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan/detail', parameter))
        .then(({ data }) => data);

      return data;
    },
    // 동적 그리드 그리기
    async loadGrid(data = [], aitmData = []) {
      const { $grid } = this.itemSettingList;
      const { forms } = this.stabEachTestInfo;
      const ansCylDivNm = FormUtil.getValue(forms, 'ansCylDivNm');

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
          // 최상단 부모 레벨의 컬럼에 핵심 데이터 저장
          dataField: 'collv1' + i,
          headerText: data[i]?.accMarkNm
            ? data[i].accMarkNm + (data[i].accMarkNm != 'Initial' ? ' ' + ansCylDivNm : '')
            : '',
          ansSmpVol: data[i]?.ansSmpVol ? data[i].ansSmpVol : '',
          accMarkNm: data[i]?.accMarkNm ? data[i].accMarkNm : '',
          sbtAnsIdx: data[i]?.sbtAnsIdx ? data[i].sbtAnsIdx : '',
          ansDt: data[i]?.ansDt ? data[i].ansDt : '',
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
                        checkableFunction: () => {
                          const dateTxt = data[i].ansDt;
                          const ansDt = dateTxt ? dayjs(dateTxt).format('YYYY-MM-DD') : null;
                          const preAccMarkNm = data[i].accMarkNm;
                          const accMarkNm = preAccMarkNm ? preAccMarkNm.split(' ')[0] : '';

                          FormUtil.setData(forms, { ansDt, accMarkNm });
                          return true;
                        },
                        disabledFunction: function () {
                          if (data[i].accMarkNm == 'Initial') {
                            return true;
                          }
                          // If the name of the line item is Anna, the checkbox is disabled
                          if (data[i]?.sbtAnsStt === 'S0310400') {
                            return true;
                          }
                          return false;
                        },
                      },
                      styleFunction: ({ columnIndex }) => {
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
                if (aitmData[j]['amitmCd'] == data2[k].amitmCd) {
                  const colName = 'col' + i;
                  aitmData[j][colName] = true;
                }
              }

              // aitmKn = aitmData[j]['aitmKn'];
              // accMarkNm = dataHeader[i]['accMarkNm'];
              checkData = aitmData[j]['col' + i];
              checkData = !!checkData;
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

      $grid.bind('headerClick', (event) => {
        const { columnIndex } = event;
        const gridHeader = $grid.getColumnLayout();
        const choosedHeader = gridHeader[columnIndex];
        const { ansDt, accMarkNm, ansSmpVol, sbtAnsIdx } = choosedHeader;

        FormUtil.setData(forms, { ansDt, accMarkNm, ansSmpVol, sbtAnsIdx });

        this.disableStabEachTestInfoAddBtn();
        if (!this.$props.disable) {
          this.enableStabEachTestInfoModifyBtn();
          this.enableStabEachTestInfoDeleteBtn();
        } else {
          this.disableStabEachTestInfoModifyBtn();
          this.disableStabEachTestInfoDeleteBtn();
        }
      });

      $grid.bind('cellClick', (event) => {
        if (event.columnIndex == 0) {
          const ansDt = '';
          const accMarkNm = '';
          const ansSmpVol = '';
          const sbtAnsIdx = '';
          FormUtil.setData(forms, { ansDt, accMarkNm, ansSmpVol, sbtAnsIdx });

          if (!this.$props.disable) {
            this.enableStabEachTestInfoAddBtn();
          } else {
            this.disableStabEachTestInfoAddBtn();
          }
          this.disableStabEachTestInfoModifyBtn();
          this.disableStabEachTestInfoDeleteBtn();
        } else {
          const preAnsDt = dataHeader[event.columnIndex - 1].ansDt;
          const ansDt = preAnsDt ? dayjs(preAnsDt).format('YYYY-MM-DD') : '';
          const preAccMarkNm = dataHeader[event.columnIndex - 1].accMarkNm;
          const accMarkNm = preAccMarkNm ? preAccMarkNm.split(' ')[0] : '';
          const ansSmpVol = dataHeader[event.columnIndex - 1].ansSmpVol;
          const sbtAnsIdx = dataHeader[event.columnIndex - 1].sbtAnsIdx;

          FormUtil.setData(forms, { ansDt, accMarkNm, ansSmpVol, sbtAnsIdx });

          this.disableStabEachTestInfoAddBtn();

          const { sbtAnsProc } = this.$props.selectedItem;

          if (
            this.isPlnStop(sbtAnsProc) ||
            this.isPlnSafetyAccepted(sbtAnsProc) ||
            this.$props.disable
          ) {
            this.disableStabEachTestInfoModifyBtn();
            this.disableStabEachTestInfoDeleteBtn();
          } else {
            this.enableStabEachTestInfoModifyBtn();
            this.enableStabEachTestInfoDeleteBtn();
          }
        }
      });

      for (let iRow = 0; iRow < dataGrid.length; iRow++) {
        //최신버전에서 삭제된 항목은 수정할 수 없도록 막음
        // if (useYn[iRow] == 'N') {
        // }
      }
    },
    async stabilityDetailAnalysis(grid, stabilityCode) {
      const data = await grid
        ._useLoader(() =>
          this.$axios.get('/stability_test_plan/stability-detail-analysis', { stabilityCode }),
        )
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });

      return data;
    },
    // 그리드 크기 조정
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
    // 오늘 날짜 얻기
    getToday() {
      let date = new Date();
      let year = date.getFullYear();
      let month = ('0' + (1 + date.getMonth())).slice(-2);
      let day = ('0' + date.getDate()).slice(-2);

      return year + month + day;
    },
    // 안정성검체량 총합 확인
    checkAnsSmpVol() {
      let totalSum = 0;
      const { forms } = this.stabEachTestInfo;
      const preAnsSmpVol = FormUtil.getValue(forms, 'ansSmpVol');
      const ansSmpVol = preAnsSmpVol ? preAnsSmpVol : 0;

      const sbtAnsIdx = FormUtil.getValue(forms, 'sbtAnsIdx');

      const { $grid } = this.itemSettingList;
      const gridHeader = $grid.getColumnLayout();
      const ansSmpVolList = gridHeader
        .filter((data) => data.sbtAnsIdx != sbtAnsIdx)
        .map((data) => data.ansSmpVol)
        .filter((data) => data);

      if (ansSmpVolList.length) {
        const sumReducer = (prev, current) => prev + current;
        totalSum += Number(ansSmpVolList.reduce(sumReducer));
      }

      totalSum += Number(ansSmpVol);

      const item = this.$props.selectedItem;
      const sbtSmpVol = item.sbtSmpVol;

      return sbtSmpVol < totalSum;
    },
    // 추가
    add() {
      const { forms } = this.stabEachTestInfo;
      const parameter = FormUtil.getData(forms);

      if (this.checkAnsSmpVol()) {
        this.$warn(this.$message.warn.noExceedAnsSmpVol);
        return;
      }

      forms.validate().then(() => {
        this.$eSign(() => this.$axios.put('st/stabPlan/detail', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    // 모달 띄우기, 가리기
    showModal(modalName) {
      this.$setState(modalName, { show: true });
    },
    hideModal(modalName) {
      this.$setState(modalName, { show: false });
    },
    // // 수정, 삭제 시 사유 모달
    // modalReturnDataEvent({ rjtReaDiv, rjtRea }) {
    //   const updateType = this.inputReasonModal.updateType;
    //   if (!updateType || !rjtReaDiv || !rjtRea) {
    //     return;
    //   }

    //   if (updateType === 'update') {
    //     this.update({ rjtReaDiv, rjtRea });
    //   } else if (updateType === 'delete') {
    //     this.delete({ rjtReaDiv, rjtRea });
    //   }
    // },
    // 수정
    update() {
      const { forms } = this.stabEachTestInfo;
      const parameter = FormUtil.getData(forms);

      if (this.checkAnsSmpVol()) {
        this.$warn(this.$message.warn.noExceedAnsSmpVol);
        return;
      }

      forms.validate().then(() => {
        this.$eSign(() => this.$axios.put('st/stabPlan/detail', parameter))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    // 삭제
    delete() {
      const { forms } = this.stabEachTestInfo;
      const parameter = FormUtil.getData(forms);

      this.$eSign(() => this.$axios.post('st/stabPlan/detail', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    // 하단 버튼 이벤트
    onClickButtonGroups({ name }) {
      if (name == 'save') {
        this.save();
      }
    },

    isPlnSafetyAccepted(sbtAnsProc) {
      return sbtAnsProc === 'S0290300';
    },
    isPlnStop(sbtAnsProc) {
      return sbtAnsProc === 'S0290400';
    },

    isTestOnProcess(sbtAnsStt) {
      return sbtAnsStt === 'S0310200';
    },
    isTestStop(sbtAnsStt) {
      return sbtAnsStt === 'S0310300';
    },
    isTestEnd(sbtAnsStt) {
      return sbtAnsStt === 'S0310400';
    },

    enableStabEachTestInfoAddBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.enableButtons(buttons, ['add']);
    },
    disableStabEachTestInfoAddBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.disableButtons(buttons, ['add']);
    },

    enableStabEachTestInfoModifyBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.enableButtons(buttons, ['update']);
    },
    disableStabEachTestInfoModifyBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.disableButtons(buttons, ['update']);
    },

    enableStabEachTestInfoDeleteBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.enableButtons(buttons, ['delete']);
    },
    disableStabEachTestInfoDeleteBtn() {
      const { buttons } = this.stabEachTestInfo;
      FormUtil.disableButtons(buttons, ['delete']);
    },

    enableItemSettingListButtonGroups() {
      const { buttons } = this.itemSettingList;
      FormUtil.enableButtons(buttons, ['save']);
    },
    disableItemSettingListButtonGroups() {
      const { buttons } = this.itemSettingList;
      FormUtil.disableButtons(buttons, ['save']);
    },

    enableStabEachTestInfoForms() {
      const { forms } = this.stabEachTestInfo;
      FormUtil.enable(forms, ['ansDt', 'accMarkNm', 'ansSmpVol']);
    },
    disableStabEachTestInfoForms() {
      const { forms } = this.stabEachTestInfo;
      FormUtil.disable(forms, ['ansDt', 'accMarkNm', 'ansSmpVol']);
    },

    resetStabEachTestInfoForms() {
      this.stabEachTestInfo.forms = values.stabEachTestInfo.forms();
      this.settingStabEachTestInfoForms();
    },

    // 체크박스 저장
    save() {
      const tempString = 'col';

      const { forms } = this.stabEachTestInfo;
      const { $grid } = this.itemSettingList;
      const gridData = $grid.getGridData();
      const gridHeader = $grid.getColumnLayout();

      let saveDataList = [];
      // Initial의 안정성 시험 IDX(SBT_ANS_IDX)
      let sbtAnsIdx = gridHeader[1]?.sbtAnsIdx;

      for (const i in gridData) {
        for (const j in gridHeader) {
          const currentData = gridData[i];
          const isSelected = currentData[tempString + j];

          if (!isSelected || !Number(j)) {
            continue;
          }

          const index = Number(j) + 1;
          saveDataList.push({
            sbtAnsIdx: gridHeader[index]?.sbtAnsIdx,
            amitmCd: gridData[i]?.amitmCd,
          });
        }
      }

      if (!saveDataList.length) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      const sbtPlnIdx = FormUtil.getValue(forms, 'sbtPlnIdx');
      const detailPlanRegList = this.$copy(saveDataList);
      const parameter = { sbtPlnIdx, sbtAnsIdx, detailPlanRegList };

      this.$eSign(() => this.$axios.put('st/stabPlan/detail/saveDetailPlanReg', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    close() {
      this.$emit('close');
    },
  },
  watch: {
    show: function () {
      if (!this.$props.show) return;

      this.doInit();
    },
  },
};
</script>
