<script setup lang="ts">
import TourListeZeile from './TourListeZeile.vue';
import type { PropType } from 'vue';
import type { ITourDTD } from '@/stores/ITourDTD';

const props = defineProps<{
  touren: ITourDTD[]
}>();
</script>

<template>
  <table>
    <thead>
      <tr>
        <th>Datum</th>
        <th>Start</th>
        <th>Ziel</th>
        <th>Distanz</th>
        <th>Plätze</th>
        <th>Frei</th>
        <th>Preis</th>
        <th>Aktionen</th>
      </tr>
    </thead>
    <tbody>
      <TourListeZeile v-for="tour in props.touren" :key="tour.id" :tour="tour" />
    </tbody>
  </table>
</template>

