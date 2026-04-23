package com.stephanie.aprendendospring.infrastructure.repository;

import com.stephanie.aprendendospring.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
//Interface auto implementada com métodos de CRUD
//Todas as entidades precisão ter uma classe repository
//A interface está herdando e não implementando a JpaRepository porque assim não é obrigatório usar todos os métodos
//JpaRepository<nome da tabela/entidade , tipo do Id>
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Script do JPA que veriica se o email já existe e retorna um boolean
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Transactional //Ajuda a não causar erros ao deletar
    void deleteByEmail(String email);

}