package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class FiltroFaturas {

	private static final Set<String> ESTADOS_SUDESTE = new HashSet<>();
	
	static {
		ESTADOS_SUDESTE.add("SP");
		ESTADOS_SUDESTE.add("RJ");
		ESTADOS_SUDESTE.add("MG");
		ESTADOS_SUDESTE.add("ES");
	}

	public static List<Fatura> filtrar(List<Fatura> faturas) {
		List<Fatura> resultado = new ArrayList<>();
		LocalDate hoje = LocalDate.now();
		LocalDate umMesAtras = hoje.minusMonths(1);
		LocalDate doisMesesAtras = hoje.minusMonths(2);
		
		for (Fatura fatura : faturas) {
			if (deveRemover(fatura, umMesAtras, doisMesesAtras)) {
				continue;
			}
			resultado.add(fatura);
		}
		
		return resultado;
	}
	
	private static boolean deveRemover(Fatura fatura, LocalDate umMesAtras, LocalDate doisMesesAtras) {
		float valor = fatura.getValor();
		LocalDate dataFatura = fatura.getData();
		Cliente cliente = fatura.getCliente();
		String estado = cliente.getEstado();
		
		// Critério 1: valor menor que 2000
		if (valor < 2000) {
			return true;
		}
		
		// Critério 2: valor entre 2000 e 2500 e data <= um mês atrás
		if (valor >= 2000 && valor <= 2500 && dataFatura.isBefore(umMesAtras.plusDays(1))) {
			return true;
		}
		
		// Critério 3: valor entre 2500 e 3000 e data de inclusão do cliente <= 2 meses atrás
		if (valor > 2500 && valor <= 3000 && cliente.getDataInclusao().isBefore(doisMesesAtras.plusDays(1))) {
			return true;
		}
		
		// Critério 4: valor maior que 4000 e estado é do Sudeste
		if (valor > 4000 && ESTADOS_SUDESTE.contains(estado.toUpperCase())) {
			return true;
		}
		
		return false;
	}
}