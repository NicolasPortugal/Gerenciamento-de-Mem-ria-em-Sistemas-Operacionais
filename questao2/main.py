import gc       # Módulo para acessar e controlar o coletor de lixo
import sys      # Módulo para verificar a contagem de referência

# 1. Classe Objeto simulando uso intensivo de memória
class Objeto:
    def __init__(self, nome):
        self.nome = nome
        self.dados = [0] * 10**6  # Simula uso de memória
        print(f'Objeto {self.nome} criado')    # Notifica criação
    def __del__(self):
        print(f'Objeto {self.nome} destruído') # Notifica destruição

# ---------------- CENÁRIO 1: Destruição automática por contagem de referências -------------
obj_a = Objeto('A')                       # Cria o objeto
print("Contagem de referências de obj_a (inicial):", sys.getrefcount(obj_a))  # Conta referências
obj_a = None                              # Remove a referência
# Objeto destruído automaticamente (contagem zera)

# ---------------- CENÁRIO 2: Referências circulares ----------------------
obj1 = Objeto('B')
obj2 = Objeto('C')
obj1.amigo = obj2       # obj1 referencia obj2
obj2.amigo = obj1       # obj2 referencia obj1 -> ciclo
print("Contagem de referências: obj1:", sys.getrefcount(obj1), "obj2:", sys.getrefcount(obj2))
del obj1                # Remove referência forte a obj1 (mas obj2 ainda referencia obj1)
del obj2                # Remove referência forte a obj2 (mas ambos ainda se referenciam)
# Objetos não são destruídos ainda -- somente o coletor geracional pode remover
print("Forçando coleta de lixo...")
gc.collect()            # Força a coleta, coletor remove ciclo e destrói os objetos

# ---------------- CENÁRIO 3: Auto-referência ----------------------------
obj_x = Objeto('D')
obj_x.eu_mesmo = obj_x  # Auto-referência
print("Contagem de referências obj_x:", sys.getrefcount(obj_x))
del obj_x               # Remove referência forte
gc.collect()            # Força a coleta

# --------------- Estatísticas do Garbage Collector -----------------------
print("Estatísticas do garbage collector:", gc.get_stats()[0])
