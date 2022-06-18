package com.board;

import javax.swing.JFrame;

public class GameMain {
	
	public static JFrame frame;

	public static void main(String[] args) {

		frame = new JFrame();
		GameBoard panel = new GameBoard();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Memory");
		frame.setIconImage(panel.icon);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.add(panel);
		frame.pack();

		panel.gameSetUp();
		panel.startGameThread();

	}

}
