package com.sudoku.sudoku_solver;

public class Tabuleiro {
	private Celula tabuleiro[][];
	private int[][] solucao;

	public Tabuleiro() {
		tabuleiro = new Celula[9][9];
		solucao = new int[9][9];
		inicializarTabuleiro();
	}

	private void inicializarTabuleiro() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				tabuleiro[i][j] = new Celula(0, true);
			}
		}
	}

	public void preencherCelula(int linha, int coluna, int valor, boolean editavel) {
		tabuleiro[linha][coluna] = new Celula(valor, editavel);
	}

	public int getValor(int linha, int coluna) {
		return tabuleiro[linha][coluna].getValue();
	}

	public void setValor(int linha, int coluna, int valor) {
		tabuleiro[linha][coluna].setValue(valor);
	}

	public boolean isEditavel(int linha, int coluna) {
		return tabuleiro[linha][coluna].isEditavel();
	}

	public Celula[][] getTabuleiro() {
		return tabuleiro;
	}

	public void setSolucoes(int[][] solucao) {
		this.solucao = solucao;
	}

	public int[][] getSolucoes() {
		return solucao;
	}
}
