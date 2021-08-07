/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sos;

import org.vast.ogc.OGCRegistry;
import org.vast.ows.OWSException;
import org.vast.ows.sos.SOSUtils;
import org.vast.ows.swe.SWEResponseWriter;
import org.w3c.dom.*;
import org.vast.xml.DOMHelper;


/**
 * <p>
 * Writer to generate an XML InsertObservation response based
 * on values contained in a InsertObservationResponse object for SOS v2.0
 * </p>
 *
 * @author Alex Robin
 * @date Feb 3, 2014
 * */
public class InsertObservationResponseWriterV20 extends SWEResponseWriter<InsertObservationResponse>
{
		
	public Element buildXMLResponse(DOMHelper dom, InsertObservationResponse response, String version) throws OWSException
	{
	    dom.addUserPrefix("sos", OGCRegistry.getNamespaceURI(SOSUtils.SOS, version));
        
        // root element
        Element rootElt = dom.createElement("sos:" + response.getMessageType());
        
        // write extensions
        writeExtensions(dom, rootElt, response);
        
        // assigned observation id
        if (response.getObsId() != null)
            dom.setElementValue(rootElt, "+sos:observation", response.getObsId());
        
        return rootElt;
	}
	
}