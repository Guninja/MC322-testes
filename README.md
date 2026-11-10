MC322 - Fábrica de Latarias de Carro

Projeto desenvolvido para a disciplina MC322 (Programação Orientada a Objetos) na Universidade Estadual de Campinas (UNICAMP).

Autores:

Luís Gustavo

Julio Marcos

Sobre o Projeto:

O projeto consiste na simulação do funcionamento de uma indústria de fabricação de peças e latarias automotivas (envolvendo latarias brutas, galvanizadas e blindadas), abordando controle de qualidade, probabilidade de falhas e desgaste de máquinas, além de priorização de demandas de produção.

Histórico de Evolução (Release x = Tarefa x):

Tarefa 1: Concepção do Tema

Escolha do tema de fábrica de peças automotivas inspirado em vivências na auto-escola e experiências na empresa júnior MECATRON.

Mapeamento inicial das matérias-primas (metais) e produtos finais (portas, capôs, tetos).

Tarefa 2: Estruturação Inicial da Linha de Produção

Ajuste no escopo para focar na produção de latarias completas em diferentes níveis de qualidade.

Implementação da linha de produção (esqueleto, corte e inspeção de verificação).

Criação de fila de demandas de produção (enfileiramento e desenfileiramento).

Tarefa 3: Cenários, Desgaste e Estratégias

Introdução de mecânicas de cenários (ideal vs. apocalíptico), alterando saldo inicial e taxas de falha/desgaste das máquinas.

Implementação de estratégias de seleção de produção:

FIFO (ordem de chegada);

Maior Demanda (maior quantidade de produtos);

Máximo de Produtos (produzir o máximo de demandas da fila).

Relatório e auditoria de desgaste progressivo da saúde das máquinas.

Submenus interativos para navegação via terminal.

Tarefa 4: Exceções Personalizadas e Javadoc

Refatoração do tratamento de erros: substituição de verificações simples por exceções personalizadas (exceptions).

Documentação integral do código-fonte utilizando Javadoc.

Tarefa 5: Refatoração de Arquitetura e Modelagem

Reorganização das responsabilidades das classes aplicando conceitos de agregação e composição.

Adição da funcionalidade de manutenção preventiva/corretiva no menu de gerenciamento.

Elaboração do diagrama de classes UML (disponível em .png, .pdf e .drawio na raiz do repositório).

Tarefa 6: Reestruturação Gradle e Testes Unitários (JUnit 5)

Reorganização do projeto no padrão do Gradle sob o pacote br.unicamp.ic.mc322:

src/main/java: código-fonte da aplicação.

src/test/java: suíte de testes unitários.

Implementação de testes unitários automatizados com JUnit 5 cobrindo:

Modelos (MaquinaTeste, LinhaDeProducaoTeste, DemandaTeste);

Estratégias de produção (EstrategiaProducaoTeste);

Tratamento de exceções e cópias defensivas em listas.

Como Executar e Compilar (Gradle)

Todos os comandos devem ser executados na raiz do projeto:

Executar os testes unitários (JUnit 5):

./gradlew test


Gerar a documentação Javadoc:

./gradlew javadoc


(A documentação será gerada na pasta build/docs/javadoc/index.html)

Compilar o projeto:

./gradlew build


Informações Adicionais

Para detalhes aprofundados sobre a motivação da escolha do tema, visão de expansão futura da fábrica e decisões de projeto tomadas ao longo de cada etapa, consulte o arquivo justificativa.txt na raiz do repositório.