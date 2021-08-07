/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Mike Botts <mike.botts@uah.edu>
 or Alexandre Robin for more information.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sos;

import net.opengis.swe.v20.DataComponent;
import net.opengis.swe.v20.DataEncoding;
import org.vast.ows.OWSResponse;


/**
 * <p>
 * Container for SOS GetResultTemplate response data
 * </p>
 *
 * @author Alex Robin
 * @date Aug 19, 2012
 * */
public class GetResultTemplateResponse extends OWSResponse
{
    protected DataComponent resultStructure;
    protected DataEncoding resultEncoding;
    
	
	public GetResultTemplateResponse()
	{
		service = "SOS";
		messageType = "GetResultTemplateResponse";
	}


    public DataComponent getResultStructure()
    {
        return resultStructure;
    }


    public void setResultStructure(DataComponent resultStructure)
    {
        this.resultStructure = resultStructure;
    }


    public DataEncoding getResultEncoding()
    {
        return resultEncoding;
    }


    public void setResultEncoding(DataEncoding resultEncoding)
    {
        this.resultEncoding = resultEncoding;
    }	
}
