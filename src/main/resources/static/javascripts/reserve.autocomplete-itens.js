Alvorecer = Alvorecer || {};

Alvorecer.AutoComplete = (function() {
	
	function AutoComplete() {
		this.nameProduct = $('.js-name-product-input');
		var htmlTempleteAutoComplete = $('#template-autocomplete-reserve').html();
		this.templete = Handlebars.compile(htmlTempleteAutoComplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	AutoComplete.prototype.enable = function() {
		var options = {
				url: function(product) {
					return this.nameProduct.data('url') + '?name=' + product;
				}.bind(this),
				getValue: 'name',
				minCharNumber: 3,
				requestDelay: 300,
				ajaxSettings:{
					contentType: 'application/json'
				},
				template:{
					type: 'custom',
					method: templete.bind(this)
				},
				list:{
					onChooseEvent: onItemSelecionado.bind(this)
				}
		};
		this.nameProduct.easyAutocomplete(options);
	}
	
	function onItemSelecionado() {
		this.emitter.trigger('item-selecionado',this.nameProduct.getSelectedItemData());
		this.nameProduct.val('');
		this.nameProduct.focus();
	}
	
	function templete(name, product) {
		product.valorformatado = Alvorecer.formatarMoeda(product.value);
		return this.templete(product);
	}
	
	return AutoComplete;
	
}());