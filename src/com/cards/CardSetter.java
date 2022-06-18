package com.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import com.board.GameBoard;

public class CardSetter {

	GameBoard gb;
	public BufferedImage card;
	public int col = 0;
	public int row = 0;

	Random random = new Random();
	public ArrayList<Integer> numbers = new ArrayList<>();
	ArrayList<BufferedImage> images = new ArrayList<>();
	public HashMap<Integer, BufferedImage> map = new HashMap<>();

	public CardSetter(GameBoard gb) {
		this.gb = gb;
		cardImport();
	}

	public void setRC() {
		if (gb.lO.noCards <= 6) {
			col = gb.lO.noCards;
			row = 2;
		} else if (gb.lO.noCards > 6) {
			if (gb.lO.noCards % 4 == 0) {
				col = gb.lO.noCards / 2;
				row = 4;
			} else if (gb.lO.noCards % 3 == 0) {
				col = (gb.lO.noCards * 2) / 3;
				row = 3;
			}
		}
	}

	private void cardImport() {
		try {
			card = ImageIO.read(getClass().getResourceAsStream("/other/card.jpg"));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/bike.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/bus.jpg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/car.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/cycle.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/train.jpg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/boat.jpg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/vehical/aeroplane.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/cake.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/cupcake.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/doughnut.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/santacookie.jpeg")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/pie.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/desserts/cookie.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/furniture/bed.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/furniture/chair.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/furniture/table.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cardOrganize() {
		int n = gb.lO.noCards * 2;

		while (numbers.size() < n) {

			int x = random.nextInt(n);
			if (!numbers.contains(x)) {
				numbers.add(x);
			}
		}
		int x = 0;
		for (int i = 0; i < gb.lO.noCards; i++) {
			map.put(numbers.get(x++), images.get(i));
			map.put(numbers.get(x++), images.get(i));
		}
	}

	public void drawCards(Graphics2D g2) {
		int x = 100, y = 120;
		g2.setFont(new Font("Serif", Font.BOLD, 30));
		g2.setPaint(new Color(238, 215, 227));
		for (int i = 0; i < gb.lO.noCards * 2; i++) {
			if (gb.clickedCards.contains(i)) {
				g2.drawImage(map.get(i), x, y, (600 / col) - 10, (400 / row) - 10, null);
			} else {
				g2.drawImage(card, x, y, (600 / col) - 10, (400 / row) - 10, null);
//				g2.drawString(Integer.toString(i), x , y);
			}
			x += (600 / col);
			if ((i + 1) % col == 0) {
				x = 100;
				y += (400 / row);
			}
		}
	}

}
