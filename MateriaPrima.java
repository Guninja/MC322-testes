public class MateriaPrima {
    private String id="MeBa"; //Metal base
    private String nome="Metal";
    private int quantidade; //int pois os produtos vão pedir apenas por int para evitar erros
    private String unidade="metros"; // considerando chapas de metal q se compra em metro
    private int quantidadeMinima=1; //vai depender do produto, mas é 1, pois precisar pelo menos ter em estoque pra produzir algokkkk
    public void consumir(int demanda){
        if (demanda<=0 && quantidade<demanda){
            quantidade-=demanda; //atualiza o estoque, já consumindo o demandado
            System.out.println("Estoque atualizado!");
        }return;
    }
    public void adicionarEstoque(int encomenda){
        if(encomenda<=0){
            System.out.println("Aviso! \nValor nulo ou negativo de encomenda de MP");
        }
        else{
            quantidade+=encomenda;
            System.out.println("Estoque atualizado!");
        }
    }
    public boolean verificarDisponibilidade(int demanda){
        if (demanda<=0){
            System.out.println("Aviso! \nDemanda nula ou negativa");//não é pra acontecer, mas por segurança existe
        }else if(quantidade<demanda){
            System.out.println("ERRO ao consumir!!!\nPuts... Demanda ultrapassou o estoque de MP");//MP é matéria-prima
        }else{
            return 1;
        }
        return 0;
    }
    public String getID(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public int getQuantidade(){
        return quantidade;
    }
}
