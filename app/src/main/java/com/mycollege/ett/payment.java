package com.mycollege.ett;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mycollege.ett.payments.WebViewActivity;
import com.mycollege.ett.utility.AvenuesParams;
import com.mycollege.ett.utility.ServiceUtility;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class payment extends  AppCompatActivity  implements PaymentResultListener {
	
	private final Timer _timer = new Timer();
	
	private String fontName = "";
	private final String typeace = "";
	
	private LinearLayout linear8;
	private ImageView imageview1;
	
	private TimerTask timer;
	private final Intent toAuth = new Intent();
	private RequestNetwork in;
	private RequestNetwork.RequestListener _in_request_listener;

	ImageView imageview_aff;
	Button pay, close ;
	//EditText rsa_link;

	private AlertDialog.Builder alertDialogBuilder;
	String order_id_ccavenue;

	EditText stu_name, dept, class_name, exam, semester, time, term;
	TextView date, fees;



	private HashMap<String, Object> submit_map = new HashMap<>();
	private RequestNetwork submit_api_call;
	private RequestNetwork.RequestListener _submit_api_listener;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.payment);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);

		submit_api_call = new RequestNetwork(this);

		_submit_api_listener= new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

				showMessage("Successfully Form Applied.");

			}

			@Override
			public void onErrorResponse(String tag, String message) {

				showMessage("Failed to submit form\n"+message);

			}
		};

		//razorpay
		Checkout.preload(getApplicationContext());


		initializeLogic();
	}





	@Override
	protected void onStart() {
		super.onStart();

		int randomNum = ServiceUtility.randInt(10000000, 99999999);
		order_id_ccavenue = Integer.toString(randomNum);


	}


	private void initialize(Bundle _savedInstanceState) {

		//rsa_link = findViewById(R.id.rsa_link);
		linear8 = findViewById(R.id.linear8);
		imageview1 = findViewById(R.id.imageview1);
		in = new RequestNetwork(this);

		pay =findViewById(R.id.pay);
		close = findViewById(R.id.close);

		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});


		date = findViewById(R.id.date);
		term = findViewById(R.id.term);
		fees = findViewById(R.id.fees);
		semester = findViewById(R.id.semister_name);
		exam = findViewById(R.id.exam_name);
		class_name = findViewById(R.id.class_name);
		dept = findViewById(R.id.dept_name);
		stu_name = findViewById(R.id.stu_name);

		date.setText("Date : "+getIntent().getStringExtra("date"));
		term.setText(getIntent().getStringExtra("term"));
		fees.setText("Fees : ₹"+getIntent().getStringExtra("fees"));
		semester.setText(getIntent().getStringExtra("semester"));
		exam.setText(getIntent().getStringExtra("exam"));
		class_name.setText(getIntent().getStringExtra("class_name"));
		dept.setText(getIntent().getStringExtra("dept_name"));
		stu_name.setText(getIntent().getStringExtra("student_name"));





		imageview_aff = findViewById(R.id.imageview_aff);


		pay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// showMessage("payment clicked");
				//startPayment(1,"Payment fees");
				start_payment_ccAvenue(Integer.parseInt(getIntent().getStringExtra("fees").replaceAll("[.]00","")));

			}
		});

		_in_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}



	public void close(View view){

		finish();
	}


	public  void start_payment_ccAvenue(int amount)
	{

		    // if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals(""))
		    // 4YRUXLSRO20O8NIH access code  AVKE93KA88AK06EKKA  ||| AVRT94KA27AL71TRLA (new)
		    // 2 merchent id 1912626
            //  1912626

			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtra(AvenuesParams.ACCESS_CODE, "AVRT94KA27AL71TRLA");
			intent.putExtra(AvenuesParams.MERCHANT_ID, "1912626");
			intent.putExtra(AvenuesParams.ORDER_ID, order_id_ccavenue);
			intent.putExtra(AvenuesParams.CURRENCY, "INR");
			intent.putExtra(AvenuesParams.AMOUNT, Integer.toString(amount));
			intent.putExtra(AvenuesParams.REDIRECT_URL, "");
			intent.putExtra(AvenuesParams.CANCEL_URL, "");

			intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://mlu.ac.in/transction/GetRSA.php");
			// RSA VALUE
		// Ordinal : https://mlu.ac.in/transction/GetRSA.php
		    // test rsa link : https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp

			startActivity(intent);




	}

	public void startPayment(int amount,String desc) {

	   /*

	   buy_coin  = get_coins;

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
		upiPayIntent.setData(Uri.parse("upi://pay?mode=02&pa=Q02250494@ybl&purpose=00&am="
				+amount+"&mc=0000&pn=LionBook&orgid=180001&sign=MEUCIDnhMrYjFpn59C7cjKT1FfIvvMxcJ4hz/4cTmBHHkIT7AiEA7yEiHItl5PEFMNiTjne1qSG4YGvOB6C9K/JxrU8SiW0="));

		startActivityForResult(upiPayIntent, UPI_PAYMENT);

		*/








		/**
		 * Instantiate Checkout
		 //		 */
		Checkout checkout = new Checkout();
		checkout.setKeyID("rzp_live_MMa1JEF6rr0QwJ");

		//Key id - rzp_live_MMa1JEF6rr0QwJ


		// Key secret - R9TU8KI1Z07LCoxEF3717ErY

		/**
		 * Set your logo here
		 */
		checkout.setImage(R.drawable.logo);

		/**
		 * Reference to current activity
		 */
		final Activity activity = this;

		/**
		 * Pass your payment options to the Razorpay Checkout as a JSONObject
		 */
		try {
			JSONObject options = new JSONObject();

			options.put("name", "Madhusudan Law University, Cuttack");
			options.put("description", desc+", 100% Secure");

			//options.put("image", "https://previews.123rf.com/images/arcady31/arcady311503/arcady31150300038/37636305-100-secure-icon.jpg");
		    //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.

			/*if(amount == 49){
				options.put("theme.color", "#3AC222");

			}else if(amount == 199) {

				options.put("theme.color", "#FF0A0A");
			} else {
				options.put("theme.color", "#367CF1");

			}*/

			options.put("theme.color", "#FF0A0A");
			options.put("currency", "INR");
			options.put("amount", amount*100);   //pass amount in currency subunits



		options.put("prefill.email", "");
		options.put("prefill.contact","");

			JSONObject retryObj = new JSONObject();
			retryObj.put("enabled", true);
			retryObj.put("max_count", 4);
			options.put("retry", retryObj);

			checkout.open(activity, options);

		} catch(Exception e) {
			showMessage( "Error in starting Razorpay Checkout\n\n"+e);
		}
	}



	private void initializeLogic() {

		//_transparentStatusAndNavigation();
		//_NavStatusBarColor("#FFFFFF", "#FFFFFF");
		_changeActivityFont("en_med");
		_DARK_ICONS();




		// ...





		Glide.with(getApplicationContext())
				.load(Uri.parse("https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=40"))
				.error(R.drawable.person)
				.placeholder(R.drawable.person)
				.thumbnail(0.01f)
				.into(imageview_aff);

	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);



	}



	
	public void _DARK_ICONS () {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
	
	
	public void _ActivityTranlation (final View _view, final String _transitionName, final Intent _intent) {
		_view.setTransitionName(_transitionName);
		
		android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(payment.this, _view, _transitionName);
		        startActivity(_intent, optionsCompat.toBundle());
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
	}


	@Override
	public void onPaymentSuccess(String s) {
        // RAZORPAY PAYMENT GATEWAY
		AlertDialog.Builder al = new AlertDialog.Builder(this);
		al.setTitle("Payment response");
		al.setMessage(s);
		al.setPositiveButton("OK",null);
		al.show();


	}

	@Override
	public void onPaymentError(int i, String s) {
		AlertDialog.Builder al = new AlertDialog.Builder(this);
		al.setTitle("Payment response");
		al.setPositiveButton("OK",null);
		al.setMessage(s);
		al.show();
	}
}
