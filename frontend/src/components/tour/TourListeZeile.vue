<script setup lang="ts">
import { computed } from 'vue';
import type { ITourDTD } from '@/stores/ITourDTD';

const props = defineProps<{
  tour: ITourDTD
}>();

const freiePlaetze = computed(() => {
  return props.tour.plaetze - props.tour.buchungen;
});
</script>

<template>
  <tr>
    <td>{{ tour.abfahrDateTime }}</td>
    <td>{{ tour.startOrtName }}</td>
    <td>{{ tour.zielOrtName }}</td>
    <td>{{ tour.distanz.toFixed(2) }} km</td>
    <td>{{ freiePlaetze }}</td>
    <td>{{ freiePlaetze }}</td>
    <td>{{ tour.preis }} €</td>
    <td>
      <router-link :to="`/tour/${tour.id}`">Details</router-link>
    </td>
  </tr>
</template>
