package com.minseok.hongdroid1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Music extends AppCompatActivity {
    WebView webView;
    String url="https://www.nl.go.kr/";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        webView= (WebView)findViewById(R.id.webview);


                webView.getSettings().setJavaScriptEnabled(true);
                //WebView는 기본적으로 JavaScript를 비활성화하고 있습니다. 이것은 웹 페이지가 JavaScript를 사용하는 경우 제대로 렌더링되지 않을 수 있습니다.
                // 따라서 웹 페이지가 JavaScript를 사용하는 경우 WebView에서 JavaScript를 활성화해야 합니다.
                //규칙임
                webView.loadUrl(url);//특정 url을 틀어라.

            }


}