package com.scrylk.libraryapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "livro")
@Data
@Entity
public class Livro {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "isbn", length = 100, nullable = false)
  private String isbn;

  @Column(name = "titulo", length = 100, nullable = false)
  private String titulo;

  @Column(name = "data_publicacao", nullable = false)
  private LocalDate dataPublicacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "genero")
  private GeneroLivro genero;

  @Column(name = "preco", precision = 18, scale = 2)
  private BigDecimal preco;

  @ManyToOne
  @JoinColumn(name = "id_autor")
  private Autor autor;

}
