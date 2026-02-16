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
    public boolean verificaMovimento () {
        
        return true;
    }
    

    public EstadoCasa[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(EstadoCasa[][] matriz) {
        this.matriz = matriz;
    }
}
