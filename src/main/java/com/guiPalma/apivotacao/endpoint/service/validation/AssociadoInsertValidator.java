package com.guiPalma.apivotacao.endpoint.service.validation;

import java.util.ArrayList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.guiPalma.apivotacao.dto.AssociadoDto;
import com.guiPalma.apivotacao.exceptions.FieldMessage;
import com.guiPalma.apivotacao.util.ApiValidator;

public class AssociadoInsertValidator implements ConstraintValidator<AssociadoInsert, AssociadoDto>{

	@Override
	public boolean isValid(AssociadoDto associado, ConstraintValidatorContext context){
		
		var listaErro = new ArrayList<FieldMessage>();
		
		if(! ApiValidator.has(associado.getNome())){
			listaErro.add(new FieldMessage("Nome", "Não informado"));			
		}
		
		if(! ApiValidator.has(associado.getCpf())){
			listaErro.add(new FieldMessage("Cpf", "Não informado"));			
		}
		
		for (FieldMessage e : listaErro) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErro.isEmpty();		
		
	}
		
	}
		
		
