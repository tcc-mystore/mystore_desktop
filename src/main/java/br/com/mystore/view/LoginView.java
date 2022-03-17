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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private JTextField jtfUsuario;
	private JTextField jtfSenha;

	public LoginView() {
		this.setTitle("Login - MyStore");
		this.setSize(250, 350);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(logo(), BorderLayout.NORTH);
		this.getContentPane().add(formulario(), BorderLayout.CENTER);
		inicializarValores();
	}

	private JPanel formulario() {
		jlUsuario = new JLabel("Usuário: ");
		jlUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		jtfUsuario = new JTextField();
		jlSenha = new JLabel("Senha: ");
		jlSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		jtfSenha = new JTextField();

		jbFechar = new JButton("Fechar");
		jbEntrar = new JButton("Entrar");

		jpFormulario = new JPanel();
		jpFormulario.setBorder(BorderFactory.createTitledBorder("Dados de Acesso"));
		jpFormulario.add(jlUsuario);
		jpFormulario.add(jtfUsuario);
		jpFormulario.add(jlSenha);
		jpFormulario.add(jtfSenha);
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
		try {
			if (e.getSource() == jbFechar) {
				fecharAplicacao();
			} else if (e.getSource() == jbEntrar) {
				// Lógoca de login
			}
		} catch (Exception ex) {
			System.out.println(String.format("Erro ao capturar o evento acionado. Erro: %s.", ex.getMessage()));
		}
	}

	private void fecharAplicacao() {
		System.out.println("Finalizando a aplicação.");
		this.dispose();
		System.exit(0);
	}

	private void inicializarValores() {
		jtfUsuario.setText("");
		jtfSenha.setText("");
	}
}
