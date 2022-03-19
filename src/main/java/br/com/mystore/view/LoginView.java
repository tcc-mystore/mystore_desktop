package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.mystore.api.controller.AuthorizationController;
import br.com.mystore.api.exception.ApiException;

public class LoginView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon iiLogo;
	private JButton jbEntrar;
	private JButton jbFechar;
	private JLabel jlUsuario;
	private JLabel jlSenha;
	private JLabel jlLogo;
	private JPanel jpFormulario;
	private JPanel jpLogo;
	private JPasswordField jpfSenha;
	private JTextField jtfUsuario;
	private AuthorizationController authorizationController;

	public LoginView() {
		this.setTitle("Login - MyStore");
		this.setSize(300, 350);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(logo(), BorderLayout.NORTH);
		this.getContentPane().add(formulario(), BorderLayout.CENTER);
		inicializarValores();
		jbEntrar.addActionListener(this);
		jbFechar.addActionListener(this);
	}

	private JPanel formulario() {
		jlUsuario = new JLabel("Usuário: ");
		jlUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		jtfUsuario = new JTextField();
		jlSenha = new JLabel("Senha: ");
		jlSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		jpfSenha = new JPasswordField();

		jbFechar = new JButton("Fechar");
		jbEntrar = new JButton("Entrar");

		jpFormulario = new JPanel();
		jpFormulario.setBorder(BorderFactory.createTitledBorder("Dados de Acesso"));
		jpFormulario.add(jlUsuario);
		jpFormulario.add(jtfUsuario);
		jpFormulario.add(jlSenha);
		jpFormulario.add(jpfSenha);
		jpFormulario.add(jbFechar);
		jpFormulario.add(jbEntrar);
		jpFormulario.setLayout(new GridLayout(3, 2));

		return jpFormulario;
	}

	private JPanel logo() {
		jpLogo = new JPanel();
		jlLogo = new JLabel();
		jlLogo.setToolTipText("My Store");
		iiLogo = new ImageIcon(getClass().getResource("/br/com/mystore/assets/logo.png"));
		jlLogo.setIcon(iiLogo);
		jpLogo.add(jlLogo);
		return jpLogo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbFechar) {
			try {
				fecharAplicacao();
			} catch (Exception ex) {
				System.out.println(String.format("Erro ao capturar o evento acionado. Erro: %s.", ex.getMessage()));
			}
		} else if (e.getSource() == jbEntrar) {
			try {
				var usuarioAutenticado = authorizationController.tokenUsuario(jtfUsuario.getText(), String.valueOf(jpfSenha.getPassword()));
				System.out.print(usuarioAutenticado.toString());
			} catch (ApiException ex) {
				JOptionPane.showMessageDialog(null, ex.getProblema().getUserMessage(), ex.getProblema().getError(), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void fecharAplicacao() {
		System.out.println("Finalizando a aplicação.");
		this.dispose();
		System.exit(0);
	}

	private void inicializarValores() {
		// jtfUsuario.setText("");
		// jpfSenha.setText("");

		jtfUsuario.setText("geversonjosedesouza@hotmail.com");
		jpfSenha.setText("123456");

		authorizationController = new AuthorizationController();
		var appAutenticado = authorizationController.tokenAplicacao();
		System.out.println(String.format("Autenticando o app. Token: %s", appAutenticado.toString()));
	}
}
