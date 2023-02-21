<template>
  <article class="tab-base">
    <ul class="tab-menu tab-base__header">
      <div>
        <template v-for="tab in tabs" :key="tab.name">
          <li
            :name="tab.name"
            :class="[tab.name === currentTabName && 'active']"
            @click="onChangeTab(tab)"
          >
            {{ tab.label }}
          </li>
        </template>
      </div>

      <div>
        <template v-if="existsCommonButtons">
          <ButtonBase
            v-for="(commonButton, index) in buttons"
            :key="commonButton.name ?? index"
            v-bind="commonButton"
            @click="onClickCommonButton(commonButton.name)"
          >
            {{ commonButton.label }}
          </ButtonBase>
        </template>
      </div>
    </ul>
    <div class="tab-content">
      <template v-for="tab in tabs" :key="tab.name">
        <div v-show="tab.name === currentTabName">
          <div class="tab-base__body-button-wrap">
            <template v-if="tab.buttons">
              <ButtonBase
                v-for="(button, index) in tab.buttons"
                :key="button.name ?? index"
                v-bind="button"
                @click="onClickButton(tab.name, button.name)"
              >
                {{ button.label }}
              </ButtonBase>
            </template>
          </div>

          <div>
            <slot :name="`tab-${tab.name}`"></slot>
          </div>
        </div>
      </template>
    </div>
  </article>
</template>

<script>
export default {
  name: 'TabBase',
  emits: ['change', 'button-click', 'common-button-click'],
  inheritAttrs: false,
  props: {
    tabs: {
      type: Array,
      default: () => [],
      meta: `[{
        name: 'tab name',
        label: 'name',
        selected: true,
        buttons: [],
      }]`,
    },
    buttons: {
      type: Array,
      default: () => [],
    },
  },
  created() {
    this.setDefaultTabName(this.$props.tabs);
  },
  mounted() {
    this.resize();
  },
  methods: {
    setDefaultTabName() {
      const defaultSelectedTab = this.$props.tabs.find(({ selected }) => selected);
      if (defaultSelectedTab == null) {
        this.$props.tabs[0].selected = true;
      }
    },
    onChangeTab(tab) {
      this.resize();
      this.selectTab(tab);
      this.$emit('change', { tab });
    },
    onClickButton(tabName, buttonName) {
      this.$emit('button-click', { tabName, name: buttonName });
    },
    onClickCommonButton(buttonName) {
      this.$emit('common-button-click', { name: buttonName });
    },
    selectTab({ name: selectedTabName }) {
      this.$props.tabs.forEach((tab) => {
        tab.selected = false;

        if (tab.name === selectedTabName) {
          tab.selected = true;
        }
      });
    },
    async resize() {
      await this.$nextTick();
      window.dispatchEvent(new CustomEvent('resize'));
    },
  },
  computed: {
    currentTabName() {
      return this.$props.tabs.find(({ selected }) => selected)?.name;
    },
    existsCommonButtons() {
      return this.$props.buttons != null && this.$props.buttons.length > 0;
    },
  },
};
</script>

<style lang="scss" scope>
.tab-base {
  &__header {
    display: flex;
    justify-content: space-between;
  }

  &__body {
    &-button-wrap {
      display: flex;
      justify-content: end;
      padding-right: 15px;
      margin-bottom: 10px;
    }
  }
}
</style>
