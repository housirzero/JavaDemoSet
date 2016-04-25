package team.demo.geometry;

/**
 * Point类，代表样本点
 */
public class LngLat{

	/**
	 * 经度
	 */
	public double lng;
	/**
	 * 纬度
	 */
	public double lat;

	public LngLat(String lng, String lat) {
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
	}
	
	public LngLat(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public LngLat Copy() {
		return new LngLat(lng, lat);
	}

	public String toString() {
		return lng + "," + lat;
	}

}
