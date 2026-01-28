package com.scrylk.libraryapi.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scrylk.libraryapi.model.Autor;

@SpringBootTest
public class AutorRepositoryTest {

  @Autowired
  AutorRepository repository;

  @Test
  public void salvarTest() {
    Autor autor = new Autor();
    autor.setNome("Teste");
    autor.setDataNascimento(LocalDate.now());
    autor.setNacionalidade("Brasil");
    repository.save(autor);
  }
}

