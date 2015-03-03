package com.coolweather.app.activity;

import com.coolweather.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class SplashActivity extends Activity {
	private Handler mMainHandler = new Handler() {  
	    @Override  
	    public void handleMessage(Message msg) {  
	          Intent intent=new Intent(SplashActivity.this,WeatherActivity.class);
	          startActivity(intent);
	    }  
	    };  
	      
	    @Override  
	    public void onCreate(Bundle icicle) {  
	    super.onCreate(icicle);  
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setBackgroundDrawableResource(R.drawable.splash);  
	    mMainHandler.sendEmptyMessageDelayed(0, 500);  
	    } 
	      
	    // much easier to handle key events  
	    @Override  
	    public void onBackPressed() {  
	    }  
}
