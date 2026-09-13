package com.acikartirmaligi.app;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import androidx.activity.OnBackPressedCallback;
import com.getcapacitor.BridgeActivity;
import com.getcapacitor.BridgeWebViewClient;

/**
 * Uygulama açılışta doğrudan canlı siteye gider (bkz. capacitor.config.ts
 * içindeki server.url). Burada eklenen iki şey:
 *
 *  1. İnternet yoksa / site ulaşılamazsa, Chromium'un çıplak
 *     "net::ERR_..." hata sayfası yerine markalı offline.html gösterilir.
 *
 *  2. GERİ TUŞU: gözlemlendi ki WebView.canGoBack() bu sitede neredeyse
 *     her zaman true dönüyor (muhtemelen ilk yüklemenin kendi history
 *     girdisi yüzünden), yani Capacitor'ın varsayılan davranışına
 *     bırakılırsa kullanıcı geri tuşuyla uygulamadan HİÇ ÇIKAMIYOR — test
 *     edildi, iki kez basınca da uygulama ön planda kaldı. Bu yüzden geri
 *     tuşu burada elle ele alınıyor: WebView gerçekten geri gidebiliyorsa
 *     gider, gidemiyorsa uygulama arka plana atılır (kapatılmaz — Recents'te
 *     kalır, tekrar açılınca kaldığı yerden devam eder; canlı bir maç/açık
 *     artırma ortasında sekmeyi tamamen öldürmek istemiyoruz).
 */
public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    WebView webView = getBridge().getWebView();

    webView.setWebViewClient(
        new BridgeWebViewClient(getBridge()) {
          @Override
          public void onReceivedError(
              WebView view, WebResourceRequest request, WebResourceError error) {
            if (request.isForMainFrame()) {
              view.loadUrl("file:///android_asset/public/offline.html");
              return;
            }
            super.onReceivedError(view, request, error);
          }
        });

    getOnBackPressedDispatcher()
        .addCallback(
            this,
            new OnBackPressedCallback(true) {
              @Override
              public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                  webView.goBack();
                } else {
                  moveTaskToBack(true);
                }
              }
            });
  }
}
