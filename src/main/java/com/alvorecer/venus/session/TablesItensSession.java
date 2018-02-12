package com.alvorecer.venus.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.alvorecer.venus.model.ItemVoucher;
import com.alvorecer.venus.model.Product;

@SessionScope
@Component
public class TablesItensSession {

	private Set<TableItensSale> tables = new HashSet<>();

	public void addItem(String uuid, Product product, int quantidade) {
		TableItensSale table = getTableUUID(uuid);		
		table.addItem(product, quantidade);
		tables.add(table);
	}

	public void alterarQuantidadeItens(String uuid, Product product, Integer quantidade) {
		TableItensSale table = getTableUUID(uuid);
		table.alterarQuantidadeItens(product, quantidade);
	}

	public void excluirItem(String uuid, Product product) {
		TableItensSale table = getTableUUID(uuid);
		table.excluirItem(product);
	}
	
	public List<ItemVoucher> getItens(String uuid) {
		return getTableUUID(uuid).getItens();
	}
	
	public Object getValorTotal(String uuid) {
		return getTableUUID(uuid).getValueTotal();
	}
	
	private TableItensSale getTableUUID(String uuid) {
		TableItensSale table = tables.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TableItensSale(uuid));
		return table;
	}

}