public class MaquinaPrensaEstampagem extends Maquina {//é o processo de moldagem da lataria dos carros
    public MaquinaPrensaEstampagem(String nomeMaq, int capMax, float prob, int custoOpe){
        super(nomeMaq, capMax, prob, custoOpe);
    }

    public boolean processar(Produto obraPrima, MateriaPrima material){
        int demandaMaterial=obraPrima.getQuantidadeMateriaPrimaPorUnidade();
        if(!estaLigada()){
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        }else if(getCapacidadeMaxima()<demandaMaterial){
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        }else if(material.consumir(demandaMaterial)){
            if (verificarFalha()){
               obraPrima.aumentarProbabilidadeFalha(0.07f); 
            }
            return obraPrima.processar();
        }else{
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }return false;
    }

    public String getTipo(){
        return "Prensa de Estampagem";
    }
}