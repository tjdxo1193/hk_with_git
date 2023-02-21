<template>
  <article>
    <section>
      <AUIGridSearch
        v-bind="audit"
        @grid-created="(proxy) => $setState('audit.$grid', proxy)"
        @button-click="onClickOnSearch"
      />
    </section>

    <section class="audit-trail-diff-wrapper">
      <Horizontal>
        <Card title="변경 전" height="380px">
          <pre v-html="selectedBeforeData"></pre>
        </Card>

        <Card title="변경 후" height="380px">
          <pre v-html="selectedAfterData"></pre>
        </Card>
      </Horizontal>
    </section>
  </article>
</template>

<script>
import values from './values/auditTrail';

export default {
  name: 'AuditTrail',
  data() {
    const { audit } = this.$copy(values);
    return {
      audit: {
        ...audit.static,
        forms: audit.forms(),
        state: {
          selectedAudit: null,
        },
        columns: audit.columns(),
        event: {
          cellDoubleClick: (e) => this.selectAudit(e.item),
        },
      },
    };
  },
  methods: {
    onClickOnSearch({ name }) {
      if (name === 'search') {
        this.getAudits();
      }
    },
    async getAudits() {
      // TODO 메뉴 코드 생성되면 Routes에 메뉴 코드를 설정하고 검색 조건 로직 구현

      this.clearAuditState();
      const { data } = await this.audit.$grid._useLoader(() => this.$axios.get('/sys/auditTrail'));
      this.audit.$grid.changeColumnLayout(data.headerDataFields);
      this.audit.$grid.setGridData(data.data);
    },
    selectAudit(audit) {
      const beforeData = audit.CHG_BEF ? JSON.parse(audit.CHG_BEF) : null;
      const afterData = audit.CHG_AFT ? JSON.parse(audit.CHG_AFT) : null;

      this.audit.state.selectedAudit = audit;
      this.audit.state.selectedAudit.beforeData = this.markDiffColumns(beforeData, afterData);
      this.audit.state.selectedAudit.afterData = this.markDiffColumns(afterData, beforeData);
    },
    markDiffColumns(target, compare) {
      const targetValues = Object.values(target ?? {});
      const comparValues = Object.values(compare ?? {});

      if (targetValues.length === 0) {
        return 0;
      }

      if (comparValues.length === 0) {
        return targetValues.reduce((acc, v) => {
          acc[v.columnComment] = `<span class=diff>${v.data}</span>`;
          return acc;
        }, {});
      }

      return targetValues.reduce((acc, v, index) => {
        if (v.data != comparValues[index].data) {
          acc[v.columnComment] = `<span class=diff>${v.data}</span>`;
        } else {
          acc[v.columnComment] = `<span>${v.data}</span>`;
        }
        return acc;
      }, {});
    },
    clearAuditState() {
      this.audit.state.selectedAudit = null;
    },
    removeObsoleteChar(str) {
      return `  ${str.replace(/\{|\}|"/g, '').trim()}`;
    },
  },
  computed: {
    selectedBeforeData() {
      const beforeData = this.audit.state.selectedAudit?.beforeData;
      return beforeData ? this.removeObsoleteChar(JSON.stringify(beforeData, null, 2)) : null;
    },
    selectedAfterData() {
      const afterData = this.audit.state.selectedAudit?.afterData;
      return afterData ? this.removeObsoleteChar(JSON.stringify(afterData, null, 2)) : null;
    },
  },
};
</script>

<style lang="scss" scope>
.audit-trail {
  &-diff-wrapper {
    & pre {
      line-height: 20px;
    }

    & span {
      &.diff {
        color: rgb(255, 0, 0);
      }
    }
  }
}
</style>
