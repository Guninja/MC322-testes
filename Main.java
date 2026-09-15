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
        String lataB="ProdutoBlindado";
        String lataG="ProdutoGalvanizado";
        String lataCB="ProdutoChapaBruta";
        String[] listaProdutos={lataB, lataG, lataCB};
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
            System.out.println("\n1 -> Atualizar demanda de Lataria Blindada\n2 -> Atualizar demanda de Lataria Galvanizada\n3 -> Atualizar demanda de Lataria Chapa Bruta");
            System.out.println("\n4 -> Fabricar Lataria na fila de Demandas");
            System.out.println("\n5 -> Ver armazém e estoque");
            System.out.println("\n6 -> Comprar matéria-prima");
            System.out.println("\n0 -> SAIR");
            System.out.println("ESCOLHA: ");
            if(entrada.hasNextInt()){//filtro para aceitar apenas int na escolha do menu
                int opcaoMenu=entrada.nextInt();
                switch(opcaoMenu){//primeiro switch, escolha do menu  principal
                    case 0:
                        System.out.println("\n-SAINDO...\nEspero ter sido útil, até mais!\n   O.O  ");
                        menuAtivo=false;
                        break;
                    case 1:
                        System.out.println("\n-CRIANDO DEMANDA DE BLINDADO-\nQuanto deseja fabricar: ");
                        if(entrada.hasNextInt()){
                            int quantidadeDemandada=entrada.nextInt();
                            Demanda novaDemanda=new Demanda(listaProdutos[0], quantidadeDemandada);
                            gerente.registrarDemanda(novaDemanda);
                        }else{
                            System.out.println("Erro, Demanda não atualizada, digite apenas números inteiros");
                            entrada.next();
                        }break;
                    case 2:
                        System.out.println("\n-CRIANDO DEMANDA DE GALVANIZADO-\nQuanto deseja fabricar: ");
                        if(entrada.hasNextInt()){
                            int quantidadeDemandada=entrada.nextInt();
                            Demanda novaDemanda=new Demanda(listaProdutos[1], quantidadeDemandada);
                            gerente.registrarDemanda(novaDemanda);
                        }else{
                            System.out.println("Erro, Demanda não atualizada, digite apenas números inteiros");
                            entrada.next();
                        }break;
                    case 3:
                        System.out.println("\n-CRIANDO DEMANDA DE CHAPA BRUTA-\nQuanto deseja fabricar: ");
                        if(entrada.hasNextInt()){
                            int quantidadeDemandada=entrada.nextInt();
                            Demanda novaDemanda=new Demanda(listaProdutos[2], quantidadeDemandada);
                            gerente.registrarDemanda(novaDemanda);
                        }else{
                            System.out.println("Erro, Demanda não atualizada, digite apenas números inteiros");
                            entrada.next();
                        }break;
                    case 4:
                        gerente.fabricarDemanda();
                        break;
                    case 5:
                        System.out.println(gerente.exibirArmazem());
                        break;
                    case 6:
                        System.out.println("Digite quantas unidades de "+metal.getNome+" deseja comprar: ");
                        if(entrada.hasNextInt()){
                            int quantidadeComprando=entrada.nextInt();
                            gerente.comprarMateriaPrima(quantidadeComprando,metal.getCustoPorUnidade());
                        }else{
                            System.out.println("Erro, Compra não realizada, digite apenas números inteiros");
                            entrada.next();
                        }break;
                }
            }else{
                System.out.println("Tente um número!!");
                entrada.next();
            }
            if (menuAtivo){
                System.out.println("DIGITE QUALQUER COISA PARA VOLTAR AO MENU...\n");
                entrada.next();
            }
        }
    }
}