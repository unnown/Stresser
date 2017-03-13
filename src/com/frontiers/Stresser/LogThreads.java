package com.frontiers.Stresser;

import org.bukkit.scheduler.BukkitTask;

public class LogThreads {
	public long startTime;
	public long EndTime;
	public BukkitTask task;
	
	public LogThreads() { }	
	
	public LogThreads(long start, BukkitTask taskwork) {
		this.startTime = start;
		this.task = taskwork;
	}
	
	public void setEnd(long end) {
		this.EndTime = end;
	}
}
