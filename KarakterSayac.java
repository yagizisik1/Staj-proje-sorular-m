import java.util.Scanner;

public class KarakterSayac {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxKarakter;
        while (true) {
            System.out.print("Maksimum karakter sayısı belirleyin: ");
            maxKarakter = scanner.nextInt();
            scanner.nextLine();

            if (maxKarakter > 0) {
                break;
            } else {
                System.out.println("Karakter sayısı 0'dan büyük olmalıdır!");
            }
        }

        String cumle;
        while (true) {
            System.out.print("Lütfen bir cümle girin: ");
            cumle = scanner.nextLine();

            if (cumle.trim().isEmpty()) {
                System.out.println("Cümle boş olamaz!");
            } else if (cumle.length() > maxKarakter) {
                System.out.println("Cümle karakter limitini aşıyor, tekrar girin!");
            } else {
                break;
            }
        }

        String duyarlilik;
        while (true) {
            System.out.print("Büyük/küçük harf duyarlılığı aktif olsun mu? (Evet/Hayır): ");
            duyarlilik = scanner.nextLine();

            if (duyarlilik.equalsIgnoreCase("Evet") || duyarlilik.equalsIgnoreCase("Hayır")) {
                break;
            } else {
                System.out.println("Lütfen geçerli bir cevap giriniz.");
            }
        }

        String karakter;
        while (true) {
            System.out.print("Analiz etmek için bir karakter girin: ");
            karakter = scanner.nextLine();

            if (karakter.length() == 1) {
                break;
            } else {
                System.out.println("Geçerli bir karakter giriniz.");
            }
        }

        if (duyarlilik.equalsIgnoreCase("Hayır")) {
            cumle = cumle.toLowerCase();
            karakter = karakter.toLowerCase();
        }

        int sayi = 0;
        char arananKar = karakter.charAt(0);

        for (char c : cumle.toCharArray()) {
            if (c == arananKar) {
                sayi++;
            }
        }

        System.out.println("Girilen cümlede '" + karakter + "' harfi toplamda " + sayi + " defa geçmektedir.");

        scanner.close();
    }
}