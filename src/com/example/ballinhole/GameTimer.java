package com.example.ballinhole;

import java.util.TimerTask;
import java.util.Timer;

public class GameTimer {

	Timer timer;
	TimerTask task;
	public GameTimer(TimerTask task){
		
		timer = new Timer();
		this.task = task;
	}
	
	public void cancel(){
		timer.cancel();
	}
	public void schedule(int value1,int value2){
		timer.schedule(task, value1,value2);
	}
}
