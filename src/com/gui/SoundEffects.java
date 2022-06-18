package com.gui;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.board.GameBoard;

public class SoundEffects {

	GameBoard gb;

	Clip clip;
	public URL gmusicM;
	public URL completeM;
	public URL correctM;
	public URL flipM;
	public URL lostM;
	public URL switchM;
	public URL wrongM;

	public SoundEffects(GameBoard gb) {
		this.gb = gb;
		gmusicM = getClass().getResource("/sounds/game_music.wav");
		completeM = getClass().getResource("/sounds/complete.wav");
		flipM = getClass().getResource("/sounds/flip.wav");
		lostM = getClass().getResource("/sounds/lost.wav");
		switchM = getClass().getResource("/sounds/switch.wav");
		wrongM = getClass().getResource("/sounds/wrong.wav");
		correctM = getClass().getResource("/sounds/correct.wav");
	}

	public void setFile(URL url) {
		try {
			AudioInputStream aIS = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(aIS);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

}
