<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import Image from "primevue/image";

const fraseSelecionada = ref("");
const emit = defineEmits(['update:visible']);
let sortearFraseInterval: any;

const props = defineProps({
    visible: {
        type: Boolean,
        default: false
    }
});

const frasesRobo: string[] = [
    "Calculando pontuação para promoção",
    "Classificando perfil com inteligência artificial",
    "Calculando score para cada tecnologia",
    "Executando avaliação do colaborador",
    "Só mais um momento",
    "Preparando resultados",
    "Finalizando cálculos",
];

onMounted(() => {
    sortearFrase();
});

watch(() => props.visible, (newVal) => {
    if (newVal === false) {
        finalizarTimeout();
    } else {
        sortearFraseInterval = setInterval(() => {
            const randomIndex = Math.floor(Math.random() * frasesRobo.length);
            fraseSelecionada.value = frasesRobo[randomIndex];
        }, 5000);
    }
});

watch(fraseSelecionada, () => {
    console.log(fraseSelecionada.value);
    console.log(props.visible)
})

function sortearFrase() {
    const randomIndex = Math.floor(Math.random() * frasesRobo.length);
    fraseSelecionada.value = frasesRobo[randomIndex];
};

function finalizarTimeout() {
    clearTimeout(sortearFraseInterval);
}

</script>

<template>
    <div class="card flex justify-center">
        <Dialog v-model:visible="props.visible" modal @update:visible="emit('update:visible', $event)" :closable="false"
            :style="{ width: '38rem' }" pt:root:class="!border-0 " pt:mask:class="backdrop-blur-sm">
            <div class="flex items-center justify-center fraseRobo">
                <span>{{ fraseSelecionada }}</span>
                <div class="loader !w-6 !mt-4 !ml-1"
                    style="--_g: no-repeat radial-gradient(circle closest-side, #05af19 90%, #0000);"></div>
            </div>

            <Image src="/src/assets/gif-robo.gif" alt="gif" />
        </Dialog>
    </div>
</template>

<style scoped></style>