Alvorecer = Alvorecer || {};

Alvorecer.StartDate = (function() {

	function StartDate() {

	}

	StartDate.prototype.enable = function() {
		$('.js-date-inicial').datepicker("setDate", new Date());
	}

	return StartDate;

}());

Alvorecer.MaskDateInicial = (function() {

	function MaskDateInicial() {
		this.inputDate = $('.js-date-inicial');
	}

	MaskDateInicial.prototype.enable = function name() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation : 'bottom',
			language : 'pt-BR',
			autoclose : true,
			todayHighlight : true,
			startDate : '-7d',
			endDate : '+7d'
		});
	}

	return MaskDateInicial;
}());

Alvorecer.MaskDateFinal = (function() {

	function MaskDateFinal() {
		this.inputDateFinal = $('.js-date-final');
	}

	MaskDateFinal.prototype.enable = function name() {
		this.inputDateFinal.mask('00/00/0000');
		this.inputDateFinal.datepicker({
			orientation : 'bottom',
			language : 'pt-BR',
			autoclose : true,
			todayHighlight : true,
			startDate : '-7d',
			endDate : '+7d'
		});
	}

	return MaskDateFinal;
}());

Alvorecer.MaskHora = (function() {

	function MaskHora() {
		this.inputHora = $('.js-hora');
	}

	MaskHora.prototype.enable = function name() {
		this.inputHora.mask('00:00');
	}

	return MaskHora;

}());

$(function() {

	var maskDateInicial = new Alvorecer.MaskDateInicial();
	maskDateInicial.enable();

	var maskDateFinal = new Alvorecer.MaskDateFinal();
	maskDateFinal.enable();

	var startDate = new Alvorecer.StartDate();
	startDate.enable();
	
	var maskHora = new Alvorecer.MaskHora();
	maskHora.enable();
});