package edu.stanford;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;

/**
 * junit4 tests ensuring fieldspec can have a numeric subfield
 * @author Naomi Dushay
 */
public class NumericSubfieldTests extends AbstractStanfordTest
{
	private static MarcFactory factory = MarcFactory.newInstance();

	// Fixed Fields
	private static Leader scoreLeader = factory.newLeader("01262ncm a22002898i 4500");
	private static ControlField cf008generic = factory.newControlField("008");
	{
		cf008generic.setData("130625s2014    maua    a    0    0eng  ");
	}

@Before
	public void setUp()
	{
		mappingTestInit();
	}

@Test
	public void testSubfield4()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);

		DataField df100 = factory.newDataField("100", ' ', ' ');
		df100.addSubfield(factory.newSubfield('4', "anything"));
		record.addVariableField(df100);
		solrFldMapTest.assertSolrFldValue(record, "f1004_sim", "anything");
	}


}
