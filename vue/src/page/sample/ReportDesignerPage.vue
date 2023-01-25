<template>
  <button @click="openReport">HTML5 Viewer (Single)</button>
  <button @click="openReportMultiple">HTML5 Viewer (Multi)</button>
</template>

<script>
import { RdUtil } from '@/util';

export default {
  name: 'ReportDesignerSamplePage',
  methods: {
    openReport() {
      RdUtil.openReport(
        '/ARTICLE_USE_STATUS.mrd',
        `/rp [${100026}] [${'2022-01-15'}] [${'2022-02-14'}] ['RS01000001', 'RS01000002']`,
      );
    },
    openReportMultiple() {
      RdUtil.openReports(
        ['/ARTICLE_USE_STATUS.mrd', '/ARTICLE_USE_STATUS.mrd', '/ARTICLE_USE_STATUS.mrd'],
        [
          `/rp [${100026}] [${'2022-01-15'}] [${'2022-02-14'}] ['RS01000001', 'RS01000002']`,
          `/rp [${100026}] [${'2022-01-15'}] [${'2022-02-14'}] ['RS01000001', 'RS01000002']`,
          `/rp [${100026}] [${'2022-01-15'}] [${'2022-02-14'}] ['RS01000001', 'RS01000002']`,
        ],
      );
    },
  },
};
</script>
