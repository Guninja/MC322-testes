public enum StatusProduto {
    PARADO ("Parado"),
    PRODUZIDO ("Produzido");
    
    private final String estado;

    StatusProduto (String estado){
        this.estado=estado;
    }

    public String getDescricao(){
        return estado;
    }
}