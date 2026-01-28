package com.scrylk.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrylk.libraryapi.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
  
}
