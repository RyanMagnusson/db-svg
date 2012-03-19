package com.dbsvg.objects.view;

public class Point<T> {

	private T y;
	private T x;

	public Point() {
	}

	public Point(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public T getY() {
		return y;
	}

	public void setY(T y) {
		this.y = y;
	}

	public T getX() {
		return x;
	}

	public void setX(T x) {
		this.x = x;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[x:");
		builder.append(x);
		builder.append(",y:");
		builder.append(y);
		builder.append(']');
		return builder.toString();
	}

}
