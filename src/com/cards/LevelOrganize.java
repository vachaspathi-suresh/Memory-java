package com.cards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.board.GameBoard;

public class LevelOrganize {

	GameBoard gb;
	public int Level = 0;
	public int noCards = 0;
	public ArrayList<Integer> completedLev = new ArrayList<>();
	public HashMap<Integer, Integer> levBest = new HashMap<>();

	public LevelOrganize(GameBoard gb) {
		this.gb = gb;
	}

	public void levelMaker() {
		try {
			if (gb.levelData.createNewFile()) {
				FileWriter fwriter = new FileWriter(gb.levelData, true);
				fwriter.write("1\n");
				fwriter.flush();
				fwriter.close();
			}
			Scanner freader = new Scanner(gb.levelData);
			while (freader.hasNextLine()) {
				int temp = Integer.parseInt(freader.nextLine());
				if (!completedLev.contains(temp))
					completedLev.add(temp);
			}
			freader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		levelAdder();
		bestAnalizer();
		if (Level <= 3)
			noCards = Level + 3;
		else {
			noCards = Level + 3;
			while (noCards % 3 != 0 && noCards % 4 != 0) {
				noCards--;
			}
		}

	}

	private void levelAdder() {
		try {
			if (!completedLev.contains(Level)) {
				completedLev.add(Level);
				FileWriter fwriter = new FileWriter(gb.levelData, true);
				fwriter.write(Level + "\n");
				fwriter.flush();
				fwriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void bestAnalizer() {
		try {
			if (!levBest.isEmpty()) {
				FileWriter fwriter = new FileWriter(gb.levelBest);
				fwriter.write("");
				fwriter.close();
				fwriter = new FileWriter(gb.levelBest, true);
				for (int i = 1; i < 11; i++) {
					String s = i + " " + levBest.get(i) + "\n";
					fwriter.write(s);
				}
				fwriter.close();
			}
			if (gb.levelBest.createNewFile()) {
				FileWriter fwriter = new FileWriter(gb.levelBest, true);
				for (int i = 1; i < 11; i++) {
					String s = i + " 0\n";
					fwriter.write(s);
				}
				fwriter.close();
			}
			Scanner freader = new Scanner(gb.levelBest);
			while (freader.hasNextLine()) {
				String l = freader.nextLine();
				String[] temp = l.split(" ");
				levBest.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
			}
			freader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void movesDeteminer() {
		gb.levMoves = ((noCards * 2) + noCards) % 2 == 0 ? ((noCards * 2) + noCards) : ((noCards * 2) + noCards + 1);

		if (Level > 4) {
			int temp = (Level / 2) % 2 == 0 ? Level / 2 : (Level / 2) + 1;
			gb.levMoves += temp;
		}
	}

}
