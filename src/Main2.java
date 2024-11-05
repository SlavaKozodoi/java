import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Main2 {
    public static void main(String[] args) {
        // Исходная матрица переходных вероятностей (до нормализации)
        double[][] transitionMatrix = {
                { 0.1014, 0.3316, 0.2917, 0.0264, 0.2489 },
                { 0.0687, 0.0490, 0.4341, 0.4142, 0.0340 },
                { 0.3595, 0.2004, 0.1799, 0.0424, 0.2177 },
                { 0.2135, 0.2284, 0.2525, 0.1541, 0.1514 },
                { 0.0950, 0.1429, 0.1003, 0.3310, 0.3308 }

        };

        int initialState = 0; // Начальное состояние (S1)
        int steps = 10; // Количество шагов для расчета


        // Вывод нормализованной матрицы
        System.out.println("Нормализованная матрица переходных вероятностей:");
        printMatrix(transitionMatrix);

        // Расчет вероятностей
        double[][] probabilities = calculateProbabilities(transitionMatrix, initialState, steps);

        // Вывод результатов на экран
        printProbabilities(probabilities);

        // Сохранение в файл
        saveProbabilitiesToFile(probabilities, "probabilities_table.txt");
    }



    public static double[][] calculateProbabilities(double[][] transitionMatrix, int initialState, int steps) {
        int numStates = transitionMatrix.length;
        double[][] probabilitiesTable = new double[steps + 1][numStates];

        // Установка начального состояния
        probabilitiesTable[0][initialState] = 1.0;

        // Расчет вероятностей для каждого шага
        for (int k = 1; k <= steps; k++) {
            for (int j = 0; j < numStates; j++) {
                for (int i = 0; i < numStates; i++) {
                    probabilitiesTable[k][j] += probabilitiesTable[k - 1][i] * transitionMatrix[i][j];
                }
            }
        }

        return probabilitiesTable;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.print("|");
            for (double value : row) {
                System.out.printf(" %.4f |", value);
            }
            System.out.println();
        }
    }

    public static void printProbabilities(double[][] probabilities) {
        int steps = probabilities.length;
        int numStates = probabilities[0].length;

        // Форматированный вывод заголовка
        System.out.print("| Номер шага |");
        for (int j = 0; j < numStates; j++) {
            System.out.printf(" S%d |", j + 1);
        }
        System.out.println();

        System.out.println("-".repeat(50));

        // Вывод данных
        for (int k = 0; k < steps; k++) {
            System.out.printf("| %d |", k);
            for (int j = 0; j < numStates; j++) {
                System.out.printf(" %.4f |", probabilities[k][j]);
            }
            System.out.println();
        }
    }

    public static void saveProbabilitiesToFile(double[][] probabilities, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int steps = probabilities.length;
            int numStates = probabilities[0].length;


            writer.write("| Номер шага |");
            for (int j = 0; j < numStates; j++) {
                writer.write(String.format(" S%d |", j + 1));
            }
            writer.newLine();
            writer.write("-".repeat(50));
            writer.newLine();


            for (int k = 0; k < steps; k++) {
                writer.write(String.format("| %d |", k));
                for (int j = 0; j < numStates; j++) {
                    writer.write(String.format(" %.4f |", probabilities[k][j]));
                }
                writer.newLine();
            }

            System.out.println("Результаты сохранены в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}
