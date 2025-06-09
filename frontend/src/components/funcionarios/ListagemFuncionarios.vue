<script setup lang="ts">
import Card from 'primevue/card';
import AutoComplete from 'primevue/autocomplete';
import Select from 'primevue/select';

import Chip from 'primevue/chip'
import Column from 'primevue/column';
import Button from 'primevue/button';
import { onMounted, ref } from 'vue';
import { DataTable, InputNumber } from 'primevue';
import { type funcionario } from '@/components/funcionarios/funcionarioType'
import router from '@/router';
import useService from '@/composables/useService';
import { cargosExample, type cargo } from '../cargos/cargoType';
import type Paginated from './Paginated';

const { funcionarioService } = useService()

const value1 = ref();
const value2 = ref();
const value3 = ref();
const listagemFuncionarios = ref();

const totalRegistros = ref(0);
const pagina = ref(0);
const loading = ref(false);

onMounted(() => {
    carregarFuncionarios();
    cargosFiltrados.value = cargos.value;
});


type testeType = {
    label: string
    value: boolean
}

const teste = ref<testeType[]>([{ label: 'Sim', value: true }, { label: 'Não', value: false }])

const verDetalhes = (param: any) => {
    router.push({ name: 'detalharFuncionarios', params: { id: param } })
}

function limparFiltros() {
    value1.value = null;
    value2.value = null;
    value3.value = null;
}

const cargos = ref<cargo[]>(cargosExample);
const cargosFiltrados = ref<cargo[]>([]);

function buscarCargo(event: { query: string }) {
    cargosFiltrados.value = cargos.value.filter(c =>
        c.nome.toLowerCase().includes(event.query.toLowerCase())
    );
}

async function carregarFuncionarios(event?: any) {
    loading.value = true;

    const page = event?.page ?? 0;
    const size = event?.rows ?? 5;


    await funcionarioService.listarFuncionarios({ page: page, size: size }).then((response) => {
        listagemFuncionarios.value = response.content.map((funcionario: funcionario) => {
            return {
                id: funcionario.id,
                nome: funcionario.nome,
                cargo: funcionario.cargoAtual,
            }
        });

        pagina.value = response.page;
        totalRegistros.value = response.totalElements;

        console.log(pagina.value, totalRegistros.value);
    }).finally(() => {
        loading.value = false;
    });
}

</script>

<template>
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
                    <AutoComplete v-model="value1" dropdown :suggestions="cargosFiltrados" @complete="buscarCargo"
                        optionLabel="nome" empty-search-message="Cargo não encontrado." />

                    <InputNumber v-model="value2" placeholder="Tempo de Experiência (em anos)" :min="1" />
                    <Select v-model="value3" :options="teste" empty-message="Filtro não encontrado"
                        placeholder="Ensino Superior Completo" class="w-full md:w-56" showClear option-value="value"
                        optionLabel="label" />
                    <Button label="Limpar Filtros" variant="link" @click="limparFiltros" />
                    <Button label="Pesquisar" />
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
                    <template #empty>
                        <div class="text-center p-4">Nenhum funcionário encontrado.</div>
                    </template>
                    <Column field="nome" header="Nome" sortable style="text-align: center;" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }"></Column>
                    <Column style="text-align: center;" field="cargo" header="Cargo" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }"></Column>
                    <Column field="score" header="Score" style="width: 25%; text-align: center;" sortable :pt="{
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
                            <Button label="Leia Mais" @click="verDetalhes(slotProps.data.id)" size="small" />
                        </template>
                    </Column>
                </DataTable>
            </template>
        </Card>
    </div>
</template>