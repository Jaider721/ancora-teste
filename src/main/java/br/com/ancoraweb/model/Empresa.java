package br.com.ancoraweb.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import lombok.Data;

@Entity
@SequenceGenerator(name= "EmpresaSeq", sequenceName = "SEQ_EMPRESA", allocationSize = 1)
@Data
public class Empresa {
    @Id
    @GeneratedValue(generator = "EmpresaSeq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String cnpj;
    private String razaoSocial;
    
    @Enumerated(EnumType.STRING)
    private AmbienteEnum ambiente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] certificado;
    private String senhaCertificado;
    private String nsu;
    
    private String uf;
    
    private Long idCondSuperLogica;
    private String podeRodar;
    
}
