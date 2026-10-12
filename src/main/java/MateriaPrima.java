/**
 * Classe que representa a matéria-prima utilizada pela fábrica na linha de produção.
 * É responsável pelo gerenciamento de estoque físico, custos unitários e 
 * validação de disponibilidade de recursos antes da fabricação.
 * 
 * @author Luis Gustavo
 */
public class MateriaPrima {
    private String id;
    private String nome;
    private int quantidade;
    private String unidade;
    private int custoPorUnidade;
    
    /**
     * Construtor para inicialização de uma nova matéria-prima no sistema.
     * 
     * @param ID O identificador único da matéria-prima.
     * @param nomeado O nome descritivo do material.
     * @param novaUnidade A unidade de medida do material.
     * @param estoque A quantidade inicial disponível no armazém.
     * @param custoDoMaterial O valor em Robux(moeda) correspondente a uma unidade deste material.
     */
    public MateriaPrima(String ID, String nomeado, String novaUnidade, int estoque, int custoDoMaterial){
        id=ID;
        nome=nomeado;
        unidade=novaUnidade;
        quantidade=estoque;
        custoPorUnidade=custoDoMaterial;
    }

    /**
     * Consome uma determinada quantidade de matéria-prima do estoque.
     * A operação só é concluída se houver disponibilidade suficiente.
     * tem MP pra gastar? br consumir e atualiza o estoque, já consumindo o demandado.
     * @param demanda A quantidade de matéria-prima a ser consumida.
     * @return true se o consumo for efetivado com sucesso.
     * @throws IllegalArgumentException se a demanda solicitada for menor ou igual a zero.
     * @throws RecursoInsuficienteException se a demanda for maior que o estoque disponível.
     */
    public boolean consumir(int demanda){
        verificarDisponibilidade(demanda);
        quantidade-=demanda;
        System.out.println("Estoque atualizado!");
        return true;
    }

    /**
     * Adiciona uma nova quantidade de material ao estoque existente.
     * mais estoqueee de MP
     * @param encomenda A quantidade de matéria-prima adquirida para reposição.
     * @throws IllegalArgumentException se o valor da encomenda for menor ou igual a zero.
     */
    public void adicionarEstoque(int encomenda){
        if(encomenda<=0){
            throw new IllegalArgumentException("Valor nulo ou negativo de encomenda de MP, deveria ser positivo");
        }
        quantidade+=encomenda;
        System.out.println("Estoque atualizado...");
    }

    /**
     * Verifica se há quantidade de matéria-prima suficiente no estoque para atender a uma solicitação.
     * tem MP suficiente?
     * @param demanda A quantidade de matéria-prima exigida pelo processo.
     * @return true se o estoque for suficiente para cobrir a demanda.
     * @throws IllegalArgumentException se a demanda avaliada for menor ou igual a zero. não é pra acontecer, mas por segurança existe
     * @throws RecursoInsuficienteException se o estoque for inferior à demanda solicitada. tratando problema como delicadesa de médico
     */
    public boolean verificarDisponibilidade(int demanda){
        if (demanda<=0){
            throw new IllegalArgumentException("Demanda de MP nula ou negativa, não pode!");
        }
        if(quantidade<demanda){
            throw new RecursoInsuficienteException("Puts... Demanda para produção ultrapassou o estoque de MP");
        }
        return true;
    }

    /**
     * Recupera o identificador único da matéria-prima.
     * 
     * @return Uma String contendo o ID de registro.
     */
    public String getId(){//comecou os gets
        return id;
    }

    /**
     * Recupera o nome ou o tipo da matéria-prima.
     * 
     * @return Uma String contendo o nome descritivo.
     */
    public String getNome(){
        return nome;
    }

    /**
     * Recupera o saldo atual da matéria-prima no armazém.
     * 
     * @return Um valor inteiro representando o estoque disponível.
     */
    public int getQuantidade(){
        return quantidade;
    }
    
    /**
    * Recupera a unidade de medida padrão vinculada a esta matéria-prima.
    * 
    * @return Uma String representando a unidade (ex: m^2).
    */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Recupera o custo de compra por unidade desta matéria-prima.
     * 
     * @return Um valor inteiro representando o custo unitário.
     */
    public int getCustoPorUnidade(){
        return custoPorUnidade;
    }
}