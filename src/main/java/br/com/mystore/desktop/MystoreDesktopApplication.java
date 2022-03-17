package br.com.mystore.desktop;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.mystore.view.LoginView;

public class MystoreDesktopApplication {

	public static void main(String[] args) {
		System.out.println("Iniciando a aplicação.");
        try {
            System.out.println("Início da configuração de visualização da interface.");
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            System.out.println("Fim da configuração de visualização da interface.");
            new LoginView().setVisible(true);
            System.out.println("Aplicação iniciada com sucesso!.");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println(String.format("Erro ao iniciar a aplicação: %s. Finalizando a aplicação.", e.getMessage()));
            System.exit(0);
        }
	}

}
