export type cargo = {
    id?: number,
    sigla?: string,
    nome: string,
    descricao:string,
    requisitos: requisitos[],
}

export type requisitos = {
    id?: number,
    nome: string,
    score: number
}

