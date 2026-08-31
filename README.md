# MC322-testes
Realese 1 == Tarefa 1

O primeiro objetivo é a contrução de uma planta de uma industria automatizada que é composta por matérias-primas, produtos, equipamentos e linha de produção.


Operações da planta industrial:

-Entrada MP, então armazenar no estoque.

-Transporte MP via esteira para a máquina de processamento, apenas se esteira em movimento.

-Processamento na máquina, MP transformada em produto, apenas se máquina ligada e MP suficiente.

- Transporte produto via esteira para a inspeção, apenas se esteira em movimento.

-Inspeção de qualidade, marca produto como aprovado

Portanto resulta na sequencia:
[Matéria-Prima] → [Esteira] → [Máquina] → [Esteira] → [Inspeção] → [Produto Final]

       ↓              ↓           ↓           ↓           ↓               ↓

    Estoque      Transporte   Transforma  Transporte   Verifica       Armazenado


Equipamentos possuem estados próprios e podem executar ações sobre os materiais.


Características importantes:
-Demanda de Matéria-Prima
-Controle de Estoque atualizado
-Estados dos Equipamentos
-Identificação de MP ou produto
-Entrada de Dados apenas NÚMERICA


Estrutura mínima do código:
    Classes:
        -MateriaPrima
            Atributos:
                -identificador
                -nome/tipo
                -quantidade em estoque
                -unidade de medida
                -quantidade mínima pra produzir
            Métodos:
                -consumir(quantidadeMP)
                -adicionarEstoque()
                -verificarDisponibilidade()
                -getID()
                -getNome()
                -getQuantidade()
        -Produto:
            Atributos:
                -identificador
                -nome/tipo
                -status
                -quantidadeMateriaPrimaNecessaria
            Métodos:
                -processar()
                -definirDemandaMateriaPrima(Quantidade)
                -getDemandaMateriaPrima()
                -getID()
                -getNome()
                -getStatus()
        -Maquina
            Atributos:
                -nome
                -ligado/status
                -capacidadeMaxima
            Métodos:
                -ligar()
                -desligar()
                -processar(tipoProduto, demandaMP)
                -getNome()
                -estaLigada()
        -Esteira
            Atributos:
                -item
                -emMovimento
                -capacidadeMaxima
            Métodos:
                -ligar()
                -desligar()
                -adicionarItem(item)
                -removerItem()
                -verificarCapacidade(item)
        -EstacaoInspecao
            Atributos:
                -ativa
                -produtosInspecionados
            Métodos:
                -ativar()
                -desativar()
                -inspecionar(produto)
                -getTotalInspecionados()
                
    Sitema no Terminal:
        Menu de opções numéricas que permita selecionar produto a produzir, definir demanda de MP e consultar estoque MP, além de informar o estado da produção durante a execução.
    Pontos de atenção:
        -Atributos privados, acesso via métodos públicos;
        -Maquina/esteira desligada não deve processar/transportar itens;
        -esteira com item não deve aceitar outro;
        
    Introdução:
        Nome da fábrica;
        Tipo de produto fabricado;
        Matéria-prima principal utilizada;
        Identificação da dupla (nomes dos integrantes).
        
        Por exemplo:
            ========================================
            F ́ABRICA DE SUCO NATURAL
            "Da fruta direto para o seu copo"
            ========================================
            Bem-vindos `a nossa f ́abrica de sucos!
            Aqui transformamos frutas frescas em
            sucos naturais e saborosos.
            Desenvolvido por: Marina Rocha e Carlos Souza
            ========================================
    Justificativa da escolha:
        Explicação do pq escolheu o tema da fábrica e uma visão de como a dupla imagina de espandir a indústria nas próximas tarefas.
    Testar no terminal com:
        javac -d bin $(find src -name "*.java$)
        java -cp bin Main
    "
    No main deve contér metodo a seguir: public static void main(String[] args)


Fim do README.md
Procure por justificativa.txt para ver mais sobre o projeto...
