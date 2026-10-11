public class MateriaPrima {
    private String id;
    private String nome;
    private int quantidade;
    private String unidade;
    private int custoPorUnidade;
    
    public MateriaPrima(String ID, String nomeado, String novaUnidade, int estoque, int custoDoMaterial){
        id=ID;
        nome=nomeado;
        unidade=novaUnidade;
        quantidade=estoque;
        custoPorUnidade=custoDoMaterial;
    }

    public boolean consumir(int demanda){
        if (verificarDisponibilidade(demanda)){
            quantidade-=demanda; //atualiza o estoque, já consumindo o demandado
            System.out.println("Estoque atualizado!");
            return true;
        }return false;
    }

    public void adicionarEstoque(int encomenda){
        if(encomenda<=0){
            System.out.println("Aviso! \nValor nulo ou negativo de encomenda de MP");
        }
        else{
            quantidade+=encomenda;
            System.out.println("Estoque atualizado...");
        }
    }

    public boolean verificarDisponibilidade(int demanda){
        if (demanda<=0){
            System.out.println("Aviso! \nDemanda nula ou negativa");//não é pra acontecer, mas por segurança existe
        }else if(quantidade<demanda){
            System.out.println("Aviso! \nPuts... Demanda ultrapassou o estoque de MP");//MP é matéria-prima
        }else{
            return true;
        }
        return false;
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public int getQuantidade(){
        return quantidade;
    }
    public String getUnidade() {
        return unidade;
    }

    public int getCustoPorUnidade(){
        return custoPorUnidade;
    }
}