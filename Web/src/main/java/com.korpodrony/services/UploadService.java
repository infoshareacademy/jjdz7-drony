package com.korpodrony.services;

import com.korpodrony.service.RepositoryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class UploadService {

    @Inject
    private RepositoryService repositoryService;

    public void uploadFile(InputStream fileContent, String fileName) throws IOException {
        repositoryService.saveFile(fileContent, fileName);
        repositoryService.loadRepositoryFromFile();
    }
}