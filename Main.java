import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MateriaPrima metal=new MateriaPrima("MeBA", "Metal", "m^2", 0, 5);//será que é aço ou európio??? nunca saberemos... mas ambos são caros
        GerenciadorProducao gerente=new GerenciadorProducao(460,metal);//esse objeto sabe muito...
        MaquinaPrensaEstampagem prensa=new MaquinaPrensaEstampagem("Prensa de Estampagem", 20, 0.0f, 1);
        MaquinaCorteLaser cnc=new MaquinaCorteLaser("CNC Corte Laser em metal", 10, 0.0f, 2);//cnc laser é cara pra rodar
        MaquinaVerificacao inspecao=new MaquinaVerificacao("Inspeção", 30, 0.15f, 1);
        gerente.adicionarMaquina(prensa);
        gerente.adicionarMaquina(cnc);
        gerente.adicionarMaquina(inspecao);
        String capo="Capô";//Kpô, donde se é uai?
        String porta="Porta";//porrta ou pohrta?
        String teto="Teto";//um dia terei um solar...
        String[] listaProdutos={capo, porta, teto};
        Scanner entrada=new Scanner(System.in);
        boolean menuAtivo=true;//esse é o estagiario, o famoso aux  :o
        while(menuAtivo){
            System.out.println("\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\nFÁBRICA DE LATARIA DE CARRO\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n");
            System.out.println("Máteria-Prima(MP): "+metal.getNome()+" ("+metal.getUnidade()+")\n");
            gerente.exibirBudget();
            System.out.println("\n\nProdutos Disponíveis:  (Qualidade)\n1 - Lataria Blindada (Alta)");
            System.out.println("2 - Lataria Galvanizada (Média)");
            System.out.println("3 - Lataria Chapa Bruta (Baixa)");
            System.out.println("\n\n__________________________\nMENU PRINCIPAL\n__________________________");
            System.out.println("ATUALIZAR DEMANDAS\n\n1 -> Atualizar demanda de Lataria Blindada\n2 -> Atualizar demanda de Lataria Galvanizada\n3 -> Atualizar demanda de Lataria Chapa Bruta\n");
            System.out.println("\nFABRICAR\n\n4 -> Fabricar Lataria Blindada\n5 -> Fabricar Lataria Galvanizada\n6 -> Fabricar Lataria Chapa Bruta");
            System.out.println("\nCONSULTAR\n\n7 -> Ver armazém\n8 -> Ver estoque de matéria-prima");
            System.out.println("\nCOMPRAR MATÉRIA-PRIMA\n\n9 -> Comprar matéria-prima");
            System.out.println("\n\n0 -> SAIR");
            System.out.println("ESCOLHA: ");
            if(entrada.hasNextInt()){//filtro para aceitar apenas int na escolha do menu
                int opcaoMenu=entrada.nextInt();
                switch(opcaoMenu){//primeiro switch, escolha do menu  principal
                    case 0:
                        System.out.println("\n-SAINDO...\nEspero ter sido útil, até mais!\n   O.O  ");
                        menuAtivo=false;
                        break;
                    case 1:
                        System.out.println("\n-CADASTRO DE ESTOQUE-\nQuanto deseja estocar de "+metal.getNome()+" (em "+metal.getUnidade()+")\nDigite um número: ");
                        if(entrada.hasNextInt()){
                            int addEstoque=entrada.nextInt();
                            metal.adicionarEstoque(addEstoque);
                        }else{
                            System.out.println("Erro, digite apenas números inteiros");
                            entrada.next();
                        }break;
                    case 2:
                        System.out.println("\n-PRODUÇAO-\n0 -> Voltar\n1 -> "+listaProdutos[0].getNome()+"\n2 -> "+listaProdutos[1].getNome()+"\n3 -> "+listaProdutos[2].getNome());
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
                        System.out.println("\n-CONSULTA DE ESTOQUE-\n");
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