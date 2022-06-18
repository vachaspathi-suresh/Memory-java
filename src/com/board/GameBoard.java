package com.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.cards.CardSetter;
import com.cards.LevelOrganize;
import com.gui.Settings;
import com.gui.SoundEffects;
import com.gui.UI;

public class GameBoard extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public final int sH = 600;
	public final int sW = 800;
	int fps = 60;
	Thread gameThread;
	int bufferCard;
	int count = 0;
	public int movesLeft = 0;
	public int levMoves = 0;
	private int clickCount = 1;
	public boolean playS = false;
	public boolean startLevel = false;
	public boolean levelS = false;
	public boolean setS = false;
	public boolean gameLostS = false;
	public boolean gameWinS = false;
	public boolean soundOn;
	public boolean musicOn;
	public boolean once = true;
	public File levelData;
	public File levelBest;
	public File gameStatus;
	public BufferedImage icon;
	public ArrayList<Integer> clickedCards = new ArrayList<>();

	public CardSetter cS = new CardSetter(this);
	public LevelOrganize lO = new LevelOrganize(this);
	public Settings setting = new Settings(this);
	public UI ui = new UI(this);
	public SoundEffects music = new SoundEffects(this);
	public SoundEffects effects = new SoundEffects(this);
	MouseClickListener mL = new MouseClickListener(this);

	public GameBoard() {
		levelData = new File("LevelData.txt");
		levelBest = new File("LevelBest.txt");
		this.setPreferredSize(new Dimension(sW, sH));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mL);
		this.setFocusable(true);
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/other/memory.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		playS = true;
		if (soundOn)
			playGM();
	}

	public void gameSetUp() {
		lO.Level++;
		lO.levelMaker();
		once = true;
		lO.movesDeteminer();
		movesLeft = levMoves;
		cS.setRC();
		clickCount = 1;
		count = 0;
		clickedCards.clear();
		cS.map.clear();
		cS.numbers.clear();
		cS.cardOrganize();
	}

	@Override
	public void run() {
		organize();
		double intervel = 1000000000 / fps;
		double nextSpot = System.nanoTime() + intervel;
		while (gameThread != null) {
			update();
			repaint();
			if (movesLeft <= 0 && clickedCards.size() < lO.noCards * 2)
				gameLost();
			try {
				double timeLeft = (nextSpot - System.nanoTime()) / 1000000;
				if (timeLeft < 0)
					timeLeft = 0;
				Thread.sleep((long) timeLeft);
				nextSpot += intervel;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void gameLost() {
		if (musicOn && once)
			playSE(effects.lostM);
		gameLostS = true;
		startLevel = false;
		levelS = false;
		setS = false;
		playS = false;
		once = false;
		repaint();
	}

	private void update() {
		if (mL.click && !clickedCards.contains(mL.cN)) {
			movesLeft--;
			clickedCards.add(mL.cN);
			clickCount = (clickCount == 2) ? 1 : 2;
			if (clickCount == 2) {
				bufferCard = mL.cN;
				if (musicOn)
					playSE(effects.flipM);
			} else {
				if (cS.map.get(bufferCard) != cS.map.get(mL.cN)) {
					mL.removeCard = true;
					if (musicOn)
						playSE(effects.wrongM);
				} else {
					if (musicOn)
						playSE(effects.correctM);
				}
			}
			mL.click = false;
		}
		if (mL.removeCard) {
			if (count >= 90) {
				clickedCards.remove(clickedCards.indexOf(mL.cN));
				clickedCards.remove(clickedCards.indexOf(bufferCard));
				count = 0;
				mL.removeCard = false;
			}
			count++;
		}

		if (clickedCards.size() >= lO.noCards * 2) {
			if (musicOn && once)
				playSE(effects.completeM);
			once = false;
			int temp = levMoves - movesLeft;
			if (temp < lO.levBest.get(lO.Level) || lO.levBest.get(lO.Level) == 0) {
				if (temp < 0)
					temp = 0;
				lO.levBest.replace(lO.Level, temp);
			}
			if (lO.Level < 10) {
				gameWinS = true;
			} else {
				lO.Level = 0;
				gameWinS = true;
			}
		}
	}

	private void organize() {
		cS.cardOrganize();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (playS) {
			ui.drawOpenS(g2);
		} else if (startLevel || gameLostS) {
			ui.drawGameS(g2);

		} else if (levelS) {
			ui.drawLevelS(g2);
		}
		if (gameLostS) {
			ui.drawLostS(g2);
		}
		if (gameWinS) {
			ui.drawWinS(g2);
		}
		if (setS) {
			ui.drawSetS(g2);
		}
		g2.dispose();

	}

	public void playGM() {
		music.setFile(music.gmusicM);
		music.play();
		music.loop();
	}

	public void stopGM() {
		music.stop();
	}

	public void playSE(URL url) {
		effects.setFile(url);
		effects.play();
	}

}
