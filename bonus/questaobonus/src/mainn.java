import java.util.*;
 
public class mainn {
 
    // ---------- FIFO ----------
    // Implementação do algoritmo FIFO (First-In, First-Out)
    // A primeira página que entrou é a primeira a sair
    public static int fifo(int[] referencias, int frames) {
        Queue<Integer> memoria = new LinkedList<>(); // Fila para controlar a ordem de entrada
        Set<Integer> paginas = new HashSet<>();      // Conjunto para verificar rápido se página está na memória
        int faltas = 0; // Contador de faltas de página
 
        for (int pagina : referencias) {
            // Se a página não está na memória, ocorre falta
            if (!paginas.contains(pagina)) { 
                faltas++; // Incrementa o contador de faltas
                
                // Se a memória está cheia, preciso remover uma página
                if (memoria.size() == frames) { 
                    int removida = memoria.poll(); // Remove a mais antiga (início da fila)
                    paginas.remove(removida);     // Remove do conjunto também
                }
                
                // Adiciona a nova página
                memoria.add(pagina);
                paginas.add(pagina);
            }
        }
        return faltas;
    }
 
    // ---------- LRU ----------
    // Implementação do algoritmo LRU (Least Recently Used)
    public static int lru(int[] referencias, int frames) {
        Map<Integer, Integer> tempoUso = new HashMap<>(); // Mapa: página -> último tempo de uso
        Set<Integer> memoria = new HashSet<>();           // Páginas atualmente na memória
        int faltas = 0;
        int tempo = 0; // Contador de tempo para saber qual página foi usada mais recentemente
 
        for (int pagina : referencias) {
            tempo++; // A cada referência, o tempo avança
            
            if (!memoria.contains(pagina)) { // Falta de página
                faltas++;
                
                if (memoria.size() == frames) {
                    // Encontra a página menos recentemente usada
                    int lruPage = -1;
                    int menorTempo = Integer.MAX_VALUE;
                    
                    for (int p : memoria) {
                        if (tempoUso.get(p) < menorTempo) {
                            menorTempo = tempoUso.get(p);
                            lruPage = p;
                        }
                    }
                    
                    // Remove a página menos recentemente usada
                    memoria.remove(lruPage);
                    tempoUso.remove(lruPage);
                }
                
                memoria.add(pagina); // Adiciona a nova página
            }
            
            // Atualiza o tempo de uso da página (mesmo se já estava na memória)
            tempoUso.put(pagina, tempo);
        }
        return faltas;
    }
 
    // ---------- LRU OTIMIZADO ----------
    // Usa LinkedHashSet que mantém a ordem de inserção
    public static int lruOtimizado(int[] referencias, int frames) {
        Map<Integer, Integer> tempoUso = new HashMap<>();
        LinkedHashSet<Integer> memoria = new LinkedHashSet<>(); // Mantém a ordem de acesso
        int faltas = 0;
        int tempo = 0;
 
        for (int pagina : referencias) {
            tempo++;
            
            if (memoria.contains(pagina)) {
                // Se a página já está na memória, movo para o final (indicando uso recente)
                memoria.remove(pagina);
                memoria.add(pagina);
            } else {
                faltas++; // Falta de página
                
                if (memoria.size() == frames) {
                    // No LinkedHashSet, a primeira página é a menos recentemente usada
                    int lruPage = memoria.iterator().next(); // Pega a primeira
                    memoria.remove(lruPage);
                    tempoUso.remove(lruPage);
                }
                
                memoria.add(pagina); // Adiciona no final (mais recente)
            }
            
            tempoUso.put(pagina, tempo);
        }
        return faltas;
    }
 
    // ---------- VALIDAÇÃO DE ENTRADA ----------
    // Função para validar se os dados de entrada fazem sentido
    public static boolean validarEntrada(int frames, int[] referencias) {
        if (frames <= 0) {
            System.out.println("Erro: Número de frames deve ser positivo");
            return false;
        }
        if (referencias.length == 0) {
            System.out.println("Erro: Sequência de referências não pode estar vazia");
            return false;
        }
        return true;
    }
 
    // ---------- MÉTODO PRINCIPAL ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
 
        // Entrada do usuário
        System.out.print("Digite o número de frames: ");
        int frames = sc.nextInt();
 
        System.out.print("Digite a sequência de referências (ex: 7 0 1 2 0 3 0 4): ");
        sc.nextLine(); // limpar buffer do teclado
        String[] entradas = sc.nextLine().trim().split("\\s+");
        
        // Converte a entrada string para números
        int[] referencias = new int[entradas.length];
        for (int i = 0; i < entradas.length; i++)
            referencias[i] = Integer.parseInt(entradas[i]);
 
        // Verifica se a entrada é válida
        if (!validarEntrada(frames, referencias)) {
            sc.close();
            return;
        }
 
        // Executa os três algoritmos e conta as faltas
        int faltasFIFO = fifo(referencias, frames);
        int faltasLRU = lru(referencias, frames);
        int faltasLRUOtimizado = lruOtimizado(referencias, frames);
 
        // Calcula as taxas de faltas em porcentagem
        double taxaFIFO = (double) faltasFIFO / referencias.length * 100;
        double taxaLRU = (double) faltasLRU / referencias.length * 100;
        double taxaLRUOtimizado = (double) faltasLRUOtimizado / referencias.length * 100;
 
        // Exibe os resultados de cada algoritmo
        System.out.println("\n===== RESULTADOS =====");
        System.out.println("Algoritmo FIFO:");
        System.out.println(" - Faltas de página: " + faltasFIFO);
        System.out.printf(" - Taxa de faltas: %.2f%%\n", taxaFIFO);
 
        System.out.println("\nAlgoritmo LRU:");
        System.out.println(" - Faltas de página: " + faltasLRU);
        System.out.printf(" - Taxa de faltas: %.2f%%\n", taxaLRU);
 
        System.out.println("\nAlgoritmo LRU Otimizado:");
        System.out.println(" - Faltas de página: " + faltasLRUOtimizado);
        System.out.printf(" - Taxa de faltas: %.2f%%\n", taxaLRUOtimizado);
 
        // Comparação entre os algoritmos
        System.out.println("\n===== ANÁLISE COMPARATIVA =====");
        int diffFIFO_LRU = Math.abs(faltasFIFO - faltasLRU);
        double diffPercentual = Math.abs(taxaFIFO - taxaLRU);
        
        System.out.printf("Diferença entre FIFO e LRU: %d faltas (%.1f%%)\n", 
                         diffFIFO_LRU, diffPercentual);
        
        // Decidindo qual algoritmo foi melhor
        if (faltasLRU < faltasFIFO) {
            System.out.println("O algoritmo LRU apresentou melhor desempenho, pois teve menos faltas de página.");
        } else if (faltasLRU > faltasFIFO) {
            System.out.println("O algoritmo FIFO apresentou melhor desempenho neste caso específico.");
        } else {
            System.out.println("Ambos os algoritmos tiveram desempenho equivalente nesta sequência.");
        }
 
        // Explicação dos algoritmos para entender melhor
        System.out.println("\n===== ANÁLISE DETALHADA =====");
        System.out.println("Vantagens do FIFO:");
        System.out.println("- Implementação simples e eficiente em memória");
        System.out.println("- Baixa sobrecarga computacional");
        System.out.println("- Previsível e fácil de implementar");
 
        System.out.println("\nVantagens do LRU:");
        System.out.println("- Melhor aproveitamento do princípio da localidade");
        System.out.println("- Geralmente menor taxa de faltas em cenários reais");
        System.out.println("- Mais inteligente na seleção de páginas para substituir");
 
        System.out.println("\nDesvantagens:");
        System.out.println("- FIFO: Pode sofrer com o 'Belady's Anomaly' (mais frames podem gerar mais faltas)");
        System.out.println("- FIFO: Não considera padrões de acesso recentes");
        System.out.println("- LRU: Alto custo para manter informações de uso");
        System.out.println("- LRU: Requer hardware adicional ou software complexo para implementação eficiente");
 
        System.out.println("\nObservações Técnicas:");
        System.out.println("- LRU Original: Complexidade O(n) para encontrar página a substituir");
        System.out.println("- LRU Otimizado: Complexidade O(1) usando LinkedHashSet");
        System.out.println("- Em geral, LRU tem melhor desempenho mas consome mais recursos");
        System.out.println("- A escolha depende do trade-off entre desempenho e custo computacional");
 
        // Estatísticas extras para análise
        System.out.println("\n===== ESTATÍSTICAS ADICIONAIS =====");
        System.out.println("Total de referências: " + referencias.length);
        System.out.println("Número de frames: " + frames);
        
        // Calcula quanto o LRU melhorou em relação ao FIFO
        if (faltasFIFO > 0) {
            System.out.printf("Eficiência do LRU vs FIFO: %.1f%% de redução de faltas\n", 
                             ((double)(faltasFIFO - faltasLRU) / faltasFIFO * 100));
        }
        
        sc.close(); // Fecha o scanner para liberar recursos
    }
}