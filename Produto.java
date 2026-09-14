public abstract class Produto {
    private String id;
    private String nome;
    private String status="parado"; //"parado", "produzido" e "avaliado"
    private int quantidadeMateriaPrimaPorUnidade;
    private float qualidade;
    private float probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados;
    
    public Produto(String ID, String nomeado, float qualidadee){
        id=ID;
        nome=nomeado;
        if(qualidadee<0){
            qualidadee=0;
        }else if(qualidadee>1){
            qualidadee=1;
        }
        qualidade=qualidadee;
        probabilidadeFalhaAcumulada=qualidade;
        totalProdutosFabricados++;
    }

    public abstract boolean processar();

    public abstract int calcularTempoProducao(); 
    
    public abstract String getTipo();

    public void avaliar(){
        status="avaliado";
    }

    public void setStatus(String statusNovo){
        status=statusNovo;
    }

    public void definirDemandaMateriaPrima(int demanda){
        if (demanda<=0){
            System.out.println("ERRO\nTentativa de demanda definida nula ou negativa");
        }else{
            quantidadeMateriaPrimaPorUnidade=demanda;
            System.out.println("Matéria Prima necessária atualizada");
        }
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
}