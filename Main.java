import view.InterfaceGrafica;
import javax.swing.SwingUtilities;
import engine.Jogo;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogo jogo = new Jogo();
            InterfaceGrafica ui = new InterfaceGrafica(jogo);
            ui.setVisible(true);
        });
    }
}
