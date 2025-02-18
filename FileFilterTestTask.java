import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileFilterTestTask {
    public static void main(String[] args) {

        String inputFile1 = "input1.txt";
        String inputFile2 = "input2.txt";
        String outputFile1 = "output1.txt";
        String outputFile2 = "output2.txt";

        try {
            writeTestFile(inputFile1, new String[]{"apple", "banana", "cherry"});
            writeTestFile(inputFile2, new String[]{"banana", "cherry", "date"});

            Set<String> set1 = readFileIntoSet(inputFile1);
            Set<String> set2 = readFileIntoSet(inputFile2);

            writeDifference(set1, set2, outputFile1);
            writeDifference(set2, set1, outputFile2);

            System.out.println("Output 1 (in input1.txt but not in input2.txt):");
            printFileContents(outputFile1);
            System.out.println("Output 2 (in input2.txt but not in input1.txt):");
            printFileContents(outputFile2);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    private static Set<String> readFileIntoSet(String filePath) throws IOException {
        Set<String> set = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
        }
        return set;
    }

    private static void writeDifference(Set<String> set1, Set<String> set2, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : set1) {
                if (!set2.contains(line)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private static void writeTestFile(String fileName, String[] lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void printFileContents(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
