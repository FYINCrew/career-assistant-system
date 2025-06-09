<script lang="ts" setup>
import useService from '@/composables/useService';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';


const { funcionarioService } = useService()
const route = useRoute();
const funcionarioId = route.params.id;

onMounted(async () => {
    await funcionarioService.detalharFuncionario(funcionarioId as string).then((response) => {
        console.log(response);
    }).catch((error) => {
        console.error('Erro ao carregar funcionário:', error);
    });
    
})



</script>

<template>
    <section id="detalhes-funcionario" class="flex flex-col items-center justify-center m-8 bg-white">
        <div>
            <div class="flex flex-col items-center justify-center gap-4">
                <Image src="src/assets/image.png" alt="Imagem do Funcionário" width="160" preview
                    class="rounded-xl shadow-md border-2 border-primary-100 bg-primary-50" />
                <div class="bg-primary-50 rounded-lg p-4 shadow">
                    <h2 class="text-2xl font-semibold mb-1 text-center text-primary-800">Caio de Jesus Nunes</h2>
                    <div class="flex flex-col gap-1 text-primary-700">
                        <span><strong>Cargo Atual:</strong> Desenvolvedor de Software Júnior</span>
                        <span><strong>Cargo Almejado:</strong> Arquiteto de Software</span>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="px-10 py-1">
        <div>
            <Panel toggleable class="!bg-gray-50 border !border-primary-200 rounded-lg shadow" collapsed>
                <template #header>
                    <div class="flex items-center justify-between">
                        <span class="text-xl font-semibold">Descrição</span>
                    </div>
                </template>
                <p class="m-0 text-gray-700">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                    pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit
                    anim id est laborum.
                </p>
            </Panel>
        </div>
    </section>

    <section class="px-10 py-1">
        <div>
            <Panel toggleable collapsed class=" !bg-gray-50 !border-primary-200 rounded-lg shadow ">
                <template #header>
                    <div class="flex items-center justify-between">
                        <span class="text-xl font-semibold">Habilidades</span>
                    </div>
                </template>
                <p class="m-0 text-gray-700">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                    et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                    pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit
                    anim id est laborum.
                </p>
            </Panel>
        </div>
    </section>
    <section class="px-10 py-1">
        <div>
            <Panel toggleable collapsed class="!bg-gray-50 border !border-primary-200 rounded-lg shadow">
                <template #header>
                    <div class="flex items-center justify-between">
                        <span class="text-xl font-semibold ">Pontuação</span>
                        <Button icon="pi pi-question-circle" class="p-button-rounded p-button-text"
                            v-tooltip.right="'A pontuação é um guia gerado pela IA baseado nas habilidades que o funcionário possui VS habilidades necessárias para o cargo almejado.'" />
                    </div>
                </template>
                <p class="m-0 text-lg font-medium">
                    Score: <span class="text-primary-600 font-bold">85</span>
                </p>
            </Panel>
        </div>
    </section>
</template>

<style scoped></style>
