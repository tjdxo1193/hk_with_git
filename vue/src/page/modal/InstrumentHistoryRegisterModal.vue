<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <template #default>
      <InputFile
        ref="fileAttach"
        :accept="inputFile.accept"
        @attachFile="transferExcelToJsonData"
      />

      <Space :gap="10" />

      <AUIGrid v-bind="detail" @grid-created="(proxy) => $setState('detail.$grid', proxy)" />
    </template>
  </ModalBase>
</template>

<script>
import { read, utils } from 'xlsx';

import { TokenUtil } from '@/util';

import values from './values/instrumentHistoryRegisterModal';

export default {
  name: 'InstrumentHistoryRegisterModal',
  emits: ['close', 'reloadData'],
  props: {
    title: {
      type: String,
      default: '이력등록',
    },
    show: {
      type: Boolean,
      default: false,
    },
    fileIdx: {
      type: Number,
      default: 0,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    const { detail, buttons } = this.$copy(values);
    return {
      detail: {
        ...detail.static,
        columns: detail.columns(),
      },
      inputFile: {
        accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      },
      buttons: buttons,
    };
  },
  methods: {
    onClickButton({ name }) {
      if (name === 'register') {
        this.dataCheckBeforeSave();
      }
      if (name === 'delete') {
        this.detail.$grid.clearGridData();
      }
    },
    /**
     * * AUIGrid의 제공 기능 중 엑셀 데이터->그리드 변환과 관련하여 참고 링크
     * * https://www.auisoft.net/demo/auigrid/default_demo_localxlsx.html?sc_lsxl&theme=default&s=8722
     */
    transferExcelToJsonData(fileInfo) {
      if (!fileInfo) {
        return false;
      }
      if (!this.isExtensionExcel(fileInfo.type)) {
        this.$refs.fileAttach.init();
        return alert('엑셀 파일만 업로드 할 수 있습니다.');
      }

      const reader = new FileReader();
      let sheetData;

      reader.onload = () => {
        const data = reader.result;
        let workBook = read(data, { type: 'binary', cellDates: true, dateNF: 'YYYY-MM-DD' });
        sheetData = utils.sheet_to_json(workBook.Sheets[workBook.SheetNames[0]], {
          raw: false,
          header: 1,
        });

        this.setHeaderName(sheetData).then((data) => {
          if (data.length == 0) {
            return alert('정확한 정보가 입력되지 않았습니다.');
          }

          this.detail.$grid.addRow(data, 'last');
          this.detail.$grid.updateAllToValue('plntCd', TokenUtil.myPlantCode());
        });
      };

      reader.readAsBinaryString(fileInfo);
      this.$refs.fileAttach.init();
    },
    createGrid(layout) {
      const { $grid } = this.detail;
      if ($grid.isCreated()) {
        $grid.destroy();
      }
      const gridProps = {
        showRowCheckColumn: true,
      };
      $grid.create(layout, gridProps);
    },
    hasGridData() {
      const editedRows = this.detail.$grid.getEditedRowItems();
      const addedRows = this.detail.$grid.getAddedRowItems();
      return editedRows.length || addedRows.length ? true : false;
    },
    hasCheckedRows() {
      const checkedRows = this.detail.$grid.getCheckedRowItems();
      return checkedRows.length ? true : false;
    },
    dataValidation() {
      //* 시험일(YYYY-MM-DD)형식으로 들어갔는지 DATA 체크
      const checkedRows = this.detail.$grid.getCheckedRowItems();
      var dateRegex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
      return checkedRows.filter(({ item }) => item.ansDt == null || !dateRegex.test(item.ansDt))
        .length > 0
        ? false
        : true;
    },
    dataCheckBeforeSave() {
      if (!this.hasGridData()) {
        return this.$warn(this.$message.warn.noSaveGridData);
      }
      if (!this.hasCheckedRows()) {
        return this.$warn(this.$message.warn.unCheckedData);
      }
      if (!this.dataValidation()) {
        return this.$warn(this.$message.validate.invalidInputDateValue);
      }
      return this.register();
    },
    register() {
      const checkedRows = this.detail.$grid.getCheckedRowItems();
      const parameter = checkedRows.map(({ item }) => item);
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSignWithReason(() => this.$axios.post('/in/instHisManage/asList', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.$emit('reloadData');
            this.close();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      });
    },
    close() {
      this.$emit('close');
      this.$refs.fileAttach.init();
      this.detail.$grid.clearGridData();
    },
    isExtensionExcel(type) {
      return (
        type == 'application/vnd.ms-excel' ||
        type == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
        type == 'application/haansoftxlsx'
      );
    },
    async setHeaderName(sheetData) {
      const nameMap = new window.Map([
        ['기기분류', 'eqmDivNm'],
        ['이력구분', 'eqmHisDivNm'],
        ['자산번호', 'sapAspNo'],
        ['기기명', 'eqmNm'],
        ['모델명', 'modNm'],
        ['시리얼넘버', 'srlNo'],
        ['시험일', 'ansDt'],
        ['비고', 'rmk'],
      ]);

      let headerField = sheetData[0];
      const changeHeaderField = new Array();
      //* 한글 field명 -> 영문 field명
      headerField.forEach((headerNm, index) => {
        changeHeaderField[index] = nameMap.get(headerNm);
      });
      headerField = changeHeaderField;

      const changeData = new Array();
      let dataToJson = {};
      for (let j = 1; j < sheetData.length; j++) {
        dataToJson = {};
        const data = sheetData[j];
        for (let i = 0; i < headerField.length; i++) {
          dataToJson[headerField[i]] = data[i];
        }
        changeData[j - 1] = dataToJson;
      }

      return this.setColumnName(changeData);
    },
    async setColumnName(sheetData) {
      const eqmDivHirCd = 'U015';
      const eqmHisDivHirCd = 'U020';
      sheetData = await this.$axios
        .get('/combo/detailUserCommonCode', { value: eqmDivHirCd })
        .then(({ data }) => this.setEqmDivHirCd(data, sheetData));
      sheetData = await this.$axios
        .get('/combo/detailUserCommonCode', { value: eqmHisDivHirCd })
        .then(({ data }) => this.setEqmHisDivHirCd(data, sheetData));
      sheetData = await this.$axios
        .get('/combo/equipment')
        .then(({ data }) => this.setEquipmentCd(data, sheetData));

      if (sheetData.length == 0) {
        return [];
      }

      const hasNullData = this.dataNullCheck(sheetData);
      if (hasNullData.length > 0) {
        return [];
      } else {
        return sheetData;
      }
    },
    setEqmDivHirCd(data, sheetData) {
      // *저장된 기기 목록중에 있는지 먼저 확인
      sheetData.forEach((item) => {
        data.find((row) => row.label == item.eqmDivNm) == null ? (sheetData = []) : sheetData;
      });

      if (sheetData.length > 0) {
        sheetData = sheetData.map((item) => ({
          ...item,
          eqmDiv: data.find((row) => row.label == item.eqmDivNm).value,
          eqmDivNm: data.find((row) => row.label == item.eqmDivNm).label,
        }));
      }

      return sheetData;
    },
    setEqmHisDivHirCd(data, sheetData) {
      // *저장된 기기 목록중에 있는지 먼저 확인
      sheetData.forEach((item) => {
        data.find((row) => row.label == item.eqmHisDivNm) == null ? (sheetData = []) : sheetData;
      });

      if (sheetData.length > 0) {
        sheetData = sheetData.map((item) => ({
          ...item,
          eqmHisDiv: data.find((row) => row.label == item.eqmHisDivNm).value,
          eqmHisDivNm: data.find((row) => row.label == item.eqmHisDivNm).label,
        }));
      }

      return sheetData;
    },
    setEquipmentCd(data, sheetData) {
      // *저장된 기기 목록중에 있는지 먼저 확인
      sheetData.forEach((item) => {
        data.find((row) => row.label == item.eqmNm) == null ? (sheetData = []) : sheetData;
      });

      if (sheetData.length > 0) {
        sheetData = sheetData.map((item) => ({
          ...item,
          eqmCd: data.find((row) => row.label == item.eqmNm).value,
        }));
      }

      return sheetData;
    },
    dataNullCheck(sheetData) {
      return sheetData.filter(
        (row) =>
          row.eqmDiv == null ||
          row.eqmHisDiv == null ||
          row.eqmNm == null ||
          row.modNm == null ||
          row.sapAspNo == null ||
          row.srlNo == null,
      );
    },
  },
};
</script>

<style></style>
