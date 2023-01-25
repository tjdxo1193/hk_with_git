<template>
  <div class="side-navigation-layout">
    <nav class="side-navigation-layout__navigation">
      <keep-alive>
        <SideNavigation />
      </keep-alive>
    </nav>

    <div id="layout-body" class="side-navigation-layout__body">
      <header class="side-navigation-layout__header">
        <keep-alive>
          <Header />
        </keep-alive>
      </header>

      <main class="side-navigation-layout__content">
        <router-view :key="$route.fullPath"></router-view>
      </main>
    </div>
  </div>
</template>

<script>
import { Header, SideNavigation } from '../navigation';

export default {
  name: 'LayoutWithSideNavigation',
  components: {
    Header,
    SideNavigation,
  },
};
</script>

<style lang="scss" scoped>
.side-navigation-layout {
  background-color: rgb(243, 247, 250);
  width: 100%;
  min-height: 100vh;
  display: grid;
  grid-template-columns: $layout-side-width 1fr;

  &__header {
    z-index: 2;
    position: fixed;
    width: 100%;
  }

  &__navigation {
    z-index: 3;
    height: 100%;
    grid-column: 1 / 2;
  }

  &__body {
    z-index: 3;
    height: 100%;
    grid-column: 2 / 6;
  }

  &__content {
    padding: 98px 10px 10px;
  }
}
</style>
