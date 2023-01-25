<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="detailPlanInfo" />

    <FormWithHeader v-bind="stabEachTestInfo" @button-click="onClickEditButton" />

    <AUIGridWithHeader
      v-bind="itemSettingList"
      @grid-created="(proxy) => $setState('itemSettingList.$grid', proxy)"
    />
    <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButtonGroups" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util/index.js';
import dayjs from 'dayjs';

import values from './values/stabDetailPlanRegModal';

export default {
  name: 'StabDetailPlanRegModal',
  emits: ['close'],
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
  },
  data() {
    const { detailPlanInfo, stabEachTestInfo, itemSettingList, buttonGroups } = this.$copy(values);
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
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
    };
  },
  methods: {
    onInit() {
      this.settingInfoText();
      this.preLoadGrid();
      this.resetStabEachTestInfoForms();
      this.enableStabEachTestInfoAddBtn();
      this.disableStabEachTestInfoModifyBtn();
      this.disableStabEachTestInfoDeleteBtn();
    },
    settingStabEachTestInfoForms() {
      const { forms } = this.stabEachTestInfo;
      const { selectedItem } = this.$props;
      
      FormUtil.setData(forms, { ...selectedItem });
    },
    settingInfoText() {
      const { forms } = this.detailPlanInfo;
      const item = this.$props.selectedItem;
      const txtinfo1 = "시험목적: " + (item?.ansPpsNm ? item.ansPpsNm : '') + "/ " + (item?.ansPpsDtl ? item.ansPpsDtl : '') + "\n" +
        "시험종류: " + (item?.ansKndNm ? item.ansKndNm : '') + "\n" +
        "시험시작일: " + (item?.ansStrDt ? item.ansStrDt : '') + "\n" +
        "시험기간: " + (item?.ansTrmMarkNm ? item.ansTrmMarkNm : '');

      const txtinfo2 = "보관조건: " + (item?.strgTermsNm ? item.strgTermsNm : '') + "\n" +
        "안정성검체량: " + (item?.sbtSmpVol ? item.sbtSmpVol : '') + ' ' + (item?.smpVolUnitNm ? item.smpVolUnitNm : '');

      const txtinfo3 = "제조번호: " + (item?.lotNo ? item.lotNo : '') + "\n" +
        "유효기간: " + (item?.expiredate ? item.expiredate : '');

      FormUtil.setData(forms, { txtinfo1, txtinfo2, txtinfo3 });
    },
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
    },
    async preLoadGrid() {
      const data = await this.fetchItemSettingList();

      const headerDetail = data.headerDetail;
      const aitmData = data.aitmData;

      this.loadGrid(headerDetail, aitmData);
    },
    async fetchItemSettingList() {
      const { $grid } = this.itemSettingList;
      const parameter = this.$props.selectedItem;

      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan/detail', parameter))
        .then(({ data }) => data);

      return data;
    },
    async loadGrid(data = [], aitmData = []) {
      const { $grid } = this.itemSettingList;
      const { forms } = this.stabEachTestInfo;
      $grid.destroy();

      const dataGrid = [
        {
          col: "항목명"
        },
        {
          col: "항목명"
        },
        {
          col: "항목명"
        },
        {
          col: "항목명"
        },
      ];

      const columnLayout2 = [];

      columnLayout2.push({
        dataField: 'aitmKn',
        headerText: "항목명",
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
        editableOnFixedCell: true
      };

      for (const i in data) {
        columnLayout2.push(
          { dataField: 'collv1' + i,
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
                        type: "CheckBoxEditRenderer",
                        showLabel: false,
                        editable: true,
                        checkableFunction: (rowIndex, columnIndex, value, isChecked, item, dataField) => {
                          const dateTxt = data[i].ansDt;
                          const dtTestDate = dateTxt ? dayjs(dateTxt).format('YYYY-MM-DD') : null;
                          const preTxtRemark = data[i].accMarkNm;
                          const txtRemark = preTxtRemark ? preTxtRemark.split(" ")[0] : "";

                          FormUtil.setData(forms, { dtTestDate, txtRemark });
                          return true;
                        },
                        disabledFunction: function (rowIndex, columnIndex, value, isChecked, item, dataField) {
                          if (data[i].accMarkNm == "Initial") {
                            return true;
                          }
                          // If the name of the line item is Anna, the checkbox is disabled
                          if (data[i]?.sbtAnsStt === 'S0310400') {
                            return true;
                          }
                          return false;
                        }
                      },
                      styleFunction: (rowIndex, columnIndex, value, headerText, item, dataField) => {
                        // Filter 
                        if (this.isTestStop(data[columnIndex - 1]?.sbtAnsStt))
                          return 'testStop';
                        else if (this.isTestOnProcess(data[columnIndex - 1]?.sbtAnsStt))
                          return 'testProgress';
                        else if (this.isTestEnd(data[columnIndex - 1]?.sbtAnsStt))
                          return 'textExit';
                      }
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
      // 내일 할 일 ☆
      // 밑에 배열 두 개는 일단 보류.
      // this.auditBeforCheckData = [];
      // this.auditAfterCheckData = [];
      let useYn = [];
      const data2 = aitmData?.table2;
      if (aitmData?.length) {
        let iAuditCnt = 0;
        let aitmKn = "";
        let accMarkNm = "";
        let checkData = "";
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
                if (aitmData[j]['amitmCd'] == data2[k].amitmCd && dataHeader[i].stabilityUnitCode == data2[k].stabilityUnitCode) {
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

      $grid.bind('cellClick', (event) => {
        if (event.columnIndex == 0) {
          const dtTestDate = '';
          const txtRemark = '';
          const txtSampleAmt = '';
          FormUtil.setData(forms, { dtTestDate, txtRemark, txtSampleAmt });

          this.enableStabEachTestInfoAddBtn();
          this.disableStabEachTestInfoModifyBtn();
          this.disableStabEachTestInfoDeleteBtn();
        } else {
          const preDtTestDate = dataHeader[event.columnIndex - 1].ansDt;
          const dtTestDate = preDtTestDate ? dayjs(preDtTestDate).format('YYYY-MM-DD') : '';
          const preTxtRemark = dataHeader[event.columnIndex - 1].accMarkNm;
          const txtRemark = preTxtRemark ? preTxtRemark.split(" ")[0] : "";
          const txtSampleAmt = dataHeader[event.columnIndex - 1].ansSmpVol;

          FormUtil.setData(forms, { dtTestDate, txtRemark, txtSampleAmt });
          
          this.disableStabEachTestInfoAddBtn();
          
          const { sbtAnsProc } = this.$props.selectedItem;

          if (this.isPlnStop(sbtAnsProc) || this.isPlnSafetyAccepted(sbtAnsProc)) {
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
        if (useYn[iRow] == "N") {

        }
      }
      // });
    },
    async stabilityDetailAnalysis(grid, stabilityCode) {
      const data = await grid
        ._useLoader(() => this.$axios.get('/stability_test_plan/stability-detail-analysis', { stabilityCode }))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });

      return data;
    },
    async setGirdColumnSizeExlude(myGrid, excludeColumns, width, isExtend) {
      let colSizeList = myGrid.getFitColumnSizeList(false);
      const columnLayout = myGrid.getColumnLayout();

      for (var i = 0; i < colSizeList.length; i++) {
        if (!!columnLayout[i] && !!columnLayout[i].dataField) {
          const colValues = myGrid.getColumnDistinctValues(columnLayout[i].dataField);
          const maxColumnSize = colValues.map(value => new Blob([value]).size).reduce((prev, current) => (prev > current) ? prev : current , 0) * 5;

          if (maxColumnSize > colSizeList[i]) {
              colSizeList[i] = maxColumnSize;
          }
        }

        if (excludeColumns.includes(i)) {
          if (isExtend) {
            colSizeList[i] = (typeof width == 'number') ? colSizeList[i] + width : null;
          } else {
            colSizeList[i] = (typeof width == 'number') ? width : null;
          }
        } else {
          colSizeList[i] += 10;
        }
      }

      myGrid.setColumnSizeList(colSizeList);
    },
    getToday(){
      let date = new Date();
      let year = date.getFullYear();
      let month = ("0" + (1 + date.getMonth())).slice(-2);
      let day = ("0" + date.getDate()).slice(-2);

      return year + month + day;
    },
    add() {
      // 내일 할 일 ☆
      // forms에 바인딩 된 데이터를 긁어서 추가, 수정, 삭제 작업을 진행할 것이다.
      // 대상이 되는 주체 테이블은 ST_SBT_ANS이고 부가 테이블은 ST_SBT_ANS_AITM이다.
      // ST_SBT_ANS_AITM은 ST_SBT_ANS에 자식 테이블이며, ST_SBT_ANS_SITM은 삭제, 사용 여부를 판단하는 컬럼이 없으므로, 수정, 체크 유무에 따라 데이터를 추가하던, 지우던 하면된다.
      const { forms } = this.stabEachTestInfo;
      const data = FormUtil.getData(forms, 'forms');
      console.log(data,' data');
    },
    update() {
      const { forms } = this.stabEachTestInfo;
      const data = FormUtil.getData(forms, 'forms');
      console.log(data,' data');
    },
    delete() {
      const { forms } = this.stabEachTestInfo;
      const data = FormUtil.getData(forms, 'forms');
      console.log(data,' data');
    },
    onClickButtonGroups({ name }) {
      if (name == 'save') {
        this.save();
      }
      if (name == 'reset') {
        this.onInit();
      }
    },

    isPlnSafetyAccepted(sbtAnsProc) {
      return sbtAnsProc === 'S0290300';
    },
    isPlnStop(sbtAnsProc) {
      // return this.$props.sbtAnsProc === '시험중단';
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

    resetStabEachTestInfoForms() {
      this.stabEachTestInfo.forms = values.stabEachTestInfo.forms();
      this.settingStabEachTestInfoForms();
    },

    save() {
      const { $grid } = this.itemSettingList;
      const gridData = $grid.getGridData();
      
      console.log($grid.getGridData());
    },
    close() {
      this.$emit('close');
    },
  },
  watch: {
    show: function() {
      if(!this.$props.show) return;

      this.onInit();
    }
  }
};
</script>
