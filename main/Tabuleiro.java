package main;

/**
 * @author Douglas
 */
public class Tabuleiro implements Cloneable {

    private EstadoCasa[][] matriz;
    private final int TAMANHO = 6;

    public Tabuleiro() {
        this.matriz = new EstadoCasa[TAMANHO][TAMANHO];
        inicializar();
    }

    private void inicializar() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 2) {
                        matriz[i][j] = EstadoCasa.PRETA; // Pretas
                    } else if (i > 3) {
                        matriz[i][j] = EstadoCasa.BRANCA; // Brancas
                    } else {
                        matriz[i][j] = EstadoCasa.VAZIA;
                    }
                } else {
                    matriz[i][j] = EstadoCasa.INVALIDA; // Casas que não podem ser usadas no jogo
                }
            }
        }
    }

    @Override
    public Tabuleiro clone() {
        try {
            Tabuleiro clone = (Tabuleiro) super.clone();
            clone.matriz = new EstadoCasa[TAMANHO][];
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

        EstadoCasa origem = this.matriz[l1][c1];
        EstadoCasa destino = this.matriz[l2][c2];

        // Se a casa destino não estiver vazia
        if (destino != EstadoCasa.VAZIA) return false;

        // Se a casa de origem não possuir uma peça
        if (!origem.isPeca()) return false;

        // Validação de movimento separada para damas e peças normais
        if (origem.isDama()) {
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

        EstadoCasa estado = matriz[l1][c1];

        if(estado == EstadoCasa.BRANCA) {
            return deltaLinha == 1;
        }

        if(estado == EstadoCasa.PRETA) {
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

            if(matriz[linhaAtual][colunaAtual] != EstadoCasa.VAZIA) return false;

            linhaAtual += moduloLinha;
            colunaAtual += moduloColuna;
        }

        return true;
    }
    

    public EstadoCasa[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(EstadoCasa[][] matriz) {
        this.matriz = matriz;
    }
}
