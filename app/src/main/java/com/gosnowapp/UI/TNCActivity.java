package com.gosnowapp.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gosnowapp.Business.BusinessConsts;
import com.gosnowapp.R;


public class TNCActivity extends AppCompatActivity {

    TransparentProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        WebView tncView  = (WebView) findViewById(R.id.tncid);
        WebSettings webSettings = tncView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        tncView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
              // cancel the progress dialog once page is loaded
                if (pd != null)
                    pd.cancel();
            }
        });

        tncView.setWebChromeClient(new WebChromeClient());

        tncView.loadUrl(BusinessConsts.TNC_URL);

        // Display progress dialog till the page content is displayed
        pd = new TransparentProgressDialog(TNCActivity.this,R.drawable.spinner_blue,"");
        ((TransparentProgressDialog) pd).show();

    }

}
