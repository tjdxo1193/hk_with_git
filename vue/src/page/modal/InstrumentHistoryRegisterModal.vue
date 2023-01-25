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
    transferExcelToJsonData(fileInfo) {
      //* IE10, 11은 readAsBinaryString 지원을 안함. 따라서 체크함.
      //* var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";
      if (!fileInfo) {
        return;
      }

      //TODO 엑셀파일 하나만? 확장자 가리기(only .xlsx)
      const reader = new FileReader();
      let sheetData;

      reader.onload = () => {
        const data = reader.result;
        let workBook = read(data, { type: 'binary', cellDates: true, dateNF: 'YYYY-MM-DD' });
        //TODO sheet1의 정보만 허용할건지, sheet여러개 사용가능하게 할건지
        //* 지금은 sheet1의 정보만 가져옴
        sheetData = utils.sheet_to_json(workBook.Sheets[workBook.SheetNames[0]], { raw: false });
        console.log(sheetData);
        //TODO 엑셀 헤더 한글로 들어오는거 db 컬럼명에 맞추기
        //TODO 공통코드명으로 들어오는거 공통코드로 변경하기
        //TODO HOW TO GET eqmCd?
        sheetData = sheetData.map((row) => ({
          ...row,
          eqmDiv: row.eqmDivNm === 'A 분류' ? 'U0150001' : '',
          eqmHisDiv: row.eqmHisDivNm === '검교정' ? 'U0200001' : '',
        }));
        this.detail.$grid.addRow(sheetData, 'last');

        //* sheet1의 정보만 허용할건지, sheet여러개 사용가능하게 할건지
        //* 모든 sheet data가져옴
        // workBook.SheetNames.forEach(function (sheetName) {
        //   let rows = utils.sheet_to_json(workBook.Sheets[sheetName]);
        // });
      };
      //* 업로드한 files 정보 read
      reader.readAsBinaryString(fileInfo);
      this.$refs.fileAttach.init();
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
      //TODO 그리드 내 필수값, 값 유효성 확인 후 저장
      //* 필수값, 시험일(YYYY-MM-DD)형식으로 들어갔는지 등
      return true;
    },
    dataCheckBeforeSave() {
      if (!this.hasGridData()) {
        return this.$warn(this.$message.warn.noSaveGridData);
      }
      if (!this.hasCheckedRows()) {
        return this.$warn(this.$message.warn.unCheckedData);
      }
      if (!this.dataValidation()) {
        return this.$warn(this.$message.validate.invalidInputValue);
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
  },
};
</script>

<style></style>
