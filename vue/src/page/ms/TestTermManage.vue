<template>
  <Horizontal :spans="[4.2, 5.8]">
    <AUIGridSearch
      v-bind="term"
      @grid-created="(proxy) => $setState('term.$grid', proxy)"
      @button-click="onClickButtonOnTerm"
    />

    <Vertical>
      <FormWithHeader v-bind="termInput" @button-click="onClickButtonOnTermInput" />
      <AUIGridWithHeader
        v-bind="interval"
        @grid-created="(proxy) => $setState('interval.$grid', proxy)"
        @button-click="onClickButtonOnInterval"
      />
    </Vertical>
  </Horizontal>
</template>

<script>
import { FormUtil, GridUtil } from '@/util';

import values from './values/testTermManage';

export default {
  name: 'TestTermManagePage',
  mounted() {
    this.fetchTermList();
    this.fetchTermUnitDivCode();
  },
  data() {
    const { term, termInput, interval } = this.$copy(values);
    return {
      term: {
        ...term.static,
        forms: term.forms(),
        columns: term.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getTermInputToGrid(e);
          },
        },
      },
      termInput: {
        ...termInput.static,
        forms: termInput.forms(),
      },
      interval: {
        ...interval.static,
        columns: interval.columns(),
        event: {
          cellEditBegin: (e) => {
            this.setDropDownList(e);
          },
          cellEditEnd: (e) => {
            this.setRuleNmAndMarkNm(e);
            this.removeRowToIntervalWhenBelowSelectedNum(e);
          },
        },
      },
      termUnitDivCode: {
        list: [],
      },
    };
  },
  methods: {
    async fetchTermList() {
      const { $grid, forms } = this.term;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/testTermManage/term', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async fetchIntervalList() {
      const { $grid } = this.interval;
      const { forms } = this.termInput;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/testTermManage/interval', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async fetchTermUnitDivCode() {
      const parameter = {
        code: 'S009',
      };
      await this.$axios
        .get('ms/testTermManage/getTestCycleDivision', parameter)
        .then(({ data }) => (this.termUnitDivCode.list = data));
    },
    getTermInputToGrid(event) {
      const selectedItem = event.item;
      FormUtil.enableButtons(this.termInput.buttons, ['delete']);
      FormUtil.setData(this.termInput.forms, selectedItem);
      FormUtil.enableButtons(this.interval.buttons, ['addRow', 'removeRow', 'save']);
      this.fetchIntervalList();
    },
    onClickButtonOnTerm({ name }) {
      if (name === 'search') {
        this.fetchTermList();
      }
    },
    onClickButtonOnTermInput({ name }) {
      if (name === 'new') {
        this.newTerm();
        return;
      }
      if (name === 'save') {
        this.termInput.forms.validate().then(() => {
          this.saveTerm();
        });
        return;
      }
      if (name === 'delete') {
        this.deleteTerm();
        return;
      }
    },
    newTerm() {
      this.termInput.forms = values.termInput.forms();
      FormUtil.disableButtons(this.termInput.buttons, ['delete']);
      FormUtil.disableButtons(this.interval.buttons, ['addRow', 'removeRow', 'save']);
      this.interval.$grid.clearGridData();
    },
    saveTerm() {
      this.termInput.forms.validate().then(() => {
        this.isExistAnsTrmCdInDetailForm() ? this.updateTerm() : this.insertTerm();
      });
    },
    isExistAnsTrmCdInDetailForm() {
      return FormUtil.existsValue(this.termInput.forms, 'ansTrmCd');
    },
    insertTerm() {
      const { forms } = this.termInput;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.post('/ms/testTermManage/term', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.newTerm();
          this.fetchTermList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    updateTerm() {
      const { forms } = this.termInput;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/ms/testTermManage/term', parameter))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.newTerm();
          this.fetchTermList();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    deleteTerm() {
      const { forms } = this.termInput;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/ms/testTermManage/term/delete', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.newTerm();
          this.fetchTermList();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },

    onClickButtonOnInterval({ name }) {
      if (name === 'addRow') {
        this.addRowToInterval();
        return;
      }
      if (name === 'removeRow') {
        this.removeRowToInterval();
        return;
      }
      if (name === 'save') {
        if (!this.isEditGrid()) {
          this.$warn(this.$message.validate.noEditedRowItems);
          return;
        }

        this.isNotAllDeleteToIntervalGrid() ? this.saveInterval() : this.deleteInterval();
        return;
      }
    },
    isEditGrid() {
      return (
        this.interval.$grid.getEditedRowItems().length !== 0 ||
        this.interval.$grid.getRemovedItems().length !== 0 ||
        this.interval.$grid.getAddedRowItems().length !== 0
      );
    },
    isNotAllDeleteToIntervalGrid() {
      return this.interval.$grid.getGridData().length !== 0;
    },
    addRowToInterval() {
      const { plntCd, ansTrmCd, ansItv, ansTrm, ansCylDiv } = FormUtil.getData(
        this.termInput.forms,
      );
      const termUnitItem = this.termUnitDivCode.list.find((row) => row.code == ansCylDiv);

      const selectedRowIdx = this.interval.$grid.getSelectedIndex()[0];
      const rowCount = this.interval.$grid.getRowCount();

      // 행개수는 기간/간격 보다 작아야한다.
      if (rowCount >= ansTrm / ansItv) {
        this.$warn(this.$message.warn.notAddRowBecauseExcess);
        return;
      }

      // 행추가시 행개수가 0 일경우
      if (rowCount == 0) {
        this.interval.$grid.addRow({
          plntCd,
          ansTrmCd,
          accRulNm: ansItv + termUnitItem.abbr,
          accMarkNm: ansItv + termUnitItem.labelEn,
          ansAccVal: ansItv,
        });
        return;
      }

      if (selectedRowIdx >= 0) {
        const intervalNum =
          parseInt(this.interval.$grid.getItemByRowIndex(selectedRowIdx).ansAccVal) +
          parseInt(ansItv);
        this.interval.$grid.addRow(
          {
            plntCd,
            ansTrmCd,
            accRulNm: intervalNum + termUnitItem.abbr,
            accMarkNm: intervalNum + termUnitItem.labelEn,
            ansAccVal: intervalNum,
          },
          'selectionDown',
        );
        return;
      }

      if (selectedRowIdx == -1) {
        const intervalNum =
          parseInt(
            this.interval.$grid.getItemByRowIndex(this.interval.$grid.getRowCount() - 1).ansAccVal,
          ) + parseInt(ansItv);
        this.interval.$grid.addRow({
          plntCd,
          ansTrmCd,
          accRulNm: intervalNum + termUnitItem.abbr,
          accMarkNm: intervalNum + termUnitItem.labelEn,
          ansAccVal: intervalNum,
        });
        return;
      }
    },
    removeRowToInterval() {
      this.interval.$grid.removeCheckedRows();
    },
    saveInterval() {
      const gridData = this.interval.$grid.getGridData();
      this.$eSignWithReason(() => this.$axios.post('/ms/testTermManage/interval', gridData))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchIntervalList();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    deleteInterval() {
      const parameter = FormUtil.getData(this.termInput.forms);
      this.$eSignWithReason(() => this.$axios.post('/ms/testTermManage/interval/delete', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchIntervalList();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    setDropDownList({ value, rowIndex }) {
      const { editRenderer } = GridUtil.findColumn(this.interval.columns, 'ansAccVal');
      const { ansItv, ansTrm } = FormUtil.getData(this.termInput.forms);

      const dropDownList = [];
      const isNotUpdateInterval = rowIndex == 0;

      if (isNotUpdateInterval) {
        dropDownList.push({ value, label: value });
      } else {
        for (
          let cumulativeNumber = ansItv;
          cumulativeNumber <= ansTrm;
          cumulativeNumber += ansItv
        ) {
          dropDownList.push({ value: cumulativeNumber, label: cumulativeNumber });
        }
      }

      editRenderer.list = dropDownList.filter((intervalItem) => intervalItem.value >= value);
    },

    setRuleNmAndMarkNm({ dataField, value, rowIndex }) {
      const dropDownColumn = 'ansAccVal';
      if (dataField == dropDownColumn) {
        const currentValue = value;
        const { forms } = this.termInput;
        const { ansCylDivEn, ansCylDivAbbr } = FormUtil.getData(forms);
        console.log(currentValue + ansCylDivAbbr);
        this.interval.$grid.setCellValue(rowIndex, 'accRulNm', currentValue + ansCylDivAbbr);
        this.interval.$grid.setCellValue(rowIndex, 'accMarkNm', currentValue + ansCylDivEn);
      }
    },
    removeRowToIntervalWhenBelowSelectedNum({ rowIndex, value }) {
      const removeIndexs = [];
      const gridData = this.interval.$grid.getGridData();
      for (let indexNum = rowIndex + 1; indexNum < gridData.length; indexNum++) {
        if (value >= gridData[indexNum].ansAccVal) {
          removeIndexs.push(indexNum);
        } else {
          break;
        }
      }
      this.interval.$grid.removeRow(removeIndexs);
    },
  },
};
</script>

<style></style>
