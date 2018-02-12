Alvorecer = Alvorecer || {};

Alvorecer.BtnSubmit = (function() {
	
	function BtnSubmit() {
		this.submitBtn = $('.js-submit-btn');
		this.formulario = $('.js-formulario-principal');
	}
	
	BtnSubmit.prototype.enable = function() {
		this.submitBtn.on('click', onSubmit.bind(this));
	}
	
	function onSubmit(evento) {
		evento.preventDefault();
		
		var botaoClicado = $(evento.target);
		var acao = botaoClicado.data('acao');
		
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		this.formulario.append(acaoInput);
		this.formulario.submit();
	}
	
	return BtnSubmit;
	
}());

$(function() {
	var btnSubimit = new Alvorecer.BtnSubmit();
	btnSubimit.enable();
});