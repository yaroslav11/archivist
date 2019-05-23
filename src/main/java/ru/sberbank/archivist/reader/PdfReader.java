package ru.sberbank.archivist.reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PdfReader implements Reader{

    public String getTextFromDocument(String documentName) {
        //Loading an existing document
        File file = getFileFromResources(documentName);

        try(PDDocument document = PDDocument.load(file)) {
            //Instantiate PDFTextStripper class
            PDFTextStripper pdfStripper = new PDFTextStripper();

            //Retrieving text from PDF document
            String text = pdfStripper.getText(document);
            return text;

        } catch (IOException ioe) {
            throw new RuntimeException(String.format("Cannot load pdf %s", documentName));
        }
    }

    // get file from classpath, resources folder
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }

    // get file by full path + name
    private File getFileByFullPath(String fileName) {
        return new File(fileName);
    }
}
