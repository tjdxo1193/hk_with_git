<template>
  <Card>
    <TabBase :tabs="tabs" @change="onChangeTab">
      <template #tab-auigrid>
        <AUIGridPage />
      </template>

      <template #tab-auigridMultiple>
        <AUIGridMultiplePage />
      </template>
    </TabBase>
  </Card>
</template>

<script>
import AUIGridMultiplePage from './AUIGridMultiplePage.vue';
import AUIGridPage from './AUIGridPage.vue';
import values from './values/tab';

export default {
  name: 'TabSamplePage',
  components: {
    AUIGridPage,
    AUIGridMultiplePage,
  },
  data() {
    const { tabs } = this.$copy(values);

    return {
      tabs,
    };
  },
};
</script>

<style></style>
