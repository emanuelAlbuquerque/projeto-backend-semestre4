package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CriarClienteRequest {

  @Length(max = 255, message = "O nome não pode ultrapassar 255 caracteres")
  @NotBlank(message = "O nome não pode ser vazio e nem nulo")
  private String nome;

  @Positive(message = "A idade precisa ser maior que 0")
  @Min(value = 10, message = "Você precisa ter mais que 10 anos.")
  @NotNull(message = "A idade não pode ser nula")
  private int idade;

  @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato XXX.XXX.XXX-XX")
  private String cpf;

  @Pattern(regexp = "(Masculino|Feminino|Outros)", message = "O gênero deve ser 'Maculino', 'Feminino' ou 'Outros'")
  private String genero;

  @Email(message = "É priciso informar um email válido aaaaa@aaaa.com")
  @NotBlank(message = "O email não pode ser vazio ou nulo")
  private String email;
}
