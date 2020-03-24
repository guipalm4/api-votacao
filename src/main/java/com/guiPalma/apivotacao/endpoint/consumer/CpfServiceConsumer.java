package com.guiPalma.apivotacao.endpoint.consumer;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CpfServiceConsumer {

	@Value("${app.servicos-externos.cpf.endpoint}")
	private String urlServicoCpf;

	public CpfResponse validarCpf(String cpf) {
		final String uri = this.urlServicoCpf + "{cpf}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cpf", cpf);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, CpfResponse.class, params);
	}

}
