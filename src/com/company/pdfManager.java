package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Written by Lex Whalen
 */

public class pdfManager extends Scrapable{
    // loops through a given folder of pdfs and
    // creates / manages pdf info
    private Pattern pdfExtPattern = Pattern.compile(".*\\.pdf");

    private String pdfFolderDirName;
    private int numFiles = 0;

    private List<pdfFile> pdfArr = new ArrayList<>();

    public pdfManager(String pdfFolderDirName) throws IOException
    {
        init(pdfFolderDirName);
    }

    private void init(String pdfFolderDirName) throws IOException
    {
        if(super.isValidPath(pdfFolderDirName))
        {
            this.pdfFolderDirName = pdfFolderDirName;

            setPDFArr(this.pdfFolderDirName);
            appendPOSTokenArrs();
        }
        else{
            this.pdfFolderDirName = null;
        }

    }
    public void printMasterPOSTokenArrs()
    {
        for(Pair pair: super.tokenPOSArr)
        {
            System.out.println(pair);
        }
    }

    private void appendPOSTokenArrs()
    {
        // appends each pdfFile's POSTokenArr to the manager's.
        for(int i=0;i<this.pdfArr.size();i++)
        {
            super.tokenPOSArr.addAll(this.pdfArr.get(i).getTokenPOSArr());
        }
    }

    private void setPDFArr(String pdfFolderDirName) throws IOException
    {
        if(pdfFolderDirName != null)
        {
            File dir = new File(pdfFolderDirName);
            File[] files = dir.listFiles();

            // set pdfFile[] to size of File[]

            for(int i =0; i<files.length;i++)
            {
                String path = files[i].getAbsolutePath();
                Matcher matcher = this.pdfExtPattern.matcher(path);
                if(matcher.matches()){
                    this.pdfArr.add(new pdfFile(path));
                    this.numFiles +=1;
                }else{
                    System.out.println("Not a pdf file: " + path);
                }
            }
        }
    }
    public List<pdfFile> getPDFArr()
    {
        return this.pdfArr;
    }

    public int getNumFiles()
    {
        return this.numFiles;
    }

    public String toString()
    {
        StringBuilder bigString = new StringBuilder();

        for(int i=0;i<this.pdfArr.size();i++)
        {
            bigString.append(this.pdfArr.get(i).toString());
        }

        return bigString.toString();
    }
}
