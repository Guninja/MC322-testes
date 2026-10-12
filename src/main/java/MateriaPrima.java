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

    public boolean consumir(int demanda){ //tem MP pra gastar? br consumir
        verificarDisponibilidade(demanda);
        quantidade-=demanda; //atualiza o estoque, já consumindo o demandado
        System.out.println("Estoque atualizado!");
        return true;
    }

    public void adicionarEstoque(int encomenda){//mais estoqueee de MP
        if(encomenda<=0){
            throw new IllegalArgumentException("Valor nulo ou negativo de encomenda de MP, deveria ser positivo");
        }
        quantidade+=encomenda;
        System.out.println("Estoque atualizado...");
    }

    public boolean verificarDisponibilidade(int demanda){ //tem MP suficiente?
        if (demanda<=0){
            throw new IllegalArgumentException("Demanda de MP nula ou negativa, não pode!");//não é pra acontecer, mas por segurança existe
        }
        if(quantidade<demanda){
            throw new RecursoInsuficienteException("Puts... Demanda para produção ultrapassou o estoque de MP");//tratando problema como delicadesa de médico
        }
        return true;
    }

    public String getId(){//comecou os gets
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