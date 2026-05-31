import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Altere para o arquivo desejado
        String nomeArquivo = "Data\\dados_pior_1M.csv";


        try {

            int[] dados = carregarDados(nomeArquivo);

            System.out.println("=== ALGORITMO EFICIENTE (QUICK SORT) ===");

            System.out.println("Antes da ordenação:");
            exibirPrimeiros15(dados);

            long inicioEficiente = System.currentTimeMillis();

            quickSort(dados, 0, dados.length - 1);

            long fimEficiente = System.currentTimeMillis();

            System.out.println("Depois da ordenação:");
            exibirPrimeiros15(dados);

            System.out.println("Tempo decorrido: "
                    + (fimEficiente - inicioEficiente) + " ms");

        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo não encontrado.");
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
            scanner.nextLine();
        }

        int tamanho;

        if (nomeArquivo.contains("1M")) {
            tamanho = 1000000;
        } else {
            tamanho = 1000;
        }

        int[] array = new int[tamanho];

        int i = 0;

        while (scanner.hasNextLine() && i < tamanho) {
            array[i] = Integer.parseInt(scanner.nextLine().trim());
            i++;
        }

        scanner.close();

        return array;
    }

    // ===================================================
    // QUICK SORT
    // ===================================================

    public static void quickSort(int[] array, int inicio, int fim) {

        if (inicio < fim) {

            int indicePivo = particionar(array, inicio, fim);

            quickSort(array, inicio, indicePivo - 1);
            quickSort(array, indicePivo + 1, fim);
        }
    }

    public static int particionar(int[] array, int inicio, int fim) {

        int meio = inicio + (fim - inicio) / 2;

        int temp = array[meio];
        array[meio] = array[fim];
        array[fim] = temp;

        int pivo = array[fim];

        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {

            if (array[j] <= pivo) {

                i++;

                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        temp = array[i + 1];
        array[i + 1] = array[fim];
        array[fim] = temp;

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