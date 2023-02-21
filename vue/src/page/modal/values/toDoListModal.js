import { ColumnBuilder } from '@/util/builder';

const list = {
  static: {
    $grid: null,
    countPerRow: 4,
  },
  columns: () => ColumnBuilder.builder().col('idx').col('toDoList', '할 일').build(),
};

export default {
  list,
};
