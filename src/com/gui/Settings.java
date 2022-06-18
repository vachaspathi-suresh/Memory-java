package com.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.board.GameBoard;

public class Settings {

	GameBoard gb;

	public Settings(GameBoard gb) {
		this.gb = gb;
		soundSettings();
	}

	private void soundSettings() {
		gb.gameStatus = new File("GameStatus.txt");
		try {
			if (gb.gameStatus.createNewFile()) {
				FileWriter fwriter = new FileWriter(gb.gameStatus);
				fwriter.write("1 1");
				fwriter.close();
			}
			Scanner freader = new Scanner(gb.gameStatus);
			String[] l = freader.nextLine().split(" ");
			freader.close();
			gb.soundOn = l[0].equals("1") ? true : false;
			gb.musicOn = l[1].equals("1") ? true : false;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clearData() {
		gb.lO.completedLev.clear();
		gb.lO.levBest.clear();
		gb.levelData.delete();
		gb.levelBest.delete();
		gb.lO.Level = 0;
		gb.gameSetUp();
	}

	public void soundSwitch() {
		gb.playSE(gb.effects.switchM);
		gb.soundOn = gb.soundOn ? false : true;
		if (gb.soundOn) {
			gb.playGM();
		} else {
			gb.stopGM();
		}

		try {
			FileWriter fwriter = new FileWriter(gb.gameStatus);
			String l = gb.soundOn ? "1 " : "0 ";
			l = l + (gb.musicOn ? "1" : "0");
			fwriter.write(l);
			fwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void musicSwitch() {
		gb.playSE(gb.effects.switchM);
		gb.musicOn = gb.musicOn ? false : true;
		try {
			FileWriter fwriter = new FileWriter(gb.gameStatus);
			String l = gb.soundOn ? "1 " : "0 ";
			l = l + (gb.musicOn ? "1" : "0");
			fwriter.write(l);
			fwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gameRetry() {
		gb.lO.Level--;
		gb.gameSetUp();
	}

}
