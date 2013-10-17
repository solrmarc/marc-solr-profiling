package edu.stanford;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;

/**
 * junit4 tests for getting specific 024 subfields based on indicator 1
 * @author Naomi Dushay
 */
public class Get024Tests extends AbstractStanfordTest
{
	private static MarcFactory factory = MarcFactory.newInstance();

	// Fixed Fields
	private static Leader scoreLeader = factory.newLeader("01262ncm a22002898i 4500");
	private static ControlField cf008generic = factory.newControlField("008");
	{
		cf008generic.setData("130625s2014    maua    a    0    0eng  ");
	}
	private DataField df024_0 = factory.newDataField("024", '0', ' ');
	private DataField df024_1 = factory.newDataField("024", '1', ' ');
	private DataField df024_2 = factory.newDataField("024", '2', ' ');
	private DataField df024_3 = factory.newDataField("024", '3', ' ');
	private DataField df024_4 = factory.newDataField("024", '4', ' ');
	private DataField df024_5 = factory.newDataField("024", '5', ' ');
	private DataField df024_6 = factory.newDataField("024", '6', ' ');
	private DataField df024_7 = factory.newDataField("024", '7', ' ');
	private DataField df024_8 = factory.newDataField("024", '8', ' ');
	private static Subfield suba = factory.newSubfield('a', "028947781677");
	private static Subfield subz = factory.newSubfield('z', "CNM512000000900");

@Before
	public void setUp()
	{
		mappingTestInit();
	}

@Test
	public void test024WithDesiredInd1()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		String fldName = "f024_0a_sim";
		df024_0.addSubfield(suba);
		record.addVariableField(df024_0);
		solrFldMapTest.assertSolrFldValue(record, fldName, suba.getData());

		fldName = "f024_3z_sim";
		df024_3.addSubfield(subz);
		record.addVariableField(df024_3);
		solrFldMapTest.assertSolrFldValue(record, fldName, subz.getData());

		fldName = "f024_8a_sim";
		df024_8.addSubfield(suba);
		record.addVariableField(df024_8);
		solrFldMapTest.assertSolrFldValue(record, fldName, suba.getData());
	}


@Test
	public void test024WithMultipleSubfields()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		String fldName = "f024_1a_sim";
		df024_1.addSubfield(suba);
		df024_1.addSubfield(factory.newSubfield('a', "666"));
		record.addVariableField(df024_1);
		solrFldMapTest.assertSolrFldValue(record, fldName, suba.getData());
		solrFldMapTest.assertSolrFldValue(record, fldName, "666");
	}

@Test
	public void test024WithMultiple024s()
	{
		Record record = factory.newRecord();
		record.setLeader(scoreLeader);
		record.addVariableField(cf008generic);
		String fldName = "f024_2z_sim";

		df024_2.addSubfield(subz);
		record.addVariableField(df024_2);

		DataField another = factory.newDataField("024", '2', ' ');
		another.addSubfield(factory.newSubfield('z', "666"));
		record.addVariableField(another);

		solrFldMapTest.assertSolrFldValue(record, fldName, subz.getData());
		solrFldMapTest.assertSolrFldValue(record, fldName, "666");
	}

}
