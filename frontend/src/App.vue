<script setup lang="ts">
</script>

<template>
   <Toast />
  <RouterView />
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=WDXL+Lubrifont+SC&display=swap');
@import "tailwindcss";
@import "tailwindcss-primeui";
@import 'primeicons/primeicons.css';

body{
  background-color: #F1F5F9;
}


.wdxl-lubrifont-sc-regular {
  font-family: "WDXL Lubrifont SC", sans-serif !important;
  font-weight: 700;
  font-style: normal;
  font-size: 1.6rem;
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}


</style>
