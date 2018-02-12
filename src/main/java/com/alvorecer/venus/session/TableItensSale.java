package com.alvorecer.venus.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.alvorecer.venus.model.ItemVoucher;
import com.alvorecer.venus.model.Product;

class TableItensSale {

	private String uuid;
	private List<ItemVoucher> itensVouchers = new ArrayList<>();

	public TableItensSale(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValueTotal() {
		return itensVouchers.stream().map(ItemVoucher::getValueTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void addItem(Product product, Integer quantidade) {
		Optional<ItemVoucher> itensVouchersOptional = getItemProduct(product);

		ItemVoucher itemVoucher = null;

		if (itensVouchersOptional.isPresent()) {
			itemVoucher = itensVouchersOptional.get();
			itemVoucher.setAmount(itemVoucher.getAmount() + quantidade);
		} else {
			itemVoucher = new ItemVoucher();
			itemVoucher.setProduct(product);
			itemVoucher.setAmount(quantidade);
			itemVoucher.setUnitaryValue(product.getValue());

			itensVouchers.add(0, itemVoucher);
		}

	}

	public void alterarQuantidadeItens(Product product, Integer quantidade) {
		ItemVoucher itemVoucher = getItemProduct(product).get();
		itemVoucher.setAmount(quantidade);
	}

	public void excluirItem(Product product) {
		int indice = IntStream.range(0, itensVouchers.size())
				.filter(i -> itensVouchers.get(i).getProduct().equals(product)).findAny().getAsInt();
		itensVouchers.remove(indice);
	}

	public int total() {
		return itensVouchers.size();
	}

	public List<ItemVoucher> getItens() {
		return itensVouchers;
	}

	private Optional<ItemVoucher> getItemProduct(Product product) {
		return itensVouchers.stream().filter(i -> i.getProduct().equals(product)).findAny();
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableItensSale other = (TableItensSale) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
