var Alvorecer = Alvorecer || {};

Alvorecer.ComboState = (function() {

	function ComboState() {
		this.state = $('#state');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	ComboState.prototype.iniciar = function() {
		this.state.on('change', onStateAlterado.bind(this));
	}

	function onStateAlterado() {
		this.emitter.trigger('alterado', this.state.val());
	}

	return ComboState;

}());

Alvorecer.ComboCity = (function() {

	function ComboCity(comboState) {
		this.comboState = comboState;
		this.city = $('#city');
		this.imgLoading = $('.js-img-loading-city');
		this.inputHiddenCitySelected = $('#inputHiddenCitySelected');
	}

	ComboCity.prototype.iniciar = function() {
		reset.call(this);
		this.comboState.on('alterado', onStateAlterado.bind(this));	
		var idState = this.comboState.state.val();
		initCity.call(this, idState);
	}

	function onStateAlterado(evento, codeState) {
		this.inputHiddenCitySelected.val('');
		initCity.call(this, codeState);
	}
	
	function initCity(codeState) {
		if(codeState){
			var resposta = $.ajax({
				url : this.city.data('url'),
				method : 'GET',
				contentType : 'application/json',
				data : {'state' : codeState},
				beforeSend: startRequisicao.bind(this),
				complete: finalRequisicao.bind(this)
				});			
			resposta.done(onGetCityFinal.bind(this));
		}else{
			reset.call(this);
		}
	}
	
	function onGetCityFinal(city) {
		var options = [];
		options.push('<option value="">Selecione uma Cidade</option>');
		city.forEach(function(city) {
			options.push('<option value ="' + city.id + '">' + city.name + '</option>');
		});
		
		this.city.html(options.join(''));
		this.city.removeAttr('disabled');
		
		var idCitySelected = this.inputHiddenCitySelected.val();
		if (idCitySelected) {
			this.city.val(idCitySelected);
		}
	}
	
	function reset() {
		this.city.html('<option value="">Selecione a Cidade </option>');
		this.city.val('');
		this.city.attr('disabled', 'disabled');
	}
	
	function startRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboCity;

}());

$(function() {
	var comboState = new Alvorecer.ComboState();
	comboState.iniciar();

	var comboCity = new Alvorecer.ComboCity(comboState);
	comboCity.iniciar();

});
