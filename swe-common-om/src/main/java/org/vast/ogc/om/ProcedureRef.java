/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin or 
 Mike Botts <mike.botts@botts-inc.net for more information.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ogc.om;

import org.vast.ogc.gml.FeatureRef;
import org.vast.util.TimeExtent;


/**
 * <p>
 * Wrapper class for use when an IProcedure object is or can be included by reference.
 * This enables fetching and instantiating the target object lazily.
 * </p>
 *
 * @author Alex Robin
 * @since Sep 28, 2012
 * */
public class ProcedureRef extends FeatureRef<IProcedure> implements IProcedure
{
    
    public ProcedureRef()
    {        
    }
    
    
    public ProcedureRef(String href)
    {
        setHref(href);
    }


    @Override
    public TimeExtent getValidTime()
    {
        return null;
    }
}
