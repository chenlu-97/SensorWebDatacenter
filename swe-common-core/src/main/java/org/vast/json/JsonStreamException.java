/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.json;

import javax.xml.stream.XMLStreamException;


public class JsonStreamException extends XMLStreamException
{
    private static final long serialVersionUID = -4151694168269965972L;

    
    public JsonStreamException(String msg)
    {
        super(msg);
    }
    
    
    public JsonStreamException(Throwable cause)
    {
        super(cause);
    }
    
    
    public JsonStreamException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
