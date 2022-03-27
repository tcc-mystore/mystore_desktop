package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import br.com.mystore.api.controller.CidadeController;
import br.com.mystore.api.controller.GrupoController;
import br.com.mystore.api.model.CidadeModel;
import br.com.mystore.api.model.EstadoModel;
import br.com.mystore.api.model.PermissaoModel;
import br.com.mystore.api.model.input.GrupoInput;
import br.com.mystore.utils.TabelaModeloObjeto;

public class GrupoView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome;
	private JTextField jtfId, jtfNome, jtfLogradouro, jtfNumero, jtfComplemento, jtfBairro;
	private JFormattedTextField jftfCpfCnpj, jftfTelefone, jftfCep;
	private JComboBox<CidadeModel> jcbCidades;
	private JComboBox<EstadoModel> jcbEstados;
	private JButton jbAlterar, jbCancelar, jbBuscarConfirma, jbSalvar, jbBuscar, jbRefresh, jbAdicionar, jbApagar,
			jbPermissoes, jbAlterarPermissoes;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpBuscarCenter, jpFormulario;
	private JTable jtGrupos, jtGruposBuscar;
	private JScrollPane jsp;
	private ButtonGroup buttonGroup;
	private MaskFormatter maskFormatter;
	private String token;
	private CidadeController cidadeController;
	private GrupoController grupoController;
	private JDialog jdDadosDaGrupo, jdPermissoesDoGrupo;
	private Window owner;
	private JList<PermissaoModel> listPermissoes;
	private JList<PermissaoModel> listPermissoesGrupo;

	public GrupoView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.cidadeController = new CidadeController();
	}

	public void dadosDaGrupo(String id) {

		this.jdDadosDaGrupo = new JDialog((Frame) this.owner);

		jpFormulario = new JPanel();
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
		var titulo = "";
		if (id == null) {
			titulo = "Cadastrar Grupo";
			this.jbSalvar = new JButton("Salvar");
			this.jpFormulario.add(jbSalvar);
			this.jbSalvar.addActionListener(this);
		} else {
			titulo = "Alterar Grupo";
			var grupo = grupoController.grupoPorId(token, id);
			jtfId.setText(String.valueOf(grupo.getId()));
			jtfNome.setText(grupo.getNome());
			this.jbAlterar = new JButton("Alterar");
			this.jpFormulario.add(jbAlterar);
			this.jbAlterar.addActionListener(this);
		}

		this.jbCancelar = new JButton("Cancelar");
		this.jpFormulario.add(jbCancelar);
		this.jbCancelar.addActionListener(this);

		this.jpFormulario.setLayout(new GridLayout(3, 2));

		this.jdDadosDaGrupo.getContentPane().setLayout(new BorderLayout());
		this.jdDadosDaGrupo.getContentPane().add(this.jpFormulario, BorderLayout.CENTER);

		this.jdDadosDaGrupo.setTitle(titulo);
		this.jdDadosDaGrupo.setSize(300, 100);
		this.jdDadosDaGrupo.setModal(true);
		this.jdDadosDaGrupo.setLocationRelativeTo(null);
		this.jdDadosDaGrupo.setVisible(true);
	}

	public void permissoesDoGrupo(String id) {

		this.jdPermissoesDoGrupo = new JDialog((Frame) this.owner);

		jpFormulario = new JPanel();
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
		jtfNome.setEditable(false);
		jpFormulario.add(jtfNome);

		jpFormulario.add(new JLabel("Disponíveis"));
		jpFormulario.add(new JLabel("Utilizadas"));

		listPermissoes = new JList<>();
		jpFormulario.add(listPermissoes);
		listPermissoesGrupo = new JList<>();
		jpFormulario.add(listPermissoesGrupo);

		var grupo = grupoController.grupoPorId(token, id);
		jtfId.setText(String.valueOf(grupo.getId()));
		jtfNome.setText(grupo.getNome());
		this.jbAlterarPermissoes = new JButton("Alterar");
		this.jpFormulario.add(jbAlterarPermissoes);
		this.jbAlterarPermissoes.addActionListener(this);

		this.jbCancelar = new JButton("Cancelar");
		this.jpFormulario.add(jbCancelar);
		// this.jbCancelar.addActionListener(this);

		this.jpFormulario.setLayout(new GridLayout(5, 2));

		this.jdPermissoesDoGrupo.getContentPane().setLayout(new BorderLayout());
		this.jdPermissoesDoGrupo.getContentPane().add(this.jpFormulario, BorderLayout.CENTER);

		this.jdPermissoesDoGrupo.setTitle("Permissões Grupo");
		this.jdPermissoesDoGrupo.setSize(300, 250);
		this.jdPermissoesDoGrupo.setModal(true);
		this.jdPermissoesDoGrupo.setLocationRelativeTo(null);
		this.jdPermissoesDoGrupo.setVisible(true);
	}

	private void carregarDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		jtGrupos = new JTable(dadosDaListagem());
		jtGrupos.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtGrupos.getColumnModel().getColumn(1).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtGrupos));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtGrupos.addMouseListener(this);
	}

	private void buttonBuscarConfirma() {
		jpBuscarCenter.removeAll();
		jtGruposBuscar = new JTable();
		jsp = new JScrollPane(jtGruposBuscar);
		jpBuscarCenter.add(jsp);
		jpBuscarCenter.setLayout(new GridLayout(1, 1));
		jsp.revalidate();
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

	private TabelaModeloObjeto dadosDaListagem() {
		grupoController = new GrupoController();
		var colunas = new String[] { "Código", "Nome" };
		var grupos = grupoController.todasGrupos(this.token);
		var dados = new String[grupos.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(grupos.get(linha).getId());
			dados[linha][1] = String.valueOf(grupos.get(linha).getNome());
		}
		return new TabelaModeloObjeto(dados, colunas);
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
				buttonBuscarConfirma();
			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDaGrupo(null);
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
				permissoesDoGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString());
			} else {
				JOptionPane.showMessageDialog(jifListar, "Ação desconecida nada foi implementado!", "Vazio...",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean apagarGrupo(String id) {
		return grupoController.apagarGrupoPorId(token, id);
	}

	private void limparDados() throws ParseException {
		jtfId.setText("");
		buttonGroup.clearSelection();
		jftfCpfCnpj.setValue(null);
		maskFormatter.setMask("");
		jftfCpfCnpj.requestFocus();
		jtfNome.setText("");
		jtfNome.requestFocus();
		jftfCpfCnpj.setEnabled(false);
		jftfTelefone.setText("");
		jtfLogradouro.setText("");
		jtfNumero.setText("");
		jtfComplemento.setText("");
		jtfBairro.setText("");
		jftfCep.setText("");
		jcbCidades.setSelectedIndex(-1);
		jcbEstados.setSelectedIndex(-1);
		if (jbSalvar != null)
			jbSalvar.setEnabled(true);
	}

	private void salvarGrupo() {
		var grupo = new GrupoInput();
		grupo.setNome(jtfNome.getText());
		var grupoController = new GrupoController();
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
					dadosDaGrupo(jtGrupos.getValueAt(jtGrupos.getSelectedRow(), 0).toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
