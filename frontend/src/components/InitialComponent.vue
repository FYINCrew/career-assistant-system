<script setup lang="ts">
import Card from 'primevue/card';
import InputText from 'primevue/inputtext';
import Select from 'primevue/select';

import Column from 'primevue/column';
import Button from 'primevue/button';
import { ref } from 'vue';
import { DataTable } from 'primevue';

const value1 = ref();
const value2 = ref();
const value3 = ref();


type funcionario = {
    nome: string,
    cargo: string,
    promocaoRecomendada: boolean
}

type teste = {
    name: string
}

const teste = ref<teste[]>([{ name: 'teste1' }, { name: 'teste2' }, { name: 'teste3' }])

const data = ref<funcionario[]>([{ nome: "Ana Souza", cargo: "Analista de Marketing", promocaoRecomendada: true },
{ nome: "Carlos Lima", cargo: "Desenvolvedor Backend", promocaoRecomendada: false },
{ nome: "Fernanda Rocha", cargo: "Coordenadora de Projetos", promocaoRecomendada: true },
{ nome: "João Pereira", cargo: "Designer UX/UI", promocaoRecomendada: false },
{ nome: "Mariana Oliveira", cargo: "Gerente de Produto", promocaoRecomendada: true },
{ nome: "Lucas Fernandes", cargo: "Analista de Dados", promocaoRecomendada: false },
{ nome: "Patrícia Castro", cargo: "Desenvolvedora Frontend", promocaoRecomendada: true },
{ nome: "Ricardo Mendes", cargo: "Especialista em Segurança", promocaoRecomendada: false },
{ nome: "Tatiane Ribeiro", cargo: "Scrum Master", promocaoRecomendada: true },
{ nome: "Eduardo Silva", cargo: "Engenheiro DevOps", promocaoRecomendada: false }])


const verDetalhes = (param: any) => {
    alert(param.data)
}

</script>

<template>
    <div class="mb-8">
        <Card>
            <template #title>Pesquisar Funcionários</template>
            <template #content>
                <InputText v-model="value1" placeholder="Filtro 1" />
                <InputText v-model="value2" placeholder="Filtro 2" />
                <Select v-model="value3" :options="teste" optionLabel="name" placeholder="Filtro 3"
                    class="w-full md:w-56" showClear />
                <Button label="Limpar Filtros" variant="link" />
                <Button label="Pesquisar" />
            </template>
        </Card>
    </div>
    <div>
        <DataTable :value="data" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
            tableStyle="min-width: 50rem">
            <Column field="nome" header="Nome" style="width: 25%"></Column>
            <Column field="cargo" header="Cargo" style="width: 25%"></Column>
            <Column field="promocaoRecomendada" header="Promoção Recomendada" style="width: 25%">
                <template #body="slotProps">
                    {{ slotProps.data.promocaoRecomendada ? 'Sim' : 'Não' }}
                </template>
            </Column>
            <Column header="Detalhes">
                <template #body="slotProps">
                    <Button label="Leia Mais" @click="verDetalhes(slotProps.data)" size="small" />
                </template>
            </Column>
        </DataTable>
    </div>
</template>
