<template>
  <div class="pb-3">
    <h1 class="text-center pt-5">Pizze</h1>
    <div class="container text-center">
      <div v-if="!create_search">
        <button @click="create_search = true" class="btn btn-primary">Ricerca</button>  
      </div>

      <div v-else>
        <input class="form-control" type="text" v-model.trim="name" @keyup.enter="search()">
        <div class="d-flex justify-content-around pt-3">
          <button class="btn btn-success" @click="search()">Cerca</button>
          <button class="btn btn-danger" @click="create_search = false">Annulla</button>
        </div>
      </div>
    </div>

    <div v-if="pizze.length > 0">
      <div class="text-center pt-5 pb-5" v-if="!pizza_create_form">
        <button class="btn btn-primary" @click="pizza_create_form = true">
          Aggiungi una nuova pizza
        </button>
      </div>
      <div class="pt-3 container" v-else>
        <div class="row">
          <div class="col">
            <form @submit="createPizza">
              <h2 class="text-center pt-4">Crea la tua nuova pizza</h2>
              <label for="name">Nome</label>
              <input class="form-control" type="text" name="name" v-model="pizza_create.name" /><br />

              <label for="img">Immagine</label>
              <input class="form-control" type="text" name="img" v-model="pizza_create.img" /><br />

              <label for="description">Descrizione</label>
              <input class="form-control" type="text" name="description" v-model="pizza_create.description"/><br />

              <label for="price">Prezzo</label>
              <input class="form-control" type="number" name="price" v-model="pizza_create.price" /><br />

              <div class="d-flex justify-content-around">
                <button class="btn btn-success" type="submit">Crea</button>
              <button class="btn btn-danger" @click="pizza_create_form=false">Annulla</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="text-center pt-5" v-for="pizza in pizze" :key="pizza.id">
        <img :src="pizza.img" alt="img" class="w-25" /><br />

        <div v-if="pizza_id != pizza.id">
          <h2>{{ pizza.name }}, prezzo: {{ pizza.price }} €</h2>
          <h4>Descrizione: {{ pizza.description }}</h4>
          <div v-if="pizza.ingredients">
            <h3 class="pt-2 text-center">Ingredienti:</h3>
            <div class="pb-2" v-if="pizza.ingredients.length > 0">
              <h4 v-for="ingredient in pizza.ingredients" :key="ingredient.id">
                {{ ingredient.name }}
              </h4>
            </div>
            <h4 class="pb-2" v-else>Non sono presenti ingredienti</h4>
          </div>
          <button class="btn btn-warning mx-1" @click="edit(pizza.id)">Modifica</button>
          <button class="btn btn-danger mx-1" @click="deleted(pizza.id)">Elimina</button>
        </div>
      
        <div v-else>
          <form class="text-start container" @submit="editPizza">
            <label for="name">Nome</label>
            <input class="form-control" type="text" name="name" v-model="pizza.name" /><br />

            <label for="img">Immagine</label>
            <input class="form-control" type="text" name="img" v-model="pizza.img" /><br />

            <label for="description">Descrizione</label>
            <input class="form-control" type="text" name="description" v-model="pizza.description"/><br />

            <label for="price">Prezzo</label>
            <input class="form-control" type="number" name="price" v-model="pizza.price"/><br />

            <div class="d-flex justify-content-around">
              <button class="btn btn-success" type="submit">Aggiorna</button>
              <button class="btn btn-danger" @click="edit(-1)">Annulla</button>
            </div>
          </form>
        </div>
        
        
      </div>
    </div>
    <h4 class="text-center pt-3" v-else>La ricerca non ha prodotto risultati</h4>
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
      create_search: false,
      name: ''
    };
  },

  methods: {

    getPizze(){
      axios.get(API_URL + "/all").then((result) => {
        const pizze = result.data;

        if (pizze == null) return;

        this.pizze = pizze;
     });
    },

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
    },

    search(){
      if(this.name === '') return this.getPizze();
      axios.get(API_URL + '/search/' + this.name).then(result => {
        const pizze = result.data;

        if(pizze == null) return;

        this.pizze = pizze;
        console.log(this.pizze);
      })
    }
  },

  created() {
    this.getPizze();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
</style>
