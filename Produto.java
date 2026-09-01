public class Produto {
    private String id="Prod01";
    private String nome="Capô";
    private String status="parado"; //"parado", "produzido" e "avaliado"
    private int quantidadeMateriaPrimaNecessaria=2; 
    public void processar(){
        status="produzido";
        return;
    }
    public void definirDemandaMateriaPrima(int demanda){
        if (demanda<=0){
            System.out.println("ERRO\nTentativa de demanda definida nula ou negativa");
        }else{
            quantidadeMateriaPrimaNecessaria=demanda;
            System.out.println("Matéria Prima necessária atualizada");
        }return;
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