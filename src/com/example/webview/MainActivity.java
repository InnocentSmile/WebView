package com.example.webview;


import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
     
   private WebView webView;
	private String url="https://www.baidu.com/";
	private ProgressDialog dialog;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity);
//        Uri uri=Uri.parse(url);//url为你要链接地址
//        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);
         
        init();
      
    }
	private void init() {
		webView=(WebView) findViewById(R.id.webView);
		//加载本地资源
		//webView.loadUrl("file:///android_asset/index.html");
		//加载Web资源
		webView.loadUrl(url);
		//覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以再Webview中打开
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				//返回值是true的时候控制网页在WebView中打开，false就调用系统浏览器或者第三方浏览器去打开
				//return super.shouldOverrideUrlLoading(view, url);
				view.loadUrl(url);
				return true;
			}
			//WebViewClient帮助WebView去处理一些页面控制，和请求通知
		});
		//启用JavaScript
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true);
		
		//WebView加载页面优先使用缓存
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		webView.setWebChromeClient(new WebChromeClient(){
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				//newProgress   1-100之间的整数
				if(newProgress==100)
				{
					//网页加载完毕，关闭ProgressDialog
					closeDialog();
				}
				else {
					//网页正在加载，打开ProgressDialog
					openDialog(newProgress);
				}
			}

			private void closeDialog() {
				// TODO Auto-generated method stub
				if(dialog!=null&&dialog.isShowing())
				{
					dialog.dismiss();
					dialog=null;
				}
			}

			private void openDialog(int newProgress) {
				// TODO Auto-generated method stub
				if(dialog==null)
				{
					dialog=new ProgressDialog(MainActivity.this);
					dialog.setTitle("正在加载");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();
				}
				else {
					dialog.setProgress(newProgress);
				}
			}
			
			
			
		});
		
		
		
	}
	//改写物理按键 反悔的逻辑
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
//			Toast.makeText(this, webView.getUrl(), Toast.LENGTH_SHORT).show();
			if(webView.canGoBack())
			{
				webView.goBack();
				return true; 
			}
			else {
			System.exit(0);
			}
			
		}
			return super.onKeyDown(keyCode, event);
		}

}
