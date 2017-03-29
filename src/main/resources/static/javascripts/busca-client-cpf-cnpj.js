Alvorecer = Alvorecer || {};

Alvorecer.BuscaClient = (function() {

	function BuscaClient() {
		this.inputCpfCnpjClient = $('.client-on-focus');
		this.inputIdClient = $('#client');
		this.imgLoading = $('.js-img-loading-client');
	}

	BuscaClient.prototype.enable = function() {
		this.inputCpfCnpjClient.on('focusout', onClientAlterado.bind(this));
	}

	function onClientAlterado() {
		if (this.inputCpfCnpjClient.val() !== '') {
			var resposta = $.ajax({
				url : this.inputCpfCnpjClient.data('url'),
				method : 'GET',
				contentType : 'application/json',
				data : {
					'cpfOuCnpj' : this.inputCpfCnpjClient.val()
				},
				beforeSend: startRequisicao.bind(this),
				complete: finalRequisicao.bind(this)
			});

			resposta.done(onGetStateFinal.bind(this));
		}
	}

	function onGetStateFinal(client) {
	
		if (client.id !== null) {
			$('#clientName').val(client.name);
			$('#client').val(client.id);
			$('#clinteEmail').val(client.email);
			$('#clientTelefone').val(client.celular);
			$('#clientPhoneNumber').val(client.phoneNumber);
			$('.attendece-js-img-new').hide();
		} else {
			$('#clientName').val('');
			$('#client').val('');
			$('#clinteEmail').val('');
			$('#clientTelefone').val('');
			$('#clientPhoneNumber').val('');
			$('.attendece-js-img-new').show();
		}
	}
	
	function startRequisicao() {
		$('.attendece-js-img-new').hide();
		this.imgLoading.show();
	}
	
	function finalRequisicao() {
		this.imgLoading.hide();
	}
	
	return BuscaClient;
}());

$(function() {

	var buscarCliente = new Alvorecer.BuscaClient();
	buscarCliente.enable();

}());