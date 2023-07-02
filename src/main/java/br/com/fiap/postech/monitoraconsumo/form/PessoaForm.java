package br.com.fiap.postech.monitoraconsumo.form;

import br.com.fiap.postech.monitoraconsumo.dominio.Parentesco;
import br.com.fiap.postech.monitoraconsumo.dominio.Pessoa;
import br.com.fiap.postech.monitoraconsumo.dominio.Sexo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaForm {

    @JsonProperty
    private Long id;
    @JsonProperty
    @NotBlank(message = "Nome é um campo obrigatório e não pode estar em branco.")
    private String nome;
    @JsonProperty
    @NotNull(message = "Data de nascimento é um campo obrigatório e não pode estar em branco.")
    @Past(message = "Data de nascimento deve ser menor que a data atual.")
    private LocalDate dataDeNascimento;
    @JsonProperty
    @NotNull(message = "Sexo é um campo obrigatório e não pode estar em branco.")
    private Sexo sexo;
    @JsonProperty
    @NotNull(message = "Parentesco é um campo obrigatório e não pode estar em branco.")
    private Parentesco parentesco;

    public Pessoa toPessoa() {
        return new Pessoa().setNome(nome).setDataDeNascimento(dataDeNascimento).setSexo(sexo).setParentesco(parentesco);
    }

}