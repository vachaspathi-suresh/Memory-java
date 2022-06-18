package com.board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickListener extends MouseAdapter {

	int x = 0;
	int y = 0;
	int cN = -1;
	GameBoard gb;
	boolean click = false;
	boolean removeCard = false;

	public MouseClickListener(GameBoard gb) {
		this.gb = gb;
	}

	public void mouseClicked(MouseEvent e) {
		if (!removeCard) {
			x = e.getX();
			y = e.getY();
			if (gb.playS) {
				if (x >= 290 && x <= 490 && y >= 350 && y <= 450) {
					gb.playS = false;
					gb.levelS = true;
				}
			} else if (gb.startLevel) {
				if (x >= 100 && x <= 700 && y >= 120 && y <= 520) {
					int lX = 100, lY = 120;
					for (int i = 0; i < gb.cS.row; i++) {
						if (y >= lY && y <= lY + (400 / gb.cS.row) - 10) {
							for (int j = 0; j < gb.cS.col; j++) {
								if (x >= lX && x <= lX + (600 / gb.cS.col) - 10) {
									cN = j + (i * gb.cS.col);
									click = true;
								}
								lX += (600 / gb.cS.col);
							}
						}
						lY += (400 / gb.cS.row);
						lX = 100;
					}
				}
				if (x >= 10 && x <= 110 && y >= 10 && y <= 60) {
					gb.startLevel = false;
					gb.levelS = true;
					gb.playS = false;
				}
			} else if (gb.levelS && !gb.setS) {
				if (x >= 100 && x <= 700 && y >= 150 && y <= 550) {
					int lX = 100, lY = 150;
					for (int i = 0; i < 2; i++) {
						if (y >= lY && y <= lY + 150) {
							for (int j = 0; j < 5; j++) {
								if (x >= lX && x <= lX + 100) {
									gb.lO.Level = j + (i * 5);
									if (gb.lO.completedLev.contains(gb.lO.Level + 1)) {
										gb.gameSetUp();
										gb.levelS = false;
										gb.playS = false;
										gb.startLevel = true;
									}
								}
								lX += 125;
							}
						}
						lY += 225;
						lX = 100;
					}
				} else if (x >= 730 && x <= 790 && y >= 10 && y <= 70) {
					gb.setS = true;
					gb.playS = false;
					gb.startLevel = false;
				}
			} else if (gb.setS) {
				if (x >= 110 && x <= 190 && y >= 80 && y <= 120) {
					gb.setS = false;
					gb.playS = false;
					gb.startLevel = false;
					gb.levelS = true;
				} else if (x >= 320 && x <= 370 && y >= 198 && y <= 221) {
					gb.setting.soundSwitch();
				} else if (x >= 320 && x <= 370 && y >= 258 && y <= 281) {
					gb.setting.musicSwitch();
				} else if (x >= 480 && x <= 580 && y >= 315 && y <= 355) {
					gb.setting.clearData();
				}
			}

			if (gb.gameLostS) {
				if (x >= 200 && x <= 350 && y >= 300 && y <= 450) {
					gb.setting.gameRetry();
					gb.gameLostS = false;
					gb.startLevel = true;
					gb.levelS = false;
				} else if (x >= 450 && x <= 600 && y >= 300 && y <= 450) {
					gb.gameLostS = false;
					gb.startLevel = false;
					gb.levelS = true;
					gb.playS = false;
					gb.setS = false;

					gb.movesLeft = 1;
				}
			}

		}
	}

}