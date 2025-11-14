package co.edu.javeriana.as.personapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
	@NonNull
	private String number;
	@NonNull
	private String company;
	@NonNull
	private Person owner;
}
