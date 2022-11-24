package com.mycollege.ett;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.view.View;


public class ForgotPassActivity extends  AppCompatActivity  { 
	
	
	private String fontName = "";
	private String typeace = "";
	private String phone = "";
	private String code = "";
	private String codeSent = "";
	
	private LinearLayout linear1;
	private LinearLayout get_otp_layout;
	private LinearLayout verify_otp_layout;
	private LinearLayout new_password_layout;
	private TextView textview2;
	private LinearLayout linear2;
	private EditText phone_no;
	private TextView send_otp;
	private ProgressBar progressbar1;
	private TextView textview4;
	private LinearLayout linear7;
	private TextView resend_otp;
	private EditText otp_value;
	private ProgressBar progressbar2;
	private TextView verify_otp;
	private TextView textview6;
	private TextView textview9;
	private EditText pass;
	private TextView textview10;
	private EditText cpass;
	private TextView textview7;
	
	private FirebaseAuth fauth;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.forgot_pass);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		get_otp_layout = (LinearLayout) findViewById(R.id.get_otp_layout);
		verify_otp_layout = (LinearLayout) findViewById(R.id.verify_otp_layout);
		new_password_layout = (LinearLayout) findViewById(R.id.new_password_layout);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		phone_no = (EditText) findViewById(R.id.phone_no);
		send_otp = (TextView) findViewById(R.id.send_otp);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		resend_otp = (TextView) findViewById(R.id.resend_otp);
		otp_value = (EditText) findViewById(R.id.otp_value);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		verify_otp = (TextView) findViewById(R.id.verify_otp);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview9 = (TextView) findViewById(R.id.textview9);
		pass = (EditText) findViewById(R.id.pass);
		textview10 = (TextView) findViewById(R.id.textview10);
		cpass = (EditText) findViewById(R.id.cpass);
		textview7 = (TextView) findViewById(R.id.textview7);
		fauth = FirebaseAuth.getInstance();
		
		send_otp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (phone_no.getText().toString().trim().length() == 10) {
					_sendotp();
					verify_otp_layout.setVisibility(View.VISIBLE);
				}
				else {
					Util.showMessage(getApplicationContext(), "Invalid number");
				}
			}
		});
		
		resend_otp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				_sendotp();
			}
		});
		
		verify_otp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				if(!otp_value.getText().toString().trim().equals("")){
				_verify();
				}
			}
		});
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				progressbar2.setVisibility(View.GONE);
				if (_success) {
					Util.showMessage(getApplicationContext(), "Verifications Success ");
				}
				else {
					Util.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_changeActivityFont("en_light");
		if (getIntent().getStringExtra("action").equals("create")) {
			get_otp_layout.setVisibility(View.GONE);
			verify_otp_layout.setVisibility(View.VISIBLE);
			new_password_layout.setVisibility(View.GONE);
		}
		else {
			get_otp_layout.setVisibility(View.VISIBLE);
			verify_otp_layout.setVisibility(View.GONE);
			new_password_layout.setVisibility(View.GONE);
		}
		progressbar1.setVisibility(View.GONE);
		progressbar2.setVisibility(View.GONE);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
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
		};
	}
	
	
	public void _sendotp () {
		progressbar1.setVisibility(View.VISIBLE);
		phone = "+91".concat(phone_no.getText().toString().trim());
		//code for send otp on user mobile
		
		com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 50, java.util.concurrent.TimeUnit.SECONDS, this, mCallbacks);
	}
	com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
		 @Override
		public void onVerificationCompleted(com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential) {
				showMessage("OTP Sent.");
				
				
				progressbar1.setVisibility(View.GONE);
		}
		 @Override
		public void onVerificationFailed(com.google.firebase.FirebaseException e) {
				showMessage(e.toString());
			progressbar1.setVisibility(View.GONE);
		}
		 @Override
		public void onCodeSent(String s, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken forceResendingToken) {
				super.onCodeSent(s, forceResendingToken);
				codeSent = s;
		}
	};
	{
	}
	
	
	public void _verify () {
		progressbar2.setVisibility(View.VISIBLE);
		code = otp_value.getText().toString().trim();
		//code for verify otp
		
		com.google.firebase.auth.PhoneAuthCredential credential = com.google.firebase.auth.PhoneAuthProvider.getCredential(codeSent, code);
		signInWithPhoneAuthCredential(credential);
	}
	private void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
		fauth.signInWithCredential(credential) .addOnCompleteListener(this, _fauth_sign_in_listener);
	}
	{
		
		
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
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
