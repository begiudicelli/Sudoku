package com.sudoku.sudoku_solver;

public class Celula {
	private Integer value;
	private boolean editavel;

	public Celula(Integer value, boolean editavel) {
		super();
		this.value = value;
		this.editavel = editavel;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public boolean isEditavel() {
		return editavel;
	}

	public void setEditavel(boolean editavel) {
		this.editavel = editavel;
	}
}
