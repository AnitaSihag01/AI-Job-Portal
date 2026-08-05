package com.example.job_portal_ai.service;

import com.example.job_portal_ai.service.impl.ResumeParserService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeParserServiceImpl implements ResumeParserService {


    @Override
    public String extractText(MultipartFile file) {

        try(PDDocument document =
                    Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);

        } catch(Exception e) {
            throw new RuntimeException("Unable to read resume");
        }
    }
}