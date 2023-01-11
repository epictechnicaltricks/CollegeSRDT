package com.mycollege.ett;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class About extends  AppCompatActivity  {
	
	private final Timer _timer = new Timer();
	
	private String fontName = "";
	private final String typeace = "";
	
	private LinearLayout linear8;
	private ImageView imageview1;
	
	private TimerTask timer;
	private final Intent toAuth = new Intent();
	private RequestNetwork in;
	private RequestNetwork.RequestListener _in_request_listener;

	ImageView imageview_aff;


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear8 = findViewById(R.id.linear8);
		imageview1 = findViewById(R.id.imageview1);
		in = new RequestNetwork(this);

		imageview_aff = findViewById(R.id.imageview_aff);
		
		_in_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}



	public void close(View view){

		finish();
	}


	private void initializeLogic() {

		//_transparentStatusAndNavigation();
		//_NavStatusBarColor("#FFFFFF", "#FFFFFF");
		_changeActivityFont("en_med");
		_DARK_ICONS();

		Glide.with(getApplicationContext())
				.load(Uri.parse("https://odishabhaskar.com/wp-content/uploads/2022/08/Madhusudan-law-University-750x430.jpg"))
				.error(R.drawable.person)
				.placeholder(R.drawable.person)
				.thumbnail(0.01f)
				.into(imageview_aff);
		

	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}


	public void _transparentStatusAndNavigation () {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		);
		setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
				| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
	}
	private void setWindowFlag(final int bits, boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}



	public void _NavStatusBarColor (final String _color1, final String _color2) {
		Window w = this.getWindow();
		w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));
		w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
	}
	
	
	public void _DARK_ICONS () {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
					if ((v instanceof EditText )) {
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
	
	
	public void _ActivityTranlation (final View _view, final String _transitionName, final Intent _intent) {
		_view.setTransitionName(_transitionName);
		
		android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(About.this, _view, _transitionName);
		        startActivity(_intent, optionsCompat.toBundle());
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

	
}
