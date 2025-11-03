import time
import statistics

# --------------------------------------------------------------
# Programa: Comparação de tempos de alocação na pilha e no heap
# --------------------------------------------------------------
# Objetivo:
# Medir e comparar o tempo médio de criação/destruição de 1.000.000
# de variáveis locais (simulando alocação na pilha) e de objetos
# criados dinamicamente (simulando alocação no heap).
#
# Observação:
# Em Python, toda variável é tecnicamente um objeto no heap,
# mas podemos simular o comportamento de "pilha" limitando o
# escopo à função (variáveis locais) e "heap" criando explicitamente
# objetos via lista/dicionário para representar alocações dinâmicas.
# --------------------------------------------------------------

NUM_TESTES = 5           # número de repetições para média
NUM_VARIAVEIS = 1_000_000  # número de variáveis/objetos

def teste_pilha():
    """Cria e destrói 1.000.000 de variáveis locais (simula alocação na pilha)."""
    for _ in range(NUM_VARIAVEIS):
        x = 42  # variável local (escopo limitado à função)
    # ao sair da função, todas as variáveis locais são desalocadas automaticamente


def teste_heap():
    """Cria e destrói 1.000.000 de objetos dinamicamente (simula alocação no heap)."""
    lista = []
    for _ in range(NUM_VARIAVEIS):
        lista.append(object())  # cria objetos dinamicamente
    del lista  # desaloca explicitamente (libera referência)


def medir_tempo(funcao):
    """Mede o tempo de execução de uma função."""
    inicio = time.time()
    funcao()
    fim = time.time()
    return fim - inicio


def main():
    tempos_pilha = []
    tempos_heap = []

    print(f"Executando {NUM_TESTES} testes com {NUM_VARIAVEIS:,} variáveis/objetos...\n")

    for i in range(NUM_TESTES):
        t_pilha = medir_tempo(teste_pilha)
        t_heap = medir_tempo(teste_heap)

        tempos_pilha.append(t_pilha)
        tempos_heap.append(t_heap)

        print(f"Teste {i+1}:")
        print(f"  Tempo (pilha): {t_pilha:.4f} s")
        print(f"  Tempo (heap):  {t_heap:.4f} s\n")

    media_pilha = statistics.mean(tempos_pilha)
    media_heap = statistics.mean(tempos_heap)

    # Cálculo da diferença percentual
    diferenca_percentual = ((media_heap - media_pilha) / media_pilha) * 100

    print("=== RESULTADOS FINAIS ===")
    print(f"Tempo médio (pilha): {media_pilha:.4f} s")
    print(f"Tempo médio (heap):  {media_heap:.4f} s")
    print(f"Diferença percentual: {diferenca_percentual:.2f}% (heap mais lento)\n")

    # ----------------------------------------------------------
    # Comentário sobre os resultados:
    #
    # Normalmente, a alocação "na pilha" (variáveis locais) é
    # mais rápida, pois envolve apenas manipulação de ponteiros
    # e ocorre automaticamente ao entrar/sair da função.
    #
    # Já a alocação "no heap" é mais lenta, pois o Python precisa:
    # - Criar novos objetos dinamicamente
    # - Gerenciar referências e garbage collector
    # - Fazer mais chamadas ao sistema e gerenciamento de memória
    #
    # Em diferentes execuções, os resultados podem variar devido a:
    # - Estado do garbage collector
    # - Carga da CPU
    # - Outras tarefas do sistema operacional
    #
    # Portanto, pequenas flutuações são esperadas entre execuções.
    # ----------------------------------------------------------

if __name__ == "__main__":
    main()
