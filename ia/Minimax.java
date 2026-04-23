package ia;

import model.Jogador;
import model.Peca;
import model.Tabuleiro;

import java.util.List;

public class Minimax {

    private static final int TAMANHO = 6;
    private final Jogador maquina;

    public Minimax(Jogador maquina) {
        this.maquina = maquina;
    }

    /**
     * Executa o Minimax com poda Alpha-Beta e retorna o melhor tabuleiro resultante.
     */
    public Tabuleiro executar(Tabuleiro tabuleiro, int profundidade) {
        List<Tabuleiro> proximos = tabuleiro.gerarProximosEstados(maquina);
        if (proximos.isEmpty()) return null;

        double melhor = Double.NEGATIVE_INFINITY;
        Tabuleiro melhorEstado = proximos.get(0);

        for (Tabuleiro filho : proximos) {
            double valor = alphaBeta(filho, profundidade - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
            if (valor > melhor) {
                melhor = valor;
                melhorEstado = filho;
            }
        }
        return melhorEstado;
    }

    private double alphaBeta(Tabuleiro tab, int profundidade, double alpha, double beta, boolean isMax) {
        Jogador jogadorAtual = isMax ? maquina : adversario();
        List<Tabuleiro> proximos = tab.gerarProximosEstados(jogadorAtual);

        if (profundidade == 0 || proximos.isEmpty()) {
            return heuristica(tab, proximos.isEmpty(), jogadorAtual);
        }

        if (isMax) {
            double valor = Double.NEGATIVE_INFINITY;
            for (Tabuleiro filho : proximos) {
                valor = Math.max(valor, alphaBeta(filho, profundidade - 1, alpha, beta, false));
                alpha = Math.max(alpha, valor);
                if (beta <= alpha) break; // poda beta
            }
            return valor;
        } else {
            double valor = Double.POSITIVE_INFINITY;
            for (Tabuleiro filho : proximos) {
                valor = Math.min(valor, alphaBeta(filho, profundidade - 1, alpha, beta, true));
                beta = Math.min(beta, valor);
                if (beta <= alpha) break; // poda alpha
            }
            return valor;
        }
    }

    private double heuristica(Tabuleiro tab, boolean semMovimentos, Jogador jogadorSemMovimento) {
        if (semMovimentos) {
            // Quem não tem movimentos perde
            return jogadorSemMovimento == maquina ? -1000 : 1000;
        }

        int score = 0;
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                char casa = tab.getEstadoCasa(l, c);
                if (!Peca.isPeca(casa)) continue;

                int valor = pecaValor(casa, l);
                if (Peca.pertenceAo(maquina, casa)) {
                    score += valor;
                } else {
                    score -= valor;
                }
            }
        }
        return score;
    }

    /**
     * Valor de uma peça considerando tipo e posição no tabuleiro.
     * Dama vale 5, peça comum vale 1 + bônus de avanço (0 a 2).
     */
    private int pecaValor(char peca, int linha) {
        if (Peca.isDama(peca)) return 5;

        // Bônus de avanço: peças mais próximas de promover valem mais
        int avanco;
        if (Peca.isBranca(peca)) {
            avanco = TAMANHO - 1 - linha; // brancas avançam para linha 0
        } else {
            avanco = linha; // pretas avançam para linha 5
        }
        // avanco varia de 0 a 5; normaliza para 0-2
        return 1 + (avanco * 2 / (TAMANHO - 1));
    }

    private Jogador adversario() {
        return maquina == Jogador.BRANCO ? Jogador.PRETO : Jogador.BRANCO;
    }
}
