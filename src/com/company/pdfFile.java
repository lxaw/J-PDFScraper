package com.company;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Pattern;

/*
Written by Lex Whalen
 */

public class pdfFile extends Scrapable {

    // a wonderful pattern to match japanese text
    private Pattern jPattern = Pattern.compile("[ぁ-んァ-ン一-龯]+",Pattern.DOTALL);

    private String filePath;
    private String fileName;
    private PDDocument document;
    private String foundText;

    private File file;

    public pdfFile(String filePath) throws IOException
    {
        init(filePath);
    }


    private void init(String filePath) throws IOException
    {
        // basic init of pdfFile
        this.filePath = setFilePath(filePath);
        this.file = setFile(filePath);

        this.document = PDDocument.load(new File(this.filePath));

        //get the file name
        this.fileName = setFileName(this.file);

        //get the text
        this.getPDFText();
        super.parseTokens(this.foundText);
    }

    public void printTokenPOSArr()
    {
        for(int i=0;i<super.tokenPOSArr.size();i++)
        {
            System.out.println(tokenPOSArr.get(i));
        }
    }

    public List<Pair> getTokenPOSArr()
    {
        return super.tokenPOSArr;
    }

    private void getPDFText() throws IOException
    {
        // scrapes the pdf's text and puts into this.foundText
        if(!this.document.isEncrypted())
        {
            PDFTextStripper stripper = new PDFTextStripper();
            this.foundText = stripper.getText(document);
        }
        this.document.close();
    }
    private String setFileName(File file)
    {
        if(file != null)
        {
            String name = file.getName();
            int endIndex = name.indexOf(".pdf");
            return name.substring(0,endIndex);
        }
        else{
            return "unknown";
        }
    }


    private File setFile(String fP)
    {
        if(fP != null)
        {
            return new File(fP);
        }
        else{
            System.out.println("invalid path");
            return null;
        }
    }

    private String setFilePath(String fP)
    {
        if(fP != null)
        {
            return fP;
        }
        else
        {
            return null;
        }
    }
    public String toString()
    {
        return "Found text for filename: " + this.fileName + "\n" + this.foundText;
    }

    public String getFileName()
    {
        return this.fileName;
    }

}
