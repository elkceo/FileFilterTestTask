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
//            writeTestFile(inputFile1, new String[]{"banana", "cherry", "apple"});
//            writeTestFile(inputFile2, new String[]{"banana", "cherry", "date"});

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

    private static Set<String> readFileIntoSet(String filePath) {
        Set<String> set = new HashSet<>();
        BufferedReader reader = getBufferedReader(filePath);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error within reading file: " + filePath, e);
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError(filePath + " is too large to fit in memory");
        }
        return set;
    }

    private static BufferedReader getBufferedReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(validateFile(filePath)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }

    private static File validateFile(String filePath) {
        File file = new File(filePath);
        if (file.length() == 0) {
            throw new IllegalArgumentException(filePath + " is empty");
        }
        if (!filePath.endsWith(".txt")) {
            throw new IllegalArgumentException(filePath + " is not a .txt file");
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException(filePath + " cannot be read");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(filePath + " is not a file");
        }
        return file;
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
