package com.coolweather.app.activity;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.coolweather.app.R;
import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;
import com.coolweather.app.activity.SlidingLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener {
	/**
	 * 本地天气按钮
	 */
	private LinearLayout locationWeather;
	
	/**
	 * 搜索按钮
	 */
	private LinearLayout search;

	/**
	 * 分享按钮
	 */
//	private Button share;
	private  LinearLayout share;

	private LinearLayout contentLayout;
	/**
	 * menu按钮，点击按钮展示左侧布局，再点击一次隐藏左侧布局。
	 */
	private Button menuButton;
	/**
	 * 侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
	 */
	private SlidingLayout slidingLayout;

	private LocationManager locationManager;

	private String provider;

	private LinearLayout weatherInfoLayout;
	/**
	 * 用于显示城市名
	 */
	private TextView cityNameText;
	/**
	 * 用于显示发布时间
	 */
	private TextView publishText;
	/**
	 * 用于显示第一天的天气情况
	 */
	private TextView weather1Text;
	/**
	 * 用于显示第二天的天气情况
	 */
	private TextView weather2Text;
	/**
	 * 用于显示第三天的天气情况
	 */
	private TextView weather3Text;
	/**
	 * 用于显示第四天的天气情况
	 */
	private TextView weather4Text;
	/**
	 * 用于显示气温1
	 */
	private TextView temp1Text;
	/**
	 * 用于显示气温2
	 */
	private TextView temp2Text;
	/**
	 * 用于显示气温3
	 */
	private TextView temp3Text;
	/**
	 * 用于显示气温4
	 */
	private TextView temp4Text;
	/**
	 * 用于显示当前日期
	 */
	private TextView currentDateText;
	/**
	 * 切换城市按钮
	 */
	private LinearLayout switchCity;
	/**
	 * 更新天气按钮
	 */
	private Button refreshWeather;
	/**
	 * 更新天气画面
	 */
	private ImageView weatherImg1;
	/**
	 * 更新天气画面
	 */
	private ImageView weatherImg2;
	/**
	 * 更新天气画面
	 */
	private ImageView weatherImg3;
	/**
	 * 更新天气画面
	 */
	private ImageView weatherImg4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		// 初始化各控件
		locationWeather=(LinearLayout) findViewById(R.id.locationWeather);
		search = (LinearLayout) findViewById(R.id.searchButton);
//		share = (Button) findViewById(R.id.fxButton);
		share = (LinearLayout)findViewById(R.id.fxButton);

		contentLayout = (LinearLayout) findViewById(R.id.content_layout);
		slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		menuButton = (Button) findViewById(R.id.menuButton);

		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weather1Text = (TextView) findViewById(R.id.weather1);
		weather2Text = (TextView) findViewById(R.id.weather2);
		weather3Text = (TextView) findViewById(R.id.weather3);
		weather4Text = (TextView) findViewById(R.id.weather4);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		temp3Text = (TextView) findViewById(R.id.temp3);
		temp4Text = (TextView) findViewById(R.id.temp4);
		currentDateText = (TextView) findViewById(R.id.current_date);
		switchCity = (LinearLayout) findViewById(R.id.switch_city);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);
		weatherImg1 = (ImageView) findViewById(R.id.weather_img1);
		weatherImg2 = (ImageView) findViewById(R.id.weather_img2);
		weatherImg3 = (ImageView) findViewById(R.id.weather_img3);
		weatherImg4 = (ImageView) findViewById(R.id.weather_img4);

		String countyCode = getIntent().getStringExtra("county_code");
		String searchCity = getIntent().getStringExtra("searchCity");

		slidingLayout.setScrollEvent(contentLayout);
		
		
		
		
		if (!TextUtils.isEmpty(countyCode)) {
			// 有县级代号时就去查询天气
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}

		else if (!TextUtils.isEmpty(searchCity)) {
			publishText.setText("同步中...");
			Toast.makeText(this, searchCity, Toast.LENGTH_SHORT).show();
			searchWeather(searchCity);

		} else {
			publishText.setText("同步中...");
			queryLocationWeather();

		}
		locationWeather.setOnClickListener(this);
		search.setOnClickListener(this);
		share.setOnClickListener(this);
		menuButton.setOnClickListener(this);
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

	}
	
	
	private Handler mMainHandler = new Handler() {  
	    @Override  
	    public void handleMessage(Message msg) {  
	    	showWeather();
	    }  
	    };  
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent0 = new Intent(this, ChooseAreaActivity.class);
			intent0.putExtra("from_weather_activity", true);
			startActivity(intent0);
			finish();
			break;

		case R.id.searchButton:
			Intent intent1 = new Intent(this, SearchActivity.class);
			startActivity(intent1);
			finish();
			break;

		case R.id.refresh_weather:
			publishText.setText("同步中...");
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("weather_code", "");
			if (!TextUtils.isEmpty(weatherCode)) {
				queryWeatherInfo(weatherCode);
			} else {
				saveBaiduWeather();
				showWeather();
			}
			;
            break;
		case R.id.fxButton:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			// 这里是你发送的文本
			sendIntent.putExtra(Intent.EXTRA_TEXT, "我发现一个新的app，你也来试一下");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			break;

		case R.id.menuButton:
			if (slidingLayout.isLeftLayoutVisible()) {
				slidingLayout.scrollToRightLayout();
			} else {
				slidingLayout.scrollToLeftLayout();
			}
			break;
		case R.id.locationWeather:
			queryLocationWeather();

		default:
			break;
		}
	}

	/**
	 * 查询县级代号所对应的天气代号。
	 */
	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city"
				+ countyCode + ".xml";
		queryFromServer(address, "countyCode");
	}

	/**
	 * 查询天气代号所对应的天气。
	 */
	private void queryWeatherInfo(String weatherCode) {
		// String address = "http://www.weather.com.cn/data/cityinfo/" +
		// weatherCode + ".html";
		String address = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode="
				+ weatherCode + "&weatherType=0";
		queryFromServer(address, "weatherCode");
	}

	/**
	 * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
	 */
	private void queryFromServer(final String address, final String type) {
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				if ("countyCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						// 从服务器返回的数据中解析出天气代号
						String[] array = response.split("\\|");
						if (array != null && array.length == 2) {
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				} else if ("weatherCode".equals(type)) {
					// 处理服务器返回的天气信息
					Utility.handleWeatherResponse(WeatherActivity.this,
							response);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showWeather();
						}
					});
				}
			}

			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						publishText.setText("同步失败");
					}
				});
			}
		});
	}

	/**
	 * 查询本地天气
	 */
	private void queryLocationWeather() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			// 当没有可用的位置提供器时，弹出Toast提示用户
			Toast.makeText(this, "无法获取当前位置，请手动选择天气", Toast.LENGTH_SHORT).show();
			return;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			// 显示当前设备的位置信息
			String url = "http://api.map.baidu.com/telematics/v3/weather?location="
					+ location.getLongitude()
					+ ","
					+ location.getLatitude()
					+ "&output=json&ak=GyFqB2QVNK4yv95fh2b1Awdt";
			SharedPreferences.Editor editor = getSharedPreferences("url",
					MODE_PRIVATE).edit();
			editor.putString("url", url);
			editor.commit();
			saveBaiduWeather();
		}
		mMainHandler.sendEmptyMessageDelayed(0, 700);
//		showWeather();

	}

	/**
	 * search天气
	 */
	private void searchWeather(String searchCity) {
		String url = "http://api.map.baidu.com/telematics/v3/weather?location="
				+ searchCity + "&output=json&ak=GyFqB2QVNK4yv95fh2b1Awdt";
		SharedPreferences.Editor editor = getSharedPreferences("url",
				MODE_PRIVATE).edit();
		editor.putString("url", url);
		editor.commit();
		saveBaiduWeather();
//		showWeather();
		mMainHandler.sendEmptyMessageDelayed(0, 700);
	}

	/**
	 * 获取和保存百度接口天气
	 */

	private void saveBaiduWeather() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SharedPreferences pref = getSharedPreferences("url",
							MODE_PRIVATE);
					String url = pref.getString("url", "");
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// 请求和响应都成功了
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						Utility.parseJSONWithJSONObject(WeatherActivity.this,
								response);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
	 */
	private void showWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));

		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		temp3Text.setText(prefs.getString("temp3", ""));
		temp4Text.setText(prefs.getString("temp4", ""));
		String weather1 = prefs.getString("weather1", "");
		String weather2 = prefs.getString("weather2", "");
		String weather3 = prefs.getString("weather3", "");
		String weather4 = prefs.getString("weather4", "");
		weather1Text.setText(weather1);
		weather2Text.setText(weather2);
		weather3Text.setText(weather3);
		weather4Text.setText(weather4);
		weatherImg1.setImageResource(Utility.showImg(weather1));
		weatherImg2.setImageResource(Utility.showImg(weather2));
		weatherImg3.setImageResource(Utility.showImg(weather3));
		weatherImg4.setImageResource(Utility.showImg(weather4));
		// publishText.setText("今天" + prefs.getString("publish_time", "") +
		// "发布");
		publishText.setText("今天发布");
		// currentDateText.setText(prefs.getString("current_date", ""));
		currentDateText.setText(prefs.getString("current_day", ""));
		// weatherInfoLayout.setVisibility(View.VISIBLE);
		// cityNameText.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, AutoUpdateService.class);
		startService(intent);
	}

	
	
	
}