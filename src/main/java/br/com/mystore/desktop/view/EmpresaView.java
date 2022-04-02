package br.com.mystore.desktop.view;

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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import br.com.mystore.desktop.api.controller.CidadeController;
import br.com.mystore.desktop.api.controller.EmpresaController;
import br.com.mystore.desktop.api.controller.EstadoController;
import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.CidadeModel;
import br.com.mystore.desktop.api.model.EstadoModel;
import br.com.mystore.desktop.api.model.input.CidadeIdInput;
import br.com.mystore.desktop.api.model.input.EmpresaAtualizaInput;
import br.com.mystore.desktop.api.model.input.EmpresaInput;
import br.com.mystore.desktop.api.model.input.EnderecoAtualizaInput;
import br.com.mystore.desktop.api.model.input.EnderecoInput;
import br.com.mystore.desktop.utils.TabelaModeloObjeto;

public class EmpresaView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome, jlTelefone, jlStatus, jlLogradouro, jlNumero, jlComplemento, jlBairro, jlCep, jlEstado,
			jlCidade;
	private JTextField jtfId, jtfNome, jtfLogradouro, jtfNumero, jtfComplemento, jtfBairro, jtfEnderecoId;
	private JFormattedTextField jftfCpfCnpj, jftfTelefone, jftfCep;
	private JComboBox<CidadeModel> jcbCidades;
	private JComboBox<EstadoModel> jcbEstados;
	private JButton jbAlterar, jbCancelar, jbSalvar, jbBuscar, jbRefresh, jbAdicionar, jbApagar;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpCpfCnpj, jpFormulario;
	private JCheckBox jckStatus;
	private JTable jtEmpresas;
	private ButtonGroup buttonGroup;
	private JRadioButton rbCpf, rbCnpj;
	private MaskFormatter maskFormatter;
	private String token;
	private CidadeController cidadeController;
	private EstadoController estadoController;
	private EmpresaController empresaController;
	private JDialog jdDadosDaEmpresa;
	private Window owner;

	public EmpresaView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.cidadeController = new CidadeController();
		this.estadoController = new EstadoController();
		this.empresaController = new EmpresaController();
	}

	public void dadosDaEmpresa(String id) throws ParseException {

		jdDadosDaEmpresa = new JDialog((Frame) owner);

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

		// 3
		jpCpfCnpj = new JPanel();
		buttonGroup = new ButtonGroup();
		rbCpf = new JRadioButton("CPF");
		rbCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		rbCpf.addActionListener(this);
		buttonGroup.add(rbCpf);
		jpCpfCnpj.add(rbCpf);
		rbCnpj = new JRadioButton("CNPJ");
		rbCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		rbCnpj.addActionListener(this);
		buttonGroup.add(rbCnpj);
		jpCpfCnpj.add(rbCnpj);
		jpCpfCnpj.setLayout(new GridLayout(1, 2));
		jpFormulario.add(jpCpfCnpj);
		maskFormatter = new MaskFormatter();
		jftfCpfCnpj = new JFormattedTextField(maskFormatter);
		jftfCpfCnpj.setEnabled(false);
		jpFormulario.add(jftfCpfCnpj);

		// 4
		jlTelefone = new JLabel("Telefone: ");
		jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlTelefone);
		jftfTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		jpFormulario.add(jftfTelefone);

		// 5
		jlStatus = new JLabel("Ativa: ");
		jlStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlStatus);
		jckStatus = new JCheckBox();
		jckStatus.setEnabled(false);
		jpFormulario.add(jckStatus);

		// 6
		jlLogradouro = new JLabel("Rua/Av.: ");
		jlLogradouro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlLogradouro);
		jtfLogradouro = new JTextField();
		jpFormulario.add(jtfLogradouro);

		// 7
		jlNumero = new JLabel("Número: ");
		jlNumero.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlNumero);
		jtfNumero = new JTextField();
		jpFormulario.add(jtfNumero);

		// 8
		jlComplemento = new JLabel("Complemento: ");
		jlComplemento.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlComplemento);
		jtfComplemento = new JTextField();
		jpFormulario.add(jtfComplemento);

		// 9
		jlBairro = new JLabel("Bairro: ");
		jlBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlBairro);
		jtfBairro = new JTextField();
		jpFormulario.add(jtfBairro);

		// 10
		jlCep = new JLabel("CEP: ");
		jlCep.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCep);
		jftfCep = new JFormattedTextField(new MaskFormatter("##.###-###"));
		jpFormulario.add(jftfCep);

		// 11
		jlEstado = new JLabel("Estado: ");
		jlEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlEstado);
		jcbEstados = new JComboBox<EstadoModel>();
		jcbEstados.addActionListener(this);
		estadoController.todosEstados(token).forEach(estado -> jcbEstados.addItem(estado));
		jpFormulario.add(jcbEstados);

		// 12
		jlCidade = new JLabel("Cidade: ");
		jlCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCidade);
		jcbCidades = new JComboBox<CidadeModel>();
		cidadeController.todasCidades(token).forEach(cidade -> jcbCidades.addItem(cidade));
		jpFormulario.add(jcbCidades);
		var titulo = "";
		if (id == null) {
			titulo = "Cadastrar Empresa";
			jckStatus.setSelected(true);
			jbSalvar = new JButton("Salvar");
			jpFormulario.add(jbSalvar);
			jbSalvar.addActionListener(this);
		} else {
			titulo = "Alterar Empresa";
			var empresa = empresaController.empresaPorId(token, id);
			jtfId.setText(String.valueOf(empresa.getId()));
			jtfNome.setText(empresa.getNome());
			if (empresa.getCpfCnpj() != null)
				tratandoCpfCnpj(empresa.getCpfCnpj().length(), empresa.getCpfCnpj());
			jftfTelefone.setValue(empresa.getTelefone());
			jckStatus.setEnabled(true);
			jckStatus.setSelected(empresa.getAtivo());
			jckStatus.addActionListener(this);
			jtfLogradouro.setText(empresa.getEndereco().getLogradouro());
			// Apenas para armazenar o FkEndereco
			jtfEnderecoId = new JTextField(String.valueOf(empresa.getEndereco().getId()));
			jtfNumero.setText(empresa.getEndereco().getNumero());
			jtfComplemento.setText(empresa.getEndereco().getComplemento());
			jtfBairro.setText(empresa.getEndereco().getBairro());
			jftfCep.setValue(empresa.getEndereco().getCep());
			jcbEstados.getModel().setSelectedItem(empresa.getEndereco().getCidade().getEstado());
			jcbCidades.getModel().setSelectedItem(empresa.getEndereco().getCidade());
			jbAlterar = new JButton("Alterar");
			jpFormulario.add(jbAlterar);
			jbAlterar.addActionListener(this);
		}

		jbCancelar = new JButton("Cancelar");
		jpFormulario.add(jbCancelar);
		jbCancelar.addActionListener(this);

		jpFormulario.setLayout(new GridLayout(13, 2));

		jdDadosDaEmpresa.getContentPane().setLayout(new BorderLayout());
		jdDadosDaEmpresa.getContentPane().add(jpFormulario, BorderLayout.CENTER);

		jdDadosDaEmpresa.setTitle(titulo);
		jdDadosDaEmpresa.setSize(300, 330);
		jdDadosDaEmpresa.setModal(true);
		jdDadosDaEmpresa.setLocationRelativeTo(null);
		jdDadosDaEmpresa.setVisible(true);
	}

	private void carregaDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		jtEmpresas = new JTable(dadosDaListagem());
		jtEmpresas.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEmpresas.getColumnModel().getColumn(1).setPreferredWidth(150);
		jtEmpresas.getColumnModel().getColumn(2).setPreferredWidth(150);
		jtEmpresas.getColumnModel().getColumn(3).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtEmpresas));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtEmpresas.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Empresas Cadastradas");
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

		carregaDados();

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpBotoesCRUD, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListaDeDados, BorderLayout.CENTER);
		jifListar.pack();
		return jifListar;
	}

	private TabelaModeloObjeto dadosDaListagem() {
		var colunas = new String[] { "Código", "Nome", "CPF/CNPJ", "Status" };
		var empresas = empresaController.todasEmpresas(this.token);
		var dados = new String[empresas.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(empresas.get(linha).getId());
			dados[linha][1] = String.valueOf(empresas.get(linha).getNome());
			dados[linha][2] = String
					.valueOf(empresas.get(linha).getCpfCnpj() != null ? empresas.get(linha).getCpfCnpj() : "");
			dados[linha][3] = String.valueOf(empresas.get(linha).getAtivo() ? "Ativo" : "Inativo");
		}
		return new TabelaModeloObjeto(dados, colunas);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbSalvar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDaEmpresa, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarEmpresa();

			} else if (e.getSource() == jbCancelar) {
				limparDados();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDaEmpresa, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarEmpresa();

			} else if (e.getSource() == rbCpf) {
				tratandoCpfCnpj(14, null);
			} else if (e.getSource() == rbCnpj) {
				tratandoCpfCnpj(18, null);
			} else if (e.getSource() == jbRefresh) {
				carregaDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDaEmpresa(null);
			} else if (e.getSource() == jcbEstados) {
				if (jcbCidades != null)
					jcbCidades.removeAllItems();
				// Provisório
				if (jcbEstados.getSelectedItem() != null)
					cidadeController.todasCidades(this.token).forEach(cidade -> {
						if (((EstadoModel) jcbEstados.getSelectedItem()).getId() == cidade.getEstado().getId())
							jcbCidades.addItem(cidade);
					});
				if (jcbCidades != null)
					jcbCidades.setSelectedIndex(-1);
			} else if (e.getSource() == jckStatus) {
				var empresaAtivadaOuDesativada = empresaController.ativarOuDesativar(token,
						Integer.parseInt(jtfId.getText()), jckStatus.isSelected());
				if (empresaAtivadaOuDesativada) {
					var mensagem = jckStatus.isSelected() ? "Empresa ativada com sucesso!"
							: "Empresa desativada com sucesso!";
					JOptionPane.showMessageDialog(jdDadosDaEmpresa, mensagem, "Alteração Realizada",
							JOptionPane.INFORMATION_MESSAGE);
				}
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

	private void tratandoCpfCnpj(int quantidadeCaracteres, String cpfCnpj) throws ParseException {
		if (quantidadeCaracteres == 14) {
			maskFormatter.setMask("###.###.###-##");
			if (!rbCpf.isSelected())
				rbCpf.setSelected(true);
		} else if (quantidadeCaracteres == 18) {
			maskFormatter.setMask("##.###.###/####-##");
			if (!rbCnpj.isSelected())
				rbCnpj.setSelected(true);
		}
		jftfCpfCnpj.setValue(cpfCnpj);
		jftfCpfCnpj.setEnabled(rbCpf.isSelected() || rbCnpj.isSelected());
		jftfCpfCnpj.requestFocus();
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

	private void salvarEmpresa() {
		var cidade = new CidadeIdInput();
		cidade.setId(((CidadeModel) jcbCidades.getSelectedItem()).getId());
		var endereco = new EnderecoInput();
		endereco.setLogradouro(jtfLogradouro.getText());
		endereco.setNumero(jtfNumero.getText());
		endereco.setComplemento(jtfComplemento.getText());
		endereco.setBairro(jtfBairro.getText());
		endereco.setCep(jftfCep.getText());
		endereco.setCidade(cidade);
		var empresa = new EmpresaInput();
		empresa.setNome(jtfNome.getText());
		empresa.setCpfCnpj(jftfCpfCnpj.getText());
		empresa.setTelefone(jftfTelefone.getText());
		empresa.setEndereco(endereco);
		var empresaCadastrada = empresaController.cadastrar(token, empresa);
		if (empresaCadastrada != null) {
			jtfId.setText(String.valueOf(empresaCadastrada.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDaEmpresa, "Empresa cadastrada com sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDaEmpresa.dispose();
		}
	}

	private void alterarEmpresa() {
		var cidade = new CidadeIdInput();
		cidade.setId(((CidadeModel) jcbCidades.getSelectedItem()).getId());
		var endereco = new EnderecoAtualizaInput();
		endereco.setId(Integer.valueOf(jtfEnderecoId.getText()));
		endereco.setLogradouro(jtfLogradouro.getText());
		endereco.setNumero(jtfNumero.getText());
		endereco.setComplemento(jtfComplemento.getText());
		endereco.setBairro(jtfBairro.getText());
		endereco.setCep(jftfCep.getText());
		endereco.setCidade(cidade);
		var empresa = new EmpresaAtualizaInput();
		empresa.setNome(jtfNome.getText());
		empresa.setCpfCnpj(jftfCpfCnpj.getText());
		empresa.setTelefone(jftfTelefone.getText());
		empresa.setEndereco(endereco);
		var empresaCadastrada = this.empresaController.alterar(token, empresa, jtfId.getText());
		if (empresaCadastrada != null) {
			jtfId.setText(String.valueOf(empresaCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDaEmpresa, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDaEmpresa.dispose();
		}
	}

	private String criticas() {
		if (jtfNome.getText().equals(""))
			return "Campo 'Nome' é obrigatório!";
		else if (!rbCnpj.isSelected() && !rbCpf.isSelected())
			return "Selecione se a empresa é 'CPF/CNPJ'!";
		else if (jftfCpfCnpj.getText().equals("  .   .   /    -  ") || jftfCpfCnpj.getText().equals("   .   .   -  ")
				|| jftfCpfCnpj.getText().equals(""))
			return "Campo 'CPF/CNPJ' é obrigatório!";
		else if (jftfTelefone.getText().equals("(  )      -    ") || jftfTelefone.getText().equals(""))
			return "Campo 'Telefone' é obrigatório!";
		else if (jtfLogradouro.getText().equals(""))
			return "Campo 'Logradouro' é obrigatório!";
		else if (jtfNumero.getText().equals(""))
			return "Campo 'Número' é obrigatório!";
		else if (jtfBairro.getText().equals(""))
			return "Campo 'Bairro' é obrigatório!";
		else if (jftfCep.getText().equals(""))
			return "Campo 'Cep' é obrigatório!";
		else if (jcbCidades.getSelectedIndex() == -1)
			return "Selecione uma 'Cidade'!";
		else if (jcbEstados.getSelectedIndex() == -1)
			return "Selecione um 'Estado'!";
		else
			return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == jtEmpresas && jtEmpresas.getSelectedRow() != -1 && e.getClickCount() == 2) {
				dadosDaEmpresa(jtEmpresas.getValueAt(jtEmpresas.getSelectedRow(), 0).toString());
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
