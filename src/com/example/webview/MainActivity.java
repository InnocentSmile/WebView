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
//        Uri uri=Uri.parse(url);//urlΪ��Ҫ���ӵ�ַ
//        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);
         
        init();
      
    }
	private void init() {
		webView=(WebView) findViewById(R.id.webView);
		//���ر�����Դ
		//webView.loadUrl("file:///android_asset/index.html");
		//����Web��Դ
		webView.loadUrl(url);
		//����WebViewĬ��ͨ��������������ϵͳ���������ҳ����Ϊ��ʹ����ҳ������Webview�д�
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				//����ֵ��true��ʱ�������ҳ��WebView�д򿪣�false�͵���ϵͳ��������ߵ����������ȥ��
				//return super.shouldOverrideUrlLoading(view, url);
				view.loadUrl(url);
				return true;
			}
			//WebViewClient����WebViewȥ����һЩҳ����ƣ�������֪ͨ
		});
		//����JavaScript
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true);
		
		//WebView����ҳ������ʹ�û���
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		webView.setWebChromeClient(new WebChromeClient(){
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				//newProgress   1-100֮�������
				if(newProgress==100)
				{
					//��ҳ������ϣ��ر�ProgressDialog
					closeDialog();
				}
				else {
					//��ҳ���ڼ��أ���ProgressDialog
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
					dialog.setTitle("���ڼ���");
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
	//��д������ ���ڵ��߼�
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
