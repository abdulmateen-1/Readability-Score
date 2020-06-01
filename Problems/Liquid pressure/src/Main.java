import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        double pressure = scanner.nextDouble();
        double height = scanner.nextDouble();
        double density = 9.8 * pressure * height;
        // double roundDensity = Math.round(density * 100.0) / 100.0;
        System.out.println(String.format("%.3f", density));
    }
}
