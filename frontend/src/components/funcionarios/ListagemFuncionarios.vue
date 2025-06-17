<script setup lang="ts">
import Card from 'primevue/card';
import AutoComplete from 'primevue/autocomplete';
import Select from 'primevue/select';

import Chip from 'primevue/chip'
import Column from 'primevue/column';
import Button from 'primevue/button';
import { onMounted, ref, watch } from 'vue';
import { DataTable, InputNumber } from 'primevue';
import { type funcionario } from '@/components/funcionarios/funcionarioType'
import router from '@/router';
import useService from '@/composables/useService';
import { cargosExample, type cargo } from '../cargos/cargoType';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';

const { funcionarioService, cargoService } = useService()

const cargoFiltro = ref<cargo>();
const tempoExperienciaFiltro = ref();
const ensinoSuperiorFiltro = ref();
const listagemFuncionarios = ref();

const totalRegistros = ref(0);
const pagina = ref(0);
const loading = ref(false);
const cargos = ref<cargo[]>([]);
const cargosFiltrados = ref<cargo[]>([]);

onMounted(() => {
    carregarCargos();
    carregarFuncionarios();
});


type testeType = {
    label: string
    value: boolean
}

const teste = ref<testeType[]>([{ label: 'Sim', value: true }, { label: 'Não', value: false }])

const verDetalhes = (param: any) => {
    router.push({ name: 'detalharFuncionarios', params: { id: param } })
}

async function limparFiltros() {
    cargoFiltro.value = undefined;
    tempoExperienciaFiltro.value = undefined;
    ensinoSuperiorFiltro.value = undefined;

    await carregarFuncionarios();
}


async function carregarFuncionarios(event?: any) {
    loading.value = true;

    const page = event?.page ?? 0;
    const size = event?.rows ?? 5;


    await funcionarioService.listarFuncionarios(
        {
            page: page, size: size, cargoId: cargoFiltro.value?.id,
            tempoExperiencia: tempoExperienciaFiltro.value,
            ensinoSuperior: ensinoSuperiorFiltro.value
        })
        .then(
            async (response) => {


                listagemFuncionarios.value = response.content.map((funcionario: funcionario) => {
                    return {
                        id: funcionario.id,
                        nome: funcionario.nome,
                        cargo: funcionario.cargoAtual.nome,
                    }
                });

                pagina.value = response.page;
                totalRegistros.value = response.totalElements;

                loading.value = false;




            })
}

function buscarCargo(event: { query: string }) {
    cargosFiltrados.value = cargos.value.filter(c =>
        c.nome.toLowerCase().includes(event.query.toLowerCase())
    );

}

async function carregarCargos() {
    await cargoService.listarCargos().then((response) => {
        cargos.value = response;
        cargosFiltrados.value = cargos.value;
    }).catch((error) => {
        console.error('Erro ao carregar cargos:', error);
    });
}

const confirmImport = useConfirm();
const toast = useToast();

const confirm = (id: number) => {
    confirmImport.require({
        message: 'Deseja calcular o Score do funcionário?',
        header: 'Confirmação',
        icon: 'pi pi-exclamation-triangle',
        rejectProps: {
            label: 'Cancelar',
            severity: 'secondary',
            outlined: true
        },
        acceptProps: {
            label: 'Sim'
        },
        accept: async () => {
            loading.value = true;
            await funcionarioService.calcularScores(id).then(() => {
                toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Score calculado com sucesso!', life: 3000 });
            }).catch((error) => {
                console.error('Erro ao calcular média:', error);
                toast.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível calcular o Score.', life: 3000 });
            }).finally(() => {
                loading.value = false;
            });

        }
    });
};


</script>

<template>
    <Toast />
    <ConfirmDialog />

    <div>
        <div>
            <Card class="m-10">
                <div class="mb-8 "></div>
                <template #title>
                    <div class="mb-4">
                        <span class="font-medium">Pesquisar Funcionários</span>
                    </div>
                </template>
                <template #content>
                    <div class="mr-4 flex flex-col md:flex-row gap-4 ">
                        <AutoComplete v-model="cargoFiltro" dropdown :suggestions="cargosFiltrados"
                            @complete="buscarCargo" optionLabel="nome" empty-search-message="Cargo não encontrado."
                            placeholder="Selecione um Cargo" />

                        <InputNumber v-model="tempoExperienciaFiltro" placeholder="Tempo de Experiência (em anos)"
                            :min="1" class="w-full md:w-72" />
                        <Select v-model="ensinoSuperiorFiltro" :options="teste" empty-message="Filtro não encontrado"
                            placeholder="Ensino Superior Completo" class="w-full md:w-70" showClear option-value="value"
                            optionLabel="label" />
                        <Button label="Limpar Filtros" variant="link" @click="limparFiltros" />
                        <Button label="Pesquisar" @click="carregarFuncionarios" />
                    </div>
                </template>
            </Card>
        </div>
    </div>
    <div>

        <Card class="m-10">
            <template #content>
                <DataTable :value="listagemFuncionarios" paginator :rows=5 :rowsPerPageOptions="[5, 10, 20, 50]"
                    tableStyle="min-width: 50rem" @page="carregarFuncionarios" :loading="loading"
                    :totalRecords="totalRegistros" :lazy="true">

                    <template #loading>
                        <div class="loader"></div>
                    </template>

                    <template #empty v-if="!loading">
                        <div class="flex flex-col items-center justify-center">
                            <span>Nenhum funcionário encontrado.</span>
                        </div>
                    </template>

                    <Column field="nome" header="Nome" style="text-align: center;" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }">
                    </Column>
                    <Column style=" text-align: center;" field="cargo" header="Cargo" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }"></Column>
                    <Column field="score" header="Score" style="width: 25%; text-align: center;" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }">
                        <template #body="slotProps">
                            <Chip :label="slotProps.data.score"
                                :style="{ backgroundColor: slotProps.data.score >= 80 ? '#4caf50' : slotProps.data.score >= 50 ? '#ff9800' : '#f44336', color: '#fff' }" />
                        </template>
                    </Column>
                    <Column style="text-align: center;" header="Detalhes" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }">
                        <template #body="slotProps">
                            <Button v-tooltip.top="'Visualizar Funcionário'" icon="pi pi-eye" class="!text-black"
                                @click="verDetalhes(slotProps.data.id)" size="small" :link=true />
                            <Button v-tooltip.top="'Calcular Score'" icon="pi pi-calculator" class="!text-black"
                                @click="confirm(slotProps.data.id)" size="small" :link=true />
                        </template>

                    </Column>


                </DataTable>
            </template>
        </Card>
    </div>
</template>

<style scoped>
/* HTML: <div class="loader"></div> */
.loader {
    width: 60px;
    aspect-ratio: 2;
    --_g: no-repeat radial-gradient(circle closest-side, #000 90%, #0000);
    background:
        var(--_g) 0% 50%,
        var(--_g) 50% 50%,
        var(--_g) 100% 50%;
    background-size: calc(100%/3) 50%;
    animation: l3 1s infinite linear;
}

@keyframes l3 {
    20% {
        background-position: 0% 0%, 50% 50%, 100% 50%
    }

    40% {
        background-position: 0% 100%, 50% 0%, 100% 50%
    }

    60% {
        background-position: 0% 50%, 50% 100%, 100% 0%
    }

    80% {
        background-position: 0% 50%, 50% 50%, 100% 100%
    }
}
</style>