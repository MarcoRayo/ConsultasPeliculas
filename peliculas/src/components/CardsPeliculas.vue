<script scope>
import axios from 'axios';
export default {
  data() {
    return {
      peliculas: null,
      pelicula: {
        "anio": '',
        "descripcion": '',
        "genero": '',
        "imagen": '',
        "nombre": '',
        'director': '',
        'fecha': ''
      },
      categories: [{ text: 'Selecciona Una', value: null }, 'Drama', 'Terror', 'Romance', 'Suspenso'],
      urlImagen: "",
    }
  },
  mounted() {
    this.created();
  },
  methods: {
    created() {
      this.buscar = null;
      axios
        .get("http://localhost:8080/api/peliculas")
        .then((response) => {
          this.peliculas = response.data;
          this.urlImagen = require(this.pelicula.imagen);
          console.log(this.urlImagen);
        })
        .catch((error) => {
          console.log("There was an error: " + error);
        });
    },
  }
}
</script>

<template>
  <div class="container row mx-auto mt-4">
    <transition-group name="zoom" tag="div" class="d-flex flex-row flex-wrap">
      <div class="col-md-4" v-for="pelicula in peliculas" :key="pelicula.id">
        <b-card :title="pelicula.nombre" :img-src="pelicula.imagen" img-alt="Image" img-top tag="article"
          style="max-width: 20rem; max-height: 30rem;" class="mb-2 mt-2 text-center">
          <b-card-text>
            <h6>Descripción: {{ pelicula.descripcion }}</h6>
            Fecha: {{ pelicula.fechaPublicacion }}
          </b-card-text>
        </b-card>
      </div>
    </transition-group>
  </div>
</template>

<style scoped></style>
