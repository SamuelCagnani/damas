package engine;

import model.Tabuleiro;
import model.Peca;
import model.Jogador;

public class Jogo {

    private Jogador jogadorAtual = Jogador.BRANCO;
    private final Tabuleiro tabuleiroLogico;

    public Jogo() {

        tabuleiroLogico = new Tabuleiro();
    }

    public boolean verificaPossibilidadeJogada(int linhaOrigem, int colunaOrigem) {

        char estado = tabuleiroLogico.getEstadoCasa(linhaOrigem, colunaOrigem);

        return Peca.isPeca(estado) && Peca.pertenceAo(jogadorAtual, estado);
    }
    // Método utilizado para alternar turnos sempre que  umajogada é considerada um sucesso
    private void alternarJogador() {
        jogadorAtual = (jogadorAtual == Jogador.BRANCO) ? Jogador.PRETO : Jogador.BRANCO;
    }

    private boolean moverPecaLogica(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {

        char origem = tabuleiroLogico.getEstadoCasa(linhaOrigem, colunaOrigem);
        char destino = tabuleiroLogico.getEstadoCasa(linhaDestino, colunaDestino);

        if(!Peca.isPeca(origem) || !Peca.pertenceAo(jogadorAtual, origem)) return false;

        if(destino != Peca.vazia) return false;

        if(!tabuleiroLogico.movimentoValido(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) return false;

        tabuleiroLogico.setEstadoCasa(origem, linhaDestino, colunaDestino);
        tabuleiroLogico.setEstadoCasa(Peca.vazia, linhaOrigem, colunaOrigem);

        // Promoção simples para Dama (opcional)
        if (tabuleiroLogico.getEstadoCasa(linhaDestino, colunaDestino) == Peca.preta && linhaDestino == 5) {
            tabuleiroLogico.setEstadoCasa(Peca.dama_preta, linhaDestino, colunaDestino);
        } else if (tabuleiroLogico.getEstadoCasa(linhaDestino, colunaDestino) == Peca.branca && linhaDestino == 0) {
            tabuleiroLogico.setEstadoCasa(Peca.dama_branca, linhaDestino, colunaDestino);
        }

        return true;
    }

    public char getEstadoCasa(int linha, int coluna) {
        return tabuleiroLogico.getEstadoCasa(linha, coluna);
    }

    public boolean tentarMoverJogador(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {

        char origem = getEstadoCasa(linhaOrigem, colunaOrigem);

        if(!Peca.isPeca(origem) || !Peca.pertenceAo(jogadorAtual, origem)) return false;

        boolean capturaObrigatoria = existeCapturaDisponivel(jogadorAtual);
        boolean sucesso;

        if(capturaObrigatoria) {
            if(!isCapturaNormal(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, jogadorAtual)) return false;
            sucesso = executarCapturaSimples(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        } else {
            sucesso = moverPecaLogica(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        }

        if(sucesso) alternarJogador();

        return sucesso;
    }

    public boolean existeCapturaDisponivelParaJogadorAtual() {
        return existeCapturaDisponivel(jogadorAtual);
    }

    private boolean existeCapturaDisponivel(Jogador jogador) {
        for(int linha = 0; linha < 6; linha++) {
            for(int coluna = 0; coluna < 6; coluna++) {
                char peca = getEstadoCasa(linha, coluna);
                if(Peca.isPeca(peca) && Peca.pertenceAo(jogador, peca)) {
                    if(pecaTemCaptura(linha, coluna, jogador)) return true;
                }
            }
        }
        return false;
    }

    private boolean pecaTemCaptura(int linha, int coluna, Jogador jogador) {
        char peca = getEstadoCasa(linha, coluna);

        if(!Peca.isPeca(peca)) return false;

        int[] direcaoColuna = {-1, 1};
        int[] direcaoLinha = {-1, 1};

        for(int dirLinha : direcaoLinha) {
            for (int dirColuna : direcaoColuna) {
                int linhaMeio = linha + dirLinha;
                int colunaMeio = coluna + dirColuna;
                int linhaDestino = linha + (2 * dirLinha);
                int colunaDestino = coluna + (2 * dirColuna);

                if (!dentroTabuleiro(linhaMeio, colunaMeio) || !dentroTabuleiro(linhaDestino, colunaDestino)) continue;

                char pecaMeio = getEstadoCasa(linhaMeio, colunaMeio);
                char pecaDestino = getEstadoCasa(linhaDestino, colunaDestino);

                if (Peca.ehAdversario(jogador, pecaMeio) && pecaDestino == Peca.vazia) return true;
            }
        }
        return false;
    }

    private boolean dentroTabuleiro(int linha, int coluna){
        return linha >= 0 && linha < 6 && coluna >= 0 && coluna < 6;
    }

    private boolean isCapturaNormal(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Jogador jogador) {

        int deltaLinha = linhaDestino - linhaOrigem;
        int deltaColuna = colunaDestino - colunaOrigem;

        if(Math.abs(deltaLinha) != 2 || Math.abs(deltaColuna) != 2) return false;

        // if(jogador == Jogador.BRANCO && deltaLinha != -2) return false;
        // if(jogador == Jogador.PRETO && deltaLinha != 2) return false;

        int linhaMeio = (linhaOrigem + linhaDestino) / 2;
        int colunaMeio = (colunaOrigem + colunaDestino) / 2;

        return (Peca.ehAdversario(jogador, getEstadoCasa(linhaMeio, colunaMeio)) && getEstadoCasa(linhaDestino, colunaDestino) == Peca.vazia);
    }

    private boolean executarCapturaSimples(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {

        int linhaMeio = (linhaOrigem + linhaDestino) / 2;
        int colunaMeio = (colunaOrigem + colunaDestino) / 2;

        char origem = getEstadoCasa(linhaOrigem, colunaOrigem);

        tabuleiroLogico.setEstadoCasa(Peca.vazia, linhaOrigem, colunaOrigem);
        tabuleiroLogico.setEstadoCasa(Peca.vazia, linhaMeio, colunaMeio);
        tabuleiroLogico.setEstadoCasa(origem, linhaDestino, colunaDestino);

        promoverSeNecessario(linhaDestino, colunaDestino);

        return true;
    }

    private void promoverSeNecessario(int linha, int coluna) {
        if(jogadorAtual == Jogador.BRANCO && linha == 0) tabuleiroLogico.setEstadoCasa(Peca.dama_branca, linha, coluna);
        else if(jogadorAtual == Jogador.PRETO && linha == 5) tabuleiroLogico.setEstadoCasa(Peca.dama_preta, linha, coluna);
    }
}
