<template>
  <div class="left_menu">
    <div class="left_menu-head">Menu List</div>
    <Menu :routes="filteredMenus" class="left_menu-body" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

import { getterType } from '@/const';
import { menus } from '@/router';
import { ObjectUtil, TokenUtil } from '@/util';

import Menu from './Menu.vue';

export default {
  name: 'SideNavigation',
  components: {
    Menu,
  },
  data() {
    return {
      menus,
    };
  },
  methods: {
    getVisibleMenus() {
      return menus.filter((route) => this.existsInServerMenu(route));
    },
    existsInServerMenu(route) {
      const code = route.meta?.menu?.code;
      return ObjectUtil.has(this.menuInfo, code);
    },
    filterMenus(menus, conditionFn) {
      return menus
        .map((menu) => {
          return {
            ...menu,
            children: menu.children ? menu.children.filter(conditionFn) : [],
          };
        })
        .filter((menu) => menu.children.length > 0);
    },
  },
  computed: {
    ...mapGetters({
      menuInfo: getterType.GET_MENU_INFOS_AS_OBJECT,
    }),
    myMenus() {
      return TokenUtil.myMenus();
    },
    filteredMenus() {
      const myMenus = this.myMenus;
      const visibleMenus = this.getVisibleMenus();
      return this.filterMenus(
        visibleMenus,
        (route) => this.existsInServerMenu(route) && myMenus.includes(route.meta?.menu?.code),
      );
    },
  },
};
</script>

<style lang="scss" scope>
.left_menu {
  width: $layout-side-width;
  height: 100%;
  position: fixed;
  top: 88px;
  left: 0;

  &-head {
    display: flex;
    align-items: center;
    padding-left: 20px;
    width: 100%;
    height: 40px;
    font-weight: 900;
    color: rgb(90, 90, 90);
    background-color: rgb(222, 222, 222);
  }

  &-body {
    border-right: 1px solid rgb(222, 222, 222);
  }
}
</style>
