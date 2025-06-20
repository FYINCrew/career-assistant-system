<script setup lang="ts">
import Menu from '@/components/menu/MyMenu.vue';
import Toolbar from 'primevue/toolbar';
</script>

<template>
    <Toolbar class="mb-4 !bg-[#10b9816b] shadow-md !border-none h-15 ">
        <template #start>
            <Menu />
        </template>
    </Toolbar>
</template>
