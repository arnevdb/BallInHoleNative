package com.example.ballinhole;

public class Ball {

	private android.graphics.PointF position, speed;

	public Ball(int x, int y, int sx, int sy) {
		position = new android.graphics.PointF();
		speed = new android.graphics.PointF();
		this.setPosition(x, y);
		this.setSpeed(sx, sy);
	}

	private void setPosition(int x, int y) {
		position.x = x;
		position.y = y;

	}

	private void setSpeed(int sx, int sy) {
		speed.x = sx;
		speed.y = sy;

	}

	public float getPosX() {
		return position.x;
	}

	public float getPosY() {
		return position.y;
	}

	public float getSpeedX() {
		return speed.x;
	}

	public float getSpeedY() {
		return speed.y;
	}

	public void changeSpeedX(float x) {
		this.speed.x = x;
	}

	public void changeSpeedY(float y) {
		this.speed.y = y;
	}

	public void move() {
		position.x += speed.x;
		position.y += speed.y;

	}

	public void changePosX(int x) {
		position.x = x;
	}

	public void changePosY(int y) {
		position.y = y;
	}
}
