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
        <button @click="pizza_create_form=false">Annulla</button>
      </form>
    </div>

    <div class="text-center pt-3" v-for="pizza in pizze" :key="pizza.id">
      <img :src="pizza.img" alt="img" class="w-25" /><br />
      <div v-if="pizza_id != pizza.id">
        <span>{{ pizza.name }}, prezzo: {{ pizza.price }} €</span>
        <div v-if="pizza.ingredients">
          <div v-if="pizza.ingredients.length > 0">
            <div v-for="ingredient in pizza.ingredients" :key="ingredient.id">
              {{ ingredient.name }}
            </div>
          </div>
          <div v-else>Non sono presenti ingredienti</div>
        </div>
        <button @click="edit(pizza.id)">Modifica</button>
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

          <button type="submit">Aggiorna</button>
          <button @click="edit(-1)">Annulla</button>
        </form>
      </div>
      <button @click="deleted(pizza.id)">Elimina</button>
      <p>{{ pizza.description }}</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const API_URL = "http://localhost:8080/api/1/pizza";
const API_URL_INGREDIENT = "http://localhost:8080/api/1/ingredient";

export default {
  name: "PizzaComponent",

  data: function () {
    return {
      pizze: [],
      pizza_id: -1,
      pizza_create: {},
      pizza_create_form: false,
      pizza_edit_form: false,
    };
  },

  methods: {
    createPizza(e) {
      e.preventDefault();

      this.pizza_create_form = false;

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
        if (pizza.id === id) return i;
      }
    },

    edit(id) {
      this.pizza_id = id;
      console.log(this.pizza_id);
    },

    getPizzaById(id) {
      return this.pizze[this.getPizzaIndexById(id)];
    },

    editPizza(e) {

      e.preventDefault();

      let id = this.pizza_id;
      const pizza = this.getPizzaById(id);

      this.edit(-1);

      axios.post(API_URL + "/edit/" + id, pizza).then((result) => {
        const index = this.getPizzaIndexById(id);
        const oldPizza = this.pizze[index];

        const pizza = result.data;
        console.log(pizza);

        pizza.ingredients = oldPizza.ingredients;
        this.pizze[index] = pizza;
      });
    },

    deleted(id){
      axios.get(API_URL + '/delete/' + id).then(result => {

        const deleted = result.data;
        console.log(deleted);

        if(!deleted) return
        const index = this.getPizzaIndexById(id);
        this.pizze.splice(index, 1);
      })
    },

    getIngredients(idPizza){
      axios.get(API_URL_INGREDIENT + '/pizza/' + idPizza).then(result => {

        const ingredients = result.data;
        if(ingredients == null) return;

        const index = this.getPizzaIndexById(idPizza);
        const pizza = this.pizze[index];

        pizza.ingredients = ingredients;
        console.log(this.pizze[index].ingredients);
      })
    }
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
