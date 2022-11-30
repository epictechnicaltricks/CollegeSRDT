package com.mycollege.ett;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;


public class apply_online extends  AppCompatActivity  {
	
	
	private LinearLayout linear1;
	private TextView textview1;
	private ImageView back;
	private String fontName = "";
	ImageView imageview;

	Spinner programs_list_spinner;

	String program_id, program_name;

	private ListView listview1;

	private RequestNetwork program_api;
	private RequestNetwork.RequestListener _program_api_listener;

	//private HashMap<String, Object> program_map = new HashMap<>();

	private HashMap<String, Object> api_map2 = new HashMap<>();
	private HashMap<String, Object> submit_map = new HashMap<>();
	private HashMap<String, Object> api_map = new HashMap<>();
	private  ArrayList<HashMap<String, Object>> listmap2 = new ArrayList<>();

	String list="";


	String api_mlu = "https://new.mlu.ac.in/api/v1/";
	String submit_api = "https://nft.digitallinkcard.xyz/api/course/inquiry/insert";

	ProgressBar prog;


	private RequestNetwork submit_api_call;
	private RequestNetwork.RequestListener _submit_api_listener;

	MaterialButton submit,reset;
	EditText name_,
			email_,
			mobile_,
			gender_,
			cast_,
			national_,
			state_,
			city_,
			qual_;




	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.apply_online_layout);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {


		submit = findViewById(R.id.submit_);
		program_api = new RequestNetwork(this);
		submit_api_call = new RequestNetwork(this);

        reset = findViewById(R.id.reset_);

		name_     = findViewById(R.id.  name_       );
		email_    = findViewById(R.id.  email_      );
		mobile_   = findViewById(R.id.  mobile_     );
		gender_   = findViewById(R.id.  gender_     );
		cast_     = findViewById(R.id.  cast_       );
		national_ = findViewById(R.id.  national_   );
		state_    = findViewById(R.id.  state_      );
		city_     = findViewById(R.id.  city_       );
		qual_     = findViewById(R.id.  qual_       );


		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		back = findViewById(R.id.back);
		imageview = findViewById(R.id.imageview);
		listview1 =  findViewById(R.id.listview_program);
		programs_list_spinner = findViewById(R.id.spinner_program);
        prog = findViewById(R.id.prog_);

		_program_api_listener = new RequestNetwork.RequestListener() {
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
						//content = obj.getString("description");
						//created_at = obj.getString("created_at");

						//String img_url_obj = obj.getString("image");

						api_map2 = new HashMap<>();
						api_map2.put("title", title);
						//api_map2.put("description", content);
						//api_map2.put("img_url", img_url_obj);
						//api_map2.put("created_at", created_at);
						listmap2.add(api_map2);
					}

					Collections.reverse(listmap2);
					listview1.setAdapter(new Listview1Adapter(listmap2));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					programs_list_spinner.setAdapter(new Listview1Adapter(listmap2));


				} catch (Exception ex) {
					showMessage("303 line "+ex.toString());
					ex.printStackTrace();
				}


			}

			@Override
			public void onErrorResponse(String tag, String message) {
               showMessage("No internet!");
               finish();
			}
		};


	programs_list_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

			if(!Objects.requireNonNull(listmap2.get(_position).get("title")).toString().equals("")){

				program_id = Objects.requireNonNull(listmap2.get(_position).get("title")).toString();
				showMessage(program_id);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	});



	_submit_api_listener = new RequestNetwork.RequestListener() {
		@Override
		public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {



			prog.setVisibility(View.GONE);

		}

		@Override
		public void onErrorResponse(String tag, String message) {


			showMessage("No internet!");
		}
	};



	submit.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {


			         if( name_      .getText().toString().trim().equals("") ||
					     email_     .getText().toString().trim().equals("") ||
						 mobile_    .getText().toString().trim().equals("") ||
						 gender_    .getText().toString().trim().equals("") ||
						 cast_      .getText().toString().trim().equals("") ||
						 national_  .getText().toString().trim().equals("") ||
						 state_     .getText().toString().trim().equals("") ||
						 city_      .getText().toString().trim().equals("") ||
							 qual_      .getText().toString().trim().equals("")){


			showMessage("Some fields are empty..");

			} else {

						 if(mobile_    .getText().toString().trim().length() == 10){

							 _submit_api_request(
									 national_.getText().toString()
									 , name_.getText().toString()
									 , email_.getText().toString()
									 , state_.getText().toString()
									 , mobile_.getText().toString()
									 , city_.getText().toString()
									 , qual_.getText().toString()
									 , gender_.getText().toString()
									 , cast_.getText().toString());

						 } else {

							 showMessage("Invalid mobile no.");
						 }




			}


		}
	});


reset.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {

		national_.setText("");
		name_.setText("");
		email_.setText("");
		state_.setText("");
		mobile_.setText("");
		city_.setText("");
		qual_.setText("");
		gender_.setText("");
		cast_.setText("");
	}
});

	/// INIT END

	}


	public void _submit_api_request(String _nationality,
									  String _name,
									  String _email, String _state,
									  String _mob, String _city,
									  String _qualification,
									  String _gender,
									  String _caste) {
		prog.setVisibility(View.VISIBLE);
		submit_map.clear();
		submit_map = new HashMap<>();
		submit_map.put("name",  _name.trim());
		submit_map.put("email", _email.trim());
		submit_map.put("mobile",  _mob.trim());
		submit_map.put("nationality", _nationality.trim());
		submit_map.put("state", _state);
		submit_map.put("city", _city.trim());
		submit_map.put("program", program_id);
		submit_map.put("qualification", _qualification.trim());
		submit_map.put("gender", _gender.trim());
		submit_map.put("caste", _caste.trim());

		submit_api_call.setParams(submit_map, RequestNetworkController.REQUEST_BODY);
		submit_api_call.startRequestNetwork(RequestNetworkController.POST, submit_api, "no tag", _submit_api_listener);

		//textview1.setText(_role +"\n"+_class_name+"\n"+_department+"\n"+_year+"\n"+_blg+"\n");

		//Toast.makeText(this, "Login complete", Toast.LENGTH_SHORT).show();
	}



	public void close(View view){

		finish();
	}
	
	private void initializeLogic() {
		_changeActivityFont("en_med");

       request_program_API();

		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1598257006675-0aaec40301f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=40"))
				.error(R.drawable.person)
				.placeholder(R.drawable.person)
				.thumbnail(0.01f)
				.into(imageview);



	}


	private void request_program_API(){

		program_api.startRequestNetwork(RequestNetworkController.GET,
				api_mlu+"courses",
				"no tag",
			_program_api_listener);

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



	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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

			textview1.setText(Objects.requireNonNull(listmap2.get((int) _position).get("title")).toString());


			return _view;
		}
	}
	
}
