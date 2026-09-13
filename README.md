# Açık Artırma Ligi — Android

[futbol_acik_arttirma](https://github.com/ardagokmenyigit/futbol_acik_arttirma) adlı web
oyununun Android native kabuğu (Capacitor).

## Mimari — neden ayrı bir repo, neden kod kopyalanmadı

Bu proje web oyununun kodunu **kopyalamaz**. `capacitor.config.ts` içindeki
`server.url` sayesinde uygulama açılınca doğrudan canlı siteye
(`https://futbol-acik-arttirma.vercel.app`) gider ve onu WebView içinde
gösterir.

Bunun tercih edilme sebebi: web repodaki her değişiklik (motor dengesi,
arayüz, veri seti) bu native kabuğu **yeniden derlemeden** otomatik yansır.
İki kod tabanının birbirinden uzaklaşma riski böylece ortadan kalkıyor.
Bedeli: uygulama internet bağlantısı olmadan açılmaz — zaten canlı çok
oyunculu bir oyun için bu kaçınılmaz (bkz. `www/offline.html`, bağlantı
yoksa gösterilen markalı hata ekranı).

Bu repo yalnızca şunları içerir: Capacitor yapılandırması, üretilen Android
projesi (`android/`), uygulama ikonu ve açılış ekranı kaynakları
(`resources/`).

## Android Studio'da açıp emülatörde denemek

1. Bu repoyu klonla, bağımlılıkları kur:
   ```
   npm install
   ```
2. Android Studio'yu aç → **Open** → bu reponun içindeki **`android/`**
   klasörünü seç (repo kökünü değil, doğrudan `android/` alt klasörünü).
3. Gradle senkronu otomatik başlar (ilk seferde birkaç dakika sürebilir).
4. Üstteki cihaz seçiciden bir emülatör seç (yoksa **Device Manager**'dan
   biri oluştur) → **Run** (▶) butonuna bas.

Uygulama açılınca doğrudan canlı siteyi yükleyecek — emülatörün internete
çıkabildiğinden emin ol.

## Adres / marka değişirse güncellenecek yerler

- `capacitor.config.ts` → `server.url`
- `resources/*.svg` → ikon ve açılış ekranı kaynakları, değiştirdikten sonra:
  ```
  npx capacitor-assets generate --android
  ```
- `android/app/src/main/res/values/strings.xml` → `app_name` (Capacitor
  senkronunda `capacitor.config.ts`'teki `appName`'den otomatik yazılır,
  elle değiştirmeye gerek yok — `npx cap sync android` yeterli)

## Doğrulandı — gerçek emülatörde çalıştırıldı

Bu repo iddia değil, ölçümle doğrulanmış:

- `./gradlew assembleDebug` başarılı, gerçek APK üretiyor
- Android emülatörde (API 35) kurulup açıldı: açılış ekranı marka renkleriyle
  (turf yeşili + altın motif) görünüyor, ardından uygulama canlı siteye
  bağlanıyor ve **"SUNUCUYA BAĞLI"** (socket.io) durumunu gösteriyor
- Geri tuşu düzeltmesi doğrulandı: `WebView.canGoBack()` bu sitede her zaman
  `true` dönüyordu (muhtemelen ilk yüklemenin kendi history girdisi yüzünden),
  yani Capacitor'ın varsayılan davranışına bırakılsaydı kullanıcı geri tuşuyla
  uygulamadan **hiç çıkamazdı** — test edilip görüldü, `MainActivity.java`'da
  elle düzeltildi (artık geri gidilecek gerçek geçmiş yoksa uygulama Recents'e
  atılıyor, kapanmıyor — canlı bir maç ortasında öldürülmesin diye)

**Bilinen kozmetik sınırlama:** durum çubuğu (saat/pil ikonları) rengi Android
15'in yeni edge-to-edge zorunluluğu yüzünden `styles.xml`'deki klasik
`android:statusBarColor` / `windowLightStatusBar` özniteliklerini görmezden
geliyor olabilir — işlevi etkilemiyor, yalnızca görsel ince ayar.

## Google Play'e yayınlamadan önce

- [ ] Google Play Console hesabı (`$25` tek seferlik)
- [ ] Uygulama imzalama anahtarı (release keystore) — henüz oluşturulmadı,
      bu repo şu an yalnızca **debug** build üretiyor
- [ ] Mağaza listelemesi: ekran görüntüleri, açıklama, gizlilik politikası
- [ ] `android/app/build.gradle` içindeki `versionCode` / `versionName`
