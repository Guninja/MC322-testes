import java.util.ArrayList;

/**
 * Classe central responsável por orquestrar o funcionamento da fábrica por trás das câmeras.
 * Atua como o contexto do padrão Strategy para seleção de demandas, gerencia o fluxo de caixa (budget), 
 * o estoque de matéria-prima, o acionamento das máquinas e a consolidação dos relatórios de auditoria.
 * 
 * @author Luis Gustavo
 */
public class GerenciadorProducao {
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private int budget;
    private EstrategiaProducao estrategiaAtual;

    /**
     * Construtor principal do Gerenciador de Produção.
     * Inicializa as coleções vazias para demandas, produtos e máquinas, e aplica o capital e o insumo iniciais.
     * 
     * @param dinheiro O saldo financeiro inicial (orçamento) disponível para a fábrica operar.
     * @param materiaprima O objeto representando o estoque principal de matéria-prima.
     */
    public GerenciadorProducao(int dinheiro, MateriaPrima materiaprima){
        demandas = new ArrayList<>();
        produtosFabricados = new ArrayList<>();
        maquinas = new ArrayList<>();
        materiaPrima = materiaprima;
        budget = dinheiro;
    }

    /**
     * Adiciona um novo pedido (demanda) à fila de produção da fábrica.
     * Calcula preventivamente o custo de operação das máquinas e injeta essa informação na demanda.
     * adiciona demanda registrada agora na fila de demandas
     * @param novaDemanda O objeto Demanda contendo as especificações do pedido.
     * @throws IllegalArgumentException se a demanda passada por parâmetro for nula.
     */
    public void registrarDemanda(Demanda novaDemanda){
        if (novaDemanda == null) throw new IllegalArgumentException("A demanda não pode ser nula.");
        int custoDasMaquinas = this.calcularCustoProducao();
        novaDemanda.setCustoProducaoUnitario(custoDasMaquinas);
        demandas.add(novaDemanda);
    }

    /**
     * Cadastra um novo equipamento industrial na linha de produção da fábrica.
     * maquininha existe, gerenciador agora sabe
     * @param maquininha O objeto Maquina a ser integrado à linha de montagem.
     * @throws IllegalArgumentException se a máquina passada por parâmetro for nula.
     */
    public void adicionarMaquina(Maquina maquininha){
        if (maquininha == null) throw new IllegalArgumentException("A máquina não pode ser nula.");
        maquinas.add(maquininha);
    }

    /**
     * Inicia um ciclo de fabricação selecionando a demanda mais adequada com base na estratégia ativa.
     * Liga as máquinas em sequência, debita os custos operacionais e armazena o produto finalizado.
     * 
     * @return true se o ciclo de fabricação foi concluído com sucesso e o produto armazenado.
     * @throws IllegalStateException proteção pra se nao houver estrategia escolhida ou possivel demanda vazia
     * @throws RecursoInsuficienteException se o orçamento da fábrica for insuficiente para ligar as máquinas necessárias.
     */
    public boolean executarProximaProducao(){
        if (estrategiaAtual==null){ //
            throw new IllegalStateException("Erro: Nenhuma estratégia definida. DEFINA UMA ESTRATEGIA ANTES DE FABRICAR");
        }
        Demanda demandaAtual=estrategiaAtual.selecionarDemanda(demandas, budget);
        if (demandaAtual==null) {
            throw new IllegalStateException("Erro: Não foi possível encontrar uma demanda viável. Verifique se a fila está vazia ou se há orçamento suficiente para as estratégias.");
        }
        Produto produto = null;
        String idGerado= "PROD"+ (produtosFabricados.size() + 1);
        switch (demandaAtual.getTipoProduto()){ //o que produzir Demanda?
            case "ProdutoChapaBruta":
                produto = new ProdutoChapaBruta(idGerado, "ProdutoChapaBruta");
                break;
            case "ProdutoGalvanizado":
                produto = new ProdutoGalvanizado(idGerado, "ProdutoGalvanizado");
                break;
            case "ProdutoBlindado":
                produto = new ProdutoBlindado(idGerado, "ProdutoBlindado");
                break;
            default:
                return false;
        }
        for (Maquina m : maquinas) { //mandando pras maquinassss
            if (this.budget < m.getCustoOperacao()) {
                throw new RecursoInsuficienteException("Sem saldo para executar a máquina: " + m.getNome());
            }
            m.ligar();
            this.budget -= m.getCustoOperacao();
            if (!m.processar(produto, materiaPrima)) {
                System.out.println("Falha na máquina " + m.getNome());
                return false;
            }
        }
        produtosFabricados.add(produto);
        demandaAtual.atualizarDemanda(-1);
        if (demandaAtual.getQuantidadeProdutos() <= 0) {
            demandas.remove(demandaAtual);
        }
        return true;
    }

    /**
     * Realiza a aquisição de novos lotes de matéria-prima, debitando o valor total do orçamento atual.
     * bora gastar com MP
     * @param quantidade O número de unidades de matéria-prima a serem adquiridas.
     * @param precoUnitario O custo individual de cada unidade comprada.
     * @throws IllegalArgumentException se a quantidade ou o preço unitário forem menores ou iguais a zero.
     * @throws RecursoInsuficienteException se o custo total da compra for maior que o orçamento disponível.
     */
    public void comprarMateriaPrima(int quantidade, int precoUnitario){
        if (quantidade<=0){
            throw new IllegalArgumentException("Erro: Quantidade deve ser maior que 0.");
        } 
        if (precoUnitario<=0) {
            throw new IllegalArgumentException("Erro: Preço da MP deve ser maior que 0");
        }
        int custoTotal = quantidade * precoUnitario;
        if(budget >= custoTotal){
            budget -= custoTotal;
            materiaPrima.adicionarEstoque(quantidade);
        } else{
            throw new RecursoInsuficienteException("Sem saldo para a compra! Faltam " + (custoTotal - budget) + " Robux.");
        }
    }

    /**
     * Altera dinamicamente a regra (algoritmo) utilizada para selecionar a próxima demanda a ser fabricada.
     * 
     * @param estrategiaNova O objeto da nova estratégia (ex: Ordem de Chegada, Maior Demanda).
     * @throws IllegalArgumentException se a estratégia fornecida for nula.
     */
    public void setEstrategia(EstrategiaProducao estrategiaNova){
        if (estrategiaNova == null) throw new IllegalArgumentException("A estratégia não pode ser nula.");
        this.estrategiaAtual=estrategiaNova; //sera que é a melhor? kskks
    }

    /**
     * Retorna a visualização formatada do orçamento atual da fábrica.
     * Robux é referencia a algo... entendeu?
     * @return Uma String contendo o saldo disponível em caixa.
     */
    public String exibirBudget(){
        return budget + " Robux";
    }

    /**
     * Gera um relatório com os produtos finalizados disponíveis no armazém da fábrica.
     * string de relatorio do armazem com produtos
     * @return Uma String listando o status, qualidade, lote e quantidade das peças fabricadas.
     */
    public String exibirArmazem(){
        String relatorio = "--- ARMAZÉM DE PRODUTOS ---\n\nProdutos Fabricados: ( " + produtosFabricados.size() + " unidade(s) )\n";
        for (Produto p : produtosFabricados) {
            relatorio += "- " + p.getNome() + " | Qualidade: " + p.getTipo() + " (" + p.getQualidade()*100 + "%) | LoteID: " + p.getId() + " | Status: " + p.getRisco() +"\n";
        }
        return relatorio;
    }

    /**
     * Retorna a visualização formatada do nível atual de matéria-prima no galpão de estoque.
     * estoque das MP
     * @return Uma String detalhando a quantidade de insumos disponíveis para produção.
     */
    public String exibirEstoqueMateriaPrima(){
        return "\n---ESTOQUE MATERIA-PRIMA ---\n\nMatéria-Prima (" + materiaPrima.getNome() + "): " + materiaPrima.getQuantidade() + "\n";
    } 

    /**
     * Calcula o custo operacional agregado para ativar toda a linha de produção (todas as máquinas) em um único ciclo.
     * quanto custa rodar as maquinas?
     * @return Um número inteiro representando a soma dos custos de operação.
     */
    public int calcularCustoProducao(){
        int custoTotal = 0;
        for (Maquina m : maquinas) {
            custoTotal += m.getCustoOperacao();
        }
        return custoTotal;
    }

    /**
     * Executa a rotina de auditoria exigindo o relatório de diagnóstico de todos os componentes auditáveis 
     * (máquinas e produtos), aplicando o princípio do polimorfismo.
     * relatorio de diagnostico solicitado pras maquinas e produtos
     * @return Uma String consolidada contendo todos os laudos técnicos estruturais da planta.
     */
    public String gerarRelatorioDiagnostico(){
        String relatorio="--- RELATÓRIO DE AUDITORIA DA FÁBRICA ---\nMAQUINAS: ";
        for (Maquina m : maquinas) {
            relatorio+=m.gerarRelatorioDiagnostico();
        }
        relatorio+="\nPRODUTOS FABRICADOS: ";
        for (Produto p : produtosFabricados) {
            relatorio+=p.gerarRelatorioDiagnostico();
        }
        return relatorio;
    }

    /**
     * Exibe os itens que estão atualmente aguardando fabricação na fila de demandas.
     * util pra mostrar fila de demandas atual pro usuario
     * @return Uma String formatada com as demandas pendentes e suas respectivas quantidades.
     */
    public String exibirDemandas(){
        String filaDemandas="Fila em ordem de criação de demandas:";
        int i=0;
        for (Demanda d:demandas){
            filaDemandas+= "\n" + ++i + " - " + d.getTipoProduto() + " (x" + d.getQuantidadeProdutos() + ")";
        }
        return filaDemandas;
    }
}