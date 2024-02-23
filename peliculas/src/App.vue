<script scope>
import axios from 'axios';
import Form from './components/Form.vue';
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
      urlImagen: "",
      buscar: "",
      buscarFecha: {
        "primera": null,
        "segunda": null
      },
      showElement: true,
      lastScrollPosition: 0,
      items: [
        {
          id: 0,
          title: "Item A",
          list: 1,
        },
        {
          id: 1,
          title: "Item B",
          list: 1,
        },
        {
          id: 2,
          title: "Item C",
          list: 2,
        },
        {
          id: 3,
          title: "Item D",
          list: 2,
        }
      ],
    }
  },
  mounted() {
    this.created();
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
  computed: {
    listOne() {
      return this.items.filter((item) => item.list === 1);
    },
    listTwo() {
      return this.items.filter((item) => item.list === 2);
    },
  },
  methods: {
    startDrag(evt, item) {
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
      evt.dataTransfer.setData("itemID", item.id);
    },
    onDrop(evt, list) {
      const itemID = evt.dataTransfer.getData("itemID");
      const item = this.items.find((item) => item.id == itemID);
      item.list = list;
    },
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
    search(search) {
      axios
        .get(`http://localhost:8080/api/peliculas/buscarPeliculas?nombre=${this.buscar}`)
        .then((response) => {
          this.peliculas = response.data; ``
        })
        .catch((error) => {
          console.log("There was an error: " + error);
        });
    },
    searchDate(searchOne, searchTwo) {
      axios
        .get(`http://localhost:8080/api/peliculas/searchDateRange?dateOne=${this.buscarFecha.primera}&dateTwo=${this.buscarFecha.segunda}`)
        .then((response) => {
          this.peliculas = response.data;
        })
        .catch((error) => {
          console.log("There was an error: " + error);
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
      this.pelicula.imagen = e.target.files[0];
    },
    onScroll() {
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;      
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 70) {
        return;
      }      
      this.showElement = currentScrollPosition < this.lastScrollPosition;      
      this.lastScrollPosition = currentScrollPosition;
    },
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
    </header>
    <div>
      <div class="drop-zone" @drop="onDrop($event, 1)" @dragover.prevent @dragenter.prevent>
        <div v-for="item in listOne" :key="item.title" class="drag-el" draggable @dragstart="startDrag($event, item)">
          {{ item.title }}
        </div>
        <div class="drop-zone" @drop="onDrop($event, 2)" @dragover.prevent @dragenter.prevent>
          <div v-for="item in listTwo" :key="item.title" class="drag-el" draggable @dragstart="startDrag($event, item)">
            <Form></Form>
          </div>
        </div>
      </div>
    </div>
    <div>
      <div>
        <div class="col-md-4 mx-auto" style="margin-top: 1rem;" v-show="showElement">
          <div class="row">
            <div class="col-md-9 mx-auto" style="margin-left: 3rem;">
              <b-form-input name="nombre" v-model="buscar" required></b-form-input>
            </div>
            <div class="col-md-2 mx-auto">
              <b-button size="sm" class="btn btn-secondary" @click="search"
                style="margin-right: 0.5rem;">Buscar</b-button>
            </div>
          </div>
          <div class="row">
            <div class="col-md-8 mx-auto" style="padding-top: 0.5rem;">
              <h5 style="padding-left: 5rem;">Buscar entre dos fechas:</h5>
            </div>
            <div class="row">
              <div class="col-md-10 mx-auto">
                <label for="fechaUno" style="padding-right: 0.5rem; padding-left: 2.5rem;">De:</label>
                <input type="date" v-model="buscarFecha.primera">
                <label for="fechaUno" style="padding-left: 0.5rem; padding-right: 0.5rem;">Hasta:</label>
                <input type="date" v-model="buscarFecha.segunda">
              </div>
            </div>
            <div class="row">
              <div class="col-md-6 mx-auto" style="padding-top: 0.5rem;">
                <div class="row">
                  <div class="col">
                    <b-button size="sm" class="btn btn-secondary" type="submit" @click="searchDate">Buscar por
                      fecha</b-button>
                  </div>
                  <div class="col">
                    <b-button size="sm" class="btn btn-secondary" type="submit" @click="created">Quitar Filtros</b-button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <b-modal id="modal-1" title="Agregar Pelicula" hide-footer="true">
          <b-form @submit="onSubmit" @reset="onReset">

            <b-form-group id="input-group-name" label="Nombre:" label-for="input-name">
              <b-form-input id="input-name" v-model="pelicula.nombre" type="text" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-description" label="Descripción:" label-for="input-description">
              <b-form-input id="input-description" v-model="pelicula.descripcion" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-category" label="Genero:" label-for="input-category">
              <b-form-select id="input-category" v-model="pelicula.genero" :options="categories" placeholder="Seleccione"
                class="btn col-md-12 border" required></b-form-select>
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
  </div>
</template>

<style >
.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
}

.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}

.zoom-enter-active,
.zoom-leave-active {
  transition: transform 0.5s ease-in-out;
}

.zoom-enter,
.zoom-leave-to {
  transform: scale(0);
}

.hidden {
  display: none;
}
</style>
