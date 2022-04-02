package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SobreView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JDialog jdSobreSistema, jdSobreDesenvolvedor;
	private JPanel jpCentro, jpRodape;
	private JButton jbConfirmaDesenvolvedor, jbConfirmaSistema;
	private JTextArea jtaSobreSistema, jtaSobreDesenvolvedor;
	private JDialog jdSobreBibliografia;
	private JTextArea jtaSobreBibliografia;
	private JButton jbConfirmaBibliografia;
	private ImageIcon iiConfirmaBibliografia;

	public SobreView() {
		sobreSistema();
	}

	public void sobreSistema() {
		this.jdSobreSistema = new JDialog();
		this.jpCentro = new JPanel();
		this.jtaSobreSistema = new JTextArea("Nome do sistema: My Store"
				+ "\nVersão 1.0.0\nAutor: Geverson José de Souza" + "\nTodos os direitos reservados"
				+ "\nEsse sistema gerencia as vendas,\n as entregas e o caixa do estabelecimento!");
		this.jtaSobreSistema.setEditable(false);
		this.jpCentro.add(this.jtaSobreSistema);
		this.jpCentro.setLayout(new GridLayout(1, 1));

		this.jpRodape = new JPanel();
		this.jbConfirmaSistema = new JButton("Ok");
		this.jbConfirmaSistema.addActionListener(this);
		this.jpRodape.add(this.jbConfirmaSistema);

		this.jdSobreSistema.getContentPane().setLayout(new BorderLayout());
		this.jdSobreSistema.getContentPane().add(this.jpCentro, BorderLayout.CENTER);
		this.jdSobreSistema.getContentPane().add(this.jpRodape, BorderLayout.SOUTH);

		this.jdSobreSistema.setTitle("Sobre o Sistema");
		this.jdSobreSistema.setSize(300, 300);
		this.jdSobreSistema.setModal(true);
		this.jdSobreSistema.setLocationRelativeTo(null);
		this.jdSobreSistema.setVisible(true);
		this.jbConfirmaSistema.addActionListener(this);
	}

	public void sobreDesenvolvedor() {
		this.jdSobreDesenvolvedor = new JDialog();
		this.jpCentro = new JPanel();
		this.jtaSobreDesenvolvedor = new JTextArea("Desenvolvido Por: Geverson José de Souza"
				+ "\nFormação: Técnico de Informática" + "\nEspecialidade: Desenvolvimento de Sistemas");
		this.jtaSobreDesenvolvedor.setEditable(false);
		this.jpCentro.add(this.jtaSobreDesenvolvedor);
		this.jpCentro.setLayout(new GridLayout(1, 1));

		this.jpRodape = new JPanel();
		this.jbConfirmaDesenvolvedor = new JButton("Ok");
		this.jbConfirmaDesenvolvedor.addActionListener(this);
		this.jpRodape.add(this.jbConfirmaDesenvolvedor);

		this.jdSobreDesenvolvedor.getContentPane().setLayout(new BorderLayout());
		this.jdSobreDesenvolvedor.getContentPane().add(this.jpCentro, BorderLayout.CENTER);
		this.jdSobreDesenvolvedor.getContentPane().add(this.jpRodape, BorderLayout.SOUTH);

		this.jdSobreDesenvolvedor.setTitle("Sobre o Desenvolvedor");
		this.jdSobreDesenvolvedor.setSize(300, 300);
		this.jdSobreDesenvolvedor.setModal(true);
		this.jdSobreDesenvolvedor.setLocationRelativeTo(null);
		this.jdSobreDesenvolvedor.setVisible(true);
		this.jbConfirmaDesenvolvedor.addActionListener(this);
	}

	public void sobreBibliografia() {
		this.jdSobreBibliografia = new JDialog();
		this.jpCentro = new JPanel();
		this.jtaSobreBibliografia = new JTextArea("SisCV - Sistema de Controle de Vendas" + "\n1º: Googele Imagens"
				+ "\n2º: GUJ (Grupo de Usuários Java) ->http://www.guj.com.br/"
				+ "\n3º: Caelum ->https://www.caelum.com.br/" + "\n4º: SQLZOO ->http://sqlzoo.net/");
		this.jtaSobreBibliografia.setEditable(false);
		this.jpCentro.add(this.jtaSobreBibliografia);
		this.jpCentro.setLayout(new GridLayout(1, 1));

		this.jpRodape = new JPanel();
		this.jbConfirmaBibliografia = new JButton("Ok");
		this.jbConfirmaBibliografia.addActionListener(this);
		this.iiConfirmaBibliografia = new ImageIcon(getClass().getResource("/img/button_confirmar.png"));
		this.jbConfirmaBibliografia.setIcon(iiConfirmaBibliografia);
		this.jpRodape.add(this.jbConfirmaBibliografia);

		this.jdSobreBibliografia.getContentPane().setLayout(new BorderLayout());
		this.jdSobreBibliografia.getContentPane().add(this.jpCentro, BorderLayout.CENTER);
		this.jdSobreBibliografia.getContentPane().add(this.jpRodape, BorderLayout.SOUTH);

		this.jdSobreBibliografia.setTitle("Bibliografia");
		this.jdSobreBibliografia.setSize(300, 300);
		this.jdSobreBibliografia.setModal(true);
		this.jdSobreBibliografia.setLocationRelativeTo(null);
		this.jdSobreBibliografia.setVisible(true);
		this.jbConfirmaBibliografia.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbConfirmaDesenvolvedor) {
			this.jdSobreDesenvolvedor.dispose();
		} else if (e.getSource() == jbConfirmaSistema) {
			this.jdSobreSistema.dispose();
		} else if (e.getSource() == jbConfirmaBibliografia) {
			this.jdSobreBibliografia.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Nada implementado para esta ação.", "Ação negada",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}