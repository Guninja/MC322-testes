public enum Cenario {
    IDEAL (10000, 0.05f, 1),
    APOCALIPTICO (300, 0.30f, 6);

    private final int budgetInicial;
    private final float fatorFalha;
    private final int fatorDesgaste;

    Cenario(int budgetInicial, float fatorFalha, int fatorDesgaste){
        this.budgetInicial=budgetInicial;
        this.fatorFalha=fatorFalha;
        this.fatorDesgaste=fatorDesgaste;
    }

    public int getBudgetInicial(){
        return budgetInicial;
    }

    public float getFatorFalha(){
        return fatorFalha;
    }

    public int getFatorDesgaste(){
        return fatorDesgaste;
    }
}