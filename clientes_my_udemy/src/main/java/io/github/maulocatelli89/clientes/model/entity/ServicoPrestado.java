package io.github.maulocatelli89.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String descricao;

    @ManyToOne //Muitos servicos para um cliente
    @JoinColumn(name = "id_cliente") //nome da fk
    private Cliente cliente;

    @Column
    private BigDecimal valor;

    @Column
    @JsonFormat( pattern = "dd/MM/yyyy")
    private LocalDate data;
}
