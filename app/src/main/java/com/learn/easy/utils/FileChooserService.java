package com.learn.easy.utils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileChooserService {

    public String getStringFromFile(File file) {
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        String resultString = "";
        switch (extension) {
            case "txt":
                resultString = getTxtString(file);
                break;
            case "docx":
                resultString = getDocxString(file);
                break;
            case "pdf":
                resultString = getPdfString(file);
                break;
        }
        return resultString;
    }

    private String getTxtString(File file) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    private String getDocxString(File file) {
        String result = "";
        try {
            InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(document);
            result = oleTextExtractor.getText();
            System.out.println("LOG_result doc: = " + result);
        } catch (Exception e) {
            System.out.println("LOG_result doc: = ERROR");
            e.printStackTrace();
        }
        return result;
    }

    private String getPdfString(File file) {
        try {
            StringBuilder parsedText = new StringBuilder();
            PdfReader reader = new PdfReader(new FileInputStream(file));
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText.append(PdfTextExtractor.getTextFromPage(reader, i + 1).trim()).append("\n");
            }
            reader.close();
            String result = parsedText.toString();
            System.out.println("LOG_result pdf: = " + result);
            return parsedText.toString();
        } catch (Exception e) {
            System.out.println("LOG_result pdf: = ERROR");
            return "";
        }
    }

    private static FileChooserService instance;

    public static FileChooserService newInstance() {
        if (instance == null) {
            instance = new FileChooserService();
        }

        return instance;
    }
}
