<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {}
}
</script>

<style>
#app {
  font-family: element-icons;
  src: url(./theme/fonts/element-icons.woff) format('woff'),
    url(./theme/fonts/element-icons.ttf) format('truetype');
  font-weight: 400;
  font-display: 'auto';
  font-style: normal;
  color: #303133;
}
</style>
