/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.swe.json;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.vast.json.JsonStreamException;
import org.vast.json.JsonStreamWriter;


public class SWEJsonStreamWriter extends JsonStreamWriter
{
    protected final static String NO_PARENT = "";
    protected final static String NO_NS = "";
    
    // array name mappings (key is parentName, subkey is localName, value is NS uri)
    protected Map<String, Map<String, String>> arrays = new HashMap<String, Map<String, String>>();
    protected Map<String, Map<String, String>> numerics = new HashMap<String, Map<String, String>>();
    protected Map<String, Map<String, String>> valueArrays = new HashMap<String, Map<String, String>>();
    
    
    public SWEJsonStreamWriter(OutputStream os, String encoding) throws JsonStreamException
    {
        super(os, encoding);
        this.markAttributes = false;
        
        // elements to encode as JSON arrays
        addSpecialNames(arrays, NO_NS, "field", "coordinate", "item", "quality", "member");
        addSpecialNamesWithParent(arrays, NO_NS, "AllowedValues", "value", "interval");
        addSpecialNamesWithParent(arrays, NO_NS, "AllowedTimes", "value", "interval");
        addSpecialNamesWithParent(arrays, NO_NS, "AllowedTokens", "value");
        addSpecialNamesWithParent(valueArrays, NO_NS, "CountRange", "value");
        addSpecialNamesWithParent(valueArrays, NO_NS, "QuantityRange", "value");
        addSpecialNamesWithParent(valueArrays, NO_NS, "CategoryRange", "value");
        addSpecialNamesWithParent(valueArrays, NO_NS, "TimeRange", "value");
        addSpecialNamesWithParent(valueArrays, NO_NS, "interval", "interval");
        
        // elements with numerical values
        addSpecialNames(numerics, NO_NS, "nilValue", "paddingBytes-after", "paddingBytes-before", "byteLength", "significantBits", "bitLength");
        addSpecialNamesWithParent(numerics, NO_NS, "Count", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "Quantity", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "Time", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "CountRange", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "QuantityRange", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "AllowedValues", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "value", "value");
        addSpecialNamesWithParent(numerics, NO_NS, "interval", "interval");
    }
    
    
    protected void addSpecialNames(Map<String, Map<String, String>> nameMaps, String namespaceURI, String... names)
    {
        addSpecialNamesWithParent(nameMaps, namespaceURI, NO_PARENT, names);
    }
    
    
    protected void addSpecialNamesWithParent(Map<String, Map<String, String>> nameMaps, String namespaceURI, String parentName, String... names)
    {
        Map<String, String> nameMapForParent = nameMaps.get(parentName);
        if (nameMapForParent == null)
        {
            nameMapForParent = new HashMap<String, String>();
            nameMaps.put(parentName, nameMapForParent);
        }
        
        for (String name: names)
            nameMapForParent.put(name, namespaceURI);
    }
    
    
    protected boolean isSpecialName(Map<String, String> nameMap, String namespaceURI, String localName)
    {
        if (nameMap != null)
        {        
            String assocNsUri = nameMap.get(localName);
            
            if (assocNsUri == null)
                return false;
            
            else if (assocNsUri.isEmpty() || assocNsUri.equals(namespaceURI))
                return true;
        }
        
        return false;
    }
    
    
    protected boolean isSpecialPath(Map<String, Map<String, String>> nameMaps, String parentName, String namespaceURI, String localName)
    {
        Map<String, String> nameMap = nameMaps.get(parentName);
        if (nameMap != null)
            return isSpecialName(nameMap, namespaceURI, localName);
        
        return isSpecialName(nameMaps.get(NO_PARENT), namespaceURI, localName);
    }
    
    
    @Override
    protected boolean isArray(String namespaceURI, String localName)
    {
        String parentName = NO_PARENT;
        if (currentContext != null && currentContext.eltName != null)
            parentName = currentContext.eltName;
            
        return isSpecialPath(arrays, parentName, namespaceURI, localName);
    }
    
    
    @Override
    protected String getPluralName(String localName)
    {
        if ("quality".equals(localName))
            return localName;
        else
            return super.getPluralName(localName);
    }
    
    
    @Override
    protected boolean isValueArray(String namespaceURI, String localName)
    {
        String parentName = NO_PARENT;
        if (currentContext.parent != null && currentContext.parent.eltName != null)
            parentName = currentContext.parent.eltName;
        
        return isSpecialPath(valueArrays, parentName, namespaceURI, localName);
    }
    
    
    @Override
    protected boolean isNumericValue(String value)
    {
        if (!super.isNumericValue(value))
            return false;
        
        return isSpecialPath(numerics, currentContext.parent.eltName, null, currentContext.eltName);
    }


    @Override
    protected boolean isObjectElement(String namespaceURI, String localName)
    {
        // for now, all elements starting with an upper case letter are considered objects
        // we could also lookup in a map containing the list of all object elements
        return Character.isUpperCase(localName.charAt(0));
    }
}
