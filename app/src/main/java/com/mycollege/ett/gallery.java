package com.mycollege.ett;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class gallery extends  AppCompatActivity  {

	private final Timer _timer = new Timer();
	private LinearLayout linear1;
	private TextView textview1;

	private TabLayout tablayout1;
	private RecyclerView recyclerview1,recyclerview2;

	private TimerTask time;

	private RequestNetwork gallery_api;
	private RequestNetwork.RequestListener _gallery_request_listener;

	boolean gallery_view = true;

	String fontName="";
	private  ArrayList<HashMap<String, Object>> listmap2 = new ArrayList<>();
	private String list = "";

	private HashMap<String, Object> api_map = new HashMap<>();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.gallery);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = findViewById(R.id.linear1);

		gallery_api = new RequestNetwork(this);

		tablayout1 = findViewById(R.id.tablayout1);
		recyclerview1 = findViewById(R.id.recyclerview1);


		tablayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				switch(_position) {
					case (0): {
						time = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {

										gallery_view = true;
										listmap2.clear();

										_request_api("image-gallery");





										time.cancel();
									}
								});
							}
						};
						_timer.schedule(time, 1500);
						break;
					}
					case (1): {
						gallery_view = false ;
						listmap2.clear();
						_request_api("video-gallery");


						break;
					}

					default: {
									break;
					}
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();

			}
		});

       _gallery_request_listener = new RequestNetwork.RequestListener() {
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



                  /*  api_map2.clear();
					listmap2.clear();
					String responseUltered = "{\n\"items\": ".concat(list.concat("}"));


					org.json.JSONObject object = new org.json.JSONObject(responseUltered);
					org.json.JSONArray array = object.getJSONArray("items");

					Log.d("img_obj_array", String.valueOf(array));

					for(int i=0;i<array.length();i++){

						org.json.JSONObject obj = array.getJSONObject(i);
						name = obj.getString("name");
						designation = obj.getString("designation");

						img_url = obj.getJSONObject("image").getString("img_url");
						api_map2 = new HashMap<>();
						api_map2.put("name", name);
						api_map2.put("designation", designation);
						api_map2.put("img_url", img_url);
						listmap2.add(api_map);
					}
*/
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

		_changeActivityFont("en_med");

		tablayout1.addTab(tablayout1.newTab().setText("Image Gallery"));
		tablayout1.addTab(tablayout1.newTab().setText("Video Gallery"));
		tablayout1.setTabTextColors(0xFF757575, 0xFF1877F2);
		tablayout1.setTabRippleColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}},

				new int[] {0xFFBBDEFB}));
		tablayout1.setSelectedTabIndicatorColor(0xFF1877F2);
		tablayout1.setSelectedTabIndicatorHeight(10);

		tablayout1.setElevation((float)25);

	}


	public void _request_api (final String _method) {

		gallery_api.startRequestNetwork(RequestNetworkController.GET, "https://new.mlu.ac.in/api/v1/"+_method, "no tag", _gallery_request_listener);


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


	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public Recyclerview1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.gallery_image_custom, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new Recyclerview1Adapter.ViewHolder(_v);
		}



		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onBindViewHolder(Recyclerview1Adapter.ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;



			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView c_name = _view.findViewById(R.id.c_name);


			final WebView wb = _view.findViewById(R.id.webview1);

			final  CardView gallery_layout = _view.findViewById(R.id.gallery_view);

			final  CardView video_layout = _view.findViewById(R.id.video_view);


			wb.getSettings().setLoadWithOverviewMode(true);
			wb.getSettings().setUseWideViewPort(true);
			final WebSettings webSettings = wb.getSettings();
			final String newUserAgent;
			newUserAgent = "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
			webSettings.setUserAgentString(newUserAgent);
			wb.getSettings().setJavaScriptEnabled(true);
			wb.getSettings().setSupportZoom(true);
			wb.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
					final String _url = _param2;

				/*	if (!_url.contains("data:text/html ,<html><html><body><iframe width=\"600\" height=\"180\" src=\"https://www.youtube.com/embed/RK0IokOKO1g\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe><html>")) {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse(_url));
						startActivity(i);
						//wb.loadUrl("webview1.loadUrl(\"data:text/html ,<html>\".concat(\"<html><body><iframe width=\\\"600\\\" height=\\\"180\\\" src=\\\"https://www.youtube.com/embed/RK0IokOKO1g\\\" title=\\\"YouTube video player\\\" frameborder=\\\"0\\\" allow=\\\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\\\" allowfullscreen></iframe>\".concat(\"<html>\")));");
					}*/

					super.onPageStarted(_param1, _param2, _param3); }

				@Override
				public void onPageFinished(WebView _param1, String _param2) {
					final String _url = _param2;

					super.onPageFinished(_param1, _param2);
				}
			});


			if(gallery_view){

				gallery_layout.setVisibility(View.VISIBLE);
				video_layout.setVisibility(View.GONE);
				c_name.setText(Objects.requireNonNull(listmap2.get(_position).get("title")).toString());


			} else  {

				String video_code = Objects.requireNonNull(listmap2.get(_position).get("embed_code")).toString();
				wb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
				wb.loadUrl("data:text/html ,<html>".concat(video_code.concat("<html>")));

				video_layout.setVisibility(View.VISIBLE);
				gallery_layout.setVisibility(View.GONE);

			}

			c_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);



			try{







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






	public void close(View view){

		finish();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

	
}
