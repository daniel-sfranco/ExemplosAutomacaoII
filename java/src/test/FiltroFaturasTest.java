package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import main.Cliente;
import main.Fatura;
import main.FiltroFaturas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FiltroFaturasTest {

	private static List<Fatura> faturas;
	private static Cliente cliSP, cliRJ, cliMG, cliES, cliRS, cliSC;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.print("Configurando os testes ... ");
		
		// Clientes de diferentes estados
		cliSP = new Cliente("Cliente SP", LocalDate.now().minusMonths(3), "SP");
		cliRJ = new Cliente("Cliente RJ", LocalDate.now().minusMonths(1), "RJ");
		cliMG = new Cliente("Cliente MG", LocalDate.now(), "MG");
		cliES = new Cliente("Cliente ES", LocalDate.now().minusMonths(5), "ES");
		cliRS = new Cliente("Cliente RS", LocalDate.now().minusMonths(2), "RS");
		cliSC = new Cliente("Cliente SC", LocalDate.now(), "SC");
		
		// Criar lista de faturas
		faturas = new ArrayList<>();
		
		System.out.println("OK");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Testes Finalizados");
	}

	// ===== CRITÉRIO 1: Valor < 2000 =====

	@Test
	@Order(1)
	void testRemoveFaturaValorMenor2000() {
		faturas.clear();
		Fatura f = new Fatura("F001", 1500, LocalDate.now(), cliSP);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura com valor < 2000 deve ser removida");
	}

	@Test
	@Order(2)
	void testMantemFaturaValorIgual2000() {
		faturas.clear();
		Fatura f = new Fatura("F002", 2000, LocalDate.now(), cliSP);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(1, resultado.size(), "Fatura com valor = 2000 deve ser mantida");
		assertEquals("F002", resultado.get(0).getCodigo());
	}

	// ===== CRITÉRIO 2: 2000 ≤ valor ≤ 2500 E data ≤ 1 mês atrás =====

	@Test
	@Order(3)
	void testRemoveFatura2000a2500DataAntiga() {
		faturas.clear();
		// data de 2 meses atrás (menor que 1 mês atrás)
		Fatura f = new Fatura("F003", 2200, LocalDate.now().minusMonths(2), cliSP);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura 2000-2500 com data > 1 mês deve ser removida");
	}

	@Test
	@Order(4)
	void testMantemFatura2000a2500DataRecente() {
		faturas.clear();
		// data de hoje (menor que 1 mês atrás = false)
		Fatura f = new Fatura("F004", 2300, LocalDate.now(), cliSP);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(1, resultado.size(), "Fatura 2000-2500 com data recente deve ser mantida");
	}

	// ===== CRITÉRIO 3: 2500 < valor ≤ 3000 E data inclusão cliente ≤ 2 meses atrás =====

	@Test
	@Order(5)
	void testRemoveFatura2500a3000ClienteAntigo() {
		faturas.clear();
		// cliente com data de inclusão de 3 meses atrás (menor que 2 meses atrás)
		Cliente clienteAntigo = new Cliente("Cliente Antigo", LocalDate.now().minusMonths(3), "SP");
		Fatura f = new Fatura("F005", 2800, LocalDate.now(), clienteAntigo);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura 2500-3000 com cliente antigo deve ser removida");
	}

	@Test
	@Order(6)
	void testMantemFatura2500a3000ClienteRecente() {
		faturas.clear();
		// cliente com data de inclusão de 1 mês atrás (maior que 2 meses atrás)
		Cliente clienteRecente = new Cliente("Cliente Recente", LocalDate.now().minusMonths(1), "SP");
		Fatura f = new Fatura("F006", 2900, LocalDate.now(), clienteRecente);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(1, resultado.size(), "Fatura 2500-3000 com cliente recente deve ser mantida");
	}

	// ===== CRITÉRIO 4: Valor > 4000 E estado do Sudeste =====

	@Test
	@Order(7)
	void testRemoveFaturaMaior4000SudesteSP() {
		faturas.clear();
		Fatura f = new Fatura("F007", 4500, LocalDate.now(), cliSP);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura > 4000 de SP deve ser removida");
	}

	@Test
	@Order(8)
	void testRemoveFaturaMaior4000SudesteRJ() {
		faturas.clear();
		Fatura f = new Fatura("F008", 5000, LocalDate.now(), cliRJ);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura > 4000 de RJ deve ser removida");
	}

	@Test
	@Order(9)
	void testRemoveFaturaMaior4000SudesteMG() {
		faturas.clear();
		Fatura f = new Fatura("F009", 5500, LocalDate.now(), cliMG);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura > 4000 de MG deve ser removida");
	}

	@Test
	@Order(10)
	void testRemoveFaturaMaior4000SudesteES() {
		faturas.clear();
		Fatura f = new Fatura("F010", 6000, LocalDate.now(), cliES);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Fatura > 4000 de ES deve ser removida");
	}

	@Test
	@Order(11)
	void testMantemFaturaMaior4000NaoSudesteRS() {
		faturas.clear();
		Fatura f = new Fatura("F011", 4500, LocalDate.now(), cliRS);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(1, resultado.size(), "Fatura > 4000 de estado não Sudeste deve ser mantida");
	}

	@Test
	@Order(12)
	void testMantemFaturaMaior4000NaoSudesteSC() {
		faturas.clear();
		Fatura f = new Fatura("F012", 5000, LocalDate.now(), cliSC);
		faturas.add(f);
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(1, resultado.size(), "Fatura > 4000 de SC deve ser mantida");
	}

	// ===== TESTES COMBINADOS =====

	@Test
	@Order(13)
	void testFiltroComTodasRegras() {
		faturas.clear();
		
		// Deve ser removida (critério 1)
		faturas.add(new Fatura("F013", 1500, LocalDate.now(), cliSP));
		// Deve ser mantida (não se encaixa em nenhum critério)
		faturas.add(new Fatura("F014", 3500, LocalDate.now(), cliSP));
		// Deve ser removida (critério 4)
		faturas.add(new Fatura("F015", 5000, LocalDate.now(), cliSP));
		// Deve ser removida (critério 2)
		faturas.add(new Fatura("F016", 2200, LocalDate.now().minusMonths(2), cliSP));
		// Deve ser mantida
		faturas.add(new Fatura("F017", 4500, LocalDate.now(), cliRS));
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertEquals(3, resultado.size(), "Devem permanecer 3 faturas");
		assertEquals("F014", resultado.get(0).getCodigo());
		assertEquals("F017", resultado.get(1).getCodigo());
	}

	@Test
	@Order(14)
	void testFiltroListaVazia() {
		faturas.clear();
		
		List<Fatura> resultado = FiltroFaturas.filtrar(faturas);
		
		assertTrue(resultado.isEmpty(), "Lista vazia deve retornar vazia");
	}
}