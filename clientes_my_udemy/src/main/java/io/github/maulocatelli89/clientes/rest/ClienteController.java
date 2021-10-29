package io.github.maulocatelli89.clientes.rest;

import io.github.maulocatelli89.clientes.model.entity.Cliente;
import io.github.maulocatelli89.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController /* Recebe as requisições HTTP e
                   também submete as respostas HTTP*/
@RequestMapping("/api/clientes") // mapeia qual a URL base
public class ClienteController {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Cliente> obterTodos(){
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@Valid @RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @GetMapping("{id}")
    public Cliente obterPorId(@PathVariable Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//codigo de sucesso que não tem nenhum retorno
    public void deletar(@PathVariable Integer id){
        clienteRepository.findById(id)
                .map( cliente -> {
                    clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@Valid @PathVariable Integer id, @RequestBody Cliente clienteAtualizado){
        clienteRepository.findById(id)
                .map( cliente -> {
                    clienteAtualizado.setId(cliente.getId());
                    return clienteRepository.save(clienteAtualizado);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Cliente não encontrado"));
    }
}
