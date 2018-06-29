package org.corgiking.location.demo.model;

import java.util.Arrays;

public class GDResp {
	/**
	 * 状态值:0 表示请求失败；1 表示请求成功。
	 */
	private Integer status;
	/**
	 * 结果数目
	 */
	private Integer count;
	/**
	 * 状态说明
	 */
	private String info;

	/**
	 * 地理编码信息列表
	 */
	private GeoCode[] geocodes;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public GeoCode[] getGeocodes() {
		return geocodes;
	}

	public void setGeocodes(GeoCode[] geocodes) {
		this.geocodes = geocodes;
	}

	@Override
	public String toString() {
		return "GDResp [status=" + status + ", count=" + count + ", info=" + info + ", geocodes="
				+ Arrays.toString(geocodes) + "]";
	}

}
