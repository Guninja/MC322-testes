public class Maquina {
    private String nome;//é o processo de moldagem da lataria dos carros
    private boolean ligada=false; //é o status da máquina se on/off
    private int capacidadeMaxima;

    public Maquina(String nomeado, int capacidade){
        nome=nomeado;
        capacidadeMaxima=capacidade;
    }

    public void ligar(){
        ligada=true;
    }

    public void desligar(){
        ligada=false;
    }

    public void processar(Produto obraPrima, MateriaPrima material, int quantidadeProdutos){
        int demandaMaterial=(quantidadeProdutos*obraPrima.getDemandaMateriaPrima());
        if(estaLigada()==false){
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        }else if(capacidadeMaxima<demandaMaterial){
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        }else if(material.consumir(demandaMaterial)){
            obraPrima.processar();
        }else{
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }
    }

    public String getNome(){
        return nome;
    }

    public boolean estaLigada(){
        return ligada;
    }
}
