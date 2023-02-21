<template>
  <div class="auigrid-container">
    <article :id="state.pid" :style="{ width, height }" class="legend-wrap"></article>
  </div>
</template>

<script>
import dayjs from 'dayjs';

import { GridUtil } from '@/util';

export default {
  name: 'AUIGridBase',
  inheritAttrs: false,
  emits: ['grid-created', 'grid-button-click'],
  props: {
    title: String,
    columns: {
      type: Array,
      default: () => [],
      meta: {
        reference: 'https://www.auisoft.net/documentation/auigrid/ColumnLayout/Column.html',
      },
    },
    props: {
      type: Object,
      default: () => ({}),
      meta: {
        reference: 'https://www.auisoft.net/documentation/auigrid/DataGrid/Properties.html',
      },
    },
    defaultData: {
      type: Array,
      default: () => [],
    },
    event: {
      type: Object,
      default: () => ({}),
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '100%',
    },
  },
  mounted() {
    const { columns, props = {}, event } = this.$props;
    const pid = this.$auigrid.create(
      this.state.pid,
      this.addEditableHeaderStyleToColumns(columns),
      {
        ...this.getDefaultProp(),
        ...props,
      },
    );

    const proxy = this.makeMethodProxy(pid);
    proxy._pid = pid;
    proxy._useLoader = (asyncFn) => GridUtil.useLoader(pid, asyncFn);

    this.created(pid, event, proxy);
    this.$auigrid.setGridData(pid, this.$props.defaultData);
  },
  beforeUnmount() {
    this.destroy();
  },
  data() {
    return {
      state: {
        pid: this.generateId(),
      },
      arrCurrentDataField: [],
    };
  },
  watch: {
    columns: {
      deep: true,
      handler(newColumns) {
        if (newColumns) {
          this.$auigrid.changeColumnLayout(
            this.state.pid,
            this.addEditableHeaderStyleToColumns(newColumns),
          );
        }
      },
    },
  },
  methods: {
    getDefaultProp() {
      return {
        editable: true,
        copySingleCellOnRowMode: true,
        enableCellMerge: false,
        selectionMode: 'singleRow',
        showRowCheckColumn: false,
        showRowNumColumn: true,
        softRemoveRowMode: false,
        useContextMenu: true,
        enableFilter: true,
        showEditedCellMarker: false,
        enableRightDownFocus: true,
        contextMenuItems: [
          { label: '모든 필터링 초기화', callback: this.contextItemHandler },
          { label: '_$line' },
          { label: '그룹핑 보이기', callback: this.contextItemHandler },
          { label: '그룹핑 숨기기', callback: this.contextItemHandler },
          { label: '필터 보이기', callback: this.contextItemHandler },
          { label: '필터 숨기기', callback: this.contextItemHandler },
          { label: '_$line' },
          { label: 'Export To Excel', callback: this.contextItemHandler },
          { label: '_$line' },
          { label: '현재 컬럼 숨기기', callback: this.contextItemHandler },
          { label: '모든 컬럼 보이기', callback: this.contextItemHandler },
          { label: '_$line' },
          { label: '행 개수', callback: this.contextItemHandler },
        ],
      };
    },
    generateId() {
      return `auigrid-${Math.trunc(Math.random() * 10 ** 17)}`;
    },
    bindAUIGridEvent(pid, event) {
      for (let eventName in event) {
        this.$auigrid.bind(pid, eventName, event[eventName]);
      }
    },
    setCustomEventEmmiter(columns) {
      for (const column of columns) {
        if (this.isButtonColumn(column)) {
          column.renderer.onClick = (event) => this.$emit('grid-button-click', event);
        }
      }
    },
    isButtonColumn(column) {
      return column.renderer && column.renderer.type === 'ButtonRenderer';
    },
    resize() {
      this.$auigrid.resize(this.state.pid);
    },
    created(pid, event, proxy) {
      window.addEventListener('resize', this.resize);
      this.bindAUIGridEvent(pid, event);
      this.setCustomEventEmmiter(this.$props.columns);
      this.$emit('grid-created', proxy);
    },
    destroy() {
      window.removeEventListener('resize', this.resize);
      this.$auigrid.destroy(this.state.pid);
    },
    makeMethodProxy(pid) {
      return Object.entries(this.$auigrid).reduce((acc, [name, property]) => {
        if (typeof property === 'function') {
          acc[name] = (...args) => property.call(window.AUIGrid, pid, ...args);
        }
        return acc;
      }, {});
    },
    contextItemHandler(event) {
      const currentDataField = event.dataField;
      const pid = this.state.pid;
      const gridRowCount = this.$auigrid.getRowCount(pid);
      const downloadFileName = 'Today : ' + dayjs(new Date()).format('YYYY-MM-DD HH_mm_ss');

      switch (event.contextIndex) {
        case 0:
          this.$auigrid.clearFilterAll(pid);
          break;
        case 2:
          this.$auigrid.setProp(pid, 'useGroupingPanel', true);
          this.$auigrid.refresh(pid);
          break;
        case 3:
          this.$auigrid.setProp(pid, 'useGroupingPanel', false);
          this.$auigrid.refresh(pid);
          break;
        case 4:
          this.setFilter(pid);
          break;
        case 5:
          this.removeFilter(pid);
          break;
        case 7:
          this.$auigrid.exportToXlsx(pid, { fileName: downloadFileName });
          break;
        case 9:
          this.$auigrid.hideColumnByDataField(pid, currentDataField);
          this.arrCurrentDataField.push(currentDataField);
          break;
        case 10:
          this.$auigrid.showColumnByDataField(pid, this.arrCurrentDataField);
          this.$auigrid.resize(pid);
          this.arrCurrentDataField = [];
          break;
        case 12:
          if (gridRowCount) {
            alert('행 개수: ' + gridRowCount);
          }
          break;
      }
    },
    setFilter(pid) {
      const selectedItems = this.$auigrid.getSelectedItems(pid);
      const firstSelectedItem = selectedItems[0];

      if (selectedItems.length <= 0) return;

      this.$auigrid.setColumnProp(pid, firstSelectedItem.columnIndex, {
        filter: { showIcon: true, displayFormatValues: true },
      });

      this.$auigrid.refresh(pid);
    },
    removeFilter(pid) {
      const selectedItems = this.$auigrid.getSelectedItems(pid);
      const firstSelectedItem = selectedItems[0];

      if (selectedItems.length <= 0) return;

      this.$auigrid.setColumnProp(pid, firstSelectedItem.columnIndex, {
        filter: { showIcon: false },
      });

      this.$auigrid.refresh(pid);
    },
    addEditableHeaderStyleToColumns(columns) {
      // const editableRenderers = [
      //   'editRenderer',
      //   'calendarRenderer',
      //   'checkboxRenderer',
      //   'comboRenderer',
      // ];
      return columns.map((col) => {
        if (col.editable) {
          return { ...col, headerStyle: 'editable-col' };
        } else {
          return col;
        }

        // if (editableRenderers.includes(col.renderer)) {
        // }
      });
    },
  },
};
</script>

<style lang="scss" scope>
.auigrid-container {
  position: relative;
}

html {
  .aui-grid-context-popup-layer {
    z-index: 9980 !important;
  }
}

.aui-grid-button-renderer {
  padding: 5px;
}
</style>
