package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.curso.jpa2.dao.ModeloCarroDao;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;
import com.algaworks.curso.jpa2.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ModeloCarro.class)
public class ModeloCarroConverter implements Converter {

	private ModeloCarroDao modeloCarroDao;

	public ModeloCarroConverter() {
		this.modeloCarroDao = CDIServiceLocator.getBean(ModeloCarroDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ModeloCarro retorno = null;
		if(value.length() == 0)
			value = "0";
		if (value != null) {
			retorno = this.modeloCarroDao.buscaPeloCodigo(new Long(value));
		}
		if(retorno == null)
			retorno = new ModeloCarro();
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Long codigo = ((ModeloCarro) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}
		return "";
	}

}
