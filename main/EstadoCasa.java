package main;

public enum EstadoCasa {
    BRANCA,
    PRETA,
    DAMA_BRANCA,
    DAMA_PRETA,
    INVALIDA,
    VAZIA;

    public boolean isPeca() {
        return this == BRANCA || this == PRETA || this == DAMA_BRANCA || this == DAMA_PRETA;
    }

    public boolean isBranca() {
        return this == BRANCA || this == DAMA_BRANCA;
    }

    public boolean isDama() {
        return this == DAMA_BRANCA || this == DAMA_PRETA;
    }

    public boolean pertenceAo(Jogador jogador) {
        if(jogador == Jogador.BRANCO) {
            return this == BRANCA || this == DAMA_BRANCA;
        } else {
            return this == PRETA || this == DAMA_PRETA;
        }
    }
}
