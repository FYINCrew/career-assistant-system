<script lang="ts" setup>
import useService from '@/composables/useService';
import { onMounted, reactive, ref } from 'vue';
import type { cargo } from './cargoType';
import router from '@/router';

const listagemCargos = ref();

const totalRegistros = ref(0);
const pagina = ref(0);
const loading = ref(false);
const mostrarModalCadastro = ref(false);


const { cargoService } = useService();


onMounted(() => {
    carregarCargosFiltro();
    carregarCargos();
});


async function carregarCargos(event?: any) {

    loading.value = true;

    const page = event?.page ?? 0;
    const size = event?.rows ?? 5;

    cargoService.listarCargosPaginados({ page: page, size: size, sigla: cargoFiltroSigla.value?.sigla, nome: cargoFiltroNome.value?.nome })
        .then((response) => {
            listagemCargos.value = response.content.map((cargo: cargo) => {
                return {
                    id: cargo.id,
                    nome: cargo.nome,
                    sigla: cargo.sigla,
                    descricao: cargo.descricao,
                }
            });


            pagina.value = response.page;
            totalRegistros.value = response.totalElements;

            loading.value = false;
        })
        .catch((error) => {
            console.error('Erro ao carregar cargos:', error);
        })
        .finally(() => {
            loading.value = false;
        });
}

async function carregarCargosFiltro() {
    loading.value = true;

    await cargoService.listarCargos().then((response) => {
        cargos.value = response;
        cargosFiltradosSigla.value = cargos.value;
        cargosFiltradosNome.value = cargos.value;
    }).catch((error) => {
        console.error('Erro ao carregar cargos:', error);
    }).finally(() => {
        loading.value = false;
    });
}

const verDetalhes = (param: any) => {
    router.push({ name: 'detalharCargo', params: { id: param } })
}

const cargoFiltroSigla = ref();
const cargoFiltroNome = ref();

const cargos = ref<cargo[]>([]);
const cargosFiltradosSigla = ref<cargo[]>([]);
const cargosFiltradosNome = ref<cargo[]>([]);

const buscarCargoNome = (event: { query: string }) => {
    cargosFiltradosNome.value = cargos.value.filter(c =>
        c.nome?.toLowerCase().includes(event.query.toLowerCase())
    );

}

const buscarCargoSigla = (event: { query: string }) => {
    cargosFiltradosSigla.value = cargos.value.filter(c =>
        c.sigla?.toLowerCase().includes(event.query.toLowerCase())
    );
};

const limparFiltros = async () => {
    cargoFiltroSigla.value = undefined;
    cargoFiltroNome.value = undefined;

    await carregarCargos();
};

</script>

<template>
    <Toast />
    <MyToolbar />
    <div>
        <div>
            <Card class="m-10 mb-3 ">
                <template #title>
                    <div class="mb-4">
                        <span class="text-2xl font-medium text-black">Pesquisar Cargos</span>
                    </div>
                </template>
                <template #content>
                    <div class="mr-4 flex flex-col md:flex-row gap-4 ">
                        <AutoComplete v-model="cargoFiltroSigla" dropdown :suggestions="cargosFiltradosSigla"
                            @complete="buscarCargoSigla" optionLabel="sigla"
                            empty-search-message="Filtro não encontrado." placeholder="Sigla" />

                        <AutoComplete v-model="cargoFiltroNome" dropdown :suggestions="cargosFiltradosNome"
                            @complete="buscarCargoNome" optionLabel="nome" empty-search-message="Filtro não encontrado."
                            placeholder="Nome" />
                        <Button label="Limpar Filtros" variant="link" @click="limparFiltros" />
                        <Button label="Pesquisar" @click="carregarCargos" />
                    </div>
                </template>
            </Card>
            <div class="flex justify-end mb-4 mr-10">
                <Button label="Cadastrar Cargo" icon="pi pi-plus" @click="router.push({ name: 'cadastrarCargo' })" />

            </div>
        </div>

    </div>

    <div>


        <Card class="m-10 mt-7">
            <template #content>
                <DataTable :value="listagemCargos" paginator :rows=5 :rowsPerPageOptions="[5, 10, 20, 50]"
                    tableStyle="min-width: 50rem" @page="carregarCargos" :loading="loading"
                    :totalRecords="totalRegistros" :lazy="true">

                    <template #loading>
                        <div class="loader"></div>
                    </template>

                    <template #empty v-if="!loading">
                        <div class="flex flex-col items-center justify-center !text-[22px]">
                            <span>Nenhum cargo encontrado.</span>
                        </div>
                    </template>

                    <Column field="sigla" header="Sigla" style=" text-align: center; font-size:17px; color: black;" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center !text-[22px]',
                        },
                    }">

                    </Column>

                    <Column field="nome" header="Nome"
                        style="text-align: center; width: 40%; font-size:17px; color: black;" :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[22px]',
                            },
                        }">
                    </Column>

                    <Column field="descricao" header="Descrição"
                        style=" text-align: center; font-size:17px; color: black;" :pt="{
                            columnHeaderContent: {
                                class: 'justify-center !text-[22px]',
                            },
                        }">

                    </Column>

                    <Column style="text-align: center; width: 25%; font-size:17px; color: black;" header="Detalhes" :pt="{
                        columnHeaderContent: {
                            class: 'justify-center !text-[22px]',
                        },
                    }">
                        <template #body="slotProps">
                            <Button v-tooltip.top="'Visualizar Cargo'" icon="pi pi-eye" class="!text-black" size="small"
                                :link=true @click="verDetalhes(slotProps.data.id)">
                                <template #icon>
                                    <i class="pi pi-eye !text-base"></i>
                                </template>
                            </Button>

                        </template>

                    </Column>


                </DataTable>
            </template>
        </Card>
    </div>

</template>
