package com.example.ballinhole;

import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private ObjectView ballV = null, holeV = null;
	private Ball ball, hole;
	private GameTimer ballTimer, clockTimer;
	private int screenw, screenh;
	private Handler redraw = new Handler();
	private float pixelsH, pixelsW;
	private boolean paused = false;
	private TimerTask clockTask, ballTask;

	private Runnable timerTick = new Runnable() {
		public void run() {
			EditText text = (android.widget.EditText) findViewById(R.id.Timer);
			String time = text.getText().toString();
			String[] split = time.split(":");
			int min = Integer.parseInt(split[0]);
			int sec = Integer.parseInt(split[1]);
			sec += 1;
			if (sec == 60) {
				sec -= 60;
				min += 1;

			}
			text.setText(min + ":" + sec);

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.init();
		((SensorManager) getSystemService(Context.SENSOR_SERVICE))
				.registerListener(new SensorEventListener() {

					@Override
					public void onSensorChanged(SensorEvent event) {
						ball.changeSpeedX(-event.values[0]);
						ball.changeSpeedY(event.values[1]);

					}

					@Override
					public void onAccuracyChanged(Sensor sensor, int accuracy) {
						// TODO Auto-generated method stub

					}
				}, ((SensorManager) getSystemService(Context.SENSOR_SERVICE))
						.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
						SensorManager.SENSOR_DELAY_NORMAL);

	}

	private void init() {
		final FrameLayout main = (android.widget.FrameLayout) findViewById(R.id.main_view);
		Display display = getWindowManager().getDefaultDisplay();
		pixelsH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 54,
				getResources().getDisplayMetrics());
		pixelsW = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 29,
				getResources().getDisplayMetrics());
		screenw = (int) (display.getWidth() - pixelsW);
		screenh = (int) (display.getHeight() - pixelsH);
		ball = new Ball(screenw / 2, screenh / 2, 0, 0);
		ballV = new ObjectView(this, 5, ball.getPosX(), ball.getPosY(),
				Color.BLUE);
		int holeX = (int) (pixelsW + (int) (Math.random() * (((screenw - pixelsW) - pixelsW) - 1)));
		int holeY = (int) (pixelsH + (int) (Math.random() * (((screenh - pixelsH) - pixelsH) - 1)));
		hole = new Ball(holeX, holeY, 0, 0);
		holeV = new ObjectView(this, 7, hole.getPosX(), hole.getPosY(),
				Color.BLACK);
		main.addView(holeV);
		main.addView(ballV);
		ballV.invalidate();
		ballTask = new TimerTask() {
			public void run() {
				moveBall();
			}
		};
		clockTask = new TimerTask() {
			public void run() {
				timerMethod();

			}
		};
	}

	private void timerMethod() {
		this.runOnUiThread(timerTick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Exit");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getTitle() == "Exit") {
			finish();
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onPause() {
		clockTimer.cancel();
		clockTimer = null;
		ballTimer.cancel();
		ballTimer = null;
		super.onPause();
	}

	public void onResume() {
		ballTimer = new GameTimer(ballTask);
		ballTimer.schedule(10, 10);
		clockTimer = new GameTimer(clockTask);
		clockTimer.schedule(0, 1000);
		super.onResume();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.runFinalizersOnExit(true);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void moveBall() {
		ball.move();
		if (ball.getPosX() > screenw) {
			ball.changePosX(screenw);
			ball.changeSpeedX(0);
		}
		if (ball.getPosY() > screenh) {
			ball.changePosY(screenh);
			ball.changeSpeedY(0);
		}
		if (ball.getPosX() < (int) pixelsW) {
			ball.changePosX((int) pixelsW);
			ball.changeSpeedX(0);
		}
		if (ball.getPosY() < (int) pixelsH) {
			ball.changePosY((int) pixelsH);
			ball.changeSpeedY(0);
		}

		ballV.updatePosition(ball.getPosX(), ball.getPosY());
		redraw.post(new Runnable() {

			@Override
			public void run() {
				ballV.invalidate();

			}
		});
	}

	public void play() {
		ballTimer = new GameTimer(ballTask);
		ballTimer.schedule(10, 10);
		clockTimer = new GameTimer(clockTask);
		clockTimer.schedule(0, 1000);
		if(!paused){
			init();
		}

	}

	public void pause() {
		clockTimer.cancel();
		clockTimer = null;
		ballTimer.cancel();
		ballTimer = null;
		paused = true;

	}

	public void stop() {
		clockTimer.cancel();
		clockTimer = null;
		ballTimer.cancel();
		ballTimer = null;
		paused = false;
	}
}
