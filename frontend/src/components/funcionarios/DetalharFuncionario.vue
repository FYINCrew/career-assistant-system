<script lang="ts" setup>
import useService from '@/composables/useService';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';


const { funcionarioService } = useService()
const route = useRoute();
const funcionarioId = route.params.id;

const nome = ref<string>('');
const cargoAtual = ref<string>('');
const cargoAlmejado = ref<string>('');
onMounted(async () => {
    await funcionarioService.detalharFuncionario(funcionarioId as string).then((response) => {
        nome.value = response.nome;
        cargoAtual.value = response.cargoAtual;
        cargoAlmejado.value = response.cargoFuturo;
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
                            v-tooltip.right="'Descrição da Experiência'" />
                    </div>
                    
                </template>
                <Accordion value="0">
                    <AccordionPanel value="0">
                        <AccordionHeader>Header I - Média:
                            <Chip label="85" class="bg-primary-100 text-primary-800" />
                        </AccordionHeader>
                        <AccordionContent>
                            <p class="m-0">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                incididunt ut labore et
                                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                                nisi ut aliquip
                                ex ea commodo
                                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore
                                eu fugiat nulla
                                pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
                                deserunt mollit anim
                                id est laborum.
                            </p>
                        </AccordionContent>
                    </AccordionPanel>
                    <AccordionPanel value="1">
                        <AccordionHeader>Header II</AccordionHeader>
                        <AccordionContent>
                            <p class="m-0">
                                Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
                                laudantium, totam
                                rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae
                                vitae dicta sunt
                                explicabo. Nemo enim
                                ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur
                                magni dolores
                                eos qui ratione voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non
                                numquam eius modi.
                            </p>
                        </AccordionContent>
                    </AccordionPanel>
                    <AccordionPanel value="2">
                        <AccordionHeader>Header III</AccordionHeader>
                        <AccordionContent>
                            <p class="m-0">
                                At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium
                                voluptatum
                                deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati
                                cupiditate non
                                provident, similique sunt in culpa
                                qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem
                                rerum facilis est
                                et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque
                                nihil impedit quo
                                minus.
                            </p>
                        </AccordionContent>
                    </AccordionPanel>
                </Accordion>

            </Panel>
        </div>
    </section>
    <section class="px-10 py-1">
        <div>
            <Panel toggleable collapsed class="!bg-gray-50 border !border-primary-200 rounded-lg shadow">
                <template #header>
                    <div class="flex items-center justify-between">
                        <span class="text-xl font-semibold ">Pontuação</span>
                        
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
