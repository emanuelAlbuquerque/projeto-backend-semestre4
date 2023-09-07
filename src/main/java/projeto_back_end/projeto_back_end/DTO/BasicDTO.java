package projeto_back_end.projeto_back_end.DTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class BasicDTO {
  private int status_code;
  private HttpStatusCode status;
  private List<String> messages = new ArrayList<>();
}
