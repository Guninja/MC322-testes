import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MateriaPrima metal=new MateriaPrima("MeBA", "Metal", "m^2", 8);//será que é aço ou európio??? nunca saberemos...
        Produto capo=new Produto("PROD01", "Capô");//Kpô, donde se é uai?
        Produto porta=new Produto("PROD02", "Porta");//porrta ou pohrta?
        Produto teto=new Produto("PROD03", "Teto");//um dia terei um solar...
        Produto[] listaProdutos={capo, porta, teto};
        Maquina estampagem=new Maquina("Estampagem", 15);//é o processo de moldagem da lataria dos carros
        Esteira esteiraOutMaquina=new Esteira(false, 10);//instanciada parada com limite de 4 produtos
        Esteira esteiraInMaquina=new Esteira(false, 10);//instanciada parada com limite de 10m^2 de MP
        EstacaoInspecao validacao=new EstacaoInspecao(false);//instanciada desligada
        Scanner entrada=new Scanner(System.in);
        boolean menuAtivo=true;//esse é o estagiario, o famoso aux  :o
        while(menuAtivo){
            System.out.println("\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\nFÁBRICA DE LATARIA DE CARRO\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n");
            System.out.println("Máteria-Prima(MP): "+metal.getId()+" = "+metal.getNome());
            System.out.println("Quantidade: "+metal.getQuantidade()+" "+metal.getUnidade());
            System.out.println("Unidade: "+metal.getUnidade()+"\n");
            System.out.println("Produtos Disponíveis:\n1 -> "+capo.getNome()+" (demanda: "+capo.getDemandaMateriaPrima()+" "+metal.getUnidade()+")");
            System.out.println("2 -> "+porta.getNome()+" (demanda: "+porta.getDemandaMateriaPrima()+" "+metal.getUnidade()+")");
            System.out.println("3 -> "+teto.getNome()+" (demanda: "+teto.getDemandaMateriaPrima()+" "+metal.getUnidade()+")");
            System.out.println("\n__________________________\nMENU PRINCIPAL\n__________________________\n0 -> Sair\n1 -> Cadastrar estoque de MP\n2 -> Produzir\n3 -> Consultar estoque geral\n");
            System.out.println("O que deseja?\nApenas número: ");
            if(entrada.hasNextInt()){//filtro para aceitar apenas int na escolha do menu
                int opcaoMenu=entrada.nextInt();
                switch(opcaoMenu){//primeiro switch, escolha do menu  principal
                    case 0:
                        System.out.println("-SAINDO...\nEspero ter sido útil, até mais!\n   O.O  ");
                        menuAtivo=false;
                        break;
                    case 1:
                        System.out.println("-CADASTRO DE ESTOQUE-\nQuanto deseja estocar de "+metal.getNome()+" (em "+metal.getUnidade()+")\nDigite um número: ");
                        if(entrada.hasNextInt()){
                            int addEstoque=entrada.nextInt();
                            metal.adicionarEstoque(addEstoque);
                        }else{
                            System.out.println("Erro, digite apenas números inteiros");
                            entrada.next();
                        }break;
                    case 2:
                        System.out.println("-PRODUÇAO-\n0 -> Voltar\n1 -> "+listaProdutos[0].getNome()+"\n2 -> "+listaProdutos[1].getNome()+"\n3 -> "+listaProdutos[2].getNome());
                        System.out.println("Digite o número respectivo do produto: ");
                        while(!entrada.hasNextInt()){//denovo o filtro pra só deixar respostar numericas
                            entrada.next();
                            System.out.println("Digite APENAS o número respectivo do produto: ");
                        }
                        int opcaoNumProduto=entrada.nextInt();
                        if(opcaoNumProduto==0){
                            System.out.println("\nVoltando...\n");
                            break;
                        }else if(opcaoNumProduto!=0 && opcaoNumProduto!=1 && opcaoNumProduto!=2 && opcaoNumProduto!=3){
                            System.out.println("NÚMERO INVÁLIDO\nVoltando para o menu\n");
                            break;
                        }
                        producao(entrada, metal, listaProdutos[opcaoNumProduto-1], esteiraInMaquina, esteiraOutMaquina, validacao, estampagem);//criamos função producao pra evitar switch dentro de switch
                        esteiraInMaquina.desligar();
                        esteiraOutMaquina.desligar();
                        estampagem.desligar();
                        validacao.desativar();
                        break;
                    case 3:
                        System.out.println("-CONSULTA DE ESTOQUE-\n");
                        System.out.println(metal.getNome()+" -> "+metal.getQuantidade()+" "+metal.getUnidade());
                        System.out.println("Produtos avaliados -> "+validacao.getTotalInspecionados());
                        break;
                }
            }else{
                System.out.println("Tente um número!!");
                entrada.next();
            }
        }
    }

    public static void producao(Scanner entrada, MateriaPrima metaal, Produto prodProduzindo, Esteira inMaquina, Esteira outMaquina, EstacaoInspecao estacao, Maquina moldagem){
        System.out.println("Digite a demanda de MP: ");
        while(!entrada.hasNextInt()){//denovo o filtro pra só deixar respostar numericas
            entrada.next();
            System.out.println("Digite APENAS numericamente a demanda: ");
        }
        int demandaMP=entrada.nextInt();
        prodProduzindo.definirDemandaMateriaPrima(demandaMP);
        inMaquina.ligar();
        outMaquina.ligar();
        moldagem.ligar();
        estacao.ativar();
        if(!inMaquina.adicionarItem(metaal.getId(), prodProduzindo.getDemandaMateriaPrima())){
            System.out.println("-FALHA NA ESTERIA APÓS ESTOQUE-");
            return;
        }else if(!moldagem.processar(prodProduzindo, metaal)){
            System.out.println("-FALHA NA ESTAMPAGEM-");
            inMaquina.removerItem();
            return;
        }else if(!outMaquina.adicionarItem(prodProduzindo.getId(), prodProduzindo.getDemandaMateriaPrima())){
            System.out.println("-FALHA NA ESTEIRA APÓS ESTAMPAGEM-");
            inMaquina.removerItem();
            return;
        }else if(!estacao.inspecionar(prodProduzindo)){
            System.out.println("-FALHA NA VALIDAÇÃO-");
            inMaquina.removerItem();
            outMaquina.removerItem();
            return;
        }else{
            inMaquina.removerItem();
            outMaquina.removerItem();
            System.out.println("-FIM DE PRODUCAO-");
        }
    }
}