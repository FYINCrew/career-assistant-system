<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { cargo, requisitos } from './cargoType';
import useService from '@/composables/useService';
import router from '@/router';
import { useToast } from 'primevue/usetoast';



const novoCargo = reactive({} as cargo);
const requisitos = reactive({} as requisitos)
const requisitoDataTable = ref<requisitos[]>([])
const { cargoService } = useService();
const loading = ref(false);

const toast = useToast();

const adicionarRequisito = () => {

    if (requisitoDataTable.value.length > 4) {
        toast.add({
            severity: 'warn',
            summary: 'Atenção',
            detail: 'Não é possível cadastrar mais de 5 requisitos.',
            life: 3000
        });
        return;
    }

    if (requisitos.nome && requisitos.score) {
        requisitoDataTable.value.push({
            nome: requisitos.nome,
            score: requisitos.score
        });
        requisitos.nome = '';
        requisitos.score = 0;
    }
};

const excluirRequisito = (index: number) => {
    requisitoDataTable.value.splice(index, 1);
};

async function cadastrarCargo() {
    if (!novoCargo.nome || !novoCargo.sigla || !novoCargo.descricao) {
        toast.add({
            severity: 'warn',
            summary: 'Atenção',
            detail: 'Preencha todos os campos do cargo.',
            life: 3000
        });
        return;
    }

    if (requisitoDataTable.value.length === 0) {
        toast.add({
            severity: 'warn',
            summary: 'Atenção',
            detail: 'Adicione ao menos um requisito.',
            life: 3000
        });
        return;
    }

    loading.value = true;
    await cargoService.cadastrarCargo({
        nome: novoCargo.nome,
        descricao: novoCargo.descricao,
        sigla: novoCargo.sigla,
        requisitos: requisitoDataTable.value
    }).then(() => {
        toast.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Cargo cadastrado com sucesso!',
            life: 5000
        });
        router.push("/listar-cargos");

    }).catch((error) => {
        console.error('Erro ao cadastrar cargo:', error);
    }).finally(() => {
        loading.value = false;
    });


}


</script>
<template>
    <Toast />
    <MyToolbar />
    <div class="flex flex-col items-center justify-center mt-5">
        <h1 class="text-5xl font-semibold text-black mb-2">Cadastrar Cargo</h1>
        <p class="text-lg text-gray-900">Cadastre um cargo e associe a ele os requisitos necessários</p>
    </div>
    <form @submit.prevent="cadastrarCargo">
        <Card class="mt-10 !shadow-2xl p-5 max-w-6xl mx-auto">
            <template #title>

                <h3 class="text-3xl text-black mb-2 font-medium">Informações do Cargo</h3>

            </template>

            <template #content>


                <div class="mb-4 flex flex-row gap-4 ">

                    <FloatLabel variant="on">
                        <InputText v-model="novoCargo.sigla" variant="filled"
                            style="width: 300px; text-transform: uppercase;" />
                        <label for="on_label" class="!font-normal">Sigla do cargo</label>
                    </FloatLabel>

                    <FloatLabel variant="on">
                        <InputText v-model="novoCargo.nome" variant="filled" style="width: 300px;" />
                        <label for="on_label" class="!font-normal">Nome do cargo</label>
                    </FloatLabel>

                    <FloatLabel variant="on">
                        <InputText v-model="novoCargo.descricao" variant="filled" style="width: 300px;" />
                        <label for="on_label" class="!font-normal">Descrição do cargo</label>
                    </FloatLabel>
                </div>
                <Divider class="!mt-7 !mb-7" />
                <h3 class="text-3xl text-black mb-4 font-medium ">Requisito</h3>
                <div class="mb-7 flex flex-row gap-4">
                    <FloatLabel variant="on">
                        <InputText v-model="requisitos.nome" variant="filled" />
                        <label for="on_label" class="!font-normal">Nome do requisito</label>
                    </FloatLabel>

                    <FloatLabel variant="on">
                        <InputNumber :min=40 :max=90 v-model="requisitos.score" variant="filled" />
                        <label for="on_label" class="!font-normal">Score</label>
                    </FloatLabel>
                    <Button v-tooltip.right="'Adicionar Requisito'" icon="pi pi-plus" @click="adicionarRequisito"
                        class="!bg-green-600 "></Button>
                </div>
                <h3 class="text-3xl text-black mb-4 font-medium">Lista de Requisitos Adicionados</h3>
                <div class="max-w-4xl mx-auto w-full">
                    <DataTable :value="requisitoDataTable" showGridlines stripedRows
                        class="w-full overflow-x-auto rounded-xl border border-gray-300 shadow-sm">
                        <Column field="nome" header="Nome"></Column>
                        <Column field="score" header="Score"></Column>
                        <Column field="acao" header="Ações">
                            <template #body="slotProps">
                                <Button @click="excluirRequisito(slotProps.index)" v-tooltip.top="'Excluir Requisito'"
                                    icon="pi pi-trash" class="!text-red-600" size="small" :link="true" />
                            </template>
                        </Column>
                    </DataTable>
                </div>

            </template>
        </Card>

        <div class="flex max-w-6xl mx-auto justify-end mt-6 mb-4 gap-3">
            <Button variant="link" label="Voltar" icon="pi pi-angle-left"
                class="!bg-gray-200 !text-gray-800 hover:!bg-gray-300 !border-1 !border-green-400"
                @click="router.push({ name: 'listarCargos' })" />
            <Button :loading="loading" label="Cadastrar" icon="pi pi-check" iconPos="right" type="submit"
                class="!bg-primary-500 !text-white hover:!bg-primary-600 !text-lg" />
        </div>
    </form>

</template>