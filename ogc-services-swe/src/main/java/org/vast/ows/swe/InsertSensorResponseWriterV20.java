/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.swe;

import org.vast.ogc.OGCRegistry;
import org.vast.ows.OWSException;
import org.w3c.dom.*;
import org.vast.xml.DOMHelper;


/**
 * <p>
 * Writer to generate an XML InsertSensor response based
 * on values contained in a InsertSensorResponse object for SOS v2.0
 * </p>
 *
 * @author Alex Robin
 * @date Feb 3, 2014
 * */
public class InsertSensorResponseWriterV20 extends SWEResponseWriter<InsertSensorResponse>
{
		
	public Element buildXMLResponse(DOMHelper dom, InsertSensorResponse response, String version) throws OWSException
	{
	    dom.addUserPrefix("swes", OGCRegistry.getNamespaceURI(SWESUtils.SWES, version));
        
        // root element
        Element rootElt = dom.createElement("swes:" + response.getMessageType());
        
        // write extensions
        writeExtensions(dom, rootElt, response);
        
        // assigned procedure
        dom.setElementValue(rootElt, "+swes:assignedProcedure", response.getAssignedProcedureId());
        
        // assigned offering
        dom.setElementValue(rootElt, "+swes:assignedOffering", response.getAssignedOffering());
        
        return rootElt;
	}
	
}