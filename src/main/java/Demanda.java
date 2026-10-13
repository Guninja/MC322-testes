/**
 * Representa uma demanda de produção na planta industrial.
 * Modela a quantidade de produtos solicitados, tipo de material,
 * custo unitário e o ciclo de vida do estado da produção.
 * 
 * @author Júlio Marcos
 */
public class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private StatusDemanda status;
    private int custoProducaoUnitario;

    /**
     * Constrói uma nova demanda com status inicial PENDENTE.
     * 
     * @param tipoMetal Nome do tipo de produto/metal solicitado.
     * @param quantosProdutos Quantidade total de itens a serem produzidos.
     * @throws DemandaInvalidaException Se a quantidade for menor ou igual a zero ou o tipo do produto for nulo/vazio.
     */
    public Demanda(String tipoMetal, int quantosProdutos) {
        if (tipoMetal == null || tipoMetal.trim().isEmpty()) {
            throw new DemandaInvalidaException("O tipo do produto não pode ser nulo ou vazio.");
        }
        if (quantosProdutos <= 0) {
            throw new DemandaInvalidaException("A quantidade inicial de produtos deve ser maior que zero.");
        }
        this.tipoProduto = tipoMetal;
        this.quantidadeProdutos = quantosProdutos;
        this.status = StatusDemanda.PENDENTE;
    }

    /**
     * Atualiza a quantidade restante da demanda e gerencia a transição de status.
     * 
     * @param demanda Valor a ser somado/subtraído da quantidade atual de produtos.
     * @return A nova quantidade restante de produtos na demanda.
     * @throws DemandaInvalidaException Se a atualização resultar em uma quantidade negativa.
     */
    public int atualizarDemanda(int demanda) {
        if (this.quantidadeProdutos + demanda < 0) {
            throw new DemandaInvalidaException("A quantidade de produtos não pode ser reduzida abaixo de zero.");
        }
        if (this.status == StatusDemanda.PENDENTE) {
            ProducaoIniciada();
        }
        this.quantidadeProdutos = this.quantidadeProdutos + demanda;
        if (this.quantidadeProdutos == 0) {
            ProducaoFinalizada();
        } else {
            this.status = StatusDemanda.PENDENTE;
        }
        return this.quantidadeProdutos;
    }

    /**
     * Calcula a quantidade de matéria-prima necessária para produzir o total da demanda.
     * 
     * @param quantidadeMPporunidade Quantidade de matéria-prima gasta por unidade do produto.
     * @return Quantidade total de matéria-prima necessária.
     * @throws IllegalArgumentException Se a quantidade de matéria-prima por unidade for negativa.
     */
    public int calcularMPnecessaria(int quantidadeMPporunidade) {
        if (quantidadeMPporunidade < 0) {
            throw new IllegalArgumentException("A quantidade de matéria-prima por unidade não pode ser negativa.");
        }
        return quantidadeMPporunidade * quantidadeProdutos;
    }

    /**
     * Altera o status da demanda de PENDENTE para EM_PRODUÇÃO.
     * 
     * @return Mensagem confirmando o início da produção.
     * @throws DemandaInvalidaException Se a demanda não estiver no status PENDENTE.
     */
    public String ProducaoIniciada() {
        if (status == StatusDemanda.PENDENTE) {
            status = StatusDemanda.EM_PRODUCAO;
            return "Em produção...";
        } else {
            throw new DemandaInvalidaException("Não é possível iniciar a produção. A demanda precisa estar PENDENTE (Status atual: " + status + ").");
        }
    }

    /**
     * Transiciona o status da demanda de EM_PRODUÇÃO para CONCLUÍDA.
     * 
     * @return Mensagem de confirmação do encerramento.
     * @throws DemandaInvalidaException Se a demanda não estiver sendo produzida no momento.
     */
    public String ProducaoFinalizada() {
        if (status == StatusDemanda.EM_PRODUCAO) {
            status = StatusDemanda.CONCLUIDA;
            return "Demanda finalizada :)";
        } else {
            throw new DemandaInvalidaException("Não é possível finalizar a produção. A demanda não está em andamento (Status atual: " + status + ").");
        }
    }

    /**
     * Cancela a demanda caso ela ainda não tenha sido concluída.
     * 
     * @return Mensagem confirmando o cancelamento.
     * @throws DemandaInvalidaException Se a demanda já estiver concluída.
     */
    public String ProducaoCancelada() {
        if (status != StatusDemanda.CONCLUIDA) {
            status = StatusDemanda.CANCELADA;
            return "Que pena. Essa demanda está cancelada :(";
        } else {
            throw new DemandaInvalidaException("Não é possível cancelar uma demanda que já foi concluída.");
        }
    }

    /**
     * Define o custo unitário de produção do item.
     * 
     * @param custo Custo individual em valor inteiro.
     * @throws IllegalArgumentException Se o custo for um valor negativo.
     */
    public void setCustoProducaoUnitario(int custo) {
        if (custo < 0) {
            throw new IllegalArgumentException("O custo de produção unitário não pode ser negativo.");
        }
        this.custoProducaoUnitario = custo;
    }

    /**
     * Obtém o status atual da demanda no ciclo de produção.
     * 
     * @return O enum {@link StatusDemanda} representando o estado atual.
     */
    public StatusDemanda getStatus() {
        return status;
    }

    /**
     * Calcula o orçamento total necessário para cobrir toda a quantidade da demanda.
     * 
     * @return Valor flutuante correspondente ao custo total de produção.
     */
    public float getOrcamentoNecessario() {
        return (float) quantidadeProdutos * custoProducaoUnitario;
    }

    /**
     * Obtém a especificação/tipo de produto da demanda.
     * 
     * @return Nome do tipo do produto.
     */
    public String getTipoProduto() {
        return tipoProduto;
    }

    /**
     * Obtém a quantidade total de produtos associada a esta demanda.
     * 
     * @return Quantidade de itens.
     */
    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }
}