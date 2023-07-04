package br.com.fiap.postech.monitoraconsumo.controller;

import br.com.fiap.postech.monitoraconsumo.dominio.Endereco;
import br.com.fiap.postech.monitoraconsumo.form.EnderecoForm;
import br.com.fiap.postech.monitoraconsumo.service.EnderecoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private Validator validator;

    @GetMapping
    public ResponseEntity<Collection<Endereco>> findAll() {
        var enderecos = enderecoService.findAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Endereco> findById(@PathVariable UUID id) {
        var endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody EnderecoForm enderecoForm) {
        var violacoesToMap = validar(enderecoForm);
        if(!violacoesToMap.isEmpty())
            return ResponseEntity.badRequest().body(violacoesToMap);
        var endereco = enderecoForm.toEndereco();
        Endereco enderecoSaved = enderecoService.save(endereco);
        enderecoForm.setId(enderecoSaved.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((enderecoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(enderecoForm);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody EnderecoForm enderecoForm) {
        var violacoesToMap = validar(enderecoForm);
        if(!violacoesToMap.isEmpty())
            return ResponseEntity.badRequest().body(violacoesToMap);
        var endereco =  enderecoForm.toEndereco();
        var enderecoUpdated = enderecoService.update(id, endereco);
        enderecoForm.setId(enderecoUpdated.getId());
        return ResponseEntity.ok(enderecoForm);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private <T> Map<Path, String> validar(T form) {
        var violacoes = validator.validate(form);
        var violacoesToMap = violacoes.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        return violacoesToMap;
    }


}
