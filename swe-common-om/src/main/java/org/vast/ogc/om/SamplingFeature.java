/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ogc.om;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.namespace.QName;
import net.opengis.gml.v32.AbstractGeometry;
import net.opengis.gml.v32.LineString;
import net.opengis.gml.v32.Point;
import net.opengis.gml.v32.Polygon;
import org.vast.ogc.gml.FeatureRef;
import org.vast.ogc.gml.GenericFeatureImpl;
import org.vast.ogc.gml.IFeature;
import org.vast.ogc.xlink.CachedReference;


public class SamplingFeature<GeomType extends AbstractGeometry> extends GenericFeatureImpl
{
    private static final long serialVersionUID = 6351566323396110876L;
    public static final String SAMS_NS_PREFIX = "sams";
    public static final String SAMS_NS_URI = "http://www.opengis.net/samplingSpatial/2.0";
    public static final String SF_NS_PREFIX = "sf";
    public static final String SF_NS_URI = "http://www.opengis.net/sampling/2.0";
    
    public static final QName SF_SAMPLING_FEATURE = new QName(SAMS_NS_URI, "SF_SpatialSamplingFeature", SAMS_NS_PREFIX);
    public static final QName PROP_TYPE = new QName(SF_NS_URI, "type", SF_NS_PREFIX);
    public static final QName PROP_SAMPLED_FEATURE = new QName(SF_NS_URI, "sampledFeature", SF_NS_PREFIX);
    public static final QName PROP_HOSTED_PROCEDURE = new QName(SAMS_NS_URI, "hostedProcedure", SAMS_NS_PREFIX);
    public static final QName PROP_SHAPE = new QName(SAMS_NS_URI, "shape", SAMS_NS_PREFIX);
    
    
    public SamplingFeature()
    {
        super(SF_SAMPLING_FEATURE);
    }
    
    
    public SamplingFeature(String type)
    {
        this();
        setType(type);
    }


    public String getType()
    {
        @SuppressWarnings("unchecked")
        CachedReference<Object> ref = (CachedReference<Object>)getProperty(PROP_TYPE);
        if (ref == null)
            return null;
        return ref.getHref();
    }


    public void setType(String type)
    {
        setProperty(PROP_TYPE, new CachedReference<Object>(type));
    }
    
    
    public void setSampledFeatureUID(String featureUID)
    {
        if (featureUID == null)
            properties.remove(PROP_SAMPLED_FEATURE);
        else
            setProperty(PROP_SAMPLED_FEATURE, new FeatureRef<IFeature>(featureUID));
    }
    
    
    public String getSampledFeatureUID()
    {
        FeatureRef<?> ref = (FeatureRef<?>)getProperty(PROP_SAMPLED_FEATURE);
        if (ref == null)
            return null;
        return ref.getHref();
    }
    
    
    public void setHostedProcedureUID(String processUID)
    {
        if (processUID == null)
            properties.remove(PROP_HOSTED_PROCEDURE);
        else
            setProperty(PROP_HOSTED_PROCEDURE, new ProcedureRef(processUID));
    }
    
    
    public String getHostedProcedureUID()
    {
        ProcedureRef ref = (ProcedureRef)getProperty(PROP_HOSTED_PROCEDURE);
        if (ref == null)
            return null;
        return ref.getHref();
    }
    
    
    public void setShape(GeomType geom)
    {
        if (geom == null)
            properties.remove(PROP_SHAPE);
        else
            setProperty(PROP_SHAPE, geom);
    }
    
    
    public GeomType getShape()
    {
        @SuppressWarnings("unchecked")
        GeomType geom = (GeomType)getProperty(PROP_SHAPE);
        if (geom != null)
            return geom;
        else
            return getShape();
    }
    
    
    @Override
    public boolean isSetGeometry()
    {
        return properties.containsKey(PROP_SHAPE);
    }
    

    @Override
    public GeomType getGeometry()
    {
        @SuppressWarnings("unchecked")
        GeomType geom = (GeomType)super.getGeometry();
        if (geom != null)
            return geom;
        else
            return getShape();
    }

    
    /*
     * Override so we can reorder properties according to schema
     */
    @Override
    public Map<QName, Object> getProperties()
    {
        return new AbstractMap<QName, Object>() {
            @Override
            public Set<Entry<QName, Object>> entrySet()
            {
                return new AbstractSet<Entry<QName, Object>>() {
                    
                    QName[] propOrder = {
                      PROP_TYPE,
                      PROP_SAMPLED_FEATURE,
                      PROP_HOSTED_PROCEDURE,
                      PROP_SHAPE
                    };
                    
                    @Override
                    public Iterator<Entry<QName, Object>> iterator()
                    {
                        return Arrays.stream(propOrder)
                            .map(qname -> {
                                Object val = properties.get(qname);
                                return (Entry<QName, Object>)(val != null ?
                                    new AbstractMap.SimpleEntry<>(qname, val) : null);
                            })
                            .filter(Objects::nonNull)
                            .iterator();
                    }

                    @Override
                    public int size()
                    {
                        return (int)Arrays.stream(propOrder)
                            .filter(qname -> properties.containsKey(qname))
                            .count();
                    }                    
                };
            }            
        };
    }
    
    
    public SamplingFeature<? extends AbstractGeometry> getAsSpecializedType()
    {
        @SuppressWarnings("rawtypes")
        String type = properties.containsKey(PROP_TYPE) ? ((CachedReference)getProperty(PROP_TYPE)).getHref() : null;
        SamplingFeature<? extends AbstractGeometry> sf = null;
        
        if (SamplingPoint.TYPE.equals(type))
        {
            sf = new SamplingPoint();
            ((SamplingPoint)sf).setShape((Point)getShape());
        }
        else if (SamplingSurface.TYPE.equals(type))
        {
            sf = new SamplingSurface();
            ((SamplingSurface)sf).setShape((Polygon)getShape());
        }
        else if (SamplingCurve.TYPE.equals(type))
        {
            sf = new SamplingCurve();
            ((SamplingCurve)sf).setShape((LineString)getShape());
        }
        else
            throw new UnsupportedOperationException("Unsupported sampling feature type " + type);
        
        sf.setId(getId());
        sf.setIdentifier(getIdentifier());
        sf.setDescription(getDescription());
        sf.setName(getName());
        sf.setBoundedByAsEnvelope(getBoundedBy());
        if (sf.isSetGeometry())
            sf.setGeometry(getGeometry());
        sf.setSampledFeatureUID(getSampledFeatureUID());
        sf.setHostedProcedureUID(getHostedProcedureUID());
        
        return sf;
    }

}