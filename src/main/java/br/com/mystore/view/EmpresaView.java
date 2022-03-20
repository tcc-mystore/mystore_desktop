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

public class EmpresaView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifAlterar, jifBuscar, jifListar, jifSalvar;
	private final JLabel jlId, jlNome, jlTelefone, jlLogradouro, jlNumero, jlComplemento, jlBairro, jlCep, jlEstado,
			jlCidade;
	private JTextField jtfId, jtfNome, jtfTelefone, jtfBuscar, jtfLogradouro, jtfNumero, jtfComplemento, jtfBairro,
			jtfCep;
	private JFormattedTextField jftfCpfCnpj;
	private JComboBox<CidadeBacicoModel> jcbCidades;
	private JComboBox<EstadoBasicoModel> jcbEstados;
	private JButton jbEditar, jbAlterar, jbCancelar, jbBuscarConfirma, jbSalvar, jbBuscar, jbRefresh, jbReport;
	private JPanel jpAlterarCenter, jpListarNorth, jpListarCenter, jpBuscarCenter, jpBuscarNorth, jpSalvarCenter,
			jpCpfCnpj;
	private JTable jtUsers, jtUsersBuscar;
	private JScrollPane jsp;
	private JButton jbImprimir;
	private ButtonGroup buttonGroup;
	private JRadioButton rbCpf, rbCnpj;
	private MaskFormatter maskFormatter;
	private String token;

	public EmpresaView(String token) {
		this.token = token;
		jlId = new JLabel("Código: ");
		jlId.setHorizontalAlignment(SwingConstants.RIGHT);

		jlNome = new JLabel("Nome: ");
		jlNome.setHorizontalAlignment(SwingConstants.RIGHT);

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

		jlTelefone = new JLabel("Telefone: ");
		jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);

		jlLogradouro = new JLabel("Rua/Av.: ");
		jlLogradouro.setHorizontalAlignment(SwingConstants.RIGHT);

		jlNumero = new JLabel("Número: ");
		jlNumero.setHorizontalAlignment(SwingConstants.RIGHT);

		jlComplemento = new JLabel("Complemento: ");
		jlComplemento.setHorizontalAlignment(SwingConstants.RIGHT);

		jlBairro = new JLabel("Bairro: ");
		jlBairro.setHorizontalAlignment(SwingConstants.RIGHT);

		jlCep = new JLabel("CEP: ");
		jlCep.setHorizontalAlignment(SwingConstants.RIGHT);

		jlEstado = new JLabel("Estado: ");
		jlEstado.setHorizontalAlignment(SwingConstants.RIGHT);

		jlCidade = new JLabel("Cidade: ");
		jlCidade.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	public JInternalFrame adicionar() throws ParseException {
		jifSalvar = new JInternalFrame();
		jifSalvar.setTitle("Cadastrar Empresa");
		jifSalvar.setClosable(true);
		jifSalvar.setIconifiable(true);
		jifSalvar.setSize(300, 200);

		jpSalvarCenter = new JPanel();

		jpSalvarCenter.add(jlId);
		jtfId = new JTextField();
		jtfId.setEnabled(false);
		jpSalvarCenter.add(jtfId);

		jpSalvarCenter.add(jlNome);
		jtfNome = new JTextField();
		jpSalvarCenter.add(jtfNome);

		jpSalvarCenter.add(jpCpfCnpj);
		maskFormatter = new MaskFormatter();
		jftfCpfCnpj = new JFormattedTextField(maskFormatter);
		jftfCpfCnpj.setEnabled(false);
		jpSalvarCenter.add(jftfCpfCnpj);

		jpSalvarCenter.add(jlTelefone);
		jtfTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		jpSalvarCenter.add(jtfTelefone);

		jpSalvarCenter.add(jlLogradouro);
		jtfLogradouro = new JTextField();
		jpSalvarCenter.add(jtfLogradouro);

		jpSalvarCenter.add(jlNumero);
		jtfNumero = new JTextField();
		jpSalvarCenter.add(jtfNumero);

		jpSalvarCenter.add(jlComplemento);
		jtfComplemento = new JTextField();
		jpSalvarCenter.add(jtfComplemento);

		jpSalvarCenter.add(jlBairro);
		jtfBairro = new JTextField();
		jpSalvarCenter.add(jtfBairro);

		jpSalvarCenter.add(jlCep);
		jtfCep = new JFormattedTextField(new MaskFormatter("##.###-###"));
		jpSalvarCenter.add(jtfCep);

		jpSalvarCenter.add(jlCidade);
		jcbCidades = new JComboBox<CidadeBacicoModel>();
		var cidades = new CidadeController();
		cidades.todasCidades(this.token).forEach(cidade -> jcbCidades.addItem(cidade));
		jpSalvarCenter.add(jcbCidades);

		jpSalvarCenter.add(jlEstado);
		jcbEstados = new JComboBox<EstadoBasicoModel>();
		var estados = new EstadoController();
		estados.todosEstados(this.token).forEach(estado -> jcbEstados.addItem(estado));
		jpSalvarCenter.add(jcbEstados);

		jbSalvar = new JButton("Salvar");

		jbCancelar = new JButton("Cancelar");

		jpSalvarCenter.add(jbSalvar);
		jpSalvarCenter.add(jbCancelar);
		jpSalvarCenter.setLayout(new GridLayout(12, 2));

		jbSalvar.addActionListener(this);
		jbCancelar.addActionListener(this);

		jifSalvar.getContentPane().setLayout(new BorderLayout());
		jifSalvar.getContentPane().add(jpSalvarCenter, BorderLayout.CENTER);
		jifSalvar.pack();
		return jifSalvar;
	}

	public JInternalFrame alterar() {
		jifAlterar = new JInternalFrame();
		jifAlterar.setTitle("Alterar dados de usuário");
		jifAlterar.setClosable(true);
		jifAlterar.setIconifiable(true);
		jifAlterar.setSize(300, 200);

		jpAlterarCenter = new JPanel();

		jtfId = new JTextField();
		jtfId.setEnabled(false);
		jtfNome = new JTextField();
		jtfNome.setEnabled(false);
		jftfCpfCnpj = new JFormattedTextField();
		jcbCidades = new JComboBox<>();
		jcbEstados = new JComboBox<>();

		jbAlterar = new JButton("Alterar");

		jbCancelar = new JButton("Cancelar");

		jpAlterarCenter.add(jlId);
		jpAlterarCenter.add(jtfId);
		jpAlterarCenter.add(jlNome);
		jpAlterarCenter.add(jtfNome);
		jpAlterarCenter.add(jpCpfCnpj);
		jpAlterarCenter.add(jftfCpfCnpj);
		jpAlterarCenter.add(jlTelefone);
		jpAlterarCenter.add(jcbCidades);
		jpAlterarCenter.add(jlEstado);
		jpAlterarCenter.add(jcbEstados);
		jpAlterarCenter.add(jbAlterar);
		jpAlterarCenter.add(jbCancelar);
		jpAlterarCenter.setLayout(new GridLayout(6, 2));

		jbAlterar.addActionListener(this);
		jbCancelar.addActionListener(this);

		jifAlterar.getContentPane().setLayout(new BorderLayout());
		jifAlterar.getContentPane().add(jpAlterarCenter, BorderLayout.CENTER);
		pack();

		return jifAlterar;
	}

	private void atualizar() {
		jpListarCenter.removeAll();
		jtUsers = new JTable();
		jpListarCenter.add(new JScrollPane(jtUsers));
		jpListarCenter.setLayout(new GridLayout(1, 1));
		jpListarCenter.revalidate();
		jtUsers.addMouseListener(this);
		if (jbEditar.isEnabled() == true) {
			jbEditar.setEnabled(false);
			jbImprimir.setEnabled(false);
		}
	}

	private void buttonBuscarConfirma() {
		jpBuscarCenter.removeAll();
		jtUsersBuscar = new JTable();
		jsp = new JScrollPane(jtUsersBuscar);
		jpBuscarCenter.add(jsp);
		jpBuscarCenter.setLayout(new GridLayout(1, 1));
		jsp.revalidate();
	}

	private JInternalFrame buscar() {
		jifBuscar = new JInternalFrame();
		jifBuscar.setTitle("Consultar Usuários");
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

		jtUsersBuscar = new JTable();
		jsp = new JScrollPane(jtUsersBuscar);
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

		jtUsers = new JTable();
		jpListarCenter.add(new JScrollPane(jtUsers));
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
		jtUsers.addMouseListener(this);

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
				jifAlterar.dispose();
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
				alterar();
				int linha;
				linha = jtUsers.getSelectedRow();
				jtfId.setText(jtUsers.getModel().getValueAt(linha, 0).toString());
				jtfNome.setText(jtUsers.getModel().getValueAt(linha, 1).toString());
				jftfCpfCnpj.setText(jtUsers.getModel().getValueAt(linha, 2).toString());
				jcbCidades.setSelectedItem(jtUsers.getModel().getValueAt(linha, 3).toString());
				jcbEstados.setSelectedItem(jtUsers.getModel().getValueAt(linha, 4).toString());
			} else if (obj == jbReport) {
			} else if (obj == jbImprimir) {
				int linha;
				linha = jtUsers.getSelectedRow();
				Integer user_pk_id = Integer.parseInt(jtUsers.getModel().getValueAt(linha, 0).toString());
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
		if (e.getSource() == jtUsers) {
			if (jtUsers.getSelectedRow() != -1) {
				jbEditar.setEnabled(true);
				jbImprimir.setEnabled(true);
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
