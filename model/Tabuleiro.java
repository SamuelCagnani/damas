package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Douglas
 */
public class Tabuleiro implements Cloneable {

    private char[][] matriz;
    private final int TAMANHO = 6;

    public Tabuleiro() {
        this.matriz = new char[TAMANHO][TAMANHO];
        inicializar();
    }

    private void inicializar() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 2) {
                        matriz[i][j] = Peca.preta; // Pretas
                    } else if (i > 3) {
                        matriz[i][j] = Peca.branca; // Brancas
                    } else {
                        matriz[i][j] = Peca.vazia;
                    }
                } else {
                    matriz[i][j] = Peca.invalida; // Casas que não podem ser usadas no jogo
                }
            }
        }
    }

    @Override
    public Tabuleiro clone() {
        try {
            Tabuleiro clone = (Tabuleiro) super.clone();
            clone.matriz = new char[TAMANHO][];
            for (int i = 0; i < TAMANHO; i++) {
                clone.matriz[i] = this.matriz[i].clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    /*
        Implementação dos métodos - getMovimentosPossiveis(), fazerMovimento(), etc
    */
    public boolean movimentoValido(int l1, int c1, int l2, int c2) {

        char origem = this.matriz[l1][c1];
        char destino = this.matriz[l2][c2];

        // Se a casa destino não estiver vazia
        if (destino != Peca.vazia) return false;

        // Se a casa de origem não possuir uma peça
        if (!Peca.isPeca(origem)) return false;

        // Validação de movimento separada para damas e peças normais
        if (Peca.isDama(origem)) {
            return validarMovimentoDama(l1, c1, l2, c2);
        } else {
            return validarMovimentoPeca(l1, c1, l2, c2);
        }
    }

    // Método de validação de movimento das peças
    private boolean validarMovimentoPeca(int l1, int c1, int l2, int c2) {

        // Mede as variações de linha e coluna
        int deltaLinha = l1 - l2;
        int deltaColuna = Math.abs(c1 - c2);

        // Obriga a peça a se mover na diagonal, alterando a coluna de 1 em 1
        if(deltaColuna != 1) return false;

        char estado = matriz[l1][c1];

        if(estado == Peca.branca) {
            return deltaLinha == 1;
        }

        if(estado == Peca.preta) {
            return deltaLinha == -1;
        }

        return false;
    }

    // Método de validação de movimento das damas
    private boolean validarMovimentoDama(int l1, int c1, int l2, int c2) {

        int deltaLinha = l2 - l1;
        int deltaColuna = c2 - c1;

        // Garante que o movimento seja na diagonal
        if(Math.abs(deltaLinha) != Math.abs(deltaColuna)) return false;

        int moduloLinha = Integer.signum(deltaLinha);
        int moduloColuna = Integer.signum(deltaColuna);

        int linhaAtual = l1 + moduloLinha;
        int colunaAtual = c1 + moduloColuna;

        while(linhaAtual != l2 && colunaAtual != c2) {

            if(matriz[linhaAtual][colunaAtual] != Peca.vazia) return false;

            linhaAtual += moduloLinha;
            colunaAtual += moduloColuna;
        }

        return true;
    }

    public void copiarEstado(Tabuleiro origem) {
        for (int i = 0; i < TAMANHO; i++)
            this.matriz[i] = origem.matriz[i].clone();
    }

    public char getEstadoCasa(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setEstadoCasa(char estado, int linha, int coluna) {
        matriz[linha][coluna] = estado;
    }

    public List<Tabuleiro> gerarProximosEstados(Jogador jogador) {
        List<Tabuleiro> capturas = gerarEstadosCaptura(jogador);
        if (!capturas.isEmpty()) return capturas;

        List<Tabuleiro> estados = new ArrayList<>();
        int[] deltas = {-1, 1};

        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                if (!Peca.pertenceAo(jogador, matriz[l][c])) continue;
                for (int dl : deltas) {
                    for (int dc : deltas) {
                        int nl = l + dl, nc = c + dc;
                        if (nl < 0 || nl >= TAMANHO || nc < 0 || nc >= TAMANHO) continue;
                        if (!movimentoValido(l, c, nl, nc)) continue;
                        Tabuleiro copia = this.clone();
                        char peca = this.getEstadoCasa(l, c);
                        copia.setEstadoCasa(peca, nl, nc);
                        copia.setEstadoCasa(Peca.vazia, l, c);
                        estados.add(copia);
                    }
                }
            }
        }
        return estados;
    }

    private List<Tabuleiro> gerarEstadosCaptura(Jogador jogador) {
        // Cada entrada: [tabuleiro intermediário, linha atual da peça, coluna atual, capturas feitas]
        List<Object[]> sequencias = new ArrayList<>();

        int[] deltas = {-1, 1};
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                if (!Peca.pertenceAo(jogador, matriz[l][c])) continue;
                expandirCaptura(this, l, c, jogador, 0, sequencias, deltas);
            }
        }

        if (sequencias.isEmpty()) return new ArrayList<>();

        int max = sequencias.stream().mapToInt(e -> (int) e[3]).max().getAsInt();
        List<Tabuleiro> estados = new ArrayList<>();
        for (Object[] seq : sequencias) {
            if ((int) seq[3] == max) estados.add((Tabuleiro) seq[0]);
        }
        return estados;
    }

    private void expandirCaptura(Tabuleiro tab, int l, int c, Jogador jogador,
                                  int capturas, List<Object[]> resultado, int[] deltas) {
        boolean expandiu = false;

        if (Peca.isDama(tab.matriz[l][c])) {
            for (int dl : deltas) {
                for (int dc : deltas) {
                    // Varrer diagonal até encontrar a primeira peça
                    int lm = l + dl, cm = c + dc;
                    while (lm >= 0 && lm < TAMANHO && cm >= 0 && cm < TAMANHO
                            && tab.matriz[lm][cm] == Peca.vazia) {
                        lm += dl; cm += dc;
                    }
                    if (lm < 0 || lm >= TAMANHO || cm < 0 || cm >= TAMANHO) continue;
                    if (!Peca.ehAdversario(jogador, tab.matriz[lm][cm])) continue;

                    // Pousar em cada casa vazia após o adversário
                    int ld = lm + dl, cd = cm + dc;
                    while (ld >= 0 && ld < TAMANHO && cd >= 0 && cd < TAMANHO
                            && tab.matriz[ld][cd] == Peca.vazia) {
                        Tabuleiro copia = tab.clone();
                        copia.setEstadoCasa(copia.getEstadoCasa(l, c), ld, cd);
                        copia.setEstadoCasa(Peca.vazia, lm, cm);
                        copia.setEstadoCasa(Peca.vazia, l, c);
                        expandiu = true;
                        expandirCaptura(copia, ld, cd, jogador, capturas + 1, resultado, deltas);
                        ld += dl; cd += dc;
                    }
                }
            }
        } else {
            for (int dl : deltas) {
                for (int dc : deltas) {
                    int lm = l + dl, cm = c + dc;
                    int ld = l + 2 * dl, cd = c + 2 * dc;
                    if (ld < 0 || ld >= TAMANHO || cd < 0 || cd >= TAMANHO) continue;
                    if (!Peca.ehAdversario(jogador, tab.matriz[lm][cm])) continue;
                    if (tab.matriz[ld][cd] != Peca.vazia) continue;

                    Tabuleiro copia = tab.clone();
                    copia.setEstadoCasa(copia.getEstadoCasa(l, c), ld, cd);
                    copia.setEstadoCasa(Peca.vazia, lm, cm);
                    copia.setEstadoCasa(Peca.vazia, l, c);
                    expandiu = true;
                    expandirCaptura(copia, ld, cd, jogador, capturas + 1, resultado, deltas);
                }
            }
        }

        if (!expandiu && capturas > 0) {
            resultado.add(new Object[]{tab, l, c, capturas});
        }
    }
}
