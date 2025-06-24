<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import type { cargo, requisitos } from './cargoType';
import useService from '@/composables/useService';
import router from '@/router';
import { useToast } from 'primevue/usetoast';
import { useRoute } from 'vue-router';



const cargo = reactive({} as cargo);
const requisitos = reactive({} as requisitos)
const requisitoDataTable = ref<requisitos[]>([])
const { cargoService } = useService();
const loading = ref(false);
const route = useRoute();
const cargoId = route.params.id;


onMounted(async () => {
    await buscarCargo();
});


async function buscarCargo() {

    loading.value = true;

    await cargoService.buscarCargoPorId(cargoId as string).then((response) => {
        cargo.sigla = response.sigla;
        cargo.nome = response.nome;
        cargo.descricao = response.descricao;
        requisitoDataTable.value = response.requisitos.map((req: requisitos) => ({
            nome: req.nome,
            score: req.score
        }));
    }).catch((error) => {
        console.error('Erro ao buscar cargo:', error);
    }).finally(() => {
        loading.value = false;
    });

}


</script>
<template>
    <Toast />
    <MyToolbar />
    <div class="max-w-6xl mx-auto flex flex-row items-center mt-5 gap-4">
        <Button icon="pi pi-arrow-left" iconPos="left" class="!bg-gray-200 !text-gray-800 hover:!bg-gray-300 !border-1 !border-green-400 !rounded-3xl" @click="router.push({ name: 'listarCargos' })" />
        <h1 class="text-5xl font-semibold text-black mb-2 mx-auto text-center">Detalhes do Cargo</h1>
    </div>

    <Card class="mt-10 !shadow-2xl p-5 max-w-6xl mx-auto">
        <template #title>

            <h3 class="text-3xl text-black mb-2 font-medium">Informações do Cargo</h3>

        </template>

        <template #content>


            <div class="mb-4 flex flex-row gap-4 ">

                <FloatLabel variant="on">
                    <InputText disabled v-model="cargo.sigla" variant="filled"
                        style="width: 300px; text-transform: uppercase;" />
                    <label for="on_label" class="!font-normal">Sigla do cargo</label>
                </FloatLabel>

                <FloatLabel variant="on">
                    <InputText disabled v-model="cargo.nome" variant="filled" style="width: 300px;" />
                    <label for="on_label" class="!font-normal">Nome do cargo</label>
                </FloatLabel>

                <FloatLabel variant="on">
                    <InputText disabled v-model="cargo.descricao" variant="filled" style="width: 300px;" />
                    <label for="on_label" class="!font-normal">Descrição do cargo</label>
                </FloatLabel>
            </div>
            <Divider class="!mt-7 !mb-7" />

            <h3 class="text-3xl text-black mb-4 font-medium">Lista de Requisitos Adicionados</h3>
            <div class="max-w-4xl mx-auto w-full">
                <DataTable :value="requisitoDataTable" showGridlines stripedRows
                    class="w-full overflow-x-auto rounded-xl border border-gray-300 shadow-sm">
                    <Column field="nome" header="Nome"></Column>
                    <Column field="score" header="Score"></Column>
                </DataTable>
            </div>
        </template>
    </Card>
</template>