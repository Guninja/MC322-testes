public class Produto {
    private String id;
    private String nome;
    private String status="parado"; //"parado", "produzido" e "avaliado"
    private int quantidadeMateriaPrimaNecessaria; 
    
    public Produto(String ID, String nomeado){
        id=ID;
        nome=nomeado;
    }

    public void processar(){
        status="produzido";
    }

    public void definirDemandaMateriaPrima(int demanda){
        if (demanda<=0){
            System.out.println("ERRO\nTentativa de demanda definida nula ou negativa");
        }else{
            quantidadeMateriaPrimaNecessaria=demanda;
            System.out.println("Matéria Prima necessária atualizada");
        }
    }

    public int getDemandaMateriaPrima(){
        return quantidadeMateriaPrimaNecessaria;
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
}