package br.com.mystore.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import br.com.mystore.api.model.UsuarioAutenticadoModel;
import br.com.mystore.utils.DataHora;

public class PrincipalView2 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel jlUsuario, jlIdEmpresa, jlData, jlHora, jlSessaoIdEmpresa, jlSessaoData, jlSessaoHora, jlStatus,
			jlSessaoStatus;
	private JPanel jpRodape;
	private JTextField jtfUsuario;
	private JMenuBar jmbBarra;
	private JMenu jmCadastro, jmConsultas;
	private JMenuItem jmiCadastroEmpresa, jmiConsultasEmpresa;
	private static DesktopView desktopView;
	private UsuarioAutenticadoModel usuario;
	private JToolBar jtbFerramentas;
	private JButton jbEmpresa,jbSair, jbSobre;

	public PrincipalView2(UsuarioAutenticadoModel usuario) {
		this.usuario = usuario;
		this.setTitle("Área de Trabalho - My Store");
		this.setSize(800, 700);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setJMenuBar(barraMenu());

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(centro(), BorderLayout.CENTER);
		this.getContentPane().add(rodape(usuario), BorderLayout.SOUTH);
	}

	private JToolBar ferramentas() {
		jtbFerramentas = new JToolBar("Ferramentas", JToolBar.HORIZONTAL);

		jbEmpresa = new JButton();
		jbEmpresa.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/assets/empresa.png")));
		jbEmpresa.setToolTipText("Cadastro de Epresas");
		jbEmpresa.setFocusable(false);
		jbEmpresa.addActionListener(this);
		jtbFerramentas.add(jbEmpresa);
		
		jbSobre = new JButton();
		jbSobre.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/assets/sobre.png")));
		jbSobre.setToolTipText("Sobre");
		jbSobre.setFocusable(false);
		jbSobre.addActionListener(this);
		jtbFerramentas.add(jbSobre);

		jbSair = new JButton();
		jbSair.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/assets/sair.png")));
		jbSair.setToolTipText("Sair");
		jbSair.setFocusable(false);
		jbSair.addActionListener(this);
		jtbFerramentas.add(jbSair);

		return jtbFerramentas;
	}

	private JMenuBar barraMenu() {
		jmbBarra = new JMenuBar();

		//jmbBarra.add(menuCadastro());

		//jmbBarra.add(menuConsultas());

		jmbBarra.add(ferramentas());
		return jmbBarra;
	}

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
	private JMenu menuConsultas() {
		jmConsultas = new JMenu("Consultas");
		jmConsultas.setMnemonic('s');
		jmbBarra.add(jmCadastro);

		jmiConsultasEmpresa = new JMenuItem("Consultas de Empresas");
		jmiConsultasEmpresa.setMnemonic('E');
		jmiConsultasEmpresa.addActionListener(this);
		jmConsultas.add(jmiConsultasEmpresa);

		return jmConsultas;
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
			if (e.getSource() == jbEmpresa) {
				addFrame(new EmpresaView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbSair) {
				fecharAplicacao();
			} else if (e.getSource() == jbSobre) {
				new SobreView();
			} else {
				JOptionPane.showMessageDialog(null, "Ação não implementada", "Operação impossível", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro encontrado: " + ex.getMessage(), "Operação falhou", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void fecharAplicacao() {
		System.exit(0);
		if (desktopView.getAllFrames().length > 0) {
			JOptionPane
					.showMessageDialog(
							null, "Não é possível fechar o sistema," + "\npor que há "
									+ desktopView.getAllFrames().length + " janela(s) aberta(s)",
							"Operação impossível", JOptionPane.ERROR_MESSAGE);
		} else {
			int confirmaSaida = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja fechar" + "\n o sistema?", "Saindo...", JOptionPane.YES_OPTION);
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
