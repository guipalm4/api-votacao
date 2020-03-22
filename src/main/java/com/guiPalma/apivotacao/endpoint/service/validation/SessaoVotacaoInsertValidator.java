package com.guiPalma.apivotacao.endpoint.service.validation;

import java.util.ArrayList;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.exceptions.FieldMessage;
import com.guiPalma.apivotacao.util.ApiValidator;

public class SessaoVotacaoInsertValidator implements ConstraintValidator<SessaoVotacaoInsert, SessaoVotacaoDto>{

	@Override
	public boolean isValid(SessaoVotacaoDto sessao, ConstraintValidatorContext context){
		
		var listaErro = new ArrayList<FieldMessage>();
		
		if(! ApiValidator.has(sessao.getDescricao())){
			listaErro.add(new FieldMessage("Descrição", "Não informada"));			
		}
		
		if(! ApiValidator.has(sessao.getPauta())){
			listaErro.add(new FieldMessage("Pauta", "Não informada"));			
		}
		
		for (FieldMessage e : listaErro) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErro.isEmpty();		
		
	}

}
