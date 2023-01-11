package com.mycollege.ett.payments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mycollege.ett.R;
import com.mycollege.ett.RequestNetwork;
import com.mycollege.ett.RequestNetworkController;
import com.mycollege.ett.utility.AvenuesParams;
import com.mycollege.ett.utility.Constants;
import com.mycollege.ett.utility.LoadingDialog;
import com.mycollege.ett.utility.RSAUtility;
import com.mycollege.ett.utility.ServiceUtility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    Intent mainIntent;
    String encVal;
    String vResponse;

    private HashMap<String, Object> submit_map = new HashMap<>();
    private RequestNetwork submit_api_call;
    private RequestNetwork.RequestListener _submit_api_listener;

    WebView webview ;
    AlertDialog.Builder builder;
    boolean payment=false;

    HashMap<String, Object> authorization = new HashMap<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);

        webview = findViewById(R.id.webview);
        submit_api_call = new RequestNetwork(this);
        builder = new AlertDialog.Builder(this);




        _submit_api_listener= new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {

                Toast.makeText(WebViewActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.d("payment_cc_submit",response);
                builder.setMessage("You have successfully applied the form you can exit now.")
                        .setCancelable(false)

                        .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Success");
                alert.show();

                showMessage("Successfully Form Applied.");

            }

            @Override
            public void onErrorResponse(String tag, String message) {

                builder.setMessage("Your payment done but error on submitting the Form, please check your internet and Try again!\n\nError message\n"+message)
                        .setCancelable(false)

                        .setPositiveButton("Retry now", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                request_submit_Api();

                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Failed to submit !");
                alert.show();

                showMessage("Failed to submit form\n"+message);

            }
        };


        mainIntent = getIntent();

//get rsa key method
        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
    }




    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface {
                @JavascriptInterface
                public void processHTML(String html) {
                    // process the html source code to get final status of transaction
                    String status = null;
                    if (html.contains("Failure")) {
                        status = "Transaction Declined!";
                    } else if (html.contains("Success")) {

                        status = "Transaction Successful!";

                    } else if (html.contains("Aborted")) {
                        status = "Transaction Cancelled!";
                    } else {
                        status = "Status Not Known!";
                    }


                    Log.d("payment_cc", status);
                 /*   Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    startActivity(intent);
*/



                }
            }


            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");




            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
                    if (url.contains("/ccavResponseHandler.jsp")) {
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                    //  Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();


                    //request_submit_Api();



                    webview.evaluateJavascript(
                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    Log.d("payment_cc_html", html);
                                    // code here
                                    if(html.contains("Successful"))
                                    {

                                        payment = true;
                                        request_submit_Api(); // sent response to server
                                        Log.d("payment_cc", "payment success");
                                        Toast.makeText(WebViewActivity.this, "Payment success", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                    Log.d("payment_cc", url);





                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
                }
            });


            try {
                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE),
                        "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");


                webview.postUrl(Constants.TRANS_URL, postData.getBytes());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    private void request_submit_Api(){

        _submit_api_request(getIntent().getStringExtra("dept_id"),
                getIntent().getStringExtra("class_id"),
                getIntent().getStringExtra("exam_id"),
                getIntent().getStringExtra("dept_name"),
                getIntent().getStringExtra("class_name"),
                getIntent().getStringExtra("exam"),
                getIntent().getStringExtra("semester"),
                getIntent().getStringExtra("date"),
                getIntent().getStringExtra("fees"),
                getIntent().getStringExtra("student_name"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        // if payment is done then it will not exit the app
        if(!payment)
        {
            finish();
        }

    }

    public void _submit_api_request(String _department_id,
                                    String _class_id,
                                    String _exam_id, String _department,
                                    String _class, String _exam, String _semistar,
                                    String _date,
                                    String _form_fee,
                                    String _student_name) {

        submit_map.clear();
        authorization.clear();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        authorization.put("Authorization",sh.getString("token", ""));
        // Log.d("payment_cc",sh.getString("token", ""));

        submit_map = new HashMap<>();   //sh.getString("token", "")
        submit_map.put("department_id",  _department_id.trim());
        submit_map.put("class_id", _class_id.trim());
        submit_map.put("exam_id",  _exam_id.trim());
        submit_map.put("department", _department.trim());
        submit_map.put("class", _class.trim());
        submit_map.put("exam", _exam.trim());
        submit_map.put("semistar", _semistar.trim());
        submit_map.put("date", _date.trim());
        submit_map.put("form_fee", _form_fee.trim());
        submit_map.put("student_name", _student_name.trim());

        submit_api_call.setHeaders(authorization);
        submit_api_call.setParams(submit_map, RequestNetworkController.REQUEST_PARAM);
        submit_api_call.startRequestNetwork(RequestNetworkController.POST,
                "https://student.mlu.ac.in/api/user/exam/add",
                "no tag", _submit_api_listener);


        //textview1.setText(_role +"\n"+_class_name+"\n"+_department+"\n"+_year+"\n"+_blg+"\n");
        //Toast.makeText(this, "Login complete", Toast.LENGTH_SHORT).show();


    }












    public void get_RSA_key(final String ac, final String od) {
        LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                        LoadingDialog.cancelLoading();

                        if (response != null && !response.equals("")) {
                            vResponse = response;     ///save retrived rsa key
                            if (vResponse.contains("!ERROR!")) {
                                show_alert(vResponse);
                            } else {
                                new RenderView().execute();   // Calling async task to get display content
                            }


                        }
                        else
                        {
                            show_alert("No response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoadingDialog.cancelLoading();
                        //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AvenuesParams.ACCESS_CODE, ac);
                params.put(AvenuesParams.ORDER_ID, od);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void show_alert(String msg) {


        AlertDialog alertDialog = new AlertDialog.Builder(
                WebViewActivity.this).create();

        alertDialog.setTitle("Error!!!");
        if (msg.contains("\n"))
            msg = msg.replaceAll("\\\n", "");

        alertDialog.setMessage(msg);



        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


        alertDialog.show();
    }


    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }


}