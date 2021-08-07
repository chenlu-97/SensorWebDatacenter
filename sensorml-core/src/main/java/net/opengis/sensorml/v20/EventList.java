/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.sensorml.v20;

import net.opengis.OgcPropertyList;


/**
 * POJO class for XML type EventListType(@http://www.opengis.net/sensorml/2.0).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface EventList extends AbstractMetadataList
{
    
    
    /**
     * Gets the list of event properties
     */
    public OgcPropertyList<Event> getEventList();
    
    
    /**
     * Returns number of event properties
     */
    public int getNumEvents();
    
    
    /**
     * Adds a new event property
     */
    public void addEvent(Event event);
}
