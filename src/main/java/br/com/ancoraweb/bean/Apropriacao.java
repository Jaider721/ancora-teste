package br.com.ancoraweb.bean;

import java.math.BigDecimal;


import lombok.Data;
@Data
public class Apropriacao
{
    private Long idDespesa;
    private String ST_CONTA_CONT;
    private String ST_DESCRICAO_CONT;
    private String ID_DESPESA_DES;
    private String ST_COMPLEMENTO_APRO;
    private BigDecimal VL_VALOR_PDES;
   
}