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
import javax.swing.JCheckBox;
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

import br.com.mystore.desktop.api.controller.UsuarioController;
import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.input.UsuarioInput;
import br.com.mystore.desktop.utils.TabelaModeloObjeto;

public class UsuarioView extends JInternalFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JInternalFrame jifListar;
	private JLabel jlId, jlNome, jlEmail, jlStatus, jlGerarNovaSenha;
	private JTextField jtfId, jtfNome, jtfEmail;
	private JButton jbAlterar, jbCancelar, jbSalvar, jbRefresh, jbAdicionar, jbGrupos, jbGerarNovaSenha;
	private JCheckBox jckStatus;
	private JPanel jpBotoesCRUD, jpListaDeDados, jpFormulario, jpCenter;
	private JTable jtUsuarios;
	private String token;
	private JDialog jdDadosDoUsuario;
	private Window owner;
	private UsuarioController usuarioController;

	public UsuarioView(String token) {
		this.owner = SwingUtilities.getWindowAncestor(this);
		this.token = token;
		this.usuarioController = new UsuarioController();
	}

	public void dadosDoUsuario(String id, Object object) {

		jdDadosDoUsuario = new JDialog((Frame) owner);

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

		jlEmail = new JLabel("E-mail: ");
		jlEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlEmail);
		jtfEmail = new JTextField();
		jpFormulario.add(jtfEmail);

		jlStatus = new JLabel("Ativo: ");
		jlStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlStatus);
		jckStatus = new JCheckBox();
		jckStatus.setEnabled(false);
		jpFormulario.add(jckStatus);

		jlGerarNovaSenha = new JLabel("Nova Senha: ");
		jlGerarNovaSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		jpFormulario.add(jlGerarNovaSenha);
		jbGerarNovaSenha = new JButton("Gerar");
		jbGerarNovaSenha.setEnabled(false);
		jbGerarNovaSenha.addActionListener(this);
		jpFormulario.add(jbGerarNovaSenha);

		jbCancelar = new JButton("Cancelar");
		jpRodape.add(jbCancelar);
		jbCancelar.addActionListener(this);

		var titulo = "";
		var larguraDaTela = 350;
		var alturaDaTela = 210;
		var quantidadeDeLinhasDoGrid = 5;
		if (id == null && object == jbAdicionar) {
			titulo = "Cadastrar Usuario";
			jbSalvar = new JButton("Salvar");
			jpRodape.add(jbSalvar);
			jbSalvar.addActionListener(this);
		} else {
			titulo = "Alterar Usuario";
			var usuario = usuarioController.usuarioPorId(token, Integer.parseInt(id));
			jtfId.setText(String.valueOf(usuario.getId()));
			jtfNome.setText(usuario.getNome());
			jtfEmail.setText(usuario.getEmail());
			jckStatus.setEnabled(true);
			jckStatus.setSelected(usuario.getAtivo() == null ? false : true);
			jckStatus.addActionListener(this);
			jbGerarNovaSenha.setEnabled(true);
			jbAlterar = new JButton("Alterar");
			jpRodape.add(jbAlterar);
			jbAlterar.addActionListener(this);
		}

		jpFormulario.setLayout(new GridLayout(quantidadeDeLinhasDoGrid, 2));

		jdDadosDoUsuario.getContentPane().setLayout(new BorderLayout());
		jdDadosDoUsuario.getContentPane().add(jpFormulario, BorderLayout.NORTH);
		jdDadosDoUsuario.getContentPane().add(jpRodape, BorderLayout.SOUTH);

		jdDadosDoUsuario.setTitle(titulo);
		jdDadosDoUsuario.setSize(larguraDaTela, alturaDaTela);
		jdDadosDoUsuario.setModal(true);
		jdDadosDoUsuario.setLocationRelativeTo(null);
		jdDadosDoUsuario.setVisible(true);
	}

	private void carregarDados() {
		if (jpListaDeDados == null)
			jpListaDeDados = new JPanel();
		jpListaDeDados.removeAll();
		var colunas = new String[] { "Código", "Nome" };
		var usuarios = usuarioController.todosUsuarios(this.token);
		var dados = new String[usuarios.size()][colunas.length];
		for (int linha = 0; linha < dados.length; linha++) {
			dados[linha][0] = String.valueOf(usuarios.get(linha).getId());
			dados[linha][1] = String.valueOf(usuarios.get(linha).getNome());
		}
		var dadosDaTabela = new TabelaModeloObjeto(dados, colunas);
		jtUsuarios = new JTable(dadosDaTabela);
		jtUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtUsuarios.getColumnModel().getColumn(1).setPreferredWidth(50);
		jpListaDeDados.add(new JScrollPane(jtUsuarios));
		jpListaDeDados.setLayout(new GridLayout(1, 1));
		jpListaDeDados.revalidate();
		jtUsuarios.addMouseListener(this);
	}

	public JInternalFrame listar() {
		jifListar = new JInternalFrame();
		jifListar.setTitle("Usuarios Cadastradas");
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

		jbGrupos = new JButton("Grupos");
		jbGrupos.setEnabled(false);
		jpBotoesCRUD.add(jbGrupos);
		jbGrupos.addActionListener(this);

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
					JOptionPane.showMessageDialog(jdDadosDoUsuario, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					salvarUsuario();

			} else if (e.getSource() == jbCancelar) {
				jdDadosDoUsuario.dispose();
			} else if (e.getSource() == jbAlterar) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoUsuario, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else
					alterarUsuario();

			} else if (e.getSource() == jbRefresh) {
				carregarDados();
			} else if (e.getSource() == jbAdicionar) {
				dadosDoUsuario(null, e.getSource());
			} else if (e.getSource() == jbGrupos) {
				//
			} else if (e.getSource() == jbGerarNovaSenha) {
				var critica = criticas();
				if (critica != null)
					JOptionPane.showMessageDialog(jdDadosDoUsuario, critica, "Atenção", JOptionPane.WARNING_MESSAGE);
				else {
					if (usuarioController.solicitarCodigoAcesso(token, jtfEmail.getText()))
						JOptionPane.showMessageDialog(jdDadosDoUsuario, "Código enviado por email!",
								"Solicitação Realizada", JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(jdDadosDoUsuario, "Falha ao enviar código por email!", "Falhou",
								JOptionPane.WARNING_MESSAGE);

				}
			} else if (e.getSource() == jckStatus) {
				var usuarioAtivadoOuDesativado = usuarioController.ativarOuDesativar(token,
						Integer.parseInt(jtfId.getText()), jckStatus.isSelected());
				if (usuarioAtivadoOuDesativado) {
					var mensagem = jckStatus.isSelected() ? "Usuário ativado com sucesso!"
							: "Usuário desativado com sucesso!";
					JOptionPane.showMessageDialog(jdDadosDoUsuario, mensagem, "Alteração Realizada",
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

	private void salvarUsuario() {
		var usuario = new UsuarioInput();
		usuario.setNome(jtfNome.getText());
		usuario.setEmail(jtfEmail.getText());
		var usuarioCadastrado = usuarioController.cadastrar(token, usuario);
		if (usuarioCadastrado != null) {
			jtfId.setText(String.valueOf(usuarioCadastrado.getId()));
			jbSalvar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoUsuario, "Usuario cadastrado com sucesso!", "Operação Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoUsuario.dispose();
		}
	}

	private void alterarUsuario() {
		var usuario = new UsuarioInput();
		usuario.setNome(jtfNome.getText());
		usuario.setEmail(jtfEmail.getText());
		var usuarioCadastrada = this.usuarioController.alterar(token, usuario, Integer.parseInt(jtfId.getText()));
		if (usuarioCadastrada != null) {
			jtfId.setText(String.valueOf(usuarioCadastrada.getId()));
			jbAlterar.setEnabled(false);
			JOptionPane.showMessageDialog(jdDadosDoUsuario, "Alteração Realizada Com Sucesso!", "Alteração Realizada",
					JOptionPane.INFORMATION_MESSAGE);
			jdDadosDoUsuario.dispose();
		}
	}

	private String criticas() {
		if (jbGerarNovaSenha.isEnabled()) {
			if (jtfEmail.getText().equals(""))
				return "Campo 'E-mail' é obrigatório!";
			else
				return null;
		} else {
			if (jtfNome.getText().equals(""))
				return "Campo 'Nome' é obrigatório!";
			else if (jtfEmail.getText().equals(""))
				return "Campo 'E-mail' é obrigatório!";
			else
				return null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == jtUsuarios && jtUsuarios.getSelectedRow() != -1) {
				jbGrupos.setEnabled(true);
				if (e.getClickCount() == 2)
					dadosDoUsuario(jtUsuarios.getValueAt(jtUsuarios.getSelectedRow(), 0).toString(), jtUsuarios);
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
