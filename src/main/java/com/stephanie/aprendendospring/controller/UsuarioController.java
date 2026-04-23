package com.stephanie.aprendendospring.controller;

import com.stephanie.aprendendospring.business.UsuarioService;
import com.stephanie.aprendendospring.controller.dtos.UsuarioDTO;
import com.stephanie.aprendendospring.infrastructure.entity.Usuario;
import com.stephanie.aprendendospring.infrastructure.security.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// a controller recebe os dados e faz o processamento da API
//indica que a classe é uma controller
@RestController
//Aponta a URI mas adequada para as requisições
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //método que vai salvar os dados do usuário
    @PostMapping //indica qual o método REST está sendo usado(POST)
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
        //Quando alguém enviar um usuário para a API, eu vou salvar esse usuário e responder(Response.ok) dizendo que foi um sucesso, devolvendo o usuário.
    }

    @PostMapping("/login")
    //Usamos DTO(Data Transfer Object) para filtrar os dados que não vai que não queremos expor na rqquisição
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    //não URI diferente porque é o mesmo para todos os verbos
    public ResponseEntity <Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

}
