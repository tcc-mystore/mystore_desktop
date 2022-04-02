package br.com.mystore.desktop.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataHora extends Thread {

	private JLabel jlHora;

	public DataHora(JLabel hora) {
		this.jlHora = hora;
	}

	public String mostraData() {
		DateFormat df = DateFormat.getDateInstance();
		Date hoje = new Date();
		return df.format(hoje);
	}

	@Override
	public void run() {
		try {
			while (true) {
				Date vtDate = new Date();
				StringBuffer vtStringBuffer = new StringBuffer();
				SimpleDateFormat vtSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
				this.jlHora.setText(vtStringBuffer.toString() + vtSimpleDateFormat.format(vtDate));
				Thread.sleep(1000);
				this.jlHora.revalidate();
			}
		} catch (Exception e) {
			log.warn("Erro ao atualizar a data/hora.", e);
		}
	}
}
