<template>
  <div class="wrapper">
    <div v-if="tour">
      <h1>{{ tour.startOrtName }} nach {{ tour.zielOrtName }}</h1>
      <p>{{ tour.info }}</p>
      <ul>
        <li><strong>Abfahrtszeit:</strong> {{ tour.abfahrDateTime }}</li>
        <li><strong>Preis:</strong> {{ tour.preis }} €</li>
        <li><strong>Plätze:</strong> {{ tour.plaetze }}</li>
        <li><strong>Gebucht:</strong> {{ tour.buchungen }}</li>
        <li><strong>Distanz:</strong> {{ tour.distanz }} km</li>
      </ul>
    </div>
    <div v-else>
      <p>Tour nicht gefunden.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useTourenStore } from '@/stores/tourenstore';
import { useInfo } from '@/composables/useInfo';

const route = useRoute();
const tourenStore = useTourenStore();
const { setzeInfo } = useInfo();

const tourId = Number(route.params.tourid);

const tour = computed(() => {
  return tourenStore.tourdata.tourliste.find(t => t.id === tourId);
});

onMounted(async () => {
  if (!tourenStore.tourdata.ok) {
    await tourenStore.updateTourListe();
  }
  if (!tour.value) {
    try {
      const response = await fetch(`/api/tour/${tourId}`);
      if (response.ok) {
        const data = await response.json();
        tourenStore.tourdata.tourliste.push(data);
      } else {
        setzeInfo(`Fehler beim Abrufen der Tourdaten: ${response.statusText}`);
      }
    } catch (error: any) {
      console.error("Fehler beim Laden der Tour:", error.message);
      setzeInfo(`Fehler beim Abrufen der Tourdaten: ${error.message}`);
    }
  }
});
</script>
