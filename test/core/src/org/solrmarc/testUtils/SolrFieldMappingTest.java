package org.solrmarc.testUtils;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;

/**
 * Utility Test class to determine if a marc record will map data to the Solr
 * fields as expected.
 * 
 * @author Naomi Dushay
 * @version $Id$
 * 
 */
public class SolrFieldMappingTest
{
    // Initialize logging category
    // static Logger logger =
    // Logger.getLogger(SolrFieldMappingTest.class.getName());

    /** marcMappingTest instance used to do the field mapping */
    private MarcMappingOnly marcMappingTest = null;

    /**
     * Constructor
     * 
     * @param configPropsName  name of xxx _config.properties file
     * @param idFldName  name of unique key field in Solr document
     */
    public SolrFieldMappingTest(String configPropsName, String idFldName) 
    		throws FileNotFoundException
    {
        marcMappingTest = new MarcMappingOnly();
        marcMappingTest.init(new String[] { configPropsName, idFldName });
    }

    /**
     * assert that when the file of marc records is processed, there will be a
     * Solr document with the given id containing at least one instance of the
     * expected Solr field with the expected value
     * 
     * @param mrcFileName  absolute path of file of marc records (name must end in .mrc or .marc or .xml)
     * @param solrDocId  value of unique key field for the Solr document to be checked
     * @param expectedFldName  name of the Solr field to be checked
     * @param expectedFldVal  the value expected to be in at least one instance of the Solr field for the indicated Solr document
     */
    public void assertSolrFldValue(String mrcFileName, String solrDocId, String expectedFldName, String expectedFldVal)
    {
    	try
    	{
	        Map<String, Object> solrFldName2ValMap = marcMappingTest.getIndexMapForRecord(solrDocId, mrcFileName);
	        if (solrFldName2ValMap == null)
	        	fail("There is no document with id " + solrDocId);
	
	        Object solrFldValObj = solrFldName2ValMap.get(expectedFldName);
	        if (solrFldValObj == null)
	            fail("Solr doc " + solrDocId + " has value assigned for Solr field " + expectedFldName);
	        if (solrFldValObj instanceof String)
	            assertEquals("Solr doc " + solrDocId + " didn't have expected value for Solr field " + expectedFldName + ": ", expectedFldVal, solrFldValObj.toString());
	        else if (solrFldValObj instanceof Collection)
	        {
	            // look for a match of at least one of the values
	            boolean foundIt = false;
	            for (String fldVal : (Collection<String>) solrFldValObj)
	            {
	                if (fldVal.equals(expectedFldVal))
	                    foundIt = true;
	                // System.out.println("DEBUG: value is [" + fldVal + "]");
	            }
	            assertTrue("Solr doc " + solrDocId + " did not have any " + expectedFldName + " fields with value matching " + expectedFldVal, foundIt);
	        }
        }
        catch (FileNotFoundException e)
        {
        	e.printStackTrace();
        	System.exit(666);
        }
    }

    /**
     * assert that when the file of marc records is processed, the Solr document
     * with the given id does NOT contain an instance of the indicated field
     * with the indicated value
     * 
     * @param mrcFileName  absolute path of file of marc records (name must end in .mrc or .marc or .xml)
     * @param solrDocId  value of unique key field for the Solr document to be checked
     * @param expectedFldName  name of the Solr field to be checked
     * @param expectedFldVal  the value that should be in NO instance of the Solr field for the indicated Solr document
     */
    public void assertSolrFldHasNoValue(String mrcFileName, String solrDocId, String expectedFldName, String expectedFldVal)
    {
    	try
    	{
	        Map<String, Object> solrFldName2ValMap = marcMappingTest.getIndexMapForRecord(solrDocId, mrcFileName);
	        if (solrFldName2ValMap == null)
	        	fail("there is no document with id " + solrDocId);
	
	        Object solrFldValObj = solrFldName2ValMap.get(expectedFldName);
	        if (solrFldValObj instanceof String)
	            assertFalse("Solr field " + expectedFldName + " unexpectedly has value [" + expectedFldVal + "]", solrFldValObj.toString().equals(expectedFldVal));
	        else if (solrFldValObj instanceof Collection)
	        {
	            // make sure none of the values match
	            for (String fldVal : (Collection<String>) solrFldValObj)
	            {
	                if (fldVal.equals(expectedFldVal))
	                    fail("Solr field " + expectedFldName + " unexpectedly has value [" + expectedFldVal + "]");
	            }
	        }
        }
        catch (FileNotFoundException e)
        {
        	e.printStackTrace();
        	System.exit(666);
        }
    }
    
    /**
     * assert that when the file of marc records is processed, the Solr document
     * with the given id contains the expected number of instances of the 
     * indicated field
     * 
     * @param mrcFileName  absolute path of file of marc records (name must end in .mrc or .marc or .xml)
     * @param solrDocId  value of unique key field for the Solr document to be checked
     * @param expectedFldName  name of the Solr field to be checked
     * @param expectedNumVals  the number of values that should be in the Solr field for the indicated Solr document
     */
    public void assertSolrFldHasNumValues(String mrcFileName, String solrDocId, String expectedFldName, int expectedNumVals)
    {
    	try
    	{
	        Map<String, Object> solrFldName2ValMap = marcMappingTest.getIndexMapForRecord(solrDocId, mrcFileName);
	        if (solrFldName2ValMap == null)
	        	fail("there is no document with id " + solrDocId);
	
	        Object solrFldValObj = solrFldName2ValMap.get(expectedFldName);
	        if (solrFldValObj == null && expectedNumVals != 0)
	        	fail("Solr field "+ expectedFldName + " unexpectedly has no values; expected " + String.valueOf(expectedNumVals));
	        if (solrFldValObj instanceof String) 
	        {
	        	if (expectedNumVals != 1) 
	        		fail("Solr field " + expectedFldName + " unexpectedly has a single value " + solrFldValObj.toString() + "; expected " + String.valueOf(expectedNumVals));
	       	}
	        else if (solrFldValObj instanceof Collection)
	        {
	        	int numVals = ((Collection<String>) solrFldValObj).size();
	            assertTrue("Solr field " + expectedFldName + " unexpectedly has " + numVals + " values; expected " + expectedNumVals, expectedNumVals == numVals);
	        }
	    }
	    catch (FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    	System.exit(666);
	    }
    }
    
    /**
     * assert that when the file of marc records is processed, the Solr document
     * with the given id will not have the named field
     * 
     * @param mrcFileName  absolute path of file of marc records (name must end in .mrc or .marc or .xml)
     * @param solrDocId  value of unique key field for the Solr document to be checked
     * @param fldName  name of the Solr field to be checked
     */
    public void assertNoSolrFld(String mrcFileName, String solrDocId, String fldName)
    {
    	try
    	{
	        Map<String, Object> solrFldName2ValMap = marcMappingTest.getIndexMapForRecord(solrDocId, mrcFileName);
	        if (solrFldName2ValMap == null)
	        	fail("there is no document with id " + solrDocId);
	        
	        Object solrFldValObj = solrFldName2ValMap.get(fldName);
	        if (solrFldValObj != null)
	            fail("There is a value assigned for Solr field " + fldName + " in Solr document " + solrDocId);
	    }
	    catch (FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    	System.exit(666);
	    }
    }


}
