# Jogo de Damas – Java

## Status

🚧 Projeto em desenvolvimento.

Este repositório contém o início da implementação de um jogo de damas em Java, com foco na modelagem correta das regras oficiais do jogo.

---

## Objetivo

Desenvolver um jogo de damas aplicando:

- Programação Orientada a Objetos
- Modelagem de regras de jogo
- Estruturação de lógica condicional
- Representação de tabuleiro em matriz

O projeto será construído de forma incremental, priorizando primeiro a lógica do jogo e depois a interface.

---

## Representação do Tabuleiro

O tabuleiro será representado por uma matriz de inteiros, onde:

- `0` → Casa vazia
- `1` → Peça branca
- `2` → Peça preta
- `3` → Dama branca
- `4` → Dama preta
- `-2` → Casas proibidas

---

## Regras que serão implementadas

- Definição do jogador que começa (peças brancas)
- Obrigatoriedade de captura
- Peças comuns não podem capturar para trás
- Captura múltipla permitida
- Damas podem:
    - Andar múltiplas casas
    - Capturar para trás
    - Capturar múltiplas peças
- A posição final da dama será determinada pela última peça capturada
- Caso um jogador fique sem movimentos possíveis, perde a partida

---

## Estrutura Atual

O projeto está sendo estruturado com separação entre:

- Lógica do tabuleiro
- Controle das regras
- Interface gráfica (em desenvolvimento)

---

## Próximos Passos

- Implementar movimentação básica
- Validar movimentos possíveis
- Implementar sistema de captura
- Criar controle de turnos
- Adicionar promoção para dama
- Implementar verificação de fim de jogo

---

## Observação

O projeto tem caráter acadêmico e está sendo desenvolvido para consolidar conhecimentos em Java e lógica de programação aplicada a jogos.
