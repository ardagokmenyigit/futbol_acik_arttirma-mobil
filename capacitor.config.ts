import type { CapacitorConfig } from '@capacitor/cli';

/**
 * Bu native kabuk, canlı siteyi (Vercel) doğrudan WebView içinde açar —
 * client kodunun ayrı bir kopyasını TAŞIMAZ. `server.url` bunu sağlıyor:
 * uygulama açılınca doğrudan bu adrese gider.
 *
 * NEDEN: web sitesindeki (asıl repo) her değişiklik — denge ayarı, arayüz
 * düzeltmesi, veri seti güncellemesi — bu native kabuğu YENİDEN DERLEMEDEN
 * otomatik yansır. İki kod tabanının birbirinden uzaklaşma riski böylece
 * ortadan kalkıyor. Bedeli: internet bağlantısı olmadan uygulama açılmaz
 * (zaten canlı çok oyunculu bir oyun için kaçınılmaz).
 *
 * Adres değişirse (özel alan adı alınırsa) burası güncellenmeli.
 */
const config: CapacitorConfig = {
  appId: 'com.acikartirmaligi.app',
  appName: 'Açık Artırma Ligi',
  webDir: 'www',
  server: {
    url: 'https://futbol-acik-arttirma.vercel.app',
    cleartext: false,
  },
  android: {
    allowMixedContent: false,
  },
};

export default config;
