<script lang="ts" setup>
import useService from '@/composables/useService';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import type { funcionario } from './funcionarioType';
import router from '@/router';
import { useToast } from 'primevue/usetoast';
import { getClassificacaoPorScore, getCorPorScore } from '@/commons/classificarPorScore';


const { funcionarioService } = useService()
const toast = useToast();
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
        funcionarioDetalhes.value = response; //testar observando isso aqui
    }).catch((error) => {
        console.error('Erro ao carregar funcionário:', error);
    });
})

const calcularScore = (id: number) => {
    funcionarioService.calcularScores(id).then((response) => {
        toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Score calculado com sucesso!', life: 3000 });
    }).catch((error) => {
        console.error('Erro ao calcular scores:', error);
         toast.add({ severity: 'error', summary: 'Erro', detail: 'Ocorreu um erro ao calcular o Score!', life: 3000 });
    });
}

</script>

<template>
    <Toast />
    <MyToolbar />
    <section id="detalhes-funcionario" class="flex flex-col items-center justify-center m-8">
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
            <Panel toggleable class=" !bg-gray-50 !border-primary-200 rounded-lg shadow-lg">
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
                                        {{ experiencia.scoreMedia !== null ? experiencia.scoreMedia + ' Pontos' : 'Média Não Calculada' }}
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
                                        <Chip v-tooltip.right="getClassificacaoPorScore(score.score, 'Pontos não Calculados')"
                                            v-for="(score, i) in experiencia.scores" :key="i"
                                            class="bg-blue-100 text-blue-800 ml-2 mt-2">
                                            <strong>{{ score.tecnologia.toUpperCase() }}</strong>

                                            <Badge
                                                class="ml-auto !text-white"
                                                :style="{ backgroundColor: getCorPorScore(score.score) }"
                                            >
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
    <div class="flex justify-end px-10 py-2 ">
        <Button
            class="!text-primary-900 !bg-primary-100 hover:!bg-primary-200 hover:!text-primary-900 transition-colors duration-200 mr-3"
            label="Voltar" variant="link" icon="pi pi-angle-left" @click=" router.push({name:'listarFuncionarios'}) " />
        <Button
            class="!text-primary-900 !bg-primary-400 !text-[1.15rem] hover:!bg-primary-500 hover:!text-primary-100 !transition-colors !ease-in-out !duration-300 teste"
            icon="pi pi-calculator"  label="Calcular Média" @click="calcularScore(Number.parseInt(funcionarioId as string))"  />
    </div>
</template>

<style scoped>


</style>
