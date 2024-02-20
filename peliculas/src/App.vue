<script scope>
import axios from 'axios';
export default {
  data() {
    return {
      peliculas: null,
      rs: '',
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
      urlImagen: ""
    }
  },
  mounted() {
    this.created()
  },
  methods: {
    created() {
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
    onSubmit(event) {
      const formData = new FormData();
      formData.append("anio", this.pelicula.anio);
      formData.append("descripcion", this.pelicula.descripcion);
      formData.append("genero", this.pelicula.genero);
      formData.append("imagen", this.pelicula.imagen);
      formData.append("nombre", this.pelicula.nombre);
      formData.append("director", this.pelicula.director)
      formData.append("fecha", this.pelicula.fecha)
      console.log(formData)
      axios.post('http://localhost:8080/api/peliculas/registrar-imagen', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      })
        .then(response => {
          mounted()
        })
        .catch(error => {
          console.error('Error en el registro:', error);
          // Puedes manejar errores y mostrar mensajes de error al usuario
        });
    },
    onReset(e) {
      e.preventDefault()
      this.pelicula.descripcion = ''
      this.pelicula.nombre = ''
      this.pelicula.genero = ''
      this.pelicula.anio = ''
      this.pelicula.imagen = ''
      this.pelicula.director = ''
      this.pelicula.fecha = null
    },
    img(e) {
      console.log(e.target.files[0])
      this.pelicula.imagen = e.target.files[0];
    }
  }
}
</script>

<template>
  <div id="app">
    <header>
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
          <b-navbar-brand style="margin-left: 1rem;">Peliculas</b-navbar-brand>          
          <div class="ml-auto">
            <b-button v-b-modal.modal-1 class="btn btn-success float-right" size="sm">Agregar
              Nueva</b-button>
          </div>
        </div>
      </nav>
      <div>
        <div>
          <div class="col-md-8 mx-auto" style="margin-top: 1rem; ">
            <div class="row rounded mx-auto" style="padding: 0.5rem;">
              <div class="col-md-5" style="margin-left: 1rem;">
                <b-form-input size="sm" placeholder="Buscar..."></b-form-input>
              </div>
              <div class="col-md-2">
                <b-dropdown text="Por" variant="secondary" size="sm">
                  <b-dropdown-item>Genero</b-dropdown-item>
                  <b-dropdown-item>Nombre</b-dropdown-item>
                </b-dropdown>
                <b-button size="sm" class="btn btn-secondary" type="submit" style="margin-right: 0.5rem;">Buscar</b-button>
              </div>
            </div>
          </div>
          <b-modal id="modal-1" title="Agregar Pelicula">
            <b-form @submit="onSubmit" @reset="onReset">

              <b-form-group id="input-group-name" label="Nombre:" label-for="input-name">
                <b-form-input id="input-name" v-model="pelicula.nombre" type="text" required></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-description" label="Descripción:" label-for="input-description">
                <b-form-input id="input-description" v-model="pelicula.descripcion" required></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-category" label="Genero:" label-for="input-category">
                <b-form-select id="input-category" v-model="pelicula.genero" :options="categories"
                  placeholder="Seleccione" class="btn col-md-12 border" required></b-form-select>
              </b-form-group>

              <b-form-group id="input-group-anio" label="Año:" label-for="input-anio">
                <b-form-input id="input-anio" v-model.number="pelicula.anio" type="number" required></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-director" label="Director:" label-for="input-director">
                <b-form-input id="input-director" v-model="pelicula.director" required></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-fecha" label="Fecha de publicación:" label-for="input-fecha">
                <b-form-input id="input-fecha" v-model="pelicula.fecha" type="date" required></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-image" label="Imagen:" label-for="input-image">
                <input accept="image/png,image/jpeg" type="file" class="form-control" id="input-image" @change="img"
                  required>
              </b-form-group>

              <div class="col-5 mx-auto container mt-3">
                <b-button type="submit" variant="success">Aceptar</b-button>
                <b-button type="reset" variant="danger" style="margin-left: 0.5rem;">Borrar</b-button>
              </div>
            </b-form>
          </b-modal>
        </div>

      </div>
    </header>
    <div class="container row mx-auto mt-4">
      <div class="col-md-4" v-for="pelicula in peliculas" :key="pelicula.id">
        <b-card :title="pelicula.nombre" :img-src="pelicula.imagen" img-alt="Image" img-top tag="article"
          style="max-width: 20rem; max-height: 30rem;" class="mb-2 mt-2">
          <b-card-text>
            Descripción: {{ pelicula.descripcion }},
            Año: {{ pelicula.anio }}
          </b-card-text>
        </b-card>
      </div>
    </div>
    <div>
      {{ rs }}
    </div>
  </div>
</template>

<style scoped></style>
