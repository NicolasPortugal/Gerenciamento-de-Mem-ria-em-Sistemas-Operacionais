#include <stdio.h>
#include <stdlib.h>

int main() {
    // 1. Declarar um array estático de 5 inteiros e preenchê-lo com valores de 1 a 5
    int array_estatico[5];
    for (int i = 0; i < 5; i++) {
        array_estatico[i] = i + 1;
    }

    // 2. Alocar dinamicamente um array de 10 inteiros usando malloc
    int *array_dinamico = (int *) malloc(10 * sizeof(int));

    // 3. Verificar se a alocação foi bem-sucedida
    if (array_dinamico == NULL) {
        printf("Erro: falha ao alocar memória.\n");
        return 1; // Encerra o programa com código de erro
    }

    // Preencher o array dinâmico com valores de 10 a 19
    for (int i = 0; i < 10; i++) {
        array_dinamico[i] = 10 + i;
    }

    // 4. Imprimir os valores e endereços de memória dos arrays
    printf("=== ARRAY ESTÁTICO ===\n");
    for (int i = 0; i < 5; i++) {
        printf("array_estatico[%d] = %d \t Endereco: %p\n", i, array_estatico[i], (void*)&array_estatico[i]);
    }

    printf("\n=== ARRAY DINÂMICO ===\n");
    for (int i = 0; i < 10; i++) {
        printf("array_dinamico[%d] = %d \t Endereco: %p\n", i, array_dinamico[i], (void*)&array_dinamico[i]);
    }

    // 5. Calcular e exibir a diferença entre os endereços base
    printf("\n=== DIFERENÇA ENTRE ENDEREÇOS ===\n");
    printf("Endereco base array_estatico: %p\n", (void*)array_estatico);
    printf("Endereco base array_dinamico: %p\n", (void*)array_dinamico);

    // A diferença é apenas ilustrativa (não tem significado prático)
    long long diferenca = (char*)array_dinamico - (char*)array_estatico;
    printf("Diferenca (em bytes): %lld\n", diferenca);

    // 6. Liberar a memória alocada dinamicamente
    free(array_dinamico);
    array_dinamico = NULL; // Boa prática: evitar ponteiro solto

    printf("\nMemória dinâmica liberada com sucesso.\n");

    return 0;
}
