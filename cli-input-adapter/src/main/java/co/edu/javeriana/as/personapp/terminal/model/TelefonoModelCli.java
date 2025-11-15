package co.edu.javeriana.as.personapp.terminal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoModelCli {
	private String num;
	private String oper;
	private PersonaModelCli duenio;

	@Override
	public String toString() {
		String duenioInfo = duenio != null ? getDuenio().getNombre() + " " + getDuenio().getApellido() : "null";
		return "TelefonoModelCli [num=" + num + ", oper=" + oper + ", duenio=" + duenioInfo + "]";
	}
}

