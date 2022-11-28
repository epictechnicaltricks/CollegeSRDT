package com.mycollege.ett;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;

import com.google.android.material.button.*;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import android.content.Intent;
import android.view.View;


public class ProfileActivity extends  AppCompatActivity  { 
	
	
	private String fontName = "";
	private String typeace = "";
	
	private ArrayList<HashMap<String, Object>> clg_list = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	
	private LinearLayout linear1;

	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private MaterialButton materialbutton1;
	private ListView listview1;
	private ImageView imageview2;
	private ImageView imageview3;
	private ImageView imageview4;
	private ImageView imageview5;
	private TextView textview1;
	private Spinner spinner1;
	
	private Intent in = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);

		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		materialbutton1 = (MaterialButton) findViewById(R.id.materialbutton1);
		listview1 = (ListView) findViewById(R.id.listview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview1 = (TextView) findViewById(R.id.textview1);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(in);
			}
		});
	}
	
	private void initializeLogic() {
		_changeActivityFont("en_med");


		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "Select College here..");
			clg_list.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "Madhusudan Law University");
			clg_list.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "Ravenshaw University, Cuttack");
			clg_list.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "IIT Bhubaneswar - Indian Institute of Technology");
			clg_list.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "Maharishi College of Natural Law");
			clg_list.add(_item);
		}
		
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("name", "Buxi Jagabandhu Bidyadhar Autonomous");
			clg_list.add(_item);
		}
		
		listview1.setAdapter(new Listview1Adapter(clg_list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		spinner1.setAdapter(new
		Listview1Adapter(clg_list));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
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
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.clg_list_cus, null);
			}
			
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);

			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);

			textview1.setText(clg_list.get((int)_position).get("name").toString());
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}


	public void close(View view){

		finish();
	}

}
