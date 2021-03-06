package org.solrmarc.marc;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.solrmarc.index.SolrIndexer;
import org.solrmarc.tools.*;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *
 * @author Robert Haschart
 * @version $Id: MarcFilteredReader.java 1547 2011-10-11 21:25:37Z rh9ec@virginia.edu $
 *
 */
public class MarcFilteredReader implements MarcReader
{
    String includeRecordIfFieldPresent = null;
    String includeRecordIfFieldContains = null;
    String includeRecordIfFieldMissing = null;
    String includeRecordIfFieldDoesntContain = null;
    String deleteSubfieldsSpec = null;
    Record currentRecord = null;
    MarcReader reader;
    SolrMarcException exception;

    // Initialize logging category
    static Logger logger = Logger.getLogger(MarcFilteredReader.class.getName());

    /**
     *
     * @param r
     * @param ifFieldPresent
     * @param ifFieldMissing
     */
    public MarcFilteredReader(MarcReader r, String ifFieldPresent, String ifFieldMissing, String deleteSubfields)
    {
        deleteSubfieldsSpec = deleteSubfields;
        if (ifFieldPresent != null)
        {
            String present[] = ifFieldPresent.split("/", 2);
            includeRecordIfFieldPresent = present[0];
            if (present.length > 1)
            {
                includeRecordIfFieldContains = present[1];
            }
        }
        if (ifFieldMissing != null)
        {
            String missing[] = ifFieldMissing.split("/", 2);
            includeRecordIfFieldMissing = missing[0];
            if (missing.length > 1)
            {
                includeRecordIfFieldDoesntContain = missing[1];
            }
        }
        reader = r;
    }

    /**
     * Implemented through interface
     * @return Returns true if the iteration has more records, false otherwise
     */
    public boolean hasNext()
    {
        if (currentRecord == null)
        {
            currentRecord = next();
        }
        return(currentRecord != null);
    }

    /**
     * Returns the next marc file in the iteration
     */
    public Record next()
    {

    	if (currentRecord != null)
        {
            Record tmp = currentRecord;
            currentRecord = null;
            return(tmp);
        }

        while (currentRecord == null)
        {
            if (!reader.hasNext()) return(null);
            Record rec = null;

            try {
                rec = reader.next();
            }
            catch (MarcException me)
            {
                //System.err.println("Error reading Marc Record: "+ me.getMessage());
//            	exception = new SolrMarcException(me.getMessage(), me.getCause());
//            	exception.printMessage("Error reading Marc record:");
//            	exception.printStackTrace();
            	logger.error("Error reading Marc Record.");
            	logger.error(me.getMessage());
            }
            if (deleteSubfieldsSpec != null)
            {
                String fieldSpecs[] = deleteSubfieldsSpec.split(":");
                for (String fieldSpec : fieldSpecs)
                {
                    String tag = fieldSpec.substring(0,3);
                    String subfield = null;
                    if (fieldSpec.length() > 3)  subfield = fieldSpec.substring(3);
                    List<VariableField> list = (List<VariableField>)rec.getVariableFields(tag);
                    for (VariableField field : list)
                    {
                        if (field instanceof DataField)
                        {
                            DataField df = ((DataField)field);
                            if (subfield != null)
                            {
                                List<Subfield> sfs = (List<Subfield>)df.getSubfields(subfield.charAt(0));
                                if (sfs != null && sfs.size() != 0)
                                {
                                    rec.removeVariableField(df);
                                    for (Subfield sf : sfs)
                                    {
                                        df.removeSubfield(sf);
                                    }
                                    rec.addVariableField(df);
                                }
                            }
                            else
                            {
                                rec.removeVariableField(df);
                            }
                        }
                    }
                }
            }
            if (rec != null && includeRecordIfFieldPresent != null)
            {
                Set<String> fields = MarcUtils.getFieldList(rec, includeRecordIfFieldPresent);
                if (fields.size() != 0)
                {
                    if (includeRecordIfFieldContains == null || Utils.setItemContains(fields, includeRecordIfFieldContains))
                    {
                        currentRecord = rec;
                    }
                }
            }

            if (rec != null && includeRecordIfFieldMissing != null)
            {
                Set<String> fields = MarcUtils.getFieldList(rec, includeRecordIfFieldMissing);
                if ((fields.size() == 0 && includeRecordIfFieldDoesntContain == null) ||
                    (fields.size() != 0 && includeRecordIfFieldDoesntContain != null && !Utils.setItemContains(fields, includeRecordIfFieldDoesntContain)))
                {
                    currentRecord = rec;
                }
            }
            if (rec != null && includeRecordIfFieldPresent == null && includeRecordIfFieldMissing == null)
            {
                currentRecord = rec;
            }
        }
        return currentRecord ;
    }
}
