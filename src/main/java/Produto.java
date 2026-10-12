/**
 * Classe abstrata que representa um produto genérico na linha de produção da fábrica.
 * Define a estrutura base, os atributos de qualidade e o contrato de auditoria para 
 * todas as peças fabricadas no sistema.
 * 
 * @author Luis Gustavo
 */
public abstract class Produto implements Auditavel {
    private String id;
    private String nome;
    private StatusProduto status=StatusProduto.PARADO; //"Parado" ou "Produzido"
    private int quantidadeMateriaPrimaPorUnidade;
    private float qualidade;
    private float probabilidadeFalhaAcumulada;
    private static int totalProdutosFabricados;
    
    /**
     * Construtor base para a inicialização de um novo produto.
     * 
     * @param ID O identificador único do produto (lote).
     * @param nomeado O nome descritivo do produto.
     * @param qualidadee O nível de qualidade do produto (deve estar entre 0.0 e 1.0).
     * @param quantidadeMateriaPrimaPorUnidade A quantidade de matéria-prima necessária para fabricar uma unidade.
     * @throws IllegalArgumentException se a qualidade estiver fora do intervalo válido ou se a quantidade de matéria-prima for menor ou igual a zero.
     */
    public Produto(String ID, String nomeado, float qualidadee, int quantidadeMateriaPrimaPorUnidade){
        id=ID;
        nome=nomeado;
        if(qualidadee < 0.0f || qualidadee > 1.0f){
            throw new IllegalArgumentException("Erro: qualidade deve estar entre 0 e 1 float.");
        }else if(qualidadee<=0){
            throw new IllegalArgumentException("Erro: quantidade de MP deve ser maior que 0");
        }
        qualidade=qualidadee;
        probabilidadeFalhaAcumulada=qualidade*0.30f;
        this.quantidadeMateriaPrimaPorUnidade=quantidadeMateriaPrimaPorUnidade;
        totalProdutosFabricados++;
    }

    /**
     * Executa o processamento específico de fabricação do produto.
     * 
     * @return true se o produto foi processado com sucesso, false caso contrário.
     */
    public abstract boolean processar();

    /**
     * Calcula o tempo estimado necessário para a produção da peça.
     * 
     * @return Um número inteiro representando o tempo de produção em minutos.
     */
    public abstract int calcularTempoProducao(); 
    
    /**
     * Recupera o tipo ou a categoria do produto fabricado.
     * 
     * @return Uma String indicando o tipo da peça.
     */
    public abstract String getTipo();
    
    /**
     * Atualiza o status atual do produto na linha de produção.
     * 
     * @param status O novo estado do produto (ex: PARADO ou PRODUZIDO).
     */
    public void setStatus(StatusProduto status){
        this.status=status;
    }

    /**
     * Aumenta a probabilidade acumulada de falha do produto devido a desgastes na máquina durante a produção.
     * aumenta a falha do produto
     * @param falhaMaquina O valor de probabilidade de falha a ser adicionado ao risco acumulado.
     * @throws IllegalArgumentException se o valor da falha fornecido for negativo.
     */
    public void aumentarProbabilidadeFalha(float falhaMaquina){
        if (falhaMaquina<0){
            throw new IllegalArgumentException("Erro: a falha a ser somada não pode ser negativo.");
        }
        probabilidadeFalhaAcumulada+=falhaMaquina;
    }
// agora vem os gets
    /**
     * Recupera a quantidade de matéria-prima exigida para fabricar este produto.
     * 
     * @return O custo de matéria-prima por unidade.
     */
    public int getQuantidadeMateriaPrimaPorUnidade(){
        return quantidadeMateriaPrimaPorUnidade;
    }

    /**
     * Recupera o identificador único do produto.
     * 
     * @return O ID do produto.
     */
    public String getId(){
        return id;
    }

    /**
     * Recupera o nome do produto.
     * 
     * @return Uma String com o nome.
     */
    public String getNome(){
        return nome;
    }

    /**
     * Recupera o status atual do produto no ciclo de vida da fábrica.
     * 
     * @return O objeto StatusProduto correspondente ao estado atual.
     */
    public StatusProduto getStatus(){
        return status;
    }

    /**
     * Recupera o índice de qualidade da peça.
     * 
     * @return Um valor decimal (float) representando a qualidade.
     */
    public float getQualidade(){
        return qualidade;
    }

    /**
     * Recupera a probabilidade total acumulada de falhas ou defeitos na peça.
     * 
     * @return O risco acumulado em formato decimal (float).
     */
    public float getProbabilidadeFalha(){
        return probabilidadeFalhaAcumulada;
    }

    /**
     * Recupera a contagem global de todos os produtos já fabricados pela planta.
     * 
     * @return O número total de instâncias geradas na fábrica.
     */
    public int getTotalProdutosFabricados(){
        return totalProdutosFabricados;
    }

    /**
     * Verifica se o produto ultrapassou o limite aceitável de risco de falhas.
     * 
     * @return true se o risco for maior que 30%, indicando necessidade de manutenção; false caso contrário.
     */
    public boolean precisaManutencao(){
        if (probabilidadeFalhaAcumulada>0.30f){
            return true;
        }
        return false;
    }

    /**
     * Retorna uma descrição em texto curto sobre a integridade da peça.
     * 
     * @return Uma String informando o estado atual da manutenção.
     */
    public String getRisco(){
        return precisaManutencao() ? "precisa de manutenção!" : "manutenção em dia :)";
    }

    /**
     * Consolida e gera um relatório completo sobre a peça, exibindo sua qualidade e status de risco.
     * gera string de relatorio com nome qualidade, falha acumulada  e status
     * @return Um texto formatado com o diagnóstico detalhado do produto.
     */
    public String gerarRelatorioDiagnostico(){
        String textoDiagnostico = precisaManutencao() ? "Lataria Danificada: precisa de manutenção!" : "Lataria com manutenção em dia :)";
        return "\n | Produto: "+nome+" | Qualidade: "+qualidade+" | Risco acumulado: "+probabilidadeFalhaAcumulada+" | Status: "+textoDiagnostico;
    }
}