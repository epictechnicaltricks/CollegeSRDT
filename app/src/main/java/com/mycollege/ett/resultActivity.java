package com.mycollege.ett;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.Objects;


public class resultActivity extends  AppCompatActivity  {


	private LinearLayout linear1,result_linear_layout;
	//private TextView textview1;

	private ArrayList<HashMap<String, Object>> result = new ArrayList<>();

	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;

	ImageView img_view_result;

	String fontName="";

	//ArrayList<HashMap<String, Object>> resultActivity = new ArrayList<>();

	private RequestNetwork result_api;
	private RequestNetwork.RequestListener _result_request_listener;


	private ArrayList<HashMap<String, Object>> exam_map = new ArrayList<>();

	private ListView listview1;

	private HashMap<String, Object> api_map = new HashMap<>();

	private final ArrayList<HashMap<String, Object>> exam_listmap = new ArrayList<>();

	private RequestNetwork exam_api;
	private RequestNetwork.RequestListener _exam_request_listener;

	HashMap<String, Object> authorization = new HashMap<>();



	private String list = "";

	private HashMap<String, Object> api_map_result = new HashMap<>();


	HashMap<String, Object> result_map = new HashMap<>();

	ArrayList<HashMap<String, Object>> exam_list = new ArrayList<>();

	String exam_id;
	Spinner exam_spinner;

	String token="";


	private TextView teacher_comment;

	private TextView title;

	private TextView mark;

	private TextView grade,gpa,tmark;

	private TextView performance;

	ProgressBar prog1;
	TextView msg;

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
		exam_spinner = findViewById(R.id.spinner_exam);
		//linear1 = findViewById(R.id.linear1);
		//textview1 = findViewById(R.id.textview1);

		prog1 = findViewById(R.id.progressbar1);

		msg = findViewById(R.id.msg);

		result_linear_layout = findViewById(R.id.result_layout_linear);

		teacher_comment = findViewById(R.id.teacher_comment);

		title = findViewById(R.id.title_);

		//mark = findViewById(R.id.mark);

		//grade = findViewById(R.id.grade);

		//performance = findViewById(R.id.performance);

		gpa = findViewById(R.id.gpa_);

		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);
		img_view_result = findViewById(R.id.imageview_result);
		listview1= findViewById(R.id.listview_exam_);
        tmark=findViewById(R.id.tmark);



		exam_api = new RequestNetwork(this);

		exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int _position, long l) {

				if(!Objects.requireNonNull(exam_map.get(_position).get("id")).toString().equals("")){

					exam_id = Objects.requireNonNull(exam_map.get(_position).get("id")).toString();

					SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
					//sh.getString("token", "")
					//userId
					// id

					prog1.setVisibility(View.VISIBLE);
				req_result_api(exam_id,"20",sh.getString("token", ""));

				//String st = 	exam_id+"\n\n"+sh.getString("userId", "")+"\n\n"+sh.getString("token", "");


				//	Log.d("info_data",st);
		        //req_result_api("1","20","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyIiwicm9sZSI6IjQiLCJyb2xlVGV4dCI6IlN0dWRlbnQiLCJuYW1lIjoiRGViZW5kcmEgS3VtYXIgU2Fob28iLCJsYXN0TG9naW4iOiIyMDIyLTExLTI0IDA0OjQ1OjQ4IiwiaXNMb2dnZWRJbiI6dHJ1ZSwiQVBJX1RJTUUiOjE2NzAyMTYyNDN9.VL2KHOT55pfOznC2XmP6mlqUCt12AGGnaw_TMLTHlEw");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		_exam_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				try {
					msg.setText("Getting..");
					api_map.clear();
					exam_map.clear();

					api_map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());


					// must add resultSet
					//" list " is a String datatype
					list = (new Gson()).toJson(api_map.get("exams"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

					//api_map3.get("img_url")).toString()
					// Log.d("img_obj", list);
					exam_map = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

					listview1.setAdapter(new Listview1Adapter(exam_map));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					exam_spinner.setAdapter(new Listview1Adapter(exam_map));


				}catch (Exception e){

					Toast.makeText(resultActivity.this, "LINE 191"+ e.toString(), Toast.LENGTH_SHORT).show();
				}


			}

			@Override
			public void onErrorResponse(String tag, String message) {

				prog1.setVisibility(View.GONE);
                msg.setText("No internet! Please restart the app");
				Toast.makeText(resultActivity.this, "No internet!", Toast.LENGTH_LONG).show();

			}
		};



		_result_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				try {

					//Log.d("error25",response);

					prog1.setVisibility(View.GONE);

					if(response.contains("Exam"))
					{


						api_map_result.clear();
						result.clear();
						api_map_result = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());


						//must add resultSet
						//" list " is a String datatype


						title.setText(api_map_result.get("Exam").toString());
						gpa.setText("GPA :"+api_map_result.get("gpa").toString());
						tmark.setText("Total mark :"+api_map_result.get("totalmark").toString().replaceAll("[.]0",""));

						if(api_map_result.get("Teacher comment").toString().trim().equals(""))
							teacher_comment.setText("No comments..");
						else teacher_comment.setText(api_map_result.get("Teacher comment").toString());

						String list2 = (new Gson()).toJson(api_map_result.get("result"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

						//api_map3.get("img_url")).toString()
						//Log.d("img_obj", list);
						result = new Gson().fromJson(list2, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());



						if(result.size()>0){

							/*result_linear_layout.setVisibility(View.VISIBLE);
							subject_name.setText(Objects.requireNonNull(result.get(0).get("Subject")).toString());
							mark.setText(Objects.requireNonNull(result.get(0).get("Obtaine mark")).toString()+" / ");
							grade.setText(Objects.requireNonNull(result.get(0).get("Grade")).toString());
							performance.setText(Objects.requireNonNull(result.get(0).get("")).toString());*/


							recyclerview1.getLayoutParams().height=(int)(getDip(70)*result.size());
							recyclerview1.setAdapter(new Recyclerview1Adapter(result));
							recyclerview1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
							result_linear_layout.setVisibility(View.VISIBLE);
							msg.setVisibility(View.GONE);


						}else {
							Toast.makeText(resultActivity.this, "No result found.", Toast.LENGTH_LONG).show();
							result_linear_layout.setVisibility(View.GONE);
							msg.setVisibility(View.VISIBLE);
							msg.setText("No result found.");
						}

					}else {

						result_linear_layout.setVisibility(View.GONE);
						msg.setVisibility(View.VISIBLE);
						msg.setText("No result found.");
						//Toast.makeText(resultActivity.this, "Sorry, No data found.", Toast.LENGTH_LONG).show();

					}




				}catch (Exception e){
					prog1.setVisibility(View.GONE);
					Log.d("error252",e.toString()+"\n\n\n"+response);
					Toast.makeText(resultActivity.this, "ERROR LINE 252 \n\n"+ e.toString(), Toast.LENGTH_SHORT).show();


				}


			}

			@Override
			public void onErrorResponse(String tag, String message) {
				Toast.makeText(resultActivity.this, "No internet!", Toast.LENGTH_SHORT).show();
				result_linear_layout.setVisibility(View.GONE);
				prog1.setVisibility(View.GONE);
			}
		};

	}

	private void initializeLogic() {

	/*	for(int _repeat10 = 0; _repeat10 < 20; _repeat10++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("g", "");
				resultActivity.add(_item);
			}

		}
		recyclerview1.setAdapter(new Recyclerview1Adapter(resultActivity));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));

*/

		result_linear_layout.setVisibility(View.GONE);
		req_exam_api();
		_changeActivityFont("en_med");
		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"))
				.error(R.drawable.school)
				.placeholder(R.drawable.school)
				.thumbnail(0.01f)
				.into(img_view_result);


	}





	private void req_exam_api()
	{
		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

		authorization.clear();
		authorization.put("Authorization",sh.getString("token", ""));
		exam_api.setHeaders(authorization);
		//exam_api.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		exam_api.startRequestNetwork(RequestNetworkController.GET, "https://exam.infydemo.in/api/exam/get", "no tag",_exam_request_listener);

		//api_map.clear();



		//exam_api.startRequestNetwork(RequestNetworkController.GET, "https://new.mlu.ac.in/api/v1/"+_method, "no tag", _exam_request_listener);

	}


	private void req_result_api(String _exam_id, String _sid, String _token)
	{
		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
		//sh.getString("token", "")
		result_map.clear();
		authorization.clear();

		result_map = new HashMap<>();
		result_map.put("sid",_sid);
		result_map.put("exam_id",_exam_id);

		authorization = new HashMap<>();
		authorization.put("Authorization",_token);

		result_api.setHeaders(authorization);
		result_api.setParams(result_map, RequestNetworkController.REQUEST_PARAM);
		result_api.startRequestNetwork(RequestNetworkController.POST, "https://exam.infydemo.in/api/user/result", "no tag",_result_request_listener);

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


	public void close(View view){

		finish();
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

			final TextView textview1 = _view.findViewById(R.id.textview1);

			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);

			textview1.setText(Objects.requireNonNull(exam_map.get(_position).get("name")).toString());


			return _view;
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
			View _v = _inflater.inflate(R.layout.result_cus, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout header_top = (LinearLayout) _view.findViewById(R.id.header_top);

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

			subject.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.BOLD);
			  mark.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			 grade.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			perfom.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);

			if(_position==0){

				header_top.setVisibility(View.VISIBLE);
			}else {

				header_top.setVisibility(View.GONE);
			}




subject.setText(result.get((int)_position).get("Subject").toString());
mark.setText(result.get((int)_position).get("Obtaine mark").toString());
grade.setText(result.get((int)_position).get("Grade").toString());
perfom.setText(result.get((int)_position).get("").toString());
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

	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
}
