package ejercicioML.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Datos {
	private Resultado[] results;
	
	public Datos(){}

	public Resultado[] getResults() {
		return results;
	}

	public void setResults(Resultado[] results) {
		this.results = results;
	}
	
	
}
