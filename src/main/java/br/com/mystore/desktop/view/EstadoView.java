package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import br.com.mystore.desktop.api.controller.EstadoController;
import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.input.EstadoInput;
import br.com.mystore.desktop.utils.TabelaModeloObjeto;

public class EstadoView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome;
	private JTextField jtfId, jtfNome;
	private JButton jbAlterar, jbCancelar, jbSalvar, jbRefresh, jbAdicionar, jbApagar;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpFormulario, jpCenter;
	private JTable jtEstados;
	private String token;
	private JDialog jdDadosDoEstado;
	private Window owner;
	private EstadoController estadoController;

	public EstadoView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.estadoController = new EstadoController();
	}

	public void dadosDoEstado(String id, Object object) {

		jdDadosDoEstado = new JDialog((Frame) owner);

		if (jpFormulario != null)
			jpFormulario.removeAll();
		jpFormulario = new JPanel();
		jpCenter = new JPanel();
		jpCenter.removeAll();
		var jpRodape = new JPanel();
		jpRodape.removeAll();

		jlId = new JLabel("Código: ");
		jlId.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlId);
		jtfId = new JTextField();
		jtfId.setEnabled(false);
		jpFormulario.add(jtfId);

		jlNome = new JLabel("Nome: ");
		jlNome.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlNome);
		jtfNome = new JTextField();
		jpFormulario.add(jtfNome);

		jbCancelar = new JButton("Cancelar");
		jpRodape.add(jbCancelar);
		jbCancelar.addActionListener(this);

		var titulo = "";
		var larguraDaTela = 350;
		var alturaDaTela = 130;
		var quantidadeDeLinhasDoGrid = 2;
		if (id == null && object == jbAdicionar) {
			titulo = "Cadastrar Estado";
			jbSalvar = new JButton("Salvar");
			jpRodape.add(jbSalvar);
			jbSalvar.addActionListener(this);
		} else {
			titulo = "Alterar Estado";
			var estado = estadoController.estadoPorId(token, id);
			jtfId.setText(String.valueOf(estado.getId()));
			jtfNome.setText(estado.getNome());
			jbAlterar = new JButton("Alterar");
			jpRodape.add(jbAlterar);
			jbAlterar.addActionListener(this);
		}

		jpFormulario.setLayout(new GridLayout(quantidadeDeLinhasDoGrid, 2));

		jdDadosDoEstado.getContentPane().setLayout(new BorderLayout());
		jdDadosDoEstado.getContentPane().add(jpFormulario, BorderLayout.NORTH);
		jdDadosDoEstado.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		jdDadosDoEstado.setTitle(titulo);
		jdDadosDoEstado.setSize(larguraDaTela, alturaDaTela);
		jdDadosDoEstado.setModal(true);
		jdDadosDoEstado.setLocationRelativeTo(null);
		jdDadosDoEstado.setVisible(true);
	}

	private void carregarDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		var colunas = new String[] { "Código", "Nome" };
		var estados = estadoController.todosEstados(this.token);
		var dados = new String[estados.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(estados.get(linha).getId());
			dados[linha][1] = String.valueOf(estados.get(linha).getNome());
		}
		var dadosDaTabela = new TabelaModeloObjeto(dados, colunas);
		jtEstados = new JTable(dadosDaTabela);
		jtEstados.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEstados.getColumnModel().getColumn(1).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtEstados));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtEstados.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Estados Cadastradas");
		jifListar.setClosable(true);
		jifListar.setIconifiable(true);
		jifListar.setMaximizable(true);

		jpBotoesCRUD = new JPanel();

		jbAdicionar = new JButton("Cadastrar");
		jpBotoesCRUD.add(jbAdicionar);
		jbAdicionar.addActionListener(this);

		jbRefresh = new JButton("Atualizar");
		jpBotoesCRUD.add(jbRefresh);
		jbRefresh.addActionListener(this);

		jbApagar = new JButton("Apagar");
		jbApagar.setEnabled(false);
		jpBotoesCRUD.add(jbApagar);
		jbApagar.addActionListener(this);

		carregarDados();

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpBotoesCRUD, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListaDeDados, BorderLayout.CENTER);
		jifListar.pack();
		return jifListar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbSalvar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoEstado, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarEstado();

			} else if (e.getSource() == jbCancelar) {
				jdDadosDoEstado.dispose();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoEstado, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarEstado();

			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDoEstado(null, e.getSource());
			} else if (e.getSource() == jbApagar) {
				if (JOptionPane.showConfirmDialog(jifListar,
						"Tem certeza que deseja apagar este estado? certifique-se de que não há usuário vinculado.",
						"Apagando...", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
					if (apagarEstado(jtEstados.getValueAt(jtEstados.getSelectedRow(), 0).toString()))
						JOptionPane.showMessageDialog(jifListar, "Estado apagado com sucesso!", "Alteração Realizada",
								JOptionPane.INFORMATION_MESSAGE);
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

	private boolean apagarEstado(String id) {
		return estadoController.apagarEstadoPorId(token, Integer.parseInt(id));
	}

	private void salvarEstado() {
		var estado = new EstadoInput();
		estado.setNome(jtfNome.getText());
		var estadoCadastrado = estadoController.cadastrar(token, estado);
		if (estadoCadastrado != null) {
			jtfId.setText(String.valueOf(estadoCadastrado.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoEstado, "Estado cadastrado com sucesso!", "Operação Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoEstado.dispose();
		}
	}

	private void alterarEstado() {
		var estado = new EstadoInput();
		estado.setNome(jtfNome.getText());
		var estadoCadastrada = this.estadoController.alterar(token, estado, Integer.parseInt(jtfId.getText()));
		if (estadoCadastrada != null) {
			jtfId.setText(String.valueOf(estadoCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoEstado, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoEstado.dispose();
		}
	}

	private String criticas() {
		if (jtfNome.getText().equals(""))
			return "Campo 'Nome' é obrigatório!";
		else
			return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == jtEstados && jtEstados.getSelectedRow() != -1) {
				jbApagar.setEnabled(true);
				if (e.getClickCount() == 2)
					dadosDoEstado(jtEstados.getValueAt(jtEstados.getSelectedRow(), 0).toString(), jtEstados);
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
