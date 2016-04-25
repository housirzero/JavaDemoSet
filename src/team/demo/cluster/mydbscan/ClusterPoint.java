package team.demo.cluster.mydbscan;

public class ClusterPoint<E> {

	private int classId;
	private boolean isClassed; // 是否已被分类
	private boolean isKey; // 是否为核心点
	
	private E e;
	
	public ClusterPoint(E e) {
		this.classId = -1; // 未分类
		this.isClassed = false; // 未分类
		this.isKey = false; // 非核心点
		this.e = e;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public boolean isClassed() {
		return isClassed;
	}

	public void setClassed(boolean isClassed) {
		this.isClassed = isClassed;
	}

	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return classId+"";
	}
	
	
}
