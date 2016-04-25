package team.demo.cluster.dbscan;


import java.io.Serializable;


/**
 * Point类，代表样本点
 */
public class LngLat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1970656646267562368L;
	/**
	 * 经度
	 */
	public double lng;
	/**
	 * 纬度
	 */
	public double lat;
	
	public int classId; // 分类结果，类别号
	
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int passFlow; // 客流量
	public int getPassFlow() {
		return passFlow;
	}

	public void setPassFlow(int passFlow) {
		this.passFlow = passFlow;
	}

	private boolean isKey;
	private boolean isClassed;

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
		this.isClassed = true;
	}

	public boolean isClassed() {
		return isClassed;
	}

	public void setClassed(boolean isClassed) {
		this.isClassed = isClassed;
	}

	public LngLat(String lng, String lat, int passFlow) {
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
		this.classId = -1;
		this.passFlow = passFlow;
	}
	
	public LngLat(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
		this.classId = -1;
		this.passFlow = 1;
	}

	public LngLat(String lng, String lat) {
		this.lng = Double.parseDouble(lng);
		this.lat = Double.parseDouble(lat);
		this.classId = -1;
		this.passFlow = 1;
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

	public String toString() {
		return lng + "," + lat + "," + passFlow;
	}

}
