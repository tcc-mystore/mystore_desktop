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
import javax.swing.JComboBox;
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

import br.com.mystore.desktop.api.controller.CidadeController;
import br.com.mystore.desktop.api.controller.EstadoController;
import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.EstadoModel;
import br.com.mystore.desktop.api.model.input.CidadeInput;
import br.com.mystore.desktop.api.model.input.EstadoIdInput;
import br.com.mystore.desktop.utils.TabelaModeloObjeto;

public class CidadeView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome, jlEstado;
	private JTextField jtfId, jtfNome;
	private JComboBox<EstadoModel> jcbEstados;
	private JButton jbAlterar, jbCancelar, jbSalvar, jbRefresh, jbAdicionar, jbApagar;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpFormulario, jpCenter;
	private JTable jtCidades;
	private String token;
	private JDialog jdDadosDoCidade;
	private Window owner;
	private CidadeController cidadeController;
	private EstadoController estadoController;

	public CidadeView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.cidadeController = new CidadeController();
		this.estadoController = new EstadoController();
	}

	public void dadosDoCidade(String id, Object object) {

		jdDadosDoCidade = new JDialog((Frame) owner);

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

		jlEstado = new JLabel("Estado: ");
		jlEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlEstado);
		jcbEstados = new JComboBox<EstadoModel>();
		estadoController.todosEstados(token).forEach(estado -> jcbEstados.addItem(estado));
		jcbEstados.setSelectedIndex(-1);
		jpFormulario.add(jcbEstados);

		jbCancelar = new JButton("Cancelar");
		jpRodape.add(jbCancelar);
		jbCancelar.addActionListener(this);

		var titulo = "";
		var larguraDaTela = 350;
		var alturaDaTela = 150;
		var quantidadeDeLinhasDoGrid = 3;
		if (id == null && object == jbAdicionar) {
			titulo = "Cadastrar Cidade";
			jbSalvar = new JButton("Salvar");
			jpRodape.add(jbSalvar);
			jbSalvar.addActionListener(this);
		} else {
			titulo = "Alterar Cidade";
			var cidade = cidadeController.cidadePorId(token, Integer.parseInt(id));
			jtfId.setText(String.valueOf(cidade.getId()));
			jtfNome.setText(cidade.getNome());
			jcbEstados.getModel().setSelectedItem(cidade.getEstado());
			jbAlterar = new JButton("Alterar");
			jpRodape.add(jbAlterar);
			jbAlterar.addActionListener(this);
		}

		jpFormulario.setLayout(new GridLayout(quantidadeDeLinhasDoGrid, 2));

		jdDadosDoCidade.getContentPane().setLayout(new BorderLayout());
		jdDadosDoCidade.getContentPane().add(jpFormulario, BorderLayout.NORTH);
		jdDadosDoCidade.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		jdDadosDoCidade.setTitle(titulo);
		jdDadosDoCidade.setSize(larguraDaTela, alturaDaTela);
		jdDadosDoCidade.setModal(true);
		jdDadosDoCidade.setLocationRelativeTo(null);
		jdDadosDoCidade.setVisible(true);
	}

	private void carregarDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		var colunas = new String[] { "Código", "Nome" };
		var cidades = cidadeController.todasCidades(this.token);
		var dados = new String[cidades.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(cidades.get(linha).getId());
			dados[linha][1] = String.valueOf(cidades.get(linha).getNome());
		}
		var dadosDaTabela = new TabelaModeloObjeto(dados, colunas);
		jtCidades = new JTable(dadosDaTabela);
		jtCidades.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtCidades.getColumnModel().getColumn(1).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtCidades));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtCidades.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Cidades Cadastradas");
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
					JOptionPane.showMessageDialog(jdDadosDoCidade, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarCidade();

			} else if (e.getSource() == jbCancelar) {
				jdDadosDoCidade.dispose();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoCidade, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarCidade();

			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDoCidade(null, e.getSource());
			} else if (e.getSource() == jbApagar) {
				if (JOptionPane.showConfirmDialog(jifListar,
						"Tem certeza que deseja apagar este cidade? certifique-se de que não há usuário vinculado.",
						"Apagando...", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
					if (apagarCidade(jtCidades.getValueAt(jtCidades.getSelectedRow(), 0).toString()))
						JOptionPane.showMessageDialog(jifListar, "Cidade apagado com sucesso!", "Alteração Realizada",
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

	private boolean apagarCidade(String id) {
		return cidadeController.apagarCidadePorId(token, Integer.parseInt(id));
	}

	private void salvarCidade() {
		var estado = new EstadoIdInput();
		estado.setId(((EstadoModel) jcbEstados.getSelectedItem()).getId());
		var cidade = new CidadeInput();
		cidade.setNome(jtfNome.getText());
		cidade.setEstado(estado);
		var cidadeCadastrado = cidadeController.cadastrar(token, cidade);
		if (cidadeCadastrado != null) {
			jtfId.setText(String.valueOf(cidadeCadastrado.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoCidade, "Cidade cadastrado com sucesso!", "Operação Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoCidade.dispose();
		}
	}

	private void alterarCidade() {
		var estado = new EstadoIdInput();
		estado.setId(((EstadoModel) jcbEstados.getSelectedItem()).getId());
		var cidade = new CidadeInput();
		cidade.setNome(jtfNome.getText());
		cidade.setEstado(estado);
		var cidadeCadastrada = this.cidadeController.alterar(token, cidade, Integer.parseInt(jtfId.getText()));
		if (cidadeCadastrada != null) {
			jtfId.setText(String.valueOf(cidadeCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoCidade, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoCidade.dispose();
		}
	}

	private String criticas() {
		if (jtfNome.getText().equals(""))
			return "Campo 'Nome' é obrigatório!";
		else if (jcbEstados.getSelectedIndex() == -1)
			return "Selecione um 'Estado'!";
		else
			return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == jtCidades && jtCidades.getSelectedRow() != -1) {
				jbApagar.setEnabled(true);
				if (e.getClickCount() == 2)
					dadosDoCidade(jtCidades.getValueAt(jtCidades.getSelectedRow(), 0).toString(), jtCidades);
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
