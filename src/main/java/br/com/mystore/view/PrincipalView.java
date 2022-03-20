package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.mystore.api.model.UsuarioAutenticadoModel;
import br.com.mystore.utils.DataHora;

public class PrincipalView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel jlUsuario, jlIdEmpresa, jlData, jlHora, jlSessaoIdEmpresa, jlSessaoData, jlSessaoHora, jlStatus,
			jlSessaoStatus;
	private JPanel jpRodape;
	private JTextField jtfUsuario;
	private JMenuBar jmbBarra;
	private JMenu jmCadastro;
	private JMenuItem jmiCadastroEmpresa, jmiSobre, jmiSair;
	private static DesktopView desktopView;
	private UsuarioAutenticadoModel usuario;

	public PrincipalView(UsuarioAutenticadoModel usuario) {
		this.usuario = usuario;
		this.setTitle("Área de Trabalho - My Store");
		this.setSize(800, 700);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setJMenuBar(barraMenu());

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(centro(), BorderLayout.CENTER);
		this.getContentPane().add(rodape(usuario), BorderLayout.SOUTH);
		this.pack();
	}

	private JMenuBar barraMenu() {
		jmbBarra = new JMenuBar();
		jmbBarra.add(menuCadastro());

		// Saindo..
		jmiSair = new JMenuItem("Sair");
		jmiSair.setMnemonic('S');
		jmiSair.addActionListener(this);
		jmbBarra.add(jmiSair);

		// Sobre..
		jmiSobre = new JMenuItem("Sobre");
		jmiSobre.setMnemonic('S');
		jmiSobre.addActionListener(this);
		jmbBarra.add(jmiSobre);
		return jmbBarra;
	}

	private JMenu menuCadastro() {
		jmCadastro = new JMenu("Cadastro");
		jmCadastro.setMnemonic('C');
		jmbBarra.add(jmCadastro);

		jmiCadastroEmpresa = new JMenuItem("Cadastro de Empresas");
		jmiCadastroEmpresa.setMnemonic('E');
		jmiCadastroEmpresa.addActionListener(this);
		jmCadastro.add(jmiCadastroEmpresa);

		return jmCadastro;
	}

	private JPanel rodape(UsuarioAutenticadoModel usuario) {
		jpRodape = new JPanel();
		jpRodape.setBorder(BorderFactory.createTitledBorder("Dados da Sessão"));

		jlUsuario = new JLabel("Usuario logado: ");
		jlUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		jpRodape.add(jlUsuario);
		jtfUsuario = new JTextField(usuario.getNome_completo());
		jtfUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		jtfUsuario.setEditable(false);
		jpRodape.add(jtfUsuario);

		jlIdEmpresa = new JLabel("Cód. Empresa: ");
		jlIdEmpresa.setHorizontalAlignment(SwingConstants.RIGHT);
		jpRodape.add(jlIdEmpresa);
		jlSessaoIdEmpresa = new JLabel(usuario.getEmpresas()[0]);
		jpRodape.add(jlSessaoIdEmpresa);

		jlStatus = new JLabel("Status: ");
		jlStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		jpRodape.add(jlStatus);
		jlSessaoStatus = new JLabel("ATIVO");
		jpRodape.add(jlSessaoStatus);

		jlHora = new JLabel("Hora: ");
		jlHora.setHorizontalAlignment(SwingConstants.RIGHT);
		jpRodape.add(jlHora);
		jlSessaoHora = new JLabel("00:00:00");
		var dataHora = new DataHora(jlSessaoHora);
		dataHora.start();
		jpRodape.add(jlSessaoHora);

		jlData = new JLabel("Data: ");
		jlData.setHorizontalAlignment(SwingConstants.RIGHT);
		jpRodape.add(jlData);
		jlSessaoData = new JLabel(dataHora.mostraData());
		jpRodape.add(jlSessaoData);

		jpRodape.setLayout(new GridLayout(1, 10));
		return jpRodape;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jmiCadastroEmpresa) {
				addFrame(new EmpresaView(usuario.getAccess_token()).adicionar());
			} else if (e.getSource() == jmiSair) {
				fecharAplicacao();
			} else if (e.getSource() == jmiSobre) {
				new SobreView();
			} else {
				JOptionPane.showMessageDialog(null, "Ação não implementada", "Operação impossível",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro encontrado: " + ex.getMessage(), "Operação falhou",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void fecharAplicacao() {
		if (desktopView.getAllFrames().length > 0) {
			JOptionPane
					.showMessageDialog(
							null, "Não é possível fechar o sistema," + "\npor que há "
									+ desktopView.getAllFrames().length + " janela(s) aberta(s)",
							"Operação impossível", JOptionPane.ERROR_MESSAGE);
		} else {
			int confirmaSaida = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja fechar" + "\n o sistema?",
					"Saindo...", JOptionPane.YES_OPTION);
			if (confirmaSaida == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	public static void addFrame(JInternalFrame internalFrame) {
		Dimension dmDesktop = desktopView.getSize();
		Dimension dmInternal = internalFrame.getSize();
		internalFrame.setLocation((dmDesktop.width - dmInternal.width) / 2, (dmDesktop.height - dmInternal.height) / 2);
		desktopView.add(internalFrame);
		internalFrame.setVisible(true);
	}

	private JDesktopPane centro() {
		desktopView = new DesktopView(null);
		return desktopView;
	}

	class DesktopView extends JDesktopPane {

		private static final long serialVersionUID = 1L;
		private final ImageIcon iiImagem;

		public DesktopView(Dimension dimension) {
			if (dimension != null) {
				iiImagem = new ImageIcon(getClass().getResource("/br/com/mystore/assets/mystore.jpg"));
				iiImagem.setImage(iiImagem.getImage().getScaledInstance(dimension.width, dimension.height, 100));
			} else
				iiImagem = new ImageIcon();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			iiImagem.paintIcon(this, g, 0, 0);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(iiImagem.getIconWidth(), iiImagem.getIconHeight());
		}
	}
}
