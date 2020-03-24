package com.guiPalma.apivotacao.endpoint.service.validation;

import java.util.ArrayList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.guiPalma.apivotacao.dto.AssociadoDto;
import com.guiPalma.apivotacao.exceptions.FieldMessage;
import com.guiPalma.apivotacao.util.ApiValidator;
import com.guiPalma.apivotacao.util.CampoUtil;

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
		
		if( ApiValidator.has(associado.getCpf())){
			if(CampoUtil.isCpf(CampoUtil.retiraMascaraCpf(associado.getCpf()))) {
				listaErro.add(new FieldMessage("Cpf", "Cpf inválido"));			
			}
		}
		
		for (FieldMessage e : listaErro) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErro.isEmpty();		
		
	}
		
	}
		
		
