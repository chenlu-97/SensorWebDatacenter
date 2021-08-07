/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.swe.v20;



/**
 * POJO class for XML type ByteEncodingType(@http://www.opengis.net/swe/2.0).
 *
 */
@SuppressWarnings("javadoc")
public enum ByteOrder
{
    LITTLE_ENDIAN("littleEndian"),
    BIG_ENDIAN("bigEndian");
    
    private final String text;
    
    
    
    /**
     * Private constructor for storing string representation
     */
    private ByteOrder(String s)
    {
        this.text = s;
    }
    
    
    
    /**
     * To convert an enum constant to its String representation
     */
    @Override
    public String toString()
    {
        return text;
    }
    
    
    
    /**
     * To get the enum constant corresponding to the given String representation
     */
    public static ByteOrder fromString(String s)
    {
        if (s.equals("littleEndian"))
            return LITTLE_ENDIAN;
        else if (s.equals("bigEndian"))
            return BIG_ENDIAN;
        
        throw new IllegalArgumentException("Invalid token " + s + " for enum ByteOrder");
    }
}
