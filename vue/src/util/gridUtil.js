import { $auigrid } from '@/plugin';

import stringUtil from './stringUtil';

export default {
  useLoader(pid, asyncFn) {
    $auigrid.showAjaxLoader(pid);
    return asyncFn().finally(() => $auigrid.removeAjaxLoader(pid));
  },
  moveToGrid(fromGridProxy, toGridProxy) {
    this.moveToGridByPid(fromGridProxy._pid, toGridProxy._pid);
  },
  moveToGridByPid(fromGridPid, toGridPid) {
    const checkedRows = $auigrid.getCheckedRowItems(fromGridPid);
    $auigrid.removeRow(
      fromGridPid,
      checkedRows.map(({ rowIndex }) => rowIndex),
    );
    $auigrid.addRow(
      toGridPid,
      checkedRows.map(({ item }) => item),
    );
  },
  findColumn(columns, dataField) {
    return columns.find((col) => col.dataField === dataField);
  },
  existsValue(proxyOrPid, rowIndex, columnNameOrColumnIndex) {
    const value =
      typeof proxyOrPid === 'string'
        ? $auigrid.getCellValue(proxyOrPid, rowIndex, columnNameOrColumnIndex)
        : proxyOrPid.getCellValue(rowIndex, columnNameOrColumnIndex);
    return stringUtil.isNotEmpty(value);
  },
};
