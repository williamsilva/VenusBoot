var Alvorecer = Alvorecer || {};

Alvorecer.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function name() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

Alvorecer.MaskDate = (function() {

	function MaskDate() {
		this.inputDate = $('.js-date');
	}

	MaskDate.prototype.enable = function name() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation : 'bottom',
			language : 'pt-BR',
			autoclose : true,
			todayHighlight : true
		});
	}

	return MaskDate;
}());

Alvorecer.MaskPhoneNumber = (function() {

	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}

	MaskPhoneNumber.prototype.enable = function name() {
		var maskBehavior = function(val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 0 0000-0000'
					: '(00) 0000-00009';
		};

		var options = {
			onKeyPress : function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}
		};

		this.inputPhoneNumber.mask(maskBehavior, options);
	}

	return MaskPhoneNumber;

}());

Alvorecer.MaskCodePostal = (function() {

	function MaskCodePostal() {
		this.inputCodePostal = $('.js-code-postal');
	}

	MaskCodePostal.prototype.enable = function() {
		this.inputCodePostal.mask('00.000-000');
	}

	return MaskCodePostal;

}());

$(function() {
	var maskDate = new Alvorecer.MaskDate();
	maskDate.enable();

	var maskPhoneNumber = new Alvorecer.MaskPhoneNumber();
	maskPhoneNumber.enable();

	var maskCodePostal = new Alvorecer.MaskCodePostal();
	maskCodePostal.enable();
	
	var security = new Alvorecer.Security();
	security.enable();

});