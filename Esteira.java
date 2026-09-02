public class Esteira {
    private String item;
    private boolean emMovimento = false;
    private int capacidadeMaxima;


    public Esteira(String transportado, int limitePeso){
        item = transportado;
        capacidadeMaxima = limitePeso;
    }

    public void ligar(){
        emMovimento = true;
    }

    public void desligar(){
        emMovimento = false;
    }



    public boolean verificarCapacidade(int pesoAdicionado){
        if (pesoAdicionado > capacidadeMaxima) {
            return false;
        }
        else{
            return true;
        }
    }



    public boolean adicionarItem(String novoItem, int pesoAdicionado){
        if (emMovimento) {
            if(item == null){
                if(verificarCapacidade(pesoAdicionado)){
                    item = novoItem;
                    System.out.println(novoItem + "'está na esteira");
                    return true;
                }
                else{
                    System.out.println("Peso maior do que a capacidade maxima");
                    return false;
                }
            }
            else{
                System.out.println("Esteira ocupada, aguarde!");
                return false;
            }
        }
        else{
            System.out.println("Esteira desligada");
            return false;
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
