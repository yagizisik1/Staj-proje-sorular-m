import java.util.InputMismatchException;
import java.util.Scanner;

public class KarakterSayac {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxKarakter = 0;
        // 1. MADDE: Hata Yönetimi (Exception Handling) eklendi
        while (true) {
            try {
                System.out.print("Maksimum karakter sayısı belirleyin: ");
                maxKarakter = scanner.nextInt();
                scanner.nextLine(); // Buffer temizleme

                if (maxKarakter > 0) {
                    break;
                } else {
                    System.out.println("Hata: Karakter sayısı 0'dan büyük olmalıdır!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Hata: Lütfen sadece sayısal bir değer giriniz!");
                scanner.nextLine(); // Hatalı girişi temizle
            }
        }

        String cumle;
        while (true) {
            System.out.print("Lütfen bir cümle girin: ");
            cumle = scanner.nextLine();

            if (cumle.trim().isEmpty()) {
                System.out.println("Hata: Cümle boş olamaz!");
            } else if (cumle.length() > maxKarakter) {
                System.out.println("Hata: Cümle karakter limitini aşıyor, tekrar girin!");
            } else {
                break;
            }
        }

        String duyarlilik;
        while (true) {
            System.out.print("Büyük/küçük harf duyarlılığı aktif olsun mu? (Evet/Hayır): ");
            duyarlilik = scanner.nextLine().trim();

            if (duyarlilik.equalsIgnoreCase("Evet") || duyarlilik.equalsIgnoreCase("Hayır")) {
                break;
            } else {
                System.out.println("Lütfen geçerli bir cevap giriniz (Evet/Hayır).");
            }
        }

        String karakterGirdisi;
        while (true) {
            System.out.print("Analiz etmek için bir karakter girin: ");
            karakterGirdisi = scanner.nextLine();

            if (karakterGirdisi.length() == 1) {
                break;
            } else {
                System.out.println("Hata: Lütfen sadece tek bir karakter giriniz.");
            }
        }

        char arananKar = karakterGirdisi.charAt(0);
        
        // Duyarlılık ayarı
        String analizMetni = cumle;
        if (duyarlilik.equalsIgnoreCase("Hayır")) {
            analizMetni = cumle.toLowerCase();
            arananKar = Character.toLowerCase(arananKar);
        }

        int sayi = 0;
        // 2. MADDE: Bellek Optimizasyonu (toCharArray yerine charAt kullanımı)
        // toCharArray() her seferinde yeni bir array oluşturup belleği yorar. 
        // charAt(i) ise mevcut String üzerinden doğrudan okuma yapar.
        for (int i = 0; i < analizMetni.length(); i++) {
            if (analizMetni.charAt(i) == arananKar) {
                sayi++;
            }
        }

        System.out.println("\n--- ANALİZ SONUCU ---");
        System.out.println("Girilen cümlede '" + arananKar + "' karakteri toplamda " + sayi + " defa geçmektedir.");

        scanner.close();
    }
}
