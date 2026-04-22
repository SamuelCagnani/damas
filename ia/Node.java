package ia;

import model.Jogador;
import model.Tabuleiro;
import java.util.ArrayList;
import java.util.List;

public class Node {

    private Tabuleiro tabuleiro;
    private Jogador jogador;
    private List<Node> filhos;
    private double valor;
    private Node melhorFilho;

    public Node(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.filhos = new ArrayList<>();
        this.valor = 0;
    }

    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public Jogador getJogador() { return jogador; }
    public List<Node> getFilhos() { return filhos; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Node getMelhorFilho() { return melhorFilho; }
    public void setMelhorFilho(Node melhorFilho) { this.melhorFilho = melhorFilho; }
}
