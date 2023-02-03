<template>
  <div class="menu">
    <ul class="menu__list">
      <li v-for="menu in computedMenus" :key="menu.path" class="menu__item">
        <div
          :class="['menu__label', menu.isOpen && 'open', isMatchedRoute(menu.path) && 'selected']"
          @click="toggle(menu.path)"
        >
          <h5>
            <a>{{ menu.meta?.menu?.title ?? menu.name }}</a>
          </h5>
        </div>

        <template v-if="menu.children">
          <MenuLeaf :open="menu.isOpen" :parent="menu" :routes="menu.children" />
        </template>
      </li>
    </ul>
  </div>
</template>

<script>
import MenuLeaf from './MenuLeaf.vue';

export default {
  name: 'Menu',
  components: {
    MenuLeaf,
  },
  props: {
    routes: {
      type: Array,
      default: () => [],
    },
  },
  created() {
    this.setDefaultOpenMenu();
  },
  watch: {
    $route(next, prev) {
      if (prev) {
        this.closeMenu(this.getRootPath(prev.path));
      }
      if (next) {
        this.openMenu(this.getRootPath(next.path));
      }
    },
  },
  data() {
    return {
      menus: null,
      openMenus: new window.Set(),
    };
  },
  methods: {
    setDefaultOpenMenu() {
      const routes = this.$props.routes;
      for (let i = 0; i < routes.length; i++) {
        if (this.isMatchedRoute(routes[i].path)) {
          this.openMenu(routes[i].path);
          break;
        }
      }
    },
    toggle(path) {
      if (this.isOpened(path)) {
        this.closeMenu(path);
      } else {
        this.clearOpenMenus();
        this.openMenu(path);
      }
    },
    isOpened(path) {
      return this.openMenus.has(path);
    },
    openMenu(path) {
      this.openMenus.add(path);
    },
    closeMenu(path) {
      this.openMenus.delete(path);
    },
    isMatchedRoute(path) {
      return this.$route.matched.some((route) => route.path === path);
    },
    clearOpenMenus() {
      this.openMenus.clear();
    },
    getRootPath(path) {
      return `/${path.split('/')[1]}`;
    },
  },
  computed: {
    computedMenus() {
      return this.$props.routes.map((route) => {
        route.isOpen = this.isOpened(route.path);
        return route;
      });
    },
  },
};
</script>

<style lang="scss" scope>
.menu {
  $menu-item-height: 48px;
  background: #fff;

  overflow: scroll;
  position: relative;
  height: calc(100vh - 128px);

  &::-webkit-scrollbar {
    display: none;
  }

  &__item {
    cursor: pointer;
    position: relative;
    width: 100%;
    margin-bottom: 5px;
    line-height: $menu-item-height;
  }

  &__label {
    padding: 0 20px;
    font-weight: 500;
    color: $menu-color-font;

    &:hover {
      background-color: $menu-color-bg-depth-1;
      transition: background-color 0.1s ease-out;
    }

    &.selected {
      transition: color 0.1s ease-out;
    }

    &::after {
      content: '';
      position: absolute;
      top: calc($menu-item-height - 26px);
      right: 15px;
      width: 7px;
      height: 7px;
      border-top: 2px solid RGB(130, 130, 130);
      border-right: 2px solid RGB(130, 130, 130);
      transform: rotate(135deg);
      opacity: 0;
      transition: transform 0.2s linear, opacity 0.2s linear;
    }

    &.open::after {
      opacity: 1;
      transform: rotate(-45deg);
    }
  }
}
</style>
