package org.corgiking.location.demo.model;

public class GeoPoint {

	/**
	 * 经度
	 */
	private float longitude;
	/**
	 * 纬度
	 */
	private float latitude;

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "GeoPoint [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

}
