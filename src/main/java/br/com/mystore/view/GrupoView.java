package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import br.com.mystore.api.controller.CidadeController;
import br.com.mystore.api.controller.GrupoController;
import br.com.mystore.api.controller.PermissaoController;
import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.CidadeModel;
import br.com.mystore.api.model.EstadoModel;
import br.com.mystore.api.model.PermissaoModel;
import br.com.mystore.api.model.input.GrupoInput;
import br.com.mystore.utils.TabelaModeloObjeto;

public class GrupoView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome;
	private JTextField jtfId, jtfNome;
	private JComboBox<CidadeModel> jcbCidades;
	private JComboBox<EstadoModel> jcbEstados;
	private JButton jbAlterar, jbCancelar, jbBuscarConfirma, jbSalvar, jbBuscar, jbRefresh, jbAdicionar, jbApagar,
			jbPermissoes, jbAlterarPermissoes, jbAdicionarPermicao, jbRemoverPermicao;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpFormulario, jpCenter;
	private JTable jtGrupos;
	private String token;
	private CidadeController cidadeController;
	private JDialog jdDadosDaGrupo;
	private Window owner;
	private JList<PermissaoModel> listPermissoes;
	private JList<PermissaoModel> listPermissoesGrupo;
	private DefaultListModel<PermissaoModel> modelPemissoesAusentes;
	private DefaultListModel<PermissaoModel> modelPermissoesExistente;
	private GrupoController grupoController;
	private PermissaoController permissaoController;

	public GrupoView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.cidadeController = new CidadeController();
		this.grupoController = new GrupoController();
		this.permissaoController = new PermissaoController();
	}

	public void dadosDaGrupo(String id, Object object) {

		this.jdDadosDaGrupo = new JDialog((Frame) this.owner);

		if (jpFormulario != null)
			jpFormulario.removeAll();
		jpFormulario = new JPanel();
		jpCenter = new JPanel();
		jpCenter.removeAll();
		var jpRodape = new JPanel();
		jpRodape.removeAll();
		// 1
		jlId = new JLabel("Código: ");
		jlId.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlId);
		jtfId = new JTextField();
		jtfId.setEnabled(false);
		jpFormulario.add(jtfId);

		// 2
		jlNome = new JLabel("Nome: ");
		jlNome.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlNome);
		jtfNome = new JTextField();
		jpFormulario.add(jtfNome);

		this.jbCancelar = new JButton("Cancelar");
		jpRodape.add(jbCancelar);
		this.jbCancelar.addActionListener(this);

		var titulo = "";
		var larguraDaTela = 350;
		var alturaDaTela = 130;
		var quantidadeDeLinhasDoGrid = 2;
		if (id == null && object == jbAdicionar) {
			titulo = "Cadastrar Grupo";
			this.jbSalvar = new JButton("Salvar");
			jpRodape.add(jbSalvar);
			this.jbSalvar.addActionListener(this);
		} else if (object == jbPermissoes) {
			titulo = "Permissões do Grupo";
			larguraDaTela = 700;
			alturaDaTela = 350;
			quantidadeDeLinhasDoGrid = 3;
			jpFormulario.add(new JLabel("Disponíveis"));
			jpFormulario.add(new JLabel("Utilizadas"));

			listPermissoes = new JList<PermissaoModel>();
			jpCenter.add(new JScrollPane(listPermissoes));
			this.modelPemissoesAusentes = new DefaultListModel<PermissaoModel>();
			var todasPemissoes = permissaoController.todasPermissoes(this.token);

			listPermissoesGrupo = new JList<PermissaoModel>();
			jpCenter.add(new JScrollPane(listPermissoesGrupo));
			this.modelPermissoesExistente = new DefaultListModel<PermissaoModel>();
			var permissoesExistentes = grupoController.permissoesDoGrupoPorId(this.token, id);

			if (permissoesExistentes != null) {
				this.modelPermissoesExistente.addAll(permissoesExistentes);
				todasPemissoes.forEach(permissao -> {
					if (!permissoesExistentes.contains(permissao))
						this.modelPemissoesAusentes.addElement(permissao);
				});
			} else
				this.modelPemissoesAusentes.addAll(todasPemissoes);

			listPermissoes.setModel(modelPemissoesAusentes);
			listPermissoesGrupo.setModel(modelPermissoesExistente);

			jpCenter.setLayout(new GridLayout(1, 2));

			var grupo = grupoController.grupoPorId(token, id);
			jtfId.setText(String.valueOf(grupo.getId()));
			jtfNome.setText(grupo.getNome());

			this.jbAdicionarPermicao = new JButton("Adicionar");
			jpRodape.add(jbAdicionarPermicao);
			this.jbAdicionarPermicao.addActionListener(this);

			this.jbRemoverPermicao = new JButton("Remover");
			jpRodape.add(jbRemoverPermicao);
			this.jbRemoverPermicao.addActionListener(this);

			this.jbAlterarPermissoes = new JButton("Alterar");
			jpRodape.add(jbAlterarPermissoes);
			this.jbAlterarPermissoes.addActionListener(this);

		} else {
			titulo = "Alterar Grupo";
			var grupo = grupoController.grupoPorId(token, id);
			jtfId.setText(String.valueOf(grupo.getId()));
			jtfNome.setText(grupo.getNome());
			this.jbAlterar = new JButton("Alterar");
			jpRodape.add(jbAlterar);
			this.jbAlterar.addActionListener(this);
		}

		this.jpFormulario.setLayout(new GridLayout(quantidadeDeLinhasDoGrid, 2));

		this.jdDadosDaGrupo.getContentPane().setLayout(new BorderLayout());
		this.jdDadosDaGrupo.getContentPane().add(this.jpFormulario, BorderLayout.NORTH);
		if (jpCenter.getComponents().length > 0) {
			listPermissoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPermissoesGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.jdDadosDaGrupo.getContentPane().add(jpCenter, BorderLayout.CENTER);
		}
		this.jdDadosDaGrupo.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		this.jdDadosDaGrupo.setTitle(titulo);
		this.jdDadosDaGrupo.setSize(larguraDaTela, alturaDaTela);
		this.jdDadosDaGrupo.setModal(true);
		this.jdDadosDaGrupo.setLocationRelativeTo(null);
		this.jdDadosDaGrupo.setVisible(true);
	}

	private void carregarDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		var colunas = new String[] { "Código", "Nome" };
		var grupos = grupoController.todasGrupos(this.token);
		var dados = new String[grupos.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(grupos.get(linha).getId());
			dados[linha][1] = String.valueOf(grupos.get(linha).getNome());
		}
		var dadosDaTabela = new TabelaModeloObjeto(dados, colunas);
		jtGrupos = new JTable(dadosDaTabela);
		jtGrupos.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtGrupos.getColumnModel().getColumn(1).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtGrupos));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtGrupos.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Grupos Cadastradas");
		jifListar.setClosable(true);
		jifListar.setIconifiable(true);
		jifListar.setMaximizable(true);

		jpBotoesCRUD = new JPanel();

		jbAdicionar = new JButton("Cadastrar");
		jpBotoesCRUD.add(jbAdicionar);
		jbAdicionar.addActionListener(this);

		jbBuscar = new JButton("Buscar");
		jbBuscar.setEnabled(false);
		jpBotoesCRUD.add(jbBuscar);
		jbBuscar.addActionListener(this);

		jbRefresh = new JButton("Atualizar");
		jpBotoesCRUD.add(jbRefresh);
		jbRefresh.addActionListener(this);

		jbApagar = new JButton("Apagar");
		jbApagar.setEnabled(false);
		jpBotoesCRUD.add(jbApagar);
		jbApagar.addActionListener(this);

		jbPermissoes = new JButton("Permissões");
		jbPermissoes.setEnabled(false);
		jpBotoesCRUD.add(jbPermissoes);
		jbPermissoes.addActionListener(this);

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
					JOptionPane.showMessageDialog(jdDadosDaGrupo, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarGrupo();

			} else if (e.getSource() == jbCancelar) {
				limparDados();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDaGrupo, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarGrupo();

			} else if (e.getSource() == jbBuscar) {
				// buscar();
			} else if (e.getSource() == jbBuscarConfirma) {
				// buttonBuscarConfirma();
			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDaGrupo(null, e.getSource());
			} else if (e.getSource() == jbApagar) {
				if (JOptionPane.showConfirmDialog(jifListar,
						"Tem certeza que deseja apagar este grupo? certifique-se de que não há usuário vinculado.",
						"Apagando...", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
					if (apagarGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString()))
						JOptionPane.showMessageDialog(jifListar, "Grupo apagado com sucesso!", "Alteração Realizada",
								JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getSource() == jcbEstados) {
				jcbCidades.removeAllItems();
				// Provisório
				if (jcbEstados.getSelectedItem() != null)
					cidadeController.todasCidades(this.token).forEach(cidade -> {
						if (((EstadoModel) jcbEstados.getSelectedItem()).getId() == cidade.getEstado().getId())
							jcbCidades.addItem(cidade);
					});
				jcbCidades.setSelectedIndex(-1);
			} else if (e.getSource() == jbPermissoes) {
				dadosDaGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString(), e.getSource());
			} else if (e.getSource() == jbAdicionarPermicao) {
				var objetoParaAdicionar = listPermissoes.getSelectedValue();
				if (objetoParaAdicionar != null) {
					modelPemissoesAusentes.removeElement(objetoParaAdicionar);
					listPermissoes.setModel(modelPemissoesAusentes);
					modelPermissoesExistente.addElement(objetoParaAdicionar);
					listPermissoesGrupo.setModel(modelPermissoesExistente);
					objetoParaAdicionar = null;
				} else
					JOptionPane.showMessageDialog(jifListar, "Selecione uma permissão!", "Permissão não selecionada",
							JOptionPane.WARNING_MESSAGE);
			} else if (e.getSource() == jbRemoverPermicao) {
				var objetoParaRemover = listPermissoesGrupo.getSelectedValue();
				if (objetoParaRemover != null) {
					modelPermissoesExistente.removeElement(objetoParaRemover);
					listPermissoesGrupo.setModel(modelPermissoesExistente);
					modelPemissoesAusentes.addElement(objetoParaRemover);
					listPermissoes.setModel(modelPemissoesAusentes);
					objetoParaRemover = null;
				} else
					JOptionPane.showMessageDialog(jifListar, "Selecione uma permissão!", "Permissão não selecionada",
							JOptionPane.WARNING_MESSAGE);
			} else if (e.getSource() == jbAlterarPermissoes) {
				var adicionarPermissoes = new ArrayList<PermissaoModel>();
				for (int i = 0; i < modelPermissoesExistente.getSize(); i++)
					adicionarPermissoes.add(modelPermissoesExistente.get(i));
				this.jdDadosDaGrupo.dispose();
				grupoController.adicionarPermissoes(token, adicionarPermissoes, jtfId.getText());
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

	private boolean apagarGrupo(String id) {
		return grupoController.apagarGrupoPorId(token, id);
	}

	private void limparDados() {
		jtfId.setText("");
		jtfNome.setText("");
		jtfNome.requestFocus();
	}

	private void salvarGrupo() {
		var grupo = new GrupoInput();
		grupo.setNome(jtfNome.getText());
		var grupoCadastrada = grupoController.cadastrar(token, grupo);
		if (grupoCadastrada != null) {
			jtfId.setText(String.valueOf(grupoCadastrada.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDaGrupo, "Grupo cadastrada com sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDaGrupo.dispose();
		}
	}

	private void alterarGrupo() {
		var grupo = new GrupoInput();
		grupo.setNome(jtfNome.getText());
		var grupoCadastrada = this.grupoController.alterar(token, grupo, jtfId.getText());
		if (grupoCadastrada != null) {
			jtfId.setText(String.valueOf(grupoCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDaGrupo, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDaGrupo.dispose();
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
			if (e.getSource() == jtGrupos && jtGrupos.getSelectedRow() != -1) {
				jbApagar.setEnabled(true);
				jbPermissoes.setEnabled(true);
				if (e.getClickCount() == 2)
					dadosDaGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString(), jtGrupos);
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
