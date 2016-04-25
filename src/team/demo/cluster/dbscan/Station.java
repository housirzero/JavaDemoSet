package team.demo.cluster.dbscan;


import java.io.Serializable;

public class Station implements Serializable{
	public String name;
	public String lineId;
	public LngLat pos;

	// 所属聚类类别
	public int classId;
	// 客流量
	public int passFlow;

	public Station(String name, String lineId, LngLat pos,
			int classId) {
		this.name = name;
		this.lineId = lineId;
		this.pos = pos;
		this.classId = -1;
	}

	public Station(String _1, String _2) {
		// 客流
		this.passFlow = Integer.parseInt(_2);
		
		// on_name + "," + on_lineId + "," + on_lng + "," + on_lat;
		String[] items = _1.split(",");
		this.name = items[0];
		this.lineId = items[1];
		this.pos = new LngLat(items[2], items[3]);
		this.pos.setPassFlow(this.passFlow);
		this.classId = -1;
	}
}
