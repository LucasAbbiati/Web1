package br.ufscar.dc.dsw.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IRestauranteDAO;
import br.ufscar.dc.dsw.domain.Restaurante;

@Component
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

	@Autowired
	private IRestauranteDAO dao;

	@Override
	public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
		if (dao != null) {
			Restaurante restaurante = dao.findByCNPJ(CNPJ);
			return restaurante == null;
		} else {
			return true;
		}

	}
}