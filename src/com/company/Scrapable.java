package com.company;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Scrapable {
    protected Tokenizer jTokenizer = Tokenizer.builder().build();
    protected List<Pair> tokenPOSArr = new ArrayList<>();

    protected void parseTokens(String rawText)
    {
        if(rawText != null)
        {
            for(Token token: this.jTokenizer.tokenize(rawText))
            {
                String res = token.getBaseForm();
                String POS = token.getPartOfSpeech().split(",")[0];
                if(res !=null)
                {
                    this.tokenPOSArr.add(new Pair(res,POS));
                }
            }
        }
    }

    protected boolean isValidPath(String p)
    {
        File file = new File(p);

        if (file.isDirectory()) {
            //System.out.println("File is a Directory");
            return true;
        } else {
            //System.out.println("Directory doesn't exist");
            return false;
        }
    }

}
