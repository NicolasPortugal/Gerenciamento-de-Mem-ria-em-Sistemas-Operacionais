# Lista de dicionários representando as partições fixas da memória
particoes = [
    {'tamanho': 100, 'livre': True,  'processo': None, 'frag_int': 0},
    {'tamanho': 150, 'livre': True,  'processo': None, 'frag_int': 0},
    {'tamanho': 200, 'livre': True,  'processo': None, 'frag_int': 0},
    {'tamanho': 250, 'livre': True,  'processo': None, 'frag_int': 0},
    {'tamanho': 300, 'livre': True,  'processo': None, 'frag_int': 0}
]

# Função para alocar um processo usando a estratégia First-Fit
def alocar_processo(nome, tamanho):
    for particao in particoes:  # Percorre as partições em ordem
        # Testa se a partição está livre e se o processo cabe nela
        if particao['livre'] and tamanho <= particao['tamanho']:
            particao['livre'] = False            # Marca a partição como ocupada
            particao['processo'] = nome          # Associa o processo à partição
            particao['frag_int'] = particao['tamanho'] - tamanho  # Calcula o desperdício (fragmentação interna)
            return f"Processo {nome} alocado na partição de {particao['tamanho']} unidades. Fragmentação interna: {particao['frag_int']}" 
    # Se não encontrou partição adequada, retorna uma mensagem de erro
    return f"Não há partição disponível para o processo {nome} (tamanho {tamanho})"

# Função para liberar o processo (liberar a partição que ele ocupa)
def liberar_processo(nome):
    for particao in particoes:
        if particao['processo'] == nome:  # Procura qual partição está com o processo
            particao['livre'] = True      # Libera a partição
            particao['processo'] = None   # Remove referência ao processo
            particao['frag_int'] = 0      # Zera a fragmentação interna
            return f"Processo {nome} liberado."
    # Caso não encontre o processo
    return f"Processo {nome} não encontrado."

# Função que mostra o estado atual da memória
def exibir_memoria():
    estado = []
    for i, p in enumerate(particoes):
        # Verifica se a partição está livre ou ocupada, e por qual processo
        if p['livre']:
            status = 'LIVRE'
        else:
            status = f"OCUPADO ({p['processo']})"
        # Monta a string descritiva desta partição
        estado.append(f"Partição {i+1} - Tamanho: {p['tamanho']} - {status} - Fragmentação interna: {p['frag_int']}")
    return '\n'.join(estado)

# Função para somar toda a fragmentação interna presente
def fragmentacao_interna_total():
    return sum(p['frag_int'] for p in particoes)

# Bloco de teste do código:
print(alocar_processo('P1', 90))       # Aloca P1
print(alocar_processo('P2', 140))      # Aloca P2
print(alocar_processo('P3', 180))      # Aloca P3
print(liberar_processo('P2'))          # Libera P2
print(alocar_processo('P4', 100))      # Tenta alocar P4 onde P2 estava
print(alocar_processo('P5', 350))      # Tenta alocar P5 (muito grande, deverá dar erro)
print(exibir_memoria())                # Mostra o estado completo da memória
print(f"Fragmentação interna total: {fragmentacao_interna_total()}")  # Mostra desperdício total atual


