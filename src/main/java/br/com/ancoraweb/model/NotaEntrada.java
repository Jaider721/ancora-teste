package br.com.ancoraweb.model;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="nota_entrada")
@SequenceGenerator(name="NotaEntradaSeq", sequenceName = "SEQ_NOTA_ENTRADA", allocationSize = 1)
@Data
public class NotaEntrada {
    @Id
    @GeneratedValue(generator = "NotaEntradaSeq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String schema;
    private String chave;
    private String nomeEmitente;
    private String cnpjEmitente;
    private BigDecimal valor;
    private Date data;
    private String numeroNota;
    private String naturezaOperacao;
    private String id_contato_con;
    private String vFrete;
    private String vDesc;
    
    private boolean enviadoSuperLogica;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] xml;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Empresa empresa;
    
    @OneToMany(mappedBy = "notaEntrada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<NotaNcm> notaNcm;
}
