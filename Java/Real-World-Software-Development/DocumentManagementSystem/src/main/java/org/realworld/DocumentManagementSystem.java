package org.realworld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentManagementSystem {
    private final Map<String, Importer> extensionsToImporter = new HashMap<>();

    public DocumentManagementSystem() {
        extensionsToImporter.put("letter", new LetterImporter());
        extensionsToImporter.put("report", new ReportImporter());
        extensionsToImporter.put("jpg", new ImageImporter());
    }

    void importFile(String path) {

    }

    List<Document> contents() {
        return null;
    }
}
