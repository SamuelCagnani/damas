# Jogo de Damas – Java

## Status

🚧 Projeto em desenvolvimento.

Projeto em desenvolvimento ativo.

Este repositório contém a implementação de um jogo de damas em Java, com foco na modelagem correta das regras oficiais e na preparação estrutural para implementação de Inteligência Artificial.

---

## Objetivo

O projeto tem como principais objetivos:

- Aplicar Programação Orientada a Objetos
- Modelar regras formais de um jogo clássico
- Separar lógica de jogo e interface gráfica
- Preparar a base para implementação de IA

O desenvolvimento está sendo feito de forma incremental:

- Implementação da lógica base
- Interface gráfica funcional
- Implementação completa das regras
- Integração com Inteligência Artificial

---

## Representação do Tabuleiro

O tabuleiro será representado por uma matriz de char visando simplicidade e eficiência para simulação de estados futuros, onde:

- ' '  → Casa vazia
- 'b'  → Peça branca
- 'p'  → Peça preta
- 'B'  → Dama branca
- 'P'  → Dama preta
- '#'  → Casa inválida

---

## Funcionalidades Implementadas

O projeto está sendo estruturado com separação entre:

- Inicialização automática do tabuleiro 
- Movimentação diagonal de peças comuns 
- Movimentação múltipla de damas 
- Promoção automática 
- Alternância de turnos 
- Estrutura preparada para simulação de estados (IA)

---

## Regras Implementadas / Em Implementação

O projeto busca respeitar as regras oficiais de damas:

- Peças brancas iniciam a partida 
- Captura obrigatória (em implementação)
- Peças comuns não capturam para trás 
- Captura múltipla permitida 
- Damas:
  - Movem múltiplas casas 
  - Capturam para trás 
  - Permitem múltiplas capturas 
- Derrota quando o jogador não possui movimentos possíveis

---

## Próximos Passos

- Implementar sistema completo de captura 
- Tornar captura obrigatória 
- Permitir múltiplas capturas encadeadas 
- Gerar lista de movimentos possíveis por jogador 
- Implementar algoritmo Minimax 
- Adicionar poda Alpha-Beta 
- Implementar verificação automática de fim de jogo

---

## Observação

O projeto tem caráter acadêmico e está sendo desenvolvido para consolidar conhecimentos em Java e lógica de programação aplicada a jogos.
