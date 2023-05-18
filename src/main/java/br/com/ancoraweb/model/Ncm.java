package br.com.ancoraweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@SequenceGenerator(name= "NcmSeq", sequenceName = "SEQ_NCM", allocationSize = 1)
@Data
public class Ncm {
    @Id
    @GeneratedValue(generator = "NcmSeq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String ncm;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "despesa_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Despesas despesa;
    
}