<script setup lang="ts">
import Card from 'primevue/card';
import AutoComplete from 'primevue/autocomplete';
import Select from 'primevue/select';

import Chip from 'primevue/chip'
import Column from 'primevue/column';
import Button from 'primevue/button';
import Toolbar from 'primevue/toolbar';
import { computed, onMounted, ref, watch } from 'vue';
import { DataTable, InputNumber } from 'primevue';
import { type funcionario } from '@/components/funcionarios/funcionarioType'
import router from '@/router';
import useService from '@/composables/useService';
import { type cargo } from '../cargos/cargoType';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import MyToolbar from '../menu/MyToolbar.vue';
import { getClassificacaoPorScore, getCorPorScore } from '@/commons/classificarPorScore';
import MyModal from '../menu/MyModal.vue';

const { funcionarioService, cargoService } = useService()

const cargoFiltro = ref<cargo>();
const tempoExperienciaFiltro = ref();
const ensinoSuperiorFiltro = ref();
const listagemFuncionarios = ref();
const listagemFuncionariosTeste = ref<funcionario[]>([]);

const loading = ref(false);
const modalVisible = ref(false);

const totalRegistros = ref(0);
const pagina = ref(0);
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
                        tempoExperiencia: funcionario.tempoExperiencia ? `${funcionario.tempoExperiencia} anos` : 'Não informado',
                        ensinoSuperior: funcionario.ensinoSuperior ? 'Sim' : 'Não',
                    }
                });

                listagemFuncionariosTeste.value = response.content;

                pagina.value = response.page;
                totalRegistros.value = response.totalElements;

                loading.value = false;
            })
}

function buscarCargo(event: { query: string }) {
    cargosFiltrados.value = cargos.value.filter(c =>
        c.nome?.toLowerCase().includes(event.query.toLowerCase())
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

const mediasGerais = computed(() => {
    return listagemFuncionariosTeste.value.reduce((acc: Record<number, number>, func) => {
        const exps = func.experiencias;
        const media =
            exps && exps.length > 0
                ? Math.round(exps.reduce((soma, exp) => soma + exp.scoreMedia, 0) / exps.length)
                : 0;

        acc[func.id] = media;
        return acc;
    }, {});
});



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
            modalVisible.value = true;
            await funcionarioService.calcularScores(id).then(() => {
                toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Score calculado com sucesso!', life: 3000 });
                carregarFuncionarios();
            }).catch((error) => {
                console.error('Erro ao calcular média:', error);
                toast.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível calcular o Score.', life: 3000 });
            }).finally(() => {
                modalVisible.value = false;
                loading.value = false;
            });

        }
    });
};
</script>

<template>
    <MyModal v-model:visible="modalVisible"/>
    <Toast />
    <ConfirmDialog />
    <MyToolbar />

    <div>
        <div>
            <Card class="m-10">
                <div class="mb-8 "></div>
                <template #title>
                    <div class="mb-4">
                        <span class="text-2xl font-medium text-black">Pesquisar Funcionários</span>
                    </div>
                </template>
                <template #content>
                    <div class="mr-4 flex flex-col md:flex-row gap-4 ">
                        <AutoComplete v-model="cargoFiltro" dropdown :suggestions="cargosFiltrados"
                            @complete="buscarCargo" optionLabel="nome" empty-search-message="Cargo não encontrado."
                            placeholder="Selecione um Cargo" />

                        <InputNumber v-model="tempoExperienciaFiltro" placeholder="Experiência Mínima (em anos)"
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

                    <Column field="nome" header="Nome" style="text-align: center; font-size: 17px; color: black;" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center !text-[20px]',
                        },
                    }">
                    </Column>
                    <Column style=" text-align: center; font-size: 17px; color: black;" field="cargo" header="Cargo"
                        :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[20px]',
                            },
                        }"></Column>
                    <Column style="width:15%; text-align: center; font-size: 17px; color: black;"
                        field="tempoExperiencia" header="Tempo de Experiência" :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[20px]',
                            },
                        }"></Column>
                    <Column style=" text-align: center; font-size: 17px; color: black;" field="ensinoSuperior"
                        header="Ensino Superior" :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[20px]',
                            },
                        }"></Column>
                    <Column field="score" header="Score" style=" text-align: center; font-size: 17px; color: black;"
                        :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[20px]',
                            },
                        }">
                        <template #body="slotProps">
                            <Chip
                                v-tooltip.top="getClassificacaoPorScore(mediasGerais[slotProps.data.id], 'Média não Calculada')"
                                class="font-medium" :label="mediasGerais[slotProps.data.id] || 'N/A'"
                                :style="{ backgroundColor: getCorPorScore(mediasGerais[slotProps.data.id]) }" />
                        </template>
                    </Column>
                    <Column style="text-align: center; font-size: 17px; color: black;" header="Ações" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center !text-[20px]',
                        },
                    }">
                        <template #body="slotProps">
                            <Button v-tooltip.top="'Visualizar Funcionário'" icon="pi pi-eye" class="!text-black"
                                @click="verDetalhes(slotProps.data.id)" size="small" :link=true>
                                <template #icon>
                                    <i class="pi pi-eye !text-base"></i>
                                </template>
                            </Button>
                            <Button v-tooltip.top="'Calcular Score'" icon="pi pi-calculator" class="!text-black"
                                @click="confirm(slotProps.data.id)" size="small" :link=true>
                                <template #icon>
                                    <i class="pi pi-calculator !text-base"></i>
                                </template>
                            </Button>
                        </template>

                    </Column>


                </DataTable>
            </template>
        </Card>
    </div>
</template>

<style scoped></style>