<template>
  <div>
    <h1 class="text-center pt-5">Pizze</h1>
    <div class="text-center pb-5" v-if="!pizza_create_form">
      <button @click="pizza_create_form = true">
        Aggiungi una nuova pizza
      </button>
    </div>
    <div v-else>
      <form class="text-center" @submit="createPizza">
        <label for="name">Nome</label>
        <input type="text" name="name" v-model="pizza_create.name" /><br />

        <label for="img">Immagine</label>
        <input type="text" name="img" v-model="pizza_create.img" /><br />

        <label for="description">Descrizione</label>
        <input
          type="text"
          name="description"
          v-model="pizza_create.description"
        /><br />

        <label for="price">Prezzo</label>
        <input type="number" name="price" v-model="pizza_create.price" /><br />

        <button type="submit">Crea</button>
        <button @click="edit(PIZZE_EDIT_ID_DEFAULT)">Annulla</button>
      </form>
    </div>

    <div class="text-center pt-3" v-for="pizza in pizze" :key="pizza.id">
      <img :src="pizza.img" alt="img" class="w-25" /><br />
      <span>{{ pizza.name }}, prezzo: {{ pizza.price }} €</span><br />
      <div v-if="!pizza_edit_form">
        <button @click="pizza_edit_form = true">Modifica la tua pizza</button>
      </div>
      <div v-else>
        <form class="text-center" @submit="editPizza">
          <label for="name">Nome</label>
          <input type="text" name="name" v-model="pizza.name" /><br />

          <label for="img">Immagine</label>
          <input type="text" name="img" v-model="pizza.img" /><br />

          <label for="description">Descrizione</label>
          <input
            type="text"
            name="description"
            v-model="pizza.description"
          /><br />

          <label for="price">Prezzo</label>
          <input
            type="number"
            name="price"
            v-model="pizza.price"
          /><br />

          <button type="submit">Modifica</button>
        </form>
      </div>
      <button>Elimina</button>
      <p>{{ pizza.description }}</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const API_URL = "http://localhost:8080/api/1/pizza";
const PIZZE_EDIT_ID_DEFAULT = -1;

export default {
  name: "PizzaComponent",

  data: function () {
    return {
      pizze: [],
      pizze_edit_id: PIZZE_EDIT_ID_DEFAULT,
      pizza_create: {},
      pizza_create_form: false,
      pizza_edit_form: false,
    };
  },

  methods: {
    createPizza(e) {
      e.preventDefault();

      axios.post(API_URL + "/create", this.pizza_create).then((result) => {
        console.log(JSON.stringify(result.data));

        const pizza = result.data;

        if (pizza == null) return;

        this.pizze.push(pizza);

        console.log(this.pizze);
      });
    },

    getPizzaIndexById(id) {
      for (let i = 0; i < this.pizze.length; i++) {
        const pizza = this.pizze[i];
        if (pizza.id == id) return i;

        return -1;
      }
    },

    edit(id) {
      this.pizze_edit_id = id;
      console.log(this.pizze_edit_id);
    },

    getPizzaById(id) {
      return this.pizze[this.getPizzaIndexById(id)];
    },

    editPizza(e) {
      e.preventDefault();

      const id = this.pizze_edit_id;
      const pizza = this.getPizzaById(id);

      this.edit(PIZZE_EDIT_ID_DEFAULT);

      axios.post(API_URL + "/edit/" + id, pizza).then((result) => {
        const index = this.getPizzaIndexById(id);
        const oldPizza = this.pizze[index];

        const pizza = result.data;

        pizza.ingredients = oldPizza.ingredients;
        this.pizze[index] = pizza;
      });
    },
  },

  created() {
    axios.get(API_URL + "/all").then((result) => {
      const pizze = result.data;

      if (pizze == null) return;

      this.pizze = pizze;
    });
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
</style>
