import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UrunYonetimSistemi {

    static class Urun {
        private String ad;
        private double fiyat;
        private int stok;
        private double puan;

        Urun(String ad, double fiyat, int stok, double puan) {
            this.ad = ad;
            this.fiyat = fiyat;
            this.stok = stok;
            this.puan = puan;
        }

        public String getAd() { return ad; }
        public double getFiyat() { return fiyat; }
        public int getStok() { return stok; }
        public double getPuan() { return puan; }
        public void setStok(int stok) { this.stok = stok; }
    }

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            ArrayList<Urun> urunler = urunleriAl();
            urunleriSirala(urunler);
            urunleriYazdir(urunler);

            ArrayList<Urun> sepet = new ArrayList<>();
            ArrayList<Integer> sepetAdetler = new ArrayList<>();
            sepetiYonet(urunler, sepet, sepetAdetler);

            sepetOzetiniYazdir(sepet, sepetAdetler);
        } catch (Exception e) {
            System.out.println("Beklenmedik bir hata ile karşılaşıldı: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    static ArrayList<Urun> urunleriAl() {
        ArrayList<Urun> urunler = new ArrayList<>();

        int urunSayisi;
        while (true) {
            try {
                System.out.print("Kaç adet ürün gireceksiniz? ");
                urunSayisi = scanner.nextInt();
                scanner.nextLine();
                if (urunSayisi > 1) break;
                System.out.println("Birden fazla ürün girmelisiniz!");
            } catch (InputMismatchException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }

        for (int i = 0; i < urunSayisi; i++) {
            System.out.println("\n" + (i + 1) + ". ürün bilgilerini girin:");
            String ad = urunAdiAl(urunler);
            double fiyat = fiyatAl();
            int stok = stokAl();
            double puan = puanAl();
            urunler.add(new Urun(ad, fiyat, stok, puan));
        }

        return urunler;
    }

    static String urunAdiAl(ArrayList<Urun> urunler) {
        while (true) {
            try {
                System.out.print("Ürün adı (max 20 karakter): ");
                String ad = scanner.nextLine();

                if (ad.length() < 1 || ad.length() > 20) {
                    System.out.println("Ürün adı en fazla 20 karakter olmalıdır!");
                    continue;
                }

                boolean ayniIsim = false;
                for (Urun mevcutUrun : urunler) {
                    if (mevcutUrun.getAd().equalsIgnoreCase(ad)) {
                        ayniIsim = true;
                        break;
                    }
                }

                if (ayniIsim) {
                    System.out.println("Bu isimde bir ürün zaten mevcut! Farklı bir isim giriniz.");
                    continue;
                }

                return ad;
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen tekrar deneyin.");
                scanner.nextLine();
            }
        }
    }

    static double fiyatAl() {
        while (true) {
            try {
                System.out.print("Fiyat (1-100 arası): ");
                double fiyat = scanner.nextDouble();
                scanner.nextLine();
                if (fiyat >= 1 && fiyat <= 100) return fiyat;
                System.out.println("Fiyat 1 ile 100 arasında olmalıdır!");
            } catch (InputMismatchException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }
    }

    static int stokAl() {
        while (true) {
            try {
                System.out.print("Stok miktarı (en az 1): ");
                int stok = scanner.nextInt();
                scanner.nextLine();
                if (stok >= 1) return stok;
                System.out.println("Stok miktarı en az 1 olmalıdır!");
            } catch (InputMismatchException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }
    }

    static double puanAl() {
        while (true) {
            try {
                System.out.print("Değerlendirme puanı (0-5 arası): ");
                double puan = scanner.nextDouble();
                scanner.nextLine();
                if (puan >= 0 && puan <= 5) return puan;
                System.out.println("Puan 0 ile 5 arasında olmalıdır!");
            } catch (InputMismatchException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }
    }

    static void urunleriSirala(ArrayList<Urun> urunler) {
        String siralama;
        while (true) {
            System.out.print("\nSıralama kriteri seçin (ad/fiyat/stok/puan): ");
            siralama = scanner.nextLine();
            if (siralama.equals("ad") || siralama.equals("fiyat") ||
                siralama.equals("stok") || siralama.equals("puan")) break;
            System.out.println("Geçerli bir kriter giriniz!");
        }

        String siralamaYonu;
        while (true) {
            System.out.print("Sıralama yönü (artan/azalan): ");
            siralamaYonu = scanner.nextLine();
            if (siralamaYonu.equals("artan") || siralamaYonu.equals("azalan")) break;
            System.out.println("Geçerli bir yön giriniz!");
        }

        Comparator<Urun> comparator;
        switch (siralama) {
            case "ad":
                comparator = Comparator.comparing(Urun::getAd);
                break;
            case "fiyat":
                comparator = Comparator.comparingDouble(Urun::getFiyat);
                break;
            case "stok":
                comparator = Comparator.comparingInt(Urun::getStok);
                break;
            default:
                comparator = Comparator.comparingDouble(Urun::getPuan);
                break;
        }

        if (siralamaYonu.equals("azalan")) {
            comparator = comparator.reversed();
        }

        urunler.sort(comparator);
    }

    static void urunleriYazdir(ArrayList<Urun> urunler) {
        System.out.println("\n--- Sıralanmış Ürünler ---");
        for (int i = 0; i < urunler.size(); i++) {
            Urun u = urunler.get(i);
            System.out.println((i + 1) + ". " + u.getAd() + " | Fiyat: " + u.getFiyat() +
                    " | Stok: " + u.getStok() + " | Puan: " + u.getPuan());
        }
    }

    static void sepetiYonet(ArrayList<Urun> urunler, ArrayList<Urun> sepet, ArrayList<Integer> sepetAdetler) {
        while (true) {
            System.out.print("\nSepete ürün eklemek ister misiniz? (Evet/Hayır): ");
            String sepeteEkle = scanner.nextLine();

            if (sepeteEkle.equalsIgnoreCase("Hayır")) {
                if (sepet.isEmpty()) {
                    System.out.println("Güle güle!");
                    scanner.close();
                    System.exit(0);
                }
                if (sepet.size() < 2) {
                    System.out.println("Sepette en az 2 ürün olmalıdır!");
                    continue;
                }
                break;
            } else if (sepeteEkle.equalsIgnoreCase("Evet")) {
                urunSepeteEkle(urunler, sepet, sepetAdetler);
            } else {
                System.out.println("Lütfen geçerli bir cevap giriniz.");
            }
        }
    }

    static void urunSepeteEkle(ArrayList<Urun> urunler, ArrayList<Urun> sepet, ArrayList<Integer> sepetAdetler) {
        ArrayList<Urun> stokluUrunler = new ArrayList<>();
        for (Urun u : urunler) {
            if (u.getStok() > 0) stokluUrunler.add(u);
        }

        if (stokluUrunler.isEmpty()) {
            System.out.println("Stokta ürün kalmadı!");
            return;
        }

        System.out.println("Mevcut ürünler:");
        for (int j = 0; j < stokluUrunler.size(); j++) {
            Urun u = stokluUrunler.get(j);
            System.out.println("  " + (j + 1) + ". " + u.getAd() +
                    " | Stok: " + u.getStok() + " | Fiyat: " + u.getFiyat());
        }

        System.out.print("Ürün adı girin: ");
        String urunAd = scanner.nextLine();

        Urun bulunanUrun = null;
        for (Urun u : stokluUrunler) {
            if (u.getAd().equalsIgnoreCase(urunAd)) {
                bulunanUrun = u;
                break;
            }
        }

        if (bulunanUrun == null) {
            System.out.println("Geçerli bir ürün giriniz!");
            return;
        }

        int mevcutIndex = -1;
        for (int j = 0; j < sepet.size(); j++) {
            if (sepet.get(j).getAd().equalsIgnoreCase(bulunanUrun.getAd())) {
                mevcutIndex = j;
                break;
            }
        }

        while (true) {
            try {
                System.out.print("Adet girin: ");
                int adet = scanner.nextInt();
                scanner.nextLine();

                if (adet <= bulunanUrun.getStok()) {
                    if (mevcutIndex != -1) {
                        sepetAdetler.set(mevcutIndex, sepetAdetler.get(mevcutIndex) + adet);
                    } else {
                        sepet.add(bulunanUrun);
                        sepetAdetler.add(adet);
                    }
                    bulunanUrun.setStok(bulunanUrun.getStok() - adet);
                    System.out.println(bulunanUrun.getAd() + " sepete eklendi!");
                    break;
                } else {
                    System.out.println("Stokta yeterli ürün yok! Mevcut stok: " + bulunanUrun.getStok());
                }
            } catch (InputMismatchException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }
    }

    static void sepetOzetiniYazdir(ArrayList<Urun> sepet, ArrayList<Integer> sepetAdetler) {
        double toplamTutar = 0;
        double[] urunTutarlari = new double[sepet.size()];
        double[] indirimliUrunFiyatlari = new double[sepet.size()];

        for (int i = 0; i < sepet.size(); i++) {
            indirimliUrunFiyatlari[i] = sepet.get(i).getFiyat();
        }

        double sonUrunFiyati = sepet.get(sepet.size() - 1).getFiyat();
        for (int i = 0; i < sepet.size() - 1; i++) {
            if (sepet.get(i).getFiyat() > sonUrunFiyati) {
                indirimliUrunFiyatlari[i] = sepet.get(i).getFiyat() - sonUrunFiyati;
            }
        }

        for (int i = 0; i < sepet.size(); i++) {
            urunTutarlari[i] = indirimliUrunFiyatlari[i] * sepetAdetler.get(i);
            toplamTutar += urunTutarlari[i];
        }

        System.out.println("\n--- Sepet Özeti ---");
        System.out.printf("%-20s %-10s %-25s %-10s%n", "Ürün Adı", "Adet", "Birim Fiyat", "Toplam");
        System.out.println("-".repeat(67));

        for (int i = 0; i < sepet.size(); i++) {
            String fiyatBilgisi;
            if (indirimliUrunFiyatlari[i] != sepet.get(i).getFiyat()) {
                fiyatBilgisi = indirimliUrunFiyatlari[i] + " (indirim uygulanmış birim fiyat)";
            } else {
                fiyatBilgisi = String.valueOf(sepet.get(i).getFiyat());
            }
            System.out.printf("%-20s %-10d %-25s %-10.2f%n",
                    sepet.get(i).getAd(), sepetAdetler.get(i), fiyatBilgisi, urunTutarlari[i]);
        }

        System.out.println("-".repeat(67));
        System.out.printf("%-20s %-10s %-25s %-10.2f%n", "", "", "Toplam", toplamTutar);
    }
}