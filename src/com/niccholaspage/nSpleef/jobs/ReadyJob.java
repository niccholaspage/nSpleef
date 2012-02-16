package com.niccholaspage.nSpleef.jobs;

import com.niccholaspage.nSpleef.player.Session;

public class ReadyJob implements Runnable {
	private final Session session;
	
	public ReadyJob(Session session){
		this.session = session;
	}
	
	public void run(){
		if (session.getArena() == null){
			return;
		}
		
		session.setReady(true);
		
		session.getArena().update();
	}
}
