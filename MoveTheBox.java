package com.peter.move_the_bag;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 
 * minimum steps to eliminate all bags
 * 
 * <pre>
 * 0000010000
 * 0000220000
 * 0000312000
 * 0000133000
 * </pre>
 * 
 * @author peter 2015年8月13日
 *
 */
public class MoveTheBox {

	public static void move(int[][] board, int minStep,
			LinkedList<String> result) {
		rearrange(board);
		cleanBoard(board);
		if (minStep == 0) {
			if (isClear(board)) {
				System.out.println(result);
			}
			return;
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 1; j < board[0].length - 1; j++) {
				if (board[i][j] != 0) {
					// move left
					int[][] copy = copy(board);
					int tmp = copy[i][j - 1];
					copy[i][j - 1] = copy[i][j];
					copy[i][j] = tmp;
					result.add(i + "_" + j + "_l");
					move(copy, minStep - 1, result);
					result.removeLast();
					copy[i][j] = copy[i][j - 1];
					copy[i][j - 1] = tmp;

					// move right
					copy = copy(board);
					tmp = copy[i][j + 1];
					copy[i][j + 1] = copy[i][j];
					copy[i][j] = tmp;
					result.add(i + "_" + j + "_r");
					move(copy, minStep - 1, result);
					result.removeLast();
					copy[i][j] = copy[i][j + 1];
					copy[i][j + 1] = tmp;
					// move down
					copy = copy(board);
					if (i != copy.length - 1) {
						tmp = copy[i + 1][j];
						copy[i + 1][j] = copy[i][j];
						copy[i][j] = tmp;
						result.add(i + "_" + j + "_d");
						move(copy, minStep - 1, result);
						result.removeLast();
						copy[i][j] = copy[i + 1][j];
						copy[i + 1][j] = tmp;
					}
				}
			}
		}
	}

	private static int[][] copy(int[][] board) {
		int[][] copy = new int[board.length][board[0].length];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	private static boolean isClear(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	private static class Pair {
		int i;
		int j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	// tracking same blocks && delete them in the one round, then rearrange
	/**
	 * <pre>
	 * 000000
	 * 000100
	 * 000100
	 * 011111
	 * </pre>
	 * 
	 * @param board
	 */

	private static void cleanBoard(int[][] board) {
		Set<Pair> set = new HashSet<Pair>();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] > 0) {
					// right
					int offset = 3;
					int base = board[i][j];
					if (j + 2 < board[0].length && base == board[i][j + 1]
							&& base == board[i][j + 2]) {
						set.add(new Pair(i, j));
						set.add(new Pair(i, j + 1));
						set.add(new Pair(i, j + 2));

						while (j + offset < board[0].length
								&& base == board[i][j + offset]) {
							set.add(new Pair(i, j + offset));
							offset++;
						}
					}
					// down
					offset = 3;
					if (i + 2 < board.length && base == board[i + 1][j]
							&& base == board[i + 2][j]) {
						set.add(new Pair(i, j));
						set.add(new Pair(i + 1, j));
						set.add(new Pair(i + 2, j));

						while (i + offset < board.length
								&& base == board[i + offset][j]) {
							set.add(new Pair(i + offset, j));
							offset++;
						}
					}
				}
			}
		}
		// cleaning
		for (Pair p : set) {
			board[p.i][p.j] = 0;
		}
		rearrange(board);
		if (!set.isEmpty()) {
			set.clear();
			cleanBoard(board);
		}
	}

	private static void rearrange(int[][] board) {
		for (int j = 0; j < board[0].length; j++) {
			int cursor = board.length - 1;
			int current = board.length - 1;
			while (cursor >= 0) {
				if (board[cursor][j] != 0) {
					if (cursor != current) {
						board[current][j] = board[cursor][j];
						board[cursor][j] = 0;
					}
					current--;
				}
				cursor--;
			}
		}
	}

	public static void main(String[] args) {
		int[][] board = { //
		// { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 }, { 0, 0, 0 }, };
				{ 0, 0, 0, 1, 0, 0, 0 },//
				{ 0, 0, 0, 2, 1, 0, 0 },//
				{ 0, 0, 1, 1, 2, 0, 3 },//
				{ 0, 0, 2, 4, 1, 1, 3 },//
				{ 0, 1, 1, 4, 5, 5, 4 },//
				{ 0, 4, 4, 5, 4, 3, 3 },//
				{ 1, 5, 5, 4, 3, 3, 4 } };

		MTB.move(board, 3, new LinkedList<String>());
		// print(board);
		// MTB.rearrange(board);
		// System.out.println();
		// print(board);
		// cleanRight(board, 3, 0);
		// System.out.println();
		// print(board);
	}
}
