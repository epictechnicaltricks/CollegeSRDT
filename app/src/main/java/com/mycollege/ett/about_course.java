package com.mycollege.ett;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
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


public class about_course extends  AppCompatActivity  {
	
	
	private LinearLayout linear1;
	private TextView title,desc,heading;

	String fontName="";
	Button apply;
	ImageView img;


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about_course);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = findViewById(R.id.linear1);
		title = findViewById(R.id.title__);
		desc = findViewById(R.id.desc);
		apply = findViewById(R.id.apply_btn);
		img = findViewById(R.id.imageview_course);
         heading = findViewById(R.id.heading);
	}
	
	private void initializeLogic() {


		if(getIntent().getStringExtra("news").equals("true")){

			apply.setVisibility(View.GONE);
			heading.setText("News Details");

		}

		title.setText(getIntent().getStringExtra("title"));
		desc.setText(getIntent().getStringExtra("desc"));

		Glide.with(getApplicationContext())
				.load(Uri.parse(getIntent().getStringExtra("img")))
				.error(R.drawable.person)
				.placeholder(R.drawable.person)
				.thumbnail(0.01f)
				.into(img);

		apply.setOnClickListener(new View.OnClickListener() {
									 @Override
									 public void onClick(View v) {

										 startActivity(new Intent(getApplicationContext(), apply_online.class));

									 }
								 }
		);

		_changeActivityFont("en_med");
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
