package team.demo.cluster.dbscan;


import java.io.Serializable;


/**
 * Point类，代表样本点
 */
public class Position implements Serializable{
	public double lng; // 经度
	public double lat; // 纬度
	
	public Position(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public Position(String lng, String lat) {
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
	}

	public Position Copy() {
		return new Position(lng, lat);
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

	@Override
	public String toString() {
		return "(" + lng + "," + lat + ")";
	}

}
