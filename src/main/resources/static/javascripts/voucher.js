Alvorecer.Voucher = (function() {
	
	function Voucher(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorAntecipadoInput = $('#valorAntecipado');
		
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.valorAntecipadoInput = this.valorAntecipadoInput.data('valor');
	}
	
	Voucher.prototype.enable = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		$('#valorAntecipado').on('keyup', onValorAntecipadoAlterado.bind(this));		
				
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		$('#valorAntecipado').on('keyup', onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}
	
	function onValorAntecipadoAlterado(evento) {
		this.valorAntecipadoInput = Alvorecer.recuperarValor($(evento.target).val());
	}
	
	function onValoresAlterados() {
		var valorTotal = numeral(this.valorTotalItens) - numeral(this.valorAntecipadoInput);
		this.valorTotalBox.html(Alvorecer.formatarMoeda(valorTotal));	
		
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}
	
	return Voucher;
	
}());

$(function() {
	var autocomplete = new Alvorecer.AutoComplete();
	autocomplete.enable();
	
	var tableItens = new Alvorecer.TableItens(autocomplete);
	tableItens.enable();
	
	var voucher = new Alvorecer.Voucher(tableItens);
	voucher.enable();
});