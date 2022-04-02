package br.com.mystore.desktop.utils;

import javax.swing.table.AbstractTableModel;

public class TabelaModeloObjeto extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columns;
	private Object[][] rows;

	public TabelaModeloObjeto() {
	}

	public TabelaModeloObjeto(Object[][] rows, String[] columns) {
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public int getRowCount() {
		return this.rows.length;
	}

	@Override
	public int getColumnCount() {
		return this.columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.rows[rowIndex][columnIndex];
	}

	public Object getObjectAt(int row) {
		Object[] object = new Object[this.columns.length];
		for (int column = 0; column < this.columns.length; column++)
			object[column] = this.rows[row][column];
		return object;
	}

	public String getColumnName(int index) {
		return this.columns[index];
	}

}
