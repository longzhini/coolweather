package com.coolweather.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class Utility {

	/**
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// 将解析出来的数据存储到Province表
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// 将解析出来的数据存储到City表
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					// 将解析出来的数据存储到County表
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}

//	/**
//	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
//	 */
//	public static void handleWeatherResponse(Context context, String response) {
//		try {
//			JSONObject jsonObject = new JSONObject(response);
//			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//			String cityName = weatherInfo.getString("city");
//			String weatherCode = weatherInfo.getString("cityid");
//			String temp1 = weatherInfo.getString("temp1");
//			String temp2 = weatherInfo.getString("temp2");
//			String weatherDesp = weatherInfo.getString("weather");
//			String publishTime = weatherInfo.getString("ptime");
//			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
//					weatherDesp, publishTime);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
	 * 改动1
	 */
	public static void handleWeatherResponse(Context context, String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String temp3 = weatherInfo.getString("temp3");
			String temp4 = weatherInfo.getString("temp4");
			String temp5 = weatherInfo.getString("temp5");
			String temp6 = weatherInfo.getString("temp6");
			String weather1 = weatherInfo.getString("weather1");
			String weather2 = weatherInfo.getString("weather2");
			String weather3 = weatherInfo.getString("weather3");
			String weather4 = weatherInfo.getString("weather4");
			String weather5 = weatherInfo.getString("weather5");
			String weather6 = weatherInfo.getString("weather6");
			String current_day=weatherInfo.getString("date_y");
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,temp3,temp4,temp5,
					temp6,weather1,weather2,weather3,weather4,weather5,weather6,current_day);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	}	
	

//	/**
//	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
//	 */
//	public static void saveWeatherInfo(Context context, String cityName,
//			String weatherCode, String temp1, String temp2, String weatherDesp,
//			String publishTime) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
//		SharedPreferences.Editor editor = PreferenceManager
//				.getDefaultSharedPreferences(context).edit();
//		editor.putBoolean("city_selected", true);
//		editor.putString("city_name", cityName);
//		editor.putString("weather_code", weatherCode);
//		editor.putString("temp1", temp1);
//		editor.putString("temp2", temp2);
//		editor.putString("weather_desp", weatherDesp);
//		editor.putString("publish_time", publishTime);
//		editor.putString("current_date", sdf.format(new Date()));
//		editor.commit();
//	}
//
//}
	
	public static void saveWeatherInfo(Context context, String cityName, String weatherCode,  
			String temp1, String temp2, String temp3, String temp4, String temp5, 
			String temp6, String weather1,  String weather2, String weather3, String weather4 ,
			String weather5,String weather6,String current_day){
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("temp3", temp3);
		editor.putString("temp4", temp4);
		editor.putString("temp5", temp5);
		editor.putString("temp6", temp6);
		editor.putString("weather1", weather1);
		editor.putString("weather2", weather2);
		editor.putString("weather3", weather3);
		editor.putString("weather4", weather4);
		editor.putString("weather5", weather5);
		editor.putString("weather6", weather6);
		editor.putString("current_day",current_day );
		editor.commit();
        }


/**
 * 本地天气的JSON解析
 */

	public static void parseJSONWithJSONObject(Context context,String jsonData) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			String current_day=jsonObject.getString("date");
            JSONArray resultsArray=jsonObject.getJSONArray("results");
			JSONObject subObject=resultsArray.getJSONObject(0);
			String cityName=subObject.getString("currentCity");
			JSONArray weather_dataArray=subObject.getJSONArray("weather_data");
			
			JSONObject aObject=weather_dataArray.getJSONObject(0);
			String temp1=aObject.getString("temperature");
			String weather1=aObject.getString("weather");
		    JSONObject bObject=weather_dataArray.getJSONObject(1);
		    String temp2=bObject.getString("temperature");
		    String weather2=bObject.getString("weather");
			JSONObject cObject=weather_dataArray.getJSONObject(2);
			String temp3=cObject.getString("temperature");
			String weather3=cObject.getString("weather");
			JSONObject dObject=weather_dataArray.getJSONObject(3);
			String temp4=dObject.getString("temperature");
			String weather4=dObject.getString("weather");
         
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
			editor.putString("current_day", current_day);
			editor.putString("city_name", cityName);
			editor.putString("weather1", weather1);
			editor.putString("weather2", weather2);
			editor.putString("weather3", weather3);
			editor.putString("weather4", weather4);
            editor.putString("temp1", temp1);
			editor.putString("temp2", temp2);
			editor.putString("temp3", temp3);
			editor.putString("temp4", temp4);
            editor.commit();
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 显示天气图像
	 * @return 
	 */
	public static int  showImg(String climate){
		if (climate.contains("转")) {// 天气带转字，取前面那部分
			String[] strs = climate.split("转");
			climate = strs[0];
			if (climate.contains("到")) {// 如果转字前面那部分带到字，则取它的后部分
				strs = climate.split("到");
				climate = strs[1];
			}
		}
		int weather_img = R.drawable.biz_plugin_weather_qing;
		switch (climate) {
		case "暴雪":
			weather_img = R.drawable.biz_plugin_weather_baoxue;
			break;
		case "暴雨":
			weather_img = R.drawable.biz_plugin_weather_baoyu;
			break;
		case "大暴雨":
			weather_img = R.drawable.biz_plugin_weather_dabaoyu;
			break;
		case "大雪":
			weather_img = R.drawable.biz_plugin_weather_daxue;
			break;
		case "大雨":
			weather_img = R.drawable.biz_plugin_weather_dayu;
			break;
		case "多云":
			weather_img = R.drawable.biz_plugin_weather_duoyun;
			break;
		case "雷阵雨":
			weather_img = R.drawable.biz_plugin_weather_leizhenyu;
			break;
		case "雷阵雨冰雹":
			weather_img = R.drawable.biz_plugin_weather_leizhenyubingbao;
			break;
		case "晴":
			weather_img = R.drawable.biz_plugin_weather_qing;
			break;
		case "沙尘暴":
			weather_img = R.drawable.biz_plugin_weather_shachenbao;
			break;
		case "特大暴雨":
			weather_img = R.drawable.biz_plugin_weather_tedabaoyu;
			break;
		case "雾":
			weather_img = R.drawable.biz_plugin_weather_wu;
			break;
		case "小雪":
			weather_img = R.drawable.biz_plugin_weather_xiaoxue;
			break;
		case "小雨":
			weather_img = R.drawable.biz_plugin_weather_xiaoyu;
			break;
		case "阴":
			weather_img = R.drawable.biz_plugin_weather_yin;
			break;
		case "雨夹雪":
			weather_img = R.drawable.biz_plugin_weather_yujiaxue;
			break;
		case "阵雪":
			weather_img = R.drawable.biz_plugin_weather_zhenxue;
			break;
		case "阵雨":
			weather_img = R.drawable.biz_plugin_weather_zhenyu;
			break;
		case "中雪":
			weather_img = R.drawable.biz_plugin_weather_zhongxue;
			break;
		case "中雨":
			weather_img = R.drawable.biz_plugin_weather_zhongyu;
			break;

		}
		
		return weather_img;
	}
}