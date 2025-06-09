export type cargo = {
    id: number,
    nome: string,
    descricao:string,
    requisitos: requisitos[],
}

type requisitos = {
    id: number,
    texto: string,
    score: number
}

export const cargosExample: cargo[] = [
    {
        id: 1,
        nome: "Desenvolvedor Frontend",
        descricao: "Responsável pelo desenvolvimento da interface de aplicações web.",
        requisitos: [
            { id: 1, texto: "Experiência com React", score: 5 },
            { id: 2, texto: "Conhecimento em TypeScript", score: 4 },
            { id: 3, texto: "Noções de UX/UI", score: 3 }
        ]
    },
    {
        id: 2,
        nome: "Analista de Dados",
        descricao: "Analisa e interpreta grandes volumes de dados para apoiar decisões.",
        requisitos: [
            { id: 1, texto: "Conhecimento em SQL", score: 5 },
            { id: 2, texto: "Experiência com Python", score: 4 },
            { id: 3, texto: "Habilidade em visualização de dados", score: 3 }
        ]
    },
    {
        id: 3,
        nome: "Gerente de Projetos",
        descricao: "Gerencia equipes e cronogramas para garantir a entrega de projetos.",
        requisitos: [
            { id: 1, texto: "Certificação PMP", score: 5 },
            { id: 2, texto: "Experiência em metodologias ágeis", score: 4 },
            { id: 3, texto: "Boa comunicação", score: 3 }
        ]
    },
    {
        id: 4,
        nome: "Designer Gráfico",
        descricao: "Cria elementos visuais para campanhas e produtos digitais.",
        requisitos: [
            { id: 1, texto: "Domínio do Adobe Photoshop", score: 5 },
            { id: 2, texto: "Conhecimento em Illustrator", score: 4 },
            { id: 3, texto: "Portfólio de trabalhos", score: 3 }
        ]
    },
    {
        id: 5,
        nome: "Administrador de Redes",
        descricao: "Gerencia e mantém a infraestrutura de redes da empresa.",
        requisitos: [
            { id: 1, texto: "Experiência com roteadores e switches", score: 5 },
            { id: 2, texto: "Conhecimento em segurança de redes", score: 4 },
            { id: 3, texto: "Certificação CCNA", score: 3 }
        ]
    }
];

