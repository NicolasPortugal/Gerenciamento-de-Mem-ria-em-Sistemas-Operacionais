#include <stdio.h>
#include <stdlib.h>
/*
stdio.h permite usar funções de entrada e saída como printf().
stdlib.h é necessário para malloc() e free() — funções de alocação dinâmica.
*/

int main() {
    // 1. Declarar um array estático de 5 inteiros e preenchê-lo com valores de 1 a 5
    int array_estatico[5];
    for (int i = 0; i < 5; i++) {
        array_estatico[i] = i + 1;
    }
    /*
    Aqui criamos um array estático (fixo) de 5 inteiros.
    Ele é armazenado na stack.
    O 'for' percorre os índices de 0 a 4, preenchendo o array com valores de 1 a 5.
    */



    // 2. Alocar dinamicamente um array de 10 inteiros usando malloc
    int *array_dinamico = (int *) malloc(10 * sizeof(int));
    /*
    Aqui criamos um ponteiro para um 'int'.
    'malloc(10 * sizeof(int))' reserva espaço para 10 inteiros na heap.
    'sizeof(int)' retorna quantos bytes um inteiro ocupa (geralmente 4 bytes).
    O '(int *)' converte o ponteiro genérico retornado por 'malloc()' para o tipo correto.

    Exemplo:
    Se 'sizeof(int) = 4', então o total alocado será 10 * 4 = 40 bytes
    */



    // 3. Verificar se a alocação foi bem-sucedida
    if (array_dinamico == NULL) {
        printf("Erro: falha ao alocar memória.\n");
        return 1; // Encerra o programa com código de erro
    }
    /*
    'malloc()' retorna 'NULL' se a alocação falhar (por exemplo, falta de memória).
    É boa prática sempre verificar isso antes de usar o ponteiro.
    */


    // Preencher o array dinâmico com valores de 10 a 19
    for (int i = 0; i < 10; i++) {
        array_dinamico[i] = 10 + i;
    }
    /*
    Acessamos o array usando o operador [], como qualquer outro array.
    Ele é armazenado na heap, em uma região diferente da stack.
    Aqui os valores são de 10 a 19.
    */



    // 4. Imprimir os valores e endereços de memória dos arrays
    printf("=== ARRAY ESTATICO ===\n");
    for (int i = 0; i < 5; i++) {
        printf("array_estatico[%d] = %d \t Endereco: %p\n", i, array_estatico[i], (void*)&array_estatico[i]);
    }

    printf("\n=== ARRAY DINAMICO ===\n");
    for (int i = 0; i < 10; i++) {
        printf("array_dinamico[%d] = %d \t Endereco: %p\n", i, array_dinamico[i], (void*)&array_dinamico[i]);
    }
    /*
    '&array_estatico[i]' obtém o endereço de cada elemento.
    '%p' é o formato correto para imprimir endereços (ponteiros).
    '(void*)' converte o tipo de ponteiro para um formato genérico.
    O mesmo é feito para o array dinâmico.

    Aqui dá pra ver que os endereços dos arrays são muito diferentes:
    O array estático (na stack) tem endereços mais próximos e geralmente mais baixos.
    O array dinâmico (na heap) fica em outra região de memória, geralmente mais alta.
    */





    // 5. Calcular e exibir a diferença entre os endereços base
    printf("\n=== DIFERENCA ENTRE ENDERECOS ===\n");
    printf("Endereco base array_estatico: %p\n", (void*)array_estatico);
    printf("Endereco base array_dinamico: %p\n", (void*)array_dinamico);

    // A diferença é apenas ilustrativa (não tem significado prático)
    long long diferenca = (char*)array_dinamico - (char*)array_estatico;
    printf("Diferenca (em bytes): %lld\n", diferenca);
    /*
    Convertemos para '(char*)' para medir a diferença em bytes, já que char ocupa 1 byte.
    A diferença não tem um significado prático — serve apenas para demonstrar que stack e heap são áreas diferentes da memória.
    */





    // 6. Liberar a memória alocada dinamicamente
    free(array_dinamico);
    array_dinamico = NULL; // Boa prática: evitar ponteiro solto

    printf("\nMemoria dinamica liberada com sucesso.\n");

    return 0;
}
/*
    free() devolve a memória da heap para o sistema.
    Atribuir NULL ao ponteiro evita que ele aponte para um espaço já liberado (isso previne bugs e falhas).
*/






/*
Conceitos Fundamentais:

Antes de entender o código, precisamos compreender como a memória funciona em um programa em C.

1. Stack (Pilha)
    A stack é uma região da memória usada para armazenar variáveis locais e informações de execução de funções.
Quando você declara algo como:

    int x = 10;
    int array[5];

Essas variáveis são guardadas na stack.
A stack é rápida, mas tem tamanho limitado e as variáveis são automaticamente destruídas quando a função termina.


2. Heap (Montão)
A heap é uma área da memória usada para alocação dinâmica — ou seja, para reservar espaço em tempo de execução (durante o funcionamento do programa).

Para usar a heap, usamos funções como:

    malloc(), calloc(), realloc(), free()
A memória alocada não é liberada automaticamente — o programador deve liberar manualmente usando free().
É útil quando você não sabe o tamanho necessário da memória com antecedência.


3. Ponteiros
Um ponteiro é uma variável que armazena um endereço de memória.

 int *p;
significa que p guarda o endereço de um int.


Se fizermos:

 int x = 5;
 p = &x;

então p aponta para x.
*p acessa o valor guardado naquele endereço.

*/