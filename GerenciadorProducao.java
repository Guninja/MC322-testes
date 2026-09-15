import java.util.ArrayList;

public class GerenciadorProducao {
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private int budget;

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

    public void atualizarDemanda(){
        if(!demandas.isEmpty()){
            demandas.remove(0);
            System.out.println("Demanda atualizada");
        } else{
            System.out.println("Sem demanda :(");
        }
    }

    public boolean fabricarDemanda(){
        if (demandas.isEmpty()) { //proteção pra possivel demanda vazia
            System.out.println("Erro em gerar demanda, tentativa de fabricar falhou!"); 
            return false; 
        }
        Demanda demandaAtual = demandas.get(0);
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
            demandas.remove(0);
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

    public void exibirBudget(){
        System.out.println("Budget atual:" + budget + " Roblux");
    }

    public String exibirArmazem(){
        String relatorio = "=== ESTOQUE DO ARMAZÉM ===\n";
        relatorio += "Matéria-Prima (" + materiaPrima.getNome() + "): " + materiaPrima.getQuantidade() + "\n";
        relatorio += "Produtos Fabricados: " + produtosFabricados.size() + " unidade(s)\n";

        for (Produto p : produtosFabricados) {
            relatorio += "- " + p.getNome() + " | Status: " + p.getStatus() + "\n";
        }
        return relatorio;
    }

    private int calcularCustoProducao(){
        int custoTotal = 0;
        for (Maquina m : maquinas) {
            custoTotal += m.getCustoOperacao();
        }
        return custoTotal;
    }
}