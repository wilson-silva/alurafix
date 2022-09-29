package br.com.alura.videoflix.domain.enums;

public enum Cor {

    AZUL("#0000FF"),
    AMARELO("#FFFF00"),
    VERMELHO("#FF0000"),
    VERDE("#008000"),
    ROXO("#800080");

    private String descricao;

    Cor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Cor corPorCorHex(String descricao){
        for (Cor cor : values()) {
            if(cor.getDescricao().equals(descricao)){
                return cor;
            }
        }
        return null;

    }

}
