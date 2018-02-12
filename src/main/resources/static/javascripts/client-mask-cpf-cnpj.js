var Alvorecer = Alvorecer || {};

Alvorecer.MascaraCpfCnpj = (function() {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.inputCpfCnpj = $('#cpfOuCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		var typeClientSelected = this.radioTipoPessoa.filter(':checked')[0];
		if(typeClientSelected){
			aplicMassk.call(this, $(typeClientSelected));
		}
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		
		aplicMassk.call(this, tipoPessoaSelecionada);
		
		this.inputCpfCnpj.val('');
		
	/*	$('#clientName').val('');
		$('#clienteEmail').val('');
		$('#clientTelefone').val('');
		$('#clientPhoneNumber').val('');
		$('#client').val(null);
		*/
		
	}
	
	function aplicMassk(tipoPessoaSelecionada) {
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.removeAttr('disabled');
		this.inputCpfCnpj.focus();
	}
	
	return MascaraCpfCnpj;
	
}());

$(function() {
	var mascaraCpfCnpj = new Alvorecer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});