package com.guiPalma.apivotacao.endpoint.service.validation;

import java.util.ArrayList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.exceptions.FieldMessage;
import com.guiPalma.apivotacao.util.ApiValidator;

public class PautaInsertValidator implements ConstraintValidator<PautaInsert, PautaDto>{

	@Override
	public boolean isValid(PautaDto pauta, ConstraintValidatorContext context){
		
		var listaErro = new ArrayList<FieldMessage>();
		
		if(! ApiValidator.has(pauta.getDescricao())){
			listaErro.add(new FieldMessage("Descrição", "Não informada"));			
		}
		
		for (FieldMessage e : listaErro) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErro.isEmpty();		
		
	}
		
	}
		
		
