import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scan = new Scanner(System.in);
        double u1 = scan.nextDouble();
        double u2 = scan.nextDouble();

        double v1 = scan.nextDouble();
        double v2 = scan.nextDouble();

        double dotProduct = u1 * v1 + u2 * v2;
        double lengthOfU = Math.sqrt(Math.pow(u1, 2) + Math.pow(u2, 2));
        double lengthOfV = Math.sqrt(Math.pow(v1, 2) + Math.pow(v2, 2));

        double angle = Math.acos(dotProduct / (lengthOfU * lengthOfV));
        double angleTheta = Math.toDegrees(angle);
        System.out.println(angleTheta);
    }
}