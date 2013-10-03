package edu.stanford;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;

/**
 * junit4 tests for getting Leader bytes in StanfordIndexer
 * @author Naomi Dushay
 */
public class LeaderByteTests extends AbstractStanfordTest
{
	private static MarcFactory factory = MarcFactory.newInstance();
	private static ControlField cf008generic = factory.newControlField("008");
	{
		cf008generic.setData("130625s2014    maua    a    0    0eng  ");
	}

	// 999s
	private static DataField df999musicM = factory.newDataField("999", ' ', ' ');
	private static Subfield mCallno = factory.newSubfield('a', "M355 .B123");
	private static Subfield musicLib = factory.newSubfield('m', "MUSIC");
	private static Subfield lcType = factory.newSubfield('w', "LC");
	private static Subfield loc = factory.newSubfield('l', "STACKS");
	private static Subfield barcode = factory.newSubfield('i', "36105111222333");
    {
    	df999musicM.addSubfield(mCallno);
    	df999musicM.addSubfield(lcType);
    	df999musicM.addSubfield(barcode);
    	df999musicM.addSubfield(loc);
    	df999musicM.addSubfield(musicLib);
    }

@Before
	public void setUp()
	{
		mappingTestInit();
	}

@Test
	public void testLeaderByte06()
	{
		String fldName = "leader_byte_06_si";
		Record record = factory.newRecord();
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		record.setLeader(factory.newLeader("01262ncm a22002898i 4500"));
		solrFldMapTest.assertSolrFldValue(record, fldName, "c");

		record.setLeader(factory.newLeader("01262nam a22002898i 4500"));
		solrFldMapTest.assertSolrFldValue(record, fldName, "a");
	}

}
