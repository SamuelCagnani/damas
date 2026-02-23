import view.MainInterfaceGrafica;
import javax.swing.SwingUtilities;
import engine.Jogo;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogo jogo = new Jogo();
            MainInterfaceGrafica ui = new MainInterfaceGrafica(jogo);
            ui.setVisible(true);
        });
    }
}
