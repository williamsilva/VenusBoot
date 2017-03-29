var Alvorecer = Alvorecer || {};

Alvorecer.MaskCpfCnpj = (function() {

	function MaskCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa-fast');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.inputCpfCnpj = $('#cpfCnpj');
	}

	MaskCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
	}
	
	function onTipoPessoaAlterado(evento) {
		var typeClientSelect = $(evento.currentTarget);
		
		this.labelCpfCnpj.text(typeClientSelect.data('documento'));
		this.inputCpfCnpj.mask(typeClientSelect.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
		this.inputCpfCnpj.focus();
	}
	
	return MaskCpfCnpj;
	
}());

$(function() {
	var maskCpfCnpj = new Alvorecer.MaskCpfCnpj();
	maskCpfCnpj.iniciar();
});
