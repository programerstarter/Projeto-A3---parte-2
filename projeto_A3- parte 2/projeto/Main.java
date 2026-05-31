import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String[] arquivos = {
            "dados_melhor_1k.csv",
            "dados_medio_1k.csv",
            "dados_pior_1k.csv",
            "dados_melhor_1M.csv",
            "dados_medio_1M.csv",
            "dados_pior_1M.csv"
        };

        String[] cenarios = {
            "Melhor Caso (1.000)",
            "Caso Médio (1.000)",
            "Pior Caso (1.000)",
            "Melhor Caso (1.000.000)",
            "Caso Médio (1.000.000)",
            "Pior Caso (1.000.000)"
        };

        System.out.printf("%-30s | %-15s | %-15s%n", "Cenário", "Bubble Sort", "Quick Sort");
        System.out.println("-".repeat(66));

        for (int i = 0; i < arquivos.length; i++) {
            try {
                int[] dadosBubble = carregarDados(arquivos[i]);
                int[] dadosQuick  = dadosBubble.clone();

                boolean ehMilhao = arquivos[i].contains("1M");
                boolean ehMelhor = arquivos[i].contains("melhor");

                // Bubble Sort (pula casos de 1M médio/pior — tempo inviável)
                String resultadoBubble;
                if (!ehMilhao || ehMelhor) {
                    long inicio = System.currentTimeMillis();
                    bubbleSort(dadosBubble);
                    resultadoBubble = (System.currentTimeMillis() - inicio) + " ms";
                } else {
                    resultadoBubble = "N/C (> 5 min)";
                }

                // Quick Sort
                long inicioQ = System.currentTimeMillis();
                quickSort(dadosQuick, 0, dadosQuick.length - 1);
                long tempoQuick = System.currentTimeMillis() - inicioQ;

                System.out.printf("%-30s | %-15s | %-15s%n",
                        cenarios[i], resultadoBubble, tempoQuick + " ms");

            } catch (FileNotFoundException e) {
                System.out.println("Erro: Arquivo não encontrado: " + arquivos[i]);
            }
        }
    }

    // ===================================================
    // LEITURA DOS DADOS
    // ===================================================

    private static int[] carregarDados(String nomeArquivo)
            throws FileNotFoundException {

        File file = new File(nomeArquivo);
        Scanner scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            scanner.nextLine(); // pula cabeçalho
        }

        int tamanho = nomeArquivo.contains("1M") ? 1_000_000 : 1_000;
        int[] array = new int[tamanho];
        int i = 0;

        while (scanner.hasNextLine() && i < tamanho) {
            String linha = scanner.nextLine().trim();
            if (!linha.isEmpty()) {
                array[i++] = Integer.parseInt(linha);
            }
        }

        scanner.close();
        return array;
    }

    // ===================================================
    // BUBBLE SORT  –  O(n²) médio/pior  |  O(n) melhor
    // ===================================================

    public static void bubbleSort(int[] array) {

        int n = array.length;

        for (int i = 0; i < n - 1; i++) {

            boolean trocou = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (array[j] > array[j + 1]) {

                    int temp     = array[j];
                    array[j]     = array[j + 1];
                    array[j + 1] = temp;

                    trocou = true;
                }
            }

            // Otimização: array já ordenado, encerra antecipadamente
            if (!trocou) break;
        }
    }

    // ===================================================
    // QUICK SORT  –  O(n log n) médio  |  O(n²) pior
    // ===================================================

    public static void quickSort(int[] array, int inicio, int fim) {

        if (inicio < fim) {
            int indicePivo = particionar(array, inicio, fim);
            quickSort(array, inicio, indicePivo - 1);
            quickSort(array, indicePivo + 1, fim);
        }
    }

    public static int particionar(int[] array, int inicio, int fim) {

        // Pivô: elemento do meio (reduz chance do pior caso em arrays ordenados)
        int meio = inicio + (fim - inicio) / 2;

        int temp    = array[meio];
        array[meio] = array[fim];
        array[fim]  = temp;

        int pivo = array[fim];
        int i    = inicio - 1;

        for (int j = inicio; j < fim; j++) {

            if (array[j] <= pivo) {

                i++;

                temp     = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        temp         = array[i + 1];
        array[i + 1] = array[fim];
        array[fim]   = temp;

        return i + 1;
    }

    // ===================================================
    // EXIBIÇÃO
    // ===================================================

    private static void exibirPrimeiros15(int[] array) {

        System.out.print("Dados: ");

        int limite = Math.min(array.length, 15);

        for (int i = 0; i < limite; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println("...");
    }
}
