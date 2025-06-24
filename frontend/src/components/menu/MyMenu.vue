<template>
    <div class="card flex justify-center">
        <Drawer v-model:visible="visible" >
            <template #container="{ closeCallback }">
                <div class="flex flex-col h-full   ">
                    <div class="flex items-center justify-between px-6 pb-1 shrink-0 bg-[#10b9816b] h-15">
                        <span class="inline-flex items-center gap-2 ">
                            <span
                                class="!text-4xl font-semibold tracking-widest !text-black ml-2 wdxl-lubrifont-sc-regular">F
                                Y I N C</span>
                        </span>
                        <span>
                            <Button type="button" @click="closeCallback" icon="pi pi-times" rounded outlined class="!text-black !border-black"></Button>
                        </span>
                    </div>
                    <div class="overflow-y-auto ">
                        <ul class="list-none pt-4 pl-4 m-0">
                            <li>
                                <div v-ripple v-styleclass="{
                                    selector: '@next',
                                    enterFromClass: 'hidden',
                                    enterActiveClass: 'animate-slidedown',
                                    leaveToClass: 'hidden',
                                    leaveActiveClass: 'animate-slideup'
                                }" class="p-4 flex items-center justify-between !text-black cursor-pointer p-ripple">
                                    <span class="text-[20px] font-medium !text-black">Cargo</span>
                                    <i class="pi pi-chevron-down !text-black"></i>
                                </div>
                                <ul class="list-none p-0 m-0 overflow-hidden hidden">
                                    <li>
                                        <a v-ripple @click="router.push({ name: 'listarCargos' })"
                                            class="flex items-center cursor-pointer p-4 ml-4 rounded !text-black hover:bg-surface-100 duration-150 transition-colors p-ripple">
                                            <i class="pi pi-list mr-2 !text-black"></i>
                                            <span class="font-medium !text-black">Listar Cargos</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a v-ripple @click="router.push({ name: 'cadastrarCargos' })"
                                            class="flex items-center cursor-pointer p-4 ml-4 rounded !text-black hover:bg-surface-100 duration-150 transition-colors p-ripple">
                                            <i class="pi pi-plus mr-2 !text-black"></i>
                                            <span class="font-medium !text-black">Cadastrar Cargo</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="list-none pl-4 m-0">
                            <li>
                                <div v-ripple v-styleclass="{
                                    selector: '@next',
                                    enterFromClass: 'hidden',
                                    enterActiveClass: 'animate-slidedown',
                                    leaveToClass: 'hidden',
                                    leaveActiveClass: 'animate-slideup'
                                }" class="p-4 flex items-center justify-between !text-black cursor-pointer p-ripple">
                                    <span class="text-[20px] font-medium !text-black">Funcionário</span>
                                    <i class="pi pi-chevron-down !text-black"></i>
                                </div>
                                <ul class="list-none p-0 m-0 overflow-hidden hidden">
                                    <li>
                                        <a v-ripple @click="router.push({ name: 'listarFuncionarios' })"
                                            class="flex items-center cursor-pointer p-4 ml-4 rounded !text-black hover:bg-surface-100 duration-150 transition-colors p-ripple">
                                            <i class="pi pi-list mr-2 !text-black"></i>
                                            <span class="font-medium !text-black">Listar Funcionários</span>
                                        </a>
                                    </li>
                                    
                                </ul>
                            </li>
                        </ul>
                    </div>

                </div>
            </template>
        </Drawer>
        <div class="">
            <Button variant="text" @click="visible = true" class="ml-7 !text-black text-2xl">
                <template #icon>
                    <i class="pi pi-bars !text-black !text-xl"></i>
                </template>
            </Button>
            <span class="!text-3xl font-semibold tracking-widest !text-black ml-2 wdxl-lubrifont-sc-regular ">F Y I N C
            </span>
        </div>
    </div>
</template>

<script setup lang="ts">
import router from "@/router";
import { Button } from "primevue";
import { ref } from "vue";

const visible = ref(false);
</script>
