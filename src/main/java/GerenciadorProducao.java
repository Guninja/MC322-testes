import java.util.ArrayList;

public class GerenciadorProducao {
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private int budget;
    private EstrategiaProducao estrategiaAtual;

    public GerenciadorProducao(int dinheiro, MateriaPrima materiaprima){
        demandas = new ArrayList<>();
        produtosFabricados = new ArrayList<>();
        maquinas = new ArrayList<>();
        materiaPrima = materiaprima;
        budget = dinheiro;
    }

    public void registrarDemanda(Demanda novaDemanda){ //adiciona demanda registrada agora na fila de demandas
        if (novaDemanda == null) throw new IllegalArgumentException("A demanda não pode ser nula.");
        int custoDasMaquinas = this.calcularCustoProducao();
        novaDemanda.setCustoProducaoUnitario(custoDasMaquinas);
        demandas.add(novaDemanda);
    }

    public void adicionarMaquina(Maquina maquininha){ //maquininha existe, gerenciador agora sabe
        if (maquininha == null) throw new IllegalArgumentException("A máquina não pode ser nula.");
        maquinas.add(maquininha);
    }

    public boolean executarProximaProducao(){
        if (estrategiaAtual==null){ //proteção pra se nao houver estrategia escolhida
            throw new IllegalStateException("Erro: Nenhuma estratégia definida. DEFINA UMA ESTRATEGIA ANTES DE FABRICAR");
        }
        Demanda demandaAtual=estrategiaAtual.selecionarDemanda(demandas, budget);
        if (demandaAtual==null) { //proteção pra possivel demanda vazia
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

    public void comprarMateriaPrima(int quantidade, int precoUnitario){ //bora gastar com MP
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

    public void setEstrategia(EstrategiaProducao estrategiaNova){
        if (estrategiaNova == null) throw new IllegalArgumentException("A estratégia não pode ser nula.");
        this.estrategiaAtual=estrategiaNova; //sera que é a melhor? kskks
    }

    public String exibirBudget(){
        return budget + " Robux"; //referencia a algo... entendeu?
    }

    public String exibirArmazem(){ //string de relatorio do armazem com produtos
        String relatorio = "--- ARMAZÉM DE PRODUTOS ---\n\nProdutos Fabricados: ( " + produtosFabricados.size() + " unidade(s) )\n";
        for (Produto p : produtosFabricados) {
            relatorio += "- " + p.getNome() + " | Qualidade: " + p.getTipo() + " (" + p.getQualidade()*100 + "%) | LoteID: " + p.getId() + " | Status: " + p.getRisco() +"\n";
        }
        return relatorio;
    }

    public String exibirEstoqueMateriaPrima(){ //estoque das MP
        return "\n---ESTOQUE MATERIA-PRIMA ---\n\nMatéria-Prima (" + materiaPrima.getNome() + "): " + materiaPrima.getQuantidade() + "\n";
    } 

    public int calcularCustoProducao(){ //quanto custa rodar as maquinas?
        int custoTotal = 0;
        for (Maquina m : maquinas) {
            custoTotal += m.getCustoOperacao();
        }
        return custoTotal;
    }

    public String gerarRelatorioDiagnostico(){ //relatorio de diagnostico solicitado pras maquinas e produtos
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

    public String exibirDemandas(){ //mostrar fila de demandas atual pro usuario
        String filaDemandas="Fila em ordem de criação de demandas:";
        int i=0;
        for (Demanda d:demandas){
            filaDemandas+= "\n" + ++i + " - " + d.getTipoProduto() + " (x" + d.getQuantidadeProdutos() + ")";
        }
        return filaDemandas;
    }
}