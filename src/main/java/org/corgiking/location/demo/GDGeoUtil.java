package org.corgiking.location.demo;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.corgiking.location.demo.model.GDResp;
import org.corgiking.location.demo.model.GeoCode;
import org.elasticsearch.common.geo.GeoPoint;

import com.google.gson.Gson;

public class GDGeoUtil {

	private static final String GD_KEY = "441c6e620ac942112845";
	private static final String GEO_API = "http://restapi.amap.com/v3/geocode/geo?city={0}&address={1}&key={2}";

	public static void main(String[] args) {
		String[] locations = { "杭州市汇和城购物中心", "杭州长运公路汽车站", "杭州市火车东站" };

		String city = "浙江杭州";

		for (String location : locations) {
			getGeoPoint(city, location);
		}
	}

	/**
	 * 根据城市，位置信息获取经纬度
	 * 
	 * @param city
	 * @param addr
	 * @return null表示获取失败
	 */
	public static GeoPoint getGeoPoint(String city, String addr) {
		GDResp resp = getGeoResp(city, addr);
		GeoCode[] geocodes = resp.getGeocodes();
		GeoPoint geoPoint = null;
		if (geocodes != null && geocodes.length == 1 && geocodes[0].getLocation() != null) {
			String[] splits = geocodes[0].getLocation().split(",");
			if (splits.length == 2) {
				geoPoint = new GeoPoint();
				geoPoint.resetLon(Float.parseFloat(splits[0]));
				geoPoint.resetLat(Float.parseFloat(splits[1]));
			}
		}
		return geoPoint;
	}

	/**
	 * 根据城市，地址获取地理信息
	 * @param city
	 * @param addr
	 * @return
	 */
	public static GDResp getGeoResp(String city, String addr) {

		String url = MessageFormat.format(GEO_API, city, addr, GD_KEY);

		GDResp resp = getHttp(url, GDResp.class);
		return resp;
	}

	/**
	 * 请求url，并将json格式的响应体转为T类型返回
	 * @param url
	 * @param clazz
	 * @return
	 */
	public static <T> T getHttp(String url, Class<T> clazz) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
			String jsonRet = EntityUtils.toString(response.getEntity());
			T obj = new Gson().fromJson(jsonRet, clazz);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
