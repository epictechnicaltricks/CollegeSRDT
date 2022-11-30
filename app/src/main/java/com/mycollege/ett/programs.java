package com.mycollege.ett;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class programs extends  AppCompatActivity  {
	
	
	private LinearLayout linear1;
	private TextView textview1;
	ImageView imageview;


	private RequestNetwork course_api;
	private RequestNetwork.RequestListener _course_api_listener;

	private HashMap<String, Object> api_map = new HashMap<>();


	RecyclerView recyclerview1;
	String fontName="";
	private  ArrayList<HashMap<String, Object>> listmap2 = new ArrayList<>();
	private String list = "";

	private HashMap<String, Object> api_map3 = new HashMap<>();

	private HashMap<String, Object> api_map2 = new HashMap<>();


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.programs);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {

		course_api = new RequestNetwork(this);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview = findViewById(R.id.imageview_bg);
		recyclerview1 = findViewById(R.id.recyclerview1);


		_course_api_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				api_map.clear();
				api_map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());
				// must add resultSet
				//" list " is a String datatype
				list = (new Gson()).toJson(api_map.get("data"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

				// Log.d("img_obj", list);
				//listmap2 = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());


				try {

					listmap2 = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

					api_map2.clear();
					listmap2.clear();
					String responseUltered = "{\n\"items\": ".concat(list.concat("}"));


					org.json.JSONObject object = new org.json.JSONObject(responseUltered);
					org.json.JSONArray array = object.getJSONArray("items");

					String img_url, title , content, created_at;
					//Log.d("img_obj_array", String.valueOf(array));

					for(int i=0;i<array.length();i++){

						org.json.JSONObject obj = array.getJSONObject(i);
						title = obj.getString("title");
						content = obj.getString("description");
						//created_at = obj.getString("created_at");

						String img_url_obj = obj.getString("image");

						api_map2 = new HashMap<>();
						api_map2.put("title", title);
						api_map2.put("description", content);
						api_map2.put("img_url", img_url_obj);
						//api_map2.put("created_at", created_at);
						listmap2.add(api_map2);
					}
					Collections.reverse(listmap2);
					recyclerview1.setAdapter(new Recyclerview1Adapter(listmap2));
					recyclerview1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


				} catch (Exception ex) {
					showMessage("303 line "+ex.toString());
					ex.printStackTrace();
				}
			}

			@Override
			public void onErrorResponse(String tag, String message) {

			}
		};
	}
	
	private void initializeLogic() {

		request_course();

		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1598257006675-0aaec40301f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=40"))
				.error(R.drawable.person)
				.placeholder(R.drawable.person)
				.thumbnail(0.01f)
				.into(imageview);

		_changeActivityFont("en_med");


	}

	private void request_course(){

		course_api.startRequestNetwork(RequestNetworkController.GET,
				"https://new.mlu.ac.in/api/v1/courses",
				"no tag",
				_course_api_listener);

	}


	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.course_custom, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new Recyclerview1Adapter.ViewHolder(_v);
		}



		@Override
		public void onBindViewHolder(Recyclerview1Adapter.ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView c_name = _view.findViewById(R.id.c_name);
			final TextView view = _view.findViewById(R.id.view);
			final TextView desc = _view.findViewById(R.id.desc);


			c_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
			view.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
			desc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);


			try{


				c_name.setText(Objects.requireNonNull(listmap2.get(_position).get("title")).toString());
				desc.setText(Objects.requireNonNull(listmap2.get(_position).get("description")).toString());

				api_map3 = new Gson().fromJson(Objects.requireNonNull(listmap2.get(_position).get("img_url")).toString(), new TypeToken<HashMap<String, Object>>(){}.getType());


				String img_url = Objects.requireNonNull(api_map3.get("img_url")).toString();

				Glide.with(getApplicationContext())
						.load(Uri.parse(img_url))
						.error(R.drawable.school)
						.placeholder(R.drawable.school)
						.thumbnail(0.01f)
						.into(imageview2);



				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						api_map3 = new Gson().fromJson(Objects.requireNonNull(listmap2.get(_position).get("img_url")).toString(), new TypeToken<HashMap<String, Object>>(){}.getType());
						String img_url = Objects.requireNonNull(api_map3.get("img_url")).toString();

						Intent in = new Intent();
						in.putExtra("title", Objects.requireNonNull(listmap2.get(_position).get("title")).toString());
						in.putExtra("img",img_url);
						in.putExtra("desc", Objects.requireNonNull(listmap2.get(_position).get("description")).toString());
						in.putExtra("news", "");
						in.setClass(getApplicationContext(),about_course.class);
						startActivity(in);

					}
				});


				/*String img_url = Objects.requireNonNull(listmap2.get(_position).get("img_url")).toString();
				Glide.with(getApplicationContext())
						.load(Uri.parse(Objects.requireNonNull(listmap2.get(_position).get("img_url")).toString()))
						.error(R.drawable.person)
						.placeholder(R.drawable.person)
						.thumbnail(0.01f)
						.into(_drawer_profile_image);*/


				/*ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("Copied Text", img_url );
				clipboard.setPrimaryClip(clip);

				Log.d("img_obj", img_url);*/



			}catch (Exception e)
			{
				showMessage("887 line "+e.toString());
			}



		}

		@Override
		public int getItemCount() {
			return _data.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}

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
