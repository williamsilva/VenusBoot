Alvorecer = Alvorecer || {};

Alvorecer.SearchFastClient = (function() {
	
	function SearchFastClient() {
		this.searchFastClientModal = $('#clientSearchFast');
		this.nameClientInput = $('#nameClientModal');
		this.searchFastBtn = $('.js-search-client-fast-btn');
		this.tableSearchFastClient = $('#tableSearchFastClient');
		
		this.htmlTableSearchFastClient = $('#table-search-fast-client').html();
		this.template = Handlebars.compile(this.htmlTableSearchFastClient);
		this.messageErros = $('.js-mensage-erro');
		
	}
	
	SearchFastClient.prototype.enable = function() {
		this.searchFastBtn.on('click', onSearchFastClick.bind(this));
		this.searchFastClientModal.on('shown.bs.modal', onModalShow.bind(this));
	}
	
	function onModalShow() {
		this.nameClientInput.focus();
	}
	
	function onSearchFastClick(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.searchFastClientModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data:{
				name: this.nameClientInput.val()
			},
			success: onSearchFinal.bind(this),
			error: onErrosSearch.bind(this)
		});
	}
	
	function onSearchFinal(result) {
		this.messageErros.addClass('hidden');
		
		var html = this.template(result);
		this.tableSearchFastClient.html(html);
		
		var tableClientSearchFast = new Alvorecer.TableClientSearchFast(this.searchFastClientModal);
		tableClientSearchFast.enable();
	}
	
	function onErrosSearch() {
		this.messageErros.removeClass('hidden');
	}
	
	return SearchFastClient;
	
}());

Alvorecer.TableClientSearchFast = (function() {
	
	function TableClientSearchFast(modal) {
		this.modalClient = modal;
		this.client = $('.js-client-search-fast');
	}
	
	TableClientSearchFast.prototype.enable = function() {
		this.client.on('click', onClientSelected.bind(this));		
	}
	
	
	function onClientSelected(event) {
		var clientSelected = $(event.currentTarget);
		this.modalClient.modal('hide');
		
		$('#nameClient').val(clientSelected.data('name'));
		$('#idClient').val(clientSelected.data('id'));
	}
	
	return TableClientSearchFast;
	
}());

$(function() {
	
	var searchFastClient = new Alvorecer.SearchFastClient();
	searchFastClient.enable();
});