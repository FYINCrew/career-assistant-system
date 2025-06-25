
export function getCorPorScore(score: number): string {
    if (score == 90) return '#08A8C4';
    if (score >= 80) return '#11BAAF';
    if (score >= 70) return '#34D399';
    if (score >= 60) return '#A3E635';
    if (score >= 50) return '#FBBF24';
    if (score >= 40) return '#F97316';
    return '#7B7B7B';
}

export function getClassificacaoPorScore(score: number, message: string): string {
    if (score >= 80 && score < 90) {
        return 'Expert';
    } else if (score >= 70 && score < 80) {
        return 'Avançado';
    } else if (score >= 60 && score < 70) {
        return 'Proficiente';
    } else if (score >= 50 && score < 60) {
        return 'Intermediário';
    } else if (score >= 40 && score < 50) {
        return 'Básico';
    } else if (score == 90) {
        return 'Especialista';
    } else {
        return message || 'Sem classificação';
    }
}