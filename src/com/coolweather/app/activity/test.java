package com.coolweather.app.activity;



import com.coolweather.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class test extends Activity {
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_layout);
		textView=(TextView)findViewById(R.id.text_view11);
		textView.setText("嘎嘎");

}
}