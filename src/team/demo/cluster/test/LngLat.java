package team.demo.cluster.test;

/**
 * 经纬度
 */
public class LngLat {
	/**
	 * 经度
	 */
	public double lng;
	/**
	 * 纬度
	 */
	public double lat;

	public LngLat() {

	}

	public LngLat(double lng, double lat) {
		super();
		this.lng = lng;
		this.lat = lat;
	}

	public LngLat(String lng, String lat) {
		super();
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
	}

	public LngLat Copy() {
		return new LngLat(lng, lat);
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * 往文件里存储的String
	 * 
	 * @return
	 */
	public String toString() {
		return lng + "," + lat;
	}
}
