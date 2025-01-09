package com.sudoku.sudoku_solver;

public class JogoSudoku {
	private Tabuleiro tabuleiro;
	private String estado;

	public JogoSudoku(Tabuleiro tabuleiro, String estado) {
		super();
		this.tabuleiro = tabuleiro;
		this.estado = estado;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
