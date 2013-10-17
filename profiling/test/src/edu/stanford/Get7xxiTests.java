package edu.stanford;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;

/**
 * junit4 tests for getting 7XXi subfields
 * @author Naomi Dushay
 */
public class Get7xxiTests extends AbstractStanfordTest
{
	private static MarcFactory factory = MarcFactory.newInstance();
	private static String fldName = "f7xxi_sim";

	// Fixed Fields
	private static Leader scoreLeader = factory.newLeader("01262ncm a22002898i 4500");
	private static ControlField cf008generic = factory.newControlField("008");
	{
		cf008generic.setData("130625s2014    maua    a    0    0eng  ");
	}
	private DataField df700 = factory.newDataField("700", ' ', ' ');
	private DataField df710 = factory.newDataField("710", ' ', ' ');
	private DataField df796 = factory.newDataField("796", ' ', ' ');
	private static Subfield subi1 = factory.newSubfield('i', "subi1");
	private static Subfield subi2 = factory.newSubfield('i', "subi2");
	private static Subfield subi3 = factory.newSubfield('i', "subi3");
	private static Subfield suba = factory.newSubfield('a', "suba");

@Before
	public void setUp()
	{
		mappingTestInit();
	}

@Test
	public void testDiff7xxFields()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		df700.addSubfield(subi1);
		record.addVariableField(df700);
		df710.addSubfield(subi2);
		record.addVariableField(df710);
		solrFldMapTest.assertSolrFldValue(record, fldName, "subi1");
		solrFldMapTest.assertSolrFldValue(record, fldName, "subi2");
	}

@Test
	public void testOne7xxWMultSubi()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		df700.addSubfield(subi1);
		df700.addSubfield(subi3);
		record.addVariableField(df700);
		solrFldMapTest.assertSolrFldValue(record, fldName, "subi1");
		solrFldMapTest.assertSolrFldValue(record, fldName, "subi3");
	}


@Test
	public void test7xxButNoSubi()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		df700.addSubfield(suba);
		df796.addSubfield(suba);

		solrFldMapTest.assertSolrFldHasNumValues(record, fldName, 0);
	}

}
