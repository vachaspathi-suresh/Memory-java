package com.gui;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.board.GameBoard;

public class UI {

	GameBoard gb;

	BufferedImage play1;
	BufferedImage play2;
	BufferedImage playButton;
	BufferedImage emptyLev;
	BufferedImage lockLev;
	BufferedImage back;
	BufferedImage menu;
	BufferedImage retry;
	BufferedImage mainMenu;
	BufferedImage trumpR;
	BufferedImage trumpL;
	int count;
	int imgNo = 1;

	public UI(GameBoard gb) {
		this.gb = gb;
		imageImport();
		count = 0;
	}

	private void imageImport() {
		try {
			play1 = ImageIO.read(getClass().getResourceAsStream("/other/playButton1.png"));
			play2 = ImageIO.read(getClass().getResourceAsStream("/other/playButton2.png"));
			emptyLev = ImageIO.read(getClass().getResourceAsStream("/other/levCard.png"));
			lockLev = ImageIO.read(getClass().getResourceAsStream("/other/lockCard.png"));
			back = ImageIO.read(getClass().getResourceAsStream("/other/back.png"));
			menu = ImageIO.read(getClass().getResourceAsStream("/other/menu.png"));
			retry = ImageIO.read(getClass().getResourceAsStream("/other/Retry.png"));
			mainMenu = ImageIO.read(getClass().getResourceAsStream("/other/MainMenu.png"));
			trumpR = ImageIO.read(getClass().getResourceAsStream("/other/trumpRight.png"));
			trumpL = ImageIO.read(getClass().getResourceAsStream("/other/trumpLeft.png"));
			playButton = play1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawOpenS(Graphics2D g2) {
		g2.setPaint(new Color(208, 170, 99));
		g2.fillRect(0, 0, gb.sW, gb.sH);
		String text = "MEMORY";
		g2.setFont(new Font("Serif", Font.BOLD, 120));
		g2.setPaint(new Color(175, 95, 0));
		g2.drawString(text, 120, 150);
		text = "PLAY";
		g2.setFont(new Font("Serif", Font.BOLD, 50));
		g2.setPaint(new Color(176, 143, 111));
		g2.drawString(text, 322, 350);
		if (count >= 20) {
			count = 0;
			imgNo = (imgNo == 1) ? 2 : 1;
			playButton = (imgNo == 2) ? play2 : play1;

		}
		g2.drawImage(playButton, 290, 350, 200, 100, null);
		count++;

	}

	public void drawLevelS(Graphics2D g2) {
		g2.setPaint(new Color(75, 40, 90));
		g2.fillRect(0, 0, gb.sW, gb.sH);

		String text = "Select Level";
		g2.setFont(new Font("Serif", Font.BOLD, 60));
		g2.setPaint(new Color(238, 215, 227));
		g2.drawString(text, 250, 60);

		g2.drawImage(menu, 730, 10, 60, 60, null);

		int x = 100, y = 150;
		for (int i = 0; i < 10; i++) {
			if (gb.lO.completedLev.contains(i + 1)) {
				g2.setPaint(new Color(238, 215, 227));
				g2.setFont(new Font("Serif", Font.BOLD, 80));
				g2.drawImage(emptyLev, x, y, 100, 150, null);
				if (i < 9)
					g2.drawString(Integer.toString(i + 1), x + 30, y + 100);
				else
					g2.drawString(Integer.toString(i + 1), x + 8, y + 100);
			} else
				g2.drawImage(lockLev, x, y, 100, 150, null);
			g2.setPaint(new Color(42, 77, 110));
			g2.fillRect(x - 5, y + 150, 110, 25);
			g2.setPaint(new Color(238, 215, 227));
			g2.setFont(new Font("Serif", Font.BOLD, 14));
			g2.drawString("Best In: " + gb.lO.levBest.get(i + 1) + " moves", x - 5, y + 167);
			x += 125;
			if (i == 4) {
				y += 225;
				x = 100;
			}
		}
	}

	public void drawGameS(Graphics2D g2) {
		g2.setPaint(new Color(60, 97, 121));
		g2.fillRect(0, 0, gb.sW, gb.sH);

		gb.cS.drawCards(g2);
		String text = "Moves Left:" + gb.movesLeft;
		g2.setFont(new Font("Serif", Font.BOLD, 40));
		g2.setPaint(new Color(255, 0, 0));
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		g2.drawString(text, gb.sW - textLength - 5, 100);

		text = "Level: " + gb.lO.Level;
		g2.setFont(new Font("Serif", Font.BOLD, 70));
		g2.setPaint(new Color(205, 98, 0));
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		g2.drawString(text, (gb.sW / 2) - (textLength / 2) - 10, 60);

		g2.drawImage(back, 10, 10, 100, 50, null);

	}

	public void drawSetS(Graphics2D g2) {
		g2.setPaint(new Color(60, 97, 121, 150));
		g2.fillRect(0, 0, gb.sW, gb.sH);

		g2.setPaint(new Color(5, 85, 74, 245));
		g2.fillRect(150, 120, 500, 300);

		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2f));
		g2.setPaint(new Color(197, 152, 169, 245));
		g2.drawRect(110, 80, 40, 40);
		g2.setPaint(new Color(20, 20, 20, 245));
		g2.drawRect(113, 83, 34, 34);
		g2.setPaint(new Color(187, 0, 9, 245));
		g2.setStroke(new BasicStroke(4f));
		g2.drawLine(120, 90, 138, 108);
		g2.drawLine(138, 90, 120, 108);
		g2.setStroke(oldStroke);

		String text = "Sound:";
		g2.setFont(new Font("Serif", Font.BOLD, 40));
		g2.setPaint(new Color(184, 72, 0));
		g2.drawString(text, 180, 220);
		g2.setPaint(new Color(197, 152, 169));
		g2.fillRect(320, 198, 50, 23);
		if (!gb.soundOn) {
			g2.setPaint(new Color(220, 0, 0));
			g2.fillRect(322, 200, 21, 19);
		} else {
			g2.setPaint(new Color(0, 220, 0));
			g2.fillRect(347, 200, 21, 19);
		}

		text = "Music:";
		g2.setFont(new Font("Serif", Font.BOLD, 40));
		g2.setPaint(new Color(184, 72, 0));
		g2.drawString(text, 180, 280);
		g2.setPaint(new Color(197, 152, 169));
		g2.fillRect(320, 258, 50, 23);
		if (!gb.musicOn) {
			g2.setPaint(new Color(220, 0, 0));
			g2.fillRect(322, 260, 21, 19);
		} else {
			g2.setPaint(new Color(0, 220, 0));
			g2.fillRect(347, 260, 21, 19);
		}

		text = "Erase Game Data:";
		g2.setPaint(new Color(184, 72, 0));
		g2.setFont(new Font("Serif", Font.BOLD, 35));
		g2.drawString(text, 180, 345);
		g2.setPaint(new Color(97, 0, 0));
		g2.draw3DRect(480, 315, 100, 40, true);
		g2.setPaint(new Color(144, 0, 0));
		g2.fillRect(482, 317, 97, 37);
		g2.setPaint(new Color(197, 152, 169));
		g2.setFont(new Font("Serif", Font.BOLD, 25));
		g2.drawString("Clear", 500, 343);

	}

	public void drawLostS(Graphics2D g2) {
		g2.setPaint(new Color(60, 97, 121, 150));
		g2.fillRect(0, 0, gb.sW, gb.sH);
		String text = "You Lost!!";
		g2.setFont(new Font("URW Gothic L", Font.ITALIC, 130));
		g2.setPaint(new Color(204, 101, 0));
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		g2.drawString(text, (gb.sW / 2) - (textLength / 2), 200);

		g2.drawImage(retry, 200, 300, 150, 150, null);
		g2.drawImage(mainMenu, 450, 300, 150, 150, null);
	}

	public void drawWinS(Graphics2D g2) {
		g2.setPaint(new Color(60, 97, 121, 180));
		g2.fillRect(0, 0, gb.sW, gb.sH);

		String text = "Congratulations!!";
		g2.setFont(new Font("URW Gothic L", Font.ITALIC, 70));
		g2.setPaint(new Color(204, 101, 0));
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		g2.drawString(text, (gb.sW / 2) - (textLength / 2), 200);

		if (count <= 40) {
			g2.drawImage(trumpL, 720 - (count * 2), 200, 200, 400, null);
			g2.drawImage(trumpR, -120 + (count * 2), 200, 200, 400, null);
			count++;
		} else if (count > 40 && count <= 150) {
			g2.drawImage(trumpL, 638, 200, 200, 400, null);
			g2.drawImage(trumpR, -38, 200, 200, 400, null);
			count++;
		} else if (count > 150 && count <= 191) {
			g2.drawImage(trumpL, 638 + ((count - 150) * 2), 200, 200, 400, null);
			g2.drawImage(trumpR, -38 - ((count - 150) * 2), 200, 200, 400, null);
			count++;
		}
		if (count == 192) {
			count = 0;
			gb.gameSetUp();
			gb.gameWinS = false;
		}

	}

}
