package com.mycollege.ett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;


import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.graphics.Typeface;


public class HomeActivity extends  AppCompatActivity  {

	private final Timer _timer = new Timer();

	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private String fontName = "";
	private final String typeace = "";
	private double count = 0;

	private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private final ArrayList<HashMap<String, Object>> listmap2 = new ArrayList<>();

	private LinearLayout linear1;
	private LinearLayout slider_layouf;
	private ScrollView vscroll1;
	private ImageView drawer_open;
	private TextView textview1;
	private ImageView notification;
	private ViewPager viewpager1;
	private LinearLayout bg;
	private TextView textview2;
	private YouTubePlayerView youtube1;
	private TextView textview3;
	private LinearLayout linear5;
	private TextView textview4;
	private RecyclerView recyclerview1;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_image_layout;
	private ScrollView _drawer_vscroll1;
	private TextView _drawer_name;
	private TextView _drawer_id;
	private LinearLayout _drawer_bottom_bg;
	private LinearLayout _drawer_profile;
	private LinearLayout _drawer_exam;
	private LinearLayout _drawer_linear6;
	private LinearLayout _drawer_gallery;
	private LinearLayout _drawer_logout;
	private ImageView _drawer_imageview1;
	private TextView _drawer_textview2;
	private ImageView _drawer_imageview2;
	private TextView _drawer_textview3;
	private ImageView _drawer_imageview3;
	private TextView _drawer_textview4;
	private ImageView _drawer_imageview4;
	private TextView _drawer_textview5;
	private ImageView _drawer_imageview5;
	private TextView _drawer_textview6;
	private TextView _drawer_textview7;
	private LinearLayout _drawer_login;
	private ImageView _drawer_profile_image;

	private TimerTask scroll_time;
	private final Intent in = new Intent();

	String youtube_player_api ="AIzaSyBZtKZFJ5QFj7BrWGoW8qSzTJyebDM42AM";
	private YouTubePlayerView ytPlayer;


	LinearLayout
			apply,
			program,
			about,
			placements,
			news,
			site ,
			pay,
			skill,
			call;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {





		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(HomeActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();

		LinearLayout _nav_view = findViewById(R.id._nav_view);

		linear1 = findViewById(R.id.linear1);
		slider_layouf = findViewById(R.id.slider_layouf);
		vscroll1 = findViewById(R.id.vscroll1);
		drawer_open = findViewById(R.id.drawer_open);
		textview1 = findViewById(R.id.textview1);
		notification = findViewById(R.id.notification);
		viewpager1 = findViewById(R.id.viewpager1);
		bg = findViewById(R.id.bg);
		textview2 = findViewById(R.id.textview2);

		textview3 = findViewById(R.id.textview3);
		linear5 = findViewById(R.id.linear5);
		textview4 = findViewById(R.id.textview4);
		recyclerview1 = findViewById(R.id.recyclerview1);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_image_layout = _nav_view.findViewById(R.id.image_layout);
		_drawer_vscroll1 = _nav_view.findViewById(R.id.vscroll1);
		_drawer_name = _nav_view.findViewById(R.id.name);
		_drawer_id = _nav_view.findViewById(R.id.id);
		_drawer_bottom_bg = _nav_view.findViewById(R.id.bottom_bg);
		_drawer_profile = _nav_view.findViewById(R.id.profile);

		_drawer_login = _nav_view.findViewById(R.id.login);

		_drawer_exam = _nav_view.findViewById(R.id.exam);
		_drawer_linear6 = _nav_view.findViewById(R.id.linear6);
		_drawer_gallery = _nav_view.findViewById(R.id.gallery);
		_drawer_logout = _nav_view.findViewById(R.id.logout);
		_drawer_imageview1 = _nav_view.findViewById(R.id.imageview1);
		_drawer_textview2 = _nav_view.findViewById(R.id.textview2);
		_drawer_imageview2 = _nav_view.findViewById(R.id.imageview2);
		_drawer_textview3 = _nav_view.findViewById(R.id.textview3);
		_drawer_imageview3 = _nav_view.findViewById(R.id.imageview3);
		_drawer_textview4 = _nav_view.findViewById(R.id.textview4);
		_drawer_imageview4 = _nav_view.findViewById(R.id.imageview4);
		_drawer_textview5 = _nav_view.findViewById(R.id.textview5);
		_drawer_imageview5 = _nav_view.findViewById(R.id.imageview5);
		_drawer_textview6 = _nav_view.findViewById(R.id.textview6);
_drawer_profile_image = _nav_view.findViewById(R.id.profile_image);

		// Get reference to the view of Video player
		/*@SuppressLint("CutPasteId")
		YouTubePlayerView ytPlayer = findViewById(R.id.youtube1);*/



		apply  = findViewById(R.id.apply  );
		program= findViewById(R.id.program);
		about  = findViewById(R.id.about  );
		placements= findViewById(R.id.placements);
		news= findViewById(R.id.news);
		site= findViewById(R.id.site);
		pay = findViewById(R.id.pay );
		skill  = findViewById(R.id.skill  );
		call= findViewById(R.id.call);




		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showMessage(" clicked apply     ");
			}
		});
		program .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showMessage(" clicked program");

			}
		});
		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage(" clicked about     ");

			}
		});
		placements .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showMessage(" clicked placements");

			}
		});
		news .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {



				SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);



						if(sh.getString("userId", "").equals(""))
						{

							showMessage("Please login to continue..");
							startActivity(new Intent(getApplicationContext(), AuthActivity.class));

						} else {

							startActivity(new Intent(getApplicationContext(), news.class));

						}

			}
		});
		site .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showMessage(" clicked site      ");

			}
		});
		pay  .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showMessage(" clicked pay     ");

			}
		});
		skill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				showMessage(" clicked skill     ");

			}
		});
		call .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getApplicationContext(), Contact.class));
			}
		});



		drawer_open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});

		notification.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), NotificationActivity.class);
				startActivity(in);
			}
		});

		viewpager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int _position) {

			}

			@Override
			public void onPageScrollStateChanged(int _scrollState) {

			}
		});

		_drawer_image_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

			}
		});

		_drawer_profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), ProfileActivity.class);
				startActivity(in);
			}
		});

		_drawer_gallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

			}
		});

		_drawer_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getApplicationContext(),AuthActivity.class));
			}
		});

		_drawer_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				SharedPreferences sha = getSharedPreferences("MySharedPref",MODE_PRIVATE);
				SharedPreferences.Editor editor = sha.edit();
				editor.clear();
				editor.apply();

				startActivity(new Intent(getApplicationContext(),AuthActivity.class));
				finish();

			}
		});
	}

	private void initializeLogic() {
		_changeActivityFont("en_med");
		_slider();
		_hide();



		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);




		if(sh.getString("userId", "").equals(""))
		{
			_drawer_login.setVisibility(View.VISIBLE);
			_drawer_logout.setVisibility(View.GONE);


		} else {

			_drawer_login.setVisibility(View.GONE);
			_drawer_logout.setVisibility(View.VISIBLE);

			try{


				showMessage("Welcome "+ sh.getString("name", ""));

				//phone.setText(_phone);
				_drawer_name.setText(sh.getString("name", ""));
				_drawer_id.setText(sh.getString("userId", ""));

/*
			Glide.with(getApplicationContext())
					.load(Uri.parse(Objects.requireNonNull(sh.getString("avatar", ""))))
					.error(R.drawable.person)
					.placeholder(R.drawable.person)
					.thumbnail(0.01f)
					.into(_drawer_profile_image);
*/

				Glide.with(getApplicationContext())
						.load(Uri.parse("https://wallpapercave.com/wp/wp11736579.jpg"))
						.error(R.drawable.person)
						.placeholder(R.drawable.person)
						.thumbnail(0.01f)
						.into(_drawer_profile_image);

				// https://wallpapercave.com/wp/wp11736579.jpg
				//email.setText(_email);
				//userid.setText(_id);
				//bill.setText(_bill +"\nbill_id");

			}catch(Exception e)
			{
				e.printStackTrace();
			}

		}





		/*ytPlayer.initialize(
				youtube_player_api,
				new YouTubePlayer.OnInitializedListener() {
					// Implement two methods by clicking on red
					// error bulb inside onInitializationSuccess
					// method add the video link or the playlist
					// link that you want to play In here we
					// also handle the play and pause
					// functionality
					@Override
					public void onInitializationSuccess(
							YouTubePlayer.Provider provider,
							YouTubePlayer youTubePlayer, boolean b)
					{
						youTubePlayer.loadVideo("RK0IokOKO1g");
						youTubePlayer.play();
					}

					// Inside onInitializationFailure
					// implement the failure functionality
					// Here we will show toast
					@Override
					public void onInitializationFailure(YouTubePlayer.Provider provider,
														YouTubeInitializationResult
																youTubeInitializationResult)
					{
						Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
					}
				});

*/

		linear1.setElevation((float)5);
		notification.setColorFilter(0xFFFFFFFF);
		drawer_open.setColorFilter(0xFFFFFFFF);
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://images.unsplash.com/photo-1567515004624-219c11d31f2e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bWFuJTIwZ2xhc3Nlc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1100&q=60");
			listmap2.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://images.unsplash.com/photo-1618077360395-f3068be8e001?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bWFuJTIwZ2xhc3Nlc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1100&q=60");
			listmap2.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://images.unsplash.com/photo-1584607839936-b6c6964330ba?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8bWFuJTIwZ2xhc3Nlc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1100&q=60");
			listmap2.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://images.unsplash.com/photo-1567515004624-219c11d31f2e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bWFuJTIwZ2xhc3Nlc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1100&q=60");
			listmap2.add(_item);
		}

		recyclerview1.setAdapter(new Recyclerview1Adapter(listmap2));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

		super.onActivityResult(_requestCode, _resultCode, _data);

	}

	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
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


	public void _slider () {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://new.mlu.ac.in/assets/uploads/media-uploader/mluslider31668096628.jpg");
			listmap.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("image", "https://new.mlu.ac.in/assets/uploads/media-uploader/mluslider21668096623.jpg");
			listmap.add(_item);
		}

		final float scaleFactor = 0.96f; viewpager1.setPageMargin(-15); viewpager1.setOffscreenPageLimit(2); viewpager1.setPageTransformer(false, new ViewPager.PageTransformer() { @Override public void transformPage(@NonNull View page1, float position) { page1.setScaleY((1 - Math.abs(position) * (1 - scaleFactor))); page1.setScaleX(scaleFactor + Math.abs(position) * (1 - scaleFactor)); } });
		viewpager1.setAdapter(new Viewpager1Adapter(listmap));
		scroll_time = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						viewpager1.setCurrentItem((int)count);
						count++;
						if (count == 4) {
							count = 0;
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(scroll_time, 1500, 2000);
	}


	public void _hide () {
		try{
			getSupportActionBar().hide();
		} catch (Exception e){}
		//mahdi_313
	}


	public class Viewpager1Adapter extends PagerAdapter {
		Context _context;
		ArrayList<HashMap<String, Object>> _data;
		public Viewpager1Adapter(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {
			_context = _ctx;
			_data = _arr;
		}

		public Viewpager1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_context = getApplicationContext();
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public boolean isViewFromObject(View _view, Object _object) {
			return _view == _object;
		}

		@Override
		public void destroyItem(ViewGroup _container, int _position, Object _object) {
			_container.removeView((View) _object);
		}

		@Override
		public int getItemPosition(Object _object) {
			return super.getItemPosition(_object);
		}

		@Override
		public CharSequence getPageTitle(int pos) {
			// use the activitiy event (onTabLayoutNewTabAdded) in order to use this method
			return "page " + pos;
		}

		@Override
		public  Object instantiateItem(ViewGroup _container,  final int _position) {
			View _view = LayoutInflater.from(_context).inflate(R.layout.slider, _container, false);
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			
			Glide.with(getApplicationContext()).load(Uri.parse(listmap.get(_position).get("image").toString())).into(imageview1);
			
			_container.addView(_view);
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
			View _v = _inflater.inflate(R.layout.custom_slide, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_light.ttf"), Typeface.NORMAL);
			Glide.with(getApplicationContext()).load(Uri.parse(listmap2.get(_position).get("image").toString())).into(imageview2);
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
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
