package com.guiPalma.apivotacao.endpoint.service.validation;

import java.util.ArrayList;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.guiPalma.apivotacao.dto.VotoDto;
import com.guiPalma.apivotacao.exceptions.FieldMessage;
import com.guiPalma.apivotacao.util.ApiValidator;

public class VotoInsertValidator implements ConstraintValidator<VotoInsert, VotoDto>{


	@Override
	public boolean isValid(VotoDto voto, ConstraintValidatorContext context) {
		
		var listaErro = new ArrayList<FieldMessage>();
		

		if(! ApiValidator.has(voto.getCpfAssociado())){
			listaErro.add(new FieldMessage("Cpf", "Não informado"));			
		}
		
		if(! ApiValidator.has(voto.getPauta())){
			listaErro.add(new FieldMessage("Pauta", "Não informada"));			
		}
		
		if(! ApiValidator.has(voto.getVoto())){
			listaErro.add(new FieldMessage("Voto", "Não informado"));			
		}
		
		if(ApiValidator.has(voto.getVoto())) {
			if(! validarSimOuNao(voto.getVoto())){
				listaErro.add(new FieldMessage("Voto", "Voto invalido. Utilize: [S]im, [N]ão"));
			}			
		}
		return listaErro.isEmpty();			
	}
	
    public boolean validarSimOuNao(String s){
        return s.matches("[sS]im | [nN][ãa]o | [sS] | [nN]");
    }

}
