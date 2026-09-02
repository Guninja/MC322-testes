public class Esteira {
    private String item;
    private boolean emMovimento;
    private int capacidadeMaxima;

    public Esteira(boolean movimento, int limiteArea){
        emMovimento=movimento;
        capacidadeMaxima = limiteArea;
    }

    public void ligar(){
        emMovimento = true;
    }

    public void desligar(){
        emMovimento = false;
    }

    public boolean verificarCapacidade(int areaAdicionada){
        if (areaAdicionada > capacidadeMaxima) {
            System.out.println("Item ultrapassou a capacidade da esteira!");
            return false;
        }else{
            return true;
        }
    }

    public boolean adicionarItem(String novoItem, int areaAdicionada){
        if (!emMovimento) {
            System.out.println("Esteira desligada!!!");
            return false;
        } else if(item != null){
            System.out.println("Esteira ocupada, já ta com outro item :(");
            return false;
        } else if(!verificarCapacidade(areaAdicionada)){
            return false;
        } else{
            item = novoItem;
            System.out.println(novoItem + " está na esteira");
            return true;
        }
    }

    public String removerItem(){
        if (item != null){
            String itemremovido = item;
            item = null;
            System.out.println("item removido");
            return itemremovido;
        }
        else {
            System.out.println("Não tem o que remover, esteira ja esta vazia");
            return null;
        }
    }
}
