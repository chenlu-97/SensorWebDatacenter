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

import java.util.LinkedHashSet;
import java.util.Set;
import org.vast.ows.OWSRequest;


/**
 * <p>
 * Container for SOS GetResultTemplate request parameters
 * </p>
 *
 * @author Alex Robin
 * @date Aug 08, 2012
 * */
public class GetResultTemplateRequest extends OWSRequest
{
    protected String offering;
    protected Set<String> observables;
    protected String format;
    
	
	public GetResultTemplateRequest()
	{
		service = "SOS";
		operation = "GetResultTemplate";
        observables = new LinkedHashSet<>();
	}


	public String getOffering()
    {
        return offering;
    }


    public void setOffering(String offering)
    {
        this.offering = offering;
    }


    public Set<String> getObservables()
    {
        return observables;
    }


    public void setObservables(Set<String> observables)
    {
        this.observables = observables;
    }


    public String getFormat()
    {
        return format;
    }


    public void setFormat(String format)
    {
        this.format = format;
    }
}
