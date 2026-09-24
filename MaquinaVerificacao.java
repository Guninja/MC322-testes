public class MaquinaVerificacao extends Maquina {
    public MaquinaVerificacao (String nomeMaq, int capMax, float prob, int custoOpe){
        super(nomeMaq, capMax, prob, custoOpe);
    }


    public boolean processar(Produto obraPrima, MateriaPrima material){
        int demandaMaterial=obraPrima.getQuantidadeMateriaPrimaPorUnidade();
        if(!estaLigada()){
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        }else if(getCapacidadeMaxima()<demandaMaterial){
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        }else if(material.consumir(demandaMaterial)){
            desgate();
            if (verificarFalha()){
               System.out.println("Produto reprovado e descartado, não passou nos testes de inspeção");
                return false; 
            }
            if (Math.random() < obraPrima.getProbabilidadeFalha()){
                System.out.println("Produto reprovado e descartado, não passou nos testes de inspeção");
                return false;
            }
            return obraPrima.processar();
        }else{
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }
        return false;
    }

    public String getTipo(){
        return "Maquina de Verificação";
    }
}