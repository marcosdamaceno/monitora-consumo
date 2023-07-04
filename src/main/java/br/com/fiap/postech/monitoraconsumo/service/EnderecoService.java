package br.com.fiap.postech.monitoraconsumo.service;

import br.com.fiap.postech.monitoraconsumo.dominio.Endereco;
import br.com.fiap.postech.monitoraconsumo.repositorio.IEnderecoRepository;
import br.com.fiap.postech.monitoraconsumo.service.exception.ControllerNotFoundException;
import br.com.fiap.postech.monitoraconsumo.service.exception.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository repository;


    public Collection<Endereco> findAll() {
        var produtos = repository.findAll();

        return produtos;
    }

    public Endereco findById(UUID id) {
        var endereco = repository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Endereco não encontrado"));
        return endereco;
    }

    public Endereco save(Endereco endereco) {
        var enderecoSaved = repository.save(endereco);
        return enderecoSaved;
    }

    public Endereco update(UUID id, Endereco endereco) {
        try {
            Endereco findEndereco = repository.getOne(id);
            findEndereco.setRua(endereco.getRua());
            findEndereco.setNumero(endereco.getNumero());
            findEndereco.setBairro(endereco.getBairro());
            findEndereco.setCidade(endereco.getCidade());
            findEndereco.setEstado(endereco.getEstado());
            findEndereco = repository.save(findEndereco);

            return findEndereco;
        } catch (EntityNotFoundException e) {
            throw  new ControllerNotFoundException("Produto não encontrado, id:" + id);
        }
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade da base");
        }

    }
    

}