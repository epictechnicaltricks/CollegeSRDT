package com.mycollege.ett;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;


public class affiliated extends  AppCompatActivity  {
	
	
	private LinearLayout linear1;
	private TextView textview1;
	private WebView webview1;
	ImageView img_view_aff;
	String fontName="";

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.affiliate);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
        img_view_aff = findViewById(R.id.imageview_aff);

		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;

				super.onPageStarted(_param1, _param2, _param3);
			}

			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;

				super.onPageFinished(_param1, _param2);
			}
		});

	}
	
	private void initializeLogic() {

		_changeActivityFont("en_med");
		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1598257006675-0aaec40301f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=40"))
				.error(R.drawable.school)
				.placeholder(R.drawable.school)
				.thumbnail(0.01f)
				.into(img_view_aff);


		webview1.getSettings().setLoadWithOverviewMode(true);
		webview1.getSettings().setUseWideViewPort(true);
		final WebSettings webSettings = webview1.getSettings();
		final String newUserAgent;
		newUserAgent = "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		webSettings.setUserAgentString(newUserAgent);

		webview1.loadUrl("file:///android_asset/aff.webp");

        _ZoomWebView(webview1,true);


	}



	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView());
	}
	private void overrideFonts(final android.content.Context context, final View v) {

		try {
			Typeface
					typeace = Typeface.createFromAsset(getAssets(), fontName);
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
					 i < vg.getChildCount();
					 i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				}
				else {
					if ((v instanceof EditText)) {
						((EditText) v).setTypeface(typeace);
					}
					else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)

		{
			Util.showMessage(getApplicationContext(), "Error Loading Font");
		}
	}

	public void _ZoomWebView (final WebView _web, final boolean _ballon) {
		_web.getSettings().setBuiltInZoomControls(_ballon);
		_web.getSettings().setDisplayZoomControls(!_ballon);
	}

	public void _Open_url (final String _url) {

		Intent i = new Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(_url)); startActivity(i);

	}

	private void loadPDF(String _url)
	{
		//pdfURL = getIntent().getStringExtra("pdfURL");

		String prefix = "https://docs.google.com/gview?embedded=true&url=";
		webview1.loadUrl(prefix.concat(_url));


	}

	public void close(View view){

		finish();
	}
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

	
}
