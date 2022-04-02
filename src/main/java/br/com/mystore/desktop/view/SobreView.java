package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SobreView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JDialog jdSobreSistema;
	private JPanel jpCentro, jpRodape;
	private JButton jbConfirmaSistema;
	private JTextArea jtaSobreSistema;

	public SobreView() {
		sobreSistema();
	}

	public void sobreSistema() {
		jdSobreSistema = new JDialog();
		jpCentro = new JPanel();
		jtaSobreSistema = new JTextArea("Nome do sistema: My Store" + "\nVersão 1.0.0\nAutor: Geverson José de Souza"
				+ "\nTodos os direitos reservados"
				+ "\nEsse sistema gerencia as vendas,\n as entregas e o caixa do estabelecimento!");
		jtaSobreSistema.setEditable(false);
		jpCentro.add(jtaSobreSistema);
		jpCentro.setLayout(new GridLayout(1, 1));

		jpRodape = new JPanel();
		jbConfirmaSistema = new JButton("Ok");
		jbConfirmaSistema.addActionListener(this);
		jpRodape.add(jbConfirmaSistema);

		jdSobreSistema.getContentPane().setLayout(new BorderLayout());
		jdSobreSistema.getContentPane().add(jpCentro, BorderLayout.CENTER);
		jdSobreSistema.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		jdSobreSistema.setTitle("Sobre o Sistema");
		jdSobreSistema.setSize(300, 300);
		jdSobreSistema.setModal(true);
		jdSobreSistema.setLocationRelativeTo(null);
		jdSobreSistema.setVisible(true);
		jbConfirmaSistema.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbConfirmaSistema) {
			this.jdSobreSistema.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Nada implementado para esta ação.", "Ação negada",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}