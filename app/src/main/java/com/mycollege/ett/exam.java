package com.mycollege.ett;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class exam extends  AppCompatActivity  {
	
	String fontName="";
	private LinearLayout linear1;
	private TextView textview1;

	private ArrayList<HashMap<String, Object>> exam_map = new ArrayList<>();

	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;
	ImageView img_view_exam;

	private String list = "";

	private HashMap<String, Object> api_map = new HashMap<>();

	//private  ArrayList<HashMap<String, Object>> exam_listmap = new ArrayList<>();

	private RequestNetwork exam_api;
	private RequestNetwork.RequestListener _exam_request_listener;

	HashMap<String, Object> authorization = new HashMap<>();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.exam_layout);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {


		exam_api = new RequestNetwork(this);

		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);

		recyclerview1 = findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);

		img_view_exam = findViewById(R.id.imageview_exam);


		_exam_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				Toast.makeText(exam.this, response, Toast.LENGTH_SHORT).show();

				api_map.clear();
				api_map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());


				// must add resultSet
				//" list " is a String datatype
				list = (new Gson()).toJson(api_map.get("exams"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

				//api_map3.get("img_url")).toString()
				// Log.d("img_obj", list);
				exam_map = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

				recyclerview1.setAdapter(new Recyclerview1Adapter(exam_map));
				recyclerview1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


			}

			@Override
			public void onErrorResponse(String tag, String message) {

			}
		};


	}
	
	private void initializeLogic() {
	/*	for(int _repeat10 = 0; _repeat10 < 10; _repeat10++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("bv", "");
				exam_map.add(_item);
			}

		}
		recyclerview1.setAdapter(new Recyclerview1Adapter(exam_map));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));*/
		_changeActivityFont("en_med");


		req_exam_api();


		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1434030216411-0b793f4b4173?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZXhhbXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"))
				.error(R.drawable.school)
				.placeholder(R.drawable.school)
				.thumbnail(0.01f)
				.into(img_view_exam);

	}


	private void req_exam_api()
	{
		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//sh.getString("token", "")
		authorization.clear();
		authorization.put("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyIiwicm9sZSI6IjQiLCJyb2xlVGV4dCI6IlN0dWRlbnQiLCJuYW1lIjoiRGViZW5kcmEgS3VtYXIgU2Fob28iLCJsYXN0TG9naW4iOiIyMDIyLTExLTI0IDA0OjQ1OjQ4IiwiaXNMb2dnZWRJbiI6dHJ1ZSwiQVBJX1RJTUUiOjE2Njk5ODE0MzJ9.GnZXnMKufGFEx9GHklORreKyIzenyg9KtuH0Q15bMU4");
		exam_api.setHeaders(authorization);
		//exam_api.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		exam_api.startRequestNetwork(RequestNetworkController.GET, "https://exam.infydemo.in/api/exam/get", "no tag",_exam_request_listener);

		//api_map.clear();



		//exam_api.startRequestNetwork(RequestNetworkController.GET, "https://new.mlu.ac.in/api/v1/"+_method, "no tag", _exam_request_listener);

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

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.exam_cus, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final LinearLayout linear8 = _view.findViewById(R.id.linear8);
			final LinearLayout linear9 = _view.findViewById(R.id.linear9);
			final TextView result = _view.findViewById(R.id.result);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView term = _view.findViewById(R.id.term);
			final TextView name = _view.findViewById(R.id.name);
			final TextView semister = _view.findViewById(R.id.semister);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final TextView exam_date = _view.findViewById(R.id.exam_date);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final TextView form_fill_date = _view.findViewById(R.id.form_fill_date);
			final TextView textview6 = _view.findViewById(R.id.textview6);
			final TextView end_form_date = _view.findViewById(R.id.end_form_date);
			final TextView textview7 = _view.findViewById(R.id.textview7);
			final TextView fees = _view.findViewById(R.id.fees);
			final TextView textview8 = _view.findViewById(R.id.textview8);
			final TextView late_fees = _view.findViewById(R.id.late_fees);




            name.setText(exam_map.get((int)_position).get("name").toString());
            semister.setText(exam_map.get((int)_position).get("semistar").toString());
            term.setText(exam_map.get((int)_position).get("term").toString());
            exam_date.setText(exam_map.get((int)_position).get("exam_date").toString());
            form_fill_date.setText(exam_map.get((int)_position).get("form_fillup_start_date").toString());
            end_form_date.setText(exam_map.get((int)_position).get("form_fillup_last_date").toString());
            fees.setText(exam_map.get((int)_position).get("form_fee").toString());
            late_fees.setText(exam_map.get((int)_position).get("late_fee").toString());




			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			     name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			          term.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			     exam_date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			form_fill_date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			 end_form_date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			          fees.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			     late_fees.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			      semister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			        result.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
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

	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

}
