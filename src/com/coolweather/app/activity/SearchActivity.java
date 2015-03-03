package com.coolweather.app.activity;

import com.coolweather.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends Activity {
	private TextView searchTextView;
	private EditText searchEditText;
	private Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_layout);
		searchTextView = (TextView) findViewById(R.id.searchtext_view);
		searchEditText = (EditText) findViewById(R.id.input_text);
		searchButton = (Button) findViewById(R.id.search);
		searchTextView.setText("请输入搜索城市");

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String searchCity = searchEditText.getText().toString();
				Intent intent0 = new Intent(SearchActivity.this,
						WeatherActivity.class);
				intent0.putExtra("searchCity", searchCity);
				startActivity(intent0);
				finish();

			}
		});

	}

	public void onBackPressed() {
		Intent intent1 = new Intent(SearchActivity.this, WeatherActivity.class);
		startActivity(intent1);
		finish();
	}

}
