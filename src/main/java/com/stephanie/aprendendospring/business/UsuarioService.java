package com.stephanie.aprendendospring.business;

import com.stephanie.aprendendospring.infrastructure.entity.Usuario;
import com.stephanie.aprendendospring.infrastructure.exceptions.ConflictException;
import com.stephanie.aprendendospring.infrastructure.exceptions.ResourceNotFoundException;
import com.stephanie.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//A Service intermedia a Controller e o Repository gravando os dados dentro do banco de dados
// da mesma forma deve ser feito um para cada entity

@Service//anotação que indica que é uma service
/*
 Injeção de dependências com um construtor favorecendo os testes unitários
 @RequiredArgsConstructor
 Inicializa a Classe Repository com private final impedindo mofificações
* */
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    //Injeção de dependências é uma forma de chamar a classe Repository dentro da classe service
    /*
    Outra forma de fazer injeção de dependências
    @Autowired
    private UsuarioRepository;
    */

    //Método para salvar os dados no banco
    public Usuario salvaUsuario(Usuario usuario){

        try{
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        }catch(ConflictException e){
        throw new ConflictException("E-mail já cadastrado", e.getCause());
        }
    }

    //Excessão personalizada caso o email já exista
    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("E-mail já cadastrado: " + email);
            }
        }catch(ConflictException e){
            throw new ConflictException("E-mail já cadastrado", e.getCause());
        }
    }
    //Método que verifica se existe email
    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

}
