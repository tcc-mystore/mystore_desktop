package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

import br.com.mystore.desktop.api.controller.GrupoController;
import br.com.mystore.desktop.api.controller.PermissaoController;
import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.PermissaoModel;
import br.com.mystore.desktop.api.model.input.GrupoInput;
import br.com.mystore.desktop.utils.TabelaModeloObjeto;

public class GrupoView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome;
	private JTextField jtfId, jtfNome;
	private JButton jbAlterar, jbCancelar, jbSalvar, jbRefresh, jbAdicionar, jbApagar, jbPermissoes,
			jbAdicionarPermicao, jbRemoverPermicao;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpFormulario, jpCenter;
	private JTable jtGrupos;
	private String token;
	private JDialog jdDadosDoGrupo;
	private Window owner;
	private JList<PermissaoModel> listPermissoes;
	private JList<PermissaoModel> listPermissoesGrupo;
	private DefaultListModel<PermissaoModel> modelPemissoesAusentes;
	private DefaultListModel<PermissaoModel> modelPermissoesExistente;
	private GrupoController grupoController;
	private PermissaoController permissaoController;
	private List<PermissaoModel> permissaoModelAusentes;
	private List<PermissaoModel> permissaoModelExistentes;

	public GrupoView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.grupoController = new GrupoController();
		this.permissaoController = new PermissaoController();
	}

	public void dadosDoGrupo(String id, Object object) {

		jdDadosDoGrupo = new JDialog((Frame) owner);

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
			titulo = "Cadastrar Grupo";
			jbSalvar = new JButton("Salvar");
			jpRodape.add(jbSalvar);
			jbSalvar.addActionListener(this);
		} else if (object == jbPermissoes) {
			titulo = "Permissões do Grupo";
			larguraDaTela = 700;
			alturaDaTela = 350;
			quantidadeDeLinhasDoGrid = 3;
			jpFormulario.add(new JLabel("Disponíveis"));
			jpFormulario.add(new JLabel("Utilizadas"));

			listPermissoes = new JList<PermissaoModel>();
			jpCenter.add(new JScrollPane(listPermissoes));
			modelPemissoesAusentes = new DefaultListModel<PermissaoModel>();
			permissaoModelAusentes = permissaoController.todasPermissoes(token);

			listPermissoesGrupo = new JList<PermissaoModel>();
			jpCenter.add(new JScrollPane(listPermissoesGrupo));
			modelPermissoesExistente = new DefaultListModel<PermissaoModel>();
			permissaoModelExistentes = grupoController.permissoesDoGrupoPorId(token, id);

			if (permissaoModelExistentes != null) {
				modelPermissoesExistente.addAll(permissaoModelExistentes);
				permissaoModelAusentes.forEach(permissao -> {
					if (!permissaoModelExistentes.contains(permissao))
						modelPemissoesAusentes.addElement(permissao);
				});
			} else
				modelPemissoesAusentes.addAll(permissaoModelAusentes);

			listPermissoes.setModel(modelPemissoesAusentes);
			listPermissoesGrupo.setModel(modelPermissoesExistente);

			jpCenter.setLayout(new GridLayout(1, 2));

			var grupo = grupoController.grupoPorId(token, id);
			jtfId.setText(String.valueOf(grupo.getId()));
			jtfNome.setText(grupo.getNome());

			jbAdicionarPermicao = new JButton("Adicionar");
			jpRodape.add(jbAdicionarPermicao);
			jbAdicionarPermicao.addActionListener(this);

			jbRemoverPermicao = new JButton("Remover");
			jpRodape.add(jbRemoverPermicao);
			jbRemoverPermicao.addActionListener(this);

		} else {
			titulo = "Alterar Grupo";
			var grupo = grupoController.grupoPorId(token, id);
			jtfId.setText(String.valueOf(grupo.getId()));
			jtfNome.setText(grupo.getNome());
			jbAlterar = new JButton("Alterar");
			jpRodape.add(jbAlterar);
			jbAlterar.addActionListener(this);
		}

		jpFormulario.setLayout(new GridLayout(quantidadeDeLinhasDoGrid, 2));

		jdDadosDoGrupo.getContentPane().setLayout(new BorderLayout());
		jdDadosDoGrupo.getContentPane().add(jpFormulario, BorderLayout.NORTH);
		if (jpCenter.getComponents().length > 0) {
			listPermissoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPermissoesGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jdDadosDoGrupo.getContentPane().add(jpCenter, BorderLayout.CENTER);
		}
		jdDadosDoGrupo.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		jdDadosDoGrupo.setTitle(titulo);
		jdDadosDoGrupo.setSize(larguraDaTela, alturaDaTela);
		jdDadosDoGrupo.setModal(true);
		jdDadosDoGrupo.setLocationRelativeTo(null);
		jdDadosDoGrupo.setVisible(true);
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
					JOptionPane.showMessageDialog(jdDadosDoGrupo, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarGrupo();

			} else if (e.getSource() == jbCancelar) {
				jdDadosDoGrupo.dispose();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoGrupo, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarGrupo();

			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDoGrupo(null, e.getSource());
			} else if (e.getSource() == jbApagar) {
				if (JOptionPane.showConfirmDialog(jifListar,
						"Tem certeza que deseja apagar este grupo? certifique-se de que não há usuário vinculado.",
						"Apagando...", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
					if (apagarGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString()))
						JOptionPane.showMessageDialog(jifListar, "Grupo apagado com sucesso!", "Alteração Realizada",
								JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getSource() == jbPermissoes) {
				dadosDoGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString(), e.getSource());
			} else if (e.getSource() == jbAdicionarPermicao) {
				var objetoParaAdicionar = listPermissoes.getSelectedValue();
				if (objetoParaAdicionar != null) {
					var permissaoAdicionada = grupoController.adicionarPermissao(token, objetoParaAdicionar,
							jtfId.getText());
					if (!permissaoAdicionada)
						JOptionPane.showMessageDialog(jdDadosDoGrupo,
								"Não foi possível adicionar a permissão, tente novamente!", "Atenção",
								JOptionPane.WARNING_MESSAGE);
					modelPemissoesAusentes.removeElement(objetoParaAdicionar);
					listPermissoes.setModel(modelPemissoesAusentes);
					modelPermissoesExistente.addElement(objetoParaAdicionar);
					listPermissoesGrupo.setModel(modelPermissoesExistente);
					objetoParaAdicionar = null;
				} else
					JOptionPane.showMessageDialog(jdDadosDoGrupo, "Selecione uma permissão!",
							"Permissão não selecionada", JOptionPane.WARNING_MESSAGE);
			} else if (e.getSource() == jbRemoverPermicao) {
				var objetoParaRemover = listPermissoesGrupo.getSelectedValue();
				if (objetoParaRemover != null) {
					var permissaoRemovida = grupoController.removerPermissao(token, objetoParaRemover, jtfId.getText());
					if (!permissaoRemovida)
						JOptionPane.showMessageDialog(jdDadosDoGrupo,
								"Não foi possível remover a permissão, tente novamente!", "Atenção",
								JOptionPane.WARNING_MESSAGE);
					modelPermissoesExistente.removeElement(objetoParaRemover);
					listPermissoesGrupo.setModel(modelPermissoesExistente);
					modelPemissoesAusentes.addElement(objetoParaRemover);
					listPermissoes.setModel(modelPemissoesAusentes);
					objetoParaRemover = null;
				} else
					JOptionPane.showMessageDialog(jdDadosDoGrupo, "Selecione uma permissão!",
							"Permissão não selecionada", JOptionPane.WARNING_MESSAGE);
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

	private void salvarGrupo() {
		var grupo = new GrupoInput();
		grupo.setNome(jtfNome.getText());
		var grupoCadastrada = grupoController.cadastrar(token, grupo);
		if (grupoCadastrada != null) {
			jtfId.setText(String.valueOf(grupoCadastrada.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoGrupo, "Grupo cadastrada com sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoGrupo.dispose();
		}
	}

	private void alterarGrupo() {
		var grupo = new GrupoInput();
		grupo.setNome(jtfNome.getText());
		var grupoCadastrada = this.grupoController.alterar(token, grupo, jtfId.getText());
		if (grupoCadastrada != null) {
			jtfId.setText(String.valueOf(grupoCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoGrupo, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoGrupo.dispose();
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
					dadosDoGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString(), jtGrupos);
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
