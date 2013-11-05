package com.example.ballinhole;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ObjectView extends View{

	public float x,y;
	private final int radius;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public ObjectView(Context context, int r,float x, float y,int color) {
		super(context);
		paint.setColor(color);
		this.radius = r;
		this.x = x;
		this.y = y;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawCircle(x, y, radius, paint);
	}
	
	public void updatePosition(float x, float y){
		this.x = x;
		this.y = y;
	}

}
