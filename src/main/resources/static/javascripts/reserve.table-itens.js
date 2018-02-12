Alvorecer.TableItens = (function() {
	
	function TableItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tableProductsContainer = $('.js-table-vouchers-containner');
		this.uuid = $('#uuid').val();
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	TableItens.prototype.enable = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
		bindQuantidade.call(this);
		bindTabelaItens.call(this);
	}
	
	TableItens.prototype.valorTotal = function() {
		return this.tableProductsContainer.data('valor');
	}
	
	function onItemSelecionado(evento, item) {
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data:{
				idProduct: item.id,
				uuid: this.uuid,
			}
		});
		
		resposta.done(onItemAddServer.bind(this));
	}
	
	function onItemAddServer(html) {
		$('.js-table-vouchers-containner').html(html);
		
		bindQuantidade.call(this);
		
		var tabelaItem =bindTabelaItens.call(this);
		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
	}
	
	function onQuantidadeItemAlterado(event) {
		var input = $(event.target);
		var quantidade = input.val();
		
		if (quantidade <= 0) {
			input.val(1);
			quantidade = 1;
		}
		
		var idProduct = input.data('codigo-product');
		
		var resposta = $.ajax({
			url: 'item/' + idProduct,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				uuid: this.uuid
			}
		});
		
		resposta.done(onItemAddServer.bind(this));
	}
	
	function onDoubleClick(evento) {
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExclusaoItemClick(evento) {
		var idProduct = $(evento.target).data('codigo-product');
		var resposta = $.ajax({
			url: 'item/' + this.uuid + '/' + idProduct,
			method: 'DELETE'
		});
		
		resposta.done(onItemAddServer.bind(this));
	}
	
	function bindQuantidade() {
		var quantidadeItemInput = $('.js-table-products-quantidade-item');
		quantidadeItemInput.on('change', onQuantidadeItemAlterado.bind(this));
		quantidadeItemInput.maskMoney({ precision: 0, thousands: '' });
	}
	
	function bindTabelaItens() {
		var tabelaItem = $('.js-tabela-item');
		tabelaItem.on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
		return tabelaItem;
	}
	return TableItens;
	
}());


