package edu.stanford;

import java.util.*;

import org.marc4j.marc.*;
import org.solrmarc.index.SolrIndexer;
import org.solrmarc.tools.Utils;

import edu.stanford.enumValues.Format;

/**
 * Format utility methods for Stanford solrmarc
 * 
 * @author Naomi Dushay
 */
public class FormatUtils {

	/**
	 * Default Constructor: private, so it can't be instantiated by other objects
	 */	
	private FormatUtils(){ }

	/**
	 * Assign formats per algorithm and marc bib record
	 */
	@SuppressWarnings("unchecked")
	static Set<String> getFormats(String leaderStr, ControlField cf008, VariableField f006, Set<Item> itemSet, Record record) 
	{
		Set<String> result = new HashSet<String>();

		// As of July 28, 2008, algorithms for formats are currently in email
		// message from Vitus Tang to Naomi Dushay, cc Phil Schreur, Margaret
		// Hughes, and Jennifer Vine dated July 23, 2008.

		// Note: MARC21 documentation refers to char numbers that are 0 based,
		// just like java string indexes, so char "06" is at index 6, and is
		// the seventh character of the field

		// assign formats based on leader chars 06, 07 and chars in 008
		char leaderChar07 = leaderStr.charAt(7);

		if (result.isEmpty() || result.size() == 0) {
			// look for serial publications - leader/07 s
			if (leaderChar07 == 's') {
				if (cf008 != null) {
					char c21 = ((ControlField) cf008).getData().charAt(21);
					switch (c21) {
					case 'd': // updating database (ignore)
						break;
					case 'l': // updating looseleaf (ignore)
						break;
					case 'm': // monographic series
						result.add(Format.BOOK.toString());
						break;
					case 'n':
						result.add(Format.NEWSPAPER.toString());
						break;
					case 'p':
						result.add(Format.JOURNAL_PERIODICAL.toString());
						break;
					case 'w': // web site
						break;
					}
				}
			}
		}

		// look for serial publications 006/00 s
		if (result.isEmpty() || result.size() == 0) {
			if (f006 != null && f006.find("^[s]")) {
				char c04 = ((ControlField) f006).getData().charAt(4);
				switch (c04) {
				case 'd': // updating database (ignore)
					break;
				case 'l': // updating looseleaf (ignore)
					break;
				case 'm': // monographic series
					result.add(Format.BOOK.toString());
					break;
				case 'n':
					result.add(Format.NEWSPAPER.toString());
					break;
				case 'p':
					result.add(Format.JOURNAL_PERIODICAL.toString());
					break;
				case 'w': // web site
					break;
				case ' ':
					result.add(Format.JOURNAL_PERIODICAL.toString());
				}
			}
			// if still nothing, see if 007/00s serial publication by default
			else if ((result.isEmpty() || result.size() == 0) && leaderChar07 == 's') {
				if (cf008 != null) 
				{
					char c21 = ((ControlField) cf008).getData().charAt(21);
					switch (c21) {
						case 'd':
						case 'l':
						case 'm':
						case 'n':
						case 'p':
						case 'w':
							break;
						case ' ':
							result.add(Format.JOURNAL_PERIODICAL.toString());
					}
				}
			}
		}

		// look for conference proceedings in 6xx
		List<DataField> dfList = (List<DataField>) record.getDataFields();
		for (DataField df : dfList) {
			if (df.getTag().startsWith("6")) {
				List<String> subList = Utils.getSubfieldStrings(df, 'x');
				subList.addAll(Utils.getSubfieldStrings(df, 'v'));
				for (String s : subList) {
					if (s.toLowerCase().contains("congresses")) {
						result.remove(Format.JOURNAL_PERIODICAL.toString());
						result.add(Format.CONFERENCE_PROCEEDINGS.toString());
					}
				}
			}
		}

		// check for format information from item call numbers
		for (Item item : itemSet) {
			String scheme = item.getCallnumScheme();
			if (scheme.equalsIgnoreCase("ALPHANUM")) {
				String callnum = item.getCallnum();
				if (callnum.startsWith("MFILM"))
					result.add(Format.MICROFORMAT.toString());
				else if (callnum.startsWith("MCD"))
					result.add(Format.MUSIC_RECORDING.toString());
				else if (callnum.startsWith("ZDVD") || callnum.startsWith("ADVD"))
					result.add(Format.VIDEO.toString());
			}
		}

		// if we still don't have a format, it's an "other"
		if (result.isEmpty() || result.size() == 0)
			result.add(Format.OTHER.toString());
		
		return result;
	}

	
	/**
	 * Assign formats based on leader chars 06, 07 and chars in 008
	 * 
	 * Algorithms for formats are currently in email  message from Vitus Tang to
	 *  Naomi Dushay, cc Phil Schreur, Margaret Hughes, and Jennifer Vine 
	 *  dated July 23, 2008.
	 *  
	 * @param leaderStr - the leader field, as a String
	 * @param cf008 - the 008 field as a ControlField object
	 * @param Set of Strings containing Format enum values per the given data
	 */
	@SuppressWarnings("unchecked")
	static Set<String> getFormatsPerLdrAnd008(String leaderStr, ControlField cf008) 
	{
		Set<String> result = new HashSet<String>();

		// Note: MARC21 documentation refers to char numbers that are 0 based,
		// just like java string indexes, so char "06" is at index 6, and is
		// the seventh character of the field

		// assign formats based on leader chars 06, 07 and chars in 008
		char leaderChar07 = leaderStr.charAt(7);
		char leaderChar06 = leaderStr.charAt(6);
		switch (leaderChar06) {
		case 'a':
			if (leaderChar07 == 'a' || leaderChar07 == 'm')
				result.add(Format.BOOK.toString());
			break;
		case 'b':
		case 'p':
			result.add(Format.MANUSCRIPT_ARCHIVE.toString());
			break;
		case 'c':
		case 'd':
			result.add(Format.MUSIC_SCORE.toString());
			break;
		case 'e':
		case 'f':
			result.add(Format.MAP_GLOBE.toString());
			break;
		case 'g':
			// look for m or v in 008 field, char 33 (count starts at 0)
			if (cf008 != null && cf008.find("^.{33}[mv]"))
				result.add(Format.VIDEO.toString());
			break;
		case 'i':
			result.add(Format.SOUND_RECORDING.toString());
			break;
		case 'j':
			result.add(Format.MUSIC_RECORDING.toString());
			break;
		case 'k':
    		// look for i, k, p, s or t in 008 field, char 33 (count starts at 0)
			if (cf008 != null && cf008.find("^.{33}[ikpst]"))
				result.add(Format.IMAGE.toString());
			break;
		case 'm':
			// look for a in 008 field, char 26 (count starts at 0)
			if (cf008 != null && cf008.find("^.*{26}a"))
				result.add(Format.COMPUTER_FILE.toString());
			break;
		case 'o': // instructional kit
			result.add(Format.OTHER.toString());
			break;
		case 'r': // object
			result.add(Format.OTHER.toString());
			break;
		case 't':
			if (leaderChar07 == 'a' || leaderChar07 == 'm')
				result.add(Format.BOOK.toString());
			break;
		} // end switch

		return result;
	}
	
	
	/**
	 * Assign format based on Serial publications - leader/07 s
	 * 
	 * Algorithms for formats are currently in email  message from Vitus Tang to
	 *  Naomi Dushay, cc Phil Schreur, Margaret Hughes, and Jennifer Vine 
	 *  dated July 23, 2008.
	 *  
	 * @param leaderStr - the leader field, as a String
	 * @param cf008 - the 008 field as a ControlField object
	 * @param Set of Strings containing Format enum values per the given data
	 */
	@SuppressWarnings("unchecked")
	static String getSerialFormat(char leaderChar07, ControlField cf008) 
	{
		String result = null;
		if (leaderChar07 == 's' && cf008 != null) {
			char c21 = ((ControlField) cf008).getData().charAt(21);
			result = getSerialFormatFromChar(c21);
		}
		if (result == null) {
			
		}
		
		return null;

	}
	
	/**
	 * Assign format if leader/07 s indicates a Serial format and the 
	 *  008 21st char has a desirable value.
	 * 
	 * @param leaderStr - the leader field, as a String
	 * @param cf008 - the 008 field as a ControlField object
	 * @return String containing Format enum value per the given data, or null
	 */
	@SuppressWarnings("unchecked")
	static String getSerialFormatLdr07s(char leaderChar07, ControlField cf008) 
	{
		if (leaderChar07 == 's' && cf008 != null) {
			char c21 = ((ControlField) cf008).getData().charAt(21);
			return getSerialFormatFromChar(c21);
		}
		return null;
	}
	
	// look for serial publications 006/00 s
	
	/**
	 * Assign format if 006 starts with 's' (?) and 4th char has a desirable
	 *  value.
	 * 
	 * @param f006 - 006 as a VariableField object
	 * @return String containing Format enum value per the given data, or null
	 */
	static String getSerialFormat006(VariableField f006) 
	{
		if (f006 != null && f006.find("^[s]")) {
			char c04 = ((ControlField) f006).getData().charAt(4);
			String format = getSerialFormatFromChar(c04);
			if (format != null)
				return format;
			if (c04 == ' ')
				return Format.JOURNAL_PERIODICAL.toString();
		}
		return null;
		
	}
	
	
	/**
	 * given a character assumed to be the 21st character (zero-based) from
	 *  the 008 field or the 4th char from an 006 field, return the format 
	 *  (assuming that there is an indication that the record is for a serial).
	 *  return null if no format is determined.
	 */
	private static String getSerialFormatFromChar(char ch) {
		switch (ch) {
//			case 'd': // updating database (ignore)
//				break;
//			case 'l': // updating looseleaf (ignore)
//				break;
			case 'm': // monographic series
				return Format.BOOK.toString();
			case 'n':
				return Format.NEWSPAPER.toString();
			case 'p':
				return Format.JOURNAL_PERIODICAL.toString();
//			case 'w': // web site
//				break;
		}
		return null;		
	}
	
	/**
	 * @param record
	 * @return true if there is a 245h that contains the string "microform", 
	 *  false otherwise
	 */
	static boolean isMicroformat(Record record) {
		Set<String> titleH = SolrIndexer.getSubfieldDataAsSet(record, "245", "h", " ");
		if (Utils.setItemContains(titleH, "microform"))
			return true;
		else
			return false;
	}
	
	/**
	 * thesis is determined by the presence of a 502 field.
	 * @param record
	 * @return true if there is a 502 field, false otherwise
	 */
	static boolean isThesis(Record record) {
//		Set<String> dissNote = SolrIndexer.getSubfieldDataAsSet(record, "502", "a", " ");
//		if (!dissNote.isEmpty() || dissNote.size() != 0)
        if (record.getVariableFields("502").isEmpty())
			return false;
		else
			return true;
	}
	
}
