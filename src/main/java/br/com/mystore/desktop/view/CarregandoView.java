package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CarregandoView extends Thread {

	private JDialog jd;
	private JPanel jpTopo;
	private ImageIcon iiCarregando;
	private JLabel jlImagemCarregando;

	public CarregandoView(Component component) {
		if (component == null)
			jd = new JDialog();
		else
			jd = new JDialog(SwingUtilities.getWindowAncestor(component));

		jpTopo = new JPanel();
		jlImagemCarregando = new JLabel();
		jlImagemCarregando.setToolTipText("My Store");
		iiCarregando = new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/carregando.gif"));
		jlImagemCarregando.setIcon(iiCarregando);
		jpTopo.add(jlImagemCarregando);

		jd.getContentPane().setLayout(new BorderLayout());
		jd.getContentPane().add(jpTopo, BorderLayout.NORTH);
		jd.setTitle("Agurade...");
		jd.setSize(300, 150);
		jd.setModal(true);
		jd.setLocationRelativeTo(null);
	}

	@Override
	public void run() {
		jd.setVisible(true);
	}

	@Override
	public void interrupt() {
		jd.setVisible(false);
	}
}