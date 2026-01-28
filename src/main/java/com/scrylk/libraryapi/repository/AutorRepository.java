package com.scrylk.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrylk.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
  
}
