/*
 * Copyright (c) 2009 wemove digital solutions. All rights reserved.
 */

package de.ingrid.interfaces.csw2;

import java.io.StringReader;

import org.geotools.util.logging.Logging;
import org.w3c.dom.Document;

import de.ingrid.interfaces.csw2.data.TestFilter;
import de.ingrid.interfaces.csw2.data.UtilsSearch;
import de.ingrid.interfaces.csw2.exceptions.CSWOperationNotSupportedException;
import de.ingrid.interfaces.csw2.filter.FilterParser;
import de.ingrid.interfaces.csw2.tools.XMLTools;
import de.ingrid.utils.query.IngridQuery;

public class FilterTest extends OperationTestBase {

	/**
	 * Test ogc filter parsing 
	 * @throws Exception
	 */
	public void testGeoToolsFilterParsingSpecSampleFilter() throws Exception {

		final Logging logging = Logging.ALL;
		try {
		    logging.setLoggerFactory("org.geotools.util.logging.CommonsLoggerFactory");
		} catch (ClassNotFoundException commonsException) {
		    try {
		        logging.setLoggerFactory("org.geotools.util.logging.Log4JLoggerFactory");
		    } catch (ClassNotFoundException log4jException) {
		        // Nothing to do, we already tried our best.
		    }
		}
		
		
		FilterParser parser = new de.ingrid.interfaces.csw2.filter.impl.geotools.FilterParserImpl();
		Document filterDoc = XMLTools.parse(new StringReader(TestFilter.UNSUPPORTED_QUERIABLE));
		try {
			parser.parse(filterDoc);
			fail("Queriable 'Unsupported' should not be supported (missing exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}
		
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_1));
		IngridQuery query = parser.parse(filterDoc);
		assertTrue("The query is not null.", query != null);

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_2));
		query = parser.parse(filterDoc);
		assertTrue("The query is not null.", query != null);

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_3));
		query = parser.parse(filterDoc);
		assertTrue(query.toString().contains("x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:intersect"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_3_1));
		query = parser.parse(filterDoc);
		assertTrue(query.toString().contains("x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:intersect"));
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_3_2));
		query = parser.parse(filterDoc);
		assertTrue(query.toString().contains("x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:inside"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_3_3));
		query = parser.parse(filterDoc);
		assertTrue(query.toString().contains("x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:include"));
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_4));
		query = parser.parse(filterDoc);
		assertTrue("'"+query.toString()+"' dos not contain 'fields: x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:intersect'", query.toString().contains("fields: x1:13.0983 x2:35.5472 y1:31.5899 y2:42.8143 coord:intersect"));
		assertTrue("'"+query.toString()+"' dos not contain 'ranges: t011_obj_geo_scale.resolution_ground:[0 TO 30]'", query.toString().contains("ranges: t011_obj_geo_scale.resolution_ground:[0 TO 30]"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_5));
		try {
			query = parser.parse(filterDoc);
			fail("FeatureId should not be supported (missinhg exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_6));
		try {
			query = parser.parse(filterDoc);
			fail("FeatureId should not be supported (missinhg exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_7));
		try {
			query = parser.parse(filterDoc);
			fail("FeatureId should not be supported (missinhg exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_8));
		try {
			query = parser.parse(filterDoc);
			fail("FeatureId should not be supported (missinhg exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_9));
		query = parser.parse(filterDoc);
		assertTrue("'"+query.toString()+"' does not contain 'ranges: t011_obj_geo_scale.resolution_ground:[100 TO 200]'", query.toString().contains("ranges: t011_obj_geo_scale.resolution_ground:[100 TO 200]"));
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_10));
		query = parser.parse(filterDoc);
		assertTrue("'"+query.toString()+"' does not contain 'ranges: t01_object.mod_time:[20010115 TO 20010306]'", query.toString().contains("ranges: t01_object.mod_time:[20010115 TO 20010306]"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_11));
		query = parser.parse(filterDoc);
		assertTrue("'"+query.toString()+"' does not contain 'wildcardFields: title:JOHN*'", query.toString().contains("wildcardFields: title:JOHN*"));
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_11_1));
		query = parser.parse(filterDoc);
		assertTrue("'"+query.toString()+"' does not contain 'wildcardFields: title:JO#H?N*'", query.toString().contains("wildcardFields: title:JO#H?N*"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_11_2));
		try {
			query = parser.parse(filterDoc);
			fail("Leading wildcards should not be supported (missing exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_12));
		try {
			query = parser.parse(filterDoc);
			fail("Overlaps should not be supported (missing exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_13));
		query = parser.parse(filterDoc);
		String qString = UtilsSearch.queryToString(query);
		assertTrue("UtilsSearch.queryToString(query) does not contain '( ?t011_obj_geo_scale.resolution_ground:10 ?t011_obj_geo_scale.resolution_ground:20) title:John'", qString.contains("( ?t011_obj_geo_scale.resolution_ground:10 ?t011_obj_geo_scale.resolution_ground:20) title:John"));
		
		
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_14));
		query = parser.parse(filterDoc);
		qString = UtilsSearch.queryToString(query);
		assertTrue("UtilsSearch.queryToString(query) does not contain 'x1:13.0983 x2:35.5472 y1:31.5899 y2:45.8143 coord:inside t011_obj_geo_scale.resolution_ground:[400 TO 800]'", qString.contains("x1:13.0983 x2:35.5472 y1:31.5899 y2:45.8143 coord:inside t011_obj_geo_scale.resolution_ground:[400 TO 800]"));

		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_15));
		try {
			query = parser.parse(filterDoc);
			fail("Queriable 'Age' should not be supported (missing exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}
		
		parser = new de.ingrid.interfaces.csw2.filter.impl.geotools.FilterParserImpl();
		filterDoc = XMLTools.parse(new StringReader(TestFilter.OGC_FILTER_SAMPLE_15_1));
		try {
			parser.parse(filterDoc);
			fail("Queriable '@Attribute' should not be supported (missing exception).");
		} catch (Throwable t) {
			if (!(t.getCause() instanceof CSWOperationNotSupportedException)) {
				fail("Exception cause must be a CSWOperationNotSupportedException!");
			}
		}
		
	}
}
