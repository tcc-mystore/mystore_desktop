package br.com.mystore.desktop.view;

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

import br.com.mystore.desktop.api.controller.AuthorizationController;
import br.com.mystore.desktop.api.exception.ApiException;

public class LoginView extends JFrame implements ActionListener {

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
		this.setSize(350, 400);
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
		jpFormulario = new JPanel();

		jlUsuario = new JLabel("Usuário: ");
		jpFormulario.add(jlUsuario);
		jlUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		jtfUsuario = new JTextField();
		jpFormulario.add(jtfUsuario);

		jlSenha = new JLabel("Senha: ");
		jpFormulario.add(jlSenha);
		jlSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		jpfSenha = new JPasswordField();
		jpFormulario.add(jpfSenha);

		jbFechar = new JButton("Fechar");
		jpFormulario.add(jbFechar);
		jbEntrar = new JButton("Entrar");
		jpFormulario.add(jbEntrar);

		jpFormulario.setBorder(BorderFactory.createTitledBorder("Dados de Acesso"));
		jpFormulario.setLayout(new GridLayout(3, 2));

		return jpFormulario;
	}

	private JPanel logo() {
		jpLogo = new JPanel();
		jlLogo = new JLabel();
		jlLogo.setToolTipText("My Store");
		iiLogo = new ImageIcon(this.getClass().getResource("/imagens/logo.png"));
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
				var usuarioAutenticado = authorizationController.tokenUsuario(jtfUsuario.getText(),
						String.valueOf(jpfSenha.getPassword()));
				new PrincipalView(usuarioAutenticado).setVisible(true);
				this.dispose();
			} catch (ApiException ex) {
				int tipoMensagem = JOptionPane.WARNING_MESSAGE;
				if (ex.getProblema().getStatus() == 500)
					tipoMensagem = JOptionPane.ERROR_MESSAGE;
				JOptionPane.showMessageDialog(null, ex.getProblema().getUserMessage(), ex.getProblema().getError(),
						tipoMensagem);
			}
		}
	}

	private void fecharAplicacao() {
		System.out.println("Finalizando a aplicação.");
		this.dispose();
		System.exit(0);
	}

	private void inicializarValores() {
		jtfUsuario.setText("");
		jpfSenha.setText("");

		authorizationController = new AuthorizationController();
	}
}
