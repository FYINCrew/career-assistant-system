import argparse
import joblib
import numpy as np
from sentence_transformers import SentenceTransformer

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Recebe dois textos: um conteúdo e uma tecnologia.')
    
    parser.add_argument('--texto', type=str, required=True, help='Texto descritivo.')
    parser.add_argument('--tecnologia', type=str, required=True, help='Nome da tecnologia.')

    args = parser.parse_args()

    texto = args.texto
    tecnologia = args.tecnologia
    modelo_carregado = joblib.load('modelo_mlp_treinado.pkl')
    model = SentenceTransformer("paraphrase-multilingual-MiniLM-L12-v2")
    embedding_texto = model.encode([texto])
    embedding_tecnologia = model.encode([tecnologia])
    embedding_novo = np.concatenate([embedding_texto, embedding_tecnologia], axis=1)
    score_mlp = modelo_carregado.predict(embedding_novo)[0]

    print('{ "texto": "'+texto+f'", "tecnologia": "{tecnologia}", '+f'"score": {score_mlp:.2f}' + " }")
    raise SystemExit(0)