<script setup lang="ts">
import Card from 'primevue/card';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';

import Chip from 'primevue/chip'
import Column from 'primevue/column';
import Button from 'primevue/button';
import { onMounted, ref } from 'vue';
import { DataTable } from 'primevue';
import { type funcionario } from '@/components/funcionarios/dataFuncionario'
import router from '@/router';
import useService from '@/composables/useService';

const { funcionarioService } = useService()

const value1 = ref();
const value2 = ref();
const value3 = ref();
const listagemFuncionarios = ref();

onMounted(() => {
    funcionarioService.listarFuncionarios().then((response) => {
        
        
        listagemFuncionarios.value = response.content.map((funcionario: funcionario) => {
            return {
                id: funcionario.id,
                nome: funcionario.nome,
                cargo: funcionario.cargoAtual,
                score: funcionario.score
            }
        });

        console.log('Funcionários carregados:', listagemFuncionarios.value);
    }).catch((error) => {
        console.error('Erro ao carregar funcionários:', error);
    });
});


type teste = {
    name: string
}

const teste = ref<teste[]>([{ name: 'teste1' }, { name: 'teste2' }, { name: 'teste3' }])

const verDetalhes = (param: any) => {
    router.push({ name: 'detalharFuncionarios', params: { id: param } })
}

function limparFiltros() {
    value1.value = '';
    value2.value = '';
    value3.value = '';
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
                    <InputText v-model="value1" placeholder="Filtro 1" />
                    <InputText v-model="value2" placeholder="Filtro 2" />
                    <Select v-model="value3" :options="teste" optionLabel="name" placeholder="Filtro 3"
                        class="w-full md:w-56" showClear />
                    <Button label="Limpar Filtros" variant="link" @click="limparFiltros" />
                    <Button label="Pesquisar"/>
                </template>
            </Card>
        </div>
    </div>
    <div>
        <Card class="m-10">
            <template #content>
                <DataTable :value="listagemFuncionarios" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
                    tableStyle="min-width: 50rem">
                    <Column field="nome" header="Nome" sortable style="text-align: center;" 
                      :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }"></Column>
                    <Column style="text-align: center;" field="cargo" header="Cargo" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center',
                        },
                    }" ></Column>
                    <Column  field="score" header="Score" style="width: 25%; text-align: center;" sortable :pt="{
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