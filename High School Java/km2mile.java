import java.util.Scanner;

public class km2mile {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter number to convet to km or mile");
        double m2k = 1.60934;
        double k2m = 0.621371;
        double numb = scan.nextDouble();
        System.out.println("M2K:" + (numb * m2k));
        System.out.println("K2M:" + (numb * k2m));

    }
}
