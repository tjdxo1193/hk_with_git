<template>
  <div :class="['menu-leaf', open && 'open']" :style="styleVariable">
    <ul class="menu-leaf__list">
      <li v-for="menu in computedMenus" :key="menu.path" class="menu-leaf__item">
        <router-link :to="getFullPath(menu.path)" tabindex="-1">
          <div :class="['menu-leaf__label', menu.isOpen && 'selected']">
            <h5>
              {{ menu.meta?.menu?.title ?? menu.name }}
            </h5>
          </div>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MenuLeaf',
  emits: ['select'],
  props: {
    open: {
      type: Boolean,
      default: false,
    },
    routes: {
      type: Array,
      default: () => [],
    },
    parent: {
      type: Object,
      default: null,
    },
  },
  data() {
    const menuItemHeight = 42;
    return {
      styleVariable: {
        '--item-height': `${menuItemHeight}px`,
        '--total-height': `${this.routes.length ? this.routes.length * menuItemHeight : 0}px`,
      },
    };
  },
  methods: {
    getFullPath(path) {
      return `${this.$props.parent?.path ?? ''}/${path}`.replaceAll('//', '/');
    },
  },
  computed: {
    computedMenus() {
      const currentFullPath = this.$route.fullPath;
      return this.$props.routes.map((menu) => {
        menu.isOpen = currentFullPath === this.getFullPath(menu.path);
        return menu;
      });
    },
  },
};
</script>

<style lang="scss" scope>
.menu-leaf {
  position: relative;
  overflow: hidden;
  height: 0;
  transition: height 0.4s ease;
  background-color: $menu-color-bg-depth-1;

  &.open {
    height: var(--total-height);
    transition: height 0.4s ease;
  }

  &__item {
    cursor: pointer;
    position: relative;
    width: 100%;
    line-height: var(--item-height);
  }

  &__label {
    position: relative;
    padding: 0 10px 0 30px;
    font-weight: 500;
    color: $menu-color-font;

    &::after {
      content: '';
      position: absolute;
      opacity: 1;
      top: 0;
      right: 0;
      bottom: 0;
      border-right: 5px solid rgb(17, 18, 118);
      transform: scaleY(0.1);
      opacity: 0;
    }

    &.selected {
      background-color: $menu-color-bg-depth-2;
      color: rgb(70, 120, 255);
    }

    &.selected::after {
      opacity: 1;
      transform: scaleY(1);
      transition: transform 0.15s cubic-bezier(0.645, 0.045, 0.355, 1),
        opacity 0.15s cubic-bezier(0.645, 0.045, 0.355, 1);
    }

    &:hover {
      background-color: $menu-color-bg-depth-2;
      transition: background-color 0.1s ease-out;
    }

    & h5 {
      pointer-events: none;
    }
  }
}
</style>
