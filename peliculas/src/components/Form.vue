<script scope>

</script>

<template>
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
            <input accept="image/png,image/jpeg" type="file" class="form-control" id="input-image" @change="img" required>
        </b-form-group>

        <div class="col-5 mx-auto container mt-3">
            <b-button type="submit" variant="success">Aceptar</b-button>
            <b-button type="reset" variant="danger" style="margin-left: 0.5rem;">Borrar</b-button>
        </div>
    </b-form>
</template>

<style></style>