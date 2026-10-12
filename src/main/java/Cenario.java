/**
 * Enumeração que define os cenários de inicialização da fábrica.
 * Cada cenário configura a dificuldade da simulação, alterando  orçamento inicial,
 * probabilidade de falhas e a taxa de desgaste das máquinas.
 * onde estamos hollywood, ceu ou inferno?
 * @author Luis Gustavo
 */
public enum Cenario {
    /**
     * Cenário favorável com alto orçamento inicial, baixa probabilidade de falhas 
     * e menor desgaste mecânico dos equipamentos.
     */
    IDEAL (10000, 0.05f, 1),
    /**
     * Cenário de crise com orçamento restrito, alta probabilidade de falhas 
     * e desgaste acelerado dos equipamentos industriais.
     */
    APOCALIPTICO (300, 0.40f, 6);

    private final int budgetInicial;
    private final float fatorFalha;
    private final int fatorDesgaste;

    /**
     * Construtor da enumeração Cenario.
     * Valida as entradas para garantir que o cenário inicie em um estado consistente.
     * 
     * @param budgetInicial O valor em Robux disponível para a fábrica iniciar suas operações.
     * @param fatorFalha A probabilidade (de 0 a 1 float) base de máquinas apresentarem defeito.
     * @param fatorDesgaste O valor deduzido da saúde da máquina a cada ciclo de produção.
     * @throws IllegalArgumentException se o orçamento ou o desgaste forem negativos, ou se a falha estiver fora do limite de 0.0 a 1.0.
     */
    Cenario(int budgetInicial, float fatorFalha, int fatorDesgaste){
        if(budgetInicial<0){
            throw new IllegalArgumentException("Erro: Saldo inicial do cenário não pode ser negativo.");
        }
        if(fatorFalha<0.0f || fatorFalha>1.0f){
            throw new IllegalArgumentException("Erro: Fator de falha deve ser entre 0 e 1 float.");
        }
        if(fatorDesgaste<0){
            throw new IllegalArgumentException("Erro: Fator de desgaste não pode ser negativo.");
        }
        this.budgetInicial=budgetInicial;
        this.fatorFalha=fatorFalha;
        this.fatorDesgaste=fatorDesgaste;
    }

    /**
     * Recupera o orçamento inicial definido pelo cenário.
     * saldo positivo pra nao se individar
     * @return Um número inteiro representando o saldo inicial da fábrica.
     */
    public int getBudgetInicial(){
        return budgetInicial;
    }

    /**
     * Recupera o multiplicador base de falha das máquinas no cenário ativo.
     * esse ai falha demais, slc
     * @return Um valor decimal representando a probabilidade estatística de falha.
     */
    public float getFatorFalha(){
        return fatorFalha;
    }

    /**
     * Recupera a taxa de desgaste aplicada às máquinas a cada operação executada.
     * horrivel quando nao produz, isso é a origem do problema
     * @return Um número inteiro representando os pontos de saúde mecânica perdidos.
     */
    public int getFatorDesgaste(){
        return fatorDesgaste;
    }
}