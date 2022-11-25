package com.mycollege.ett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.animation.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.util.LogWriter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;

import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.graphics.Typeface;


public class AuthActivity extends  AppCompatActivity  {

	private final Timer _timer = new Timer();

	private String fontName = "";
	private final String typeace = "";
	private boolean Current = false;
	private boolean Hiden = false;
	private boolean onStart = false;
	private double count = 0;

	private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();

	private LinearLayout linear2;
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private ViewPager viewpager1;
	private LinearLayout linear10;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear7,linear12;
	private CheckBox checkbox1;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private EditText email;
	private TextView textview4;
	private LinearLayout linear9;
	private TextView textview9;
	private LinearLayout linear8;
	private EditText pass;
	private TextView textview5;
	private TextView action_btn;
	private TextView textview7,textview10;
	private ImageView imageview2;
	private EditText edittext3,edittext4;

	private RequestNetwork retweet;
	private RequestNetwork.RequestListener _retweet_request_listener;
	private TimerTask timer;
	private final Intent in = new Intent();
	private TimerTask scroll_time;

	private RequestNetwork login_api;
	private RequestNetwork.RequestListener _login_api_listener;

	private RequestNetwork register_api;
	private RequestNetwork.RequestListener _register_api_listener;

	private HashMap<String, Object> api_map = new HashMap<>();

private TextView edittext4_textview_91;


	private final ArrayList<HashMap<String, Object>> results = new ArrayList<>();

	private HashMap<String, Object> map = new HashMap<>();

	String list="";
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;

	///////////////////////////////////////////////////////////





  /**   ░█████╗░██████╗░██╗      ██╗░░░░░██╗███╗░░██╗██╗░░██╗
		██╔══██╗██╔══██╗██║      ██║░░░░░██║████╗░██║██║░██╔╝
		███████║██████╔╝██║      ██║░░░░░██║██╔██╗██║█████═╝░
		██╔══██║██╔═══╝░██║      ██║░░░░░██║██║╚████║██╔═██╗░
		██║░░██║██║░░░░░██║      ███████╗██║██║░╚███║██║░╚██╗
		╚═╝░░╚═╝╚═╝░░░░░╚═╝      ╚══════╝╚═╝╚═╝░░╚══╝╚═╝░░╚═╝
	**/

	   String api = "https://exam.infydemo.in/api/";


	////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.auth);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {




   linear12 = findViewById(R.id.linear12);
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		viewpager1 = findViewById(R.id.viewpager1);
		linear10 = findViewById(R.id.linear10);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear7 = findViewById(R.id.linear7);
		checkbox1 = findViewById(R.id.checkbox1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);

		textview10 = findViewById(R.id.textview10);

		email = findViewById(R.id.email);
		textview4 = findViewById(R.id.textview4);
		linear9 = findViewById(R.id.linear9);
		textview9 = findViewById(R.id.textview9);
		linear8 = findViewById(R.id.linear8);
		pass = findViewById(R.id.pass);
		textview5 = findViewById(R.id.textview5);


		action_btn = findViewById(R.id.action_btn);

		edittext4_textview_91 = findViewById(R.id.edittext4_textview_91);

		textview7 = findViewById(R.id.textview7);
		imageview2 = findViewById(R.id.imageview2);
		edittext3 = findViewById(R.id.edittext3);

		edittext4 = findViewById(R.id.edittext4);

		retweet = new RequestNetwork(this);


		login_api = new RequestNetwork(this);

		register_api = new RequestNetwork(this);







		textview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				in.setClass(getApplicationContext(), ForgotPassActivity.class);
				in.putExtra("action", "");
				in.putExtra("email", "");
				in.putExtra("pass", "");
				in.putExtra("phone", "");
				startActivity(in);


			}
		});

		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(Hiden){
					Hiden = false;
					pass.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());

					edittext3.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());

					imageview2.setImageResource(R.drawable.pido_1);
					imageview2.setColorFilter(0xFFBDBDBD);

				}else {

					imageview2.setImageResource(R.drawable.pido_2);
					imageview2.setColorFilter(0xFF2196F3);



					pass.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());

					edittext3.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());

					Hiden = true;

				}
			}
		});

		action_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

			/*	/////////////////////////////////////
				in.setClass(getApplicationContext(), ProfileActivity.class);
				in.putExtra("action", "create");
				startActivity(in);
				////////////////////*/


				if (email.getText().toString().trim().equals("")) {

					textview3.setTextColor(0xFFFF1744);
					final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(linear3, "backgroundColor", new ArgbEvaluator(), 0xffEEEEEE, 0xffFF1744);
					backgroundColorAnimator.setDuration(500);
					backgroundColorAnimator.start();
				}
				else {
					if (pass.getText().toString().equals("")) {
						textview4.setTextColor(0xFFFF1744);
						final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(linear4,
								"backgroundColor",
								new ArgbEvaluator(),
								0xffEEEEEE,
								0xffFF1744);
						backgroundColorAnimator.setDuration(500);
						backgroundColorAnimator.start();
					} else {
						if (Current) {
							if (edittext3.getText().toString().equals("") ) {
								textview9.setTextColor(0xFFFF1744);
								final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(linear7,
										"backgroundColor",
										new ArgbEvaluator(),
										0xffEEEEEE,
										0xffFF1744);
								backgroundColorAnimator.setDuration(500);
								backgroundColorAnimator.start();
							}
							else {

								if(!edittext3.getText().toString().trim().equals(pass.getText().toString().trim())){

									showMessage("confirm password not match!");

							}

								if(edittext4.getText().toString().length()!=10){

									showMessage("Not a valid phone no !");


								} else {

								_autoTransitionScroll(vscroll1);
								linear8.setVisibility(View.VISIBLE);
								action_btn.setVisibility(View.GONE);
								textview7.setEnabled(false);
								checkbox1.setEnabled(false);

								pass.setEnabled(false);
								edittext3.setEnabled(false);
								timer = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {

												if (Util.isConnected(getApplicationContext())) {

													///////////////////// register  ///////////////
													/*in.setClass(getApplicationContext(), ForgotPassActivity.class);
													in.putExtra("action", "create");
													in.putExtra("email", email.getText().toString().trim());
													in.putExtra("pass", pass.getText().toString().trim());
													in.putExtra("phone", edittext4.toString().trim());
													startActivity(in);*/


													_register_api_request("123"
															,email.getText().toString().substring(0,12)
															,email.getText().toString()
															,pass.getText().toString()
															,edittext4.getText().toString()
															,"DEMO CLASS NAME"
															,"COMPUTER SCIENCE"
															,"2022"
															,"123");



													//fauth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnCompleteListener(LoginActivity.this, _fauth_sign_in_listener);
												}
												else {
													showMessage( "No internet !");

												}


												//retweet.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com", "", _retweet_request_listener);
												   timer.cancel();


											}
										});
									}
								};
								_timer.schedule(timer, 1000);
								//Util.showMessage(getApplicationContext(), "created account ");

							}

							}
						}
						else {
							//Util.showMessage(getApplicationContext(), "Login success");
							_autoTransitionScroll(vscroll1);
							linear8.setVisibility(View.VISIBLE);
							action_btn.setVisibility(View.GONE);
							textview7.setEnabled(false);
							checkbox1.setEnabled(false);

							pass.setEnabled(false);
							edittext3.setEnabled(false);
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {




											if (Util.isConnected(getApplicationContext())) {

												//_login_api_request("student@example.com","abcdabcd");
													_login_api_request(email.getText().toString().trim(),pass.getText().toString().trim());



													//fauth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnCompleteListener(LoginActivity.this, _fauth_sign_in_listener);
											}
											else {
												showMessage( "No internet !");

											}



											timer.cancel();

										///	retweet.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com", "", _retweet_request_listener);
										}
									});
								}
							};
							_timer.schedule(timer, 1000);
						}
					}
				}
			}
		});

		textview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Current) {
					Current = false;
					_autoTransitionScroll(vscroll1);
					textview1.setText("Welcome back !");
					textview2.setText("Connect to your account");
					action_btn.setText("Login now");
					textview7.setText("Create account");
					textview9.setVisibility(View.GONE);
					textview10.setVisibility(View.GONE);

					edittext4.setVisibility(View.GONE);  //phone no
					edittext4_textview_91.setVisibility(View.GONE); // phone +91 text
					edittext3.setVisibility(View.GONE);
					linear7.setVisibility(View.GONE);
					checkbox1.setVisibility(View.GONE);
					textview5.setVisibility(View.VISIBLE);
				}
				else {
					Current = true;
					_autoTransitionScroll(vscroll1);
					textview1.setText("Hello new user !");
					textview2.setText("Sign in to continue ");
					action_btn.setText("Create account");
					textview7.setText("Login now");


					textview9.setVisibility(View.VISIBLE);
					textview10.setVisibility(View.VISIBLE);
					edittext3.setVisibility(View.VISIBLE);
					edittext4.setVisibility(View.VISIBLE);  //phone no
					edittext4_textview_91.setVisibility(View.VISIBLE); // phone +91 text
					linear7.setVisibility(View.VISIBLE);
					//checkbox1.setVisibility(View.VISIBLE);
					textview5.setVisibility(View.GONE);
				}
			}
		});



		_login_api_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				showMessage(""+response);

				Log.d("login",response);

				if(response.contains("avatar")) {

					map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());




					SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
					SharedPreferences.Editor myEdit = sharedPreferences.edit();


					myEdit.putString("name", Objects.requireNonNull(map.get("name")).toString());
					myEdit.putString("userId", Objects.requireNonNull(map.get("userId")).toString());
					myEdit.putString("roleId",  Objects.requireNonNull(map.get("roleId")).toString());
					myEdit.putString("token", Objects.requireNonNull(map.get("token")).toString());
					myEdit.putString("role", Objects.requireNonNull(map.get("role")).toString());
					myEdit.putString("avatar", Objects.requireNonNull(map.get("avatar")).toString());
					myEdit.putString("API", Objects.requireNonNull(api));




					myEdit.apply();


					success();

				} else {

					failure();
				}



			}

			@Override
			public void onErrorResponse(String tag, String message) {

				failure();
				showMessage(message);


			}
		};








	}

	private void initializeLogic() {
		_slider();


		edittext4.setVisibility(View.GONE);   //phone no
		textview10.setVisibility(View.GONE); // PHONE NO TEXT

		edittext4_textview_91.setVisibility(View.GONE); // phone +91 text



		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFFFFFFF));
	}


	private void success(){



		_autoTransitionScroll(vscroll1);
		linear8.setVisibility(View.GONE);
		action_btn.setVisibility(View.VISIBLE);
		textview7.setEnabled(true);
		checkbox1.setEnabled(true);

		pass.setEnabled(true);
		edittext3.setEnabled(true);
		in.setClass(getApplicationContext(), HomeActivity.class);
		startActivity(in);
		finish();

	}


	private void failure(){

		_autoTransitionScroll(vscroll1);
		linear8.setVisibility(View.GONE);
		action_btn.setVisibility(View.VISIBLE);
		textview7.setEnabled(true);
		checkbox1.setEnabled(true);

		pass.setEnabled(true);
		edittext3.setEnabled(true);


	}

	public void _login_api_request(String email,String password)
	{
		linear7.setVisibility(View.VISIBLE);

		api_map = new HashMap<>();

		api_map.put("email", email);
		api_map.put("password", password);

		login_api.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		login_api.startRequestNetwork(RequestNetworkController.POST, api+"login?", "no tag", _login_api_listener);

		api_map.clear();

		//Toast.makeText(this, "Login complete", Toast.LENGTH_SHORT).show();
	}



	public void _register_api_request(String _role,String _name,
									  String _email, String _pass,
									  String _mob, String _class_name,
									  String _department, String _year,
									  String _roll)
	{
		linear7.setVisibility(View.VISIBLE);
		api_map.clear();
		api_map = new HashMap<>();

		api_map.put("role", _role.trim());
		api_map.put("name", _name.trim());
		api_map.put("email", _email.trim());
		api_map.put("password", _pass.trim());
		api_map.put("mobile", _mob.trim());
		api_map.put("classname", _class_name.trim());
		api_map.put("department", _department.trim());
		api_map.put("year", _year.trim());
		api_map.put("roll", _roll.trim());




		register_api.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		register_api.startRequestNetwork(RequestNetworkController.POST, api+"register?", "no tag", _login_api_listener);


		//Toast.makeText(this, "Login complete", Toast.LENGTH_SHORT).show();
	}


	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

		super.onActivityResult(_requestCode, _resultCode, _data);

		switch (_requestCode) {

			default:
				break;
		}
	}

	@Override
	public void onBackPressed() {

		if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
		{
			super.onBackPressed();
			return;
		}
		else {

			Toast.makeText(getBaseContext(), "Tap again to exit", Toast.LENGTH_SHORT).show();
		}

		mBackPressed = System.currentTimeMillis();

	}

	@Override
	public void onStart() {
		super.onStart();
		if (onStart) {

		}
		else {
			onStart = true;
			_Design_UI_Logic();
			_Typeface_Logic();
			_Edittexts_Logic();
		}
	}
	public void _NavStatusBarColor (final String _color1, final String _color2) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
		}
	}


	public void _DARK_ICONS () {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}


	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
				Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
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


	public void _autoTransitionScroll (final View _scroll) {
		android.transition.TransitionManager.beginDelayedTransition((ScrollView)_scroll, new android.transition.AutoTransition());
	}


	public void _transitionComplete (final View _view, final String _transitionName) {
		_view.setTransitionName(_transitionName);
	}


	public void _TextFieldStatus (final boolean _status, final TextView _edittext) {
		_edittext.setOnFocusChangeListener(new OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
			}
			else {
			} } });
	}


	public void _EditTexts (final TextView _et, final TextView _title, final View _line) {
		_et.setOnFocusChangeListener(new OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				_title.setTextColor(0xFF00B0FF);
				_et.setTextColor(0xFF212121);
				final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(_line,
						"backgroundColor",
						new ArgbEvaluator(),
						0xffEEEEEE,
						0xff00B0FF);
				backgroundColorAnimator.setDuration(500);
				backgroundColorAnimator.start();
			}
			else {
				_title.setTextColor(0xFF9E9E9E);
				_et.setTextColor(0xFF9E9E9E);
				final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(_line,
						"backgroundColor",
						new ArgbEvaluator(),
						0xff00B0FF,
						0xffEEEEEE);
				backgroundColorAnimator.setDuration(500);
				backgroundColorAnimator.start();
			} } });
	}


	public void _dotsProgress (final View _view, final String _color1, final String _color2, final double _count, final double _size, final double _scale, final double _growth, final double _spacing) {
		final DilatingDotsProgressBar bar = new DilatingDotsProgressBar(this);
		bar.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

		/*dots size here*/

		bar.setDotRadius((float)_size);
		/* dots color here*/

		bar.setDotColors(Color.parseColor("#" + _color1.replace("#", "")),Color.parseColor("#" + _color2.replace("#", "")));
		/*total dots here*/

		bar.setNumberOfDots((int)_count);
		bar.setDotScaleMultiplier((float)_scale);
		/*Set animation speed here*/

		bar.setGrowthSpeed((int)_growth);
		/* dot spacing between dots here*/

		bar.setDotSpacing((float)_spacing);
		((LinearLayout) _view).addView(bar);

		bar.showNow();
		/*bar.hideNow();*/

	}
	public static class DilatingDotDrawable extends android.graphics.drawable.Drawable {
		private static final String TAG = DilatingDotDrawable.class.getSimpleName();
		private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private float radius;
		private float maxRadius;
		final Rect mDirtyBounds = new Rect(0, 0, 0, 0);

		public DilatingDotDrawable(final int color, final float radius, final float maxRadius) {
			mPaint.setColor(color);
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeJoin(Paint.Join.ROUND);

			this.radius = radius;
			setMaxRadius(maxRadius);
		}

		@Override
		public void draw(Canvas canvas) {
			final Rect bounds = getBounds();
			canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius - 1, mPaint);
		}

		@Override
		public void setAlpha(int alpha) {
			if (alpha != mPaint.getAlpha()) {
				mPaint.setAlpha(alpha);
				invalidateSelf();
			}
		}

		@Override
		public void setColorFilter(ColorFilter colorFilter) {
			mPaint.setColorFilter(colorFilter);
			invalidateSelf();
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		public void setColor(int color) {
			mPaint.setColor(color);
			invalidateSelf();
		}

		public void setRadius(float radius) {
			this.radius = radius;
			invalidateSelf();
		}

		public float getRadius() {
			return radius;
		}

		@Override
		public int getIntrinsicWidth() {
			return (int) (maxRadius * 2) + 2;
		}

		@Override
		public int getIntrinsicHeight() {
			return (int) (maxRadius * 2) + 2;
		}

		public void setMaxRadius(final float maxRadius) {
			this.maxRadius = maxRadius;
			mDirtyBounds.bottom = (int) (maxRadius * 2) + 2;
			mDirtyBounds.right = (int) (maxRadius * 2) + 2;
		}

		@Override
		public Rect getDirtyBounds() {
			return mDirtyBounds;
		}

		@Override
		protected void onBoundsChange(final Rect bounds) {
			super.onBoundsChange(bounds);
			mDirtyBounds.offsetTo(bounds.left, bounds.top);
		}
	}


	public static class DilatingDotsProgressBar extends View {
		public static final String TAG = DilatingDotsProgressBar.class.getSimpleName();
		public static final double START_DELAY_FACTOR = 0.35;
		private static final float DEFAULT_GROWTH_MULTIPLIER = 1.75f;
		private static final int MIN_SHOW_TIME = 500; // ms
		private static final int MIN_DELAY = 500; // ms
		private int mDotColor;
		private int mDotEndColor;
		private int mAnimationDuration;
		private int mWidthBetweenDotCenters;
		private int mNumberDots;
		private float mDotRadius;
		private float mDotScaleMultiplier;
		private float mDotMaxRadius;
		private float mHorizontalSpacing;
		private long mStartTime = -1;
		private boolean mShouldAnimate;
		private boolean mDismissed = false;
		private boolean mIsRunning = false;
		private boolean mAnimateColors = false;
		private final ArrayList<DilatingDotDrawable> mDrawables = new ArrayList<>();
		private final List<android.animation.Animator> mAnimations = new ArrayList<>();
		/** delayed runnable to stop the progress */
		private final Runnable mDelayedHide = new Runnable() {
			@Override
			public void run() {
				mStartTime = -1;
				mIsRunning = false;
				setVisibility(View.GONE);
				stopAnimations();
			}
		};

		/** delayed runnable to start the progress */
		private final Runnable mDelayedShow = new Runnable() {
			@Override
			public void run() {
				if (!mDismissed) {
					mStartTime = System.currentTimeMillis();
					setVisibility(View.VISIBLE);
					startAnimations();
				}
			}
		};

		public DilatingDotsProgressBar(Context context) {
			this(context, null);
		}

		public DilatingDotsProgressBar(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public DilatingDotsProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init(attrs);
		}

		private void init(AttributeSet attrs) {
			//TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DilatingDotsProgressBar);
			//mNumberDots = a.getInt(R.styleable.DilatingDotsProgressBar_dd_numDots, 3);
			//mDotRadius = a.getDimension(R.styleable.DilatingDotsProgressBar_android_radius, 8);
			//mDotColor = a.getColor(R.styleable.DilatingDotsProgressBar_android_color, 0xff9c27b0);
			//mDotEndColor = a.getColor(R.styleable.DilatingDotsProgressBar_dd_endColor, mDotColor);
			//mDotScaleMultiplier = a.getFloat(R.styleable.DilatingDotsProgressBar_dd_scaleMultiplier, DEFAULT_GROWTH_MULTIPLIER);
			//mAnimationDuration = a.getInt(R.styleable.DilatingDotsProgressBar_dd_animationDuration, 300);
			//mHorizontalSpacing = a.getDimension(R.styleable.DilatingDotsProgressBar_dd_horizontalSpacing, 12);
			//a.recycle();


			mNumberDots = 3;
			mDotRadius = 8;
			mDotColor = Color.RED;
			mDotEndColor = mDotColor;
			mDotScaleMultiplier = DEFAULT_GROWTH_MULTIPLIER;
			mAnimationDuration = 300;
			mHorizontalSpacing = 12;



			mShouldAnimate = false;
			mAnimateColors = mDotColor != mDotEndColor;
			calculateMaxRadius();
			calculateWidthBetweenDotCenters();

			initDots();
			updateDots();
		}

		@Override
		protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			if (computeMaxHeight() != h || w != computeMaxWidth()) {
				updateDots();
			}
		}

		@Override
		public void onDetachedFromWindow() {
			super.onDetachedFromWindow();
			removeCallbacks();
		}

		private void removeCallbacks() {
			removeCallbacks(mDelayedHide);
			removeCallbacks(mDelayedShow);
		}

		public void reset() {
			hideNow();
		}

		/**
		 * Hide the progress view if it is visible. The progress view will not be
		 * hidden until it has been shown for at least a minimum show time. If the
		 * progress view was not yet visible, cancels showing the progress view.
		 */
		@SuppressWarnings ("unused")
		public void hide() {
			hide(MIN_SHOW_TIME);
		}

		public void hide(int delay) {
			mDismissed = true;
			removeCallbacks(mDelayedShow);
			long diff = System.currentTimeMillis() - mStartTime;
			if ((diff >= delay) || (mStartTime == -1)) {
				mDelayedHide.run();
			} else {
				if ((delay - diff) <= 0) {
					mDelayedHide.run();
				} else {
					postDelayed(mDelayedHide, delay - diff);
				}
			}
		}

		/**
		 * Show the progress view after waiting for a minimum delay. If
		 * during that time, hide() is called, the view is never made visible.
		 */
		@SuppressWarnings ("unused")
		public void show() {
			show(MIN_DELAY);
		}

		@SuppressWarnings ("unused")
		public void showNow() {
			show(0);
		}

		@SuppressWarnings ("unused")
		public void hideNow() {
			hide(0);
		}

		public void show(int delay) {
			if (mIsRunning) {
				return;
			}

			mIsRunning = true;

			mStartTime = -1;
			mDismissed = false;
			removeCallbacks(mDelayedHide);

			if (delay == 0) {
				mDelayedShow.run();
			} else {
				postDelayed(mDelayedShow, delay);
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			if (shouldAnimate()) {
				for (DilatingDotDrawable dot : mDrawables) {
					dot.draw(canvas);
				}
			}
		}

		@Override
		protected boolean verifyDrawable(final android.graphics.drawable.Drawable who) {
			if (shouldAnimate()) {
				return mDrawables.contains(who);
			}
			return super.verifyDrawable(who);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension((int) computeMaxWidth(), (int) computeMaxHeight());
		}

		private float computeMaxHeight() {
			return mDotMaxRadius * 2;
		}

		private float computeMaxWidth() {
			return computeWidth() + ((mDotMaxRadius - mDotRadius) * 2);
		}

		private float computeWidth() {
			return (((mDotRadius * 2) + mHorizontalSpacing) * mDrawables.size()) - mHorizontalSpacing;
		}

		private void calculateMaxRadius() {
			mDotMaxRadius = mDotRadius * mDotScaleMultiplier;
		}

		private void calculateWidthBetweenDotCenters() {
			mWidthBetweenDotCenters = (int) (mDotRadius * 2) + (int) mHorizontalSpacing;
		}

		private void initDots() {
			mDrawables.clear();
			mAnimations.clear();
			for (int i = 1; i <= mNumberDots; i++) {
				final DilatingDotDrawable dot = new DilatingDotDrawable(mDotColor, mDotRadius, mDotMaxRadius);
				dot.setCallback(this);
				mDrawables.add(dot);

				final long startDelay = (i - 1) * (int) (START_DELAY_FACTOR * mAnimationDuration);

				// Sizing
				android.animation.ValueAnimator growAnimator = android.animation.ObjectAnimator.ofFloat(dot, "radius", mDotRadius, mDotMaxRadius, mDotRadius);
				growAnimator.setDuration(mAnimationDuration);
				growAnimator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
				if (i == mNumberDots) {
					growAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(android.animation.Animator animation) {
							if (shouldAnimate()) {
								startAnimations();
							}
						}
					});
				}
				growAnimator.setStartDelay(startDelay);

				mAnimations.add(growAnimator);

				if (mAnimateColors) {
					// Gradient
					android.animation.ValueAnimator colorAnimator = android.animation.ValueAnimator.ofInt(mDotEndColor, mDotColor);
					colorAnimator.setDuration(mAnimationDuration);
					colorAnimator.setEvaluator(new android.animation.ArgbEvaluator());
					colorAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {

						@Override
						public void onAnimationUpdate(android.animation.ValueAnimator animator) {
							dot.setColor((int) animator.getAnimatedValue());
						}

					});
					if (i == mNumberDots) {
						colorAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(android.animation.Animator animation) {
								if (shouldAnimate()) {
									startAnimations();
								}
							}
						});
					}
					colorAnimator.setStartDelay(startDelay);

					mAnimations.add(colorAnimator);
				}
			}
		}

		private void updateDots() {
			if (mDotRadius <= 0) {
				mDotRadius = getHeight() / 2 / mDotScaleMultiplier;
			}

			int left = (int) (mDotMaxRadius - mDotRadius);
			int right = (int) (left + mDotRadius * 2) + 2;
			int top = 0;
			int bottom = (int) (mDotMaxRadius * 2) + 2;

			for (int i = 0; i < mDrawables.size(); i++) {
				final DilatingDotDrawable dot = mDrawables.get(i);
				dot.setRadius(mDotRadius);
				dot.setBounds(left, top, right, bottom);
				android.animation.ValueAnimator growAnimator = (android.animation.ValueAnimator) mAnimations.get(i);
				growAnimator.setFloatValues(mDotRadius, mDotRadius * mDotScaleMultiplier, mDotRadius);

				left += mWidthBetweenDotCenters;
				right += mWidthBetweenDotCenters;
			}
		}

		protected void startAnimations() {
			mShouldAnimate = true;
			for (android.animation.Animator anim : mAnimations) {
				anim.start();
			}
		}

		protected void stopAnimations() {
			mShouldAnimate = false;
			removeCallbacks();
			for (android.animation.Animator anim : mAnimations) {
				anim.cancel();
			}
		}

		protected boolean shouldAnimate() {
			return mShouldAnimate;
		}

		// -------------------------------
		// ------ Getters & Setters ------
		// -------------------------------

		public void setDotRadius(float radius) {
			reset();
			mDotRadius = radius;
			calculateMaxRadius();
			calculateWidthBetweenDotCenters();
			setupDots();
		}

		public void setDotSpacing(float horizontalSpacing) {
			reset();
			mHorizontalSpacing = horizontalSpacing;
			calculateWidthBetweenDotCenters();
			setupDots();
		}

		public void setGrowthSpeed(int growthSpeed) {
			reset();
			mAnimationDuration = growthSpeed;
			setupDots();
		}

		public void setNumberOfDots(int numDots) {
			reset();
			mNumberDots = numDots;
			setupDots();
		}

		public void setDotScaleMultiplier(float multiplier) {
			reset();
			mDotScaleMultiplier = multiplier;
			calculateMaxRadius();
			setupDots();
		}

		public void setDotColor(int color) {
			if (color != mDotColor) {
				if (mAnimateColors) {
					// Cancel previous animations
					reset();
					mDotColor = color;
					mDotEndColor = color;
					mAnimateColors = false;

					setupDots();

				} else {
					mDotColor = color;
					for (DilatingDotDrawable dot : mDrawables) {
						dot.setColor(mDotColor);
					}
				}
			}
		}

		/**
		 * Set different start and end colors for dots. This will result in gradient behaviour. In case you want set 1 solid
		 * color - use {@link #setDotColor(int)} instead
		 *
		 * @param startColor starting color of the dot
		 * @param endColor   ending color of the dot
		 */
		public void setDotColors(int startColor, int endColor) {
			if (mDotColor != startColor || mDotEndColor != endColor) {
				if (mAnimateColors) {
					reset();
				}
				mDotColor = startColor;
				mDotEndColor = endColor;

				mAnimateColors = mDotColor != mDotEndColor;

				setupDots();
			}
		}

		private void setupDots() {
			initDots();
			updateDots();
			showNow();
		}

		public int getDotGrowthSpeed() {
			return mAnimationDuration;
		}

		public float getDotRadius() {
			return mDotRadius;
		}

		public float getHorizontalSpacing() {
			return mHorizontalSpacing;
		}

		public int getNumberOfDots() {
			return mNumberDots;
		}

		public float getDotScaleMultiplier() {
			return mDotScaleMultiplier;
		}
	}
	{
	}


	public void _ICC (final ImageView _img, final String _c1, final String _c2) {
		_img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
	}


	public void _RippleEffects (final String _color, final View _view) {
		android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
		_view.setBackground(ripdr);
	}


	public void _removeScollBar (final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}


	public void _Design_UI_Logic () {
		_dotsProgress(linear8, "#00B0FF", "#FFFFFF", 3, 8, 1, 600, 11);
		_rippleRoundStroke(action_btn, "#00B0FF", "#33FFFFFF", 10, 2, "#00B0FF");
		_rippleRoundStroke(linear8, "#00B0FF", "#33FFFFFF", 10, 2, "#00B0FF");
		_rippleRoundStroke(textview7, "#0000B0FF", "#16000000", 10, 2, "#E0E0E0");
		_ICC(imageview2, "#9E9E9E", "#00B0FF");
		_NavStatusBarColor("#FFFFFF", "#FFFFFF");
		_RippleEffects("#F5F5F5", imageview2);
		textview9.setVisibility(View.GONE);
		edittext3.setVisibility(View.GONE);
		checkbox1.setVisibility(View.GONE);
		linear7.setVisibility(View.GONE);
		linear8.setVisibility(View.GONE);
		_removeScollBar(vscroll1);
		_DARK_ICONS();
	}


	public void _Typeface_Logic () {
		_changeActivityFont("en_light");
		action_btn.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/en_med.ttf"), Typeface.NORMAL);
	}


	public void _Edittexts_Logic () {
		_EditTexts(email, textview3, linear3);
		_EditTexts(pass, textview4, linear4);
		_EditTexts(edittext3, textview9, linear7);
		_EditTexts(edittext4, textview10, linear12);


		pass.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
		edittext3.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());

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
						if (count == 2) {
							count = 0;
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(scroll_time, 1500, 2000);
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


			Glide.with(getApplicationContext())
					.load(Uri.parse(listmap.get(_position).get("image").toString()))
					.error(R.drawable.pyramids)
					.placeholder(R.drawable.pyramids)
					.thumbnail(0.01f)
					.into(imageview1);


			_container.addView(_view);
			return _view;
		}
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
	}



}
