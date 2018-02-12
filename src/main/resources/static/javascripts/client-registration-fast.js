var Alvorecer = Alvorecer || {};

Alvorecer.ClientRegistrationFast = (function() {

	function ClientRegistrationFast() {
		this.modal = $('#modalRegistrationFastClient');
		this.btnSave = this.modal.find('.js-modal-client-fast-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.containerMesageErro = $('.js-mensage-cadastro-fast-client');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa-fast');
		/**
		 * Variaveis de montagem do objeto Cliente
		 * 
		 */
		this.inputTypeClient = $('input[name=typeClient]');
		this.inputCpfOuCnpjClient = $('#cpfCnpj');
		this.inputNameClient = $('#name');
		this.inputEmailClient = $('#email');
		this.inputCelularClient = $('#cellPhone');
		this.inputPhoneNumberClient = $('#phoneNumber');
		this.inputCodePostalClient = $('#codePostal');
		this.inputCityClient = $('#city');
		this.inputNumberClient = $('#number');
		this.inputNeighborhoodClient = $('#neighborhood');
		this.inputStreetClient = $('#street');
		this.inputReferenceClient = $('#reference');
		
		this.buscarCliente = new Alvorecer.BuscaClient();
	}

	ClientRegistrationFast.prototype.iniciar = function() {
		this.form.on('submit', function(event) {
			event.preventDefault()
		});
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.btnSave.on('click', onButonSaveClick.bind(this));
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
	}

	function onModalShow() {

	}

	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		this.inputTypeClient.val(tipoPessoaSelecionada.data('value'));
	}

	function onModalClose() {
		this.inputTypeClient.attr('checked', false);
		this.inputCpfOuCnpjClient.val('');
		this.inputCpfOuCnpjClient.attr('disabled', 'disabled');
		this.inputNameClient.val('');
		this.inputEmailClient.val('');
		this.inputCelularClient.val('');
		this.inputPhoneNumberClient.val('');
		this.inputCityClient
				.html('<option value="">Selecione a Cidade </option>');
		this.inputCityClient.attr('disabled', 'disabled');
		this.inputNumberClient.val('');
		this.inputNeighborhoodClient.val('');
		this.inputStreetClient.val('');
		this.inputReferenceClient.val('');
		this.inputCodePostalClient.val('');
		this.labelCpfCnpj.text('CPF-CNPJ');

		this.containerMesageErro.addClass('hidden');
		this.form.find('.client-requerid-city').removeClass('has-error');
		this.form.find('.client-requerid-name').removeClass('has-error');
		this.form.find('.client-requerid-email').removeClass('has-error');
		this.form.find('.client-requerid-cpf-cnpj').removeClass('has-error')
	}

	function onButonSaveClick() {
		if (this.inputCityClient.val() !== '') {
			this.form.find('.client-requerid-city').removeClass('has-error');
			$.ajax({
				url : this.url,
				method : 'POST',
				contentType : 'application/json',
				data : JSON.stringify({
					name : this.inputNameClient.val().trim(),
					email : this.inputEmailClient.val(),
					typeClient : this.inputTypeClient.val(),
					cpfOuCnpj : this.inputCpfOuCnpjClient.val(),
					celular : this.inputCelularClient.val(),
					phoneNumber : this.inputPhoneNumberClient.val(),
					codePostal : this.inputCodePostalClient.val(),
					number : this.inputNumberClient.val(),
					neighborhood : this.inputNeighborhoodClient.val(),
					street : this.inputStreetClient.val(),
					reference : this.inputReferenceClient.val(),
					city : {
						id : this.inputCityClient.val(),
						name: this.inputCityClient.val(),
					}
				}),
				error : onErroSaveClient.bind(this),
				success : onClientSave.bind(this)
			});
		}else{
			this.containerMesageErro.removeClass('hidden');
			var mensageErro = 'Informe uma Cidade';
			this.form.find('.client-requerid-city').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
	}
	
	function onClientSave(client) {
		$('.js-cpfOuCnpj').val(client.cpfOuCnpj);
		this.buscarCliente.enable();
		this.modal.modal('hide');
		$('.attendece-js-img-new').hide();
	}

	function onErroSaveClient(obj) {
		var mensageErro = obj.responseText;
		this.containerMesageErro.removeClass('hidden');
		
		console.log('Erro ao salvar Cliente ', mensageErro)

		if (obj.responseText === 'CPF/CNPJ é obrigatório') {
			this.form.find('.client-requerid-cpf-cnpj').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}

		if (obj.responseText === 'Nome do Cliente é obrigatório') {
			this.form.find('.client-requerid-name').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}

		if (obj.responseText === 'E-mail informado inválido') {
			this.form.find('.client-requerid-email').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
		
		if (obj.responseText === 'Cliente já cadastrado') {
			this.form.find('.client-requerid-cpf-cnpj').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
		
		if (obj.responseText === 'Informe uma Cidade') {
			this.form.find('.client-requerid-city').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
		
		if (obj.responseText === 'CPF inválido') {
			this.form.find('.client-requerid-cpf-cnpj').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
		
		if (obj.responseText === 'CNPJ inválido') {
			this.form.find('.client-requerid-cpf-cnpj').addClass('has-error');
			this.containerMesageErro.html('<span>' + mensageErro + '</span>');
		}
	}

	return ClientRegistrationFast;

}());

$(function() {
	var clientRegistrationFast = new Alvorecer.ClientRegistrationFast();
	clientRegistrationFast.iniciar();
});
