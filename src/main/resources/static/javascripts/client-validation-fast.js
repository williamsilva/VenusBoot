var Alvorecer = Alvorecer || {};

Alvorecer.Validation = (function() {

	function Validation() {
		this.modal = $('#modalRegistrationFastClient');
		this.form = this.modal.find('form');

		this.inputClientName = $('.client-requerid-name');
		this.inputClientCpfCnpj = $('.client-requerid-cpf-cnpj');
		this.inputClientEmail = $('.client-requerid-email');
		this.inputClientCity = $('.client-requerid-city');
	}

	Validation.prototype.iniciar = function() {
		this.inputClientName.on('focusout', onNameAlterado.bind(this));
		this.inputClientCpfCnpj.on('focusout', onCpfCnpjAlterado.bind(this));
		this.inputClientEmail.on('focusout', onEmailAlterado.bind(this));
		this.inputClientCity.on('change', onCityAlterado.bind(this));
	}
	
	function onCityAlterado() {
		var inputClientCity = $('#city');
		
		if (inputClientCity.val() === '') {
			this.form.find('.client-requerid-city').addClass('has-error');
		} else {
			this.form.find('.client-requerid-city').removeClass('has-error');
		}
	}

	function onNameAlterado(evento) {
		var inputClientName = $(evento.currentTarget);

		if ($('#name').val() === '') {
			this.form.find('.client-requerid-name').addClass('has-error');
		} else {
			this.form.find('.client-requerid-name').removeClass('has-error');
		}
	}

	function onCpfCnpjAlterado(evento) {
		var inputClientCpfCnpj = $(evento.currentTarget);

		if ($('#cpfCnpj').val() === '') {
			this.form.find('.client-requerid-cpf-cnpj').addClass('has-error');
		} else {
			this.form.find('.client-requerid-cpf-cnpj')
					.removeClass('has-error');
		}
	}

	function onEmailAlterado(evento) {
		var clientEmail = $('#email').val();

		if (clientEmail !== '') {
			if (IsEmail(clientEmail)) {
				this.form.find('.client-requerid-email').removeClass(
						'has-error');
			} else {
				this.form.find('.client-requerid-email').addClass('has-error');
			}
		}
	}

	function IsEmail(email) {
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

	return Validation;

}());

$(function() {
	var validation = new Alvorecer.Validation();
	validation.iniciar();
});