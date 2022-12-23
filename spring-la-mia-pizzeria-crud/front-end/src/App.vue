<template>
  <div id="app">
    <HeaderComponent/>
    <PizzaComponent/>
  </div>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue'
import PizzaComponent from './components/PizzaComponent.vue'

export default {
  name: 'App',
  components: {
    HeaderComponent,
    PizzaComponent
  }
}
</script>

<style lang="scss">

</style>
