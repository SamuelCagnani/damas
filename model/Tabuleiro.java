package model;

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

    public char getEstadoCasa(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setEstadoCasa(char estado, int linha, int coluna) {
        matriz[linha][coluna] = estado;
    }
}
