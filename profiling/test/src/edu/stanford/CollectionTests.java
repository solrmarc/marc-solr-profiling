package edu.stanford;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;
import org.solrmarc.tools.SolrMarcIndexerException;

import edu.stanford.enumValues.Format;

/**
 * junit4 tests for collection selection methods in StanfordIndexer
 * @author Naomi Dushay
 */
public class CollectionTests extends AbstractStanfordTest
{
	private static String collFldName = "collection";
	private static MarcFactory factory = MarcFactory.newInstance();

	// Fixed Fields
	private static Leader bookLeader = factory.newLeader("01539nam a2200421 a 4500");
	private static ControlField cf008generic = factory.newControlField("008");
	{
		cf008generic.setData("130625s2014    maua    a    0    0eng  ");
	}

	// 999s
	private static DataField df999musicNotM = factory.newDataField("999", ' ', ' ');
	private static DataField df999musicM = factory.newDataField("999", ' ', ' ');
	private static DataField df999musicML = factory.newDataField("999", ' ', ' ');
    private static DataField df999nonMusicM = factory.newDataField("999", ' ', ' ');
	private static DataField df999nonMusicNonLCM = factory.newDataField("999", ' ', ' ');
	private static Subfield notMcallno = factory.newSubfield('a', "PQ9661 .P31");
	private static Subfield mCallno = factory.newSubfield('a', "M355 .B123");
	private static Subfield musicLib = factory.newSubfield('m', "MUSIC");
	private static Subfield lcType = factory.newSubfield('w', "LC");
	private static Subfield loc = factory.newSubfield('l', "STACKS");
	private static Subfield barcode = factory.newSubfield('i', "36105111222333");
    {
    	df999musicNotM.addSubfield(notMcallno);
    	df999musicNotM.addSubfield(lcType);
    	df999musicNotM.addSubfield(barcode);
    	df999musicNotM.addSubfield(loc);
    	df999musicNotM.addSubfield(musicLib);

    	df999musicM.addSubfield(mCallno);
    	df999musicM.addSubfield(lcType);
    	df999musicM.addSubfield(barcode);
    	df999musicM.addSubfield(loc);
    	df999musicM.addSubfield(musicLib);

    	df999musicML.addSubfield(factory.newSubfield('a', "ML410 .C123"));
    	df999musicML.addSubfield(lcType);
    	df999musicML.addSubfield(barcode);
    	df999musicML.addSubfield(loc);
    	df999musicML.addSubfield(musicLib);

    	df999nonMusicM.addSubfield(mCallno);
    	df999nonMusicM.addSubfield(lcType);
    	df999nonMusicM.addSubfield(barcode);
    	df999nonMusicM.addSubfield(loc);
    	df999nonMusicM.addSubfield(factory.newSubfield('m', "GREEN"));

    	df999nonMusicNonLCM.addSubfield(factory.newSubfield('a', "M1347"));
    	df999nonMusicNonLCM.addSubfield(factory.newSubfield('w', "ALPHANUM"));
    	df999nonMusicNonLCM.addSubfield(barcode);
    	df999nonMusicNonLCM.addSubfield(loc);
    	df999nonMusicNonLCM.addSubfield(factory.newSubfield('m', "GREEN"));
    }


@Before
	public void setUp()
	{
		mappingTestInit();
	}

@Test
	public void testMusicScoreFormat()
	{
		Record record = factory.newRecord();
		record.setLeader(factory.newLeader("01262ncm a22002898i 4500"));
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		solrFldMapTest.assertSolrFldValue(record, "format", Format.MUSIC_SCORE.toString());
		solrFldMapTest.assertSolrFldValue(record, collFldName, "music");
	}

@Test
	public void testMusicRecordingFormat()
	{
		Record record = factory.newRecord();
		record.setLeader(factory.newLeader("02270cjm a2200421Ia 4500"));
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		solrFldMapTest.assertSolrFldValue(record, "format", Format.MUSIC_RECORDING.toString());
		solrFldMapTest.assertSolrFldValue(record, collFldName, "music");
	}

@Test
	public void testSoundRecordingFormat()
	{
		Record record = factory.newRecord();
		record.setLeader(factory.newLeader("01294cim a2200313Ia 4500"));
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		solrFldMapTest.assertSolrFldValue(record, "format", Format.SOUND_RECORDING.toString());
		solrFldMapTest.assertSolrFldValue(record, collFldName, "music");
	}

@Test
	public void testBookFormatWithMusicMCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);
	    record.addVariableField(df999musicM);
	    solrFldMapTest.assertSolrFldValue(record, "format", Format.BOOK.toString());
	    solrFldMapTest.assertSolrFldValue(record, collFldName, "music");
	}


@Test
	public void testBookFormatWithNonMusicMCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);
	    record.addVariableField(df999nonMusicM);
	    solrFldMapTest.assertSolrFldValue(record, "format", Format.BOOK.toString());
	    solrFldMapTest.assertSolrFldValue(record, collFldName, "music");
	}


@Test
	public void testBookFormatWithNoMCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);
		assertEmptyCollField(record);

	    record.addVariableField(df999musicNotM);
		assertEmptyCollField(record);

		record.addVariableField(df999musicML);
		assertEmptyCollField(record);

		record.addVariableField(df999nonMusicNonLCM);
		assertEmptyCollField(record);
	}

@Test
	public void testBookFormatWithMLaneCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);

		DataField df999LaneM = factory.newDataField("999", ' ', ' ');
		df999LaneM.addSubfield(factory.newSubfield('a', "M35 .L25 1895 "));
		df999LaneM.addSubfield(factory.newSubfield('w', "LC"));
		df999LaneM.addSubfield(barcode);
		df999LaneM.addSubfield(loc);
		df999LaneM.addSubfield(factory.newSubfield('m', "LANE-MED"));
		record.addVariableField(df999LaneM);
		assertEmptyCollField(record);

		record.addVariableField(df999nonMusicNonLCM);
		assertEmptyCollField(record);

		record.addVariableField(df999musicNotM);
		assertEmptyCollField(record);
	}


	private void assertEmptyCollField(Record record)
	{
		try
		{
			solrFldMapTest.assertSolrFldHasNumValues(record, collFldName, 0);
			fail("Expected SolrMarcIndexerException for record purposely not indexed");
		}
		catch (SolrMarcIndexerException e)
		{
			assertEquals("Record  purposely not indexed because collection field is empty", e.getMessage());
		}
	}

}
