package ia;

import model.Jogador;
import model.Peca;
import model.Tabuleiro;

import java.util.List;

public class Minimax {

    private final Jogador maquina;

    public Minimax(Jogador maquina) {
        this.maquina = maquina;
    }

    public Node executar(Node no, int profundidade) {
        List<Tabuleiro> proximos = no.getTabuleiro().gerarProximosEstados(no.getJogador());

        if (proximos.isEmpty()) {
            no.setValor(avaliarFinal(no.getJogador()));
            no.setMelhorFilho(null);
            return no;
        }

        if (profundidade == 0) {
            no.setValor(heuristica(no.getTabuleiro()));
            return no;
        }

        Jogador proximo = no.getJogador() == Jogador.BRANCO ? Jogador.PRETO : Jogador.BRANCO;
        boolean isMax = no.getJogador() == maquina;
        double melhor = isMax ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        Node melhorFilho = null;

        for (Tabuleiro tab : proximos) {
            Node filho = new Node(tab, proximo);
            executar(filho, profundidade - 1);
            no.getFilhos().add(filho);

            if (isMax) {
                if (filho.getValor() > melhor) { melhor = filho.getValor(); melhorFilho = filho; }
            } else {
                if (filho.getValor() < melhor) { melhor = filho.getValor(); melhorFilho = filho; }
            }
        }

        no.setValor(melhor);
        no.setMelhorFilho(melhorFilho);
        return no;
    }

    private double avaliarFinal(Jogador jogadorSemMovimento) {
        // Quem não tem movimentos perde
        if (jogadorSemMovimento == maquina) return -1;
        return 1;
    }

    // Pontuação máxima estimada: 6 damas × 3 pontos cada = 18
    private static final double SCORE_MAX = 18.0;

    private double heuristica(Tabuleiro tabuleiro) {
        int score = 0;
        int tamanho = 6;
        for (int l = 0; l < tamanho; l++) {
            for (int c = 0; c < tamanho; c++) {
                char casa = tabuleiro.getEstadoCasa(l, c);
                if (Peca.pertenceAo(maquina, casa)) {
                    score += Peca.isDama(casa) ? 3 : 1;
                } else if (Peca.isPeca(casa)) {
                    score -= Peca.isDama(casa) ? 3 : 1;
                }
            }
        }
        return score / SCORE_MAX;
    }
}
