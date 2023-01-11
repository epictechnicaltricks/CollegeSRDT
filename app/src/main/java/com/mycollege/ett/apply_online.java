package com.mycollege.ett;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;

import java.io.IOException;
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

	Spinner gender_spinner;
	Spinner religion_spinner;
	Spinner nation_spinner;
	Spinner state_spinner;
	Spinner city_spinner;
	Spinner qualification_spinner;



	private ListView

			listview1,
			listview2,
			listview3,
			listview4,
			listview5,
			listview6,
			listview7;



	private RequestNetwork program_api;
	private RequestNetwork.RequestListener _program_api_listener;

	//private HashMap<String, Object> program_map = new HashMap<>();

	private HashMap<String, Object> api_map2 = new HashMap<>();
	private HashMap<String, Object> submit_map = new HashMap<>();
	private HashMap<String, Object> api_map = new HashMap<>();
	private  ArrayList<HashMap<String, Object>> listmap2 = new ArrayList<>();

	String list="";


	String api_mlu = "https://mlu.ac.in/api/v1/";
	String submit_api = "https://student.mlu.ac.in/api/course/inquiry/insert";

	ProgressBar prog;

	String
			program_name,
	        gender_name,
			religion_name,
			nation_name,
			state_name="Odisha",
			city_name="Bubaneswar",
			qualification_name;




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


	ArrayList<HashMap<String, Object>> gender_list = new ArrayList<>();

	ArrayList<HashMap<String, Object>> religion_list = new ArrayList<>();

	ArrayList<HashMap<String, Object>> nation_list = new ArrayList<>();

	ArrayList<HashMap<String, Object>> state_list = new ArrayList<>();

	ArrayList<HashMap<String, Object>> city_list = new ArrayList<>();

	ArrayList<HashMap<String, Object>> city_list_filtered = new ArrayList<>();


	ArrayList<HashMap<String, Object>> qualification_list = new ArrayList<>();


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.apply_online_layout);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		try {
			initializeLogic();
		}catch (IOException e){

			showMessage(e.toString());
		}

	}
	
	private void initialize(Bundle _savedInstanceState) {


		submit = findViewById(R.id.submit_);
		program_api = new RequestNetwork(this);
		submit_api_call = new RequestNetwork(this);

        reset = findViewById(R.id.reset_);

		name_     = findViewById(R.id.  name_       );
		email_    = findViewById(R.id.  email_      );
		mobile_   = findViewById(R.id.  mobile_     );




		//gender_   = findViewById(R.id.  gender_     );
		//cast_     = findViewById(R.id.  cast_       );
		//national_ = findViewById(R.id.  national_   );
		//state_    = findViewById(R.id.  state_      );
		//city_     = findViewById(R.id.  city_       );
		//qual_     = findViewById(R.id.  qual_       );


		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		back = findViewById(R.id.back);
		imageview = findViewById(R.id.imageview);

		listview1 =  findViewById(R.id.listview_program);
		listview2 =  findViewById(R.id.listview_gender);
		listview3 =  findViewById(R.id.listview_religion);
		listview4 =  findViewById(R.id.listview_nationality);
		listview5 =  findViewById(R.id.listview_state);
		listview6 =  findViewById(R.id.listview_city);
		listview7 =  findViewById(R.id.listview_qualification);




		programs_list_spinner = findViewById(R.id.spinner_program);



		gender_spinner= findViewById(R.id.spinner_gender);
		religion_spinner= findViewById(R.id.spinner_religion);
		nation_spinner= findViewById(R.id.spinner_nationality);
		state_spinner= findViewById(R.id.spinner_state);
		city_spinner= findViewById(R.id.spinner_city);

		qualification_spinner = findViewById(R.id.spinner_qualification);



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


				} catch (JSONException ex) {
					showMessage("255 line "+ex.toString());
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

				program_name = Objects.requireNonNull(listmap2.get(_position).get("title")).toString();
				//showMessage(program_name);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	});


		gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				if(!Objects.requireNonNull(gender_list.get(_position).get("title")).toString().equals("")){

					gender_name = Objects.requireNonNull(gender_list.get(_position).get("title")).toString();
					//showMessage();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		religion_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				if(!Objects.requireNonNull(religion_list.get(_position).get("title")).toString().equals("")){

					religion_name = Objects.requireNonNull(religion_list.get(_position).get("title")).toString();
					//showMessage(program_name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		nation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				if(!Objects.requireNonNull(nation_list.get(_position).get("title")).toString().equals("")){

					nation_name = Objects.requireNonNull(nation_list.get(_position).get("title")).toString();
					//showMessage(program_name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				city_list_filtered.clear();

				if(!Objects.requireNonNull(state_list.get(_position).get("name")).toString().equals("")){

					state_name = Objects.requireNonNull(state_list.get(_position).get("name")).toString();

					try {
						java.io.InputStream inputstream1 = getAssets().open("city.json");
						city_list = new Gson().fromJson(Util.copyFromInputStream(inputstream1), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());



						for(int x=0; city_list.size()>x; x++) {


							if(Objects.requireNonNull(city_list.get(x).get("state")).toString().equals(state_name)){
								{
									HashMap<String, Object> _item = new HashMap<>();
									_item.put("name", Objects.requireNonNull(city_list.get(x).get("name")).toString());
									city_list_filtered.add(_item);
								}

							}

						}
						//showMessage("refresh");
						Util.sortListMap(city_list_filtered,"name",false,true);
						listview6.setAdapter(new Listview6Adapter(city_list_filtered));
						((BaseAdapter)listview6.getAdapter()).notifyDataSetChanged();
						city_spinner.setAdapter(new Listview6Adapter(city_list_filtered));

					}catch (Exception e){


					}



					//showMessage(program_name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				if(!Objects.requireNonNull(city_list.get(_position).get("name")).toString().equals("")){

					city_name = Objects.requireNonNull(city_list.get(_position).get("name")).toString();
					//showMessage(program_name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		qualification_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int _position, long id) {

				if(!Objects.requireNonNull(qualification_list.get(_position).get("title")).toString().equals("")){

					qualification_name = Objects.requireNonNull(qualification_list.get(_position).get("title")).toString();
					//showMessage(program_name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});





		_submit_api_listener = new RequestNetwork.RequestListener() {
		@Override
		public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

			Log.d("submit",response);

			prog.setVisibility(View.GONE);


			if(response.contains("success")){

				showMessage("Submitted Successfully");
				startActivity(new Intent(getApplicationContext(), apply_online.class));
				finish();

			} else {

				showMessage("Failed to submit\n\n"+response);
			}


		}

		@Override
		public void onErrorResponse(String tag, String message) {


			showMessage("No internet!");
		}
	};



	submit.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {


			         if( program_name          .equals("") ||
					     gender_name           .equals("Select here..") ||
						 religion_name         .equals("Select here..") ||
						 nation_name           .equals("Select here..") ||
						 state_name            .equals("Select here..") ||
						 city_name             .equals("Select here..") ||
						 qualification_name    .equals("Select here..") ||
							 name_             .getText().toString().trim().equals("") ||
							 !email_           .getText().toString().trim().contains("@")||
							 mobile_           .getText().toString().trim().equals("")


					 ){


			showMessage("Some fields are empty.. or invalid");
			//

			} else {

						 if(mobile_    .getText().toString().trim().length() == 10){

							 _submit_api_request(
									   nation_name
									 , name_.getText().toString()
									 , email_.getText().toString()
									 , state_name
									 , mobile_.getText().toString()
									 , city_name
									 , program_name
									 , qualification_name
									 , gender_name
									 , religion_name);

						 } else {

						 	 //
							 showMessage("Invalid mobile no.");
						 }




			}


		}
	});


reset.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {

		startActivity(new Intent(getApplicationContext(), apply_online.class));
		finish();
	}
});

	/// INIT END

	}


	public void _submit_api_request(String _nationality,
									  String _name,
									  String _email, String _state,
									  String _mob, String _city, String _program_name,
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
		submit_map.put("program", _program_name);
		submit_map.put("qualification", _qualification.trim());
		submit_map.put("gender", _gender.trim());
		submit_map.put("caste", _caste.trim());

		submit_api_call.setParams(submit_map, RequestNetworkController.REQUEST_PARAM);
		submit_api_call.startRequestNetwork(RequestNetworkController.POST, submit_api, "no tag", _submit_api_listener);


		//textview1.setText(_role +"\n"+_class_name+"\n"+_department+"\n"+_year+"\n"+_blg+"\n");

		//Toast.makeText(this, "Login complete", Toast.LENGTH_SHORT).show();
	}



	public void close(View view){

		finish();
	}
	
	private void initializeLogic() throws IOException {
		_changeActivityFont("en_med");
		gender_data();
		nation_data();
		qualify_data();
		religion_data();
		//city_data();
		//state_data();


		//PROJECT BY SHUBHAMJIT ON 2022 DEC 1
		// 3RD SEM BTECH FROM RAAJDHANI ENGINEERING COLLEGE, BBSR , ODISHA
		try {
			java.io.InputStream inputstream1 = getAssets().open("state.json");
			state_list = new Gson().fromJson(Util.copyFromInputStream(inputstream1), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

			//Util.sortListMap(state_list,"name",false,true);
			listview5.setAdapter(new Listview5Adapter(state_list));
			((BaseAdapter)listview5.getAdapter()).notifyDataSetChanged();
			state_spinner.setAdapter(new Listview5Adapter(state_list));


		}catch (Exception e){


		}



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



	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}



	private void gender_data() {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			gender_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "MALE");
			gender_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "FEMALE");
			gender_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "OTHER");
			gender_list.add(_item);
		}



		listview2.setAdapter(new Listview4Adapter(gender_list));
		((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
		gender_spinner.setAdapter(new Listview2Adapter(gender_list));

	}

	private void religion_data()
	{
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Hindu");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Muslim");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Christian");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Buddhist");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Jain");
			religion_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Other");
			religion_list.add(_item);
		}

		listview3.setAdapter(new Listview4Adapter(religion_list));
		((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
		religion_spinner.setAdapter(new Listview3Adapter(religion_list));

	}

	private void state_data() {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			state_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Odisha");
			state_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Telangana");
			state_list.add(_item);
		}


		listview5.setAdapter(new Listview5Adapter(state_list));
		((BaseAdapter)listview5.getAdapter()).notifyDataSetChanged();
		state_spinner.setAdapter(new Listview5Adapter(state_list));

	}



	private void city_data() {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			city_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Bhubaneswar");
			city_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Cuttack");
			city_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Puri");
			city_list.add(_item);
		}


		listview6.setAdapter(new Listview6Adapter(city_list));
		((BaseAdapter)listview6.getAdapter()).notifyDataSetChanged();
		city_spinner.setAdapter(new Listview6Adapter(city_list));

	}

	private void nation_data()
	{
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			nation_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "INDIAN");
			nation_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "OTHER");
			nation_list.add(_item);
		}


		listview4.setAdapter(new Listview4Adapter(nation_list));
		((BaseAdapter)listview4.getAdapter()).notifyDataSetChanged();
		nation_spinner.setAdapter(new Listview4Adapter(nation_list));

	}



	private void qualify_data()
	{
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Select here..");
			qualification_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "BE/B.Tech");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "BE/M.Tech");
			qualification_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "B.Arch");
			qualification_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "LL.B.");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "LL.M.");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "B.Sc/BCA");
			qualification_list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "BBA/MBA");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "B.Com/M.Com");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Ph.D.");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "MBL-LLM/ MBA-LLM");
			qualification_list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", "Other");
			qualification_list.add(_item);
		}




		listview7.setAdapter(new Listview7Adapter(qualification_list));
		((BaseAdapter)listview7.getAdapter()).notifyDataSetChanged();
		qualification_spinner.setAdapter(new Listview7Adapter(qualification_list));

	}


	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
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
	public class Listview2Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			textview1.setText(Objects.requireNonNull(gender_list.get((int) _position).get("title")).toString());


			return _view;
		}
	}
	public class Listview3Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			textview1.setText(Objects.requireNonNull(religion_list.get((int) _position).get("title")).toString());


			return _view;
		}
	}
	public class Listview4Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			textview1.setText(Objects.requireNonNull(nation_list.get((int) _position).get("title")).toString());


			return _view;
		}
	}
	public class Listview5Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview5Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			//if(state_list.get(_position).get("state")).toString().contains())
			textview1.setText(Objects.requireNonNull(state_list.get(_position).get("name")).toString());


			return _view;
		}
	}
	public class Listview6Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview6Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			textview1.setText(Objects.requireNonNull(city_list_filtered.get((int) _position).get("name")).toString());


			return _view;
		}
	}
	public class Listview7Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview7Adapter(ArrayList<HashMap<String, Object>> _arr) {
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

			textview1.setText(Objects.requireNonNull(qualification_list.get((int) _position).get("title")).toString());


			return _view;
		}
	}



	
}
