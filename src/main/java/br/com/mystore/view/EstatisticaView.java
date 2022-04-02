package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import br.com.mystore.api.exception.ApiException;

public class EstatisticaView extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JButton jbBuscar, jbRefresh, jbAdicionar;
	private JPanel jpBotoesCRUD, jpListaDeDados;

	public EstatisticaView(String token) {
	}

	private void atualizar() {
		jpListaDeDados.removeAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setSize(800, 300);
		jifListar.setTitle("Estatísticas");
		jifListar.setClosable(true);
		jifListar.setIconifiable(true);
		jifListar.setMaximizable(true);

		jpBotoesCRUD = new JPanel();

		jbBuscar = new JButton("Buscar");
		jbBuscar.setEnabled(false);
		jpBotoesCRUD.add(jbBuscar);
		jbBuscar.addActionListener(this);

		jbRefresh = new JButton("Atualizar");
		jpBotoesCRUD.add(jbRefresh);
		jbRefresh.addActionListener(this);

		jpListaDeDados = new JPanel();
		DefaultPieDataset pieDataset = new DefaultPieDataset();

		pieDataset.setValue("A", 75);
		pieDataset.setValue("B", 10);
		pieDataset.setValue("C", 10);
		pieDataset.setValue("D", 10);

		JFreeChart grafico = ChartFactory.createPieChart("Titulo Do Grafico", // Titulo do grafico
				pieDataset, // DataSet
				true, // Para mostrar ou não a legenda
				true, // Para mostrar ou não os tooltips
				false);

		jpListaDeDados.add(new ChartPanel(grafico));
		jpListaDeDados.setLayout(new GridLayout(1, 1));

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpBotoesCRUD, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListaDeDados, BorderLayout.CENTER);
		return jifListar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbAdicionar) {

			} else if (e.getSource() == jbBuscar) {

			} else if (e.getSource() == jbRefresh) {
				atualizar();
			} else {
				JOptionPane.showMessageDialog(jifListar, "Ação desconecida nada foi implementado!", "Vazio...",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (ApiException aex) {
			aex.printStackTrace();
			JOptionPane.showMessageDialog(jifListar, aex.getProblema().getUserMessage(), aex.getProblema().getTitle(),
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(jifListar, "Detalhes do erro:" + ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
