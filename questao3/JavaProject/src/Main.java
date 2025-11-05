import java.util.*;
public class Main {
/**
 * Método principal - ponto de entrada do programa
 * Este programa simula o algoritmo de substituição de páginas FIFO
 */
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

   // Solicita o número de frames disponíveis na memória
    System.out.print("Digite o número de frames disponíveis: ");
    int numFrames = sc.nextInt();
    sc.nextLine(); // consome a quebra de linha deixada pelo nextInt()

    // Valida se o número de frames é positivo
    if (numFrames <= 0) {
        System.out.println("Erro: O número de frames deve ser maior que zero.");
        sc.close();
        return;
    }

    // Solicita a sequência de referências de páginas do usuário
    System.out.print("Digite a sequência de páginas (separadas por espaço): ");
    String line = sc.nextLine().trim();
    
    // Verifica se a sequência não está vazia
    if (line.isEmpty()) {
        System.out.println("Erro: Sequência vazia. Encerrando.");
        sc.close();
        return;
    }

    // Converte a string de entrada em um array de inteiros
    String[] parts = line.split("\\s+"); //divide em pedaços separando por espaços em branco
    int[] pages = new int[parts.length]; //Cria um vetor de inteiros com o mesmo tamanho de parts
    for (int i = 0; i < parts.length; i++) {
        try {
            pages[i] = Integer.parseInt(parts[i]); //Converte a string parts[i] em um número inteiro (int) e armazena em pages[i]
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida '" + parts[i] + "'. Use apenas números inteiros.");
            sc.close();
            return;
        }
    }

    // Estruturas de dados para o algoritmo FIFO:
    // Queue: mantém a ordem de chegada das páginas (mais antiga na frente)
    // Set: permite verificação rápida se uma página está na memória
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> inFrames = new HashSet<>();

    int pageFaults = 0; // Contador de faltas de página
    System.out.println("\n--- Simulação FIFO ---");

    // Processa cada referência na sequência
    for (int i = 0; i < pages.length; i++) {
        int currentPage = pages[i];

        // Verifica se a página já está na memória (Page Hit)
        if (inFrames.contains(currentPage)) {
            // Página encontrada na memória - não ocorre falta
            System.out.printf("Referência: %d | Frames: %s | Page Hit | Total Faults: %d%n",
                    currentPage, queueAsList(queue), pageFaults);
        } else {
            // Página não encontrada - ocorre Page Fault
            pageFaults++;
            
            // Verifica se a memória está cheia
            if (queue.size() == numFrames) {
                // Memória cheia: precisa substituir uma página
                // Remove a página mais antiga (início da fila)
                int removedPage = queue.poll();
                inFrames.remove(removedPage);
                
                // Adiciona a nova página
                queue.add(currentPage);
                inFrames.add(currentPage);
                
                System.out.printf("Referência: %d | Frames: %s | Page Fault (substituiu %d) | Total Faults: %d%n",
                        currentPage, queueAsList(queue), removedPage, pageFaults);
            } else {
                // Ainda há espaço livre na memória
                // Apenas adiciona a nova página
                queue.add(currentPage);
                inFrames.add(currentPage);
                System.out.printf("Referência: %d | Frames: %s | Page Fault | Total Faults: %d%n",
                        currentPage, queueAsList(queue), pageFaults);
            }
        }
    }

    // Calcula estatísticas finais
    int totalReferences = pages.length;
    double faultRate = (double) pageFaults / totalReferences * 100.0;

    // Exibe resultados finais
    System.out.println("\n--- Resultados Finais ---");
    System.out.println("Total de referências: " + totalReferences);
    System.out.println("Total de faltas de página: " + pageFaults);
    System.out.println("Total de acertos de página: " + (totalReferences - pageFaults));
    System.out.printf("Taxa de faltas de página: %.2f%%\n", faultRate);
    System.out.printf("Taxa de acertos de página: %.2f%%\n", 100.0 - faultRate);

    sc.close();
}

/**
 * Método auxiliar para converter a Queue em uma Lista para exibição
 * Isso formata a saída como [7, 0, 1] em vez da representação padrão da Queue
 * @param q A fila a ser convertida
 * @return Uma lista contendo os elementos da fila
 */
private static List<Integer> queueAsList(Queue<Integer> q) {
    return new ArrayList<>(q);
}
 }