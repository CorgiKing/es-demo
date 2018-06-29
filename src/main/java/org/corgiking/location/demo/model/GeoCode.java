package org.corgiking.location.demo.model;

/**
 * 地理编码信息列表
 */
public class GeoCode {
	/**
	 * 结构化地址信息:省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
	 */
	private String formatted_address;
	/**
	 * 省份名
	 */
	private String province;
	/**
	 * 城市编码
	 */
	private String citycode;
	/**
	 * 城市名
	 */
	private String city;
	/**
	 * 所在的区
	 */
	// private String district;
	/**
	 * 所在的乡镇
	 */
	// private String township;
	/**
	 * 街道
	 */
	// private String street;
	/**
	 * 门牌
	 */
	// private String number;
	/**
	 * 区域编码
	 */
	// private String adcode;
	/**
	 * 坐标点(经度，纬度)
	 */
	private String location;
	/**
	 * 匹配级别
	 */
	private String level;

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "GeoCode [formatted_address=" + formatted_address + ", province=" + province + ", citycode=" + citycode
				+ ", city=" + city + ", location=" + location + ", level=" + level + "]";
	}

}
