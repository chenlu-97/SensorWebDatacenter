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

import javax.xml.namespace.QName;
import org.vast.ogc.gml.IGeoFeature;
import net.opengis.OgcProperty;


/**
 * POJO class for XML type AbstractFeatureType(@http://www.opengis.net/gml/3.2).
 *
 * This is a complex type.
 */
@SuppressWarnings("javadoc")
public interface AbstractFeature extends IGeoFeature, AbstractGML
{
    
    /**
     * @return Qualified name of feature type
     */
    public QName getQName();
    
    
    /**
     * Gets the boundedBy property
     */
    public Envelope getBoundedBy();
    
    
    /**
     * Checks if boundedBy is set
     */
    public boolean isSetBoundedBy();
    
    
    /**
     * Sets the boundedByAsEnvelope property
     */
    public void setBoundedByAsEnvelope(Envelope boundedBy);
    
    
    /**
     * Gets the geometry property
     */
    public AbstractGeometry getGeometry();
    
    
    /**
     * Gets extra info (name, xlink, etc.) carried by the geometry property
     */
    public OgcProperty<AbstractGeometry> getGeometryProperty();
    
    
    /**
     * Checks if geometry is set
     */
    public boolean isSetGeometry();
    
    
    /**
     * Sets the geometry property
     */
    public void setGeometry(AbstractGeometry geom);

}
