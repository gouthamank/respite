package app.drool.respite.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import app.drool.respite.R;

/**
 * Created by drool on 6/19/16.
 */

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra("url");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Uri linkURL;
                try {
                    linkURL = Uri.parse(url);
                    getSupportActionBar().setTitle(linkURL.getHost());
                    getSupportActionBar().setSubtitle(linkURL.toString());

                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "Could not parse URL", Toast.LENGTH_LONG).show();
                } finally {
                    invalidateOptionsMenu();
                }
            }

        });
        webView.loadUrl(url);
        setContentView(webView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_webview, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(webView.canGoBack())
            menu.findItem(R.id.menu_webview_back).setEnabled(true);
        else
            menu.findItem(R.id.menu_webview_back).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_webview_openexternal:
                try {
                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Could not launch external browser", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_webview_back:
                webView.goBack();
                break;

            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.clearCache(true);
    }


}
