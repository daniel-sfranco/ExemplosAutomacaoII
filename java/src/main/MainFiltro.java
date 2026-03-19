package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainFiltro {

	public static void main(String[] args) {
		
		// Criando clientes
		Cliente cli1 = new Cliente("Empresa A", LocalDate.now().minusMonths(3), "SP");
		Cliente cli2 = new Cliente("Empresa B", LocalDate.now().minusMonths(1), "RJ");
		Cliente cli3 = new Cliente("Empresa C", LocalDate.now(), "MG");
		Cliente cli4 = new Cliente("Empresa D", LocalDate.now().minusMonths(5), "SP");
		Cliente cli5 = new Cliente("Empresa E", LocalDate.now().minusMonths(2), "RS");
		
		// Criando faturas
		List<Fatura> faturas = new ArrayList<>();
		
		// Caso 1: valor < 2000 (deve ser removida)
		faturas.add(new Fatura("F001", 1500, LocalDate.now(), cli1));
		
		// Caso 2: valor entre 2000-2500 e data > um mês atrás (deve permanecer)
		faturas.add(new Fatura("F002", 2200, LocalDate.now(), cli2));
		
		// Caso 3: valor entre 2000-2500 e data <= um mês atrás (deve ser removida)
		faturas.add(new Fatura("F003", 2300, LocalDate.now().minusMonths(2), cli3));
		
		// Caso 4: valor entre 2500-3000 e data inclusão cliente > 2 meses (deve permanecer)
		faturas.add(new Fatura("F004", 2700, LocalDate.now(), cli1));
		
		// Caso 5: valor entre 2500-3000 e data inclusão cliente <= 2 meses (deve ser removida)
		faturas.add(new Fatura("F005", 2800, LocalDate.now(), cli2));
		
		// Caso 6: valor > 4000 e estado NÃO Sudeste (deve permanecer)
		faturas.add(new Fatura("F006", 4500, LocalDate.now(), cli5));
		
		// Caso 7: valor > 4000 e estado É Sudeste (deve ser removida)
		faturas.add(new Fatura("F007", 5000, LocalDate.now(), cli4));
		
		// Caso 8: valor normal que deve permanecer
		faturas.add(new Fatura("F008", 3500, LocalDate.now(), cli3));
		
		System.out.println("=== Faturas antes do filtro (" + faturas.size() + ") ===");
		for (Fatura f : faturas) {
			System.out.printf("  %s - R$%.2f - Data: %s - Cliente: %s (%s)%n", 
				f.getCodigo(), f.getValor(), f.getData(), f.getCliente().getNome(), f.getCliente().getEstado());
		}
		
		// Aplicar filtro
		List<Fatura> filtradas = FiltroFaturas.filtrar(faturas);
		
		System.out.println("\n=== Faturas após o filtro (" + filtradas.size() + ") ===");
		for (Fatura f : filtradas) {
			System.out.printf("  %s - R$%.2f - Data: %s - Cliente: %s (%s)%n", 
				f.getCodigo(), f.getValor(), f.getData(), f.getCliente().getNome(), f.getCliente().getEstado());
		}
		
		System.out.println("\n=== Resumo ===");
		System.out.println("Removidas: " + (faturas.size() - filtradas.size()));
	}
}