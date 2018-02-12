var Alvorecer = Alvorecer || {};

Alvorecer.BuscaCep = (function() {

	function BuscaCep() {
		this.getCepBtn = $('.js-getcep-btn');
		this.cepInput = $('.js-cep');
		this.endereco = $('.js-endereco');
		this.bairro = $('.js-bairro');
		this.cidade = $('#cidade');
		this.estado = $('#estado');
		this.complemento = $('.js-complemento');
	}

BuscaCep.prototype.enable = function() {
		this.getCepBtn.on('click', onBuscaCep.bind(this));
		/* this.cepInput.on('keyup', onBuscaCep.bind(this)); */
	}

	function onBuscaCep(evento) {
		evento.preventDefault();

		var cep = this.cepInput.val().replace(/\D/g, '');

		if (cep != "") {
			var validacep = /^[0-9]{8}$/;
			
			if (validacep.test(cep)) {				
                
				$.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", Callback.bind(this));
				
           } //end if.
			else {
				// limpa_formulário_cep();
				alert("Formato de CEP inválido.");
			}
		}
	}
	
	function Callback(dados) {		
		 if (!("erro" in dados)) {
			 
			 this.estado.val(dados.uf);	
			 		 
			 this.endereco.val(dados.logradouro);
			 this.bairro.val(dados.bairro);
			 this.cidade.val(dados.localidade);			
			 this.complemento.val(dados.complemento);
			 
            /* $("#ibge").val(dados.ibge);*/
         } //end if.
         else {
        	 reset.call(this);
             alert("CEP não encontrado.");
         }
	}
	
	function reset() {
		this.cepInput.val('');
		this.endereco.val('');
		this.bairro.val('');
		this.cidade.val('');
		this.estado.val('');
		this.complemento.val('');
	}

	return BuscaCep;

}());

$(function() {
	var buscaCep = new Alvorecer.BuscaCep();
	buscaCep.enable();
});
