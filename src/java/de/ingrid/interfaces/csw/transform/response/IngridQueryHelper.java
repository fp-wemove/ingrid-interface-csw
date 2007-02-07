/*
 * Copyright (c) 2007 wemove digital solutions. All rights reserved.
 */
package de.ingrid.interfaces.csw.transform.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ingrid.interfaces.csw.tools.CSWInterfaceConfig;
import de.ingrid.utils.IngridHit;
import de.ingrid.utils.IngridHitDetail;
import de.ingrid.utils.IngridHits;
import de.ingrid.utils.query.IngridQuery;
import de.ingrid.utils.queryparser.QueryStringParser;

/**
 * TODO Describe your created type (class, etc.) here.
 * 
 * @author joachim@wemove.com
 */
public class IngridQueryHelper {

    private final static Log log = LogFactory.getLog(IngridQueryHelper.class);

    public final static String HIT_KEY_ADDRESS_CLASS = "T02_address.typ";

    public static final String HIT_KEY_ADDRESS_FIRSTNAME = "T02_address.firstname";

    public static final String HIT_KEY_ADDRESS_LASTNAME = "T02_address.lastname";

    public static final String HIT_KEY_ADDRESS_TITLE = "T02_address.title";

    public static final String HIT_KEY_ADDRESS_ADDRESS = "T02_address.address";

    public static final String HIT_KEY_ADDRESS_ADDRID = "T02_address.adr_id";

    public static final String HIT_KEY_ADDRESS_INSTITUITION = "title";

    public static final String HIT_KEY_ADDRESS_JOB = "summary";

    public static final String HIT_KEY_ADDRESS_COMM_TYPE = "t021_communication.comm_type";

    public static final String HIT_KEY_ADDRESS_COMM_VALUE = "t021_communication.comm_value";

    public static final String HIT_KEY_ADDRESS_STREET = "street";

    public static final String HIT_KEY_ADDRESS_CITY = "city";

    public static final String HIT_KEY_ADDRESS_ZIP = "zip";

    public static final String HIT_KEY_ADDRESS_STATE_ID = "t02_address.state_id";

    public static final String HIT_KEY_OBJECT_TITLE = "title";

    public static final String HIT_KEY_OBJECT_SERVICE_TYPE = "T011_obj_serv.type";

    public static final String HIT_KEY_OBJECT_SERVICE_TYPE_VERSION = "T011_obj_serv_version.version";

    public static final String HIT_KEY_OBJECT_OBJECT_SPECIAL_REF = "T012_obj_obj.special_ref";

    public static final String HIT_KEY_OBJECT_OBJ_CLASS = "t01_object.obj_class";

    public static final String HIT_KEY_OBJECT_REL_ADR_TYPE = "t012_obj_adr.typ";

    public static final String HIT_KEY_OBJECT_REL_ADR_ID = "t012_obj_adr.adr_id";

    public static final String HIT_KEY_OBJECT_OBJ_ID = "t01_object.obj_id";

    public static final String HIT_KEY_OBJECT_GEO_TOPIC_CATEGORY = "t011_obj_geo_topic_cat.topic_category";

    public static final String HIT_KEY_OBJECT_DATASET_REFERENCE_DATE = "T0113_dataset_reference.reference_date";

    public static final String HIT_KEY_OBJECT_DATASET_REFERENCE_TYPE = "T0113_dataset_reference.type";

    public static final String HIT_KEY_OBJECT_MOD_TIME = "t01_object.mod_time";

    public static final String HIT_KEY_OBJECT_DESCR = "summary";

    public static final String HIT_KEY_OBJECT_AVAIL_ACCESS_NOTE = "T01_object.avail_access_note";

    public static final String HIT_KEY_OBJECT_DATA_LANGUAGE = "T01_object.data_language";

    public static final String HIT_KEY_OBJECT_METADATA_LANGUAGE = "T01_object.metadata_language";

    public static final String[] REQUESTED_STRING_BRIEF = { HIT_KEY_OBJECT_OBJ_ID, HIT_KEY_OBJECT_REL_ADR_ID,
            HIT_KEY_OBJECT_REL_ADR_TYPE, HIT_KEY_OBJECT_OBJ_CLASS, HIT_KEY_OBJECT_SERVICE_TYPE,
            HIT_KEY_OBJECT_SERVICE_TYPE_VERSION, HIT_KEY_OBJECT_OBJECT_SPECIAL_REF, HIT_KEY_OBJECT_GEO_TOPIC_CATEGORY, };

    public static final String[] REQUESTED_STRING_SUMMARY = { HIT_KEY_OBJECT_OBJ_ID, HIT_KEY_OBJECT_REL_ADR_ID,
            HIT_KEY_OBJECT_REL_ADR_TYPE, HIT_KEY_OBJECT_OBJ_CLASS, HIT_KEY_OBJECT_SERVICE_TYPE,
            HIT_KEY_OBJECT_SERVICE_TYPE_VERSION, HIT_KEY_OBJECT_OBJECT_SPECIAL_REF, HIT_KEY_OBJECT_GEO_TOPIC_CATEGORY,
            HIT_KEY_OBJECT_DATASET_REFERENCE_DATE, HIT_KEY_OBJECT_DATASET_REFERENCE_TYPE, HIT_KEY_OBJECT_MOD_TIME,
            HIT_KEY_OBJECT_DESCR, HIT_KEY_OBJECT_AVAIL_ACCESS_NOTE, HIT_KEY_OBJECT_DATA_LANGUAGE,
            HIT_KEY_OBJECT_METADATA_LANGUAGE };

    public static final String HIT_KEY_OBJECT_DATASET_CHARACTER_SET = "T01_object.dataset_character_set";

    public static final String HIT_KEY_OBJECT_OBJ_TO_ID = "t012_obj_obj.object_to_id";

    public static final String HIT_KEY_OBJECT_OBJ_FROM_ID = "t012_obj_obj.object_from_id";

    public static final String HIT_KEY_OBJECT_METADATA_STANDARD_NAME = "T01_object.metadata_standard_name";

    public static final String HIT_KEY_OBJECT_METADATA_STANDARD_VERSION = "T01_object.metadata_standard_version";

    public static final String HIT_KEY_OBJECT_DATASET_ALTERNATE_TITLE = "T01_object.dataset_alternate_name";

    public static final String HIT_KEY_OBJECT_INFO_NOTE = "T01_object.info_note";

    public static final String HIT_KEY_OBJECT_TIME_STATUS = "T01_object.time_status";

    public static final String HIT_KEY_OBJECT_DATASET_USAGE = "T01_object.dataset_usage";

    public static final String HIT_KEY_OBJECT_SEARCH_SEARCHTERM = "T04_search.searchterm";

    public static final String HIT_KEY_OBJECT_SEARCH_TYPE = "T04_search.type";

    public static final String HIT_KEY_OBJECT_TIME_DESCR = "T01_object.time_descr";

    public static final String HIT_KEY_OBJECT_TIME_PERIOD = "T01_object.time_period";

    public static final String HIT_KEY_OBJECT_TIME_INTERVAL = "T01_object.time_interval";

    public static final String HIT_KEY_OBJECT_SERV_OPERATION_NAME = "T011_obj_serv_operation.name";

    public static final String HIT_KEY_OBJECT_SERV_OP_PLATFORM = "T011_obj_serv_op_platform.platform";

    public static final String HIT_KEY_OBJECT_SERV_INVOVATION_NAME = "T011_obj_serv_operation.invocation_name";

    public static final String HIT_KEY_OBJECT_SERV_OP_CONNECT_POINT = "T011_obj_serv_op_connpoint.connect_point";

    public static final String HIT_KEY_OBJECT_SERV_OP_PARAM_NAME = "T011_obj_serv_op_para.name";

    public static final String HIT_KEY_OBJECT_SERV_OP_PARAM_DIRECTION = "T011_obj_serv_op_para.direction";

    public static final String HIT_KEY_OBJECT_SERV_OP_PARAM_DESCR = "T011_obj_serv_op_para.descr";

    public static final String HIT_KEY_OBJECT_SERV_OP_PARAM_OPTIONAL = "T011_obj_serv_op_para.optional";

    public static final String HIT_KEY_OBJECT_SERV_OP_PARAM_REPEATABILITY = "T011_obj_serv_op_para.repeatability";

    public static final String HIT_KEY_OBJECT_GEO_SPECIAL_BASE = "T011_obj_geo.special_base";

    public static final String HIT_KEY_OBJECT_GEO_DATA_BASE = "T011_obj_geo.data_base";

    public static final String HIT_KEY_OBJECT_GEO_METHOD = "T011_obj_geo.method";

    public static final String HIT_KEY_OBJECT_SERV_BASE = "T011_obj_serv.base";

    public static final String HIT_KEY_OBJECT_SERV_HISTORY = "T011_obj_serv.history";

    public static final String HIT_KEY_OBJECT_VECTOR_TOPOLOGY_LEVEL = "T011_obj_geo.vector_topology_level";

    public static final String HIT_KEY_OBJECT_VECTOR_GEOMETRIC_OBJECT_COUNT = "T011_obj_geo_vector.geometric_object_count";

    public static final String HIT_KEY_OBJECT_VECTOR_GEOMETRIC_OBJECT_TYPE = "T011_obj_geo_vector.geometric_object_type";

    public static final String HIT_KEY_OBJECT_GEO_REFERENCESYSTEM_ID = "T011_obj_geo.referencesystem_id";

    public static final String HIT_KEY_OBJECT_KEYC_INCL_W_DATASET = "T011_obj_geo.keyc_incl_w_dataset";

    public static final String HIT_KEY_OBJECT_KEYC_SUBJECT_CAT = "T011_obj_geo_keyc.subject_cat";

    public static final String HIT_KEY_OBJECT_KEYC_KEY_DATE = "T011_obj_geo_keyc.key_date";

    public static final String HIT_KEY_OBJECT_KEYC_EDITION = "T011_obj_geo_keyc.edition";

    public static final String HIT_KEY_OBJECT_AVAIL_FORMAT_NAME = "T0110_avail_format.name";

    public static final String HIT_KEY_OBJECT_AVAIL_FORMAT_VERSION = "T0110_avail_format.version";

    public static final String HIT_KEY_OBJECT_AVAIL_FORMAT_FILE_DECOMPRESSION_TECHNIQUE = "T0110_avail_format.file_decompression_technique";

    public static final String HIT_KEY_OBJECT_AVAIL_FORMAT_SPECIFIKATION = "T0110_avail_format.specification";

    public static final String HIT_KEY_OBJECT_FEES = "T01_object.fees";

    public static final String HIT_KEY_OBJECT_ORDER_INSTRUCTIONS = "T01_object.ordering_instructions";

    public static final String HIT_KEY_OBJECT_MEDIA_OPTION_MEDIUM_NAME = "T0112_media_option.medium_name";

    public static final String HIT_KEY_OBJECT_MEDIA_OPTION_TANSFER_SIZE = "T0112_media_option.transfer_size";

    public static final String HIT_KEY_OBJECT_MEDIA_OPTION_MEDIUM_NOTE = "T0112_media_option.medium_note";

    public static final String HIT_KEY_OBJECT_URL_REF_URL_LINK = "T017_url_ref.url_link";

    public static final String HIT_KEY_OBJECT_URL_REF_DESCR = "T017_url_ref.descr";

    public static final String HIT_KEY_OBJECT_URL_REF_CONTENT = "T017_url_ref.content";

    public static final String HIT_KEY_OBJECT_LOC_DESCR = "T01_object.loc_descr";

    public static final String HIT_KEY_OBJECT_VERTICAL_EXTENT_MINIMUM = "T01_object.vertical_extent_minimum";

    public static final String HIT_KEY_OBJECT_VERTICAL_EXTENT_MAXIMUM = "T01_object.vertical_extent_maximum";

    public static final String HIT_KEY_OBJECT_VERTICAL_EXTENT_UNIT = "T01_object.vertical_extent_unit";

    public static final String HIT_KEY_OBJECT_VERTICAL_EXTENT_VDATUM = "T01_object.vertical_extent_vdatum";

    public static final String HIT_KEY_OBJECT_TIME_FROM = "T01_object.time_from";

    public static final String HIT_KEY_OBJECT_TIME_TO = "T01_object.time_to";

    public static final String HIT_KEY_OBJECT_COORDINATES_BEZUG = "T019_coordinates.bezug";

    public static final String HIT_KEY_OBJECT_COORDINATES_GEO_X1 = "T019_coordinates.geo_x1";

    public static final String HIT_KEY_OBJECT_COORDINATES_GEO_X2 = "T019_coordinates.geo_x2";

    public static final String HIT_KEY_OBJECT_COORDINATES_GEO_Y1 = "T019_coordinates.geo_y1";

    public static final String HIT_KEY_OBJECT_COORDINATES_GEO_Y2 = "T019_coordinates.geo_y2";

    public static final String HIT_KEY_OBJECT_TOWNSHIP_NO = "T011_township.township_no";

    public static final String HIT_KEY_OBJECT_ST_BOX_X1 = "T01_st_bbox.x1";

    public static final String HIT_KEY_OBJECT_ST_BOX_X2 = "T01_st_bbox.x2";

    public static final String HIT_KEY_OBJECT_ST_BOX_Y1 = "T01_st_bbox.y1";

    public static final String HIT_KEY_OBJECT_ST_BOX_Y2 = "T01_st_bbox.y2";

    public static final String HIT_KEY_OBJECT_ST_BBOX_LOC_TOWN_NO = "t01_st_bbox.loc_town_no";
    
    public static final String[] REQUESTED_STRING_FULL = { HIT_KEY_OBJECT_OBJ_ID, HIT_KEY_OBJECT_REL_ADR_ID,
            HIT_KEY_OBJECT_REL_ADR_TYPE, HIT_KEY_OBJECT_OBJ_CLASS, HIT_KEY_OBJECT_SERVICE_TYPE,
            HIT_KEY_OBJECT_SERVICE_TYPE_VERSION, HIT_KEY_OBJECT_OBJECT_SPECIAL_REF, HIT_KEY_OBJECT_GEO_TOPIC_CATEGORY,
            HIT_KEY_OBJECT_DATASET_REFERENCE_DATE, HIT_KEY_OBJECT_DATASET_REFERENCE_TYPE, HIT_KEY_OBJECT_MOD_TIME,
            HIT_KEY_OBJECT_DESCR, HIT_KEY_OBJECT_AVAIL_ACCESS_NOTE, HIT_KEY_OBJECT_DATA_LANGUAGE,
            HIT_KEY_OBJECT_METADATA_LANGUAGE, HIT_KEY_OBJECT_DATASET_CHARACTER_SET, HIT_KEY_OBJECT_OBJ_TO_ID,
            HIT_KEY_OBJECT_OBJ_FROM_ID, HIT_KEY_OBJECT_METADATA_STANDARD_NAME,
            HIT_KEY_OBJECT_METADATA_STANDARD_VERSION, HIT_KEY_OBJECT_DATASET_ALTERNATE_TITLE, HIT_KEY_OBJECT_INFO_NOTE,
            HIT_KEY_OBJECT_TIME_STATUS, HIT_KEY_OBJECT_DATASET_USAGE, HIT_KEY_OBJECT_SEARCH_SEARCHTERM,
            HIT_KEY_OBJECT_SEARCH_TYPE, HIT_KEY_OBJECT_TIME_DESCR, HIT_KEY_OBJECT_TIME_PERIOD,
            HIT_KEY_OBJECT_TIME_INTERVAL, HIT_KEY_OBJECT_SERV_OPERATION_NAME, HIT_KEY_OBJECT_SERV_OP_PLATFORM,
            HIT_KEY_OBJECT_SERV_INVOVATION_NAME, HIT_KEY_OBJECT_SERV_OP_CONNECT_POINT,
            HIT_KEY_OBJECT_SERV_OP_PARAM_NAME, HIT_KEY_OBJECT_SERV_OP_PARAM_DIRECTION,
            HIT_KEY_OBJECT_SERV_OP_PARAM_DESCR, HIT_KEY_OBJECT_SERV_OP_PARAM_OPTIONAL,
            HIT_KEY_OBJECT_SERV_OP_PARAM_REPEATABILITY, HIT_KEY_OBJECT_GEO_SPECIAL_BASE, HIT_KEY_OBJECT_GEO_DATA_BASE,
            HIT_KEY_OBJECT_GEO_METHOD, HIT_KEY_OBJECT_SERV_BASE, HIT_KEY_OBJECT_SERV_HISTORY,
            HIT_KEY_OBJECT_VECTOR_TOPOLOGY_LEVEL, HIT_KEY_OBJECT_VECTOR_GEOMETRIC_OBJECT_COUNT,
            HIT_KEY_OBJECT_VECTOR_GEOMETRIC_OBJECT_TYPE, HIT_KEY_OBJECT_GEO_REFERENCESYSTEM_ID,
            HIT_KEY_OBJECT_KEYC_INCL_W_DATASET, HIT_KEY_OBJECT_KEYC_SUBJECT_CAT, HIT_KEY_OBJECT_KEYC_KEY_DATE,
            HIT_KEY_OBJECT_KEYC_EDITION, HIT_KEY_OBJECT_AVAIL_FORMAT_NAME, HIT_KEY_OBJECT_AVAIL_FORMAT_VERSION,
            HIT_KEY_OBJECT_AVAIL_FORMAT_FILE_DECOMPRESSION_TECHNIQUE, HIT_KEY_OBJECT_AVAIL_FORMAT_SPECIFIKATION,
            HIT_KEY_OBJECT_FEES, HIT_KEY_OBJECT_ORDER_INSTRUCTIONS, HIT_KEY_OBJECT_MEDIA_OPTION_MEDIUM_NAME,
            HIT_KEY_OBJECT_MEDIA_OPTION_TANSFER_SIZE, HIT_KEY_OBJECT_MEDIA_OPTION_MEDIUM_NOTE,
            HIT_KEY_OBJECT_URL_REF_URL_LINK, HIT_KEY_OBJECT_URL_REF_DESCR, HIT_KEY_OBJECT_URL_REF_CONTENT,
            HIT_KEY_OBJECT_LOC_DESCR, HIT_KEY_OBJECT_VERTICAL_EXTENT_MINIMUM, HIT_KEY_OBJECT_VERTICAL_EXTENT_MAXIMUM,
            HIT_KEY_OBJECT_VERTICAL_EXTENT_UNIT, HIT_KEY_OBJECT_VERTICAL_EXTENT_VDATUM, HIT_KEY_OBJECT_TIME_FROM,
            HIT_KEY_OBJECT_TIME_TO, HIT_KEY_OBJECT_COORDINATES_BEZUG, HIT_KEY_OBJECT_COORDINATES_GEO_X1,
            HIT_KEY_OBJECT_COORDINATES_GEO_X2, HIT_KEY_OBJECT_COORDINATES_GEO_Y1, HIT_KEY_OBJECT_COORDINATES_GEO_Y2,
            HIT_KEY_OBJECT_TOWNSHIP_NO, HIT_KEY_OBJECT_ST_BOX_X1, HIT_KEY_OBJECT_ST_BOX_X2, HIT_KEY_OBJECT_ST_BOX_Y1,
            HIT_KEY_OBJECT_ST_BOX_Y2, HIT_KEY_OBJECT_ST_BBOX_LOC_TOWN_NO, "" };


    /*
     * String[] requestedFields = new String[15]; requestedFields[0] =
     * IngridQueryHelper.HIT_KEY_OBJECT_OBJ_ID; requestedFields[1] =
     * IngridQueryHelper.HIT_KEY_OBJECT_REL_ADR_ID; requestedFields[2] =
     * IngridQueryHelper.HIT_KEY_OBJECT_REL_ADR_TYPE; requestedFields[3] =
     * IngridQueryHelper.HIT_KEY_OBJECT_OBJ_CLASS; requestedFields[4] =
     * IngridQueryHelper.HIT_KEY_OBJECT_SERVICE_TYPE; requestedFields[5] =
     * IngridQueryHelper.HIT_KEY_OBJECT_SERVICE_TYPE_VERSION; requestedFields[6] =
     * IngridQueryHelper.HIT_KEY_OBJECT_OBJECT_SPECIAL_REF; requestedFields[7] =
     * IngridQueryHelper.HIT_KEY_OBJECT_GEO_TOPIC_CATEGORY; requestedFields[8] =
     * IngridQueryHelper.HIT_KEY_OBJECT_DATASET_REFERENCE_DATE;
     * requestedFields[9] =
     * IngridQueryHelper.HIT_KEY_OBJECT_DATASET_REFERENCE_TYPE;
     * requestedFields[10] = IngridQueryHelper.HIT_KEY_OBJECT_MOD_TIME;
     * requestedFields[11] = IngridQueryHelper.HIT_KEY_OBJECT_DESCR;
     * requestedFields[12] = IngridQueryHelper.HIT_KEY_OBJECT_AVAIL_ACCESS_NOTE;
     * requestedFields[13] = IngridQueryHelper.HIT_KEY_OBJECT_DATA_LANGUAGE;
     * requestedFields[14] = IngridQueryHelper.HIT_KEY_OBJECT_METADATA_LANGUAGE;
     */

    public static IngridHit getCompleteAddress(String addrId, String iPlugId) throws Exception {
        IngridHit address = getAddressHit(addrId, iPlugId);
        IngridHitDetail addressDetail = (IngridHitDetail) address.get("detail");
        String addrClass = IngridQueryHelper.getDetailValue(addressDetail, IngridQueryHelper.HIT_KEY_ADDRESS_CLASS);
        if (addrClass.equals("2") || addrClass.equals("1")) {
            String addressInstitution = getDetailValue(addressDetail, HIT_KEY_ADDRESS_INSTITUITION);
            String currentAddressId = getDetailValue(addressDetail, HIT_KEY_ADDRESS_ADDRID);
            String newAddressId = null;
            boolean skipSearch = false;
            while (!skipSearch) {
                IngridQuery query = QueryStringParser.parse("T022_adr_adr.adr_to_id:".concat(currentAddressId).concat(
                        " datatype:address ranking:score"));
                IngridHits results = CSWInterfaceConfig.getInstance().getIBus().search(query, 10, 1, 10, 3000);
                if (results.getHits().length > 0) {
                    IngridHitDetail details[] = CSWInterfaceConfig.getInstance().getIBus()
                            .getDetails(
                                    results.getHits(),
                                    query,
                                    new String[] { HIT_KEY_ADDRESS_ADDRID, HIT_KEY_ADDRESS_CLASS,
                                            HIT_KEY_ADDRESS_INSTITUITION });
                    // find first parent of the address in the result set
                    for (int j = 0; j < details.length; j++) {
                        IngridHitDetail addrDetail = (IngridHitDetail) details[j];
                        addrClass = getDetailValue(addrDetail, HIT_KEY_ADDRESS_CLASS);
                        newAddressId = getDetailValue(addrDetail, HIT_KEY_ADDRESS_ADDRID);
                        if ((addrClass.equals("0") || addrClass.equals("1")) && !currentAddressId.equals(newAddressId)) {
                            if (addressInstitution.length() > 0) {
                                addressInstitution = getDetailValue(addrDetail, HIT_KEY_ADDRESS_INSTITUITION).concat(
                                        ", ").concat(addressInstitution);
                            } else {
                                addressInstitution = getDetailValue(addrDetail, HIT_KEY_ADDRESS_INSTITUITION);
                            }
                            break;
                        }
                    }
                    // check for search skip
                    if (!addrClass.equals("1") || currentAddressId.equals(newAddressId)) {
                        skipSearch = true;
                    } else {
                        currentAddressId = newAddressId;
                    }
                } else {
                    skipSearch = true;
                }
            }
            addressDetail.put(HIT_KEY_ADDRESS_INSTITUITION, addressInstitution);
        }
        return address;
    }

    public static IngridHit getAddressHit(String addrId, String iPlugId) {
        String[] requestedMetadata = new String[14];
        requestedMetadata[0] = HIT_KEY_ADDRESS_CLASS;
        requestedMetadata[1] = HIT_KEY_ADDRESS_FIRSTNAME;
        requestedMetadata[2] = HIT_KEY_ADDRESS_LASTNAME;
        requestedMetadata[3] = HIT_KEY_ADDRESS_TITLE;
        requestedMetadata[4] = HIT_KEY_ADDRESS_ADDRESS;
        requestedMetadata[5] = HIT_KEY_ADDRESS_ADDRID;
        requestedMetadata[6] = HIT_KEY_ADDRESS_INSTITUITION;
        requestedMetadata[7] = HIT_KEY_ADDRESS_JOB;
        requestedMetadata[8] = HIT_KEY_ADDRESS_COMM_TYPE;
        requestedMetadata[9] = HIT_KEY_ADDRESS_COMM_VALUE;
        requestedMetadata[10] = HIT_KEY_ADDRESS_STREET;
        requestedMetadata[11] = HIT_KEY_ADDRESS_CITY;
        requestedMetadata[12] = HIT_KEY_ADDRESS_ZIP;
        requestedMetadata[13] = HIT_KEY_ADDRESS_STATE_ID;

        ArrayList result = getHits("T02_address.adr_id:".concat(addrId).concat(
                " iplugs:\"".concat(getAddressPlugIdFromPlugId(iPlugId)).concat("\"")), requestedMetadata, null);
        if (result.size() > 0) {
            return (IngridHit) result.get(0);
        } else {
            return null;
        }
    }

    private static ArrayList getHits(String queryStr, String[] requestedMetaData, HashMap filter) {
        ArrayList result = new ArrayList();
        try {
            IngridQuery query = QueryStringParser.parse(queryStr.concat(" ranking:any datatype:any"));
            IngridHits hits;
            // request hits in chunks of 20 results per page
            int page = 0;
            do {
                page++;
                hits = CSWInterfaceConfig.getInstance().getIBus().search(query, 20, page, 20, 3000);
                IngridHitDetail details[] = CSWInterfaceConfig.getInstance().getIBus().getDetails(hits.getHits(),
                        query, requestedMetaData);
                for (int j = 0; j < details.length; j++) {
                    IngridHitDetail detail = (IngridHitDetail) details[j];
                    boolean include = true;
                    if (filter != null && filter.size() > 0) {
                        Iterator it = filter.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            String recordKey = (String) entry.getKey();
                            String value = (String) entry.getValue();
                            if (value.equals(getDetailValue(detail, recordKey))) {
                                include = false;
                                break;
                            }
                        }
                    }
                    if (include) {
                        // flatten alle detail fields
                        /*
                         * for (int i = 0; i < requestedMetaData.length; i++) {
                         * detail.put(requestedMetaData[i],
                         * getDetailValue(detail, requestedMetaData[i])); }
                         */
                        hits.getHits()[j].put("detail", detail);
                        result.add(hits.getHits()[j]);
                    }
                }
            } while (hits.getHits().length == 20);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("Problems getting hits from iBus!", e);
            } else {
                log.info("Problems getting hits from iBus!");
            }
        }
        return result;
    }

    /**
     * Private method handling all Detail Fetching (Mapping)
     * 
     * @param detail
     * @param key
     * @param resources
     * @param raw
     * @return
     */
    static String getDetailValue(IngridHit detail, String key) {

        Object obj = detail.get(key);
        if (obj == null) {
            return "";
        }

        StringBuffer values = new StringBuffer();
        if (obj instanceof String[]) {
            String[] valueArray = (String[]) obj;
            for (int i = 0; i < valueArray.length; i++) {
                if (i != 0) {
                    values.append(", ");
                }
                values.append(valueArray[i]);
            }
        } else if (obj instanceof ArrayList) {
            ArrayList valueList = (ArrayList) obj;
            for (int i = 0; i < valueList.size(); i++) {
                if (i != 0) {
                    values.append(", ");
                }
                values.append(valueList.get(i).toString());
            }
        } else {
            values.append(obj.toString());
        }

        return values.toString();
    }

    /**
     * Returns the address plug id of the corresponding plug id, by adding the
     * postfix "_addr" to the plug id. Only plug id's that contain the string
     * "udk-db" and do not contain the postfix "_addr" will be changed.
     * 
     * @param plugId
     * @return
     */
    private static String getAddressPlugIdFromPlugId(String plugId) {
        if (plugId != null && plugId.indexOf("udk-db") > -1 && !plugId.endsWith("_addr")) {
            return plugId.concat("_addr");
        } else {
            return plugId;
        }
    }

    public static String getCompletePersonName(IngridHit addressHit) {
        IngridHitDetail address = (IngridHitDetail) addressHit.get("detail");

        String personName = "";
        if (address.get(IngridQueryHelper.HIT_KEY_ADDRESS_ADDRESS) != null) {
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_ADDRESS));
            personName = personName.concat(" ");
        }
        if (address.get(IngridQueryHelper.HIT_KEY_ADDRESS_TITLE) != null) {
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_TITLE));
            personName = personName.concat(" ");
        }
        if (address.get(IngridQueryHelper.HIT_KEY_ADDRESS_FIRSTNAME) != null
                && address.get(IngridQueryHelper.HIT_KEY_ADDRESS_LASTNAME) != null) {
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_FIRSTNAME));
            personName = personName.concat(" ");
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_LASTNAME));
        } else if (address.get(IngridQueryHelper.HIT_KEY_ADDRESS_FIRSTNAME) == null
                && address.get(IngridQueryHelper.HIT_KEY_ADDRESS_LASTNAME) != null) {
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_LASTNAME));
        } else if (address.get(IngridQueryHelper.HIT_KEY_ADDRESS_FIRSTNAME) != null
                && address.get(IngridQueryHelper.HIT_KEY_ADDRESS_LASTNAME) == null) {
            personName = personName.concat(getDetailValue(address, IngridQueryHelper.HIT_KEY_ADDRESS_FIRSTNAME));
        }

        return personName;
    }

    public static String getDetailValueAsString(IngridHit hit, String key) {
        IngridHitDetail detail = (IngridHitDetail) hit.get("detail");
        return getDetailValue(detail, key);
    }

    public static String[] getDetailValueAsArray(IngridHit hit, String key) {
        IngridHitDetail detail = (IngridHitDetail) hit.get("detail");

        String[] valueArray = new String[] {};
        Object obj = detail.get(key);
        if (obj == null) {
            return valueArray;
        }

        if (obj instanceof String[]) {
            valueArray = (String[]) obj;
        } else if (obj instanceof ArrayList) {
            ArrayList valueList = (ArrayList) obj;
            valueArray = (String[]) valueList.toArray(valueArray);
        } else {
            valueArray = new String[1];
            valueArray[0] = obj.toString();
        }

        return valueArray;
    }

    public static HashMap getCommunications(IngridHit addressHit) {
        IngridHitDetail address = (IngridHitDetail) addressHit.get("detail");

        ArrayList phoneNumber = new ArrayList();
        ArrayList faxNumber = new ArrayList();
        ArrayList email = new ArrayList();
        ArrayList url = new ArrayList();

        String[] commTypes = (String[]) address.get(IngridQueryHelper.HIT_KEY_ADDRESS_COMM_TYPE);
        String[] commValues = (String[]) address.get(IngridQueryHelper.HIT_KEY_ADDRESS_COMM_VALUE);
        for (int j = 0; j < commTypes.length; j++) {
            if (commTypes[j].equalsIgnoreCase("Telefon")) {
                phoneNumber.add(commValues[j]);
            } else if (commTypes[j].equalsIgnoreCase("Fax")) {
                faxNumber.add(commValues[j]);
            } else if (commTypes[j].equalsIgnoreCase("E-Mail") || commTypes[j].equalsIgnoreCase("EMail")) {
                email.add(commValues[j]);
            } else if (commTypes[j].equalsIgnoreCase("url")) {
                url.add(commValues[j]);
            }
        }
        HashMap map = new HashMap();
        map.put("phone", phoneNumber);
        map.put("fax", faxNumber);
        map.put("email", email);
        map.put("url", url);
        return map;
    }

    public static String getParentIdentifier(IngridHit hit) {
        String[] toIds = getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_OBJ_TO_ID);
        String[] fromIds = getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_OBJ_FROM_ID);
        String objId = IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_OBJ_ID);
        for (int i = 0; i < toIds.length; i++) {
            if (objId.equals(toIds[i])) {
                return fromIds[i];
            }
        }
        return "";
    }

}
