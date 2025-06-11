<script lang="ts" setup>
import useService from '@/composables/useService';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import type { funcionario } from './funcionarioType';


const { funcionarioService } = useService()
const route = useRoute();
const funcionarioId = route.params.id;

const nome = ref<string>('');
const cargoAtual = ref<string>('');
const cargoAlmejado = ref<string>('');
const funcionarioDetalhes = ref<funcionario>();


onMounted(async () => {
    await funcionarioService.detalharFuncionario(funcionarioId as string).then((response) => {
        nome.value = response.nome;
        cargoAtual.value = response.cargoAtual.nome;
        cargoAlmejado.value = response.cargoDesejado.nome;
        funcionarioDetalhes.value = response;
        console.log('Cargo Atual:', response);
    }).catch((error) => {
            console.error('Erro ao carregar funcionário:', error);
        });
})

const classificacaoScore = (score: number) => {
    if (score >= 80 && score <= 90) {
        return 'Expert';
    } else if (score >= 70 && score <= 79) {
        return 'Avançado';
    } else if (score >= 60 && score <= 69) {
        return 'Proficiente';
    } else if (score >= 50 && score <= 59) {
        return 'Intermediário';
    } else if (score >= 40 && score <= 49) {
        return 'Básico';
    }
}



</script>

<template>
    <section id="detalhes-funcionario" class="flex flex-col items-center justify-center m-8 bg-white">
        <div>
            <div class="flex flex-col items-center justify-center gap-4">
                <div class="bg-primary-50 rounded-lg p-4 shadow">
                    <h2 class="text-2xl font-semibold mb-1 text-center text-primary-800">{{ nome }}</h2>
                    <div class="flex flex-col gap-1 text-primary-700">
                        <span><strong>Cargo Atual:</strong> {{ cargoAtual }}</span>
                        <span><strong>Cargo Almejado:</strong> {{ cargoAlmejado }}</span>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="px-10 py-1">
        <div>
            <Panel toggleable class=" !bg-gray-50 !border-primary-200 rounded-lg shadow ">
                <template #header>
                    <div class="flex items-center justify-between">
                        <span class="text-xl font-semibold">Experiências</span>
                        <Button icon="pi pi-question-circle" class="p-button-rounded p-button-text"
                            v-tooltip.right="'Veja detalhes sobre as experiências e tecnologias utilizadas pelo usuário.'" />
                    </div>

                </template>
                <Accordion :value="['0']" multiple>
                    <AccordionPanel :value="index" v-for="(experiencia, index) in funcionarioDetalhes?.experiencias"
                        :key="index" class="mb-2">
                        <AccordionHeader>
                            <div class="flex justify-between items-center w-full">
                                <span class="font-bold text-lg text-primary">
                                    Experiência {{ index + 1 }}
                                </span>
                                <Chip class="!bg-primary-100 !text-primary-900 mr-4">
                                    <span class="font-semibold ">
                                        {{ experiencia.scoreTotal ?? 'Média Não Calculada' }}
                                    </span>
                                </Chip>
                            </div>
                        </AccordionHeader>
                        <AccordionContent>
                            <div class="!text-base !text-gray-700 !leading-relaxed !mb-6 ml-2 ">
                                <strong class="text-primary">Descrição</strong>
                                <p class="ml-2 mt-2">{{ experiencia.texto }}</p>
                            </div>

                            <div>
                                <strong class="text-primary ml-2">Tecnologias</strong>
                                <div class="flex flex-wrap gap-2 mt-1">
                                    <div class="flex flex-col gap-2 mt-1">
                                        <Chip 
                                            v-tooltip.right="classificacaoScore(score.score)"
                                            v-for="(score, i) in experiencia.scores" :key="i"
                                            class="bg-blue-100 text-blue-800 ml-2 mt-2">
                                            <strong>{{ score.tecnologia.toUpperCase() }}</strong>

                                            <Badge class="ml-auto" severity="sucess">
                                                {{ score.score }}
                                            </Badge>
                                        </Chip>
                                    </div>
                                </div>
                            </div>
                        </AccordionContent>
                    </AccordionPanel>
                </Accordion>


            </Panel>
        </div>
    </section>
    <section class="px-10 py-1">
        <div>
        </div>
    </section>
</template>

<style scoped></style>
