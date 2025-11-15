package co.edu.javeriana.as.personapp.terminal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaModelCli {
	private Integer cc;
	private String nombre;
	private String apellido;
	private String genero;
	private Integer edad;
}
