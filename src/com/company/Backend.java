package com.company;

//provides the backend of the program

import java.io.IOException;
import java.util.Scanner;

public class Backend extends Scrapable{
    private Scanner keyboard = new Scanner(System.in);
    private pdfManager pdfManager;
    private CSVMaker csvMaker = new CSVMaker();
    private String folderDir;

    public Backend()
    {

    }
    public void init() throws IOException
    {
        if(this.getPDFFolder())
        {
            showOptions();
        }else{
            System.exit(0);
        }
    }

    private boolean getPDFFolder() throws IOException
    {
        System.out.println("Enter the directory of the folder of PDFs.");
        String folderDir = keyboard.nextLine();

        if(super.isValidPath(folderDir))
        {
            this.folderDir = folderDir;
            System.out.println("Reading from " + this.folderDir + "...");
            this.pdfManager = new pdfManager(this.folderDir);
            if(this.pdfManager != null)
            {
                System.out.println("Number of pdf files found: " +
                        this.pdfManager.getNumFiles());
                return true;
            }else{
                System.out.println("Error in pdfManager creation");
                return false;
            }
        }else{
            System.out.println("Invalid path passed.");
            return false;
        }
    }
    private void showOptions()
    {

        boolean running = true;

        while(running){
            printSelections();
            int response = this.keyboard.nextInt();
            keyboard.nextLine();
            switch(response){
                case 0:
                    //code to create combined csv
                    this.createComboCSV();
                    break;
                case 1:
                    //code to create only individual csvs
                    //for these, csv file names are only the names of individual files
                    this.createIndividualCSVs();
                    break;
                case 2:
                    //code to create both individual and combined csvs
                    System.out.println("Creating both combo and individual csvs.");
                    this.createIndividualCSVs();
                    this.createComboCSV();
                    break;
                case 3:
                    //show files in the dir
                    this.showFiles();
                    break;
                case 4:
                    //quits
                    System.out.println("Goodbye");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
                    //i think this works
                    break;
            }
        }
        System.exit(0);
    }
    private void printSelections()
    {
        System.out.println("\nPress:\n" +
                "0 to create combined csv file\n" +
                "1 to create individual csv files\n" +
                "2 to create both individual and combined csv files\n" +
                "3 to view all files within the folder " + this.folderDir + ".\n" +
                "4 to quit.\n");
    }
    private void showFiles()
    {
        System.out.println("Showing pdf files: \n");
        for(int i=0;i<this.pdfManager.getPDFArr().size();i++)
        {
            System.out.println("File #" + (i + 1) + ": " + this.pdfManager.getPDFArr().get(i).getFileName());
        }
    }
    private void createComboCSV()
    {
        System.out.println("Enter the desired name of the combined csv.");
        String fileName = keyboard.nextLine();
        System.out.println("Creating compiled csv file...");
        this.csvMaker.createCSV(this.pdfManager.tokenPOSArr,fileName);
        System.out.println("Complete.");
    }
    private void createIndividualCSVs()
    {
        System.out.println("Creating individual csv files...");
        for(pdfFile f:this.pdfManager.getPDFArr())
        {
            this.csvMaker.createCSV(f.getTokenPOSArr(),f.getFileName());
        }
        System.out.println("Complete.");
    }
}
