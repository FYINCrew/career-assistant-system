import warnings

def fxn(): warnings.warn("deprecated", DeprecationWarning)
with warnings.catch_warnings(action="ignore"): fxn()

import sys, io, joblib, numpy as np
from json import loads, dumps
from sentence_transformers import SentenceTransformer

sys.stdin = io.TextIOWrapper(sys.stdin.buffer, encoding='utf-8')

if __name__ == "__main__":
    try:
        INPUT_DATA = sys.stdin.read()
        profile = loads(INPUT_DATA)
        for exp in profile["experiencias"]:
            texto = str(exp["texto"])
            scores = exp["scores"]
            modelo_carregado = joblib.load('modelo_mlp_treinado.pkl')
            model = SentenceTransformer("paraphrase-multilingual-MiniLM-L12-v2")
            embedding_texto = model.encode([texto.lower()])
            techs = []
            sum_scores = 0
            for score in scores:
                embedding_tecnologia = model.encode([str(score["tecnologia"])])
                embedding_novo = np.concatenate([embedding_texto, embedding_tecnologia], axis=1)
                score_mlp = modelo_carregado.predict(embedding_novo)[0]
                temp_tech = {
                    "id": score["id"],
                    "tecnologia": str(score["tecnologia"]),
                    "score": f'{score_mlp:.2f}'
                }
                techs.append(temp_tech)
                sum_scores+=score_mlp
            
            result = {
                "id": exp["id"],
                "texto": exp["texto"],
                "total_score": round(sum_scores / len(scores), 2),
                "scores": techs
            }
            print(dumps(result))
    except: raise
    finally: raise SystemExit(0)