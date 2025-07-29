package io.project.dossier.service;

import org.springframework.core.io.ByteArrayResource;

public interface DocumentService {
    ByteArrayResource generateCreditPdf(String creditJson);
}
