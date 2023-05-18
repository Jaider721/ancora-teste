package br.com.ancoraweb.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@SequenceGenerator(name= "DespesasSeq", sequenceName = "SEQ_DESPESAS", allocationSize = 1)
@Data
public class Despesas {
    @Id
    @GeneratedValue(generator = "DespesasSeq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String idDespesa;
    private String descricao;
    private String pai;
    @Column(columnDefinition = "boolean default true")
    private boolean associado;
    
}