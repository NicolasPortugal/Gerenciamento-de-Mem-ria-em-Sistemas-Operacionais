import time          # Importa o módulo 'time' para medir o tempo de execução
import statistics    # Importa o módulo 'statistics' para calcular médias


# PROGRAMA: Comparação entre alocação na PILHA e no HEAP em Python

# Objetivo:
# - Medir e comparar o tempo de criação e destruição de 1.000.000
#   de variáveis locais (pilha) e 1.000.000 de objetos dinâmicos (heap).
# - Mostrar médias e diferença percentual entre os tempos.
#
# OBS: Em Python, tecnicamente tudo é objeto (logo, heap), mas
#      simulamos “pilha” com variáveis locais dentro de funções
#      e “heap” criando objetos dinamicamente.


NUM_TESTES = 5             # Quantas vezes cada teste será executado (para calcular a média)
NUM_VARIAVEIS = 1_000_000  # Quantas variáveis ou objetos serão criados em cada teste


def teste_pilha():
    """
    Função que simula alocação na pilha:
    Cria e destrói 1.000.000 de variáveis locais simples.
    """
    for _ in range(NUM_VARIAVEIS):  # Executa o loop 1.000.000 de vezes
        x = 42                      # Cria uma variável local (escopo apenas dentro da função)
    # Ao sair da função, todas as variáveis locais são automaticamente desalocadas.


def teste_heap():
    """
    Função que simula alocação no heap:
    Cria e destrói 1.000.000 de objetos dinamicamente.
    """
    lista = []                      # Cria uma lista vazia para armazenar objetos (na heap)
    for _ in range(NUM_VARIAVEIS):  # Executa o loop 1.000.000 de vezes
        lista.append(object())      # Cria um novo objeto e adiciona na lista (alocação dinâmica)
    del lista                       # Remove a lista, liberando as referências aos objetos


def medir_tempo(funcao):
    """
    Mede o tempo de execução de uma função qualquer.
    """
    inicio = time.time()            # Marca o tempo inicial (em segundos)
    funcao()                        # Executa a função recebida como parâmetro
    fim = time.time()               # Marca o tempo final
    return fim - inicio             # Retorna o tempo total gasto (diferença entre fim e início)


def main():
    """Função principal do programa."""
    
    # Listas para armazenar os tempos medidos de cada teste
    tempos_pilha = []
    tempos_heap = []

    print(f"Executando {NUM_TESTES} testes com {NUM_VARIAVEIS:,} variáveis/objetos...\n")

    # Loop principal: executa várias vezes para obter médias mais precisas
    for i in range(NUM_TESTES):
        t_pilha = medir_tempo(teste_pilha)  # Mede o tempo do teste de pilha
        t_heap = medir_tempo(teste_heap)    # Mede o tempo do teste de heap

        # Armazena os resultados em listas
        tempos_pilha.append(t_pilha)
        tempos_heap.append(t_heap)

        # Mostra o resultado de cada execução
        print(f"Teste {i+1}:")
        print(f"  Tempo (pilha): {t_pilha:.4f} s")
        print(f"  Tempo (heap):  {t_heap:.4f} s\n")

    # Calcula a média dos tempos de cada tipo de alocação
    media_pilha = statistics.mean(tempos_pilha)
    media_heap = statistics.mean(tempos_heap)

    # Calcula a diferença percentual entre os tempos médios
    diferenca_percentual = ((media_heap - media_pilha) / media_pilha) * 100

    # Exibe os resultados finais de forma organizada
    print("=== RESULTADOS FINAIS ===")
    print(f"Tempo médio (pilha): {media_pilha:.4f} s")
    print(f"Tempo médio (heap):  {media_heap:.4f} s")
    print(f"Diferença percentual: {diferenca_percentual:.2f}% (heap mais lento)\n")

    # ----------------------------------------------------------
    # COMENTÁRIOS SOBRE OS RESULTADOS
    # ----------------------------------------------------------
    # → Em geral, o tempo da "pilha" será menor, pois variáveis locais
    #   são criadas e destruídas rapidamente, sem gerenciar referências.
    #
    # → O "heap" tende a ser mais lento porque:
    #   - O Python precisa criar novos objetos (chamadas ao sistema).
    #   - Precisa gerenciar ponteiros e referências.
    #   - O garbage collector atua para liberar memória.
    #
    # → Os tempos podem variar entre execuções por fatores como:
    #   - Estado atual do garbage collector do Python.
    #   - Processos rodando no sistema operacional.
    #   - Carga do processador no momento do teste.
    #
    # → Pequenas diferenças de tempo são esperadas e normais.
    # ----------------------------------------------------------

# Verifica se o arquivo está sendo executado diretamente
if __name__ == "__main__":
    main()  # Executa o programa principal
