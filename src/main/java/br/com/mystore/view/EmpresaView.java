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
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

import br.com.mystore.api.controller.CidadeController;
import br.com.mystore.api.controller.EmpresaController;
import br.com.mystore.api.controller.EstadoController;
import br.com.mystore.api.model.CidadeBacicoModel;
import br.com.mystore.api.model.EstadoBasicoModel;
import br.com.mystore.api.model.input.CidadeInput;
import br.com.mystore.api.model.input.EmpresaInput;
import br.com.mystore.api.model.input.EnderecoInput;
import br.com.mystore.utils.TabelaModeloObjeto;

public class EmpresaView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifBuscar, jifListar;
	private JLabel jlId, jlNome, jlTelefone, jlLogradouro, jlNumero, jlComplemento, jlBairro, jlCep, jlEstado, jlCidade;
	private JTextField jtfId, jtfNome, jtfTelefone, jtfBuscar, jtfLogradouro, jtfNumero, jtfComplemento, jtfBairro,
			jtfCep;
	private JFormattedTextField jftfCpfCnpj;
	private JComboBox<CidadeBacicoModel> jcbCidades;
	private JComboBox<EstadoBasicoModel> jcbEstados;
	private JButton jbAlterar, jbCancelar, jbBuscarConfirma, jbSalvar, jbBuscar, jbRefresh, jbAdicionar;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpBuscarCenter, jpBuscarNorth, jpCpfCnpj, jpFormulario;
	private JTable jtEmpresas, jtEmpresasBuscar;
	private JScrollPane jsp;
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
	}

	private void componentesComuns() throws ParseException {
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
		jtfTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		jpFormulario.add(jtfTelefone);

		// 5
		jlLogradouro = new JLabel("Rua/Av.: ");
		jlLogradouro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlLogradouro);
		jtfLogradouro = new JTextField();
		jpFormulario.add(jtfLogradouro);

		// 6
		jlNumero = new JLabel("Número: ");
		jlNumero.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlNumero);
		jtfNumero = new JTextField();
		jpFormulario.add(jtfNumero);

		// 7
		jlComplemento = new JLabel("Complemento: ");
		jlComplemento.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlComplemento);
		jtfComplemento = new JTextField();
		jpFormulario.add(jtfComplemento);

		// 8
		jlBairro = new JLabel("Bairro: ");
		jlBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlBairro);
		jtfBairro = new JTextField();
		jpFormulario.add(jtfBairro);

		// 9
		jlCep = new JLabel("CEP: ");
		jlCep.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCep);
		jtfCep = new JFormattedTextField(new MaskFormatter("##.###-###"));
		jpFormulario.add(jtfCep);

		// 10
		jlEstado = new JLabel("Estado: ");
		jlEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlEstado);
		jcbCidades = new JComboBox<CidadeBacicoModel>();
		cidadeController.todasCidades(this.token).forEach(cidade -> jcbCidades.addItem(cidade));
		jpFormulario.add(jcbCidades);

		// 11
		jlCidade = new JLabel("Cidade: ");
		jlCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCidade);
		jcbEstados = new JComboBox<EstadoBasicoModel>();
		estadoController.todosEstados(this.token).forEach(estado -> jcbEstados.addItem(estado));
		jpFormulario.add(jcbEstados);

	}

	public void dadosDaEmpresa(String id) throws ParseException {

		this.jdDadosDaEmpresa = new JDialog((Frame) this.owner);

		componentesComuns();

		if (id == null) {
			this.jbSalvar = new JButton("Salvar");
			this.jpFormulario.add(jbSalvar);
			this.jbSalvar.addActionListener(this);
		} else {
			var empresa = empresaController.empresaPorId(token, id);

			jtfId.setText(String.valueOf(empresa.getId()));
			jtfNome.setText(empresa.getNome());
			jftfCpfCnpj.setText(empresa.getCpfCnpj());
			// jtfTelefone.setText(empresa.getTelefone());
			// jtfEnderecoId.setText(String.valueOf(empresa.getEndereco().getId()));
			jtfLogradouro.setText(empresa.getEndereco().getLogradouro());
			jtfNumero.setText(empresa.getEndereco().getNumero());
			jtfComplemento.setText(empresa.getEndereco().getComplemento());
			jtfBairro.setText(empresa.getEndereco().getBairro());
			jtfCep.setText(empresa.getEndereco().getCep());
			jcbCidades.setSelectedItem(empresa.getEndereco().getCidade());

			this.jbAlterar = new JButton("Alterar");
			this.jpFormulario.add(jbAlterar);
			this.jbAlterar.addActionListener(this);
		}

		this.jbCancelar = new JButton("Cancelar");
		this.jpFormulario.add(jbCancelar);
		this.jbCancelar.addActionListener(this);

		this.jpFormulario.setLayout(new GridLayout(12, 2));

		this.jdDadosDaEmpresa.getContentPane().setLayout(new BorderLayout());
		this.jdDadosDaEmpresa.getContentPane().add(this.jpFormulario, BorderLayout.CENTER);

		this.jdDadosDaEmpresa.setTitle("Alterar Empresa");
		this.jdDadosDaEmpresa.setSize(300, 330);
		this.jdDadosDaEmpresa.setModal(true);
		this.jdDadosDaEmpresa.setLocationRelativeTo(null);
		this.jdDadosDaEmpresa.setVisible(true);
	}

	private void atualizar() {
		jpListaDeDados.removeAll();
		jtEmpresas = new JTable(dadosDaListagem());
		jpListaDeDados.add(new JScrollPane(jtEmpresas));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtEmpresas.addMouseListener(this);
	}

	private void buttonBuscarConfirma() {
		jpBuscarCenter.removeAll();
		jtEmpresasBuscar = new JTable();
		jsp = new JScrollPane(jtEmpresasBuscar);
		jpBuscarCenter.add(jsp);
		jpBuscarCenter.setLayout(new GridLayout(1, 1));
		jsp.revalidate();
	}

	private JInternalFrame buscar() {
		jifBuscar = new JInternalFrame();
		jifBuscar.setTitle("Consultar Empresas");
		jifBuscar.setSize(1250, 400);
		jifBuscar.setClosable(true);
		jifBuscar.setIconifiable(true);
		jifBuscar.setMaximizable(true);

		jpBuscarNorth = new JPanel();
		jpBuscarCenter = new JPanel();

		jtfBuscar = new JTextField();

		jbBuscarConfirma = new JButton();
		jbBuscarConfirma.addActionListener(this);

		jpBuscarNorth.add(jtfBuscar);
		jpBuscarNorth.add(jbBuscarConfirma);
		jpBuscarNorth.setLayout(new GridLayout(1, 2));

		jtEmpresasBuscar = new JTable();
		jsp = new JScrollPane(jtEmpresasBuscar);
		jpBuscarCenter.add(jsp);
		jpBuscarCenter.setLayout(new GridLayout(1, 1));

		jifBuscar.getContentPane().setLayout(new BorderLayout());
		jifBuscar.getContentPane().add(jpBuscarNorth, BorderLayout.NORTH);
		jifBuscar.getContentPane().add(jpBuscarCenter, BorderLayout.CENTER);
		return jifBuscar;
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Usuários Cadastrados");
		jifListar.setSize(1250, 400);
		jifListar.setClosable(true);
		jifListar.setIconifiable(true);
		jifListar.setMaximizable(true);

		jpBotoesCRUD = new JPanel();

		jbAdicionar = new JButton("Cadastrar");
		jpBotoesCRUD.add(jbAdicionar);
		jbAdicionar.addActionListener(this);

		jbBuscar = new JButton("Buscar");
		jpBotoesCRUD.add(jbBuscar);
		jbBuscar.addActionListener(this);

		jbRefresh = new JButton("Atualizar");
		jpBotoesCRUD.add(jbRefresh);
		jbRefresh.addActionListener(this);

		jpListaDeDados = new JPanel();
		jtEmpresas = new JTable(dadosDaListagem());
		jtEmpresas.addMouseListener(this);
		jpListaDeDados.add(new JScrollPane(jtEmpresas));
		jpListaDeDados.setLayout(new GridLayout(1, 1));

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpBotoesCRUD, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListaDeDados, BorderLayout.CENTER);
		return jifListar;
	}

	private TabelaModeloObjeto dadosDaListagem() {
		empresaController = new EmpresaController();
		var colunas = new String[] { "Código", "Nome", "CPF/CNPJ", "Status", "Endereco" };
		var empresas = empresaController.todasEmpresas(this.token);
		var dados = new String[empresas.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(empresas.get(linha).getId());
			dados[linha][1] = String.valueOf(empresas.get(linha).getNome());
			dados[linha][2] = String
					.valueOf(empresas.get(linha).getCpfCnpj() != null ? empresas.get(linha).getCpfCnpj() : "");
			dados[linha][3] = String.valueOf(empresas.get(linha).getAtivo() ? "Ativo" : "Inativo");
			dados[linha][4] = String.valueOf("");
		}
		var jtModeloObjeto = new TabelaModeloObjeto(dados, colunas);
		return jtModeloObjeto;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object obj = e.getSource();
			if (obj == jbSalvar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDaEmpresa, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarEmpresa();

			} else if (obj == jbCancelar) {
				limparDados();
			} else if (obj == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDaEmpresa, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarEmpresa();

			} else if (obj == jbBuscar) {
				buscar();
			} else if (obj == rbCpf) {
				jftfCpfCnpj.setValue(null);
				maskFormatter.setMask("###.###.###-##");
				jftfCpfCnpj.setEnabled(true);
				jftfCpfCnpj.requestFocus();
			} else if (obj == rbCnpj) {
				jftfCpfCnpj.setValue(null);
				maskFormatter.setMask("##.###.###/####-##");
				jftfCpfCnpj.setEnabled(true);
				jftfCpfCnpj.requestFocus();
			} else if (obj == jbBuscarConfirma) {
				buttonBuscarConfirma();
			} else if (obj == jbRefresh) {
				atualizar();
			} else if (obj == jbAdicionar) {
				dadosDaEmpresa(null);
			} else {
				JOptionPane.showMessageDialog(jifListar, "Ação desconecida nada foi implementado!", "Vazio...",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		jtfTelefone.setText("");
		jtfLogradouro.setText("");
		jtfNumero.setText("");
		jtfComplemento.setText("");
		jtfBairro.setText("");
		jtfCep.setText("");
		jcbCidades.setSelectedIndex(-1);
		jcbEstados.setSelectedIndex(-1);
		jbSalvar.setEnabled(true);
	}

	private void salvarEmpresa() {
		var cidade = new CidadeInput();
		cidade.setId(((CidadeBacicoModel) jcbCidades.getSelectedItem()).getId());
		var endereco = new EnderecoInput();
		endereco.setLogradouro(jtfLogradouro.getText());
		endereco.setNumero(jtfNumero.getText());
		endereco.setComplemento(jtfComplemento.getText());
		endereco.setBairro(jtfBairro.getText());
		endereco.setCep(jtfCep.getText());
		endereco.setCidade(cidade);
		var empresa = new EmpresaInput();
		empresa.setNome(jtfNome.getText());
		empresa.setCpfCnpj(jftfCpfCnpj.getText());
		empresa.setTelefone(jtfTelefone.getText());
		empresa.setEndereco(endereco);
		var empresaController = new EmpresaController();
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
		var cidade = new CidadeInput();
		cidade.setId(((CidadeBacicoModel) jcbCidades.getSelectedItem()).getId());
		var endereco = new EnderecoInput();
		endereco.setLogradouro(jtfLogradouro.getText());
		endereco.setNumero(jtfNumero.getText());
		endereco.setComplemento(jtfComplemento.getText());
		endereco.setBairro(jtfBairro.getText());
		endereco.setCep(jtfCep.getText());
		endereco.setCidade(cidade);
		var empresa = new EmpresaInput();
		empresa.setNome(jtfNome.getText());
		empresa.setCpfCnpj(jftfCpfCnpj.getText());
		empresa.setTelefone(jtfTelefone.getText());
		empresa.setEndereco(endereco);
		var empresaCadastrada = this.empresaController.alterar(token, empresa, jtfId.getText());
		if (empresaCadastrada != null) {
			jtfId.setText(String.valueOf(empresaCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDaEmpresa, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
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
		else if (jtfTelefone.getText().equals("(  )      -    ") || jtfTelefone.getText().equals(""))
			return "Campo 'Telefone' é obrigatório!";
		else if (jtfLogradouro.getText().equals(""))
			return "Campo 'Logradouro' é obrigatório!";
		else if (jtfNumero.getText().equals(""))
			return "Campo 'Número' é obrigatório!";
		else if (jtfComplemento.getText().equals(""))
			return "Campo 'Complemento' é obrigatório!";
		else if (jtfBairro.getText().equals(""))
			return "Campo 'Bairro' é obrigatório!";
		else if (jtfCep.getText().equals(""))
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
				System.out.println(String.format("getSelectedRow=%d", jtEmpresas.getSelectedRow()));
				// limparDados();
				System.out.println(String.format("idTemporario=%s",
						jtEmpresas.getValueAt(jtEmpresas.getSelectedRow(), 0).toString()));
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
