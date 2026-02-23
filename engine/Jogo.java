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

        boolean sucesso = moverPecaLogica(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        if(sucesso) alternarJogador();

        return sucesso;
    }

}
