<template>
  <h1 class="title">Das aktuelle Mitfahrangebot</h1>
  <div class="search-container">
    <input v-model="searchQuery" type="text" placeholder="Suche nach Start- oder Zielort" />
    <button @click="resetSearch">Reset</button>
  </div>
  <TourenListe :touren="filteredTouren"></TourenListe>
  <button @click="updateTourListe">Daten ändern</button>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import TourenListe from '@/components/tour/TourenListe.vue';
import { useTourenStore } from '@/stores/tourenstore';

const { tourdata, updateTourListe } = useTourenStore();

const searchQuery = ref('');

const filteredTouren = computed(() => {
  if (!searchQuery.value) {
    return tourdata.tourliste;
  }
  const query = searchQuery.value.toLowerCase();
  return tourdata.tourliste.filter(tour =>
    tour.startOrtName.toLowerCase().includes(query) ||
    tour.zielOrtName.toLowerCase().includes(query)
  );
});

const resetSearch = () => {
  searchQuery.value = '';
};

onMounted(() => {
  updateTourListe();
});
</script>

<style scoped>
.title {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.search-container {
  display: flex;
  gap: 10px;
  margin-bottom: 1rem;
}

input[type="text"] {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  margin-top: 0;
  padding: 0.5rem 1rem;
  background-color: #333;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #555;
}
</style>
