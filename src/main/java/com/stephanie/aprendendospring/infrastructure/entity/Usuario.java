package com.stephanie.aprendendospring.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Getter, Setter e construtores feito automaticamento com o lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//Entity -> Identifico que a classe se trata de uma tabela usando o JPA
//@Table identifico o nome da tabela com JPA
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id//Criação do ID com JPA
    //Id pode ser gerado manualmente ou automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gerado automaticamente com JPA
    private Long id;
    @Column(name ="nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha", length = 255)
    private String senha;

    //Configurando vários endereços vinculando uma tabela de endereço ao usuário
    //no caso de ser apensa um seria @OneToOne/Cascade faz com que se um usuário seja deletado o endereço também seja
    //A coluna que identifica o pertencimento do endereço é usuario_id
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "senha";
    }

    @Override
    public String getUsername() {
        return "email";
    }
}
