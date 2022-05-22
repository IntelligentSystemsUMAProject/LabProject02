package logic;

import java.util.Objects;

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
	
	public int getf(Tuple goal) {
		return this.g + getHeuristic(this.pos, goal);
	}
	
	public int getHeuristic(Tuple pos, Tuple exit) {
		int xDistance = Math.abs(exit.getX() - pos.getX());
		int yDistance = Math.abs(exit.getY() - pos.getY());

		return xDistance + yDistance;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(pos, other.pos);
	}
	
	@Override
	public String toString() {
		return "Node{ g: " + this.g + " pos: " + pos.toString() + "}";
	}
	
}
