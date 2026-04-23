import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class UrunYonetimSistemi {

    static class Urun {
        String ad;
        double fiyat;
        int stok;
        double puan;

        Urun(String ad, double fiyat, int stok, double puan) {
            this.ad = ad;
            this.fiyat = fiyat;
            this.stok = stok;
            this.puan = puan;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Urun> urunler = new ArrayList<>();

        // Ürün sayısı alma
        int urunSayisi;
        while (true) {
            System.out.print("Kaç adet ürün gireceksiniz? ");
            urunSayisi = scanner.nextInt();
            scanner.nextLine();

            if (urunSayisi > 1) {
                break;
            } else {
                System.out.println("Birden fazla ürün girmelisiniz!");
            }
        }

        // Ürün bilgilerini alma
        for (int i = 0; i < urunSayisi; i++) {
            System.out.println("\n" + (i + 1) + ". ürün bilgilerini girin:");

            String ad;
            while (true) {
                System.out.print("Ürün adı (max 20 karakter): ");
                ad = scanner.nextLine();
                if (ad.length() >= 1 && ad.length() <= 20) {
                    break;
                } else {
                    System.out.println("Ürün adı en fazla 20 karakter olmalıdır!");
                }
            }

            double fiyat;
            while (true) {
                System.out.print("Fiyat (1-100 arası): ");
                fiyat = scanner.nextDouble();
                scanner.nextLine();
                if (fiyat >= 1 && fiyat <= 100) {
                    break;
                } else {
                    System.out.println("Fiyat 1 ile 100 arasında olmalıdır!");
                }
            }

            int stok;
            while (true) {
                System.out.print("Stok miktarı (en az 1): ");
                stok = scanner.nextInt();
                scanner.nextLine();
                if (stok >= 1) {
                    break;
                } else {
                    System.out.println("Stok miktarı en az 1 olmalıdır!");
                }
            }

            double puan;
            while (true) {
                System.out.print("Değerlendirme puanı (0-5 arası): ");
                puan = scanner.nextDouble();
                scanner.nextLine();
                if (puan >= 0 && puan <= 5) {
                    break;
                } else {
                    System.out.println("Puan 0 ile 5 arasında olmalıdır!");
                }
            }

            urunler.add(new Urun(ad, fiyat, stok, puan));
        }

        // Sıralama kriteri alma
        String siralama;
        while (true) {
            System.out.print("\nSıralama kriteri seçin (ad/fiyat/stok/puan): ");
            siralama = scanner.nextLine();
            if (siralama.equals("ad") || siralama.equals("fiyat") ||
                siralama.equals("stok") || siralama.equals("puan")) {
                break;
            } else {
                System.out.println("Geçerli bir kriter giriniz!");
            }
        }

        String siralamaYonu;
        while (true) {
            System.out.print("Sıralama yönü (artan/azalan): ");
            siralamaYonu = scanner.nextLine();
            if (siralamaYonu.equals("artan") || siralamaYonu.equals("azalan")) {
                break;
            } else {
                System.out.println("Geçerli bir yön giriniz!");
            }
        }

        // Sıralama işlemi
        Comparator<Urun> comparator;
        switch (siralama) {
            case "ad":
                comparator = Comparator.comparing(u -> u.ad);
                break;
            case "fiyat":
                comparator = Comparator.comparingDouble(u -> u.fiyat);
                break;
            case "stok":
                comparator = Comparator.comparingInt(u -> u.stok);
                break;
            default:
                comparator = Comparator.comparingDouble(u -> u.puan);
                break;
        }

        if (siralamaYonu.equals("azalan")) {
            comparator = comparator.reversed();
        }

        urunler.sort(comparator);

        // Sıralanmış ürünleri göster
        System.out.println("\n--- Sıralanmış Ürünler ---");
        for (int i = 0; i < urunler.size(); i++) {
            Urun u = urunler.get(i);
            System.out.println((i + 1) + ". " + u.ad + " | Fiyat: " + u.fiyat +
                    " | Stok: " + u.stok + " | Puan: " + u.puan);
        }

        // Sepete ekleme
        ArrayList<Urun> sepet = new ArrayList<>();
        ArrayList<Integer> sepetAdetler = new ArrayList<>();

        String sepeteEkle;
        while (true) {
            System.out.print("\nSepete ürün eklemek ister misiniz? (Evet/Hayır): ");
            sepeteEkle = scanner.nextLine();

            if (sepeteEkle.equalsIgnoreCase("Hayır")) {
                if (sepet.size() < 2) {
                    System.out.println("Sepette en az 2 ürün olmalıdır!");
                    continue;
                }
                break;
            } else if (sepeteEkle.equalsIgnoreCase("Evet")) {
                System.out.print("Ürün adı girin: ");
                String urunAd = scanner.nextLine();

                Urun bulunanUrun = null;
                for (Urun u : urunler) {
                    if (u.ad.equalsIgnoreCase(urunAd)) {
                        bulunanUrun = u;
                        break;
                    }
                }

                if (bulunanUrun == null) {
                    System.out.println("Ürün bulunamadı!");
                    continue;
                }

                while (true) {
                    System.out.print("Adet girin: ");
                    int adet = scanner.nextInt();
                    scanner.nextLine();

                    if (adet <= bulunanUrun.stok) {
                        sepet.add(bulunanUrun);
                        sepetAdetler.add(adet);
                        bulunanUrun.stok -= adet;
                        System.out.println(bulunanUrun.ad + " sepete eklendi!");
                        break;
                    } else {
                        System.out.println("Stokta yeterli ürün yok! Mevcut stok: " + bulunanUrun.stok);
                    }
                }
            } else {
                System.out.println("Lütfen geçerli bir cevap giriniz.");
            }
        }

        // Toplam tutar hesaplama ve tablo gösterimi
        double toplamTutar = 0;
        double[] urunTutarlari = new double[sepet.size()];
        double[] indirimliUrunFiyatlari = new double[sepet.size()];

        for (int i = 0; i < sepet.size(); i++) {
            indirimliUrunFiyatlari[i] = sepet.get(i).fiyat;
        }

        for (int i = 0; i < sepet.size() - 1; i++) {
            if (sepet.get(i).fiyat > sepet.get(i + 1).fiyat) {
                indirimliUrunFiyatlari[i] = sepet.get(i).fiyat - sepet.get(i + 1).fiyat;
            }
        }

        for (int i = 0; i < sepet.size(); i++) {
            urunTutarlari[i] = indirimliUrunFiyatlari[i] * sepetAdetler.get(i);
            toplamTutar += urunTutarlari[i];
        }

        // Tablo başlığı
        System.out.println("\n--- Sepet Özeti ---");
        System.out.printf("%-20s %-10s %-25s %-10s%n", "Ürün Adı", "Adet", "Birim Fiyat", "Toplam");
        System.out.println("-".repeat(67));

        for (int i = 0; i < sepet.size(); i++) {
            String fiyatBilgisi;
            if (indirimliUrunFiyatlari[i] != sepet.get(i).fiyat) {
                fiyatBilgisi = indirimliUrunFiyatlari[i] + " (indirim uygulanmış birim fiyat)";
            } else {
                fiyatBilgisi = String.valueOf(sepet.get(i).fiyat);
            }
            System.out.printf("%-20s %-10d %-25s %-10.2f%n",
                    sepet.get(i).ad, sepetAdetler.get(i), fiyatBilgisi, urunTutarlari[i]);
        }

        System.out.println("-".repeat(67));
        System.out.printf("%-20s %-10s %-25s %-10.2f%n", "", "", "Toplam", toplamTutar);

        scanner.close();
    }
}