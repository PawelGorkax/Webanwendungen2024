<template>
  <div>
    <header class="header">
      <div class="nav-item">
        <img class="icon" src="./assets/icon.png" alt="Icon" />
        <router-link class="index" to="/benutzer">Benutzer</router-link>
      </div>
      <div class="nav-item">
        <img class="icon" src="./assets/ort.png" alt="Ort Icon" />
        <router-link class="index" to="/ort">Orte</router-link>
      </div>
      <div class="nav-item">
        <img class="icon" src="./assets/city-tour.png" alt="Tour Icon" />
        <router-link class="index" to="/touren">Touren</router-link>
      </div>
    </header>

    <main>
      <div v-if="info" class="info-box">
        <div class="info-box-head">
          <h3>Achtung!</h3>
          <button @click="loescheInfo">x</button>
        </div>
        <p>{{ info }}</p>
      </div>

      
      <router-view></router-view>
    </main>

    <footer class="footer">
      <div class="wrapper">
        <ul class="liste">
          <li class="liste-item"><a href="#" class="link">Impressum</a></li>
          <li class="liste-item"><a href="#" class="link">Kontakt</a></li>
        </ul>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { useInfo } from '@/composables/useInfo';

const { info, loescheInfo } = useInfo();
</script>

<style scoped>
@import './assets/main.css';

.info-box {
  background-color: #f8d7da;
  color: #721c24;
  
  margin: 20px 0;
  border: 1px solid #f5c6cb;
  border-radius: 4px;
}

.content{
  padding: 0px 10px 0px 0px;
}

.info-box-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f5c6cb;
  padding: 10px;
  border-bottom: 1px solid #f5c6cb;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}

.info-box button {
  background: transparent;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  font-size: 1.5rem;
  font-weight: bold;
  color: #721c24;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

button {
  border-radius: 5px;
  margin-top: 1rem;
  margin-right: 1rem;
  margin-bottom: 1rem;
  padding: 0.5rem 1rem;
  background-color: #c9c9c9;
  color: rgb(0, 0, 0);
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #555;
}

p {
  margin-left: 1rem;
}
</style>
