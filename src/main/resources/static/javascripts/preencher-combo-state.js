$(function() {
	
	var modal = $('#modalRegistrationFastClient');
	var comboState = $('#state');
	imgLoading = $('.js-img-loading-state');
	
	modal.on('shown.bs.modal', onModalShow);
	
	function onModalShow() {
				
		var resposta = $.ajax({
			url: comboState.data('url'),
			method : 'GET',
			contentType : 'application/json',
			data :{'state' : '1'},
			beforeSend: startRequisicao,
			complete: finalRequisicao
			
		});
		
		resposta.done(onGetStateFinal);
	}
	
	function onGetStateFinal(states) {
		var options = [];
		options.push('<option value="">Selecione um Estado </option>');
		states.forEach(function(states) {
			options.push('<option value ="' + states.id + '">' + states.name + '</option>');
		});
		
		comboState.html(options.join(''));
		comboState.removeAttr('disabled');
	}
	
	function startRequisicao() {
		imgLoading.show();
	}
	
	function finalRequisicao() {
		imgLoading.hide();
	}
});
