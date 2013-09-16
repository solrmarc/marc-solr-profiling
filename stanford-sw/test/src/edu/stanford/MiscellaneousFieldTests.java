package edu.stanford;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.xml.sax.SAXException;

import org.junit.*;
import org.marc4j.marc.*;

import static org.junit.Assert.*;


/**
 * junit4 tests for Stanford University revisions to solrmarc
 * @author Naomi Dushay
 */
public class MiscellaneousFieldTests extends AbstractStanfordTest
{
	/**
	 * Test correct document id - the id is from 001 with an a in front
	 */
@Test
	public final void testId()
		throws ParserConfigurationException, SAXException, IOException, SolrServerException
	{
		closeSolrProxy();  // need to reset the solrProxy to get the right request handling
		createFreshIx("idTests.mrc", true, false);
		String fldName = "id";

        int numDocs = getNumMatchingDocs("collection", "sirsi");
        assertEquals("Number of documents in index incorrect: ", 3, numDocs);
        assertDocNotPresent("001noSubNo004");
        assertDocPresent("001suba");
        assertDocNotPresent("001and004nosub");
        assertDocNotPresent("004noSuba");
        assertDocPresent("001subaAnd004nosub");
        assertDocNotPresent("004noSuba");
        assertDocPresent("001subaAnd004suba");
        assertDocNotPresent("004suba");

        assertSingleResult("001suba", fldName, "\"001suba\"");
        assertSingleResult("001subaAnd004nosub", fldName, "\"001subaAnd004nosub\"");
        assertSingleResult("001subaAnd004suba", fldName, "\"001subaAnd004suba\"");
	}


	/**
	 * Test that there is no field created when the translation map is missing
	 *  the value to be mapped and when the map has value set to null
	 */
@Test
	public final void testMapMissingValue()
			throws ParserConfigurationException, IOException, SAXException, SolrServerException
	{
		String fldName = "language";
		createFreshIx("langTests.mrc");

		assertZeroResults(fldName, "null");
		assertZeroResults(fldName, "\\?\\?\\?");
		assertZeroResults(fldName, "mis");     // 008mis041ak
		assertZeroResults(fldName, "Miscellaneous languages");
		assertZeroResults(fldName, "mul");     // 008mul041atha
		assertZeroResults(fldName, "Multiple languages");
		assertZeroResults(fldName, "und");
		assertZeroResults(fldName, "zxx");
	}


	/**
	 * Test population of allfields
	 */
@Test
	public final void testAllSearch()
			throws ParserConfigurationException, IOException, SAXException, SolrServerException
	{
		String fldName = "all_search";
		createFreshIx("allfieldsTests.mrc");

		String docId = "allfields1";

		// 245 just for good measure
        assertSingleResult(docId, fldName, "should");

        // 0xx fields are not included except 024, 027, 028
        assertSingleResult(docId, fldName, "2777802000"); // 024
        assertSingleResult(docId, fldName, "90620"); // 024
        assertSingleResult(docId, fldName, "technical"); // 027
        assertSingleResult(docId, fldName, "vibrations"); // 027
        assertZeroResults(fldName, "ocolcm");  // 035
        assertZeroResults(fldName, "orlob");  // 040

        // 3xx fields ARE included
        assertSingleResult(docId, fldName, "sound"); // 300
        assertSingleResult(docId, fldName, "annual");  // 310

        // 6xx subject fields - we're including them, even though
        // fulltopic is all subfields of all 600, 610, 630, 650, 655
        // fullgeographic is all subfields of all 651
        //   b/c otherwise standard numbers and other things are doubled here,
        //   but topics are not.

        // 9xx fields are NOT included
        assertZeroResults(fldName, "EDATA");  // 946
        assertZeroResults(fldName, "pamphlet");  // 947
        assertZeroResults(fldName, "stacks");  // 999

        // Except for 905, 920 and 986 (SW-814)
		mappingTestInit();
		MarcFactory factory = MarcFactory.newInstance();
		Record record = factory.newRecord();
        DataField df = factory.newDataField("905", ' ', ' ');
        df.addSubfield(factory.newSubfield('a', "905a"));
        df.addSubfield(factory.newSubfield('r', "905r"));
        df.addSubfield(factory.newSubfield('t', "905t"));
        record.addVariableField(df);
        df = factory.newDataField("908", ' ', ' ');
        df.addSubfield(factory.newSubfield('a', "908a"));
        df.addSubfield(factory.newSubfield('b', "908b"));
        record.addVariableField(df);
        df = factory.newDataField("920", ' ', ' ');
        df.addSubfield(factory.newSubfield('a', "920a"));
        df.addSubfield(factory.newSubfield('b', "920b"));
        record.addVariableField(df);
        df = factory.newDataField("986", ' ', ' ');
        df.addSubfield(factory.newSubfield('1', "986a"));
        record.addVariableField(df);
        solrFldMapTest.assertSolrFldValue(record, fldName, "905a 905r 905t 908a 908b 920a 920b 986a");
	}


	// NOTE:  see VernFieldsTests for  testVernCatchallField

	/**
	 * display_type is supposed to be a sort of "hidden" facet to allow UI
	 *  to look at appropriate types of records for different "views"
	 *  (e.g.  Images, Maps, Book Reader ...)
	 */
@Test
	public final void testDisplayTypeField()
	    throws ParserConfigurationException, IOException, SAXException, SolrServerException
	{
		closeSolrProxy();  // need to reset the solrProxy to get the right request handling
		createFreshIx("idTests.mrc", true, false);
	    String fldName = "display_type";

	    // all MARC records from Symphony
        assertEquals("docs aren't all display_type sirsi", 3, getNumMatchingDocs(fldName, "sirsi"));
	}


	/**
	 * test preservation of field ordering from marc input to marc stored in record
	 */
@Test
	public final void testFieldOrdering()
			throws ParserConfigurationException, IOException, SAXException, SolrServerException
	{
		createFreshIx("fieldOrdering.mrc");
		SolrDocument doc = getDocument("1");
		String marc21 = (String) doc.getFirstValue("marcxml");
		int ix650 = marc21.indexOf("650first");
		int ix600 = marc21.indexOf("600second");
		assertTrue("fields are NOT in the original order", ix650 < ix600);
	}

}
