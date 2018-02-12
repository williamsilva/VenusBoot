package com.alvorecer.venus.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.alvorecer.venus.model.Product;



public class TableItensSaleTest {

	private TableItensSale tableItensSale;

	@Before
	public void setUp()	{
		this.tableItensSale = new TableItensSale("1");
	}

	@Test
	public void caulcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tableItensSale.getValueTotal());
	}
	
	@Test
	public void calculaValorTotalComUmItem() throws Exception {
		Product product = new Product();
		BigDecimal value = new BigDecimal("20.50");
		product.setValue(value);
		
		tableItensSale.addItem(product, 1);
		assertEquals(value, tableItensSale.getValueTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		Product c1 = new Product();
		c1.setId(1L);
		BigDecimal v1 = new BigDecimal("8.90");
		c1.setValue(v1);;
		
		Product c2 = new Product();
		c2.setId(2L);
		BigDecimal v2 = new BigDecimal("4.99");
		c2.setValue(v2);;
		
		tableItensSale.addItem(c1, 1);
		tableItensSale.addItem(c2, 2);;
		
		assertEquals(new BigDecimal("18.88"), tableItensSale.getValueTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
		Product c1 = new Product();
		c1.setId(1L);
		c1.setValue(new BigDecimal("4.50"));
		
		tableItensSale.addItem(c1, 1);
		tableItensSale.addItem(c1, 1);
		
		assertEquals(1, tableItensSale.total());
		assertEquals(new BigDecimal("9.00"), tableItensSale.getValueTotal());
	}
	
	@Test
	public void deveAlterarQuantidadeItem() throws Exception {
		Product c1 = new Product();
		c1.setId(1L);
		c1.setValue(new BigDecimal("4.50"));
		
		tableItensSale.addItem(c1, 1);
		tableItensSale.alterarQuantidadeItens(c1, 3);
		
		assertEquals(1, tableItensSale.total());
		assertEquals(new BigDecimal("13.50"), tableItensSale.getValueTotal());
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		Product c1 = new Product();
		c1.setId(1L);
		c1.setValue(new BigDecimal("8.90"));
		
		Product c2 = new Product();
		c2.setId(2L);
		c2.setValue(new BigDecimal("4.99"));
		
		Product c3 = new Product();
		c3.setId(3L);
		c3.setValue(new BigDecimal("2.00"));
		
		tableItensSale.addItem(c1, 1);
		tableItensSale.addItem(c2, 2);
		tableItensSale.addItem(c3, 1);
		
		tableItensSale.excluirItem(c2);
		
		assertEquals(2, tableItensSale.total());
		assertEquals(new BigDecimal("10.90"), tableItensSale.getValueTotal());
	}
}
