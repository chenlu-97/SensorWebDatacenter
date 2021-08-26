/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Initial Developer of the Original Code is SENSIA SOFTWARE LLC.
 Portions created by the Initial Developer are Copyright (C) 2012
 the Initial Developer. All Rights Reserved.

 Please Contact Alexandre Robin for more
 information.
 
 Contributor(s): 
    Alexandre Robin
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ogc.xlink;

import java.io.IOException;


/**
 * <p>
 * Interface for resolving a URI to an object instance obtained from data
 * referenced from an XlinkReference object.
 * @param <TargetType> Type of the link target object
 * </p>
 *
 * @author Alex Robin
 * @since Sep 28, 2012
 * */
public interface IReferenceResolver<TargetType>
{
    /**
     * Fetches the object referenced by the given URI
     * @param uri
     * @return target object
     * @throws IOException if the object cannot be fetched
     */
    public TargetType fetchTarget(String uri) throws IOException;
}