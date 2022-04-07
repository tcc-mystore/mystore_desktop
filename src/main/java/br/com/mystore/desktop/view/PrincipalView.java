package br.com.mystore.desktop.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import br.com.mystore.desktop.api.model.UsuarioAutenticadoModel;
import br.com.mystore.desktop.utils.DataHora;

public class PrincipalView extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private JLabel jlUsuario, jlIdEmpresa, jlData, jlHora, jlSessaoIdEmpresa, jlSessaoData, jlSessaoHora, jlStatus,
			jlSessaoStatus;
	private JPanel jpRodape;
	private JTextField jtfUsuario;
	private JMenuBar jmbBarra;
	private static DesktopView desktopView;
	private UsuarioAutenticadoModel usuario;
	private JToolBar jtbFerramentas, jtbEstatisticas;
	private JButton jbEstatistica, jbEstado, jbCidade, jbUsuario, jbGrupo, jbPermissao, jbEmpresa, jbSair, jbSobre;

	public PrincipalView(UsuarioAutenticadoModel usuario) {
		this.usuario = usuario;
		this.setTitle("Área de Trabalho - My Store");
		this.setSize(800, 700);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setJMenuBar(barraMenu());
		this.addWindowListener(this);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(rodape(usuario), BorderLayout.SOUTH);
		this.getContentPane().add(estatisticas(), BorderLayout.WEST);
		this.getContentPane().add(centro(), BorderLayout.CENTER);
	}

	private JToolBar ferramentas() {
		jtbFerramentas = new JToolBar("Ferramentas", JToolBar.HORIZONTAL);
		jtbFerramentas.setAlignmentX(SwingConstants.CENTER);

		jbEstado = new JButton();
		jbEstado.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/estado.png")));
		jbEstado.setToolTipText("Estados");
		jbEstado.setFocusable(false);
		jbEstado.addActionListener(this);
		jtbFerramentas.add(jbEstado);

		jbCidade = new JButton();
		jbCidade.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/cidade.png")));
		jbCidade.setToolTipText("Cidades");
		jbCidade.setFocusable(false);
		jbCidade.addActionListener(this);
		jtbFerramentas.add(jbCidade);

		jbUsuario = new JButton();
		jbUsuario.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/usuario.png")));
		jbUsuario.setToolTipText("Usuários");
		jbUsuario.setFocusable(false);
		jbUsuario.addActionListener(this);
		jtbFerramentas.add(jbUsuario);

		jbGrupo = new JButton();
		jbGrupo.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/grupos.png")));
		jbGrupo.setToolTipText("Grupos de Permissões");
		jbGrupo.setFocusable(false);
		jbGrupo.addActionListener(this);
		jtbFerramentas.add(jbGrupo);

		jbPermissao = new JButton();
		jbPermissao.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/permissao.png")));
		jbPermissao.setToolTipText("Permissões do Sistema");
		jbPermissao.setFocusable(false);
		jbPermissao.addActionListener(this);
		jtbFerramentas.add(jbPermissao);

		jbEmpresa = new JButton();
		jbEmpresa.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/empresa.png")));
		jbEmpresa.setToolTipText("Cadastro de Epresas");
		jbEmpresa.setFocusable(false);
		jbEmpresa.addActionListener(this);
		jtbFerramentas.add(jbEmpresa);

		jbSobre = new JButton();
		jbSobre.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/sobre.png")));
		jbSobre.setToolTipText("Sobre");
		jbSobre.setFocusable(false);
		jbSobre.addActionListener(this);
		jtbFerramentas.add(jbSobre);

		jbSair = new JButton();
		jbSair.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/sair.png")));
		jbSair.setToolTipText("Sair");
		jbSair.setFocusable(false);
		jbSair.addActionListener(this);
		jtbFerramentas.add(jbSair);

		return jtbFerramentas;
	}

	private JToolBar estatisticas() {
		jtbEstatisticas = new JToolBar("Estatísticas", JToolBar.VERTICAL);

		jbEstatistica = new JButton();
		jbEstatistica.setIcon(new ImageIcon(getClass().getResource("/br/com/mystore/desktop/assets/estatistica.png")));
		jbEstatistica.setToolTipText("Quantidade de Acessos Por Usuário");
		jbEstatistica.setFocusable(false);
		jbEstatistica.addActionListener(this);
		jtbEstatisticas.add(jbEstatistica);

		return jtbEstatisticas;
	}

	private JMenuBar barraMenu() {
		jmbBarra = new JMenuBar();

		jmbBarra.add(ferramentas());
		return jmbBarra;
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
			if (e.getSource() == jbUsuario) {
				addFrame(new UsuarioView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbCidade) {
				addFrame(new CidadeView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbEstado) {
				addFrame(new EstadoView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbEstatistica) {
				addFrame(new EstatisticaView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbGrupo) {
				addFrame(new GrupoView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbPermissao) {
				addFrame(new PermissaoView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbEmpresa) {
				addFrame(new EmpresaView(usuario.getAccess_token()).listar());
			} else if (e.getSource() == jbSair) {
				fecharAplicacao();
			} else if (e.getSource() == jbSobre) {
				new SobreView();
			} else {
				JOptionPane.showMessageDialog(null, "Ação não implementada", "Operação impossível",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
			if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja fechar" + "\n o sistema?", "Saindo...",
					JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
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

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		fecharAplicacao();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
