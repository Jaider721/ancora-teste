package br.com.ancoraweb.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.swconsultoria.nfe.schema_4.consReciNFe.TUfEmi;
import lombok.Data;

@Entity
@SequenceGenerator(name= "FornecedorSeq", sequenceName = "SEQ_FORNECEDOR", allocationSize = 1)
@Data
public class Fornecedor {
    @Id
    @GeneratedValue(generator = "FornecedorSeq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String CNPJ;
    private String xNome;
    private String xFant;
    private String IE;
    private String xLgr;
    private String nro;
    private String xBairro;
    private String cMun;
    private String xMun;
    @Enumerated(EnumType.STRING)
    private TUfEmi UF;
    private String CEP;
    private String cPais;
    private String xPais;
    private String fone;
    private String idSuperlogica;
  
    
}