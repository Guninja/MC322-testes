public enum Cenario { //onde estamos hollywood, ceu ou inferno?
    IDEAL (10000, 0.05f, 1),
    APOCALIPTICO (300, 0.40f, 6);

    private final int budgetInicial;
    private final float fatorFalha;
    private final int fatorDesgaste;

    Cenario(int budgetInicial, float fatorFalha, int fatorDesgaste){
        this.budgetInicial=budgetInicial;
        this.fatorFalha=fatorFalha;
        this.fatorDesgaste=fatorDesgaste;
    }

    public int getBudgetInicial(){ //saldo positivo pra nao se individar
        return budgetInicial;
    }

    public float getFatorFalha(){ //esse ai falha demais, slc
        return fatorFalha;
    }

    public int getFatorDesgaste(){ //horrivel quando nao produz, isso é a origem do problema
        return fatorDesgaste;
    }
}