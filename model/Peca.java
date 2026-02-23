package model;

public final class Peca {

    // Impede instância da classe
    private Peca() {}

    // Constantes que elevam a legibilidade do código
    public static final char vazia = ' ';
    public static final char branca = 'b';
    public static final char preta = 'p';
    public static final char dama_branca = 'B';
    public static final char dama_preta = 'P';
    public static final char invalida = '#';

    public static boolean isPeca(char x) {
        return x == branca || x == preta || x == dama_branca || x == dama_preta;
    }

    public static boolean isDama(char x) {
        return x == dama_branca || x == dama_preta;
    }

    public static boolean isBranca(char x) {
        return x == branca || x == dama_branca;
    }

    public static boolean pertenceAo(Jogador jogador, char estado) {
        if(jogador == Jogador.BRANCO) {
            return estado == branca || estado == dama_branca;
        } else {
            return estado == preta || estado == dama_preta;
        }
    }
}
