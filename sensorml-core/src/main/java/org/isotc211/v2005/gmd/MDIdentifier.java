/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.isotc211.v2005.gmd;

import net.opengis.OgcProperty;
import org.isotc211.v2005.gco.AbstractObject;


/**
 * POJO class for XML type MD_Identifier_Type(@http://www.isotc211.org/2005/gmd).
 *
 * This is a complex type.
 */
public interface MDIdentifier extends AbstractObject
{
    
    
    /**
     * Gets the authority property
     */
    public CICitation getAuthority();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the authority property
     */
    public OgcProperty<CICitation> getAuthorityProperty();
    
    
    /**
     * Checks if authority is set
     */
    public boolean isSetAuthority();
    
    
    /**
     * Sets the authority property
     */
    public void setAuthority(CICitation authority);
    
    
    /**
     * Gets the code property
     */
    public String getCode();
    
    
    /**
     * Sets the code property
     */
    public void setCode(String code);
}
