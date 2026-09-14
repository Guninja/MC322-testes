public class MaquinadeCorteLazer extends Maquina {
    public MaquinadeCorteLazer(String nomeMaq, int capMax, float prob, float custoOpe){
        super(nomeMaq, capMax, prob, custoOpe);
    }


    public boolean processar(Produto obraPrima, MateriaPrima material){
        int demandaMaterial=obraPrima.getDemandaMateriaPrima();
        if(!estaLigada()){
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        }else if(getCapacidadeMaxima()<demandaMaterial){
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        }else if(material.consumir(demandaMaterial)){
            if (verificarFalha()){
               obraPrima.aumentarProbabilidadeFalha(0.06); 
            }
            return obraPrima.processar();
        }else{
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }return false;
    }


    public String getTipo(){
        return "Maquina de corte a Lazer";
    }
}