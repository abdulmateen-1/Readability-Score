package readability;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    protected static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    protected static int[] getWordsCount(String file) {
        var sentences = file.trim().split("[.]|[!]|[?]");
        var newSentence = file.replace(" ", "");

        var totalWordCount = 0;
        var sentenceAmount = 0;
        var charCount = newSentence.length();
        var syllablesCount = 0;
        var polySyllablesCount = 0;

        for (var sub : sentences) {
            for (var str : sub.trim().split("[\\s+]|[\\pZ]")){
                syllablesCount += getSyllableCount(str);
                if (getSyllableCount(str) >= 3) {
                    polySyllablesCount++;
                }
            }
            totalWordCount += sub.trim().split("[\\s+]|[\\pZ]").length;
            sentenceAmount++;
        }

        return new int[]{totalWordCount, sentenceAmount, charCount, syllablesCount, polySyllablesCount};
    }

    protected static String message (int wordCount, int sentenceCount,
                                     int charNum, int syllable, int polySyllable) {
        return "Words: " + wordCount + "\n" +
                "Sentences: " + sentenceCount + "\n" +
                "Characters: " + charNum + "\n" +
                "Syllables: " + syllable + "\n" +
                "Polysyllables: " + polySyllable;
    }

    protected static  int getSyllableCount(String word) {
        ArrayList<String> syllable = new ArrayList<>();
        var regExp = "[bcdfghjklmnpqrstvwxz]*[aeiouy]+[bcdfghjklmnpqrstvwxz]*";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(word.toLowerCase());

        while (m.find()) {
            syllable.add(m.group());
        }

        if (syllable.size() > 1 &&
                syllable.get(syllable.size() - 1).equals("e")) {
            return syllable.size() - 1;
        }

        return syllable.size();
    }

    protected static String printMessage(String output, int totalWord,
                                     int sentence, int charac,
                                     int syllable, int polySyll) {
        var autoIndex = 4.71 * ((double) charac / totalWord) + 0.5 *
                ((double) totalWord / sentence) - 21.43;
        var fleshIndex =  0.39 * ((double) totalWord / sentence) + 11.8 *
                ((double) syllable / totalWord) - 15.59;
        var smogIndex = 1.043 * Math.sqrt(polySyll * 30.0 / sentence) + 3.1291;
        var liauIndex = 0.0588 * ((double) charac / totalWord * 100)
                - 0.296 * ((double) sentence / totalWord * 100) - 15.8;
        switch (output) {
            case "all":
                return "Automated Readability Index: " + String.format("%.2f", autoIndex) + " (about " + getAge(autoIndex) + " year olds)." + "\n" +
                        "Flesch–Kincaid readability tests: " + String.format("%.2f", fleshIndex) + " (about " + getAge(fleshIndex) + " year olds)." + "\n" +
                        "Simple Measure of Gobbledygook: " + String.format("%.2f", smogIndex) + " (about " + getAge(smogIndex) + " year olds)." + "\n" +
                        "Coleman–Liau index: " + String.format(".%2f", liauIndex) + " (about " + getAge(autoIndex) + " year olds).";
            case "ARI":
                return "Automated Readability Index: " + String.format("%.2f", autoIndex) + " (about " + getAge(autoIndex) + " year olds).";
            case "FK":
                return "Flesch–Kincaid readability tests: " + String.format("%.2f", fleshIndex) + " (about " + getAge(fleshIndex) + " year olds).";
            case "SMOG":
                return "Simple Measure of Gobbledygook: " + String.format("%.2f", smogIndex) + " (about " + getAge(smogIndex) + " year olds).";
            case "CL":
                return "Coleman–Liau index: " + String.format("%.2f", liauIndex) + " (about " + getAge(autoIndex) + " year olds).";
            default:
                return "Invalid Index";
        }
    }

    private static String getAge(double index) {
        int test = (int) Math.ceil(index);
        return Integer.toString(test);
    }


    public static void main(String[] args) {
        var fileName = args[0];
        try {
            String fileRead = readFileAsString(fileName);
            var theCounts = getWordsCount(fileRead);
            var totalWordCount = theCounts[0];
            var sentenceAmount = theCounts[1];
            var charCount = theCounts[2];
            var syllablesCount = theCounts[3];
            var polyCount = theCounts[4];


            System.out.println("Java Main " + fileName + "\n" +
                                "The text is: " + "\n" + fileRead + "\n");
            System.out.println(message(totalWordCount, sentenceAmount, charCount, syllablesCount, polyCount));
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, all): ");
            String output;
            try (Scanner scan = new Scanner(System.in)) {
                output = scan.nextLine();
            }

            System.out.println(printMessage(output, totalWordCount, sentenceAmount, charCount,
                                            syllablesCount, polyCount));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }

        
    }

}
