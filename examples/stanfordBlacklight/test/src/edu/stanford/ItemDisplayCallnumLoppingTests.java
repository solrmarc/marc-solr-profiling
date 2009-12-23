package edu.stanford;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.*;
import static org.junit.Assert.assertTrue;
import org.xml.sax.SAXException;

/**
 * junit4 tests for Stanford University call number fields for blacklight index
 * @author Naomi Dushay
 */
public class ItemDisplayCallnumLoppingTests extends AbstractStanfordBlacklightTest {

	private final String fldName = "item_display";
	private final boolean isSerial = true;
	private final String SEP = " -|- ";
	private final String shelByTitl = "Shelved by title";
	private String testFilePath = testDataParentPath + File.separator + "callNumVolLopTests.mrc";


@Before
	public void setup() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		mappingTestInit();
	}	

	/**
	 * test that volume number for serial is reverse in the full call number
	 *  volume sort field
	 */
@Test
	public void testSerialSuffixIsReverse()
			throws ParserConfigurationException, IOException, SAXException 
	{
		createIxInitVars("callNumVolLopTests.mrc");
		String callnum = "TX519 .D26S 1954 V.2";
// TODO: currently look for other suffixes before we resort to year ...
//		String lopped = "TX519 .D26S";
		String lopped = "TX519 .D26S 1954";
		String recId = "cutterEndsLetLCSerial";
		String shelfkey = edu.stanford.CallNumUtils.getShelfKey(lopped, lcScheme, recId).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, isSerial, recId);

		String[] values = getDocument(recId).getValues(fldName);
		for (String val : values) {
			if (val.startsWith("36105049317907")) {
				assertTrue("volSort doesn't reverse vol number for serial", !volSort.contains("v.2"));
				assertTrue("volSort doesn't reverse vol number for serial", volSort.contains("4~zzzzzx"));
			}
		}
	}

	/**
	 * test vol lopping of month suffix
	 */
@Test
	public void testMonthSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		// LC
		String callnum = "BM198.2 .H85 OCT 2006";
		String lopped = "BM198.2 .H85";
		String id = "Months";
		String shelfkey = edu.stanford.CallNumUtils.getShelfKey(lopped, lcScheme, id).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		String fldVal = "36105127767619 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 NOV 2006";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105127767627 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 DEC 2006";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105127767635 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 JAN 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105127767643 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 FEB 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105127767650 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 MAR 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105127767668 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 APR 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104107 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 MAY 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104115 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 JUN 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104123 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 JUL 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104124 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 AUG 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104125 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
		callnum = "BM198.2 .H85 SEP 2007";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, id);
		fldVal = "36105122104126 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has BOX suffix
	 */
@Test
	public void testBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 BOX 1";
		String lopped = "M1522";
		String id = "box";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, id).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, id);
		String fldVal = "36105115680386 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, id, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has carton suffix
	 */
@Test
	public void testCartonSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1479 CARTON 1";
		String lopped = "M1479";
		String recId = "carton";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115855228 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has flat box suffix
	 */
@Test
	public void testFlatBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 FLAT BOX 17";
		String lopped = "M1522";
		String recId = "flatBox";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115690641 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has half box suffix
	 */
@Test
	public void testHalfBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 HALF BOX 1";
		String lopped = "M1522";
		String recId = "halfBox";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115680386 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has half carton suffix
	 */
@Test
	public void testHalfCartonSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 HALF CARTON 1";
		String lopped = "M1522";
		String recId = "halfCarton";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115680386 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has carton suffix
	 */
@Test
	public void testIndexSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "ML1 .I614 INDEX 1969-1986";
		String lopped = "ML1 .I614";
		String recId = "index";
		String shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		String fldVal = "36105006679893 -|- MUSIC -|- R-STACKS" + SEP + SEP + "NH-7DAY" + SEP +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has large map folder suffix
	 */
@Test
	public void testLargeMapFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 LARGE MAP FOLDER 26";
		String lopped = "M1522";
		String recId = "largeMapFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has large folder suffix
	 */
@Test
	public void testLargeFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 LARGE FOLDER 26";
		String lopped = "M1522";
		String recId = "largeFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has map folder suffix
	 */
@Test
	public void testMapFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 MAP FOLDER 26";
		String lopped = "M1522";
		String recId = "mapFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
		
	/**
	 * test vol lopping when call number has mfilm reel suffix
	 */
@Test
	public void testMfilmReelSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "CD3031 .A35 T-60 MFILM REEL 3";
		String lopped = "CD3031 .A35 T-60";
		String recId = "mfilmReel";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105073572195 -|- HOOVER -|- MICROFILM" + SEP + SEP + "MFILM" + SEP +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has os box suffix
	 */
@Test
	public void testOSBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 OS BOX 26";
		String lopped = "M1522";
		String recId = "osBox";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has os folder suffix
	 */
@Test
	public void testOSFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 OS FOLDER 26";
		String lopped = "M1522";
		String recId = "osFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT" + SEP + SEP + "MANUSCRIPT" + SEP +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has small map folder suffix
	 */
@Test
	public void testSmallMapFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 SMALL MAP FOLDER 26";
		String lopped = "M1522";
		String recId = "smallMapFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has small folder suffix
	 */
@Test
	public void testSmallFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 SMALL FOLDER 26";
		String lopped = "M1522";
		String recId = "smallFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has tube suffix
	 */
@Test
	public void testTubeSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1522 TUBE 26";
		String lopped = "M1522";
		String recId = "tube";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115640307 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- MANUSCRIPT -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has Series Box Suffix
	 */
@Test
	public void testSeriesBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "SC 165 SERIES 5 BOX 1";
		String lopped = "SC 165";
		String recId = "seriesBox";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105116222980 -|- SPEC-COLL -|- UARCH-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
		
		callnum = "M1090 SERIES 24 BOX 1";
		lopped = "M1090";
		shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		fldVal = "36105115652104 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has series half suffix
	 */
@Test
	public void testSeriesHalfBoxSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1090 SERIES 16 HALF BOX 1.1";
		String lopped = "M1090";
		String recId = "seriesHalfBox";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115668217 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "M1090 SERIES 6 HALF BOX 39B";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, otherScheme, !isSerial, recId);
		fldVal = "36105115691045 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has series os folder suffix
	 */
@Test
	public void testSeriesOSFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1090 SERIES 16 OS FOLDER 276.3";
		String lopped = "M1090";
		String recId = "seriesOSFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115689627 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has series small folder suffix
	 */
@Test
	public void testSeriesSmallFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1090 SERIES 16 SMALL FOLDER 72.06";
		String lopped = "M1090";
		String recId = "seriesSmallFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115689692 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number has series small map folder suffix
	 */
@Test
	public void testSeriesSmallMapFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1090 SERIES 16 SMALL MAP FOLDER 72.02";
		String lopped = "M1090";
		String recId = "seriesSmallMapFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115689635 -|- SPEC-COLL -|- MANUSCRIPT -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number has series large map folder suffix
	 */
@Test
	public void testSeriesLargMapFolderSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "M1090 SERIES 16 LARGE MAP FOLDER 276.5";
		String lopped = "M1090";
		String recId = "seriesLargeMapFolder";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105115689759 -|- SPEC-COLL -|- MSS-30 -|-  -|- NONCIRC -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when serial and call number has 4 digit year suffix
	 */
@Test
	public void testSerialYearSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "QD1 .C59 1975:P.1-742";
		String lopped = "QD1 .C59";
		String recId = "year4digitSerial";
		String shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, isSerial, recId);
		String fldVal = "36105002195076 -|- SAL -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "QD1 .C59 1972:P.1207-2456";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, isSerial, recId);
		fldVal = "36105002195134 -|- SAL -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when not serial and call number has 4 digit year suffix
	 */
@Test
	public void testNonSerialYearSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "QD1 .C59 1975:P.1-742";
		String lopped = "QD1 .C59 1975:P.1-742";
		String recId = "year4digitNonSerial";
		String shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		String fldVal = "36105002195076 -|- SAL -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "QD1 .C59 1972:P.1207-2456";
		lopped = "QD1 .C59 1972:P.1207-2456";
		shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		fldVal = "36105002195134 -|- SAL -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when call number is california gov doc
	 */
@Test
	public void testCalifGovDocSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "CALIF T900 .J6 V.1-2";
// FIXME:  problem will get corrected with longest common prefix
//		String lopped = "CALIF T900 .J6";
		String lopped = "CALIF";
		String recId = "govDocCalif";
		String shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, alphanumScheme, !isSerial, recId);
		String fldVal = "36105123936382 -|- GREEN -|- CALIF-DOCS" + SEP + SEP + "GOVSTKS" + SEP +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
		
		callnum = "CALIF L1080 .J67 V.1-12:NO.1";
		lopped = "CALIF L1080 .J67";
		shelfkey = CallNumUtils.getShelfKey(lopped, alphanumScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, otherScheme, !isSerial, recId);
		fldVal = "36105123634045 -|- GREEN -|- CALIF-DOCS" + SEP + SEP + "GOVSTKS" + SEP +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when LC call number has cutter ending in letter(s)
	 */
@Test
	public void testCutterEndsLetLCSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "TX519 .D26S 1954 V.1";
		String lopped = "TX519 .D26S 1954";
		String recId = "cutterEndsLetLC";
		String shelfkey = CallNumUtils.getShelfKey(lopped, lcScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, recId);
		String fldVal = "36105049317899 -|- EDUCATION -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		// serial
		callnum = "TX519 .D26S 1954 V.2";
		lopped = "TX519 .D26S";
		recId = "cutterEndsLetLCSerial";
		shelfkey = CallNumUtils.getShelfKey(lopped, lcScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, isSerial, recId);
		fldVal = "36105049317907 -|- EDUCATION -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
		
		// grade
		callnum = "TX519 .L18ST GRADE 1";
		lopped = "TX519 .L18ST";
		recId = "cutterEndsLetLCGrade";
		shelfkey = CallNumUtils.getShelfKey(lopped, lcScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcScheme, !isSerial, recId);
		fldVal = "36105049323657 -|- EDUCATION -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when Dewey call number has cutter ending in letter(s)
	 */
@Test
	public void testCutterEndsLetDeweySuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "505 .N285B V.241-245 1973";
		String lopped = "505 .N285B";
		String recId = "cutterEndsLetDewey";
		String shelfkey = CallNumUtils.getShelfKey(lopped, deweyScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, deweyScheme, !isSerial, recId);
		String fldVal = "36105000923040 -|- PHYSICS -|- STACKS -|-  -|- PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		// dewey cutter invalid: starts 2 letters - treated as non-dewey		
		callnum = "888.4 .JF78A V.5";
		lopped = "888.4 .JF78A";
		shelfkey = CallNumUtils.getShelfKey(lopped, otherScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, otherScheme, !isSerial, recId);
		fldVal = "36105002486350 -|- GREEN -|- STACKS -|-  -|- STKS-MONO -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when LC call number has colon in vol suffix
	 */
@Test
	public void testColonLCSuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = "Q1 .N2 V.434:NO.7031 2005:MAR.17";
		String lopped = "Q1 .N2";
		String recId = "colonLC";
		String shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		String fldVal = "36105113299601 -|- EARTH-SCI -|- STACKS -|-  -|- PERIUNBND -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "Q1 .N2 V.421-426 2003:INDEX";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		fldVal = "36105113872662 -|- EARTH-SCI -|- STACKS -|-  -|- PERIUNBND -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

// FIXME:  trailing period now removed
//		callnum = "Q1 .N2 V.171 1953:JAN.-MAR.";
		callnum = "Q1 .N2 V.171 1953:JAN.-MAR";
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		fldVal = "36105126662050 -|- GREEN -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

//		callnum = "Q1 .S34 V.293:5527-5535 2001:JUL.-AUG.";
		callnum = "Q1 .S34 V.293:5527-5535 2001:JUL.-AUG";
		lopped = "Q1 .S34";
		shelfkey = CallNumUtils.getShelfKey(lopped, lcperScheme, recId).toLowerCase();
		reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		volSort = CallNumUtils.getVolumeSortCallnum(callnum, lopped, shelfkey, lcperScheme, !isSerial, recId);
		fldVal = "36105028826514 -|- SAL3 -|- STACKS -|-  -|- STKS-PERI -|- " +
				lopped + SEP + shelfkey + SEP + reversekey + SEP + callnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}

	/**
	 * test vol lopping when Dewey call number has colon in vol suffix 
	 */
@Test
	public void testColonDeweySuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		// Note:  these are Shelbytitle
		// String callnum = "505 .N285 V.434:1-680 2005";
		String volSuffix = "V.434:1-680 2005";
		String recId = "colonDewey";
		String shelfkey = CallNumUtils.getShelfKey(shelByTitl, "DEWEYPER", recId).toLowerCase();
		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String fullItemCallnum = shelByTitl + " " + volSuffix;
		String volSort = CallNumUtils.getVolumeSortCallnum(fullItemCallnum, shelByTitl, shelfkey, deweyScheme, isSerial, recId);
		String fldVal = "36105121608587 -|- CHEMCHMENG -|- SHELBYTITL" + SEP + SEP + "PERI2" + SEP +
				shelByTitl + SEP + shelfkey + SEP + reversekey + SEP + fullItemCallnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		// callnum = "505 .N285 V.458:543--1212 2009";
		volSuffix = "V.458:543--1212 2009";
		fullItemCallnum = shelByTitl + " " + volSuffix;
		volSort = CallNumUtils.getVolumeSortCallnum(fullItemCallnum, shelByTitl, shelfkey, otherScheme, isSerial, recId);
		fldVal = "36105123660933 -|- CHEMCHMENG -|- SHELBYTITL" + SEP + SEP + "PERI2" + SEP +
				shelByTitl + SEP + shelfkey + SEP + reversekey + SEP + fullItemCallnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
	/**
	 * test vol lopping when call number is only the volume info
	 */
@Test
	public void testCallNumVolOnlySuffix() 
			throws ParserConfigurationException, IOException, SAXException 
	{
		String callnum = " V.432: NO.7013-7017-7020 2004";
		String volSuffix = callnum.trim();
		String recId = "volOnly";
// FIXME:  shelfkey for shelbytitle should be call number from bib record, if there is one ...
		String shelfkey = shelByTitl.toLowerCase();

		String reversekey = org.solrmarc.tools.CallNumUtils.getReverseShelfKey(shelfkey).toLowerCase();
		String fullItemCallnum = shelByTitl + " " + volSuffix;
		String volSort = CallNumUtils.getVolumeSortCallnum(fullItemCallnum, shelByTitl, shelfkey, otherScheme, isSerial, recId);
		String fldVal = "36105024533866 -|- HOPKINS -|- SHELBYTITL" + SEP + SEP + "PERI" + SEP +
				shelByTitl + SEP + shelfkey + SEP + reversekey + SEP + fullItemCallnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "V.433: NO.7021-7024 2005";
		volSuffix = callnum;
		fullItemCallnum = shelByTitl + " " + volSuffix;
		volSort = CallNumUtils.getVolumeSortCallnum(fullItemCallnum, shelByTitl, shelfkey, otherScheme, isSerial, recId);
		fldVal = "36105024533981 -|- HOPKINS -|- SHELBYTITL" + SEP + SEP + "PERI" + SEP +
				shelByTitl + SEP + shelfkey + SEP + reversekey + SEP + fullItemCallnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);

		callnum = "V.421/426 2003 INDEX";
		volSuffix = callnum;
		fullItemCallnum = shelByTitl + " " + volSuffix;
		volSort = CallNumUtils.getVolumeSortCallnum(fullItemCallnum, shelByTitl, shelfkey, otherScheme, isSerial, recId);
		fldVal = "36105028435308 -|- HOPKINS -|- SHELBYTITL" + SEP + SEP + "PERI" + SEP +
				shelByTitl + SEP + shelfkey + SEP + reversekey + SEP + fullItemCallnum + SEP + volSort;
	    solrFldMapTest.assertSolrFldValue(testFilePath, recId, fldName, fldVal);
	}
	
}