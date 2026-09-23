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

    public void registrarDemanda(Demanda novaDemanda){
        demandas.add(novaDemanda);
    }

    public void adicionarMaquina(Maquina maquininha){
        maquinas.add(maquininha);
    }

    public boolean executarProximaProducao(){
        if (estrategiaAtual==null){//proteção pra se nao houver estrategia escolhida
            System.out.println("Erro, nenhuma estratégia definida\nDEFINA UMA ESTRATEGIA ANTES DE FABRICAR");
            return false;
        }
        Demanda demandaAtual=estrategiaAtual.selecionarDemanda(demandas, budget);
        if (demandaAtual==null) {//proteção pra possivel demanda vazia
            System.out.println("Erro ao encontrar demanda viável!(falta orçamento ou demanda na fila) Tentativa de fabricar falhou!"); 
            return false;
        }
        Produto produto = null;
        String idGerado= "PROD"+ (produtosFabricados.size() + 1);
        switch (demandaAtual.getTipoProduto()){
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
        for (Maquina m : maquinas) {
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

    public void comprarMateriaPrima(int quantidade, int precoUnitario){
        int custoTotal = quantidade * precoUnitario;
        if(budget >= custoTotal){
            budget -= custoTotal;
            materiaPrima.adicionarEstoque(quantidade);
        } else{
            System.out.println("Sem dinheiro para a compra");
        }
    }

    public void setEstrategia(EstrategiaProducao estrategiaNova){
        this.estrategiaAtual=estrategiaNova;
    }

    public String exibirBudget(){
        return budget + " Robux";
    }

    public String exibirArmazem(){
        String relatorio = "--- ARMAZÉM DE PRODUTOS ---\n\nProdutos Fabricados: ( " + produtosFabricados.size() + " unidade(s) )\n";
        for (Produto p : produtosFabricados) {
            relatorio += "- " + p.getNome() + " | Qualidade: " + p.getTipo() + " (" + p.getQualidade()*100 + "%) | LoteID: " + p.getId() + " | Status: " + p.getRisco() +"\n";
        }
        return relatorio;
    }

    public String exibirEstoqueMateriaPrima(){
        return "\n---ESTOQUE MATERIA-PRIMA ---\n\nMatéria-Prima (" + materiaPrima.getNome() + "): " + materiaPrima.getQuantidade() + "\n";
    } 

    public int calcularCustoProducao(){
        int custoTotal = 0;
        for (Maquina m : maquinas) {
            custoTotal += m.getCustoOperacao();
        }
        return custoTotal;
    }

    public String gerarRelatorioDiagnostico(){
        String relatorio="--- RELATÓRIO DE AUDITORIA DA FÁBRICA ---\nMAQUINAS: ";
        for (Maquina m : maquinas) {
            relatorio+=m.gerarRelatorioDiagnostico();
        }
        relatorio+="PRODUTOS FABRICADOS: ";
        for (Produto p : produtosFabricados) {
            relatorio+=p.gerarRelatorioDiagnostico();
        }
        return relatorio;
    }

    public String exibirDemandas(){
        String filaDemandas="Fila em ordem de criação de demandas:";
        int i=0;
        for (Demanda d:demandas){
            filaDemandas+= "\n" + ++i + " - " + d.getTipoProduto() + " (x" + d.getQuantidadeProdutos() + ")";
        }
        return filaDemandas;
    }
}