package edu.stanford;

import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.*;

import edu.stanford.enumValues.Format;

/**
 * junit4 tests for collection selection methods in StanfordIndexer
 * @author Naomi Dushay
 */
public class CollectionTests extends AbstractStanfordTest
{
	private static String fldName = "collection";
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
		solrFldMapTest.assertSolrFldValue(record, fldName, "music");
	}

@Test
	public void testMusicRecordingFormat()
	{
		Record record = factory.newRecord();
		record.setLeader(factory.newLeader("02270cjm a2200421Ia 4500"));
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		solrFldMapTest.assertSolrFldValue(record, "format", Format.MUSIC_RECORDING.toString());
		solrFldMapTest.assertSolrFldValue(record, fldName, "music");
	}

@Test
	public void testSoundRecordingFormat()
	{
		Record record = factory.newRecord();
		record.setLeader(factory.newLeader("01294cim a2200313Ia 4500"));
		record.addVariableField(cf008generic);
		record.addVariableField(df999musicM);
		solrFldMapTest.assertSolrFldValue(record, "format", Format.SOUND_RECORDING.toString());
		solrFldMapTest.assertSolrFldValue(record, fldName, "music");
	}

@Test
	public void testBookFormatWithMCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);
	    record.addVariableField(df999musicM);
	    solrFldMapTest.assertSolrFldValue(record, "format", Format.BOOK.toString());
	    solrFldMapTest.assertSolrFldValue(record, fldName, "music");
	}


//#   Book, Thesis, Conf proceedings  with M call number




	public void testAddlFormatsWithMCallNum()
	{

	}

@Test
	public void testBookFormatWithNoMCallNum()
	{
		Record record = factory.newRecord();
		record.setLeader(bookLeader);
		record.addVariableField(cf008generic);
		solrFldMapTest.assertNoRecordExists(record);

	    record.addVariableField(df999musicNotM);
		solrFldMapTest.assertNoRecordExists(record);

		record.addVariableField(df999musicML);
		solrFldMapTest.assertNoRecordExists(record);

		record.addVariableField(df999nonMusicNonLCM);
		solrFldMapTest.assertNoRecordExists(record);
	}

}
