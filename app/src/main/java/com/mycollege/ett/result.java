package com.mycollege.ett;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;


public class result extends  AppCompatActivity  {
	
	
	private LinearLayout linear1;
	private TextView textview1;

	private final ArrayList<HashMap<String, Object>> result = new ArrayList<>();

	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;

	ImageView img_view_result;

	String fontName="";

	ArrayList<HashMap<String, Object>> result_listmap = new ArrayList<>();

	private RequestNetwork result_api;
	private RequestNetwork.RequestListener _result_request_listener;


	private String list = "";

	private HashMap<String, Object> api_map = new HashMap<>();


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.result_layout);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {

		result_api = new RequestNetwork(this);

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview1 = (TextView) findViewById(R.id.textview1);

		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		img_view_result = findViewById(R.id.imageview_result);

		_result_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				//api_map.clear();
			//	api_map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());


				// must add resultSet
				//" list " is a String datatype
			//	list = (new Gson()).toJson(api_map.get("result"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

				//api_map3.get("img_url")).toString()
				// Log.d("img_obj", list);
			//	result_api = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

				//recyclerview1.setAdapter(new Recyclerview1Adapter(exam_map));
				//recyclerview1.setLayoutManager(new LinearLayoutManager(this));


			}

			@Override
			public void onErrorResponse(String tag, String message) {

			}
		};

	}
	
	private void initializeLogic() {

		for(int _repeat10 = 0; _repeat10 < 20; _repeat10++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("g", "");
				result.add(_item);
			}

		}
		recyclerview1.setAdapter(new Recyclerview1Adapter(result));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));


		_changeActivityFont("en_med");
		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"))
				.error(R.drawable.school)
				.placeholder(R.drawable.school)
				.thumbnail(0.01f)
				.into(img_view_result);


	}

	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView());
	}
	private void overrideFonts(final Context context, final View v) {

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
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.result_cus, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final TextView subject = (TextView) _view.findViewById(R.id.subject);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView mark = (TextView) _view.findViewById(R.id.mark);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final TextView grade = (TextView) _view.findViewById(R.id.grade);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView perfom = (TextView) _view.findViewById(R.id.perfom);

			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			subject.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			  mark.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			 grade.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			perfom.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);


			/*
subject.setText(result.get((int)_position).get("Subject").toString());
mark.setText(result.get((int)_position).get("Obtaine mark").toString());
grade.setText(result.get((int)_position).get("Grade").toString());
perfom.setText(result.get((int)_position).get("").toString());
*/
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

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

}
