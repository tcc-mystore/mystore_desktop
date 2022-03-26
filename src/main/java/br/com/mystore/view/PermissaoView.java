package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.mystore.api.controller.PermissaoController;
import br.com.mystore.utils.TabelaModeloObjeto;

public class PermissaoView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JButton jbBuscar, jbRefresh, jbAdicionar;
	private JPanel jpBotoesCRUD, jpListaDeDados;
	private JTable jtEmpresas;
	private String token;
	private PermissaoController permissaoController;

	public PermissaoView(String token) {
		this.token = token;
	}

	private void atualizar() {
		jpListaDeDados.removeAll();
		jtEmpresas = new JTable(dadosDaListagem());
		jpListaDeDados.add(new JScrollPane(jtEmpresas));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtEmpresas.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setSize(800, 300);
		jifListar.setTitle("Empresas Cadastradas");
		jifListar.setClosable(true);
		jifListar.setIconifiable(true);
		jifListar.setMaximizable(true);

		jpBotoesCRUD = new JPanel();

		jbAdicionar = new JButton("Cadastrar");
		jbAdicionar.setEnabled(false);
		jpBotoesCRUD.add(jbAdicionar);
		jbAdicionar.addActionListener(this);

		jbBuscar = new JButton("Buscar");
		jbBuscar.setEnabled(false);
		jpBotoesCRUD.add(jbBuscar);
		jbBuscar.addActionListener(this);

		jbRefresh = new JButton("Atualizar");
		jpBotoesCRUD.add(jbRefresh);
		jbRefresh.addActionListener(this);

		jpListaDeDados = new JPanel();
		jtEmpresas = new JTable(dadosDaListagem());
		jtEmpresas.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEmpresas.getColumnModel().getColumn(1).setPreferredWidth(300);
		jtEmpresas.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtEmpresas.addMouseListener(this);
		jpListaDeDados.add(new JScrollPane(jtEmpresas));
		jpListaDeDados.setLayout(new GridLayout(1, 1));

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpBotoesCRUD, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListaDeDados, BorderLayout.CENTER);
		return jifListar;
	}

	private TabelaModeloObjeto dadosDaListagem() {
		permissaoController = new PermissaoController();
		var colunas = new String[] { "Código", "Descrição", "Nome Técnico" };
		var permissoes = permissaoController.todasPermissoes(this.token);
		var dados = new String[permissoes.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(permissoes.get(linha).getId());
			dados[linha][1] = permissoes.get(linha).getDescricao();
			dados[linha][2] = permissoes.get(linha).getNome();
		}
		return new TabelaModeloObjeto(dados, colunas);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
