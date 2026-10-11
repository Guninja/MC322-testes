public class MaquinaCorteLaser extends Maquina {
    public MaquinaCorteLaser(String nomeMaq, int capMax, float prob, int custoOpe, int fatorDesgaste){
        super(nomeMaq, capMax, prob, custoOpe, fatorDesgaste);
    }


    public boolean processar(Produto obraPrima, MateriaPrima material){
        int demandaMaterial=obraPrima.getQuantidadeMateriaPrimaPorUnidade();
        if(!estaLigada()){
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        }else if(getCapacidadeMaxima()<demandaMaterial){
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        }else if(material.consumir(demandaMaterial)){
            desgate();// aplicar o desgate na maquina
            if (verificarFalha()){
               obraPrima.aumentarProbabilidadeFalha(0.06f); 
            }
            return obraPrima.processar();
        }else{
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }
        return false;
    }


    public String getTipo(){
        return "Maquina de corte a Laser";
    }
}