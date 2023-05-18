package br.com.ancoraweb.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name="nota_ncm")
@SequenceGenerator(name="NotaNcmSeq", sequenceName = "SEQ_NOTA_NCM", allocationSize = 1)
@Data
public class NotaNcm {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -2543425088717298236L;
	
    @Id
    @GeneratedValue(generator = "NotaNcmSeq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String ncm;
    private String descricao;
    
    private BigDecimal vProd;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notaEntada_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NotaEntrada notaEntrada;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ncmNota")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ncm ncmNota;
    

}