import type { cargo } from "../cargos/cargoType"

export type funcionario = {
    id: number,
    nome: string,
    email:string,
    cargoAtual: cargo,
    cargoDesejado: cargo,
    experiencias: experiencia[],
    promocaoRecomendada: boolean,
}

type experiencia = {
    id: number,
    texto: string,
    scores: score[]
    scoreTotal: number
}

type score = {
    id: number,
    tecnologia: string,
    score: number
}