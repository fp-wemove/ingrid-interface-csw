/*
 * Copyright (c) 2012 wemove digital solutions. All rights reserved.
 */
package de.ingrid.interfaces.csw.domain.request.impl;

import de.ingrid.interfaces.csw.domain.query.CSWQuery;
import de.ingrid.interfaces.csw.domain.request.GetRecordsRequest;

public class GetRecordsRequestImpl extends AbstractRequestImpl implements GetRecordsRequest {

	@Override
	public void validate() {
		// TODO Auto-generated method stub
	}

	@Override
	public CSWQuery getQuery() {
		return this.getEncoding().getQuery();
	}
}
