/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class EmpresaView implements ActionListener, MouseListener {

	private JInternalFrame jifBuscar, jifListar, jifSalvar;
	private JLabel jlId, jlNome, jlTelefone, jlLogradouro, jlNumero, jlComplemento, jlBairro, jlCep, jlEstado, jlCidade;
	private JTextField jtfId, jtfNome, jtfTelefone, jtfBuscar, jtfLogradouro, jtfNumero, jtfComplemento, jtfBairro,
			jtfCep;
	private JFormattedTextField jftfCpfCnpj;
	private JComboBox<CidadeBacicoModel> jcbCidades;
	private JComboBox<EstadoBasicoModel> jcbEstados;
	private JButton jbEditar, jbAlterar, jbCancelar, jbBuscarConfirma, jbSalvar, jbBuscar, jbRefresh, jbReport;
	private JPanel jpAlterarCenter, jpListarNorth, jpListarCenter, jpBuscarCenter, jpBuscarNorth, jpCpfCnpj;
	private JTable jtEmpresas, jtEmpresasBuscar;
	private JScrollPane jsp;
	private JButton jbImprimir;
	private ButtonGroup buttonGroup;
	private JRadioButton rbCpf, rbCnpj;
	private MaskFormatter maskFormatter;
	private String token;
	private CidadeController cidadeController;
	private EstadoController estadoController;
	private JPanel jpFormulario;
	private JDialog jdEditar;

	public EmpresaView(String token) {
		this.token = token;
		this.cidadeController = new CidadeController();
		this.estadoController = new EstadoController();
	}

	public JInternalFrame adicionar() throws ParseException {
		componentesComuns();

		jbSalvar = new JButton("Salvar");

		jbCancelar = new JButton("Cancelar");

		jpFormulario.add(jbSalvar);
		jpFormulario.add(jbCancelar);
		jpFormulario.setLayout(new GridLayout(12, 2));

		jbSalvar.addActionListener(this);
		jbCancelar.addActionListener(this);

		jifSalvar.getContentPane().setLayout(new BorderLayout());
		jifSalvar.getContentPane().add(jpFormulario, BorderLayout.CENTER);
		jifSalvar.pack();
		return jifSalvar;
	}

	private void componentesComuns() throws ParseException {
		jpFormulario = new JPanel();
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

		jlTelefone = new JLabel("Telefone: ");
		jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlTelefone);
		jtfTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		jpFormulario.add(jtfTelefone);

		jlLogradouro = new JLabel("Rua/Av.: ");
		jlLogradouro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlLogradouro);
		jtfLogradouro = new JTextField();
		jpFormulario.add(jtfLogradouro);

		jpFormulario.add(jlNumero);
		jtfNumero = new JTextField();
		jpFormulario.add(jtfNumero);
		jlNumero = new JLabel("Número: ");
		jlNumero.setHorizontalAlignment(SwingConstants.RIGHT);

		jlComplemento = new JLabel("Complemento: ");
		jlComplemento.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlComplemento);
		jtfComplemento = new JTextField();
		jpFormulario.add(jtfComplemento);

		jlBairro = new JLabel("Bairro: ");
		jlBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlBairro);
		jtfBairro = new JTextField();
		jpFormulario.add(jtfBairro);

		jlCep = new JLabel("CEP: ");
		jlCep.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCep);
		jtfCep = new JFormattedTextField(new MaskFormatter("##.###-###"));
		jpFormulario.add(jtfCep);

		jlEstado = new JLabel("Estado: ");
		jlEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlCidade);
		jcbCidades = new JComboBox<CidadeBacicoModel>();
		cidadeController.todasCidades(this.token).forEach(cidade -> jcbCidades.addItem(cidade));
		jpFormulario.add(jcbCidades);

		jlCidade = new JLabel("Cidade: ");
		jlCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlEstado);
		jcbEstados = new JComboBox<EstadoBasicoModel>();
		estadoController.todosEstados(this.token).forEach(estado -> jcbEstados.addItem(estado));
		jpFormulario.add(jcbEstados);

	}

	public void editar() throws ParseException {
		this.jdEditar = new JDialog();

		componentesComuns();

		this.jbAlterar = new JButton("Alterar");

		this.jbCancelar = new JButton("Cancelar");

		this.jpAlterarCenter.add(jbAlterar);
		this.jpAlterarCenter.add(jbCancelar);
		this.jpAlterarCenter.setLayout(new GridLayout(12, 2));

		this.jdEditar.getContentPane().setLayout(new BorderLayout());
		this.jdEditar.getContentPane().add(this.jpFormulario, BorderLayout.CENTER);

		this.jdEditar.setTitle("Alterar Usuário");
		this.jdEditar.setSize(300, 300);
		this.jdEditar.setModal(true);
		this.jdEditar.setLocationRelativeTo(null);
		this.jdEditar.setVisible(true);
		this.jbAlterar.addActionListener(this);
		this.jbCancelar.addActionListener(this);
	}

	private void atualizar() {
		jpListarCenter.removeAll();
		jtEmpresas = new JTable();
		jpListarCenter.add(new JScrollPane(jtEmpresas));
		jpListarCenter.setLayout(new GridLayout(1, 1));
		jpListarCenter.revalidate();
		jtEmpresas.addMouseListener(this);
		if (jbEditar.isEnabled() == true) {
			jbEditar.setEnabled(false);
			jbImprimir.setEnabled(false);
		}
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

		jpListarCenter = new JPanel();
		jpListarNorth = new JPanel();

		jbBuscar = new JButton("Buscar");

		jbRefresh = new JButton("Atualizar");

		jbEditar = new JButton("Editar");
		jbEditar.setEnabled(false);

		jbReport = new JButton("Relatório");

		jbImprimir = new JButton("Imprimir");
		jbImprimir.setEnabled(false);

		var empresaControler = new EmpresaController();
		var colunas = new String[] { "Código", "Nome", "CPF/CNPJ", "Status", "Endereco" };
		var empresas = empresaControler.todasEmpresas(this.token);
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
		jtEmpresas = new JTable(jtModeloObjeto);
		jpListarCenter.add(new JScrollPane(jtEmpresas));
		jpListarCenter.setLayout(new GridLayout(1, 1));

		jpListarNorth.add(jbBuscar);
		jpListarNorth.add(jbRefresh);
		jpListarNorth.add(jbEditar);
		jpListarNorth.add(jbReport);
		jpListarNorth.add(jbImprimir);

		jbBuscar.addActionListener(this);
		jbRefresh.addActionListener(this);
		jbEditar.addActionListener(this);
		jbReport.addActionListener(this);
		jbImprimir.addActionListener(this);
		jtEmpresas.addMouseListener(this);

		jifListar.getContentPane().setLayout(new BorderLayout(5, 5));
		jifListar.getContentPane().add(jpListarNorth, BorderLayout.NORTH);
		jifListar.getContentPane().add(jpListarCenter, BorderLayout.CENTER);
		return jifListar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object obj = e.getSource();
			if (obj == jbSalvar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(null, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarEmpresa();

			} else if (obj == jbCancelar) {
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
			} else if (obj == jbAlterar) {

				JOptionPane.showMessageDialog(null, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (obj == jbBuscar) {
				buscar();
			} else if (obj == rbCpf) {
				jftfCpfCnpj.setValue(null);
				maskFormatter.setMask("###.###.###-##");
				jftfCpfCnpj.setEnabled(true);
				jftfCpfCnpj.requestFocus();// Ao ganhar foco a mascara é aplicada
			} else if (obj == rbCnpj) {
				jftfCpfCnpj.setValue(null);
				maskFormatter.setMask("##.###.###/####-##");
				jftfCpfCnpj.setEnabled(true);
				jftfCpfCnpj.requestFocus();// Ao ganhar foco a mascara é aplicada
			} else if (obj == jbBuscarConfirma) {
				buttonBuscarConfirma();
			} else if (obj == jbRefresh) {
				atualizar();
			} else if (obj == jbEditar) {
				editar();
				
			} else if (obj == jbReport) {
			} else if (obj == jbImprimir) {
				int linha;
				linha = jtEmpresas.getSelectedRow();
				Integer user_pk_id = Integer.parseInt(jtEmpresas.getModel().getValueAt(linha, 0).toString());
				HashMap<String, Integer> parametro = new HashMap<String, Integer>();
				parametro.put("user_pk_id", user_pk_id);
			} else {
				JOptionPane.showMessageDialog(null, "Ação desconecida nada foi implementado!", "Vazio...",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception exception) {
		}
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
			JOptionPane.showMessageDialog(null, "Empresa cadastrada com sucesso!");
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
		if (e.getSource() == jtEmpresas) {
			if (jtEmpresas.getSelectedRow() != -1) {
				jbEditar.setEnabled(true);
				jbImprimir.setEnabled(true);
				System.out.println(String.format("Linha=%d e Coluna=%d e Valor=%s", jtEmpresas.getSelectedRow(),
						jtEmpresas.getSelectedColumn(),
						jtEmpresas.getValueAt(jtEmpresas.getSelectedRow(), jtEmpresas.getSelectedColumn())));
			}
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
