public abstract class Produto implements Auditavel {
    private String id;
    private String nome;
    private String status="parado"; //"parado", "produzido" e "avaliado"
    private int quantidadeMateriaPrimaPorUnidade;
    private float qualidade;
    private float probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados;
    
    public Produto(String ID, String nomeado, float qualidadee, int quantidadeMateriaPrimaPorUnidade){
        id=ID;
        nome=nomeado;
        if(qualidadee<0){
            qualidadee=0;
        }else if(qualidadee>1){
            qualidadee=1;
        }
        qualidade=qualidadee;
        probabilidadeFalhaAcumulada=qualidade*0.30f;
        totalProdutosFabricados++;
        this.quantidadeMateriaPrimaPorUnidade=quantidadeMateriaPrimaPorUnidade;
    }

    public abstract boolean processar();

    public abstract int calcularTempoProducao(); 
    
    public abstract String getTipo();
    
    public void setStatus(String statusNovo){
        status=statusNovo;
    }

    public void aumentarProbabilidadeFalha(float falhaMaquina){
        probabilidadeFalhaAcumulada+=falhaMaquina;
    }

    public int getQuantidadeMateriaPrimaPorUnidade(){
        return quantidadeMateriaPrimaPorUnidade;
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getStatus(){
        return status;
    }

    public float getQualidade(){
        return qualidade;
    }

    public float getProbabilidadeFalha(){
        return probabilidadeFalhaAcumulada;
    }

    public int getTotalProdutosFabricados(){
        return totalProdutosFabricados;
    }

    public boolean precisaManutencao(){
        if (probabilidadeFalhaAcumulada>0.30f){
            return true;
        }
        return false;
    }

    public String gerarRelatorioDiagnostico(){
        String textoDiagnostico = precisaManutencao() ? "Lataria Danificada: precisa de manutenção!" : "Lataria com manutenção em dia :)";
        return "Relatório...\n | Produto: "+nome+"\n | Qualidade: "+qualidade+"\n | Risco acumulado: "+probabilidadeFalhaAcumulada+"\n | Status: "+textoDiagnostico;
    }
}