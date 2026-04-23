package ia;

import model.Jogador;
import model.Tabuleiro;

public class Node {

    private final Tabuleiro tabuleiro;
    private final Jogador jogador;

    public Node(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
    }

    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public Jogador getJogador() { return jogador; }
}
