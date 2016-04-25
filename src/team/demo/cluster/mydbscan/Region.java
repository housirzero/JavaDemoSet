package team.demo.cluster.mydbscan;

import java.io.Serializable;

/**
 * 热点区域
 */
public class Region implements Serializable{
	public Position center; // 简单地取点的加权客流平均中心
	public double r; // 半径
	public int passFlow; // 客流量
	public double alpha; // 是热点的可信度
	public String describe; // 备注，描述
	
	public Region(Position center, double r, int passFlow) {
		super();
		this.center = center;
		this.r = r;
		this.passFlow = passFlow;
	}

	@Override
	public String toString() {
		return "[(" + center + ")," + r + "," + passFlow + "]";
	}
	
	
}
