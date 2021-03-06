package org.solrmarc.marc;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.solrmarc.tools.RawRecord;

/**
 * Read a binary marc file
 * @author Robert Haschart
 * @version $Id: RawRecordReader.java 1500 2011-07-22 18:36:27Z rh9ec@virginia.edu $
 *
 */
public class RawRecordReader
{
    // Initialize logging category
    static Logger logger = Logger.getLogger(RawRecordReader.class.getName());

    private DataInputStream input;
    RawRecord nextRec = null;
    RawRecord afterNextRec = null;
    boolean mergeRecords = true;
    
    public RawRecordReader(InputStream is)
    {
        input = new DataInputStream(new BufferedInputStream(is));
    }
    
    public RawRecordReader(InputStream is, boolean mergeRecords)
    {
        this.mergeRecords = mergeRecords;
        input = new DataInputStream(new BufferedInputStream(is));
    }
    
    public boolean hasNext()
    {
        if (nextRec == null)
        {
            nextRec = new RawRecord(input);
        }
        if (nextRec != null && nextRec.getRecordBytes() != null)
        {
            if (afterNextRec == null)
            {
                afterNextRec = new RawRecord(input);
                if (mergeRecords)
                {
                    while (afterNextRec != null && afterNextRec.getRecordBytes() != null && afterNextRec.getRecordId().equals(nextRec.getRecordId()))
                    {
                        nextRec = new RawRecord(nextRec, afterNextRec);
                        afterNextRec = new RawRecord(input);
                    }
                }
           }
            return(true);
        }
        return(false);
    }
    
    public RawRecord next() 
    {
        RawRecord tmpRec = nextRec;
        nextRec = afterNextRec;
        afterNextRec = null;
        return(tmpRec);
    }
    
	
//    private static int parseRecordLength(byte[] leaderData) throws IOException {
//        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(
//                leaderData));
//        int length = -1;
//        char[] tmp = new char[5];
//        isr.read(tmp);
//        try {
//            length = Integer.parseInt(new String(tmp));
//        } catch (NumberFormatException e) {
//            throw new IOException("unable to parse record length");
//        }
//        return(length);
//    }
    
	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args)
    {
    //    try {
        RawRecordReader reader;
        
        if (args.length < 2)
        {
            System.err.println("Error: No records specified for extraction");
        }
        try
        {
            if (args[0].equals("-"))
            {
                reader = new RawRecordReader(System.in);
            }
            else
            {    
                reader = new RawRecordReader(new FileInputStream(new File(args[0])));
            }            
            if (args[1].equals("-id"))
            {
                printIds(reader);
            }
            else if (args[1].equals("-h") && args.length >= 3)
            {
                String idRegex = args[2].trim();
                processInput(reader, null, idRegex, null);
            }
            else if (!args[1].endsWith(".txt"))
            {
                String idRegex = args[1].trim();
                processInput(reader, idRegex, null, null);
            }
            else 
            {
                File idList = new File(args[1]);
                BufferedReader idStream = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(idList))));
                String line;
                String findReplace[] = null;
                if (args.length > 2) findReplace = args[2].split("->");
                LinkedHashSet<String> idsLookedFor = new LinkedHashSet<String>();
                while ((line = idStream.readLine()) != null)
                {
                    if (findReplace != null)
                    {
                        line = line.replaceFirst(findReplace[0], findReplace[1]);
                    }
                    idsLookedFor.add(line);
                }
                processInput(reader, null, null, idsLookedFor);

            }
        }
        catch (EOFException e)
        {
            //  Done Reading input,   Be happy
        }
        catch (IOException e)
        {
            //  e.printStackTrace();
            logger.error(e.getMessage());
        }

    }
    
    static void printIds(RawRecordReader reader) throws IOException
    {
        while (reader.hasNext())
        {
            RawRecord rec = reader.next();
            String id = rec.getRecordId();
            System.out.println(id);
        }
    }

    static void processInput(RawRecordReader reader, String idRegex, String recordHas, HashSet<String>idsLookedFor) throws IOException
    {
        while (reader.hasNext())
        {
            RawRecord rec = reader.next();
            String id = rec.getRecordId();
            if ( (idsLookedFor == null && recordHas == null && id.matches(idRegex)) ||
                 (idsLookedFor != null && idsLookedFor.contains(id) ) )
            { 
                byte recordBytes[] = rec.getRecordBytes();
                System.out.write(recordBytes);
                System.out.flush();
            }
            else if (idsLookedFor == null && idRegex == null && recordHas != null)
            {
                String tag = recordHas.substring(0, 3);
                String field = rec.getFieldVal(tag);
                if (field != null)
                {
                    byte recordBytes[] = rec.getRecordBytes();
                    System.out.write(recordBytes);
                    System.out.flush();
                }
            }
        }
    }

}
