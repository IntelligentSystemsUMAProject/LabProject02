package logic;

public class Node {
	private int g;
	private Tuple pos;
	private Node parent;

	public Node(int g, Tuple pos, Node parent) {
		this.g = g;
		this.pos = pos;
		this.parent = parent;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public Tuple getPos() {
		return pos;
	}
	
	public void setPos(Tuple pos) {
		this.pos = pos;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	
	
	
	
}
