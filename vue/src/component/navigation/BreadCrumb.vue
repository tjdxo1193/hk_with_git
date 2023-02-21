<template>
  <div class="breadcrumb">
    <h5><router-link to="/home">Home</router-link></h5>
    <template v-for="name in matchedRouteNames" :key="name">
      <span v-if="name">
        <span class="breadcrumb__seperator">/</span>
        <a>{{ name }}</a>
      </span>
    </template>
  </div>
</template>

<script>
export default {
  name: 'Breadcrumb',
  computed: {
    matchedRouteNames() {
      return this.$route.matched.map(({ meta }) => meta?.menu?.title);
    },
  },
};
</script>

<style lang="scss" scope>
.breadcrumb {
  display: flex;

  &__seperator {
    margin: 0 8px;
  }

  & a {
    cursor: pointer;
  }

  & a:hover {
    color: $common-color-hover-orange;
    transition: color 0.1s ease-out;
  }
}
</style>
