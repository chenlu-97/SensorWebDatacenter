/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
 ******************************* END LICENSE BLOCK ***************************/

package org.vast.ows;

import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p>
 * Base interface for all OWS request writers
 * </p>
 *
 * @author Alex Robin
 * @since Jan 16, 2007
 * * @param <RequestType> Type of request object supported by this writer
 */
public interface OWSRequestWriter<RequestType extends OWSRequest>
{

    /**
     * Builds a String containing the GET request URL
     * @param request OWS request object
     * @return qury string generated from the content of the request object
     * @throws OWSException 
     */
    public String buildURLQuery(RequestType request) throws OWSException;


    /**
     * Builds a DOM element containing the request XML
     * Note that the element is not yet appended to any parent.
     * @param dom DOMHelper instance that will own the generated element
     * @param request OWs request object
     * @return DOM element containing the XML representation of the request
     * @throws OWSException 
     */
    public Element buildXMLQuery(DOMHelper dom, RequestType request) throws OWSException;

}