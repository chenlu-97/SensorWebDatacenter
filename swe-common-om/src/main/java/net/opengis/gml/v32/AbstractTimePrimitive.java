/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.gml.v32;

import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type AbstractTimePrimitiveType(@http://www.opengis.net/gml/3.2).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface AbstractTimePrimitive extends AbstractGML
{ 
    
    /**
     * Gets the list of relatedTime properties
     */
    public OgcPropertyList<AbstractTimePrimitive> getRelatedTimeList();
    
    
    /**
     * Returns number of relatedTime properties
     */
    public int getNumRelatedTimes();
    
    
    /**
     * Adds a new relatedTime property
     */
    public void addRelatedTime(AbstractTimePrimitive relatedTime);
}
