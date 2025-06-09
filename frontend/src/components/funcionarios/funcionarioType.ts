export type funcionario = {
    id: number,
    nome: string,
    email:string,
    cargoAtual: string,
    cargoFuturo: string,
    experiencias: experiencia[],
    promocaoRecomendada: boolean,
}

type experiencia = {
    id: number,
    texto: string,
    score: score[]
}

type score = {
    id: number,
    tecnologia: string,
    score: number
}