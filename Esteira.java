public class Esteira {
    private String item;
    private boolean emMovimento;
    private int capacidadeMaxima;

    public Esteira(boolean movimento, int capacidade){
        emMovimento=movimento;
        capacidadeMaxima=capacidade;
    }

    public void ligar(){
        emMovimento=true;
    }

    public void desligar(){
        emMovimento=false;
    }

    public boolean adicionarItem(String Id, int volume){
        if(!emMovimento){
            System.out.println("Esteira parada, não recebe item sem movimento!");
            return false;
        }else if(!verificarCapacidade(volume)){
            return false;
        }else if(item!=null){
            System.out.println("Esteira já ta com outro item :(");
            return false;
        }else{
            item=Id;
            System.out.println("Item adicionado a esteira...");
            return true;
        }
    }

    public String removerItem(){
        String itemFora=item;
        item=null;
        return itemFora;
    }

    public boolean verificarCapacidade(int tamanho){
        if(tamanho>capacidadeMaxima){
            System.out.println("Item ultrapassou a capacidade da esteira!");
            return false;
        }else{
            return true;
        }
    }
}
