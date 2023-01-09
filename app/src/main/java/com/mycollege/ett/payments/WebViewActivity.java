package com.mycollege.ett.payments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);

        webview = (WebView) findViewById(R.id.webview);
        submit_api_call = new RequestNetwork(this);
        builder = new AlertDialog.Builder(this);
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
                    Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();

                    webview.evaluateJavascript(
                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    Log.d("payment_cc_html", html);
                                    // code here
                                    if(html.contains("Successful"))
                                    {

                                        builder.setMessage("Payment Success")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }
                                                });
                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        //Setting the title manually
                                        alert.setTitle("Response");
                                        alert.show();

                                        Log.d("payment_cc", "payment success");
                                        Toast.makeText(WebViewActivity.this, "Payment success", Toast.LENGTH_SHORT).show();

                                    }else {

                                        Log.d("payment_cc", "payment failure");
                                        //Toast.makeText(WebViewActivity.this, "Payment failure", Toast.LENGTH_SHORT).show();


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webview.canGoBack()){

            webview.goBack();
        }else {

            finish();
        }
    }

    public void _submit_api_request(String _nationality,
                                    String _name,
                                    String _email, String _state,
                                    String _mob, String _city, String _program_name,
                                    String _qualification,
                                    String _gender,
                                    String _caste) {

        submit_map.clear();
        submit_map = new HashMap<>();

        submit_map.put("name",  _name.trim());
        submit_map.put("department", _email.trim());
        submit_map.put("class_id",  _mob.trim());
        submit_map.put("semistar", _nationality.trim());
        submit_map.put("term", _state);
        submit_map.put("type", _city.trim());
        submit_map.put("exam_date", _program_name);
        submit_map.put("qualification", _qualification.trim());
        submit_map.put("gender", _gender.trim());
        submit_map.put("caste", _caste.trim());

        submit_api_call.setParams(submit_map, RequestNetworkController.REQUEST_PARAM);
        submit_api_call.startRequestNetwork(RequestNetworkController.POST, "https://student.mlu.ac.in/api/exam/add", "no tag", _submit_api_listener);


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