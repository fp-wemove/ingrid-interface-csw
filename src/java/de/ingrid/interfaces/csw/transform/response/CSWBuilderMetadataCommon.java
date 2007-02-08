/*
 * Copyright (c) 2007 wemove digital solutions. All rights reserved.
 */
package de.ingrid.interfaces.csw.transform.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import de.ingrid.utils.IngridHit;
import de.ingrid.utils.udk.UtilsDate;
import de.ingrid.utils.udk.UtilsUDKCodeLists;

/**
 * TODO Describe your created type (class, etc.) here.
 * 
 * @author joachim@wemove.com
 */
public abstract class CSWBuilderMetadataCommon extends CSWBuilderMetaData {

    private static Log log = LogFactory.getLog(CSWBuilderMetadataCommon.class);

    /**
     * Add the hierarchy level construct to a given element
     * 
     * @param parent
     * @param level
     * @return The parent element
     */
    protected Element addHierarchyLevel(Element parent, String level) {
        // TODO: if dataset, get scope from T011_obj_geo.hierarchy_level and transform code with codelist 525
        parent.addElement("smXML:MD_ScopeCode").addAttribute("codeList",
                "http://www.tc211.org/ISO19139/resources/codeList.xml?MD_ScopeCode").addAttribute("codeListValue",
                level);
        return parent;
    }

    protected void addContact(Element parent, IngridHit hit) throws Exception {
        addContact(parent, hit, null);
    }

    protected void addContact(Element parent, IngridHit hit, String ns) throws Exception {
        addSMXMLContact(parent.addElement(getNSElementName(ns, "contact")), hit);
    }

    /**
     * Adds a CSW file identifier to a given element.
     * 
     * @param parent
     *            The Element to add the identifier to.
     * @param hit
     *            The IngridHit.
     * @param doc
     *            The Document.
     * @return The parent element.
     */
    protected void addFileIdentifier(Element parent, String id, String ns) {
        this.addSMXMLCharacterString(parent.addElement(getNSElementName(ns, "fileIdentifier")), id);
    }

    protected void addFileIdentifier(Element parent, String id) {
        addFileIdentifier(parent, id, null);
    }
    
    

    protected void addLanguage(Element metaData, IngridHit hit) {
        addLanguage(metaData, hit, null);
    }

    protected void addLanguage(Element metaData, IngridHit hit, String ns) {
        String metadataLang =  IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_METADATA_LANGUAGE);
        if (metadataLang.equals("121")) {
            metadataLang = "de";
        } else {
            metadataLang = "en";
        }
        this.addSMXMLCharacterString(metaData.addElement(getNSElementName(ns, "language")), metadataLang);
    }
    
    
    protected void addDateStamp(Element metaData, IngridHit hit) {
        addDateStamp(metaData, hit, null);
    }

    protected void addDateStamp(Element metaData, IngridHit hit, String ns) {
        String creationDate = IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_MOD_TIME);
        Date d = UtilsDate.parseDateString(creationDate);
        if (d != null) {
            creationDate = DATE_TIME_FORMAT.format(d);
            metaData.addElement(getNSElementName(ns, "dateStamp")).addElement("smXML:Date").addText(creationDate);
        }
    }
    
    protected void addCitationReferenceDates(Element parent, IngridHit hit) {
        // add dates (creation, revision etc.)
        String[] referenceDate = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_DATASET_REFERENCE_DATE);
        String[] referenceDateTypes = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_DATASET_REFERENCE_TYPE);
        if (referenceDate != null) {
            for (int i = 0; i < referenceDate.length; i++) {
                String creationDate = referenceDate[i];
                Date d = UtilsDate.parseDateString(creationDate);
                if (d != null) {
                    creationDate = DATE_TIME_FORMAT.format(d);
                    Element ciDate = parent.addElement("date").addElement("smXML:CI_Date");
                    ciDate.addElement("smXML:Date").addText(creationDate);
                    String codeListValue;
                    if (referenceDateTypes[i].equals("1")) {
                        codeListValue = "creation";
                    } else if (referenceDateTypes[i].equals("2")) {
                        codeListValue = "publication";
                    } else if (referenceDateTypes[i].equals("3")) {
                        codeListValue = "revision";
                    } else {
                        log.warn("Invalid UDK dataset reference date type: " + referenceDateTypes[i] + ".");
                        codeListValue = "unspecified";
                    }
                    ciDate.addElement("smXML:CI_DateTypeCode").addAttribute("codeList",
                            "http://www.tc211.org/ISO19139/resources/codeList.xml?CI_DateTypeCode").addAttribute(
                            "codeListValue", codeListValue);
                    
                }
            }
        }
    }
    
    protected void addOperationMetadata(Element parent, IngridHit hit) {
        // operationMetadata
        Element svOperationMetadata = parent.addElement("iso19119:operationMetadata").addElement("iso19119:SV_OperationMetadata");
        // iso19119:operationMetadata -> iso19119:SV_OperationMetadata -> iso19119:operationName -> String
        this.addSMXMLCharacterString(svOperationMetadata.addElement("iso19119:operationName"), IngridQueryHelper
                .getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OPERATION_NAME));
        // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:DCP -> iso19119:SV_DCPList/@codeListValue
        String[] platforms = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PLATFORM);
        for (int i=0; i< platforms.length; i++) {
            svOperationMetadata.addElement("iso19119:DCP").addElement("iso19119:SV_DCPList")
                .addAttribute("codeList", "http://opengis.org/codelistRegistry?CSW_DCPCodeType")
                .addAttribute("codeList", platforms[i]);
        }
        // iso19119:operationMetadata -> iso19119:SV_OperationMetadata -> iso19119:invocationName -> String
        this.addSMXMLCharacterString(svOperationMetadata.addElement("iso19119:invocationName"), IngridQueryHelper
                .getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_INVOVATION_NAME));
        // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:connectPoint -> smXML:CI_OnlineResource
        String[] connectPoints = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_CONNECT_POINT);
        for (int i=0; i< connectPoints.length; i++) {
            svOperationMetadata.addElement("iso19119:connectPoint")
                .addElement("smXML:CI_OnlineResource").addElement("smXML:linkage")
                    .addElement("smXML:URL").addText(connectPoints[i]);
        }
        // iso19119:paramaters
        String[] parameterNames = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PARAM_NAME);
        String[] parameterDirections = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PARAM_DIRECTION);
        String[] parameterDescriptions = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PARAM_DESCR);
        String[] parameterOptionality = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PARAM_OPTIONAL);
        String[] parameterRepeatability = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_SERV_OP_PARAM_REPEATABILITY);
        for (int i=0; i<parameterNames.length; i++) {
            Element parameters = svOperationMetadata.addElement("iso19119:parameters");
            // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:parameters -> iso19119:SV_Parameter -> iso19119:name -> smXML:MemberName -> smXML:aName -> smXML:CharacterString
            this.addSMXMLCharacterString(parameters.addElement("SV_Parameter").addElement("iso19119:name").addElement("smXML:MemberName").addElement("smXML:aName"),parameterNames[i]); 
            // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:parameters -> iso19119:direction -> iso19119:SV_ParameterDirection -> (in|out|in/out)
            parameters.addElement("iso19119:direction").addElement("iso19119:SV_ParameterDirection").addText(parameterDirections[i]);
            // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:parameters -> iso19119:description -> smXML:CharacterString
            this.addSMXMLCharacterString(parameters.addElement("iso19119:description"), parameterDescriptions[i]);
            // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:parameters -> iso19119:optionality -> smXML:CharacterString
            this.addSMXMLCharacterString(parameters.addElement("iso19119:optionality"), parameterOptionality[i]);
            // iso19119:operationMetadata -> iso19119:SV_OperationMetadata ->(1:n) iso19119:parameters -> iso19119:repeatability -> smXML:Boolean
            this.addSMXMLBoolean(parameters.addElement("iso19119:repeatability"), (parameterRepeatability[i] !=null && parameterRepeatability[i].equals("1")));
        }
        
    }
    
    protected void addExtent (Element parent, IngridHit hit, String ns) {
        // extend
        Element exExent = parent.addElement(this.getNSElementName(ns, "extent")).addElement("smXML:EX_Extent");
        // T01_object.loc_descr MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/description
        super.addSMXMLCharacterString(exExent.addElement("smXML:description"), IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_LOC_DESCR));
        
        Element exVerticalExtent = exExent.addElement("smXML:verticalElement").addElement("EX_VerticalExtent");
        // T01_object.vertical_extent_minimum MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/verticalElement/EX_VerticalExtent.minimumValue
        super.addSMXMLReal(exVerticalExtent.addElement("smXML:minimumValue"), IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_VERTICAL_EXTENT_MINIMUM));
        // T01_object.vertical_extent_maximum MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/verticalElement/EX_VerticalExtent.maximumValue
        super.addSMXMLReal(exVerticalExtent.addElement("smXML:maximumValue"), IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_VERTICAL_EXTENT_MAXIMUM));
        
        // T01_object.vertical_extent_unit = Wert [Domain-ID Codelist 102] MD_Metadata/full:identificationInfo/MD_DataIdentification/extent/EX_Extent/verticalElement/EX_VerticalExtent/unitOfMeasure/UomLength/uomName/CharacterString
        try {
            Long code = Long.valueOf(IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_VERTICAL_EXTENT_UNIT));
            String codeVal = UtilsUDKCodeLists.getCodeListEntryName(new Long(102), code, new Long(94));
            if (codeVal.length() > 0) {
                super.addSMXMLCharacterString(exVerticalExtent.addElement("smXML:unitOfMeasure").addElement("smXML:unitOfMeasure").addElement("smXML:uomName"), codeVal);
            }
        } catch (NumberFormatException e) {}

        // T01_object.vertical_extent_vdatum = Wert [Domain-Id Codelist 101] MD_Metadata/smXML:identificationInfo/iso19119:CSW_ServiceIdentification/iso19119:extent/smXML:EX_Extent/verticalElement/EX_VerticalExtent/verticalDatum/smXML:RS_Identifier/code/smXML:CharacterString
        try {
            Long code = Long.valueOf(IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_VERTICAL_EXTENT_VDATUM));
            String codeVal = UtilsUDKCodeLists.getCodeListEntryName(new Long(101), code, new Long(94));
            if (codeVal.length() > 0) {
                super.addSMXMLCharacterString(exVerticalExtent.addElement("smXML:verticalDatum").addElement("smXML:RS_Identifier").addElement("smXML:code"), codeVal);
            }
        } catch (NumberFormatException e) {}
        
        
        Element timePeriod = exExent.addElement("smXML:TM_Primitive").addElement("gml:relatedTime").addElement("gml:TimePeriod");
        // T01_object.time_from MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/temporalElement/EX_TemporalExtent/extent/TM_Primitive/gml:relatedTime/gml:TimePeriod/gml:beginPosition/gml:TimeInstant/gml:timePosition
        String myDate = IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_TIME_FROM);
        Date d = UtilsDate.parseDateString(myDate);
        if (d != null) {
            myDate = DATE_TIME_FORMAT.format(d);
            timePeriod.addElement("gml:beginPosition").addElement("gml:TimeInstant").addElement("gml:timePosition")
                .addText(myDate);
        }
        // T01_object.time_to MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/temporalElement/EX_TemporalExtent/extent/TM_Primitive/gml:relatedTime/gml:TimePeriod/gml:endPosition/gml:TimeInstant/gml:timePosition
        myDate = IngridQueryHelper.getDetailValueAsString(hit, IngridQueryHelper.HIT_KEY_OBJECT_TIME_TO);
        d = UtilsDate.parseDateString(myDate);
        if (d != null) {
            myDate = DATE_TIME_FORMAT.format(d);
            timePeriod.addElement("gml:endPosition").addElement("gml:TimeInstant").addElement("gml:timePosition")
                .addText(myDate);
        }

        String[] coordinatesBezug = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_COORDINATES_BEZUG);
        String[] coordinatesGeoX1 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_COORDINATES_GEO_X1);
        String[] coordinatesGeoX2 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_COORDINATES_GEO_X2);
        String[] coordinatesGeoY1 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_COORDINATES_GEO_Y1);
        String[] coordinatesGeoY2 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_COORDINATES_GEO_Y2);
        for (int i=0; i< coordinatesBezug.length; i++) {
            Element geographicElement = exExent.addElement("smXML:geographicElement");
            // T019_coordinates.bezug MD_Metadata/smXML:identificationInfo/iso19119:CSW_ServiceIdentification/iso19119:extent/smXML:EX_Extent/smXML:geographicElement/smXML:EX_GeographicDescription/smXML:geographicIdentifier/smXML:RS_Identifier/code/smXML:CharacterString
            super.addSMXMLCharacterString(geographicElement.addElement("smXML:EX_GeographicDescription").addElement("smXML:geographicIdentifier").addElement("smXML:RS_Identifier").addElement("smXML:code"), coordinatesBezug[i]);
            Element exGeographicBoundingBox = geographicElement.addElement("smXML:EX_GeographicBoundingBox");
            // T019_coordinates.geo_x1 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox/westBoundLongitude/smXML:approximateLongitude/smXML:Decimal
            super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:westBoundLongitude").addElement("smXML:approximateLongitude"), coordinatesGeoX1[i]);
            // T019_coordinates.geo_x2 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox/eastBoundLongitude/smXML:approximateLongitude/smXML:Decimal
            super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:eastBoundLongitude").addElement("smXML:approximateLongitude"), coordinatesGeoX2[i]);
            // T019_coordinates.geo_y2 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox/southBoundLongitude/smXML:approximateLongitude/smXML:Decimal
            super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:southBoundLongitude").addElement("smXML:approximateLongitude"), coordinatesGeoY1[i]);
            // T019_coordinates.geo_y1 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox/northBoundLongitude/smXML:approximateLongitude/smXML:Decimal
            super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:northBoundLongitude").addElement("smXML:approximateLongitude"), coordinatesGeoY2[i]);
        }

        String[] townshipNo = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_TOWNSHIP_NO);
        String[] stBBoxLocTownNo = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_ST_BBOX_LOC_TOWN_NO);
        String[] stBoxX1 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_ST_BOX_X1);
        String[] stBoxX2 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_ST_BOX_X2);
        String[] stBoxY1 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_ST_BOX_Y1);
        String[] stBoxY2 = IngridQueryHelper.getDetailValueAsArray(hit, IngridQueryHelper.HIT_KEY_OBJECT_ST_BOX_Y2);

        for (int i=0; i< townshipNo.length; i++) {
            Element geographicElement = exExent.addElement("smXML:geographicElement");
            // T011_township.township_no MD_Metadata/smXML:identificationInfo/iso19119:CSW_ServiceIdentification/iso19119:extent/smXML:EX_Extent/smXML:geographicElement/smXML:EX_GeographicDescription/smXML:geographicIdentifier/smXML:RS_Identifier/code/smXML:CharacterString
            super.addSMXMLCharacterString(geographicElement.addElement("smXML:EX_GeographicDescription").addElement("smXML:geographicIdentifier").addElement("smXML:RS_Identifier").addElement("smXML:code"), townshipNo[i]);
            for (int j=0; j< stBBoxLocTownNo.length; j++) {
                if (stBBoxLocTownNo[j].equals(townshipNo[i])) {
                    Element exGeographicBoundingBox = geographicElement.addElement("smXML:EX_GeographicBoundingBox");
                    // T01_st_bbox.x1 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox.westBoundLongitude/smXML:approximateLongitude/smXML:Decimal
                    super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:westBoundLongitude").addElement("smXML:approximateLongitude"), stBoxX1[i]);
                    // T01_st_bbox.x2 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox.eastBoundLongitude/smXML:approximateLongitude/smXML:Decimal
                    super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:eastBoundLongitude").addElement("smXML:approximateLongitude"), stBoxX2[i]);
                    // T01_st_bbox.y2 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox.southBoundLongitude/smXML:approximateLongitude/smXML:Decimal
                    super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:southBoundLongitude").addElement("smXML:approximateLongitude"), stBoxY1[i]);
                    // T01_st_bbox.y1 MD_Metadata/identificationInfo/MD_DataIdentification/extent/EX_Extent/geographicElement/EX_GeographicBoundingBox.northBoundLongitude/smXML:approximateLongitude/smXML:Decimal
                    super.addSMXMLDecimal(exGeographicBoundingBox.addElement("smXML:northBoundLongitude").addElement("smXML:approximateLongitude"), stBoxY2[i]);
                    break;
                }
            }
        }        
    }
    
    
    private void addSMXMLContact(Element parent, IngridHit hit)
            throws Exception {
        
        String[] addressIds = IngridQueryHelper.getDetailValueAsArray(hit, "t012_obj_adr.adr_id");
        String[] addressTypes = IngridQueryHelper.getDetailValueAsArray(hit, "t012_obj_adr.typ");
        for (int i = 0; i < addressTypes.length; i++) {
            // get complete address information
            IngridHit address = IngridQueryHelper.getCompleteAddress(addressIds[i], hit.getPlugId());
            Element party = parent.addElement("smXML:CI_ResponsibleParty");
            Element e = party.addElement("smXML:individualName");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getCompletePersonName(address));
            e = party.addElement("smXML:organisationName");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_INSTITUITION));
            e = party.addElement("smXML:positionName");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_JOB));
            Element CIContact = party.addElement("smXML:contactInfo").addElement("smXML:CI_Contact");

            HashMap communications = IngridQueryHelper.getCommunications(address);
            ArrayList phoneNumbers = (ArrayList) communications.get("phone");
            ArrayList faxNumbers = (ArrayList) communications.get("fax");
            if (phoneNumbers.size() > 0 || faxNumbers.size() > 0) {
                Element CI_Telephone = CIContact.addElement("smXML:phone").addElement("smXML:CI_Telephone");
                for (int j = 0; j < phoneNumbers.size(); j++) {
                    e = CI_Telephone.addElement("smXML:voice");
                    this.addSMXMLCharacterString(e, (String) phoneNumbers.get(j));
                }
                for (int j = 0; j < faxNumbers.size(); j++) {
                    e = CI_Telephone.addElement("smXML:facsimile");
                    this.addSMXMLCharacterString(e, (String) faxNumbers.get(j));
                }
            }

            Element CIAddress = CIContact.addElement("smXML:address").addElement("smXML:CI_Address");
            e = CIAddress.addElement("smXML:deliveryPoint");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_STREET));
            e = CIAddress.addElement("smXML:city");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_CITY));
            e = CIAddress.addElement("smXML:postalCode");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_ZIP));
            e = CIAddress.addElement("smXML:country");
            this.addSMXMLCharacterString(e, IngridQueryHelper.getDetailValueAsString(address,
                    IngridQueryHelper.HIT_KEY_ADDRESS_STATE_ID));
            ArrayList emails = (ArrayList) communications.get("email");
            for (int j = 0; j < emails.size(); j++) {
                e = CIAddress.addElement("smXML:electronicMailAddress");
                this.addSMXMLCharacterString(e, (String) emails.get(j));
            }

            // CSW 2.0 unterstützt nur eine online resource
            ArrayList url = (ArrayList) communications.get("url");
            if (url.size() > 0) {
                Element CI_OnlineResource = CIContact.addElement("smXML:onlineResource").addElement(
                        "smXML:CI_OnlineResource");
                e = CI_OnlineResource.addElement("smXML:linkage");
                this.addSMXMLCharacterString(e, (String) url.get(0));
            }
        }
    }

}
