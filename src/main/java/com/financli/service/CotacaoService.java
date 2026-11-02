package com.financli.service;

import com.financli.model.CotacaoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Service
public class CotacaoService {

    private static final Logger LOG = LoggerFactory.getLogger(CotacaoService.class);

    private final RestClient restClient;
    private final String cotacaoUrl;

    public CotacaoService(@Value("${cotacao.api.url}") String cotacaoUrl) {
        this.restClient = RestClient.create();
        this.cotacaoUrl = cotacaoUrl;
    }

    public Optional<CotacaoResponse> buscarCotacoes() {
        try {
            CotacaoResponse resposta = restClient.get()
                    .uri(cotacaoUrl)
                    .retrieve()
                    .body(CotacaoResponse.class);
            return Optional.ofNullable(resposta);
        } catch (RestClientException e) {
            LOG.warn("Falha ao consultar a API de cotações: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
