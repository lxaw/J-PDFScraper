package com.company;

/*
Written by Lex Whalen
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class CSVMaker {
    // creates a CSV from sorted HashMap
    private List<Pair> tokenPOSArr;
    private HashMap<String,Integer> freqHash = new HashMap<>();

    public CSVMaker()
    {

    }

    public void createCSV(List<Pair> tokenPOSArr, String fileName)
    {
        this.tokenPOSArr = tokenPOSArr;
        putInHashMap();
        sortHashMap();
        exportCSV(fileName);
    }

    private void putInHashMap()
    {
        for(Pair pair: this.tokenPOSArr)
        {
            String token = pair.first.toString();
            String POS = pair.second.toString();

            String entry = String.format("%s,%s",token,POS);
            Integer count = this.freqHash.get(entry);
            if(count == null)
            {
                // not yet in hm, so put in w/ a 1
                this.freqHash.put(entry,1);
            }
            else{
                // already in hm, so increment by 1
                this.freqHash.put(entry,count+1);
            }
        }
    }
    private void sortHashMap()
    {
        List<Map.Entry<String,Integer>> list =
                new LinkedList<Map.Entry<String,Integer>>(this.freqHash.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2)

            {
                int i = o1.getValue().compareTo(o2.getValue());
                if(i!=0) return -i; // reverse sort
                return o1.getValue().compareTo(o2.getValue());
            }

        });
        //put data from sorted list into hm
        HashMap<String, Integer> temp = new LinkedHashMap<String,Integer>();

        for(Map.Entry<String,Integer> aa:list)
        {
            temp.put(aa.getKey(),aa.getValue());
        }
        this.freqHash = temp;
    }

    public void exportCSV(String outCSVFileName)
    {
        String eol = System.getProperty("line.separator");
        try
        {
            try(Writer writer = new FileWriter(outCSVFileName+".csv"))
            {
                writer.append("token,pos,frequency");
                writer.append(eol);
                for(Map.Entry<String,Integer> entry: this.freqHash.entrySet())
                {
                    writer.append(entry.getKey())
                            .append(',')
                            .append((entry.getValue()).toString())
                            .append(eol);
                }

            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
    }

}
