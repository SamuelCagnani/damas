package view;

import model.Peca;
import engine.Jogo;
import javax.swing.*;
import java.awt.*;

/**
 * @author Douglas
 */
public final class MainInterfaceGrafica extends JFrame {

    private final int TAMANHO = 6;
    private Jogo jogo;
    private final CasaBotao[][] tabuleiroInterface = new CasaBotao[TAMANHO][TAMANHO];
    private int linhaOrigem = -1, colunaOrigem = -1;

    public MainInterfaceGrafica(Jogo jogo) {

        this.jogo = jogo;
        /*
            TABULEIRO DO JOGO INTERFACE
        */
        setTitle("DISCIPLINA - IA - MINI JOGO DE DAMAS DO SAMUEL");
        setSize(800, 800);
        setLayout(new GridLayout(TAMANHO, TAMANHO));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        sincronizarInterface();
    }

    private void inicializarComponentes() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiroInterface[i][j] = new CasaBotao();

                // Cores do tabuleiro
                if ((i + j) % 2 == 0) {
                    tabuleiroInterface[i][j].setBackground(new Color(235, 235, 208)); // Bege
                } else {
                    tabuleiroInterface[i][j].setBackground(new Color(119, 149, 86));  // Verde
                }

                int linha = i;
                int coluna = j;
                tabuleiroInterface[i][j].addActionListener(e -> tratarClique(linha, coluna));
                add(tabuleiroInterface[i][j]);
            }
        }
    }

    private void tratarClique(int linha, int coluna) {

        // Caso 1: Nenhuma peça selecionada ainda
        if (linhaOrigem == -1) {

            // Verifica se a casa clicada possui uma peça e se essa peça é da cor do jogador atual
            if (jogo.verificaPossibilidadeJogada(linha, coluna)) {
                linhaOrigem = linha;
                colunaOrigem = coluna;
                tabuleiroInterface[linha][coluna].setBackground(new Color(246, 246, 105)); // Destaque do clique
            }
        } 
        // Caso 2: Já existe uma peça selecionada, tentando mover
        else {
            
            // Se clicar na mesma peça, cancela a seleção
            if (linhaOrigem == linha && colunaOrigem == coluna) {
                cancelarSelecao();
                return;
            }

            boolean sucesso = jogo.tentarMoverJogador(linhaOrigem, colunaOrigem, linha, coluna);

            if (sucesso) {
                cancelarSelecao();
                sincronizarInterface();

                /*
                    VERIFICAÇÃO DE QUEM É A VEZ DE JOGAR E IMPLEMENTAÇÃO DA JOGADA DA IA
                */
                
                
            } else {
                // Se o movimento for inválido (ex: clicar em cima de outra peça)
                cancelarSelecao();
            }
        }
    }

    private void cancelarSelecao() {
        if (linhaOrigem != -1 ) {
            // Restaura a cor original
            calcularCorCasa(linhaOrigem, colunaOrigem);
        }
        linhaOrigem = -1;
        colunaOrigem = -1;
    }

    // Problema de restaurar cor única resolvido
    private void calcularCorCasa(int i, int j){
        if((i + j) % 2 == 0) {
            tabuleiroInterface[i][j].setBackground(new Color(235, 235, 208));
        } else {
            tabuleiroInterface[i][j].setBackground(new Color(119, 149, 86));
        }
    }
    
    /*
     * Atualiza a interface gráfica com base na matriz lógica do Tabuleiro. Este
     * método será chamado após cada jogada da IA.
     */
    public void sincronizarInterface() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiroInterface[i][j].setTipoPeca(jogo.getEstadoCasa(i, j));
            }
        }
    }

    private static class CasaBotao extends JButton {

        private char tipoPeca = Peca.vazia;

        public void setTipoPeca(char tipo) {
            this.tipoPeca = tipo;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int margem = 10;
            // Brancas
            if (tipoPeca == Peca.branca || tipoPeca == Peca.dama_branca) {
                g2.setColor(Color.WHITE);
                g2.fillOval(margem, margem, getWidth() - 2 * margem, getHeight() - 2 * margem);
                g2.setColor(Color.BLACK);
                g2.drawOval(margem, margem, getWidth() - 2 * margem, getHeight() - 2 * margem);
            // Pretas
            } else if (tipoPeca == Peca.preta || tipoPeca == Peca.dama_preta) {
                g2.setColor(Color.BLACK);
                g2.fillOval(margem, margem, getWidth() - 2 * margem, getHeight() - 2 * margem);
            }

            // Representação de Dama (uma borda dourada)
            if (tipoPeca == Peca.dama_branca || tipoPeca == Peca.dama_preta) {
                g2.setColor(new Color(212, 175, 55));
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(margem + 5, margem + 5, getWidth() - 2 * margem - 10, getHeight() - 2 * margem - 10);
            }
        }
    }
}
